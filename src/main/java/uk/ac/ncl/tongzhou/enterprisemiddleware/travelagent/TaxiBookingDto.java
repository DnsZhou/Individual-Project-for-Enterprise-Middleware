
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 02:24 23-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.travelagent;

import java.io.Serializable;
import java.util.Date;

/**
 * TaxiCustomerDto
 * 
 * 
 */
public class TaxiBookingDto implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Date date;
	private Long customerId;
	private Long taxiId;

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
	 * Return the date.
	 *
	 * @return date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set the value of date
	 *
	 * @param date:
	 *            date to be set.
	 */
	public void setDate(Date date) {
		this.date = date;
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

	
	/**   
	 * toString 
	 *  
	 * @return   
	 * @see java.lang.Object#toString()   
	 */
	@Override
	public String toString() {
		return "TaxiBookingDto [id=" + id + ", date=" + date + ", customerId=" + customerId + ", taxiId=" + taxiId
				+ "]";
	}

}
