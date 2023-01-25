/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.db.dialect.db.sqlstream;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.luciddb.LucidDbDialect;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import aQute.bnd.annotation.spi.ServiceProvider;

/**
 * Implementation of {@link Dialect} for the SQLstream streaming SQL system.
 *
 * @author jhyde
 * @since Mar 23, 2009
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='SQLSTREAM'",
        "database.product:String='SQLSTREAM'" })
@Component(service = Dialect.class, scope = ServiceScope.PROTOTYPE)
public class SqlStreamDialect extends LucidDbDialect {

    private static final String SUPPORTED_PRODUCT_NAME = "SQLSTREAM";

    @Override
    protected boolean isSupportedProduct(String productName, String productVersion) {
        return SUPPORTED_PRODUCT_NAME.equalsIgnoreCase(productVersion);
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
