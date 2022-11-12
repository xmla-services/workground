package org.opencube.junit5.dataloader;

import org.eclipse.daanse.sql.dialect.api.Dialect;
import mondrian.spi.DialectManager;
import org.opencube.junit5.Constants;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class SteelWheelsDataLoader implements DataLoader {
	public static List<String> tables = List.of(
	"customer_w_ter", "customers", "department_managers", "employees", "offices", "orderdetails", "orderfact", "orders",
	"payments", "products", "quadrant_actuals", "time", "trial_balance");

	@Override
	public boolean loadData(DataSource dataSource) throws Exception {
	try (Connection connection = dataSource.getConnection()) {

	    Dialect dialect = DialectManager.createDialect(null, connection);

	    List<String> dropTableSQLs = dropTableSQLs();
	    DataLoaderUtil.executeSql(connection, dropTableSQLs,true);

	    InputStream sqlFile= new FileInputStream(new File(Constants.TESTFILES_DIR + "loader/steelwheel/SteelWheels.mysql.sql"));

	    DataLoaderUtil.loadFromSqlInserts(connection, dialect, sqlFile);

	}
	return true;
    }

	private List<String> dropTableSQLs() {

		return tables.stream().map(t -> dropTableSQL(t)).collect(Collectors.toList());
	}

	private String dropTableSQL(String table) {
		return "DROP TABLE IF EXISTS " + table;
	}
}
