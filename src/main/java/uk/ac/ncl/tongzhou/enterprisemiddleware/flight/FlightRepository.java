
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 01:12 16-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.flight;

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
 * {@link FlightService} with the Domain/Entity Object (see {@link Flight}).
 * </p>
 *
 * <p>
 * There are no access modifiers on the methods making them 'package' scope.
 * They should only be accessed by a Service/Control object.
 * </p>
 *
 * @author Tong Zhou
 * @see Flight
 * @see javax.persistence.EntityManager
 */
public class FlightRepository {
	@Inject
	private @Named("logger") Logger log;

	@Inject
	private EntityManager em;

	/**
	 * <p>
	 * Returns a List of all persisted {@link Flight} objects, sorted alphabetically
	 * by last name.
	 * </p>
	 *
	 * @return List of Flight objects
	 */
	List<Flight> findAllOrderedByNumber() {
		TypedQuery<Flight> query = em.createNamedQuery(Flight.FIND_ALL, Flight.class);
		return query.getResultList();
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
	Flight findById(Long id) {
		return em.find(Flight.class, id);
	}

	/**
	 * <p>
	 * Persists the provided Flight object to the application database using the
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
	 * @param flight
	 *            The Flight object to be persisted
	 * @return The Flight object that has been persisted
	 * @throws ConstraintViolationException,
	 *             ValidationException, Exception
	 */
	Flight create(Flight flight) throws ConstraintViolationException, ValidationException, Exception {
		log.info("FlightRepository.create() - Creating " + flight.getNumber());

		// Write the flight to the database.
		em.persist(flight);

		return flight;
	}

}
