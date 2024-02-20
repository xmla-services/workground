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

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.annotation.bundle.Capability;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.dto.ServiceReferenceDTO;
import org.osgi.namespace.implementation.ImplementationNamespace;
import org.osgi.service.component.AnyService;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.jakartaws.runtime.WebserviceServiceRuntime;
import org.osgi.service.jakartaws.runtime.dto.EndpointDTO;
import org.osgi.service.jakartaws.runtime.dto.HandlerDTO;
import org.osgi.service.jakartaws.runtime.dto.RuntimeDTO;
import org.osgi.service.jakartaws.whiteboard.SoapWhiteboardConstants;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.MessageContext;

@Component(immediate = true, service = { WebserviceServiceRuntime.class })
@Capability(namespace = ImplementationNamespace.IMPLEMENTATION_NAMESPACE, //
		name = SoapWhiteboardConstants.SOAP, //
		version = SoapWhiteboardConstants.SOAP_SPECIFICATION_VERSION)
public class EndpointRegistrar implements WebserviceServiceRuntime {

	private BundleContext bundleContext;
	private Logger logger;

	private Map<Bundle, BundleEndpointContext> contextMap = new WeakHashMap<>();
	private Map<Handler<? extends MessageContext>, HandlerInfo> handlerMap = new ConcurrentHashMap<>();
	private Map<Object, EndpointRegistration> endpointRegistrations = new ConcurrentHashMap<>();
	private ComponentContext context;

	@Activate
	public EndpointRegistrar(BundleContext bc, @Reference(service = LoggerFactory.class) Logger logger,
			ComponentContext context) {
		this.bundleContext = bc;
		this.logger = logger;
		this.context = context;
	}

	@Reference(service = AnyService.class, target = "(" + SoapWhiteboardConstants.SOAP_ENDPOINT_IMPLEMENTOR
			+ "=true)", cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY)
	public void bindEndpointImplementor(ServiceReference<?> endpointImplementorReference) {
		System.out.println("EndpointRegistrar.bindEndpointImplementor()");
		Map<String, Object> filteredProperties = FrameworkUtil.asMap(endpointImplementorReference.getProperties()).entrySet().stream()//
				.filter(e -> !e.getKey().startsWith("."))// secret properties should not be propagated
				.filter(e -> !SoapWhiteboardConstants.SOAP_ENDPOINT_IMPLEMENTOR.equals(e.getKey()))// don't propagate
																									// the implementor
																									// property
				.collect(Collectors.toUnmodifiableMap(Entry::getKey, Entry::getValue));
		logger.debug(">> BINDING implementor={} properties={}, filtered={}", endpointImplementorReference, endpointImplementorReference.getProperties(),
				filteredProperties);
		EndpointRegistration registration = new EndpointRegistration(filteredProperties, endpointImplementorReference);
		endpointRegistrations.put(endpointImplementorReference, registration);
		registration.register();
	}

