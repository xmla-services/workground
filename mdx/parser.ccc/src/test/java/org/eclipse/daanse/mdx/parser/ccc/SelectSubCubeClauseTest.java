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
import org.eclipse.daanse.mdx.model.api.expression.CompoundId;
import org.eclipse.daanse.mdx.model.api.expression.KeyObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.ObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.select.Axis;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAxesClause;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAxisClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClauseName;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClauseStatement;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.daanse.mdx.parser.ccc.SelectSubCubeClauseTest.SelectSubCubeClauseNameTest.checkSelectSubcubeClauseName;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SelectSubCubeClauseTest {

    @Nested
    public class SelectSubCubeClauseNameTest {

        @Test
        public void testUnQuoted() throws MdxParserException {
            SelectSubcubeClause selectSubcubeClause = new MdxParserWrapper("subcube").parseSelectSubcubeClause();
            assertThat(selectSubcubeClause).isNotNull().isInstanceOf(SelectSubcubeClauseName.class);
            SelectSubcubeClauseName selectSubcubeClauseName = (SelectSubcubeClauseName) selectSubcubeClause;
            assertThat(selectSubcubeClauseName.cubeName()).isNotNull();
            assertThat(selectSubcubeClauseName.cubeName().name()).isNotNull().isEqualTo("subcube");
            assertThat(selectSubcubeClauseName.cubeName().quoting()).isNotNull().isEqualTo(ObjectIdentifier.Quoting.UNQUOTED);
        }

        @Test
        public void testQuoted() throws MdxParserException {
            SelectSubcubeClause selectSubcubeClause = new MdxParserWrapper("[subcube]").parseSelectSubcubeClause();
            assertThat(selectSubcubeClause).isNotNull().isInstanceOf(SelectSubcubeClauseName.class);
            checkSelectSubcubeClauseName((SelectSubcubeClauseName) selectSubcubeClause,
                "subcube",
                ObjectIdentifier.Quoting.QUOTED);
        }

        @Test
        public void testEmpty() {
            assertThrows(MdxParserException.class, () -> new MdxParserWrapper("").parseSelectSubcubeClause());
        }

        public static void checkSelectSubcubeClauseName(SelectSubcubeClauseName selectSubcubeClauseName,
                                                        String name,
                                                        ObjectIdentifier.Quoting quoting) {
            assertThat(selectSubcubeClauseName.cubeName()).isNotNull();
            assertThat(selectSubcubeClauseName.cubeName().name()).isNotNull().isEqualTo(name);
            assertThat(selectSubcubeClauseName.cubeName().quoting()).isNotNull().isEqualTo(quoting);
        }
    }

    @Nested
    public class SelectSubCubeClauseStatementTest {

        @Test
        public void testSingleSubCube() throws MdxParserException {
            SelectSubcubeClause selectSubcubeClause = new MdxParserWrapper("(SELECT {[Date].[Calendar].[Calendar Year].&[2001]} ON 0 FROM [Adventure Works])").parseSelectSubcubeClause();
            assertThat(selectSubcubeClause).isNotNull().isInstanceOf(SelectSubcubeClauseStatement.class);
            SelectSubcubeClauseStatement selectSubcubeClauseStatement = (SelectSubcubeClauseStatement) selectSubcubeClause;

            assertThat(selectSubcubeClauseStatement.selectQueryClause()).isNotNull();
            assertThat(selectSubcubeClauseStatement.selectQueryClause()).isInstanceOf(SelectQueryAxesClause.class);
            SelectQueryAxesClause selectQueryAxesClause = (SelectQueryAxesClause)selectSubcubeClauseStatement.selectQueryClause();
            assertThat(selectQueryAxesClause.selectQueryAxisClauses()).hasSize(1);
            SelectQueryAxisClause selectQueryAxisClause = selectQueryAxesClause.selectQueryAxisClauses().get(0);
            assertThat(selectQueryAxisClause.nonEmpty()).isEqualTo(false);
            assertThat(selectQueryAxisClause.axis()).isNotNull();
            assertThat(selectQueryAxisClause.expression()).isNotNull();
            assertThat(selectQueryAxisClause.selectDimensionPropertyListClause()).isNull();
            Axis axis = selectQueryAxisClause.axis();
            assertThat(axis.named()).isEqualTo(true);
            assertThat(axis.ordinal()).isEqualTo(0);
            assertThat(selectQueryAxisClause.expression()).isInstanceOf(CallExpression.class);
            CallExpression callExpression = (CallExpression) selectQueryAxisClause.expression();
            assertThat(callExpression.name()).isEqualTo("{}");
            assertThat(callExpression.type()).isEqualTo(CallExpression.Type.Braces);
            assertThat(callExpression.expressions()).isNotNull().hasSize(1);
            assertThat(callExpression.expressions().get(0)).isInstanceOf(CompoundId.class);
            CompoundId compoundId = (CompoundId)callExpression.expressions().get(0);
            assertThat(compoundId.objectIdentifiers()).isNotNull().hasSize(4);
            assertThat(compoundId.objectIdentifiers().get(0)).isInstanceOf(NameObjectIdentifier.class);
            assertThat(compoundId.objectIdentifiers().get(1)).isInstanceOf(NameObjectIdentifier.class);
            assertThat(compoundId.objectIdentifiers().get(2)).isInstanceOf(NameObjectIdentifier.class);
            assertThat(compoundId.objectIdentifiers().get(3)).isInstanceOf(KeyObjectIdentifier.class);
            NameObjectIdentifier nameObjectIdentifier0 = (NameObjectIdentifier) compoundId.objectIdentifiers().get(0);
            NameObjectIdentifier nameObjectIdentifier1 = (NameObjectIdentifier) compoundId.objectIdentifiers().get(1);
            NameObjectIdentifier nameObjectIdentifier2 = (NameObjectIdentifier) compoundId.objectIdentifiers().get(2);
            KeyObjectIdentifier keyObjectIdentifier = (KeyObjectIdentifier) compoundId.objectIdentifiers().get(3);
            assertThat(nameObjectIdentifier0.name()).isEqualTo("Date");
            assertThat(nameObjectIdentifier0.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
            assertThat(nameObjectIdentifier1.name()).isEqualTo("Calendar");
            assertThat(nameObjectIdentifier1.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
            assertThat(nameObjectIdentifier2.name()).isEqualTo("Calendar Year");
            assertThat(nameObjectIdentifier2.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
            assertThat(keyObjectIdentifier.nameObjectIdentifiers()).hasSize(1);
            assertThat(keyObjectIdentifier.nameObjectIdentifiers().get(0).name()).isEqualTo("2001");
            assertThat(keyObjectIdentifier.nameObjectIdentifiers().get(0).quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);

            assertThat(selectSubcubeClauseStatement.selectSubcubeClause()).isNotNull().isInstanceOf(SelectSubcubeClauseName.class);
            checkSelectSubcubeClauseName((SelectSubcubeClauseName)selectSubcubeClauseStatement.selectSubcubeClause(),
                "Adventure Works", ObjectIdentifier.Quoting.QUOTED);
            assertThat(selectSubcubeClauseStatement.selectSlicerAxisClause()).isNotNull().isNotPresent();
        }
    }

    @Test
    public void testMultiSubCube() throws MdxParserException {

        SelectSubcubeClause selectSubcubeClause = new MdxParserWrapper("(SELECT {[Date].[Calendar].[Calendar Year].&[2001]} ON 0 FROM (SELECT {test} ON 0 FROM [cube]))").parseSelectSubcubeClause();
        assertThat(selectSubcubeClause).isNotNull().isInstanceOf(SelectSubcubeClauseStatement.class);
        SelectSubcubeClauseStatement selectSubcubeClauseStatement = (SelectSubcubeClauseStatement) selectSubcubeClause;

        assertThat(selectSubcubeClauseStatement.selectQueryClause()).isNotNull();
        assertThat(selectSubcubeClauseStatement.selectQueryClause()).isInstanceOf(SelectQueryAxesClause.class);
        SelectQueryAxesClause selectQueryAxesClause = (SelectQueryAxesClause)selectSubcubeClauseStatement.selectQueryClause();
        assertThat(selectQueryAxesClause.selectQueryAxisClauses()).hasSize(1);
        SelectQueryAxisClause selectQueryAxisClause = selectQueryAxesClause.selectQueryAxisClauses().get(0);
        assertThat(selectQueryAxisClause.nonEmpty()).isEqualTo(false);
        assertThat(selectQueryAxisClause.axis()).isNotNull();
        assertThat(selectQueryAxisClause.expression()).isNotNull();
        assertThat(selectQueryAxisClause.selectDimensionPropertyListClause()).isNull();
        Axis axis = selectQueryAxisClause.axis();
        assertThat(axis.named()).isEqualTo(true);
        assertThat(axis.ordinal()).isEqualTo(0);
        assertThat(selectQueryAxisClause.expression()).isInstanceOf(CallExpression.class);
        CallExpression callExpression = (CallExpression) selectQueryAxisClause.expression();
        assertThat(callExpression.name()).isEqualTo("{}");
        assertThat(callExpression.type()).isEqualTo(CallExpression.Type.Braces);
        assertThat(callExpression.expressions()).isNotNull().hasSize(1);
        assertThat(callExpression.expressions().get(0)).isInstanceOf(CompoundId.class);
        CompoundId compoundId = (CompoundId)callExpression.expressions().get(0);
        assertThat(compoundId.objectIdentifiers()).isNotNull().hasSize(4);
        assertThat(compoundId.objectIdentifiers().get(0)).isInstanceOf(NameObjectIdentifier.class);
        assertThat(compoundId.objectIdentifiers().get(1)).isInstanceOf(NameObjectIdentifier.class);
        assertThat(compoundId.objectIdentifiers().get(2)).isInstanceOf(NameObjectIdentifier.class);
        assertThat(compoundId.objectIdentifiers().get(3)).isInstanceOf(KeyObjectIdentifier.class);
        NameObjectIdentifier nameObjectIdentifier0 = (NameObjectIdentifier) compoundId.objectIdentifiers().get(0);
        NameObjectIdentifier nameObjectIdentifier1 = (NameObjectIdentifier) compoundId.objectIdentifiers().get(1);
        NameObjectIdentifier nameObjectIdentifier2 = (NameObjectIdentifier) compoundId.objectIdentifiers().get(2);
        KeyObjectIdentifier keyObjectIdentifier = (KeyObjectIdentifier) compoundId.objectIdentifiers().get(3);
        assertThat(nameObjectIdentifier0.name()).isEqualTo("Date");
        assertThat(nameObjectIdentifier0.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
        assertThat(nameObjectIdentifier1.name()).isEqualTo("Calendar");
        assertThat(nameObjectIdentifier1.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
        assertThat(nameObjectIdentifier2.name()).isEqualTo("Calendar Year");
        assertThat(nameObjectIdentifier2.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
        assertThat(keyObjectIdentifier.nameObjectIdentifiers()).hasSize(1);
        assertThat(keyObjectIdentifier.nameObjectIdentifiers().get(0).name()).isEqualTo("2001");
        assertThat(keyObjectIdentifier.nameObjectIdentifiers().get(0).quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);

        assertThat(selectSubcubeClauseStatement.selectSubcubeClause()).isNotNull().isInstanceOf( SelectSubcubeClauseStatement.class);
        SelectSubcubeClauseStatement selectSubcubeClauseStatementInner = (SelectSubcubeClauseStatement)selectSubcubeClauseStatement.selectSubcubeClause();
        assertThat(selectSubcubeClauseStatementInner.selectSubcubeClause()).isNotNull();

        SelectQueryAxesClause selectQueryAxesClauseInner = (SelectQueryAxesClause)selectSubcubeClauseStatementInner.selectQueryClause();
        assertThat(selectQueryAxesClauseInner.selectQueryAxisClauses()).hasSize(1);
        SelectQueryAxisClause selectQueryAxisClauseInner = selectQueryAxesClauseInner.selectQueryAxisClauses().get(0);
        assertThat(selectQueryAxisClauseInner.nonEmpty()).isEqualTo(false);
        assertThat(selectQueryAxisClauseInner.axis()).isNotNull();
        assertThat(selectQueryAxisClauseInner.expression()).isNotNull();
        assertThat(selectQueryAxisClauseInner.selectDimensionPropertyListClause()).isNull();
        Axis axisInner = selectQueryAxisClauseInner.axis();
        assertThat(axisInner.named()).isEqualTo(true);
        assertThat(axisInner.ordinal()).isEqualTo(0);

        CallExpression callExpressionInner = (CallExpression) selectQueryAxisClauseInner.expression();
        assertThat(callExpressionInner.name()).isEqualTo("{}");
        assertThat(callExpressionInner.type()).isEqualTo(CallExpression.Type.Braces);
        assertThat(callExpressionInner.expressions()).isNotNull().hasSize(1);
        assertThat(callExpressionInner.expressions().get(0)).isInstanceOf(CompoundId.class);
        CompoundId compoundIdInner = (CompoundId)callExpressionInner.expressions().get(0);
        assertThat(compoundIdInner.objectIdentifiers()).isNotNull().hasSize(1);
        assertThat(compoundIdInner.objectIdentifiers().get(0)).isInstanceOf(NameObjectIdentifier.class);
        NameObjectIdentifier nameObjectIdentifierInner = (NameObjectIdentifier) compoundIdInner.objectIdentifiers().get(0);
        assertThat(nameObjectIdentifierInner.name()).isEqualTo("test");
        assertThat(nameObjectIdentifierInner.quoting()).isEqualTo(ObjectIdentifier.Quoting.UNQUOTED);

        assertThat(selectSubcubeClauseStatementInner.selectSlicerAxisClause()).isNotNull().isNotPresent();

        assertThat(selectSubcubeClauseStatementInner.selectSubcubeClause()).isNotNull().isInstanceOf(SelectSubcubeClauseName.class);
        checkSelectSubcubeClauseName((SelectSubcubeClauseName)selectSubcubeClauseStatementInner.selectSubcubeClause(),
            "cube", ObjectIdentifier.Quoting.QUOTED);
    }

}
