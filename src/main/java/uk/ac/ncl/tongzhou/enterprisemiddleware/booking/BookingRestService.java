
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 01:13 16-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.quickstarts.wfk.util.RestServiceException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * <p>
 * This class produces a RESTful service exposing the functionality of
 * {@link BookingService}.
 * </p>
 *
 * <p>
 * The Path annotation defines this as a REST Web Service using JAX-RS.
 * </p>
 *
 * <p>
 * By placing the Consumes and Produces annotations at the class level the
 * methods all default to JSON. However, they can be overriden by adding the
 * Consumes or Produces annotations to the individual methods.
 * </p>
 *
 * <p>
 * It is Stateless to "inform the container that this RESTful web service should
 * also be treated as an EJB and allow transaction demarcation when accessing
 * the database." - Antonio Goncalves
 * </p>
 *
 * <p>
 * The full path for accessing endpoints defined herein is: api/bookings/*
 * </p>
 * 
 * @author Tong Zhou
 * @see BookingService
 * @see javax.ws.rs.core.Response
 */
@Path("/bookings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/bookings", description = "Operations about bookings")
@Stateless
public class BookingRestService {
	@Inject
	private @Named("logger") Logger log;

	@Inject
	private BookingService service;


	/**
	 * <p>
	 * Return all the Bookings. They are sorted alphabetically by bookingId.
	 * </p>
	 *
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 * GET api/bookings?name=John
	 * </pre>
	 * </p>
	 *
	 * @return A Response containing a list of Bookings
	 */
	@GET
	@ApiOperation(value = "Fetch all Bookings", notes = "Returns a JSON array of all stored Booking objects.")
	public Response retrieveAllBookings() {
		// Create an empty collection to contain the intersection of Bookings to be
		// returned
		List<Booking> bookings;

		bookings = service.findAllOrderedByName();

		return Response.ok(bookings).build();
	}

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
	public Response createBooking(
			@ApiParam(value = "JSON representation of Booking object to be added to the database", required = true) Booking booking) {

		if (booking == null) {
			throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
		}

		Response.ResponseBuilder builder;

		try {
			// Go add the new Booking.
			service.create(booking);

			// Create a "Resource Created" 201 Response and pass the Booking back in case
			// it is needed.
			builder = Response.status(Response.Status.CREATED).entity(booking);

		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			Map<String, String> responseObj = new HashMap<>();

			for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
				responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
			}
			throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);

		} catch (CustomerNotFoundException e) {
			// Handle the unique constraint violation
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("customerId", "The customerId not exist");
			throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, e);
		} catch (FlightNotFoundException e) {
			// Handle the unique constraint violation
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("flightId", "The flightId not exist");
			throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, e);
		} catch (Exception e) {
			// Handle generic exceptions
			log.log(Level.SEVERE, e.getMessage());
			throw new RestServiceException(e);
		}

		log.info("createBooking completed. Booking = " + booking.toString());
		return builder.build();
	}

	/**
	 * <p>
	 * Deletes a booking using the ID provided. If the ID is not present then
	 * nothing can be deleted.
	 * </p>
	 *
	 * <p>
	 * Will return a JAX-RS response with either 204 NO CONTENT or with a map of
	 * fields, and related errors.
	 * </p>
	 *
	 * @param id
	 *            The Long parameter value provided as the id of the Booking to be
	 *            deleted
	 * @return A Response indicating the outcome of the delete operation
	 */
	@DELETE
	@Path("/{id:[0-9]+}")
	@ApiOperation(value = "Delete a Booking from the database")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "The booking has been successfully deleted"),
			@ApiResponse(code = 400, message = "Invalid Booking id supplied"),
			@ApiResponse(code = 404, message = "Booking with id not found"),
			@ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request") })
	public Response deleteBooking(
			@ApiParam(value = "Id of Booking to be deleted", allowableValues = "range[0, infinity]", required = true) @PathParam("id") long id) {

		Response.ResponseBuilder builder;

		Booking booking = service.findById(id);
		if (booking == null) {
			// Verify that the booking exists. Return 404, if not present.
			throw new RestServiceException("No Booking with the id " + id + " was found!", Response.Status.NOT_FOUND);
		}

		try {
			service.delete(booking);

			builder = Response.noContent();

		} catch (Exception e) {
			// Handle generic exceptions
			throw new RestServiceException(e);
		}
		log.info("deleteBooking completed. Booking = " + booking.toString());
		return builder.build();
	}
}
