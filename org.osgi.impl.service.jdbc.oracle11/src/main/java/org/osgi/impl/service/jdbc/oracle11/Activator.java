package org.osgi.impl.service.jdbc.oracle11;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.annotation.bundle.Header;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.jdbc.DataSourceFactory;

import oracle.jdbc.OracleDriver;


@Header(name = Constants.BUNDLE_ACTIVATOR, value = "${@class}")
public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		
		Oracle11DataSourceFactory dsf = new Oracle11DataSourceFactory();
		Dictionary<String, String> props = new Hashtable<String, String>();
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, OracleDriver.class.getName());
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME, "oracle11");
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_VERSION, dsf.getVersion());

		context.registerService(DataSourceFactory.class.getName(), dsf, props);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// Auto unregister over BundleContext
	}
}
