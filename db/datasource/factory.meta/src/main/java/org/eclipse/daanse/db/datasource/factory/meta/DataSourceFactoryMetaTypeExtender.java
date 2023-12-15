package org.eclipse.daanse.db.datasource.factory.meta;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.jdbc.DataSourceFactory;
import org.osgi.service.metatype.MetaTypeProvider;
import org.osgi.service.metatype.ObjectClassDefinition;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * This extender do the following:
 * <ul>
 * <li>Track any {@link DataSourceFactory}</li>
 * <li>Create a Driver and inspect supported properties</li>
 * <li>Register a {@link ManagedServiceFactory} on behalf of the driver that
 * allows to configure new DataSources using the supplied configuration</li>
 * </ul>
 *
 */
@Component(service = {}, immediate = true)
public class DataSourceFactoryMetaTypeExtender {

	private ServiceTracker<?, ?> serviceTracker;

	@Activate
	public void startTracking(BundleContext bundleContext) {
		DataSourceFactoryExtensionTracker extensionTracker = new DataSourceFactoryExtensionTracker(bundleContext);
		serviceTracker = new ServiceTracker<>(bundleContext, DataSourceFactory.class, extensionTracker);
		serviceTracker.open();
	}

	@Deactivate
	public void stopTracking() {
		serviceTracker.close();
	}

	private static final class DataSourceFactoryExtensionTracker
			implements ServiceTrackerCustomizer<DataSourceFactory, ServiceRegistration<ManagedServiceFactory>> {

		private BundleContext bundleContext;

		public DataSourceFactoryExtensionTracker(BundleContext bundleContext) {
			this.bundleContext = bundleContext;
		}

		@Override
		public ServiceRegistration<ManagedServiceFactory> addingService(ServiceReference<DataSourceFactory> reference) {
			DataSourceFactory dataSourceFactory = bundleContext.getService(reference);
			if (dataSourceFactory != null) {
				try {
					String driverName = (String) reference.getProperty(DataSourceFactory.OSGI_JDBC_DRIVER_NAME);
					String driverUrl = ""; //FIXME how to determine that??; Mssql+clickhouse want the prefix, mysql can work without other maybe as well...
					DriverManagedServiceFactory managedServiceFactory = new DriverManagedServiceFactory(
							dataSourceFactory.createDriver(null),driverName, driverUrl , bundleContext);
					return bundleContext.registerService(ManagedServiceFactory.class, managedServiceFactory,
							createExtendedProperties(reference));
				} catch (SQLException e) {
					// can't use this then...
					bundleContext.ungetService(reference);
				}
			}
			return null;
		}

		private Dictionary<String, Object> createExtendedProperties(ServiceReference<DataSourceFactory> reference) {
			Dictionary<String, Object> extensionProperties = reference.getProperties();
			extensionProperties.put(Constants.SERVICE_PID,
					reference.getProperty(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS) + ".driver");
			return extensionProperties;
		}

		@Override
		public void modifiedService(ServiceReference<DataSourceFactory> reference,
				ServiceRegistration<ManagedServiceFactory> service) {
			service.setProperties(createExtendedProperties(reference));
		}

		@Override
		public void removedService(ServiceReference<DataSourceFactory> reference,
				ServiceRegistration<ManagedServiceFactory> service) {
			service.unregister();
		}

	}

	private static final class DriverManagedServiceFactory implements ManagedServiceFactory, MetaTypeProvider {

		private Driver driver;
		private String name;
		private BundleContext bundleContext;
		private String defaultUrl;

		public DriverManagedServiceFactory(Driver driver, String name, String defaultUrl, BundleContext bundleContext) {
			this.driver = driver;
			this.name = name;
			this.defaultUrl = defaultUrl;
			this.bundleContext = bundleContext;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void updated(String pid, Dictionary<String, ?> properties) throws ConfigurationException {
			// TODO Auto-generated method stub

		}

		@Override
		public void deleted(String pid) {
			// TODO Auto-generated method stub

		}

		@Override
		public ObjectClassDefinition getObjectClassDefinition(String id, String locale) {
			return new DriverObjectClassDefinition(driver, getName(), id, defaultUrl,null);
		}

		@Override
		public String[] getLocales() {
			// drivers usually localize in a driver specific manner
			return null;
		}

	}
}
