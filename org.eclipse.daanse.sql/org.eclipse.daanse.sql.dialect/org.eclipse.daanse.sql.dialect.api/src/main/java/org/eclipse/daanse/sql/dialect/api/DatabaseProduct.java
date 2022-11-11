package org.eclipse.daanse.sql.dialect.api;

/**
 * Enumeration of common database types.
 *
 * <p>Branching on this enumeration allows you to write code which behaves
 * differently for different databases. However, since the capabilities of
 * a database can change between versions, it is recommended that
 * conditional code is in terms of capabilities methods in
 * {@link Dialect}.
 *
 * <p>Because there are so many differences between various versions and
 * ports of DB2, we represent them as 3 separate products. If you want to
 * treat them all as one product, note that the {@link #getFamily()} method
 * for {@link #DB2_AS400} and {@link #DB2_OLD_AS400} returns {@link #DB2}.
 */
public enum DatabaseProduct {
    ACCESS,
    UNKNOWN,
    CLICKHOUSE,
    DERBY,
    DB2_OLD_AS400,
    DB2_AS400,
    DB2,
    FIREBIRD,
    GREENPLUM,
    HIVE,
    HSQLDB,
    IMPALA,
    INFORMIX,
    INFOBRIGHT,
    INGRES,
    INTERBASE,
    LUCIDDB,
    MSSQL,
    MONETDB,
    NETEZZA,
    NEOVIEW,
    NUODB,
    ORACLE,
    POSTGRESQL,
    REDSHIFT,
    MYSQL,
    SQLSTREAM,
    SYBASE,
    TERADATA,
    VERTICA,
    VECTORWISE,
    MARIADB,
    PDI,
    GOOGLEBIGQUERY,
    SNOWFLAKE;

    /**
     * Return the root of the family of products this database product
     * belongs to.
     *
     * <p>For {@link #DB2_AS400} and {@link #DB2_OLD_AS400} returns
     * {@link #DB2}; for all other database products, returns the same
     * product.
     *
     * @return root of family of database products
     */
    public DatabaseProduct getFamily() {
        switch (this) {
            case DB2_OLD_AS400:
            case DB2_AS400:
                return DB2;
            default:
                return this;
        }
    }

    public static DatabaseProduct getDatabaseProduct(String name) {
        for (DatabaseProduct databaseProduct : values()) {
            if (databaseProduct.name().equalsIgnoreCase(name)) {
                return databaseProduct;
            }
        }
        return UNKNOWN;
    }
}
