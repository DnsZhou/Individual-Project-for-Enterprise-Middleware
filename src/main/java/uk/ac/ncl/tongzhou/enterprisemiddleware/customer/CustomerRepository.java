
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 00:48 16-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.customer;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * <p>
 * This is a Repository class and connects the Service/Control layer (see
 * {@link CustomerService} with the Domain/Entity Object (see {@link Customer}).
 * <p/>
 *
 * <p>
 * There are no access modifiers on the methods making them 'package' scope.
 * They should only be accessed by a Service/Control object.
 * <p/>
 *
 * @author Tong Zhou
 * @see Customer
 * @see javax.persistence.EntityManager
 */
public class CustomerRepository {

	@Inject
	private @Named("logger") Logger log;

	@Inject
	private EntityManager em;

	/**
	 * <p>
	 * Returns a List of all persisted {@link Customer} objects, sorted
	 * alphabetically by last name.
	 * </p>
	 *
	 * @return List of Customer objects
	 */
	List<Customer> findAllOrderedByName() {
		TypedQuery<Customer> query = em.createNamedQuery(Customer.FIND_ALL, Customer.class);
		return query.getResultList();
	}

	/**
	 * <p>
	 * Persists the provided Customer object to the application database using the
	 * EntityManager.
	 * </p>
	 *
	 * <p>
	 * {@link javax.persistence.EntityManager#persist(Object) persist(Object)} takes
	 * an entity instance, adds it to the context and makes that instance managed
	 * (ie future updates to the entity will be tracked)
	 * </p>
	 *
	 * <p>
	 * persist(Object) will set the @GeneratedValue @Id for an object.
	 * </p>
	 *
	 * @param customer
	 *            The Customer object to be persisted
	 * @return The Customer object that has been persisted
	 * @throws ConstraintViolationException,
	 *             ValidationException, Exception
	 */
	Customer create(Customer customer) throws ConstraintViolationException, ValidationException, Exception {
		log.info("CustomerRepository.create() - Creating " + customer.getName());

		// Write the customer to the database.
		em.persist(customer);

		return customer;
	}

	/**
	 * <p>
	 * Returns a single Customer object, specified by a String email.
	 * </p>
	 *
	 * <p>
	 * If there is more than one Customer with the specified email, only the first
	 * encountered will be returned.
	 * <p/>
	 *
	 * @param email
	 *            The email field of the Customer to be returned
	 * @return The first Customer with the specified email
	 */
	Customer findByEmail(String email) {
		TypedQuery<Customer> query = em.createNamedQuery(Customer.FIND_BY_EMAIL, Customer.class).setParameter("email",
				email);
		return query.getSingleResult();
	}

	/**
	 * <p>
	 * Returns a single Customer object, specified by a Long id.
	 * <p/>
	 *
	 * @param id
	 *            The id field of the Customer to be returned
	 * @return The Customer with the specified id
	 */
	Customer findById(Long id) {
		return em.find(Customer.class, id);
	}
}
