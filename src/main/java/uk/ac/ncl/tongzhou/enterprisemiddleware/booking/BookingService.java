
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 01:13 16-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.booking;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

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
	// @Inject
	// private BookingValidator validator;

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
	 * <p/>
	 *
	 * @return List of Booking objects
	 */
	List<Booking> findAllOrderedByName() {
		return crud.findAllOrderedByName();
	}
}
