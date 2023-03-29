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
import org.eclipse.daanse.mdx.model.api.expression.CallExpression;
import org.eclipse.daanse.mdx.model.api.expression.Expression;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAsteriskClause;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAxesClause;
import org.eclipse.daanse.mdx.model.record.expression.CallExpressionR;
import org.eclipse.daanse.mdx.model.record.select.SelectQueryAsteriskClauseR;
import org.eclipse.daanse.mdx.model.record.select.SelectQueryAxesClauseR;
import org.eclipse.daanse.mdx.model.record.select.SelectQueryEmptyClauseR;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SelectQueryClauseTest {

    @Nested
    public class SelectQueryAxesClauseClauseTest {

        @Test
        public void testInStatement() throws MdxParserException {
            String mdx = """
                    SELECT [Customer].[Gender].[Gender].Membmers ON COLUMNS,
                             {[Customer].[Customer].[Aaron A. Allen],
                              [Customer].[Customer].[Abigail Clark]} ON ROWS
                       FROM [c]
                    """;

            SelectStatement selectStatement = new MdxParserWrapper(mdx).parseSelectStatement();
            System.out.println(selectStatement);
            assertThat(selectStatement).isNotNull();

        }

        @Test
        public void testInClauseMultiple() throws MdxParserException {
            String mdx = """
                    [Customer].[Gender].[Gender].Membmers ON COLUMNS,
                            {[Customer].[Customer].[Aaron A. Allen],
                             [Customer].[Customer].[Abigail Clark]} ON ROWS
                    """;
            SelectQueryAxesClause clause = new MdxParserWrapper(mdx).parseSelectQueryAxesClause();
            assertThat(clause).isNotNull()
                    .isInstanceOf(SelectQueryAxesClauseR.class);
        }

        @Test
        public void testInClauseSingle() throws MdxParserException {
            String mdx = "[Customer] ON COLUMNS";
            SelectQueryAxesClause clause = new MdxParserWrapper(mdx).parseSelectQueryAxesClause();
            assertThat(clause).isNotNull()
                    .isInstanceOf(SelectQueryAxesClauseR.class);
        }
    }

    @Nested
    public class SelectQueryEmptyClauseTest {
        @Test
        public void testInStatement() throws MdxParserException {
            String mdx = "SELECT FROM [c]";

            SelectStatement selectStatement = new MdxParserWrapper(mdx).parseSelectStatement();
            assertThat(selectStatement).isNotNull();
            assertThat(selectStatement.selectQueryClause()).isNotNull()
                    .isInstanceOf(SelectQueryEmptyClauseR.class);

        }
    }

    @Nested
    public class SelectQueryAsteriskClauseTest {

        @Test
        public void testAsteriskInStatement() throws MdxParserException {
            String mdx = "SELECT * FROM [c]";

            SelectStatement selectStatement = new MdxParserWrapper(mdx).parseSelectStatement();
            assertThat(selectStatement).isNotNull();
            assertThat(selectStatement.selectQueryClause()).isNotNull()
                    .isInstanceOf(SelectQueryAsteriskClauseR.class);

        }

        @Test
        public void testAsteriskInClause() throws MdxParserException {
            String mdx = "*";
            SelectQueryAsteriskClause clause = new MdxParserWrapper(mdx).parseSelectQueryAsteriskClause();
            assertThat(clause).isNotNull();
        }

        @ParameterizedTest
        @ValueSource(strings = { "FunctionName()", "FunctionName(arg)", "FunctionName([arg1, arg2])"})
        public void testCallExpressionFunction(String mdx) throws MdxParserException {
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Function);
        }

        @ParameterizedTest
        @ValueSource(strings = {"object.PROPERTY"})
        public void testCallExpressionProperty(String mdx) throws MdxParserException {
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Property);
        }

        @ParameterizedTest
        @ValueSource(strings = {"object.&PROPERTY"})
        public void testCallExpressionPropertyQuoted(String mdx) throws MdxParserException {
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.PropertyQuoted);
        }

        @ParameterizedTest
        @ValueSource(strings = {"object.[&PROPERTY"})
        public void testCallExpressionPropertyAmpersAndQuoted(String mdx) throws MdxParserException {
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.PropertyAmpersAndQuoted);
        }

        @ParameterizedTest
        @ValueSource(strings = {"object.FunctionName()", "object.FunctionName([arg1, arg2])", "object.FunctionName(arg)"})
        public void testCallExpressionMethod(String mdx) throws MdxParserException {
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Method);
        }

        @ParameterizedTest
        @ValueSource(strings = {"CASE a WHEN b THEN c END"})
        public void testCallExpressionTermCase(String mdx) throws MdxParserException {
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Term_Case);
        }

        @ParameterizedTest
        @ValueSource(strings = {"{ expression } { expression,expression } { [a][a] : [a][c] } { [a][a], [a][b], [a][c] }"})
        public void testCallExpressionBraces(String mdx) throws MdxParserException {
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Braces);
        }

        @ParameterizedTest
        @ValueSource(strings = {"( arg, arg )"})
        public void testCallExpressionParentheses(String mdx) throws MdxParserException {
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Parentheses);
        }

        @ParameterizedTest
        @ValueSource(strings = {"arg IS EMPTY"})
        public void testCallExpressionTermPostfix(String mdx) throws MdxParserException {
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Term_Postfix);
        }

    }
}
