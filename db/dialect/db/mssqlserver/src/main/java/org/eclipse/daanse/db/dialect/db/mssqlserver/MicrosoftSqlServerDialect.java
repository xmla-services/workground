/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (C) 2012-2017 Hitachi Vantara and others
* All Rights Reserved.
*
* Contributors:
*   SmartCity Jena, Stefan Bischof - make OSGi Component
*/
package org.eclipse.daanse.db.dialect.db.mssqlserver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.eclipse.daanse.db.dialect.db.common.Util;

/**
 * Implementation of {@link Dialect} for the Microsoft SQL Server
 * database.
 *
 * @author jhyde
 * @since Nov 23, 2008
 */
public class MicrosoftSqlServerDialect extends JdbcDialectImpl {

    private final DateFormat df =
        new SimpleDateFormat("yyyyMMdd");

    private static final String SUPPORTED_PRODUCT_NAME = "MSSQL";

    public MicrosoftSqlServerDialect(Connection connection) {
        super(connection);
    }

    @Override
    public StringBuilder generateInline(
        List<String> columnNames,
        List<String> columnTypes,
        List<String[]> valueList)
    {
        return generateInlineGeneric(
            columnNames, columnTypes, valueList, null, false);
    }

    @Override
    public boolean requiresAliasForFromQuery() {
        return true;
    }

    @Override
    public boolean requiresUnionOrderByOrdinal() {
        return false;
    }

    @Override
    public void quoteBooleanLiteral(StringBuilder buf, String value) {
      // avoid padding origin values with blanks to n for char(n),
      // when ANSI_PADDING=ON
      String boolLiteral = value.trim();
      if (!boolLiteral.equalsIgnoreCase("TRUE")
          && !(boolLiteral.equalsIgnoreCase("FALSE"))
          && !(boolLiteral.equalsIgnoreCase("1"))
          && !(boolLiteral.equalsIgnoreCase("0")))
      {
        throw new NumberFormatException(
            "Illegal BOOLEAN literal:  " + value);
      }
      buf.append(Util.singleQuoteString(value));
    }

    @Override
    protected void quoteDateLiteral(StringBuilder buf, Date date)
    {
        buf.append("CONVERT(DATE, '");
        buf.append(df.format(date));
        // Format 112 is equivalent to "yyyyMMdd" in Java.
        // See http://msdn.microsoft.com/en-us/library/ms187928.aspx
        buf.append("', 112)");
    }

    @Override
    protected void quoteTimestampLiteral(
        StringBuilder buf,
        String value,
        Timestamp timestamp)
    {
        buf.append("CONVERT(datetime, '");
        buf.append(timestamp.toString());
        // Format 120 is equivalent to "yyyy-mm-dd hh:mm:ss" in Java.
        // See http://msdn.microsoft.com/en-us/library/ms187928.aspx
        buf.append("', 120)");
    }

    @Override
    public String getDialectName() {
        return SUPPORTED_PRODUCT_NAME.toLowerCase();
    }
}
