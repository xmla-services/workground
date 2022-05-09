package org.opencube.junit5.propupdator;

import org.opencube.junit5.Constants;

import mondrian.olap.Util.PropertyList;
import mondrian.rolap.RolapConnectionProperties;

public class AppandFoodMartCatalogAsFile implements PropertyUpdater {

	@Override
	public PropertyList update(PropertyList propertyList) {

		propertyList.put(RolapConnectionProperties.Catalog.name(), Constants.TESTFILES_DIR + "/catalogs/FoodMart.xml");
		return propertyList;
	}

}
