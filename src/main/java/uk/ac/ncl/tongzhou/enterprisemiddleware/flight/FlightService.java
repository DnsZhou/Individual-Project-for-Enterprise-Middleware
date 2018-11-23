
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 01:13 16-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.flight;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import uk.ac.ncl.tongzhou.enterprisemiddleware.flight.Flight;

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
 * @see FlightValidator
 * @see FlightRepository
 */
public class FlightService {
	@Inject
	private @Named("logger") Logger log;
	@Inject
	private FlightValidator validator;

	@Inject
	private FlightRepository crud;

	/**
	 * <p>
	 * Returns a List of all persisted {@link Flight} objects, sorted alphabetically
	 * by last name.
	 * </p>
	 *
	 * @return List of Flight objects
	 */
	List<Flight> findAllOrderedByNumber() {
		return crud.findAllOrderedByNumber();
	}

	/**
	 * <p>
	 * Writes the provided Flight object to the application database.
	 * </p>
	 *
	 * <p>
	 * Validates the data in the provided Flight object using a
	 * {@link FlightValidator} object.
	 * </p>
	 *
	 * @param flight
	 *            The Flight object to be written to the database using a
	 *            {@link FlightRepository} object
	 * @return The Flight object that has been successfully written to the
	 *         application database
	 * @throws ConstraintViolationException,
	 *             ValidationException, Exception
	 */
	Flight create(Flight flight) throws ConstraintViolationException, ValidationException, Exception {
		log.info("FlightService.create() - Creating Flight-" + flight.getNumber());

		// Check to make sure the data fits with the parameters in the Flight model and
		// passes validation.
		validator.validateFlight(flight);

		// Write the flight to the database.
		return crud.create(flight);
	}

	/**
	 * <p>
	 * Returns a single Flight object, specified by a Long id.
	 * </p>
	 *
	 * @param id
	 *            The id field of the Flight to be returned
	 * @return The Flight with the specified id
	 */
	public Flight findById(Long id) {
		return crud.findById(id);
	}

	/**
	 * <p>
	 * Returns a single Flight object, specified by a Long id.
	 * </p>
	 *
	 * @param flightNumber
	 *            The number of the Flight to be returned
	 * @return The Flight with the specified Number
	 */
	public Flight findByNumber(String flightNumber) {
		return crud.findByNumber(flightNumber);
	}

	/**
	 * <p>
	 * Deletes the provided Flight object from the application database if found
	 * there.
	 * </p>
	 *
	 * @param flight
	 *            The Flight object to be removed from the application database
	 * @return The Flight object that has been successfully removed from the
	 *         application database; or null
	 * @throws Exception
	 */
	Flight delete(Flight flight) throws Exception {
		log.info("delete() - Deleting " + flight.toString());

		Flight deletedFlight = null;

		if (flight.getId() != null) {
			deletedFlight = crud.delete(flight);
		} else {
			log.info("delete() - No ID was found so can't Delete.");
		}

		return deletedFlight;
	}
}
