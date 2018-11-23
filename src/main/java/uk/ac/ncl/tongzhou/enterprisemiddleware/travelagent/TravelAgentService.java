
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 23:48 22-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.travelagent;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.jboss.quickstarts.wfk.util.ErrorMessage;

import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.Booking;
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.BookingDto;
import uk.ac.ncl.tongzhou.enterprisemiddleware.booking.BookingRepository;
import uk.ac.ncl.tongzhou.enterprisemiddleware.customer.Customer;

/**
 * TravelAgentService
 * 
 * 
 */
public class TravelAgentService {
	@Inject
	private @Named("logger") Logger log;

	@Inject
	private TravelAgentBookingRepository crud;

	/**
	 * Find the customer in other two system by customer, return the customerId.
	 * create one if not found.
	 * 
	 * @return
	 */
	Long getCustomerIdInOuterSystem(BookingSystemType systemType, Customer customer) {

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
				tCustomer.setName(customer.getCustomerName());
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
				hCustomer.setName(customer.getCustomerName());
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
		}
		case FLIGHT_SYSTEM: {
			List<Customer> customers = response.readEntity(new GenericType<List<Customer>>() {
			});
			response.close();

			// Find target customer by e-mail address in remote system
			Customer targetCustomer = customers.stream()
					.filter(cus -> cus.getEmail().equals(customer.getEmail())).findFirst().orElse(null);
			if (targetCustomer == null) {
				// Create the customer if the customer is not found in remote System
				HotelCustomerDto hCustomer = new HotelCustomerDto();
				hCustomer.setName(customer.getCustomerName());
				hCustomer.setEmail(customer.getEmail());
				hCustomer.setPhoneNumber(customer.getPhoneNumber());
				response = target.request().post(Entity.entity(hCustomer, "application/json"));
				if (response.getStatus() == 200) {
					targetCustomer = response.readEntity(Customer.class);
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

	/**
	 * Create the booking in other two system by existing information, return the
	 * bookingId.
	 * 
	 * @return
	 */
	Long createBookingInOuterSystem(BookingSystemType systemType, Long customerId, Long commodityId, Date bookingDate) {

		// Get all customers from specific remote system
		Client client = ClientBuilder.newBuilder().build();
		WebTarget target = client.target(systemType.getBaseUrl() + "bookings");

		switch (systemType) {
		case TAXI_SYSTEM: {
			TaxiBookingDto bookingDto = new TaxiBookingDto();
			bookingDto.setCustomerId(customerId);
			bookingDto.setTaxiId(commodityId);
			bookingDto.setDate(bookingDate);
			Response response = target.request().post(Entity.entity(bookingDto, "application/json"));

			response.close();
			return null;
		}
		case HOTEL_SYSTEM: {
			HotelBookingDto bookingDto = new HotelBookingDto();
			bookingDto.setCustomerId(customerId);
			bookingDto.setHotelId(commodityId);
			bookingDto.setBookingDate(bookingDate);
			Response response = target.request().post(Entity.entity(bookingDto, "application/json"));

			response.close();
			return null;
		}
		case FLIGHT_SYSTEM: {
			BookingDto bookingDto = new BookingDto();
			bookingDto.setCustomerId(customerId);
			bookingDto.setFlightId(commodityId);
			bookingDto.setBookingDate(bookingDate);
			Response response = target.request().post(Entity.entity(bookingDto, "application/json"));
			if (response.getStatus() == 201) {
				Booking bookingResult = response.readEntity(Booking.class);
				response.close();
				return bookingResult.getId();
			} else {
				response.getStatusInfo();
			}
			response.close();
			return null;
		}
		}
		return null;
	}

	/**
	 * Cancel the booking in other two system by existing information, return the
	 * bookingId.
	 * 
	 * @return
	 */
	boolean deleteBookingInOuterSystem(BookingSystemType systemType, Long bookingId) {
		boolean rs = false;
		if (bookingId != null) {
			Client client = ClientBuilder.newBuilder().build();
			WebTarget target = client.target(systemType.getBaseUrl() + "bookings").path(bookingId.toString());
			Response response = target.request().delete();
			rs = response.getStatus() == 204;
			response.close();
		}

		return rs;
	}

	/**
	 * deleteTravelAgentBooking
	 * 
	 * @param tABookingId
	 * @return
	 */
	boolean deleteTravelAgentBooking(Long tABookingId) {
		return false;
	}

	TravelAgentBooking create(TravelAgentBookingDto travelAgentBookingDto, Long flightBookingId, Long hotelBookingId,
			Long taxiBookingId) throws ConstraintViolationException, ValidationException, Exception {
		log.info("TravelAgentService.create() - Creating Booking-" + travelAgentBookingDto);
		
		TravelAgentBooking booking = new TravelAgentBooking();
		booking.setFlightBookingId(flightBookingId);
		booking.setHotelBookingId(hotelBookingId);
		booking.setTaxiBookingId(taxiBookingId);

		return crud.create(booking);
	}
}
