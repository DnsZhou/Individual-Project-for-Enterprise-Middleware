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
package uk.ac.ncl.tongzhou.enterprisemiddleware.flight;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Date;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.quickstarts.wfk.util.RestServiceException;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <p>
 * A suite of tests, run with {@link org.jboss.arquillian Arquillian} to test
 * the JAX-RS endpoint for Flight creation functionality (see
 * {@link FlightRestService#createFlight(Flight) createFlight(Flight)}).
 * </p>
 *
 * @author balunasj
 * @author Joshua Wilson
 * @see FlightRestService
 */
@RunWith(Arquillian.class)
public class FlightRegistrationTest {

	/**
	 * <p>
	 * Compiles an Archive using Shrinkwrap, containing those external dependencies
	 * necessary to run the tests.
	 * </p>
	 *
	 * <p>
	 * Note: This code will be needed at the start of each Arquillian test, but
	 * should not need to be edited, except to pass *.class values to
	 * .addClasses(...) which are appropriate to the functionality you are trying to
	 * test.
	 * </p>
	 *
	 * @return Micro test war to be deployed and executed.
	 */
	@Deployment
	public static Archive<?> createTestArchive() {
		File[] libs = Maven.resolver().loadPomFromFile("pom.xml").resolve("io.swagger:swagger-jaxrs:1.5.15")
				.withTransitivity().asFile();

		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackages(true, "uk.ac.ncl.tongzhou.enterprisemiddleware")
				.addPackages(true, "org.jboss.quickstarts.wfk").addAsLibraries(libs)
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource("arquillian-ds.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	FlightRestService flightRestService;

	@Inject
	@Named("logger")
	Logger log;

	// Set millis 498484800000 from 1985-10-10T12:00:00.000Z
	private Date date = new Date(498484800000L);

	@Test
	@InSequence(1)
	public void testRegister() throws Exception {
		Flight flight = createFlightInstance("PVG", "NU001", "NCL");
		Response response = flightRestService.createFlight(flight);

		assertEquals("Unexpected response status", 201, response.getStatus());
		log.info(" New flight was persisted and returned status " + response.getStatus());
	}

	@SuppressWarnings("unchecked")
	@Test
	@InSequence(2)
	public void testInvalidRegister() {
		Flight flight = createFlightInstance("", "", "");

		try {
			flightRestService.createFlight(flight);
			fail("Expected a RestServiceException to be thrown");
		} catch (RestServiceException e) {
			assertEquals("Unexpected response status", Response.Status.BAD_REQUEST, e.getStatus());
			assertEquals("Unexpected response body", 3, e.getReasons().size());
			log.info("Invalid flight register attempt failed with return code " + e.getStatus());
		}

	}

	@SuppressWarnings("unchecked")
	@Test
	@InSequence(3)
	public void testDestinationDuplicateWithDeparture() throws Exception {
		// Register an initial user
		Flight flight = createFlightInstance("PVG", "NU001", "PVG");
		try {
			flightRestService.createFlight(flight);
			fail("Expected a RestServiceException to be thrown");
		} catch (RestServiceException e) {
			assertEquals("Unexpected response status", Response.Status.BAD_REQUEST, e.getStatus());
			assertTrue("The destination is same with point of departure",
					e.getCause() instanceof DestinationDuplicateWithDepartureException);
			assertEquals("Unexpected response body", 1, e.getReasons().size());
			log.info("Duplicate destination with departure attempt failed with return code " + e.getStatus());
		}

	}

	/**
	 * <p>
	 * A utility method to construct a
	 * {@link org.jboss.quickstarts.wfk.flight.Flight Flight} object for use in
	 * testing. This object is not persisted.
	 * </p>
	 *
	 * @return The Flight object create
	 */
	private Flight createFlightInstance(String destination, String number, String pointOfDeparture) {
		Flight flight = new Flight();
		flight.setDestination(destination);
		flight.setNumber(number);
		flight.setPointOfDeparture(pointOfDeparture);
		return flight;
	}

}
