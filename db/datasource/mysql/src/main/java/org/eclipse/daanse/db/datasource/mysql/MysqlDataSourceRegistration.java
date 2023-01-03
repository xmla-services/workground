package org.eclipse.daanse.db.datasource.mysql;

import java.util.Dictionary;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;

import com.mysql.cj.conf.PropertyDefinition;
import com.mysql.cj.conf.PropertyDefinitions;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.conf.RuntimeProperty;

final class MysqlDataSourceRegistration<Srv extends DataSource & PropertySet> {

		private ServiceRegistration<?> serviceRegistration;
		private final Srv managedObject;
		private final Class<? super Srv> serviceType;
		private PropertySet propertySet;

		public MysqlDataSourceRegistration(Srv managedObject, Class<? super Srv> serviceType) {
			this.managedObject = managedObject;
			this.serviceType = serviceType;
			this.propertySet = managedObject;
		}

		public synchronized void update(Dictionary<String, ?> properties, BundleContext bundleContext)
				throws ConfigurationException {
			Properties temp = new Properties();
			for (PropertyDefinition<?> def : PropertyDefinitions.PROPERTY_KEY_TO_PROPERTY_DEFINITION.values()) {
				RuntimeProperty<?> propToSet = propertySet.getProperty(def.getPropertyKey());
				propToSet.resetValue();
				String key = propToSet.getPropertyDefinition().getName();
				try {
					Object object = properties.get(key);
					if (object != null) {
						temp.setProperty(key, String.valueOf(object));
						propToSet.initializeFrom(temp, null);
					}
				} catch (RuntimeException e) {
					throw new ConfigurationException(key, e.getMessage(), e);
				}
			}
			if (serviceRegistration == null) {
				serviceRegistration = bundleContext.registerService(serviceType, managedObject, properties);
			} else {
				serviceRegistration.setProperties(properties);
			}
		}

		public synchronized void dispose() {
			if (serviceRegistration != null) {
				serviceRegistration.unregister();
				serviceRegistration = null;
			}
		}

	}