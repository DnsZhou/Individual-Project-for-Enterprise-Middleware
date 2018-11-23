
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 02:24 23-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.travelagent;

import java.io.Serializable;

/**
 * TaxiCustomerDto
 * 
 * 
 */
public class FlightCustomerDto implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String email;
	private String phoneNumber;
	
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
	 * Return the name.
	 *
	 * @return name 
	 */
	public String getName() {
		return name;
	}

	
	/** 
	 * Set the value of name
	 *
	 * @param name: name to be set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** 
	 * Return the email.
	 *
	 * @return email 
	 */
	public String getEmail() {
		return email;
	}
	
	/** 
	 * Set the value of email
	 *
	 * @param email: email to be set.
	 */
	public void setEmail(String email) {
		this.email = email;
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
		return "TaxiCustomerDto [id=" + id + ", cname=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ "]";
	}
	
	
}
