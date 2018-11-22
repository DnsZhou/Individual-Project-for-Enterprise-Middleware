
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import uk.ac.ncl.tongzhou.enterprisemiddleware.flight.Flight;

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

	/**
	 * findByNumber
	 * 
	 * @param flightNumber
	 * @return Flight with the flight number
	 */
	public Flight findByNumber(String number) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Flight> criteria = cb.createQuery(Flight.class);
		Root<Flight> flight = criteria.from(Flight.class);
		criteria.select(flight).where(cb.equal(flight.get("number"), number));
		List<Flight> res = em.createQuery(criteria).getResultList();
		return res.size() > 0 ? res.get(0) : null;
	}
	
	/**
	 * <p>
	 * Deletes the provided Flight object from the application database if found
	 * there
	 * </p>
	 *
	 * @param flight
	 *            The Flight object to be removed from the application database
	 * @return The Flight object that has been successfully removed from the
	 *         application database; or null
	 * @throws Exception
	 */
	Flight delete(Flight flight) throws Exception {
		log.info("FlightRepository.delete() - Deleting " + flight.getId());

		if (flight.getId() != null) {
			/*
			 * The Hibernate session (aka EntityManager's persistent context) is closed and
			 * invalidated after the commit(), because it is bound to a transaction. The
			 * object goes into a detached status. If you open a new persistent context, the
			 * object isn't known as in a persistent state in this new context, so you have
			 * to merge it.
			 * 
			 * Merge sees that the object has a primary key (id), so it knows it is not new
			 * and must hit the database to reattach it.
			 * 
			 * Note, there is NO remove method which would just take a primary key (id) and
			 * a entity class as argument. You first need an object in a persistent state to
			 * be able to delete it.
			 * 
			 * Therefore we merge first and then we can remove it.
			 */
			em.remove(em.merge(flight));

		} else {
			log.info("FlightRepository.delete() - No ID was found so can't Delete.");
		}

		return flight;
	}
}
