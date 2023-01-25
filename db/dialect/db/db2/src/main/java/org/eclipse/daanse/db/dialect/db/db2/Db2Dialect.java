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
package org.eclipse.daanse.db.dialect.db.db2;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import aQute.bnd.annotation.spi.ServiceProvider;

/**
 * Implementation of {@link Dialect} for the IBM DB2 database.
 *
 * @see Db2OldAs400Dialect
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='DB2'",
        "database.product:String='DB2'" })
@Component(service = Dialect.class, scope = ServiceScope.PROTOTYPE)
public class Db2Dialect extends JdbcDialectImpl {

    private static final String SUPPORTED_PRODUCT_NAME = "DB2";

    @Override
    protected boolean isSupportedProduct(String productName, String productVersion) {
        return SUPPORTED_PRODUCT_NAME.equalsIgnoreCase(productVersion);
    }

    @Override
    public StringBuilder toUpper(CharSequence expr) {
        return new StringBuilder("UCASE(").append(expr).append(")");
    }

    @Override
    public boolean supportsGroupingSets() {
        return true;
    }

    @Override
    public StringBuilder quoteDecimalLiteral(
        CharSequence value)
    {
        return new StringBuilder("FLOAT(").append(value).append(")");
    }

    @Override
    public String getDialectName() {
        return "db2";
    }

}
