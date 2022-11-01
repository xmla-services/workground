package org.opencube.junit5.propupdator;

import mondrian.olap.Util.PropertyList;
import mondrian.rolap.RolapConnectionProperties;
import org.opencube.junit5.Constants;

public class AppandSteelWheelsCatalogAsFile implements PropertyUpdater {

	@Override
	public PropertyList update(PropertyList propertyList) {

		propertyList.put(RolapConnectionProperties.Catalog.name(), Constants.TESTFILES_DIR + "/catalogs/SteelWheels.xml");
		return propertyList;
	}

}
