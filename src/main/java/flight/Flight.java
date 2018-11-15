/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package flight;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * This is a the Domain object. The Flight class represents how flight resources
 * are represented in the application database.
 * </p>
 *
 * <p>
 * The class also specifies how a flights are retrieved from the database
 * (with @NamedQueries), and acceptable values for Flight fields
 * (with @NotNull, @Pattern etc...)
 * <p/>
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
@NamedQueries({
		@NamedQuery(name = Flight.FIND_ALL, query = "SELECT c FROM Flight c ORDER BY c.flightNumber ASC")})
//		@NamedQuery(name = Flight.FIND_BY_EMAIL, query = "SELECT c FROM Flight c WHERE c.email = :email") })
@XmlRootElement
@Table(name = "flight", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Flight implements Serializable {
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Flight.findAll";
	public static final String FIND_BY_EMAIL = "Flight.findByEmail";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	// Flight number: a non-empty alpha-numerical string which is 5 characters in
	// length.
	@NotNull
	@Size(min = 5, max = 5)
	@Pattern(regexp = "[A-Za-z0-9]+", message = "Please use a non-empty alpha-numerical string which is 5 characters in length")
	@Column(name = "flight_number")
	private String flightNumber;

	// Flight point of departure: a non-empty alphabetical string, which is upper
	// case and 3 characters in length.
	@NotNull
	@Size(min = 3, max = 3)
	@Pattern(regexp = "[A-Z]+", message = "Please use a non-empty alphabetical string, which is upper case and 3 characters in length")
	@Column(name = "flight_point_of_departure")
	private String flightPointOfDeparture;

	// Flight destination: a non-empty alphabetical string, which is upper case, 3
	// characters in length and different from its point of departure.
	@NotNull
	@Size(min = 3, max = 3)
	@Pattern(regexp = "[A-Z]+", message = "Please use a non-empty alphabetical string, which is upper case, 3 characters in length and different from its point of departure")
	@Column(name = "flight_destination")
	private String flightDestination;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	/** 
	 * Return the flightNumber.
	 *
	 * @return flightNumber 
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	
	/** 
	 * Set the value of flightNumber
	 *
	 * @param flightNumber: flightNumber to be set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	
	/** 
	 * Return the flightPointOfDeparture.
	 *
	 * @return flightPointOfDeparture 
	 */
	public String getFlightPointOfDeparture() {
		return flightPointOfDeparture;
	}

	
	/** 
	 * Set the value of flightPointOfDeparture
	 *
	 * @param flightPointOfDeparture: flightPointOfDeparture to be set.
	 */
	public void setFlightPointOfDeparture(String flightPointOfDeparture) {
		this.flightPointOfDeparture = flightPointOfDeparture;
	}

	
	/** 
	 * Return the flightDestination.
	 *
	 * @return flightDestination 
	 */
	public String getFlightDestination() {
		return flightDestination;
	}

	
	/** 
	 * Set the value of flightDestination
	 *
	 * @param flightDestination: flightDestination to be set.
	 */
	public void setFlightDestination(String flightDestination) {
		this.flightDestination = flightDestination;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Flight))
			return false;
		Flight flight = (Flight) o;
		if (!id.equals(flight.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
