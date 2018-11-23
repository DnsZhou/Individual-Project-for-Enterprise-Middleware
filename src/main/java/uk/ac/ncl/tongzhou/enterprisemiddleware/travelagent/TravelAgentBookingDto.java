
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 16:31 22-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.travelagent;

import java.util.Date;

/**
 * BookingDTO
 * 
 * 
 */
public class TravelAgentBookingDto {
	private Long customerId;
	private Long flightId;
	private Long hotelId;
	private Long taxiId;
	private Date bookingDate;

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

	/**
	 * Return the hotelId.
	 *
	 * @return hotelId
	 */
	public Long getHotelId() {
		return hotelId;
	}

	/**
	 * Set the value of hotelId
	 *
	 * @param hotelId:
	 *            hotelId to be set.
	 */
	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	/**
	 * Return the taxiId.
	 *
	 * @return taxiId
	 */
	public Long getTaxiId() {
		return taxiId;
	}

	/**
	 * Set the value of taxiId
	 *
	 * @param taxiId:
	 *            taxiId to be set.
	 */
	public void setTaxiId(Long taxiId) {
		this.taxiId = taxiId;
	}

}
