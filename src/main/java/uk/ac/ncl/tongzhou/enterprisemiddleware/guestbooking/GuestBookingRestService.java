
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 23:55 21-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.guestbooking;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.quickstarts.wfk.util.RestServiceException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.Booking;
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.BookingDto;
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.BookingRestService;
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.BookingService;
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.CustomerNotFoundException;
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.FlightAndDateExistsException;
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.FlightNotFoundException;
import uk.ac.ncl.tongzhou.enterprisemiddleware.customer.CustomerRestService;
import uk.ac.ncl.tongzhou.enterprisemiddleware.customer.CustomerService;
import uk.ac.ncl.tongzhou.enterprisemiddleware.flight.Flight;
import uk.ac.ncl.tongzhou.enterprisemiddleware.flight.FlightService;

/**
 * <p>
 * a GuestBooking Bean and RestService class. The Bean is not persisted and
 * should not be a Hibernate Entity. It should simply contain fields (and
 * getters and setters) for a Customer object and a Booking object. Its purpose
 * is simply to allow the Jackson JSON library to deserialize a request Body
 * containing both a Customer and a Booking.
 * </p>
 * 
 * @author Tong Zhou
 * @see BookingService
 * @see CustomerService
 * @see FlightService
 * @see javax.ws.rs.core.Response
 */
@Path("/guestbookings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/guestbookings", description = "Operations for guest bookings")
@Stateless
// @TransactionManagement(TransactionManagementType.BEAN)
public class GuestBookingRestService {
	@Inject
	private @Named("logger") Logger log;

	@Inject
	private BookingRestService bookingRestService;

	@Inject
	private CustomerRestService customerRestService;

	// @Inject
	// private CustomerService customerService;
	//
	// @Inject
	// private FlightRestService flightRestService;

	@Inject
	private FlightService flightService;

	// @Resource
	// private UserTransaction userTransaction;

	/**
	 * <p>
	 * Creates a new Guest Booking from the values provided. Performs validation and
	 * will return a JAX-RS response with either 201 (Resource created) or with a
	 * map of fields, and related errors.
	 * </p>
	 *
	 * @param guestBooking
	 *            The Booking object, constructed automatically from JSON input, to
	 *            be <i>created</i> via {@link BookingService#create(Booking)}
	 * @return A Response indicating the outcome of the create operation
	 */
	@POST
	@ApiOperation(value = "Add a new Booking to the database")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Guest Booking created successfully."),
			@ApiResponse(code = 400, message = "Invalid Booking supplied in request body"),
			@ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request") })
	public Response createGuestBooking(
			@ApiParam(value = "JSON representation of Customer object to be added to the database", required = true) GuestBooking guestbooking) {

		if (guestbooking == null) {
			throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
		}

		Response.ResponseBuilder builder;

		try {
			// userTransaction.begin();

			customerRestService.createCustomer(guestbooking.getCustomer());
			BookingDto booking = new BookingDto();
			booking.setCustomerId(guestbooking.getCustomer().getId());
			booking.setFlightId(guestbooking.getFlightId());
			booking.setBookingDate(guestbooking.getBookingDate());
			bookingRestService.createBooking(booking);

			// userTransaction.commit();

			builder = Response.status(Response.Status.CREATED).entity(booking);
		} catch (ConstraintViolationException e) {
			// Handle bean validation issues
			Map<String, String> responseObj = new HashMap<>();

			for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
				responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
			}
			// try {
			// userTransaction.rollback();
			// } catch (SystemException syex) {
			// Logger.getLogger(BookingRestService.class.getName()).log(Level.SEVERE,
			// "Failed to rollback", syex);
			// }
			Logger.getLogger(BookingRestService.class.getName()).log(Level.SEVERE,
					"Failed to create Guest Booking, transaction rolled back", e);
			throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, e);

		} catch (CustomerNotFoundException e) {
			// Handle the unique constraint violation
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("customerId", "The customerId does not exist");
			// try {
			// userTransaction.rollback();
			// } catch (SystemException syex) {
			// Logger.getLogger(BookingRestService.class.getName()).log(Level.SEVERE,
			// "Failed to rollback", syex);
			// }
			Logger.getLogger(BookingRestService.class.getName()).log(Level.SEVERE,
					"Failed to create Guest Booking, transaction rolled back", e);
			throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, e);
		} catch (FlightNotFoundException e) {
			// Handle the unique constraint violation
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("flightId", "The flightId does not exist");
			// try {
			// userTransaction.rollback();
			// } catch (SystemException syex) {
			// Logger.getLogger(BookingRestService.class.getName()).log(Level.SEVERE,
			// "Failed to rollback", syex);
			// }
			Logger.getLogger(BookingRestService.class.getName()).log(Level.SEVERE,
					"Failed to create Guest Booking, transaction rolled back", e);
			throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, e);
		} catch (FlightAndDateExistsException e) {
			// Handle the unique constraint violation
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("flightId,bookingTime", "Booking flight and date duplicate with existing record");
			// try {
			// userTransaction.rollback();
			// } catch (SystemException syex) {
			// Logger.getLogger(BookingRestService.class.getName()).log(Level.SEVERE,
			// "Failed to rollback", syex);
			// }
			Logger.getLogger(BookingRestService.class.getName()).log(Level.SEVERE,
					"Failed to create Guest Booking, transaction rolled back", e);
			throw new RestServiceException("Bad Request", responseObj, Response.Status.CONFLICT, e);
		} catch (Exception e) {
			// Handle generic exceptions
			log.log(Level.SEVERE, e.getMessage());
			// try {
			// userTransaction.rollback();
			// } catch (SystemException syex) {
			// Logger.getLogger(BookingRestService.class.getName()).log(Level.SEVERE,
			// "Failed to rollback", syex);
			// }
			Logger.getLogger(BookingRestService.class.getName()).log(Level.SEVERE,
					"Failed to create Guest Booking, transaction rolled back", e);
			throw new RestServiceException(e);
		}

		log.info("createBooking completed. Guest Booking= " + guestbooking.toString());
		return builder.build();
	}
}
