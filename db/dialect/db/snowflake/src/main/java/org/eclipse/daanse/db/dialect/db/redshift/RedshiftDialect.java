/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2020 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.db.dialect.db.redshift;

import java.sql.Connection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.DialectUtil;
import org.eclipse.daanse.db.dialect.db.common.Util;
import org.eclipse.daanse.db.dialect.db.postgresql.PostgreSqlDialect;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import aQute.bnd.annotation.spi.ServiceProvider;

/**
 * User: cboyden Date: 2/8/13
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='POSTGRESQL'",
        "database.product:String='REDSHIFT'" })
@Component(service = Dialect.class, scope = ServiceScope.PROTOTYPE)

public class RedshiftDialect extends PostgreSqlDialect {
    private static final String SUPPORTED_PRODUCT_NAME = "REDSHIFT";

    @Override
    protected boolean isSupportedProduct(String productName, String productVersion) {
        return SUPPORTED_PRODUCT_NAME.equalsIgnoreCase(productVersion);
    }

    @Override
    public boolean initialize(Connection connection) {
        return super.initialize(connection) && isDatabase(SUPPORTED_PRODUCT_NAME, connection);
    }

    @Override
    public StringBuilder generateInline(List<String> columnNames, List<String> columnTypes, List<String[]> valueList) {
        return generateInlineGeneric(columnNames, columnTypes, valueList, null, false);
    }

    @Override
    public void quoteStringLiteral(StringBuilder buf, String value) {
        // '\' to '\\'
        Util.singleQuoteString(value.replace("\\\\", "\\\\\\\\"), buf);
    }

    @Override
    public boolean allowsRegularExpressionInWhereClause() {
        return true;
    }

    @Override
    public StringBuilder generateRegularExpression(String source, String javaRegex) {
        try {
            Pattern.compile(javaRegex);
        } catch (PatternSyntaxException e) {
            // Not a valid Java regex. Too risky to continue.
            return null;
        }

        // We might have to use case-insensitive matching
        javaRegex = DialectUtil.cleanUnicodeAwareCaseFlag(javaRegex);
        StringBuilder mappedFlags = new StringBuilder();
        String[][] mapping = new String[][] { { "i", "i" } };
        javaRegex = extractEmbeddedFlags(javaRegex, mapping, mappedFlags);
        boolean caseSensitive = true;
        if (mappedFlags.toString()
                .contains("i")) {
            caseSensitive = false;
        }

        // Now build the string.
        final StringBuilder sb = new StringBuilder();
        // https://docs.aws.amazon.com/redshift/latest/dg/REGEXP_INSTR.html
        sb.append("REGEXP_INSTR(");
        sb.append(source);
        sb.append(",");
        quoteStringLiteral(sb, javaRegex);
        sb.append(",1,1,0,");
        sb.append(caseSensitive ? "'c'" : "'i'");
        sb.append(") > 0");

        return sb;
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
