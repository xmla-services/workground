/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.db.dialect.db.teradata;

import java.util.List;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import aQute.bnd.annotation.spi.ServiceProvider;

/**
 * Implementation of {@link Dialect} for the Teradata database.
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='TERADATA'",
        "database.product:String='TERADATA'" })
@Component(service = Dialect.class, scope = ServiceScope.PROTOTYPE)
public class TeradataDialect extends JdbcDialectImpl {

    private static final String SUPPORTED_PRODUCT_NAME = "TERADATA";

    @Override
    protected boolean isSupportedProduct(String productName, String productVersion) {
        return SUPPORTED_PRODUCT_NAME.equalsIgnoreCase(productVersion);
    }

    @Override
    public boolean requiresAliasForFromQuery() {
        return true;
    }

    @Override
    public StringBuilder generateInline(List<String> columnNames, List<String> columnTypes, List<String[]> valueList) {
        String fromClause = null;
        if (valueList.size() > 1) {
            // In Teradata, "SELECT 1,2" is valid but "SELECT 1,2 UNION
            // SELECT 3,4" gives "3888: SELECT for a UNION,INTERSECT or
            // MINUS must reference a table."
            fromClause = " FROM (SELECT 1 a) z ";
        }
        return generateInlineGeneric(columnNames, columnTypes, valueList, fromClause, true);
    }

    @Override
    public boolean supportsGroupingSets() {
        return true;
    }

    @Override
    public boolean requiresUnionOrderByOrdinal() {
        return true;
    }
}
