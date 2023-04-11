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
import org.eclipse.daanse.mdx.model.api.expression.CompoundId;
import org.eclipse.daanse.mdx.model.api.expression.ObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAxesClause;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAxisClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClauseName;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClauseStatement;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.daanse.mdx.parser.ccc.MdxTestUtils.checkAxis;
import static org.eclipse.daanse.mdx.parser.ccc.MdxTestUtils.checkNameObjectIdentifiers;
import static org.eclipse.daanse.mdx.parser.ccc.MdxTestUtils.checkSelectSubcubeClauseName;

class SelectStatementTest {

    @Test
    void testSimpleStatement() throws MdxParserException {
        String mdx = """
            SELECT [Customer].[Gender].[Gender].Membmers ON COLUMNS,
                     {[Customer].[Customer].[Aaron A. Allen],
                      [Customer].[Customer].[Abigail Clark]} ON ROWS
               FROM [Adventure Works]
               WHERE [Measures].[Internet Sales Amount]
            """;

        SelectStatement selectStatement = new MdxParserWrapper(mdx).parseSelectStatement();
        assertThat(selectStatement).isNotNull();
        assertThat(selectStatement.selectWithClauses()).isEmpty();
        assertThat(selectStatement.selectQueryClause()).isNotNull().isInstanceOf(SelectQueryAxesClause.class);
        SelectQueryAxesClause selectQueryAxesClause = (SelectQueryAxesClause) selectStatement.selectQueryClause();
        assertThat(selectQueryAxesClause.selectQueryAxisClauses()).hasSize(2);
        SelectQueryAxisClause selectQueryAxisClause1 = selectQueryAxesClause.selectQueryAxisClauses().get(0);
        SelectQueryAxisClause selectQueryAxisClause2 = selectQueryAxesClause.selectQueryAxisClauses().get(1);
        checkSelectQueryAxisClause1(selectQueryAxisClause1);
        checkSelectQueryAxisClause2(selectQueryAxisClause2);
        checkSelectSubcubeClauseName(selectStatement.selectSubcubeClause(), "Adventure Works",
            ObjectIdentifier.Quoting.QUOTED);
        assertThat(selectStatement.selectSlicerAxisClause()).isPresent();
        assertThat(selectStatement.selectSlicerAxisClause().get().expression())
            .isNotNull().isInstanceOf(CompoundId.class);
        CompoundId compoundId = (CompoundId) selectStatement.selectSlicerAxisClause().get().expression();
        assertThat(compoundId.objectIdentifiers()).hasSize(2);
        checkNameObjectIdentifiers(compoundId.objectIdentifiers(), 0, "Measures", ObjectIdentifier.Quoting.QUOTED);
        checkNameObjectIdentifiers(
            compoundId.objectIdentifiers(), 1, "Internet Sales Amount", ObjectIdentifier.Quoting.QUOTED
        );
    }

