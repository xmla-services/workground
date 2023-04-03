/*
* Copyright (c) 2023 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.mdx.parser.ccc;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.daanse.mdx.model.api.SelectStatement;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Test;

public class SelectStatementTest {

    @Test
    public void testdefault() throws MdxParserException {
        String mdx = """
                SELECT [Customer].[Gender].[Gender].Membmers ON COLUMNS,
                         {[Customer].[Customer].[Aaron A. Allen],
                          [Customer].[Customer].[Abigail Clark]} ON ROWS
                   FROM [Adventure Works]
                   WHERE [Measures].[Internet Sales Amount]
                """;

        SelectStatement selectStatement = new MdxParserWrapper(mdx).parseSelectStatement();
        assertThat(selectStatement).isNotNull();

    }

    @Test
    public void test1() throws MdxParserException {
        String mdx = """
                SELECT [Store].[Store].Members DIMENSION PROPERTIES [Store].[Store].[Store Name].[Store Type] on 0
                from [Sales]
                """;

        SelectStatement selectStatement = new MdxParserWrapper(mdx).parseSelectStatement();
        assertThat(selectStatement).isNotNull();

    }


    @Test
    public void testSubCube() throws MdxParserException {
        String mdx = """
            SELECT
            [Measures].[Internet Sales Amount] on 0,
            [Date].[Calendar].Members on 1
            FROM\s
            (
            SELECT {[Date].[Calendar].[Month].&[2001]&[7], [Date].[Calendar].[Month].&[2001]&[12]} on 0
            FROM
            (SELECT {[Date].[Calendar].[Calendar Year].&[2001]} ON 0 FROM [Adventure Works])
            )
                """;

        SelectStatement selectStatement = new MdxParserWrapper(mdx).parseSelectStatement();
        assertThat(selectStatement).isNotNull();

    }
}
