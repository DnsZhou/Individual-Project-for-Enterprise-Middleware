
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

/**
 * <p>
 * This is a Repository class and connects the Service/Control layer (see
 * {@link FlightService} with the Domain/Entity Object (see {@link Flight}).
 * <p/>
 *
 * <p>
 * There are no access modifiers on the methods making them 'package' scope.
 * They should only be accessed by a Service/Control object.
 * <p/>
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
	List<Flight> findAllOrderedByName() {
		TypedQuery<Flight> query = em.createNamedQuery(Flight.FIND_ALL, Flight.class);
		return query.getResultList();
	}
}
