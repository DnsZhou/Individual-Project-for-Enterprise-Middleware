
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 20:10 22-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.travelagent;

import java.util.HashMap;
import java.util.Map;
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
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.BookingDto;
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.BookingRestService;
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

	@Inject
	private BookingService bookingService;

	@Inject
	private TravelAgentService travelAgentService;
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
		Long hotelCustomerId = null;
		Long taxiCustomerId = null;
		Long hotelBookingId = null;
		Long taxiBookingId = null;
		Booking flightBooking = null;

		try {

			// Find the Customer in own by the customerId provided, popup exception if not
			// found
			Customer currentCustomer = customerService.findById(travelAgentBookingDto.getCustomerId());

			// Find the Flight in own system by the flightId provided, popup exception if
			// not found
			Flight currentFlight = flightService.findById(travelAgentBookingDto.getFlightId());

			// Find the customer in other two system by customer, return the customerId.
			// create one if not found.
			hotelCustomerId = travelAgentService.getCustomerIdInOuterSystem(BookingSystemType.FLIGHT_SYSTEM,
					currentCustomer);
			taxiCustomerId = travelAgentService.getCustomerIdInOuterSystem(BookingSystemType.FLIGHT_SYSTEM,
					currentCustomer);

			// create the booking in own system
			BookingDto bookingDto = new BookingDto();
			bookingDto.setBookingDate(travelAgentBookingDto.getBookingDate());
			bookingDto.setCustomerId(travelAgentBookingDto.getCustomerId());
			bookingDto.setFlightId(travelAgentBookingDto.getFlightId());
			flightBooking = bookingService.create(bookingDto);

			// create the booking in other two system
			hotelBookingId = travelAgentService.createBookingInOuterSystem(BookingSystemType.FLIGHT_SYSTEM,
					hotelCustomerId, travelAgentBookingDto.getHotelId(), travelAgentBookingDto.getBookingDate());
			taxiBookingId = travelAgentService.createBookingInOuterSystem(BookingSystemType.FLIGHT_SYSTEM,
					taxiCustomerId, travelAgentBookingDto.getTaxiId(), travelAgentBookingDto.getBookingDate());

			// create agent booking in local system
			TravelAgentBooking travelAgentBooking = travelAgentService.create(travelAgentBookingDto,
					flightBooking.getId(), hotelBookingId, taxiBookingId);
			builder = Response.status(Response.Status.CREATED).entity(travelAgentBooking);
		} catch (Exception e) {
			log.info(e.getMessage());

			// roll back the booking in other system
			try {
				travelAgentService.deleteBookingInOuterSystem(BookingSystemType.FLIGHT_SYSTEM, hotelBookingId);
				travelAgentService.deleteBookingInOuterSystem(BookingSystemType.FLIGHT_SYSTEM, taxiBookingId);
				bookingService.delete(flightBooking);
			} catch (Exception re) {
				log.info("Roll back failed," + re.getMessage());
			}
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("travelAgentBooking", "Failed to create travelAgentBooking");
			throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, e);
		}

		log.info("create agentBooking completed. Booking = " + travelAgentBookingDto.toString());
		return builder.build();

	}

}
