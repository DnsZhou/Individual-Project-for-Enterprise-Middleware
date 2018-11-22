
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 21:56 15-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.guestbooking;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>
 * This is a GuestBooking Bean and RestService class. The Bean is not persisted and should not be a Hibernate Entity. 
 * It should simply contain fields (and getters and setters) for a Customer object and a Booking object. Its purpose is 
 * simply to allow the Jackson JSON library to deserialize a request Body containing both a Customer and a Booking.
 * </p>
 *
 * @author Tong Zhou
 */

/**
 * Booking
 * 
 * 
 */
@XmlRootElement
@Table(name = "booking", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class GuestBooking implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long flightId;

	private Date bookingDate;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof GuestBooking))
			return false;
		GuestBooking guestBooking = (GuestBooking) o;
		if (!flightId.equals(guestBooking.flightId) && bookingDate.equals(guestBooking.bookingDate))
			return false;
		return true;
	}

	@Override
	public int hashCode() {

		return Objects.hash(bookingDate, flightId);
	}

//	/**
//	 * toString
//	 * 
//	 * @return
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		return "Booking [customerId=" + customerId + ", flightId=" + flightId + ", bookingDate=" + bookingDate + "]";
//	}

}