    //{[Customer].[Customer].[Aaron A. Allen],[Customer].[Customer].[Abigail Clark]} ON ROWS
    private void checkSelectQueryAxisClause2(SelectQueryAxisClause selectQueryAxisClause2) {
        assertThat(selectQueryAxisClause2.nonEmpty()).isFalse();
        checkAxis(selectQueryAxisClause2.axis(), 1, true);
        assertThat(selectQueryAxisClause2.selectDimensionPropertyListClause()).isNull();
        assertThat(selectQueryAxisClause2.expression()).isNotNull().isInstanceOf(CallExpression.class);
        CallExpression callExpression = (CallExpression) selectQueryAxisClause2.expression();
        assertThat(callExpression.name()).isEqualTo("{}");
        assertThat(callExpression.type()).isEqualTo(CallExpression.Type.Braces);
        assertThat(callExpression.expressions()).hasSize(2);
        assertThat(callExpression.expressions().get(0)).isNotNull().isInstanceOf(CompoundId.class);
        assertThat(callExpression.expressions().get(1)).isNotNull().isInstanceOf(CompoundId.class);
        CompoundId compoundId1 = (CompoundId) callExpression.expressions().get(0);
        CompoundId compoundId2 = (CompoundId) callExpression.expressions().get(1);
        assertThat(compoundId1.objectIdentifiers()).hasSize(3);
        assertThat(compoundId2.objectIdentifiers()).hasSize(3);

        checkNameObjectIdentifiers(compoundId1.objectIdentifiers(), 0, "Customer", ObjectIdentifier.Quoting.QUOTED);
        checkNameObjectIdentifiers(compoundId1.objectIdentifiers(), 1, "Customer", ObjectIdentifier.Quoting.QUOTED);
        checkNameObjectIdentifiers(compoundId1.objectIdentifiers(), 2, "Aaron A. Allen",
            ObjectIdentifier.Quoting.QUOTED);
        checkNameObjectIdentifiers(compoundId2.objectIdentifiers(), 0, "Customer", ObjectIdentifier.Quoting.QUOTED);
        checkNameObjectIdentifiers(compoundId2.objectIdentifiers(), 1, "Customer", ObjectIdentifier.Quoting.QUOTED);
        checkNameObjectIdentifiers(compoundId2.objectIdentifiers(), 2, "Abigail Clark",
            ObjectIdentifier.Quoting.QUOTED);
    }

    //[Customer].[Gender].[Gender].Membmers ON COLUMNS,
    private void checkSelectQueryAxisClause1(SelectQueryAxisClause selectQueryAxisClause) {
        assertThat(selectQueryAxisClause.nonEmpty()).isFalse();
        checkAxis(selectQueryAxisClause.axis(), 0, true);
        assertThat(selectQueryAxisClause.selectDimensionPropertyListClause()).isNull();
        assertThat(selectQueryAxisClause.expression()).isNotNull().isInstanceOf(CallExpression.class);
        CallExpression callExpression = (CallExpression) selectQueryAxisClause.expression();
        assertThat(callExpression.name()).isEqualTo("Membmers");
        assertThat(callExpression.type()).isEqualTo(CallExpression.Type.Property);
        assertThat(callExpression.expressions()).hasSize(1);
        assertThat(callExpression.expressions().get(0)).isNotNull().isInstanceOf(CompoundId.class);
        CompoundId compoundId = (CompoundId) callExpression.expressions().get(0);
        assertThat(compoundId.objectIdentifiers()).hasSize(3);
        checkNameObjectIdentifiers(compoundId.objectIdentifiers(), 0, "Customer", ObjectIdentifier.Quoting.QUOTED);
        checkNameObjectIdentifiers(compoundId.objectIdentifiers(), 1, "Gender", ObjectIdentifier.Quoting.QUOTED);
        checkNameObjectIdentifiers(compoundId.objectIdentifiers(), 2, "Gender", ObjectIdentifier.Quoting.QUOTED);
    }

