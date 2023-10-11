package org.eclipse.daanse.db.jdbc.ecoregen;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.Property.Scalar;
import org.osgi.test.common.annotation.Property.Type;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ConfigurationExtension.class)
@RequireConfigurationAdmin
public class Test {

	@WithFactoryConfiguration(factoryPid = "org.eclipse.daanse.db.datasource.postgresql.DataSourceService", location = "?", name = "foo", properties = {
			@Property(key = "user", value = "gast"), @Property(key = ".password", value = "gast"),
			@Property(key = "dbname", value = "alt.."), @Property(key = "host", value = "pgsql..."),
			@Property(key = "port", value = "5432", scalar = Scalar.Integer, type = Type.Scalar),
			@Property(key = "ds", value = "1") })
	@org.junit.jupiter.api.Test
	void testName(@InjectService(filter = "(ds=1)") DataSource dataSource) throws Exception {

		assertThat(dataSource).isNotNull();
		
		Thread.sleep(100000);

		try (Connection connection = dataSource.getConnection()) {

			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rsCatalogs = metaData.getCatalogs();

			while (rsCatalogs.next()) {
				String catalog = rsCatalogs.getString("TABLE_CAT");

				ResultSet rsSchema = metaData.getSchemas(catalog, null);
				while (rsSchema.next()) {
					String schemaName = rsSchema.getString("TABLE_SCHEM");

					ResultSet rsTable = metaData.getTables(catalog, schemaName, null, new String[] { "TABLE" });

					while (rsTable.next()) {
						String tableName = rsTable.getString("TABLE_NAME");
						String tableRemarks = rsTable.getString("REMARKS");

						ResultSet rsColumn = metaData.getColumns(catalog, schemaName, tableName, null);

						while (rsColumn.next()) {
							String colName = rsColumn.getString("COLUMN_NAME");

							String colType = rsColumn.getString("TYPE_NAME");

							int nullable = rsColumn.getInt("NULLABLE");
							String colRemarks = rsColumn.getString("REMARKS");
							System.out.println(colName);
							System.out.println(colType);
							System.out.println(nullable);
							System.out.println(colRemarks);

						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
