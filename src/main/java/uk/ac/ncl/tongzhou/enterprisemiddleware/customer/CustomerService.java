
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 00:29 16-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.customer;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

/**
 * <p>
 * This Service assumes the Control responsibility in the ECB pattern.
 * </p>
 *
 * <p>
 * The validation is done here so that it may be used by other Boundary
 * Resources. Other Business Logic would go here as well.
 * </p>
 *
 * <p>
 * There are no access modifiers on the methods, making them 'package' scope.
 * They should only be accessed by a Boundary / Web Service class with public
 * methods.
 * </p>
 *
 *
 * @author Tong Zhou
 * @see CustomerValidator
 * @see CustomerRepository
 */
@Dependent
public class CustomerService {
	@Inject
	private @Named("logger") Logger log;
	@Inject
	private CustomerValidator validator;

	@Inject
	private CustomerRepository crud;

	private ResteasyClient client;

	/**
	 * <p>
	 * Create a new client which will be used for our outgoing REST client
	 * communication
	 * </p>
	 */
	public CustomerService() {
		// Create client service instance to make REST requests to upstream service
		client = new ResteasyClientBuilder().build();
	}

	/**
	 * <p>
	 * Returns a List of all persisted {@link Customer} objects, sorted
	 * alphabetically by last name.
	 * <p/>
	 *
	 * @return List of Customer objects
	 */
	List<Customer> findAllOrderedByName() {
		return crud.findAllOrderedByName();
	}

	/**
	 * <p>
	 * Writes the provided Customer object to the application database.
	 * <p/>
	 *
	 * <p>
	 * Validates the data in the provided Customer object using a
	 * {@link CustomerValidator} object.
	 * <p/>
	 *
	 * @param customer
	 *            The Customer object to be written to the database using a
	 *            {@link CustomerRepository} object
	 * @return The Customer object that has been successfully written to the
	 *         application database
	 * @throws ConstraintViolationException,
	 *             ValidationException, Exception
	 */
	Customer create(Customer customer) throws ConstraintViolationException, ValidationException, Exception {
		log.info("CustomerService.create() - Creating " + customer.getName());
		
		// Check to make sure the data fits with the parameters in the Customer model
		// and passes validation.
		validator.validateCustomer(customer);

		// Write the customer to the database.
		return crud.create(customer);
	}

}
