
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 21:56 15-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.guestbooking;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import uk.ac.ncl.tongzhou.enterprisemiddleware.customer.Customer;

/**
 * <p>
 * This is a GuestBooking Bean and RestService class. The Bean is not persisted
 * and should not be a Hibernate Entity. It should simply contain fields (and
 * getters and setters) for a Customer object and a Booking object. Its purpose
 * is simply to allow the Jackson JSON library to deserialize a request Body
 * containing both a Customer and a Booking.
 * </p>
 *
 * @author Tong Zhou
 */

@XmlRootElement
public class GuestBooking implements Serializable {
	private static final long serialVersionUID = 1L;

	private Customer customer;

	private Date bookingDate;

	private Long flightId;

	/**
	 * Return the customer.
	 *
	 * @return customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Set the value of customer
	 *
	 * @param customer:
	 *            customer to be set.
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Return the bookingDate.
	 *
	 * @return bookingDate
	 */
	public Date getBookingDate() {
		return bookingDate;
	}

	/**
	 * Set the value of bookingDate
	 *
	 * @param bookingDate:
	 *            bookingDate to be set.
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * Return the flightId.
	 *
	 * @return flightId
	 */
	public Long getFlightId() {
		return flightId;
	}

	/**
	 * Set the value of flightId
	 *
	 * @param flightId:
	 *            flightId to be set.
	 */
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	/**
	 * Return the booking.
	 *
	 * @return booking
	 */

}
