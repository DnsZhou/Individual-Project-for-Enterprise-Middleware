
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

	private ResteasyClient client;

	/**
	 * <p>
	 * Create a new client which will be used for our outgoing REST client
	 * communication
	 * </p>
	 */
	public FlightService() {
		// Create client service instance to make REST requests to upstream service
		client = new ResteasyClientBuilder().build();
	}

	/**
	 * <p>
	 * Returns a List of all persisted {@link Flight} objects, sorted alphabetically
	 * by last name.
	 * <p/>
	 *
	 * @return List of Flight objects
	 */
	List<Flight> findAllOrderedByNumber() {
		return crud.findAllOrderedByNumber();
	}

	/**
	 * <p>
	 * Writes the provided Flight object to the application database.
	 * <p/>
	 *
	 * <p>
	 * Validates the data in the provided Flight object using a
	 * {@link FlightValidator} object.
	 * <p/>
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
}
