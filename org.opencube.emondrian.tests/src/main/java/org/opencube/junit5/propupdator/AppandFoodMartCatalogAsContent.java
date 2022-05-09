package org.opencube.junit5.propupdator;

import java.io.File;

import org.opencube.junit5.Constants;

import mondrian.olap.Util.PropertyList;
import mondrian.rolap.RolapConnectionProperties;

public class AppandFoodMartCatalogAsContent implements PropertyUpdater {

	@Override
	public PropertyList update(PropertyList propertyList) {
		String content;
		try {
			content = new String( new File(Constants.TESTFILES_DIR +"/catalogs/FoodMart.xml").toURI().toURL().openConnection().getInputStream().readAllBytes());
			propertyList.put(RolapConnectionProperties.CatalogContent.name(), content);
			return propertyList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
