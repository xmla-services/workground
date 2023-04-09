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
import java.util.function.Function;

import org.opencube.junit5.SchemaUtil;

import mondrian.olap.Util.PropertyList;
import mondrian.rolap.RolapConnectionProperties;

public class SchemaUpdater implements PropertyUpdater {

	private Function<String, String> function;
	private boolean removeCatalog = true;

	private SchemaUpdater() {
	}

	public SchemaUpdater(Function<String, String> function) {
		this();
		this.function = function;
	}

	public SchemaUpdater(Function<String, String> function, boolean removeCatalog) {
		this();
		this.removeCatalog = removeCatalog;
		this.function = function;
	}

	public static SchemaUpdater createSubstitutingCube(final String cubeName, final String dimensionDefs, boolean removeCatalog) {
		return new SchemaUpdater(schema -> SchemaUtil.createSubstitutingCube(schema, cubeName, dimensionDefs), removeCatalog);

	}

	public static SchemaUpdater createSubstitutingCube(final String cubeName, final String dimensionDefs) {

		return new SchemaUpdater(schema -> SchemaUtil.createSubstitutingCube(schema, cubeName, dimensionDefs));

	}

	public static SchemaUpdater createSubstitutingCube(final String cubeName, final String dimensionDefs,
			final String memberDefs) {

		return new SchemaUpdater(
				schema -> SchemaUtil.createSubstitutingCube(schema, cubeName, dimensionDefs, memberDefs));

	}

	public static SchemaUpdater createSubstitutingCube(final String cubeName, final String dimensionDefs,
													   final String memberDefs, boolean removeCatalog) {

		return new SchemaUpdater(
				schema -> SchemaUtil.createSubstitutingCube(schema, cubeName, dimensionDefs, memberDefs), removeCatalog);

	}

	public static SchemaUpdater createSubstitutingCube(final String cubeName, final String dimensionDefs,
			final String measureDefs, final String memberDefs, final String namedSetDefs) {

		return new SchemaUpdater(schema -> SchemaUtil.createSubstitutingCube(schema, cubeName, dimensionDefs,
				measureDefs, memberDefs, namedSetDefs));

	}

	public static SchemaUpdater createSubstitutingCube(final String cubeName, final String dimensionDefs,
													   final String measureDefs, final String memberDefs, final String namedSetDefs, boolean removeCatalog) {

		return new SchemaUpdater(schema -> SchemaUtil.createSubstitutingCube(schema, cubeName, dimensionDefs,
				measureDefs, memberDefs, namedSetDefs), removeCatalog);

	}


	public static SchemaUpdater createSubstitutingCube(final String cubeName,
													   final String dimensionDefs,
													   final String measureDefs,
													   final String memberDefs,
													   final String namedSetDefs,
													   final String defaultMeasure) {

		return new SchemaUpdater(schema -> SchemaUtil.createSubstitutingCube(schema, cubeName, dimensionDefs,
				measureDefs, memberDefs, namedSetDefs, defaultMeasure));

	}

	@Override
	public PropertyList update(PropertyList propertyList) {
		String content = null;
		String catalog = propertyList.get(RolapConnectionProperties.Catalog.name());
		if (removeCatalog) {
			propertyList.remove(RolapConnectionProperties.Catalog.name());
		}
		if (catalog != null && !catalog.isBlank()) {
			try {
				content = new String(
						new File(catalog).toURI().toURL().openConnection().getInputStream().readAllBytes());
			} catch (Exception e) {
				e.printStackTrace();

				throw new RuntimeException(e);
			}
		} else {
			content = propertyList.get(RolapConnectionProperties.CatalogName.name());

		}
		content = function.apply(content);
		// maybe write temp file
		propertyList.put(RolapConnectionProperties.CatalogContent.name(), content);

		return propertyList;
	}

}
