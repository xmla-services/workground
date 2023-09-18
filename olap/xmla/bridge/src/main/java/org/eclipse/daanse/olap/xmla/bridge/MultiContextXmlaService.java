/*
* Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
*/
package org.eclipse.daanse.olap.xmla.bridge;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.olap.xmla.bridge.discover.DelegatingDiscoverService;
import org.eclipse.daanse.olap.xmla.bridge.execute.OlapExecuteService;
import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.discover.DiscoverService;
import org.eclipse.daanse.xmla.api.execute.ExecuteService;
import org.osgi.namespace.unresolvable.UnresolvableNamespace;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;

@Component(service = XmlaService.class)
@Designate(factory = true, ocd = Config.class)
public class MultiContextXmlaService implements XmlaService {

	private static final Converter CONVERTER = Converters.standardConverter();
	private Config config;

	@Activate
	public MultiContextXmlaService(Map<String, Object> props) {
		this(CONVERTER.convert(props).to(Config.class));
	}

	public MultiContextXmlaService(Config config) {
		this.config = config;
		reInit();
	}

	public static final String PID = "org.eclipse.daanse.olap.xmla.bridge.MultiContextXmlaService";
	public static final String REF_NAME_CONTEXT = "context";

	private ExecuteService executeService;
	private DiscoverService discoverService;
	private List<Context> contexts = new CopyOnWriteArrayList<>();

	/*
	 * target must be configured. no auto fetch of a Context
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, name = REF_NAME_CONTEXT, target = UnresolvableNamespace.UNRESOLVABLE_FILTER)
	void bindContext(Context context) {
		contexts.add(context);
		reInit();
	}

	private void reInit() {
		ContextListSupplyer contextsListSupplyer = new ContextsSupplyerImpl(getContexts());
		executeService = new OlapExecuteService(contextsListSupplyer);
		discoverService = new DelegatingDiscoverService(contextsListSupplyer);
	}

	void unbindContext(Context context) {
		contexts.remove(context);
		reInit();

	}

	@Override
	public DiscoverService discover() {
		return discoverService;
	}

	@Override
	public ExecuteService execute() {
		return executeService;
	}

	List<Context> getContexts() {
		return contexts;
	}

}
