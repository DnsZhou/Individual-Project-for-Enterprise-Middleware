
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 04:19 23-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.travelagent;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQueries({
		@NamedQuery(name = TravelAgentBooking.FIND_ALL, query = "SELECT c FROM TravelAgentBooking c ORDER BY c.id ASC") })
@XmlRootElement
@Table(name = "travelAgentBooking", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class TravelAgentBooking implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String FIND_ALL = "TravelAgentBooking.findAll";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@JoinColumn(name = "flight_id", nullable = false)
	private Long flightBookingId;

	@JoinColumn(name = "hotel_booking_id", nullable = false)
	private Long hotelBookingId;

	@JoinColumn(name = "taxi_booking_id", nullable = false)
	private Long taxiBookingId;

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
	 * Return the flightBookingId.
	 *
	 * @return flightBookingId
	 */
	public Long getFlightBookingId() {
		return flightBookingId;
	}

	/**
	 * Set the value of flightBookingId
	 *
	 * @param flightBookingId:
	 *            flightBookingId to be set.
	 */
	public void setFlightBookingId(Long flightBookingId) {
		this.flightBookingId = flightBookingId;
	}

	/**
	 * Return the hotelBookingId.
	 *
	 * @return hotelBookingId
	 */
	public Long getHotelBookingId() {
		return hotelBookingId;
	}

	/**
	 * Set the value of hotelBookingId
	 *
	 * @param hotelBookingId:
	 *            hotelBookingId to be set.
	 */
	public void setHotelBookingId(Long hotelBookingId) {
		this.hotelBookingId = hotelBookingId;
	}

	/**
	 * Return the taxiBookingId.
	 *
	 * @return taxiBookingId
	 */
	public Long getTaxiBookingId() {
		return taxiBookingId;
	}

	/**
	 * Set the value of taxiBookingId
	 *
	 * @param taxiBookingId:
	 *            taxiBookingId to be set.
	 */
	public void setTaxiBookingId(Long taxiBookingId) {
		this.taxiBookingId = taxiBookingId;
	}

	/**
	 * toString
	 * 
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TravelAgentBooking [id=" + id + ", flightBookingId=" + flightBookingId + ", hotelBookingId="
				+ hotelBookingId + ", taxiBookingId=" + taxiBookingId + "]";
	}

}
