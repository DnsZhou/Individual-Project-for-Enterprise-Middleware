
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 23:48 22-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.travelagent;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.jboss.quickstarts.wfk.util.ErrorMessage;

import uk.ac.ncl.tongzhou.enterprisemiddleware.customer.Customer;

/**
 * TravelAgentService
 * 
 * 
 */
public class TravelAgentService {

	/**
	 * Find the customer in other two system by customer, return the customerId.
	 * create one if not found.
	 * 
	 * @return
	 */
	public static Long getCustomerIdInOuterSystem(BookingSystemType systemType, Customer customer) {

		// Get all customers from specific remote system
		Client client = ClientBuilder.newBuilder().build();
		WebTarget target = client.target(systemType.getBaseUrl() + "customers");
		Response response = target.request().get();

		switch (systemType) {
		case TAXI_SYSTEM: {
			List<HotelCustomerDto> customers = response.readEntity(new GenericType<List<HotelCustomerDto>>() {
			});
			response.close();

			// Find target customer by e-mail address in remote system
			HotelCustomerDto targetCustomer = customers.stream()
					.filter(cus -> cus.getEmail().equals(customer.getEmail())).findFirst().orElse(null);
			if (targetCustomer == null) {
				// Create the customer if the customer is not found in remote System
				HotelCustomerDto tCustomer = new HotelCustomerDto();
				tCustomer.setCustomerName(customer.getCustomerName());
				tCustomer.setEmail(customer.getEmail());
				tCustomer.setPhoneNumber(customer.getPhoneNumber());
				response = target.request().post(Entity.entity(customer, "application/json"));
				targetCustomer = response.readEntity(HotelCustomerDto.class);
				response.close();
			}
			return targetCustomer.getId();
		}
		case HOTEL_SYSTEM: {
			List<HotelCustomerDto> customers = response.readEntity(new GenericType<List<HotelCustomerDto>>() {
			});
			response.close();

			// Find target customer by e-mail address in remote system
			HotelCustomerDto targetCustomer = customers.stream()
					.filter(cus -> cus.getEmail().equals(customer.getEmail())).findFirst().orElse(null);
			if (targetCustomer == null) {
				// Create the customer if the customer is not found in remote System
				HotelCustomerDto hCustomer = new HotelCustomerDto();
				hCustomer.setCustomerName(customer.getCustomerName());
				hCustomer.setEmail(customer.getEmail());
				hCustomer.setPhoneNumber(customer.getPhoneNumber());
				response = target.request().post(Entity.entity(hCustomer, "application/json"));
				if (response.getStatus() == 200) {
					targetCustomer = response.readEntity(HotelCustomerDto.class);
					response.close();
				} else {
					ErrorMessage res = response.readEntity(ErrorMessage.class);
					System.out.println(res);
				}
			}
			return targetCustomer.getId();
		}case FLIGHT_SYSTEM: {
			List<HotelCustomerDto> customers = response.readEntity(new GenericType<List<HotelCustomerDto>>() {
			});
			response.close();

			// Find target customer by e-mail address in remote system
			HotelCustomerDto targetCustomer = customers.stream()
					.filter(cus -> cus.getEmail().equals(customer.getEmail())).findFirst().orElse(null);
			if (targetCustomer == null) {
				// Create the customer if the customer is not found in remote System
				HotelCustomerDto hCustomer = new HotelCustomerDto();
				hCustomer.setCustomerName(customer.getCustomerName());
				hCustomer.setEmail(customer.getEmail());
				hCustomer.setPhoneNumber(customer.getPhoneNumber());
				response = target.request().post(Entity.entity(hCustomer, "application/json"));
				if (response.getStatus() == 200) {
					targetCustomer = response.readEntity(HotelCustomerDto.class);
					response.close();
				} else {
					response.getStatusInfo();
				}
			}
			return targetCustomer.getId();
		}
		}
		return null;
	}
}
