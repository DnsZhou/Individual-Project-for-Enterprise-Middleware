
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 01:26 19-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.customer;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

/**
 * <p>
 * This class provides methods to check Customer objects against arbitrary
 * requirements.
 * </p>
 *
 * @author Tong Zhou
 * @see Customer
 * @see CustomerRepository
 * @see javax.validation.Validator
 */
public class CustomerValidator {
	@Inject
	private Validator validator;

	@Inject
	private @Named("logger") Logger log;

	@Inject
	private CustomerRepository crud;
	
	/**
     * <p>Validates the given Customer object and throws validation exceptions based on the type of error. If the error is standard
     * bean validation errors then it will throw a ConstraintValidationException with the set of the constraints violated.</p>
     *
     *
     * <p>If the error is caused because an existing customer with the same email is registered it throws a regular validation
     * exception so that it can be interpreted separately.</p>
     *
     *
     * @param customer The Customer object to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     * @throws ValidationException If customer with the same email already exists
     */
	void validateCustomer(Customer customer) throws ConstraintViolationException, ValidationException {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
		// Check the uniqueness of the email address
		if (emailAlreadyExists(customer.getEmail(), customer.getId())) {
			throw new UniqueEmailException("Unique Email Violation");
		}
	}

	/**
	 * <p>
	 * Checks if a customer with the same email address is already registered. This
	 * is the only way to easily capture the "@UniqueConstraint(columnNames =
	 * "email")" constraint from the Customer class.
	 * </p>
	 *
	 * <p>
	 * Since Update will being using an email that is already in the database we
	 * need to make sure that it is the email from the record being updated.
	 * </p>
	 *
	 * @param email
	 *            The email to check is unique
	 * @param id
	 *            The user id to check the email against if it was found
	 * @return boolean which represents whether the email was found, and if so if it
	 *         belongs to the user with id
	 */
	boolean emailAlreadyExists(String email, Long id) {
		Customer customer = null;
		Customer customerWithID = null;
		try {
			customer = crud.findByEmail(email);
		} catch (NoResultException e) {
			log.log(Level.SEVERE, "NoResultException : - " + e.getMessage());
		}
		if (customer != null && id != null) {
			try {
				customerWithID = crud.findById(id);
				if (customerWithID != null && customerWithID.getEmail().equals(email)) {
					customer = null;
				}
			} catch (NoResultException e) {
				log.log(Level.SEVERE, "NoResultException : - " + e.getMessage());
			}
		}
		return customer != null;
	}
}
