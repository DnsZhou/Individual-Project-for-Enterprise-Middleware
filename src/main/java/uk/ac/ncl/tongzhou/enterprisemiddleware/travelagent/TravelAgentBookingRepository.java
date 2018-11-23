
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 05:39 23-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.travelagent;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * TravelAgentBookingRepository
 * 
 * 
 */
public class TravelAgentBookingRepository {
	@Inject
	private @Named("logger") Logger log;

	@Inject
	private EntityManager em;

	/**
	 * <p>
	 * Returns a List of all persisted {@link Booking} objects, sorted
	 * alphabetically by last name.
	 * </p>
	 *
	 * @return List of Booking objects
	 */
	List<TravelAgentBooking> findAllOrderedById() {
		TypedQuery<TravelAgentBooking> query = em.createNamedQuery(TravelAgentBooking.FIND_ALL,
				TravelAgentBooking.class);
		return query.getResultList();
	}

	/**
	 * <p>
	 * Returns a single Booking object, specified by a Long id.
	 * </p>
	 *
	 * @param id
	 *            The id field of the Booking to be returned
	 * @return The Booking with the specified id
	 */
	TravelAgentBooking findById(Long id) {
		return em.find(TravelAgentBooking.class, id);
	}

	/**
	 * <p>
	 * Persists the provided Booking object to the application database using the
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
	 * @param booking
	 *            The Booking object to be persisted
	 * @return The Booking object that has been persisted
	 * @throws ConstraintViolationException,
	 *             ValidationException, Exception
	 */
	TravelAgentBooking create(TravelAgentBooking booking)
			throws ConstraintViolationException, ValidationException, Exception {
		log.info("TravelAgentBookingRepository.create() - Creating " + booking.getId());

		// Write the booking to the database.
		em.persist(booking);

		return booking;
	}

	/**
	 * <p>
	 * Deletes the provided Booking object from the application database if found
	 * there
	 * </p>
	 *
	 * @param booking
	 *            The Booking object to be removed from the application database
	 * @return The Booking object that has been successfully removed from the
	 *         application database; or null
	 * @throws Exception
	 */
	TravelAgentBooking delete(TravelAgentBooking booking) throws Exception {
		log.info("BookingRepository.delete() - Deleting " + booking.getId());

		if (booking.getId() != null) {
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
			em.remove(em.merge(booking));

		} else {
			log.info("BookingRepository.delete() - No ID was found so can't Delete.");
		}

		return booking;
	}
}