	public void unbindEndpointImplementor(ServiceReference<?> endpointImplementorReference) {
		logger.debug("UNBINDING implementor={}", endpointImplementorReference);
		EndpointRegistration registration = endpointRegistrations.remove(endpointImplementorReference);
		if (registration != null) {
			registration.unregister();
		}
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addHandler(Handler<? extends MessageContext> handler, Map<String, ?> properties)
			throws InvalidSyntaxException {
		Object epSelect = ""; // FIXME properties.get(SoapWhiteboardConstants.SOAP_ENDPOINT_SELECT);
		logger.debug("ADD handler={}, epSelect = {}, properties={}", handler, epSelect, properties);
		if (epSelect instanceof String fs) {
			try {
				Filter filter = FrameworkUtil.createFilter(fs);
				Number priority = (Integer) properties.get(Constants.SERVICE_RANKING);
				handlerMap.put(handler, new HandlerInfo(filter, priority == null ? 0 : priority.intValue()));
				updateAll();
			} catch (RuntimeException e) {
				logger.error("EndpointRegistrar error addHandler", e);
			}
		}
	}

	public void removeHandler(Handler<? extends MessageContext> handler) {
		logger.debug("REMOVE handler={}", handler);
		if (handlerMap.remove(handler) != null) {
			updateAll();
		}
	}

	private void updateAll() {
		logger.debug("Update all handlers...");
		endpointRegistrations.values().stream().forEach(EndpointRegistration::refresh);
	}

	private final class EndpointRegistration implements PrototypeServiceFactory<Endpoint> {

		private ServiceRegistration<?> registration;
		private Map<Bundle, Endpoint> endpointMap = new ConcurrentHashMap<>();
		private Map<String, Object> properties;
		private List<Handler> handlerChain;
		private ServiceReference<?> implementorReference;

		public EndpointRegistration(Map<String, Object> properties, ServiceReference<?> implementorReference) {
			this.properties = properties;
			this.implementorReference = implementorReference;
		}

		public void refresh() {
			if (registration != null) {
				unregister();
				register();
			}
		}

		public void unregister() {
			try {
				if (registration != null) {
					registration.unregister();
				}
			} catch (IllegalStateException e) {
				// no one cares then...
			}
			endpointMap.forEach((b, e) -> ungetService(b, null, e));
		}

		public void register() {
			Dictionary<String, Object> dictionaryProperties = getServiceProperties();
			handlerChain = handlers(dictionaryProperties).toList();
			registration = bundleContext.registerService(Endpoint.class, this, dictionaryProperties);
		}

		@Override
		public Endpoint getService(Bundle bundle, ServiceRegistration<Endpoint> registration) {
			BundleContext requesting = bundle.getBundleContext();
			if (requesting == null) {
				//should never happen but better safe than sorry!
				return null;
			}
			Object implementor = requesting.getService(implementorReference);
			if (implementor == null) {
				//bundle is maybe not allowed to fetch this service!
				return null;
			}
			Endpoint endpoint = Endpoint.create(implementor);
			endpointMap.put(bundle, endpoint);
			try {
				endpoint.getBinding().setHandlerChain(handlerChain);
			} catch (UnsupportedOperationException | WebServiceException e) {
				// can't set a handler chain then!
				logger.warn("Can't update handler chain for {}: {}", endpoint, e.toString());
			}
			endpoint.setProperties(properties);
			synchronized (contextMap) {
				BundleEndpointContext context = contextMap.computeIfAbsent(bundle, BundleEndpointContext::new);
				context.addEndpoint(endpoint);
				endpoint.setEndpointContext(context);
			}
			return endpoint;
		}

		private Dictionary<String, Object> getServiceProperties() {
			return FrameworkUtil.asDictionary(properties);
		}

		@Override
		public void ungetService(Bundle bundle, ServiceRegistration<Endpoint> registration, Endpoint endpoint) {
			BundleContext requesting = bundle.getBundleContext();
			if (requesting != null) {
				requesting.ungetService(implementorReference);
			}
			endpointMap.remove(bundle);
			synchronized (contextMap) {
				BundleEndpointContext context = contextMap.get(bundle);
				if (context != null) {
					context.removeEndpoint(endpoint);
					if (context.getEndpoints().isEmpty()) {
						contextMap.remove(bundle);
					}
				}
			}
			endpoint.stop();
		}

		@SuppressWarnings("rawtypes"/* required by API */)
		private Stream<Handler> handlers(Dictionary<String, ?> properties) {
			return handlerMap.entrySet().stream().filter(e -> handlerMatches(properties, e.getKey(), e.getValue()))
					.sorted(HandlerInfo.SORT_BY_PRIORITY).map(Entry::getKey);
		}

		private boolean handlerMatches(Dictionary<String, ?> properties, Handler<?> handler, HandlerInfo handlerInfo) {
			if (handlerInfo.filter().match(properties)) {
				logger.debug("Handler {} filter {} matches {}", handler, handlerInfo.filter(), properties);
				return true;
			}
			logger.debug("Handler {} filter {} NOT matches {}", handler, handlerInfo.filter(), properties);
			return false;
		}
	}

	@Override
	public RuntimeDTO getRuntimeDTO() {
		// TODO implement me!
		RuntimeDTO runtimeDTO = new RuntimeDTO();
		runtimeDTO.endpoints = endpointRegistrations.values().stream().map(EndpointRegistrar::getEndpointDTO).toArray(EndpointDTO[]::new);
		runtimeDTO.handlers = new HandlerDTO[0];
		runtimeDTO.serviceReference = context.getServiceReference().adapt(ServiceReferenceDTO.class);
		return runtimeDTO;
	}
	
	private static EndpointDTO getEndpointDTO(EndpointRegistration registration) {
		EndpointDTO dto = new EndpointDTO();
		dto.implementor = registration.implementorReference.adapt(ServiceReferenceDTO.class);
		return dto;
	}

}
