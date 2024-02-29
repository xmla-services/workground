/*
* Copyright (c) 2023 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*   Christoph Läubrich - initial
*/
package org.eclipse.daanse.ws.runtime.registrar;

import java.util.Map;

import org.eclipse.daanse.ws.runtime.registrar.spi.EndpointPublisher;
import org.eclipse.daanse.ws.runtime.registrar.spi.PublishedEndpoint;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.osgi.service.jakartaws.whiteboard.SoapWhiteboardConstants;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

import jakarta.xml.ws.Endpoint;

/**
 * publishes an {@link Endpoint} in the simplest way by passing its address
 * through to the {@link Endpoint#publish(String)}, because of this reason we
 * use a lower version range that the default so any other maybe smarter
 * implementation can take precedence over this one.
 */
@Component(service = EndpointPublisher.class)
@ServiceRanking(-100)
public class GenericPublisher implements EndpointPublisher {

	private Logger logger;

	@Activate
	public GenericPublisher(@Reference(service = LoggerFactory.class) Logger logger) {
		this.logger = logger;
	}

	@Override
	public PublishedEndpoint publishEndpoint(Endpoint endpoint) {
		System.out.println("GenericPublisher.publishEndpoint()"+endpoint);
		Map<String, Object> properties = endpoint.getProperties();
		Object epAddress = properties.get(SoapWhiteboardConstants.SOAP_ENDPOINT_ADDRESS);
		if (epAddress instanceof String address && !address.isEmpty()) {
			logger.info("Registering {} with generic publisher and address {}", endpoint, address);
			logger.debug(">>> PUBLISH EP @ " + epAddress + " properties=" + properties);
			endpoint.publish(address);
			return new PublishedEndpoint() {

				@Override
				public void unpublish() {
					endpoint.stop();

				}

				@Override
				public String getAddress() {
					return address;
				}
			};
		}
		return null;
	}

}
