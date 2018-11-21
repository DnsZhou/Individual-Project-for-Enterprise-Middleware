
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 21:33 15-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.customer;

import java.io.Serializable;
import java.util.Objects;

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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.quickstarts.wfk.contact.Contact;

/**
 * <p>
 * This is a the Domain object. The Customer class represents how flight
 * resources are represented in the application database.
 * </p>
 *
 * <p>
 * The class also specifies how a flights are retrieved from the database
 * (with @NamedQueries), and acceptable values for Customer fields
 * (with @NotNull, @Pattern etc...)
 * </p>
 *
 * @author Tong Zhou
 */
/*
 * The @NamedQueries included here are for searching against the table that
 * reflects this object. This is the most efficient form of query in JPA though
 * is it more error prone due to the syntax being in a String. This makes it
 * harder to debug.
 */
@Entity
@NamedQueries({ @NamedQuery(name = Customer.FIND_ALL, query = "SELECT c FROM Customer c ORDER BY c.customerName ASC"),
		@NamedQuery(name = Customer.FIND_BY_EMAIL, query = "SELECT c FROM Customer c WHERE c.email = :email") })
@XmlRootElement
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Customer.findAll";
	public static final String FIND_BY_EMAIL = "Customer.findByEmail";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@NotNull(message = "Name could not be empty")
	@Size(min = 1, max = 50)
	@Pattern(regexp = "[A-Za-z-' ]+", message = "Please use a customer name without numbers or specials")
	@Column(name = "customer_name")
	private String customerName;

	@NotNull
	@NotEmpty(message = "Email could not be empty")
	@Email(message = "The email address must be in the format of name@domain.com")
	// @Column(name = "email")
	private String email;

	@NotNull(message = "Phone number could not be empty")
	@Pattern(regexp = "0[0-9]{10}")
	@Column(name = "phone_number")
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
	 * @param id:
	 *            id to be set.
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
		return customerName;
	}

	/**
	 * Set the value of name
	 *
	 * @param name:
	 *            name to be set.
	 */
	public void setName(String name) {
		this.customerName = name;
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
	 * @param email:
	 *            email to be set.
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
	 * @param phoneNumber:
	 *            phoneNumber to be set.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Customer))
			return false;
		Customer customer = (Customer) o;
		if (!email.equals(customer.email))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(email);
	}

	
	/**   
	 * toString 
	 *  
	 * @return   
	 * @see java.lang.Object#toString()   
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", customerName=" + customerName + ", email=" + email + ", phoneNumber="
				+ phoneNumber + "]";
	}
	
	
}
