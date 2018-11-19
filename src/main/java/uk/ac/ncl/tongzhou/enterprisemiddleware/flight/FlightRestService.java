
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 01:13 16-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.flight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
 * {@link FlightService}.
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
 * The full path for accessing endpoints defined herein is: api/flights/*
 * </p>
 * 
 * @author Tong Zhou
 * @see FlightService
 * @see javax.ws.rs.core.Response
 */
@Path("/flights")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/flights", description = "Operations about flights")
@Stateless
public class FlightRestService {
	@Inject
	private @Named("logger") Logger log;

	@Inject
	private FlightService service;

	/**
	 * <p>
	 * Return all the Flights. They are sorted alphabetically by Number.
	 * </p>
	 *
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 * GET api/flights
	 * </pre>
	 * </p>
	 *
	 * @return A Response containing a list of Flights
	 */
	@GET
	@ApiOperation(value = "Fetch all Flights", notes = "Returns a JSON array of all stored Flight objects.")
	public Response retrieveAllFlights() {
		// Create an empty collection to contain the intersection of Flights to be
		// returned
		List<Flight> flights;

		flights = service.findAllOrderedByNumber();

		return Response.ok(flights).build();
	}

	/**
	 * <p>
	 * Creates a new Flight from the values provided. Performs validation and will
	 * return a JAX-RS response with either 201 (Resource created) or with a map of
	 * fields, and related errors.
	 * </p>
	 *
	 * @param Flight
	 *            The Flight object, constructed automatically from JSON input, to
	 *            be <i>created</i> via {@link FlightService#create(Flight)}
	 * @return A Response indicating the outcome of the create operation
	 */
	@POST
	@ApiOperation(value = "Add a new Flight to the database")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Flight created successfully."),
			@ApiResponse(code = 400, message = "Invalid Flight supplied in request body"),
			@ApiResponse(code = 409, message = "Flight supplied in request body conflicts with an existing Flight"),
			@ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request") })
	public Response createFlight(
			@ApiParam(value = "JSON representation of Flight object to be added to the database", required = true) Flight Flight) {

		if (Flight == null) {
			throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
		}

		Response.ResponseBuilder builder;

		try {
			// Go add the new Flight.
			service.create(Flight);

			// Create a "Resource Created" 201 Response and pass the Flight back in case
			// it is needed.
			builder = Response.status(Response.Status.CREATED).entity(Flight);

		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			Map<String, String> responseObj = new HashMap<>();

			for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
				responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
			}
			throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);

			// } catch (UniqueEmailException e) {
			// // Handle the unique constraint violation
			// Map<String, String> responseObj = new HashMap<>();
			// responseObj.put("email", "That email is already used, please use a unique
			// email");
			// throw new RestServiceException("Bad Request", responseObj,
			// Response.Status.CONFLICT, e);
		} catch (Exception e) {
			// Handle generic exceptions
			throw new RestServiceException(e);
		}

		log.info("createFlight completed. Flight = " + Flight.toString());
		return builder.build();
	}
}
