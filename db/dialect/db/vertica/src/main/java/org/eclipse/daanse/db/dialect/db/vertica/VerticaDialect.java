/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (c) 2002-2020 Hitachi Vantara..  All rights reserved.
*/
package org.eclipse.daanse.db.dialect.db.vertica;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.daanse.db.dialect.api.BestFitColumnType;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.DialectUtil;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import aQute.bnd.annotation.spi.ServiceProvider;

/**
 * Implementation of {@link Dialect} for the Vertica database.
 *
 * @author Pedro Alves
 * @since Sept 11, 2009
 */
@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='VERTICA'",
        "database.product:String='VERTICA'" })
@Component(service = Dialect.class, scope = ServiceScope.PROTOTYPE)
public class VerticaDialect extends JdbcDialectImpl {

    private static final String SUPPORTED_PRODUCT_NAME = "VERTICA";

    @Override
    protected boolean isSupportedProduct(String productName, String productVersion) {
        return SUPPORTED_PRODUCT_NAME.equalsIgnoreCase(productVersion);
    }

    @Override
    public boolean requiresAliasForFromQuery() {
        return true;
    }

    @Override
    public boolean allowsFromQuery() {
        return true;
    }

    @Override
    public boolean allowsMultipleCountDistinct() {
        return false;
    }

    @Override
    public boolean allowsCountDistinctWithOtherAggs() {
        return false;
    }

    @Override
    public boolean supportsResultSetConcurrency(int type, int concurrency) {
        return false;
    }

    @Override
    public StringBuilder generateInline(List<String> columnNames, List<String> columnTypes, List<String[]> valueList) {
        return generateInlineGeneric(columnNames, columnTypes, valueList, null, false);
    }

    private static final Map<Integer, BestFitColumnType> VERTICA_TYPE_MAP;
    static {
        Map<Integer, BestFitColumnType> typeMapInitial = new HashMap<>();
        typeMapInitial.put(Types.SMALLINT, BestFitColumnType.LONG);
        typeMapInitial.put(Types.TINYINT, BestFitColumnType.LONG);
        typeMapInitial.put(Types.INTEGER, BestFitColumnType.LONG);
        typeMapInitial.put(Types.BOOLEAN, BestFitColumnType.INT);
        typeMapInitial.put(Types.DOUBLE, BestFitColumnType.DOUBLE);
        typeMapInitial.put(Types.FLOAT, BestFitColumnType.DOUBLE);
        typeMapInitial.put(Types.BIGINT, BestFitColumnType.LONG);
        VERTICA_TYPE_MAP = Collections.unmodifiableMap(typeMapInitial);
    }

    @Override
    public BestFitColumnType getType(ResultSetMetaData metaData, int columnIndex) throws SQLException {
        final int columnType = metaData.getColumnType(columnIndex + 1);

        BestFitColumnType internalType = null;
        // all int types in vertica are longs.
        if (columnType == Types.NUMERIC || columnType == Types.DECIMAL) {
            final int precision = metaData.getPrecision(columnIndex + 1);
            final int scale = metaData.getScale(columnIndex + 1);
            if (scale == 0 && precision <= 9) {
                // An int (up to 2^31 = 2.1B) can hold any NUMBER(10, 0) value
                // (up to 10^9 = 1B).
                internalType = BestFitColumnType.INT;
            } else if (scale == 0 && precision <= 19) {
                // An int (up to 2^31 = 2.1B) can hold any NUMBER(10, 0) value
                // (up to 10^9 = 1B).
                internalType = BestFitColumnType.LONG;
            } else {
                internalType = BestFitColumnType.DOUBLE;
            }
        } else {
            internalType = VERTICA_TYPE_MAP.get(columnType);
            if (internalType == null) {
                internalType = BestFitColumnType.OBJECT;
            }
        }
        logTypeInfo(metaData, columnIndex, internalType);
        return internalType;
    }

    @Override
    public boolean supportsMultiValueInExpr() {
        return true;
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

        javaRegex = DialectUtil.cleanUnicodeAwareCaseFlag(javaRegex);
        javaRegex = javaRegex.replace("\\Q", "");
        javaRegex = javaRegex.replace("\\E", "");

        StringBuilder mappedFlags = new StringBuilder();
        // Vertica regex allowed inline modifiers
        // https://www.vertica.com/docs/9.2.x/HTML/Content/Authoring/SQLReferenceManual/Functions/RegularExpressions/REGEXP_LIKE.htm?tocpath=SQL%20Reference%20Manual%7CSQL%20Functions%7CRegular%20Expression%20Functions%7C_____5
        String[][] mapping = new String[][] { { "c", "c" }, { "i", "i" }, { "m", "m" }, { "x", "x" }, { "s", "n" } };
        javaRegex = extractEmbeddedFlags(javaRegex, mapping, mappedFlags);

        final StringBuilder sb = new StringBuilder();
        sb.append(" REGEXP_LIKE ( CAST (");
        sb.append(source);
        sb.append(" AS VARCHAR), ");
        quoteStringLiteral(sb, javaRegex);
        if (mappedFlags.length() > 0) {
            sb.append(", ");
            quoteStringLiteral(sb, mappedFlags.toString());
        }
        sb.append(")");
        return sb;
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
