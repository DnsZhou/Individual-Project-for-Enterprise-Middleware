
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 00:31 16-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.customer;

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

import org.jboss.quickstarts.wfk.area.InvalidAreaCodeException;
import org.jboss.quickstarts.wfk.contact.UniqueEmailException;
import org.jboss.quickstarts.wfk.util.RestServiceException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * <p>
 * This class produces a RESTful service exposing the functionality of
 * {@link CustomerService}.
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
 * The full path for accessing endpoints defined herein is: api/customers/*
 * </p>
 * 
 * @author Tong Zhou
 * @see CustomerService
 * @see javax.ws.rs.core.Response
 */
@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/customers", description = "Operations about customers")
@Stateless
public class CustomerRestService {
	@Inject
	private @Named("logger") Logger log;

	@Inject
	private CustomerService service;

	/**
	 * <p>
	 * Return all the Customers. They are sorted alphabetically by name.
	 * </p>
	 *
	 * <p>
	 * The url may optionally include query parameters specifying a Customer's name
	 * </p>
	 *
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 * GET api/customers?name=John
	 * </pre>
	 * </p>
	 *
	 * @return A Response containing a list of Customers
	 */
	@GET
	@ApiOperation(value = "Fetch all Customers", notes = "Returns a JSON array of all stored Customer objects.")
	public Response retrieveAllCustomers(@QueryParam("customerName") String customerName) {
		// Create an empty collection to contain the intersection of Customers to be
		// returned
		List<Customer> customers;

		if (customerName == null) {
			customers = service.findAllOrderedByName();
		} else {
			// customers = service.findAllByName(name);
			customers = service.findAllOrderedByName();
		}

		return Response.ok(customers).build();
	}

	/**
	 * <p>
	 * Creates a new customer from the values provided. Performs validation and will
	 * return a JAX-RS response with either 201 (Resource created) or with a map of
	 * fields, and related errors.
	 * </p>
	 *
	 * @param customer
	 *            The Customer object, constructed automatically from JSON input, to
	 *            be <i>created</i> via {@link CustomerService#create(Customer)}
	 * @return A Response indicating the outcome of the create operation
	 */
	@SuppressWarnings("unused")
	@POST
	@ApiOperation(value = "Add a new Customer to the database")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Customer created successfully."),
			@ApiResponse(code = 400, message = "Invalid Customer supplied in request body"),
			@ApiResponse(code = 409, message = "Customer supplied in request body conflicts with an existing Customer"),
			@ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request") })
	public Response createCustomer(
			@ApiParam(value = "JSON representation of Customer object to be added to the database", required = true) Customer customer) {

		if (customer == null) {
			throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
		}

		Response.ResponseBuilder builder;

		try {
			// Go add the new Customer.
			service.create(customer);

			// Create a "Resource Created" 201 Response and pass the customer back in case
			// it is needed.
			builder = Response.status(Response.Status.CREATED).entity(customer);

		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			Map<String, String> responseObj = new HashMap<>();

			for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
				responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
			}
			throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);

		} catch (UniqueEmailException e) {
			// Handle the unique constraint violation
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("email", "That email is already used, please use a unique email");
			throw new RestServiceException("Bad Request", responseObj, Response.Status.CONFLICT, e);
		} catch (InvalidAreaCodeException e) {
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("area_code", "The telephone area code provided is not recognised, please provide another");
			throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, e);
		} catch (Exception e) {
			// Handle generic exceptions
			throw new RestServiceException(e);
		}

		log.info("createCustomer completed. Customer = " + customer.toString());
		return builder.build();
	}
}
