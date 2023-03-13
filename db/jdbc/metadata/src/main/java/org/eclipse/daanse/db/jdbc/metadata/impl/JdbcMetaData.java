/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.db.jdbc.metadata.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class JdbcMetaData {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcMetaData.class);
    Connection conn;
    DatabaseMetaData md = null;

    public static final String LEVEL_SEPARATOR = "->";

    private String errMsg = null;
    private Database db = new Database();

    public JdbcMetaData(Connection connection) {
        conn = connection;
        try {
            md = conn.getMetaData();
            db.productName = md.getDatabaseProductName();
            db.productVersion = md.getDatabaseProductVersion();
            db.catalogName = conn.getCatalog();

            LOGGER.debug("Catalog name = " + db.catalogName);
            LOGGER.debug("Database Product Name: " + db.productName);
            LOGGER.debug("Database Product Version: " + db.productVersion);
            LOGGER.debug("JdbcMetaData: initConnection - no error");
            setAllSchemas();

        } catch (SQLException e) {
            errMsg =
                e.getClass().getSimpleName() + " : " + e.getLocalizedMessage();
            LOGGER.error("Database connection exception : " + errMsg, e);
        }


    }

    /* set all schemas in the currently connected database */
    private void setAllSchemas() {
        LOGGER.debug("JdbcMetaData: setAllSchemas");

        ResultSet rs = null;
        boolean gotSchema = false;
        try {
            try {
                rs = md.getSchemas(db.catalogName, null);
            } catch (SQLException | AbstractMethodError e) {
                LOGGER.debug("Error retrieving schemas", e);
                //teradata and jtds do not support passing a catalogName
                rs = md.getSchemas();
            }
            while (rs.next()) {
                String schemaName = rs.getString("TABLE_SCHEM");
                DbSchema dbs = new DbSchema();
                dbs.name = schemaName;
                LOGGER.debug("JdbcMetaData: setAllTables - " + dbs.name);
                setAllTables(dbs);
                db.addDbSchema(dbs);
                gotSchema = true;
            }
        } catch (Exception e) {
            LOGGER.debug(
                "Exception : Database does not support schemas." + e
                    .getMessage());
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                // ignore
            }
        }

        if (!gotSchema) {
            LOGGER.debug(
                "JdbcMetaData: setAllSchemas - tables with no schema name");
            DbSchema dbs = new DbSchema();
            dbs.name = null;    // tables with no schema name
            setAllTables(dbs);
            db.addDbSchema(dbs);
        }
    }

    /* set all tables in the currently connected database */
    private void setAllTables(DbSchema dbs) {
        LOGGER.debug("JdbcMetaData: Loading schema: '" + dbs.name + "'");
        ResultSet rs = null;
        try {
            // Tables and views can be used
            try {
                rs = md.getTables(
                    db.catalogName,
                    dbs.name,
                    null,
                    new String[]{"TABLE", "VIEW"});
            } catch (Exception e) {
                // this is a workaround for databases that throw an exception
                // when views are requested.
                rs = md.getTables(
                    db.catalogName, dbs.name, null, new String[]{"TABLE"});
            }
            ArrayList<String> tableNames = new ArrayList<>();
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                // Oracle 10g Driver returns bogus BIN$ tables that cause
                // exceptions
                if (tableName.matches("(?!BIN\\$).+")) {
                    tableNames.add(tableName);
                }
            }
            tableNames.stream().parallel().forEach(
                (tbname) -> {
                    DbTable dbt = null;

                    // Note: Imported keys are foreign keys which are primary keys
                    // of in some other tables; Exported keys are primary keys which
                    // are referenced as foreign keys in other tables.
                    try {
                        ResultSet rsForeignKeys =
                            md.getImportedKeys(db.catalogName, dbs.name, tbname);
                        try {
                            if (rsForeignKeys.next()) {
                                dbt = new FactTable();
                                do {
                                    ((FactTable) dbt).addFks(
                                        rsForeignKeys.getString("FKCOLUMN_NAME"),
                                        rsForeignKeys.getString("pktable_name"));
                                } while (rsForeignKeys.next());
                            } else {
                                dbt = new DbTable();
                            }
                        } finally {
                            try {
                                rsForeignKeys.close();
                            } catch (Exception e) {
                                // ignore
                            }
                        }
                    } catch (Exception e) {
                        // this fails in some cases (Redshift)
                        LOGGER.warn("unable to process foreign keys", e);
                        if (dbt == null) {
                            dbt = new FactTable();
                        }
                    }
                    dbt.schemaName = dbs.name;
                    dbt.name = tbname;
                    setPKey(dbt);
                    // Lazy loading
                    // setColumns(dbt);
                    dbs.addDbTable(dbt);
                    db.addDbTable(dbt);
                }
            );
        } catch (Exception e) {
            LOGGER.error("setAllTables", e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    /**
     * Gets the Primary key name for a given table name.
     * This key may be a  composite key made of multiple columns.
     */
    private void setPKey(DbTable dbt) {
        ResultSet rs = null;
        try {
            rs = md.getPrimaryKeys(db.catalogName, dbt.schemaName, dbt.name);
            if (rs.next()) {
                //   // a column may have been given a primary key name
                //===dbt.pk = rs.getString("PK_NAME");
                // We need the column name which is primary key for the given
                // table.
                dbt.pk = rs.getString("column_name");
            }
        } catch (Exception e) {
            LOGGER.error("setPKey", e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    private void setColumns(String schemaName, String tableName) {
        LOGGER.debug(
            "setColumns: <" + tableName + "> in schema <" + schemaName + ">");
        DbTable dbt = db.getTable(schemaName, tableName);
        if (dbt == null) {
            LOGGER.debug(
                "No table with name: <"
                    + tableName
                    + "> in schema <"
                    + schemaName
                    + ">");
            return;
        }
        setColumns(dbt);
        LOGGER.debug("got " + dbt.colsDataType.size() + " columns");
    }

    /**
     * Gets all columns for a given table name.
     * <p>
     * Assumes that the caller has acquired a connection using
     */
    private void setColumns(DbTable dbt) {
        ResultSet rs = null;
        try {
            rs = md.getColumns(db.catalogName, dbt.schemaName, dbt.name, null);
            while (rs.next()) {
                DbColumn col = new DbColumn();

                col.dataType = rs.getInt("DATA_TYPE");
                col.name = rs.getString("COLUMN_NAME");
                col.typeName = rs.getString("TYPE_NAME");
                col.columnSize = getSafeInt(rs, "COLUMN_SIZE");
                col.decimalDigits = getSafeInt(rs, "DECIMAL_DIGITS");

                dbt.addColsDataType(col);
            }
        } catch (Exception e) {
            LOGGER.error("setColumns", e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    /**
     * Some columns in JDBC can return null on certain primitive type methods.
     * This is unfortunate, as it forces us to dance around the issue like so.
     * Will check wasNull(). Returns 0 if the value was null.
     */
    private static int getSafeInt(ResultSet rs, String columnName)
        throws SQLException {
        try {
            return rs.getInt(columnName);
        } catch (Exception e) {
            if (rs.wasNull()) {
                return 0;
            }
            throw new SQLException(e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // The following functions provide an interface to JdbcMetaData class to
    // retrieve the meta data details

    public List<String> getAllSchemas() {
        return db.getAllSchemas();
    }

    /**
     * Returns all tables in a given schema.
     */
    public List<String> getAllTables(String schemaName) {
        return db.getAllTables(schemaName);
    }

    public boolean isTableExists(String schemaName, String tableName) {
        if (tableName == null) {
            return true;
        } else {
            return db.tableExists(schemaName, tableName);
        }
    }

    public boolean isColExists(
        String schemaName, String tableName, String colName
    ) {
        if (tableName == null || colName == null) {
            return true;
        } else {
            if (!db.hasColumns(schemaName, tableName)) {
                setColumns(schemaName, tableName);
            }
            return db.colExists(schemaName, tableName, colName);
        }
    }

    // get column data type of given table and its col
    public int getColumnDataType(
        String schemaName, String tableName, String colName
    ) {
        if (tableName == null || colName == null) {
            return -1;
        } else {
            if (!db.hasColumns(schemaName, tableName)) {
                setColumns(schemaName, tableName);
            }
            return db.getColumnDataType(schemaName, tableName, colName);
        }
    }

    public String getErrMsg() {
        return errMsg;
    }

    /**
     * Database metadata.
     */
    class Database {

        String catalogName = ""; // database name.
        String productName = "Unknown";
        String productVersion = "";

        // list of all schemas in database
        Map<String, DbSchema> schemas = new TreeMap<>();
        // ordered collection, allows duplicates and null
        Map<String, TableTracker> tables = new ConcurrentSkipListMap<>();
        // list of all tables in all schemas in database

        List<String> allSchemas;

        private void addDbSchema(DbSchema dbs) {
            schemas.put(
                dbs.name != null
                    ? dbs.name
                    : "", dbs);
        }

        class TableTracker {

            List<DbTable> namedTable = new ArrayList<DbTable>();

            public void add(DbTable table) {
                namedTable.add(table);
            }

            public int count() {
                return namedTable.size();
            }
        }

        private void addDbTable(DbTable dbs) {
            TableTracker tracker = tables.get(dbs.name);

            if (tracker == null) {
                tracker = new TableTracker();
                tables.put(dbs.name, tracker);
            }
            tracker.add(dbs);
        }

        private DbSchema getSchema(String schemaName) {
            return schemas.get(
                schemaName != null
                    ? schemaName
                    : "");
        }

        private List<String> getAllSchemas() {
            if (allSchemas == null) {
                allSchemas = new ArrayList<String>();

                allSchemas.addAll(schemas.keySet());
            }
            return allSchemas;
        }

        private boolean tableExists(String sname, String tableName) {
            return getTable(sname, tableName) != null;
        }

        private DbTable getTable(String sname, String tableName) {
            if (sname == null || sname.equals("")) {
                TableTracker t = tables.get(tableName);
                if (t != null) {
                    return t.namedTable.get(0);
                } else {
                    return null;
                }
            } else {
                DbSchema s = schemas.get(sname);

                if (s == null) {
                    return null;
                }

                return s.getTable(tableName);
            }
        }

        private boolean hasColumns(String schemaName, String tableName) {
            DbTable table = getTable(schemaName, tableName);
            if (table != null) {
                return table.hasColumns();
            }
            return false;
        }

        private boolean colExists(
            String sname, String tableName, String colName
        ) {
            DbTable t = getTable(sname, tableName);

            if (t == null) {
                return false;
            }

            return t.getColumn(colName) != null;
        }

        private List<String> getAllTables(String sname) {
            return getAllTables(sname, false);
        }

        private List<String> getAllTables(String sname, boolean factOnly) {
            List<String> v = new ArrayList<String>();

            if (sname == null || sname.equals("")) {
                // return a list of "schemaname -> table name" string objects
                for (TableTracker tt : tables.values()) {
                    for (DbTable t : tt.namedTable) {
                        if (!factOnly || (factOnly && t instanceof FactTable)) {
                            if (t.schemaName == null) {
                                v.add(t.name);
                            } else {
                                v.add(t.schemaName + LEVEL_SEPARATOR + t.name);
                            }
                        }
                    }
                }
            } else {
                // return a list of "tablename" string objects
                DbSchema s = getSchema(sname);

                if (s != null) {
                    for (DbTable t : s.tables.values()) {
                        if (!factOnly || (factOnly && t instanceof FactTable)) {
                            v.add(t.name);
                        }
                    }
                }
            }
            return v;
        }

        private int getColumnDataType(
            String sname, String tableName, String colName
        ) {
            DbColumn result = getColumnDefinition(sname, tableName, colName);

            if (result == null) {
                return -1;
            }

            return result.dataType;
        }

        private DbColumn getColumnDefinition(
            String sname, String tableName, String colName
        ) {
            DbTable t = getTable(sname, tableName);

            if (t == null) {
                return null;
            }
            return t.colsDataType.get(colName);
        }
    }

    class DbSchema {

        String name;
        /**
         * ordered collection, allows duplicates and null
         */
        final Map<String, DbTable> tables = new ConcurrentSkipListMap<>();

        private DbTable getTable(String tableName) {
            return tables.get(tableName);
        }

        private void addDbTable(DbTable dbt) {
            tables.put(dbt.name, dbt);
        }
    }

    public class DbColumn {

        public String name;
        public int dataType;
        public String typeName;
        public int columnSize;
        public int decimalDigits;

    }

    class DbTable {

        String schemaName;
        String name;
        String pk;
        /**
         * sorted map key=column, value=data type of column
         */
        final Map<String, DbColumn> colsDataType =
            new TreeMap<String, DbColumn>();

        private void addColsDataType(DbColumn columnDefinition) {
            colsDataType.put(columnDefinition.name, columnDefinition);
        }

        private DbColumn getColumn(String cname) {
            return colsDataType.get(cname);
        }

        private boolean hasColumns() {
            return colsDataType.size() > 0;
        }
    }

    class FactTable extends DbTable {

        /**
         * Sorted map key = foreign key col, value=primary key table associated
         * with this fk.
         */
        final Map<String, String> fks = new TreeMap<String, String>();

        private void addFks(String fk, String pkt) {
            fks.put(fk, pkt);
        }
    }
}
