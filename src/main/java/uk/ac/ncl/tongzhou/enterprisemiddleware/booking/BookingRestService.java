
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 01:13 16-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.booking;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.Booking;
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.BookingService;

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
	 * Return all the Bookings. They are sorted alphabetically by name.
	 * </p>
	 *
	 * <p>
	 * The url may optionally include query parameters specifying a Booking's name
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
	public Response retrieveAllBookings(@QueryParam("bookingName") String bookingName) {
		// Create an empty collection to contain the intersection of Bookings to be
		// returned
		List<Booking> bookings;

		if (bookingName == null) {
			bookings = service.findAllOrderedByName();
		} else {
			// bookings = service.findAllByName(name);
			bookings = service.findAllOrderedByName();
		}

		return Response.ok(bookings).build();
	}
}
