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
package org.eclipse.daanse.db.jdbc.util.impl;

import org.eclipse.daanse.db.dialect.api.Dialect;

public class Type {

    /**
     * The name of this type. Immutable, and independent of the RDBMS.
     */
    public final String name;

    public final Type Integer = new Type("INTEGER");
    public final Type Currency = new Type("DECIMAL(10,4)");
    public final Type Smallint = new Type("SMALLINT");
    public final Type Varchar30 = new Type("VARCHAR(30)");
    public final Type Varchar255 = new Type("VARCHAR(255)");
    public final Type Varchar60 = new Type("VARCHAR(60)");
    public final Type Real = new Type("REAL");
    public final Type Boolean = new Type("BOOLEAN");
    public final Type Bigint = new Type("BIGINT");
    public final Type Date = new Type("DATE");
    public final Type Timestamp = new Type("TIMESTAMP");

    public Type(String name) {
        this.name = name;
    }

    /**
     * Returns the physical type which a given RDBMS (dialect) uses to represent
     * this logical type.
     */
    public String toPhysical(Dialect dialect) {
        //TODO move logic to dialect
        if (this == Integer || this == Currency || this == Smallint || this == Varchar30 || this == Varchar60
            || this == Varchar255 || this == Real) {
            return name;
        }
        if (this == Boolean) {
            switch (dialect.getDialectName()) {
                case "POSTGRES":
                case "GREENPLUM":
                case "LUCIDDB":
                case "NETEZZA":
                case "HSQLDB":
                    return name;
                case "MARIADB":
                case "MYSQL":
                case "INFOBRIGHT":
                    return "TINYINT(1)";
                case "MSSQL":
                case "SYBASE":
                    return "BIT";
                default:
                    return Smallint.name;
            }
        }
        if (this == Bigint) {
            switch (dialect.getDialectName()) {
                case "ORACLE":
                case "FIREBIRD":
                    return "DECIMAL(15,0)";
                default:
                    return name;
            }
        }
        if (this == Date) {
            switch (dialect.getDialectName()) {
                case "MSSQL":
                    return "DATETIME";
                case "INGRES":
                    return "INGRESDATE";
                default:
                    return name;
            }
        }
        if (this == Timestamp) {
            switch (dialect.getDialectName()) {
                case "MSSQL":
                case "MARIADB":
                case "MYSQL":
                case "INFOBRIGHT":
                case "SYBASE":
                    return "DATETIME";
                case "INGRES":
                    return "INGRESDATE";
                case "INFORMIX":
                    return "DATETIME YEAR TO FRACTION(1)";
                default:
                    return name;
            }
        }
        throw new AssertionError("unexpected type: " + name);
    }
}



