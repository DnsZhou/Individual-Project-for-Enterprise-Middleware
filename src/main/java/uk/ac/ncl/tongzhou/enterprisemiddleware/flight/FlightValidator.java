
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 04:39 19-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.flight;

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

import uk.ac.ncl.tongzhou.enterprisemiddleware.flight.Flight;
import uk.ac.ncl.tongzhou.enterprisemiddleware.flight.FlightRepository;

/**
 * <p>
 * This class provides methods to check Flight objects against arbitrary
 * requirements.
 * </p>
 *
 * @author Tong Zhou
 * @see Flight
 * @see FlightRepository
 * @see javax.validation.Validator
 */
public class FlightValidator {
	@Inject
	private Validator validator;

	@Inject
	private @Named("logger") Logger log;

	@Inject
	private FlightRepository crud;

	/**
     * <p>Validates the given Flight object and throws validation exceptions based on the type of error. If the error is standard
     * bean validation errors then it will throw a ConstraintValidationException with the set of the constraints violated.</p>
     *
     *
     * <p>If the error is caused because an existing flight with the same email is registered it throws a regular validation
     * exception so that it can be interpreted separately.</p>
     *
     *
     * @param flight The Flight object to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     */
	void validateFlight(Flight flight) throws ConstraintViolationException, ValidationException {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Flight>> violations = validator.validate(flight);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
		// Check the uniqueness of the email address
		if (destinationDuplicateWithdeparture(flight)) {
			throw new DestinationDuplicateWithDepartureException("Destination Duplicate With Departure Point");
		}
	}

	/**
	 * <p>
	 * Checks if a flight destination is different from its point of departure.
	 * </p>
	 *
	 * 
	 * @param flight
	 *            validate whether destination Duplicate With departure
	 * 
	 * @return boolean which represents whether the email was found, and if so if it
	 *         belongs to the user with id
	 */
	boolean destinationDuplicateWithdeparture(Flight flight) {
		return flight.getDestination().equals(flight.getPointOfDeparture());
	}
}