    @Test
    void testDimentionProperties() throws MdxParserException {
        String mdx = """
            SELECT [Store].[Store].Members DIMENSION PROPERTIES [Store].[Store].[Store Name].[Store Type] on 0
            from [Sales]
            """;

        SelectStatement selectStatement = new MdxParserWrapper(mdx).parseSelectStatement();
        assertThat(selectStatement).isNotNull();
        assertThat(selectStatement.selectWithClauses()).isNotNull().isEmpty();
        assertThat(selectStatement.selectQueryClause()).isNotNull().isInstanceOf(SelectQueryAxesClause.class);
        SelectQueryAxesClause selectQueryAxesClause = (SelectQueryAxesClause) selectStatement.selectQueryClause();
        assertThat(selectQueryAxesClause.selectQueryAxisClauses()).isNotNull().hasSize(1);
        assertThat(selectQueryAxesClause.selectQueryAxisClauses().get(0).nonEmpty()).isFalse();
        assertThat(selectQueryAxesClause.selectQueryAxisClauses().get(0).expression())
            .isInstanceOf(CallExpression.class);
        checkAxis(selectQueryAxesClause.selectQueryAxisClauses().get(0).axis(), 0, true);
        assertThat(selectQueryAxesClause.selectQueryAxisClauses().get(0).selectDimensionPropertyListClause().properties()).hasSize(1);
        List<ObjectIdentifier> objectIdentifiers =
            selectQueryAxesClause.selectQueryAxisClauses().get(0).selectDimensionPropertyListClause().properties().get(0).objectIdentifiers();
        assertThat(objectIdentifiers).hasSize(4);
        checkNameObjectIdentifiers(objectIdentifiers, 0, "Store", ObjectIdentifier.Quoting.QUOTED);
        checkNameObjectIdentifiers(objectIdentifiers, 1, "Store", ObjectIdentifier.Quoting.QUOTED);
        checkNameObjectIdentifiers(objectIdentifiers, 2, "Store Name", ObjectIdentifier.Quoting.QUOTED);
        checkNameObjectIdentifiers(objectIdentifiers, 3, "Store Type", ObjectIdentifier.Quoting.QUOTED);

        CallExpression callExpression =
            (CallExpression) selectQueryAxesClause.selectQueryAxisClauses().get(0).expression();
        assertThat(callExpression.name()).isEqualTo("Members");
        assertThat(callExpression.type()).isEqualTo(CallExpression.Type.Property);
        assertThat(callExpression.expressions()).hasSize(1);
        assertThat(callExpression.expressions().get(0)).isInstanceOf(CompoundId.class);
        CompoundId compoundId = (CompoundId) callExpression.expressions().get(0);
        assertThat(compoundId.objectIdentifiers()).hasSize(2);

        checkNameObjectIdentifiers(compoundId.objectIdentifiers(), 0, "Store", ObjectIdentifier.Quoting.QUOTED);
        checkNameObjectIdentifiers(compoundId.objectIdentifiers(), 0, "Store", ObjectIdentifier.Quoting.QUOTED);

        checkSelectSubcubeClauseName(selectStatement.selectSubcubeClause(), "Sales", ObjectIdentifier.Quoting.QUOTED);
        assertThat(selectStatement.selectSlicerAxisClause()).isNotPresent();
        assertThat(selectStatement.selectCellPropertyListClause()).isNotPresent();
    }

    @Test
    void testSubCube() throws MdxParserException {
        String mdx = """
            SELECT
            [Measures].[Internet Sales Amount] on 0,
            [Date].[Calendar].Members on 1
            FROM
            (
            SELECT {[Date].[Calendar].[Month].&[2001]&[7], [Date].[Calendar].[Month].&[2001]&[12]} on 0
            FROM
            (SELECT {[Date].[Calendar].[Calendar Year].&[2001]} ON 0 FROM [Adventure Works])
            )
                """;

        SelectStatement selectStatement = new MdxParserWrapper(mdx).parseSelectStatement();
        assertThat(selectStatement).isNotNull();
        // test sub cubes
        assertThat(selectStatement.selectSubcubeClause()).isNotNull().isInstanceOf(SelectSubcubeClauseStatement.class);
        SelectSubcubeClauseStatement selectSubcubeClauseStatement1 =
            (SelectSubcubeClauseStatement) selectStatement.selectSubcubeClause();
        assertThat(selectSubcubeClauseStatement1.selectSubcubeClause())
            .isNotNull().isInstanceOf(SelectSubcubeClauseStatement.class);
        SelectSubcubeClauseStatement selectSubcubeClauseStatement2 =
            (SelectSubcubeClauseStatement) selectSubcubeClauseStatement1.selectSubcubeClause();
        assertThat(selectSubcubeClauseStatement2.selectSubcubeClause())
            .isNotNull().isInstanceOf(SelectSubcubeClauseName.class);
        SelectSubcubeClauseName selectSubcubeClauseName =
            (SelectSubcubeClauseName) selectSubcubeClauseStatement2.selectSubcubeClause();
        assertThat(selectSubcubeClauseName.cubeName().name()).isEqualTo("Adventure Works");
        assertThat(selectSubcubeClauseName.cubeName().quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
    }

}
