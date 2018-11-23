
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 07:42 23-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.travelagent;

import java.io.Serializable;

/**
 * HotelDto 
 * 
 * 
 */
public class HotelDto  implements Serializable{
	private Long id;
	private String hotelName = "Hilton";
	private String postcode = "NE1 7RU";
	private String phoneNumber = "01912014000";
	
	
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
	 * @param id: id to be set.
	 */
	public void setId(Long id) {
		this.id = id;
	}


	
	
	/** 
	 * Return the hotelName.
	 *
	 * @return hotelName 
	 */
	public String getHotelName() {
		return hotelName;
	}


	
	/** 
	 * Set the value of hotelName
	 *
	 * @param hotelName: hotelName to be set.
	 */
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}


	
	/** 
	 * Return the postcode.
	 *
	 * @return postcode 
	 */
	public String getPostcode() {
		return postcode;
	}


	
	/** 
	 * Set the value of postcode
	 *
	 * @param postcode: postcode to be set.
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}


	
	/** 
	 * Return the phoneNumber.
	 *
	 * @return phoneNumber 
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}


	
	/** 
	 * Set the value of phoneNumber
	 *
	 * @param phoneNumber: phoneNumber to be set.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	/**   
	 * toString 
	 *  
	 * @return   
	 * @see java.lang.Object#toString()   
	 */
	@Override
	public String toString() {
		return "HotelDto [id=" + id + ", hotelName=" + hotelName + ", postcode=" + postcode + ", phoneNumber="
				+ phoneNumber + "]";
	}
	
	
}
