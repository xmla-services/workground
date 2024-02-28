package org.eclipse.daanse.ws.runtime.registrar.spi;

public interface PublishedEndpoint {
	
	/**
	 * Unpublish the endpoint
	 */
	void unpublish();
	
	/**
	 * @return the full address under that this endpoint is reachable
	 */
	String getAddress();

}
