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

	public static SchemaUpdater createSubstitutingCube(final String cubeName, final String dimensionDefs) {

		return new SchemaUpdater((schema) -> SchemaUtil.createSubstitutingCube(schema, cubeName, dimensionDefs));

	}

	public static SchemaUpdater createSubstitutingCube(final String cubeName, final String dimensionDefs,
			final String memberDefs) {

		return new SchemaUpdater(
				(schema) -> SchemaUtil.createSubstitutingCube(schema, cubeName, dimensionDefs, memberDefs));

	}

	public static SchemaUpdater createSubstitutingCube(final String cubeName, final String dimensionDefs,
			final String measureDefs, final String memberDefs, final String namedSetDefs) {

		return new SchemaUpdater((schema) -> SchemaUtil.createSubstitutingCube(schema, cubeName, dimensionDefs,
				measureDefs, memberDefs, namedSetDefs));

	}

	public static SchemaUpdater createSubstitutingCube(final String cubeName, final String dimensionDefs,
													   final String measureDefs, final String memberDefs, final String namedSetDefs, boolean removeCatalog) {

		return new SchemaUpdater((schema) -> SchemaUtil.createSubstitutingCube(schema, cubeName, dimensionDefs,
				measureDefs, memberDefs, namedSetDefs), removeCatalog);

	}


	public static SchemaUpdater createSubstitutingCube(final String cubeName,
													   final String dimensionDefs,
													   final String measureDefs,
													   final String memberDefs,
													   final String namedSetDefs,
													   final String defaultMeasure) {

		return new SchemaUpdater((schema) -> SchemaUtil.createSubstitutingCube(schema, cubeName, dimensionDefs,
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
