/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * History:
 *  This files came from the mondrian project. Some of the Flies
 *  (mostly the Tests) did not have License Header.
 *  But the Project is EPL Header. 2002-2022 Hitachi Vantara.
 *
 * Contributors:
 *   Hitachi Vantara.
 *   SmartCity Jena - initial  Java 8, Junit5
 */

package org.opencube.junit5.propupdator;

import java.io.File;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.foodmart.record.FoodMartRecordDbMappingSchemaProvider;
import org.opencube.junit5.Constants;
import org.opencube.junit5.context.TestContext;

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
	
	@Override
	public void updateContext(TestContext context) {
		context.setDatabaseMappingSchemaProviders(List.of(new FoodMartRecordDbMappingSchemaProvider()));
	}

}
