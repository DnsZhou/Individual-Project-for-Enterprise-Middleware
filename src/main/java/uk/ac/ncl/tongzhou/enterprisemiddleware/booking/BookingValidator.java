
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 18:55 20-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.booking;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

import uk.ac.ncl.tongzhou.enterprisemiddleware.customer.Customer;
import uk.ac.ncl.tongzhou.enterprisemiddleware.customer.CustomerService;
import uk.ac.ncl.tongzhou.enterprisemiddleware.flight.Flight;
import uk.ac.ncl.tongzhou.enterprisemiddleware.flight.FlightService;

/**
 * <p>
 * This class provides methods to check Booking objects against arbitrary
 * requirements.
 * </p>
 *
 * @author Tong Zhou
 * @see Booking
 * @see BookingRepository
 * @see javax.validation.Validator
 */
public class BookingValidator {
	@Inject
	private Validator validator;

	@Inject
	private @Named("logger") Logger log;

	@Inject
	private BookingRepository crud;

	@Inject
	private FlightService flightService;

	@Inject
	private CustomerService customerService;

	/**
	 * <p>
	 * Validates the given Booking object and throws validation exceptions based on
	 * the type of error. If the error is standard bean validation errors then it
	 * will throw a ConstraintValidationException with the set of the constraints
	 * violated.
	 * </p>
	 *
	 *
	 * @param booking
	 *            The Booking object to be validated
	 * @throws ConstraintViolationException
	 *             If Bean Validation errors exist
	 */
	void validateBooking(BookingDto booking)
			throws CustomerNotFoundException, ConstraintViolationException, ValidationException {
		// // Create a bean validator and check for issues.
		// Set<ConstraintViolation<Booking>> violations = validator.validate(booking);
		// if (!violations.isEmpty()) {
		// throw new ConstraintViolationException(new
		// HashSet<ConstraintViolation<?>>(violations));
		// }
		// Check the customer id and flight id of the booking
		if (customerIdNotExist(booking)) {
			throw new CustomerNotFoundException("Customer Not Found");
		}
		if (flightIdNotExists(booking)) {
			throw new FlightNotFoundException("Flight Not Found");
		}
		// Check whether the Booking flight and date exists
		if (flightIdAndDateExists(booking)) {
			throw new FlightAndDateExistsException("Booking flight and date duplicate with existing record");
		}
	}

	/**
	 * customerIdNotExist
	 * 
	 * <p>
	 * Validating if the given Customer id exists in system.
	 * </p>
	 * 
	 * @param booking
	 * @return
	 */
	boolean customerIdNotExist(BookingDto booking) {
		Customer customer = customerService.findById(booking.getCustomerId());
		return customer == null;
	}

	/**
	 * flightIdNotExist
	 * 
	 * <p>
	 * Validating if the given Flight id exists in system.
	 * </p>
	 * 
	 * @param booking
	 * @return
	 */
	boolean flightIdNotExists(BookingDto booking) {
		Flight flight = flightService.findById(booking.getFlightId());
		return flight == null;
	}

	/**
	 * flightIdAndDateExists
	 * 
	 * <p>
	 * Validating if the given Flight id exists in system.
	 * </p>
	 * 
	 * @param booking
	 * @return
	 */
	boolean flightIdAndDateExists(BookingDto booking) {
		Flight flight = flightService.findById(booking.getFlightId());
		List<Booking> bookings = crud.findAllByBookingDate(booking.getBookingDate());
		bookings.contains(crud.findAllByFlightId(booking.getFlightId()));
		return bookings != null && bookings.size() > 0;
	}
}
