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

import org.eclipse.daanse.mdx.model.api.expression.CallExpression;
import org.eclipse.daanse.mdx.model.api.expression.Expression;
import org.eclipse.daanse.mdx.model.api.expression.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.record.expression.CallExpressionR;
import org.eclipse.daanse.mdx.model.record.expression.CompoundIdR;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpressionTest {

    @Nested
    public class CallExpressionTest {

        @Test
        public void testCallExpressionFunctionWithArrayParam() throws MdxParserException {
            String mdx = "FunctionName([arg1, arg2])";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Function);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(1);
            checkArgument((CallExpressionR) clause, 0, "arg1, arg2");
        }

        @Test
        public void testCallExpressionFunctionWithoutParams() throws MdxParserException {
            String mdx = "FunctionName()";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Function);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(0);
        }

        @Test
        public void testCallExpressionFunctionWithOneParam() throws MdxParserException {
            String mdx = "FunctionName(arg)";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Function);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(1);
            checkArgument((CallExpressionR) clause, 0, "arg");
        }

        @Test
        public void testCallExpressionFunctionWithSeveralParams() throws MdxParserException {
            String mdx = "FunctionName(arg1, arg2)";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Function);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(2);
            checkArgument((CallExpressionR) clause, 0, "arg1");
            checkArgument((CallExpressionR) clause, 1, "arg2");
        }

        @Test
        public void testCallExpressionFunctionWithSeveralParamsWithArray() throws MdxParserException {
            String mdx = "FunctionName(arg1, [arg2, arg3])";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Function);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(2);
            checkArgument((CallExpressionR) clause, 0, "arg1");
            checkArgument((CallExpressionR) clause, 1, "arg2, arg3");
        }

        @Test
        public void testCallExpressionProperty() throws MdxParserException {
            String mdx = "object.PROPERTY";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Property);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("PROPERTY");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(1);
            checkArgument((CallExpressionR) clause, 0, "object");
        }

        @Test
        public void testCallExpressionPropertyQuoted() throws MdxParserException {
            String mdx = "object.&PROPERTY";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.PropertyQuoted);
        }

        @Test
        public void testCallExpressionPropertyAmpersAndQuoted() throws MdxParserException {
            String mdx = "object.[&PROPERTY";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.PropertyAmpersAndQuoted);
        }

        @Test
        public void testCallExpressionMethod() throws MdxParserException {
            String mdx = "object.FunctionName()";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Method);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(2);
            checkArgument((CallExpressionR) clause, 0, "object");
            CallExpression callExpression = ((CallExpression)(((CallExpressionR) clause).expressions().get(1)));
            assertThat(callExpression.type()).isEqualTo(CallExpression.Type.Empty);
            assertThat(callExpression.name()).isEqualTo("");
            assertThat(callExpression.expressions()).isNotNull().hasSize(0);
        }

        @Test
        public void testCallExpressionMethodWithParameter() throws MdxParserException {
            String mdx = "object.FunctionName(arg)";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Method);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(2);
            checkArgument((CallExpressionR) clause, 0, "object");
            checkArgument((CallExpressionR) clause, 1, "arg");
        }

        @Test
        public void testCallExpressionMethodWithParameterArray() throws MdxParserException {
            String mdx = "object.FunctionName([arg1, arg2])";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Method);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("FunctionName");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(2);
            checkArgument((CallExpressionR) clause, 0, "object");
            checkArgument((CallExpressionR) clause, 1, "arg1, arg2");
        }

        @Test
        public void testCallExpressionMethodWithInnerFunction() throws MdxParserException {
            Expression clause = new MdxParserWrapper("object.FunctionOuter(FunctionInner())").parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Method);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("FunctionOuter");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(2);
            checkArgument((CallExpressionR) clause, 0, "object");
            assertThat(((CallExpressionR) clause).expressions().get(1)).isInstanceOf(CallExpressionR.class);
            CallExpression callExpression = ((CallExpression)(((CallExpressionR) clause).expressions().get(1)));
            assertThat(callExpression.type()).isEqualTo(CallExpression.Type.Function);
            assertThat(callExpression.name()).isEqualTo("FunctionInner");
            assertThat(callExpression.expressions()).isNotNull().hasSize(0);
        }

        @Test
        public void testCallExpressionTermCase() throws MdxParserException {
            String mdx = "CASE a WHEN b THEN c END";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Term_Case);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("_CaseMatch");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(3);
            checkArgument((CallExpressionR) clause, 0, "a");
            checkArgument((CallExpressionR) clause, 1, "b");
            checkArgument((CallExpressionR) clause, 2, "c");
        }

        @Test
        public void testCallExpressionBraces1() throws MdxParserException {
            String mdx = "{ [a][a], [a][b], [a][c] }";
            Expression clause = new MdxParserWrapper("{ expression }").parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Braces);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("{}");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(1);
            checkArgument((CallExpressionR) clause, 0, "expression");
        }

        @Test
        public void testCallExpressionBraces2() throws MdxParserException {
            Expression clause = new MdxParserWrapper("{ expression1, expression2 }").parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Braces);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("{}");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(2);
            checkArgument((CallExpressionR) clause, 0, "expression1");
            checkArgument((CallExpressionR) clause, 1, "expression2");
        }

        @Test
        public void testCallExpressionBraces3() throws MdxParserException {
            Expression clause = new MdxParserWrapper("{ [a] : [c] }").parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Braces);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("{}");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(1);

            assertThat(((CallExpressionR) clause).expressions().get(0)).isInstanceOf(CallExpressionR.class);
            CallExpressionR callExpression = ((CallExpressionR)(((CallExpressionR) clause).expressions().get(0)));
            assertThat(callExpression.type()).isEqualTo(CallExpression.Type.Term_Infix);
            assertThat(callExpression.name()).isEqualTo(":");
            assertThat(callExpression.expressions()).isNotNull().hasSize(2);
            checkArgument(callExpression, 0, "a");
            checkArgument(callExpression, 1, "c");

        }

        @Test
        public void testCallExpressionBraces4() throws MdxParserException {
            Expression clause = new MdxParserWrapper("{ [a].[a], [a].[b], [a].[c] }").parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            CallExpressionR callExpression = ((CallExpressionR) clause);
            assertThat(callExpression.type()).isEqualTo(CallExpression.Type.Braces);
            assertThat(callExpression.name()).isEqualTo("{}");
            assertThat(callExpression.expressions()).hasSize(3);
            assertThat(callExpression.expressions().get(0)).isInstanceOf(CompoundIdR.class);

            CompoundIdR compoundId0 = (CompoundIdR)callExpression.expressions().get(0);
            assertThat(compoundId0.objectIdentifiers()).hasSize(2);
            checkCompoundId(compoundId0, 2, 0, "a");
            checkCompoundId(compoundId0, 2, 1, "a");

            CompoundIdR compoundId1 = (CompoundIdR)callExpression.expressions().get(1);
            assertThat(compoundId1.objectIdentifiers()).hasSize(2);
            checkCompoundId(compoundId1, 2, 0, "a");
            checkCompoundId(compoundId1, 2, 1, "b");

            CompoundIdR compoundId2 = (CompoundIdR)callExpression.expressions().get(2);
            assertThat(compoundId2.objectIdentifiers()).hasSize(2);
            checkCompoundId(compoundId2, 2, 0, "a");
            checkCompoundId(compoundId2, 2, 1, "c");
        }

        @Test
        public void testCallExpressionParentheses() throws MdxParserException {
            String mdx = "( arg1, arg2 )";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Parentheses);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("()");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(2);
            checkArgument((CallExpressionR) clause, 0, "arg1");
            checkArgument((CallExpressionR) clause, 1, "arg2");
        }

        @Test
        public void testCallExpressionParenthesesWithArray() throws MdxParserException {
            String mdx = "( arg1, [arg2, arg3] )";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Parentheses);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("()");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(2);
            checkArgument((CallExpressionR) clause, 0, "arg1");
            checkArgument((CallExpressionR) clause, 1, "arg2, arg3");
        }

        @Test
        public void testCallExpressionTermPostfix() throws MdxParserException {
            String mdx = "arg IS EMPTY";
            Expression clause = new MdxParserWrapper(mdx).parseExpression();
            assertThat(clause).isNotNull().isInstanceOf(CallExpressionR.class);
            assertThat(((CallExpressionR) clause).type()).isEqualTo(CallExpression.Type.Term_Postfix);
            assertThat(((CallExpressionR) clause).name()).isEqualTo("IS EMPTY");
            assertThat(((CallExpressionR) clause).expressions()).hasSize(1);
            checkArgument((CallExpressionR) clause, 0, "arg");
        }

        private void checkArgument(CallExpressionR clause, int index, String arg) {
            assertThat(clause.expressions().get(index)).isInstanceOf(CompoundIdR.class);
            CompoundIdR compoundId = (CompoundIdR) (clause.expressions().get(index));
            checkCompoundId(compoundId, 1, 0, arg);
        }

        private void checkCompoundId(CompoundIdR compoundId, int size, int index, String arg) {
            assertThat(compoundId.objectIdentifiers()).isNotNull().hasSize(size);
            assertThat(compoundId.objectIdentifiers().get(0)).isInstanceOf(NameObjectIdentifier.class);
            assertThat(((NameObjectIdentifier) (compoundId.objectIdentifiers().get(index))).name()).isEqualTo(arg);
        }
    }
}
