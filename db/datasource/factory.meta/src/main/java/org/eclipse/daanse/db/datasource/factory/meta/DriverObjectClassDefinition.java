package org.eclipse.daanse.db.datasource.factory.meta;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Stream;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

public class DriverObjectClassDefinition implements ObjectClassDefinition {

	private Driver driver;
	private String name;
	private String id;
	private String defaultUrl;
	private String iconPath;

	public DriverObjectClassDefinition(Driver driver, String name, String id, String defaultUrl, String iconPath) {
		this.driver = driver;
		this.name = name;
		this.id = id;
		this.defaultUrl = defaultUrl;
		this.iconPath = iconPath;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public String getDescription() {
		return String.format("%%s", getName());
	}

	@Override
	public AttributeDefinition[] getAttributeDefinitions(int filter) {
		try {
			DriverPropertyInfo[] propertyInfos = driver.getPropertyInfo(defaultUrl, new Properties());
			Stream<DriverPropertyInfo> stream = Arrays.stream(propertyInfos);
			if (filter == OPTIONAL) {
				stream = stream.filter(pi -> !pi.required);
			} else if (filter == REQUIRED) {
				stream = stream.filter(pi -> pi.required);
			}
			return stream.map(DriverPropertyInfoAttributeDefinition::new).toArray(AttributeDefinition[]::new);
		} catch (SQLException e) {
			return new AttributeDefinition[0];
		}
	}

	@Override
	public InputStream getIcon(int size) throws IOException {
		if (iconPath != null && !iconPath.isBlank()) {
			Bundle bundle = FrameworkUtil.getBundle(driver.getClass());
			if (bundle != null) {
				URL entry = bundle.getEntry(iconPath);
				if (entry != null) {
					return entry.openStream();
				}
			}
		}
		return null; // no icon...
	}

}
