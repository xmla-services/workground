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

import org.eclipse.daanse.mdx.model.api.SelectStatement;
import org.eclipse.daanse.mdx.model.api.expression.CallExpression;
import org.eclipse.daanse.mdx.model.api.expression.Expression;
import org.eclipse.daanse.mdx.model.api.expression.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAsteriskClause;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAxesClause;
import org.eclipse.daanse.mdx.model.record.expression.CallExpressionR;
import org.eclipse.daanse.mdx.model.record.expression.CompoundIdR;
import org.eclipse.daanse.mdx.model.record.select.SelectQueryAsteriskClauseR;
import org.eclipse.daanse.mdx.model.record.select.SelectQueryAxesClauseR;
import org.eclipse.daanse.mdx.model.record.select.SelectQueryEmptyClauseR;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

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

        @Test
        public void testCallExpressionFunctionWithArrayParam() throws MdxParserException {
        	String mdx = "FunctionName([arg1, arg2])";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Function);
            assertThat(((CallExpressionR)clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR)clause).expressions()).hasSize(1);
            checkArgument((CallExpressionR)clause, 0, "arg1, arg2");
        }

        @Test
        public void testCallExpressionFunctionWithoutParams() throws MdxParserException {
        	String mdx = "FunctionName()";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Function);
            assertThat(((CallExpressionR)clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR)clause).expressions()).hasSize(0);
        }

        @Test
        public void testCallExpressionFunctionWithOneParam() throws MdxParserException {
            String mdx = "FunctionName(arg)";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Function);
            assertThat(((CallExpressionR)clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR)clause).expressions()).hasSize(1);
            checkArgument((CallExpressionR)clause, 0, "arg");
        }

        @Test
        public void testCallExpressionFunctionWithSeveralParams() throws MdxParserException {
            String mdx = "FunctionName(arg1, arg2)";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Function);
            assertThat(((CallExpressionR)clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR)clause).expressions()).hasSize(2);
            checkArgument((CallExpressionR)clause, 0, "arg1");
            checkArgument((CallExpressionR)clause, 1, "arg2");
        }

        @Test
        public void testCallExpressionFunctionWithSeveralParamsWithArray() throws MdxParserException {
            String mdx = "FunctionName(arg1, [arg2, arg3])";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Function);
            assertThat(((CallExpressionR)clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR)clause).expressions()).hasSize(2);
            checkArgument((CallExpressionR)clause, 0, "arg1");
            checkArgument((CallExpressionR)clause, 1, "arg2, arg3");
        }

        @Test
        public void testCallExpressionProperty() throws MdxParserException {
        	String mdx = "object.PROPERTY";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Property);
            assertThat(((CallExpressionR)clause).name()).isEqualTo("PROPERTY");
            assertThat(((CallExpressionR)clause).expressions()).hasSize(1);
            checkArgument((CallExpressionR)clause, 0, "object");
        }

        @Test
        public void testCallExpressionPropertyQuoted() throws MdxParserException {
        	String mdx = "object.&PROPERTY";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.PropertyQuoted);
        }

        @Test
        public void testCallExpressionPropertyAmpersAndQuoted() throws MdxParserException {
        	String mdx = "object.[&PROPERTY";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.PropertyAmpersAndQuoted);
        }

        @ParameterizedTest
        @ValueSource(strings = {"object.FunctionName()"})
        public void testCallExpressionMethod(String mdx) throws MdxParserException {
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Method);
            assertThat(((CallExpressionR)clause).name()).isEqualTo("FunctionName");
        }
        
        @Test        
        public void testCallExpressionMethodWithParameter() throws MdxParserException {
        	String mdx = "object.FunctionName(arg)";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Method);
            assertThat(((CallExpressionR)clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR)clause).expressions()).hasSize(2);
            checkArgument((CallExpressionR)clause, 0, "object");
            checkArgument((CallExpressionR)clause, 1, "arg");
        }

        @Test        
        public void testCallExpressionMethodWithParameterArray() throws MdxParserException {
        	String mdx = "object.FunctionName([arg1, arg2])";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Method);
            assertThat(((CallExpressionR)clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR)clause).expressions()).hasSize(2);
            checkArgument((CallExpressionR)clause, 0, "object");
            checkArgument((CallExpressionR)clause, 1, "arg1, arg2");
        }

        
        @Test
        public void testCallExpressionTermCase() throws MdxParserException {
        	String mdx = "CASE a WHEN b THEN c END";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Term_Case);
            assertThat(((CallExpressionR)clause).name()).isEqualTo("_CaseMatch");
            assertThat(((CallExpressionR)clause).expressions()).hasSize(3);            
        }

        @Test
        public void testCallExpressionBraces() throws MdxParserException {
        	String mdx = "{ expression } { expression,expression } { [a][a] : [a][c] } { [a][a], [a][b], [a][c] }";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Braces);
            assertThat(((CallExpressionR)clause).name()).isEqualTo("{}");
        }

        @Test
        public void testCallExpressionParentheses() throws MdxParserException {
        	String mdx = "( arg, arg )";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Parentheses);
            assertThat(((CallExpressionR)clause).name()).isEqualTo("()");
        }

        @Test
        public void testCallExpressionTermPostfix() throws MdxParserException {
        	String mdx = "arg IS EMPTY";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR)clause).type()).isEqualTo(CallExpression.Type.Term_Postfix);
            assertThat(((CallExpressionR)clause).name()).isEqualTo("IS EMPTY");
        }

        private void checkArgument(CallExpressionR clause, int index, String arg) {
            assertThat(clause.expressions().get(index)).isInstanceOf(CompoundIdR.class);
            CompoundIdR compoundId = (CompoundIdR)(clause.expressions().get(index));
            assertThat(compoundId.objectIdentifiers()).isNotNull().hasSize(1);
            assertThat(compoundId.objectIdentifiers().get(0)).isInstanceOf(NameObjectIdentifier.class);
            assertThat(((NameObjectIdentifier)(compoundId.objectIdentifiers().get(0))).name()).isEqualTo(arg);
        }
    }
}
