/**
 * jboss-wfk-quickstarts
 * </p>
 * Copyright (c) 2015 Jonny Daenen, Hugo Firth & Bas Ketsman
 * Email: <me@hugofirth.com/>
 * </p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 * </p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * </p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.booking;

import javax.validation.ValidationException;

import uk.ac.ncl.tongzhou.enterprisemiddleware.customer.Customer;

/**
 * <p>ValidationException caused if a Booking's Flight ID not found in system</p>
 *
 * <p>This violates the uniqueness constraint.</p>
 *
 * @author Tong Zhou
 * @see Customer
 */
public class FlightNotFoundException extends ValidationException {

    public FlightNotFoundException(String message) {
        super(message);
    }

    public FlightNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlightNotFoundException(Throwable cause) {
        super(cause);
    }
}
