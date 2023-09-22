/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2012-2020 Hitachi Vantara and others
// All Rights Reserved.
*/
package org.eclipse.daanse.db.dialect.db.impala;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.daanse.db.dialect.db.common.DialectUtil;
import org.eclipse.daanse.db.dialect.db.hive.HiveDialect;

/**
 * Dialect for Cloudera's Impala DB.
 *
 * @author cboyden
 * @since 2/11/13
 */
public class ImpalaDialect extends HiveDialect {

    private static final String ESCAPE_REGEXP = "(\\\\Q([^\\\\Q]+)\\\\E)";
    private static final Pattern escapePattern = Pattern.compile(ESCAPE_REGEXP);

    private static final String SUPPORTED_PRODUCT_NAME = "IMPALA";
    public static final String CAST = "cast(";
    public static final String AS_STRING = " as string)";

    public ImpalaDialect(Connection connection) {
        super(connection);
    }

    @Override
    protected String deduceIdentifierQuoteString(DatabaseMetaData databaseMetaData) {
        return "`";
    }

    @Override
    protected StringBuilder generateOrderByNulls(CharSequence expr, boolean ascending, boolean collateNullsLast) {
        StringBuilder sb = new StringBuilder(expr);
        if (ascending) {
            return sb.append(" ASC");
        } else {
            return sb.append(" DESC");
        }
    }

    @Override
    public StringBuilder generateOrderItem(CharSequence expr, boolean nullable, boolean ascending, boolean collateNullsLast) {
        StringBuilder ret = new StringBuilder();

        if (nullable && collateNullsLast) {
            ret.append("CASE WHEN ").append(expr).append(" IS NULL THEN 1 ELSE 0 END, ");
        } else {
            ret.append("CASE WHEN ").append(expr).append(" IS NULL THEN 0 ELSE 1 END, ");
        }

        if (ascending) {
            ret.append(expr).append(" ASC");
        } else {
            ret.append(expr).append(" DESC");
        }

        return ret;
    }

    @Override
    public boolean allowsMultipleCountDistinct() {
        return false;
    }

    @Override
    public boolean allowsCompoundCountDistinct() {
        return true;
    }

    @Override
    public boolean requiresOrderByAlias() {
        return false;
    }

    @Override
    public boolean requiresAliasForFromQuery() {
        return true;
    }

    @Override
    public boolean supportsGroupByExpressions() {
        return false;
    }

    @Override
    public boolean allowsSelectNotInGroupBy() {
        return false;
    }

    @Override
    public StringBuilder generateInline(List<String> columnNames, List<String> columnTypes, List<String[]> valueList) {
        return generateInlineGeneric(columnNames, columnTypes, valueList, null, false);
    }

    @Override
    public boolean allowsJoinOn() {
        return false;
    }

    @Override
    public void quoteStringLiteral(StringBuilder buf, String value) {
        String quote = "\'";
        String s0 = value;

        if (s0.contains("\\")) {
            s0 = s0.replace("\\\\", "\\\\");
        }
        if (s0.contains(quote)) {
            s0 = s0.replace(quote, "\\\\" + quote);
        }

        buf.append(quote);

        buf.append(s0);

        buf.append(quote);
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
        // We might have to use case-insensitive matching
        StringBuilder mappedFlags = new StringBuilder();
        String[][] mapping = new String[][] { { "i", "i" } };
        javaRegex = extractEmbeddedFlags(javaRegex, mapping, mappedFlags);
        boolean caseSensitive = true;
        if (mappedFlags.toString()
                .contains("i")) {
            caseSensitive = false;
        }
        final Matcher escapeMatcher = escapePattern.matcher(javaRegex);
        while (escapeMatcher.find()) {
            javaRegex = javaRegex.replace(escapeMatcher.group(1), escapeMatcher.group(2));
        }

        final StringBuilder sb = new StringBuilder();
        // Now build the string.
        sb.append(CAST);
        sb.append(source);
        sb.append(AS_STRING);
        sb.append(" IS NOT NULL AND ");
        if (caseSensitive) {
            sb.append(CAST).append(source).append(AS_STRING);
        } else {
            sb.append("UPPER(");
            sb.append(CAST).append(source).append(AS_STRING);
            sb.append(")");
        }
        sb.append(" REGEXP ");
        if (caseSensitive) {
            quoteStringLiteral(sb, javaRegex);
        } else {
            quoteStringLiteral(sb, javaRegex.toUpperCase());
        }
        return sb;
    }

    @Override
    public boolean allowsDdl() {
        return true;
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
