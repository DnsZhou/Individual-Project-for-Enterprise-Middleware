
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
import org.jboss.quickstarts.wfk.util.RestServiceException;

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
	 * @throws Exception
	 */
	Long getCustomerIdInOuterSystem(BookingSystemType systemType, Customer customer) throws Exception {

		// Get all customers from specific remote system
		Client client = ClientBuilder.newBuilder().build();
		WebTarget target = client.target(systemType.getBaseUrl() + "customers");
		Response response = target.request().get();

		switch (systemType) {
		case TAXI_SYSTEM: {
			List<TaxiCustomerDto> customers = response.readEntity(new GenericType<List<TaxiCustomerDto>>() {
			});
			response.close();

			// Find target customer by e-mail address in remote system
			TaxiCustomerDto targetCustomer = customers.stream()
					.filter(cus -> cus.getEmail().equals(customer.getEmail())).findFirst().orElse(null);
			if (targetCustomer == null) {
				// Create the customer if the customer is not found in remote System
				TaxiCustomerDto tCustomer = new TaxiCustomerDto();
				tCustomer.setName(customer.getCustomerName());
				tCustomer.setEmail(customer.getEmail());
				tCustomer.setPhoneNumber(customer.getPhoneNumber());
				response = target.request().post(Entity.entity(tCustomer, "application/json"));
				if (response.getStatus() == 201) {
					targetCustomer = response.readEntity(TaxiCustomerDto.class);
					response.close();
				} else {
					log.info("failed to get Customer Id from Outer System: " + systemType.name());
					throw new Exception();
				}
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
				if (response.getStatus() == 201) {
					targetCustomer = response.readEntity(HotelCustomerDto.class);
					response.close();
				} else {
					log.info("failed to get Customer Id from Outer System: " + systemType.name());
					throw new Exception();
				}
			}
			return targetCustomer.getId();
		}
		case FLIGHT_SYSTEM: {
			List<FlightCustomerDto> customers = response.readEntity(new GenericType<List<FlightCustomerDto>>() {
			});
			response.close();

			// Find target customer by e-mail address in remote system
			FlightCustomerDto targetCustomer = customers.stream()
					.filter(cus -> cus.getEmail().equals(customer.getEmail())).findFirst().orElse(null);
			if (targetCustomer == null) {
				// Create the customer if the customer is not found in remote System
				FlightCustomerDto fCustomer = new FlightCustomerDto();
				fCustomer.setName(customer.getCustomerName());
				fCustomer.setEmail(customer.getEmail());
				fCustomer.setPhoneNumber(customer.getPhoneNumber());
				response = target.request().post(Entity.entity(fCustomer, "application/json"));
				if (response.getStatus() == 201) {
					targetCustomer = response.readEntity(FlightCustomerDto.class);
					response.close();
				} else {
					log.info("failed to get Customer Id from Outer System: " + systemType.name());
					throw new Exception();
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
	Long createBookingInOuterSystem(BookingSystemType systemType, Long customerId, Long commodityId, Date bookingDate)
			throws Exception {

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
			if (response.getStatus() == 201) {
				TaxiBookingDto bookingResult = response.readEntity(TaxiBookingDto.class);
				response.close();
				return bookingResult.getId();
			} else {
				response.getStatusInfo();
				response.close();
				throw new RestServiceException();
			}
		}
		case HOTEL_SYSTEM: {
			HotelBookingDto bookingDto = new HotelBookingDto();
			HotelCustomerDto hCustomer = new HotelCustomerDto();
			HotelDto hotel = new HotelDto();
			hotel.setId(commodityId);
			hCustomer.setId(customerId);
			bookingDto.setCustomer(hCustomer);
			bookingDto.setHotel(hotel);
			bookingDto.setBookingDate(bookingDate);
			Response response = target.request().post(Entity.entity(bookingDto, "application/json"));
			if (response.getStatus() == 201) {
				HotelBookingDto bookingResult = response.readEntity(HotelBookingDto.class);
				response.close();
				return bookingResult.getId();
			} else {
				response.getStatusInfo();
				response.close();
				throw new RestServiceException();
			}
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
				response.close();
				throw new RestServiceException();
			}
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
