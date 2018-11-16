
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 21:56 15-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.booking;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.Booking;

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
 * <p/>
 *
 * @author Tong Zhou
 */
@Entity
@NamedQueries({
	@NamedQuery(name = Booking.FIND_ALL, query = "SELECT c FROM Booking c ORDER BY c.id ASC")
})
@XmlRootElement
@Table(name = "booking", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ALL = "Booking.findAll";
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@NotNull
	@NotEmpty
	@Column(name = "customer_id")
	private String customerId;

	@NotNull
	@NotEmpty
	@Column(name = "flight_id")
	private String flightId;

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
	 * Return the bookingId.
	 *
	 * @return bookingId
	 */
	public String getBookingId() {
		return customerId;
	}

	/**
	 * Set the value of bookingId
	 *
	 * @param bookingId:
	 *            bookingId to be set.
	 */
	public void setBookingId(String bookingId) {
		this.customerId = bookingId;
	}

	/**
	 * Return the flightId.
	 *
	 * @return flightId
	 */
	public String getFlightId() {
		return flightId;
	}

	/**
	 * Set the value of flightId
	 *
	 * @param flightId:
	 *            flightId to be set.
	 */
	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

}
