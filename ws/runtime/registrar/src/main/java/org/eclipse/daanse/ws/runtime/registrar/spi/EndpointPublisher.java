package org.eclipse.daanse.ws.runtime.registrar.spi;

import java.util.Map;

import jakarta.xml.ws.Endpoint;

/**
 * An SPI interface implemented by components that are capable of publishing an
 * endpoints.
 */
public interface EndpointPublisher {

	/**
	 * Called by the Endpoint Registrar to publish an endpoint, if this publisher is
	 * capable of publishing the endpoint given the provided properties it must
	 * return a {@link PublishedEndpoint} that can be used to unpublish it again. If
	 * the method throw any {@link RuntimeException} the endpoint is assumed to
	 * failed to publish
	 * 
	 * @param endpoint the endpoint to publish
	 * @return the published endpoint
	 * @throws RuntimeException when the endpoint is failing to publish because it is invalid
	 */
	PublishedEndpoint publishEndpoint(Endpoint endpoint);
}
