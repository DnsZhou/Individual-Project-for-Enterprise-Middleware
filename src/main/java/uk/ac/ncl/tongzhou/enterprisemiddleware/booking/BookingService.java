
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 01:13 16-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.booking;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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
 * @see BookingValidator
 * @see BookingRepository
 */
public class BookingService {
	@Inject
	private @Named("logger") Logger log;
	@Inject
	private BookingValidator validator;

	@Inject
	private BookingRepository crud;

	private ResteasyClient client;

	/**
	 * <p>
	 * Create a new client which will be used for our outgoing REST client
	 * communication
	 * </p>
	 */
	public BookingService() {
		// Create client service instance to make REST requests to upstream service
		client = new ResteasyClientBuilder().build();
	}

	/**
	 * <p>
	 * Returns a List of all persisted {@link Booking} objects, sorted
	 * alphabetically by last name.
	 * </p>
	 *
	 * @return List of Booking objects
	 */
	List<Booking> findAllOrderedByName() {
		return crud.findAllOrderedById();
	}
	
	/**
	 * <p>
	 * Writes the provided Booking object to the application database.
	 * </p>
	 *
	 * <p>
	 * Validates the data in the provided Booking object using a
	 * {@link BookingValidator} object.
	 * </p>
	 *
	 * @param booking
	 *            The Booking object to be written to the database using a
	 *            {@link BookingRepository} object
	 * @return The Booking object that has been successfully written to the
	 *         application database
	 * @throws ConstraintViolationException,
	 *             ValidationException, Exception
	 */
	Booking create(Booking booking) throws ConstraintViolationException, ValidationException, Exception {
		log.info("BookingService.create() - Creating Booking-" + booking.getId());

		// Check to make sure the data fits with the parameters in the Booking model and
		// passes validation.
		validator.validateBooking(booking);

		// Write the booking to the database.
		return crud.create(booking);
	}

    /**
     * <p>Returns a single Booking object, specified by a Long id.</p>
     *
     * @param id The id field of the Booking to be returned
     * @return The Booking with the specified id
     */
    Booking findById(Long id) {
        return crud.findById(id);
    }
    
    /**
     * <p>Deletes the provided Booking object from the application database if found there.</p>
     *
     * @param booking The Booking object to be removed from the application database
     * @return The Booking object that has been successfully removed from the application database; or null
     * @throws Exception
     */
    Booking delete(Booking booking) throws Exception {
        log.info("delete() - Deleting " + booking.toString());

        Booking deletedBooking = null;

        if (booking.getId() != null) {
            deletedBooking = crud.delete(booking);
        } else {
            log.info("delete() - No ID was found so can't Delete.");
        }

        return deletedBooking;
    }

	
	
	/**   
	 *  findAllByFlightId   
	 * 
	 * @param flightId
	 * @return the result with the flightId provided         
	 */
    List<Booking> findAllByFlightId(Long flightId) {
		return crud.findAllByFlightId(flightId);
	}
	
	
	/**   
	 *  findAllByBookingDate   
	 * 
	 * @param bookingDate
	 * @return the result with the bookingDate provided       
	 */
	List<Booking> findAllByBookingDate(Date bookingDate) {
		return crud.findAllByBookingDate(bookingDate);
	}
}
