
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 02:25 23-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.travelagent;

import java.io.Serializable;
import java.util.Date;

/**
 * HotelCustomerDto
 * 
 * 
 */
public class HotelBookingDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Date bookingDate;
	private Long customerId;
	private Long hotelId;

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

}
