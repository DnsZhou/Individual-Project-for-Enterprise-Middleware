
package uk.ac.ncl.tongzhou.enterprisemiddleware.booking;

import javax.validation.ValidationException;

import uk.ac.ncl.tongzhou.enterprisemiddleware.customer.Customer;

/**
 * <p>ValidationException caused if a Booking's Flight ID not found in system</p>
 *
 * <p>This violates the uniqueness constraint.</p>
 *
 * @author Tong Zhou
 * @see Customer
 */
public class FlightAndDateExistsException extends ValidationException {

    public FlightAndDateExistsException(String message) {
        super(message);
    }

    public FlightAndDateExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlightAndDateExistsException(Throwable cause) {
        super(cause);
    }
}
