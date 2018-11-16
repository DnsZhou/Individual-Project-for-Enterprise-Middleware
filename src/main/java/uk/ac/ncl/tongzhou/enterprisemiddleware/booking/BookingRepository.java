
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 01:12 16-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.booking;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * <p>
 * This is a Repository class and connects the Service/Control layer (see
 * {@link BookingService} with the Domain/Entity Object (see {@link Booking}).
 * <p/>
 *
 * <p>
 * There are no access modifiers on the methods making them 'package' scope.
 * They should only be accessed by a Service/Control object.
 * <p/>
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

	/**
	 * <p>
	 * Returns a List of all persisted {@link Booking} objects, sorted
	 * alphabetically by last name.
	 * </p>
	 *
	 * @return List of Booking objects
	 */
	List<Booking> findAllOrderedByName() {
		TypedQuery<Booking> query = em.createNamedQuery(Booking.FIND_ALL, Booking.class);
		return query.getResultList();
	}
}
