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
package uk.ac.ncl.tongzhou.enterprisemiddleware;

/**
 * A class extending {@link Application} and annotated with @ApplicationPath is
 * the Java EE 6 "no XML" approach to activating JAX-RS.
 *
 * <p>
 * Resources are served relative to the servlet path specified in the
 * {@link ApplicationPath} annotation.
 * </p>
 */
//@ApplicationPath("/api")
//public class FlightServiceApplication extends Application {
//
//	public FlightServiceApplication() {
//		BeanConfig beanConfig = new BeanConfig();
//		beanConfig.setVersion("0.1.0");
//		beanConfig.setSchemes(new String[] { "http" });
//		// We may no longer need to change this
//		// beanConfig.setHost("localhost:8080/jboss-flights-swagger");
//		beanConfig.setBasePath("/api");
//		beanConfig.setTitle("Tong Zhou - Flight APIs");
//		beanConfig.setDescription("JBoss WFK Flights Swagger Quickstart");
//		// Add additional RESTService containing packages here, separated by commas:
//		// "org.jboss.quickstarts.wfk.flight," +
//		// "org.jboss.quickstarts.wfk.other"
//		beanConfig.setResourcePackage("uk.ac.ncl.tongzhou.enterprisemiddleware");
//		// beanConfig.setResourcePackage("uk.ac.ncl.tongzhou.enterprisemiddleware");
//
//		beanConfig.setScan(true);
//
//		// Do not edit below
//		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
//	}
//
//	@Override
//	public Set<Class<?>> getClasses() {
//		Set<Class<?>> services = new HashSet<>();
//
//		// Add RESTful resources here as you create them
//		services.add(FlightRestService.class);
//		services.add(CustomerRestService.class);
//		services.add(BookingRestService.class);
//
//		// Do not edit below
//		services.add(RestServiceExceptionHandler.class);
//		services.add(io.swagger.jaxrs.listing.ApiListingResource.class);
//		services.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
//
//		return services;
//	}
//
//	@Override
//	public Set<Object> getSingletons() {
//		Set<Object> singletons = new HashSet<>();
//		singletons.add(new JacksonConfig());
//		return singletons;
//	}
//
//}
