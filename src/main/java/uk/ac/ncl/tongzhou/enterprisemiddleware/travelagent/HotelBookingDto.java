
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
	private HotelCustomerDto customer;
	private HotelDto hotel;

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
	 * Return the customer.
	 *
	 * @return customer 
	 */
	public HotelCustomerDto getCustomer() {
		return customer;
	}

	
	/** 
	 * Set the value of customer
	 *
	 * @param customer: customer to be set.
	 */
	public void setCustomer(HotelCustomerDto customer) {
		this.customer = customer;
	}

	
	/** 
	 * Return the hotel.
	 *
	 * @return hotel 
	 */
	public HotelDto getHotel() {
		return hotel;
	}

	
	/** 
	 * Set the value of hotel
	 *
	 * @param hotel: hotel to be set.
	 */
	public void setHotel(HotelDto hotel) {
		this.hotel = hotel;
	}

	
	/**   
	 * toString 
	 *  
	 * @return   
	 * @see java.lang.Object#toString()   
	 */
	@Override
	public String toString() {
		return "HotelBookingDto [id=" + id + ", bookingDate=" + bookingDate + ", customer=" + customer + ", hotel="
				+ hotel + "]";
	}


}
