
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 20:10 22-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.travelagent;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
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
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.BookingService;
import uk.ac.ncl.tongzhou.enterprisemiddleware.customer.Customer;
import uk.ac.ncl.tongzhou.enterprisemiddleware.customer.CustomerService;
import uk.ac.ncl.tongzhou.enterprisemiddleware.flight.Flight;
import uk.ac.ncl.tongzhou.enterprisemiddleware.flight.FlightService;

/**
 * TravelRestService
 * 
 * @author Tong Zhou
 */
@Path("/travelagent")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/travelagent", description = "Operations about bookings")
@Stateless
public class TravelAgentRestService {
	@Inject
	private @Named("logger") Logger log;

	@Inject
	private CustomerService customerService;

	@Inject
	private FlightService flightService;

	/**
	 * <p>
	 * Create a new client which will be used for our outgoing REST client
	 * communication
	 * </p>
	 */
	// public TravelAgentRestService() {
	// // Create client service instance to make REST requests to upstream service
	// client = new ResteasyClientBuilder().build();
	// }

	/**
	 * <p>
	 * Creates a new Booking from the values provided. Performs validation and will
	 * return a JAX-RS response with either 201 (Resource created) or with a map of
	 * fields, and related errors.
	 * </p>
	 *
	 * @param booking
	 *            The Booking object, constructed automatically from JSON input, to
	 *            be <i>created</i> via {@link BookingService#create(Booking)}
	 * @return A Response indicating the outcome of the create operation
	 */
	@POST
	@ApiOperation(value = "Add a new Booking to the database")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Booking created successfully."),
			@ApiResponse(code = 400, message = "Invalid Booking supplied in request body"),
			@ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request") })
	public Response createTravelAgentBooking(
			@ApiParam(value = "JSON representation of Booking object to be added to the database", required = true) TravelAgentBookingDto travelAgentBookingDto) {

		if (travelAgentBookingDto == null) {
			throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
		}

		Response.ResponseBuilder builder;

		try {

			// Find the Customer in own by the customerId provided, popup error if not found
			Customer currentCustomer = customerService.findById(travelAgentBookingDto.getCustomerId());

			// Find the Flight in own system by the flightId provided, popup error if not
			// found
			Flight currentFlight = flightService.findById(travelAgentBookingDto.getFlightId());

			// Find the customer in other two system by customer, return the customerId.
			// create one if not found.
			Long HotelCustomerId = TravelAgentService.getCustomerIdInOuterSystem(BookingSystemType.FLIGHT_SYSTEM,
					currentCustomer);
			Long TaxiCustomerId = TravelAgentService.getCustomerIdInOuterSystem(BookingSystemType.FLIGHT_SYSTEM,
					currentCustomer);

			// create the booking in own system

			// create the booking in other two system
		} catch (Exception e) {
			log.info(e.getMessage());
		}

		// Create client service instance to make REST requests to other service

		// try {
		// // Go add the new Booking.
		// bookingRestService.createBooking(booking)
		//
		// // Create a "Resource Created" 201 Response and pass the Booking back in case
		// // it is needed.
		// builder = Response.status(Response.Status.CREATED).entity(booking);
		//
		// } catch (ConstraintViolationException ce) {
		// // Handle bean validation issues
		// Map<String, String> responseObj = new HashMap<>();
		//
		// for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
		// responseObj.put(violation.getPropertyPath().toString(),
		// violation.getMessage());
		// }
		// throw new RestServiceException("Bad Request", responseObj,
		// Response.Status.BAD_REQUEST, ce);
		//
		// } catch (CustomerNotFoundException e) {
		// // Handle the unique constraint violation
		// Map<String, String> responseObj = new HashMap<>();
		// responseObj.put("customerId", "The customerId does not exist");
		// throw new RestServiceException("Bad Request", responseObj,
		// Response.Status.BAD_REQUEST, e);
		// } catch (FlightNotFoundException e) {
		// // Handle the unique constraint violation
		// Map<String, String> responseObj = new HashMap<>();
		// responseObj.put("flightId", "The flightId does not exist");
		// throw new RestServiceException("Bad Request", responseObj,
		// Response.Status.BAD_REQUEST, e);
		// } catch (FlightAndDateExistsException e) {
		// // Handle the unique constraint violation
		// Map<String, String> responseObj = new HashMap<>();
		// responseObj.put("flightId,bookingTime", "Booking flight and date duplicate
		// with existing record");
		// throw new RestServiceException("Bad Request", responseObj,
		// Response.Status.CONFLICT, e);
		// } catch (Exception e) {
		// // Handle generic exceptions
		// log.log(Level.SEVERE, e.getMessage());
		// throw new RestServiceException(e);
		// }
		//
		// log.info("createBooking completed. Booking = " + booking.toString());
		// return builder.build();

		return null;
	}

}
