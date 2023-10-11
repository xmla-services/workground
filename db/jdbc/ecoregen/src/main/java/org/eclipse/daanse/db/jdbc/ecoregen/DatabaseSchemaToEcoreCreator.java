package org.eclipse.daanse.db.jdbc.ecoregen;

import static org.eclipse.daanse.db.jdbc.ecoregen.EcoreCreateUtil.addAnnotation;
import static org.eclipse.daanse.db.jdbc.ecoregen.EcoreCreateUtil.addAttribute;
import static org.eclipse.daanse.db.jdbc.ecoregen.EcoreCreateUtil.createEClass;
import static org.eclipse.daanse.db.jdbc.ecoregen.EcoreCreateUtil.createPackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;

@Designate(ocd = CreatorConfig.class, factory = true)
@Component(immediate = true)
public class DatabaseSchemaToEcoreCreator {

	public static Converter CONVERTER = Converters.standardConverter();

	@Reference
	DataSource dataSource;

	@Activate
	public void activate(Map<String, Object> map) {
		CreatorConfig config = CONVERTER.convert(map).to(CreatorConfig.class);
		activate(config);
	}

	public void activate(CreatorConfig config) {
		try {
			createNewModel(config.ecore_packageName(), config.ecore_nsUri(), config.ecore_nsPrefix());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createNewModel(String packageName, String nsPrefix, String nsUri) {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		try (Connection connection = dataSource.getConnection()) {

			String catalogName = connection.getCatalog();
			DatabaseMetaData metaData = connection.getMetaData();

			ResultSet rsSchema = metaData.getSchemas(catalogName, null);
			while (rsSchema.next()) {
				String schemaName = rsSchema.getString("TABLE_SCHEM");

				System.out.println(schemaName);
				ResourceSet resourceSet = new ResourceSetImpl();
				URI uri = URI.createFileURI(schemaName + ".ecore");
				Resource resource = resourceSet.createResource(uri);

				final EPackage basePackage = createPackage(packageName, nsPrefix, nsUri);
				// add our new package to resource contents
				resource.getContents().add(basePackage);
				addAnnotation(basePackage);

				ResultSet rsTable = metaData.getTables(catalogName, schemaName, null, new String[] { "TABLE" });

				while (rsTable.next()) {
					String tableName = rsTable.getString("TABLE_NAME");

					System.out.println(tableName);
					String tableRemarks = rsTable.getString("REMARKS");

					// next, create the row class
					EClass eClass = createEClass(tableName);
					// add to package before we do anything else
					basePackage.getEClassifiers().add(eClass);

					addAnnotation(eClass);

					ResultSet rsColumn = metaData.getColumns(catalogName, schemaName, tableName, null);

					while (rsColumn.next()) {
						String colName = rsColumn.getString("COLUMN_NAME");

						String colType = rsColumn.getString("TYPE_NAME");

						int nullable = rsColumn.getInt("NULLABLE");
						String colRemarks = rsColumn.getString("REMARKS");

						EDataType eDataType = convertType(colType);
						System.out.println(colName);
						System.out.println(colType);
						System.out.println(nullable);
						System.out.println(colRemarks);
						addAttribute(eClass, colName, eDataType, true, 1, 1);

					}

				}
//					resource.save(System.out, Collections.emptyMap());
				resource.save(Collections.emptyMap());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private EDataType convertType(String colType) {
		if (colType.equals("int4")) {
			return EcorePackage.Literals.EINTEGER_OBJECT;

		} else if (colType.equals("float8")) {
			return EcorePackage.Literals.EBIG_DECIMAL;

		} else if (colType.equals("text")) {
			return EcorePackage.Literals.ESTRING;

		}
		if (colType.equals("varchar")) {
			return EcorePackage.Literals.ESTRING;

		}
		if (colType.equals("geometry")) {
			return EcorePackage.Literals.ESTRING;

		}

		if (colType.equals("bpchar")) {
			return EcorePackage.Literals.ESTRING;

		}
		if (colType.equals("bool")) {
			return EcorePackage.Literals.EBOOLEAN;

		}
		if (colType.equals("serial")) {
			return EcorePackage.Literals.ESTRING;

		}
		if (colType.equals("xml")) {
			return EcorePackage.Literals.ESTRING;

		}

		return EcorePackage.Literals.ESTRING;
	}

	public Resource createModel(String packageName, String nsPrefix, String nsUri, String schema,
			List<String> tableFilters) throws IOException {

		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());

		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = URI.createFileURI("test.ecore");
		Resource resource = resourceSet.createResource(uri);

		final EPackage basePackage = createPackage(packageName, nsPrefix, nsUri);
		// add our new package to resource contents
		resource.getContents().add(basePackage);
		addAnnotation(basePackage);

		// next, create the row class
		EClass customerRow = createEClass("CustomerRow");
		// add to package before we do anything else
		basePackage.getEClassifiers().add(customerRow);

		addAnnotation(customerRow);
		// add our super-class
//		addSuperType(customerRow, otherPackage, "AbstractRow");
		// add our features
		addAttribute(customerRow, "id", EcorePackage.Literals.ESTRING, true, 1, 1);
		addAttribute(customerRow, "firstName", EcorePackage.Literals.ESTRING, false, 0, 1);
		addAttribute(customerRow, "lastName", EcorePackage.Literals.ESTRING, false, 0, 1);

		// next, create the table class
		EClass customers = createEClass("Customers");
		// add to package before we do anything else
		basePackage.getEClassifiers().add(customers);
		// add our super-class
//		addSuperType(customers, otherPackage, "AbstractTable");
		// add our features
		EcoreCreateUtil.addReference(customers, "rows", customerRow, 0, -1);

		// and at last, we save to standard out. Remove the first argument to save to
		// file specified in pathToOutputFile
		resource.save(System.out, Collections.emptyMap());

		return resource;

	}

}
