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

public enum Type {

    INTEGER("INTEGER"),
    NUMERIC("DECIMAL(15,4)"),
    SMALLINT("SMALLINT"),
    STRING("VARCHAR(255)"),
    BOOLEAN("BOOLEAN"),
    LONG("BIGINT"),
    DATE("DATE"),
    TIMESTAMP("TIMESTAMP"),
    TIME("TIME");

    /**
     * The name of this type. Immutable, and independent of the RDBMS.
     */
    public final String name;

    Type(String name) {
        this.name = name;
    }

    public static Type fromName(String n) {
        if (n == null){
            return Type.STRING;
        }
        for (Type t : Type.values()) {
            if (t.name.equals(n)) {
                return t;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("Type Illegal name ")
            .append(n).toString());
    }

    /**
     * Returns the physical type which a given RDBMS (dialect) uses to represent
     * this logical type.
     */
    public String toPhysical(Dialect dialect) {
        //TODO move logic to dialect
        if (this == INTEGER ||  this == SMALLINT
            || this == STRING) {
            return name;
        }
        if (this == BOOLEAN) {
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
                    return SMALLINT.name;
            }
        }
        if (this == LONG) {
            switch (dialect.getDialectName()) {
                case "ORACLE":
                case "FIREBIRD":
                    return "DECIMAL(15,0)";
                default:
                    return name;
            }
        }
        if (this == DATE) {
            switch (dialect.getDialectName()) {
                case "MSSQL":
                    return "DATETIME";
                case "INGRES":
                    return "INGRESDATE";
                default:
                    return name;
            }
        }
        if (this == TIMESTAMP) {
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



