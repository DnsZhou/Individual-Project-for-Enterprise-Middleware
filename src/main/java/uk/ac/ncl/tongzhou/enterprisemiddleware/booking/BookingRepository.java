
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 01:12 16-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.booking;

import java.util.Date;
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
import uk.ac.ncl.tongzhou.enterprisemiddleware.flight.FlightService;

/**
 * <p>
 * This is a Repository class and connects the Service/Control layer (see
 * {@link BookingService} with the Domain/Entity Object (see {@link Booking}).
 * </p>
 *
 * <p>
 * There are no access modifiers on the methods making them 'package' scope.
 * They should only be accessed by a Service/Control object.
 * </p>
 *
 * @author Tong Zhou
 * @see Booking
 * @see javax.persistence.EntityManager
 */
public class BookingRepository {

	@Inject
	private @Named("logger") Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private FlightService flightService;

	/**
	 * <p>
	 * Returns a List of all persisted {@link Booking} objects, sorted
	 * alphabetically by last name.
	 * </p>
	 *
	 * @return List of Booking objects
	 */
	List<Booking> findAllOrderedById() {
		TypedQuery<Booking> query = em.createNamedQuery(Booking.FIND_ALL, Booking.class);
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
	Booking findById(Long id) {
		return em.find(Booking.class, id);
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
	Booking create(Booking booking) throws ConstraintViolationException, ValidationException, Exception {
		log.info("BookingRepository.create() - Creating " + booking.getId());

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
	Booking delete(Booking booking) throws Exception {
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

	/**
	 * findByFlightIdAndDate
	 * 
	 * @param flightId
	 * @return the result with the flightId provided
	 */
	public List<Booking> findAllByFlightId(Long flightId) {
		Flight flight = flightService.findById(flightId);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Booking> criteria = cb.createQuery(Booking.class);
		Root<Booking> booking = criteria.from(Booking.class);
		criteria.select(booking).where(cb.equal(booking.get("flight"), flight));
		return em.createQuery(criteria).getResultList();
	}

	/**
	 * findAllByBookingDate
	 * 
	 * @param bookingDate
	 * @return the result with the bookingDate provided
	 */
	public List<Booking> findAllByBookingDate(Date bookingDate) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Booking> criteria = cb.createQuery(Booking.class);
		Root<Booking> booking = criteria.from(Booking.class);
		criteria.select(booking).where(cb.equal(booking.get("bookingDate"), bookingDate));
		return em.createQuery(criteria).getResultList();
	}
}
