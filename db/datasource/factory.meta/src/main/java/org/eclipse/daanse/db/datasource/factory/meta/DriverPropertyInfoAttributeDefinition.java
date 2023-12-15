package org.eclipse.daanse.db.datasource.factory.meta;

import java.sql.DriverPropertyInfo;

import org.osgi.service.metatype.AttributeDefinition;

public class DriverPropertyInfoAttributeDefinition implements AttributeDefinition{
	
	private DriverPropertyInfo propertyInfo;

	public DriverPropertyInfoAttributeDefinition(DriverPropertyInfo propertyInfo) {
		this.propertyInfo = propertyInfo;
	}

	@Override
	public String getName() {
		return propertyInfo.name;
	}

	@Override
	public String getID() {
		return propertyInfo.name;
	}

	@Override
	public String getDescription() {
		return propertyInfo.description;
	}

	@Override
	public int getCardinality() {
		return 0;//return propertyInfo.required;
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return AttributeDefinition.STRING ;
	}

	@Override
	public String[] getOptionValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getOptionLabels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String validate(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDefaultValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
