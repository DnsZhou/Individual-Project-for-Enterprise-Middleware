
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 21:56 15-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.booking;

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
 * This is a the Domain object. The Booking class represents how flight
 * resources are represented in the application database.
 * </p>
 *
 * <p>
 * The class also specifies how a flights are retrieved from the database
 * (with @NamedQueries), and acceptable values for Booking fields
 * (with @NotNull, @Pattern etc...)
 * </p>
 *
 * @author Tong Zhou
 */

/**
 * Booking 
 * 
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = Booking.FIND_ALL, query = "SELECT c FROM Booking c ORDER BY c.id ASC") })
@XmlRootElement
@Table(name = "booking", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Booking.findAll";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@NotNull(message = "Customer Id could not be empty")
	@Column(name = "customer_id")
	private Long customerId;

	@NotNull(message = "Flight Id could not be empty")
	@Column(name = "flight_id")
	private Long flightId;

	@NotNull(message = "Booking date could not be empty")
	@Future(message = "Booking date should be in the future")
	@Column(name = "booking_date")
	@Temporal(TemporalType.DATE)
	private Date bookingDate;

	/**
	 * Return the id.
	 *
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set the value of id
	 *
	 * @param id:
	 *            id to be set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Return the customerId.
	 *
	 * @return customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * Set the value of customerId
	 *
	 * @param customerId:
	 *            customerId to be set.
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Booking))
			return false;
		Booking booking = (Booking) o;
		if (!id.equals(booking.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	
	/**   
	 * toString 
	 *  
	 * @return   
	 * @see java.lang.Object#toString()   
	 */
	@Override
	public String toString() {
		return "Booking [id=" + id + ", customerId=" + customerId + ", flightId=" + flightId + ", bookingDate="
				+ bookingDate + "]";
	}


}
