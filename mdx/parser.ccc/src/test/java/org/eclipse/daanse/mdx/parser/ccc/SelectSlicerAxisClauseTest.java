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
import org.eclipse.daanse.mdx.model.api.expression.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.ObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.select.SelectSlicerAxisClause;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SelectSlicerAxisClauseTest {

    @Test
    public void test1() throws MdxParserException {
        Optional<SelectSlicerAxisClause> selectSlicerAxisClauseOption =
            new MdxParserWrapper("WHERE [Measures].[Internet Sales Amount]")
                .parseSelectSlicerAxisClause();
        assertThat(selectSlicerAxisClauseOption).isPresent();
        checkSelectSlicerAxisClause1(selectSlicerAxisClauseOption.get());
    }

    @Test
    public void test2() throws MdxParserException {
        Optional<SelectSlicerAxisClause> selectSlicerAxisClauseOption =
            new MdxParserWrapper("where a=b")
                .parseSelectSlicerAxisClause();
        assertThat(selectSlicerAxisClauseOption).isPresent();
        SelectSlicerAxisClause selectSlicerAxisClause = selectSlicerAxisClauseOption.get();
        assertThat(selectSlicerAxisClause.expression()).isNotNull().isInstanceOf(CallExpression.class);
        CallExpression callExpression = (CallExpression)selectSlicerAxisClause.expression();
        assertThat(callExpression.name()).isEqualTo("=");
        assertThat(callExpression.type()).isEqualTo(CallExpression.Type.Term_Infix);
        assertThat(callExpression.expressions()).isNotNull().hasSize(2);
        assertThat(callExpression.expressions().get(0)).isInstanceOf(CompoundId.class);
        assertThat(callExpression.expressions().get(1)).isInstanceOf(CompoundId.class);
        CompoundId compoundId1 = (CompoundId) callExpression.expressions().get(0);
        CompoundId compoundId2 = (CompoundId) callExpression.expressions().get(1);
        assertThat(compoundId1.objectIdentifiers()).hasSize(1);
        assertThat(compoundId1.objectIdentifiers().get(0).quoting()).isEqualTo(ObjectIdentifier.Quoting.UNQUOTED);
        assertThat(compoundId1.objectIdentifiers().get(0)).isInstanceOf(NameObjectIdentifier.class);
        assertThat(((NameObjectIdentifier)compoundId1.objectIdentifiers().get(0)).name()).isEqualTo("a");

        assertThat(compoundId2.objectIdentifiers()).hasSize(1);
        assertThat(compoundId2.objectIdentifiers().get(0).quoting()).isEqualTo(ObjectIdentifier.Quoting.UNQUOTED);
        assertThat(compoundId2.objectIdentifiers().get(0)).isInstanceOf(NameObjectIdentifier.class);
        assertThat(((NameObjectIdentifier)compoundId2.objectIdentifiers().get(0)).name()).isEqualTo("b");
    }

    @ParameterizedTest
    @ValueSource(strings = { "where ", "where", "" })
    public void testThrows(String where) {
        assertThrows(MdxParserException.class, () -> new MdxParserWrapper(where).parseSelectSlicerAxisClause());
    }

    //WHERE [Measures].[Internet Sales Amount]
    public static void checkSelectSlicerAxisClause1(SelectSlicerAxisClause selectSlicerAxisClause) {
        assertThat(selectSlicerAxisClause.expression()).isNotNull().isInstanceOf(CompoundId.class);
        CompoundId compoundId = (CompoundId) selectSlicerAxisClause.expression();
        assertThat(compoundId.objectIdentifiers()).isNotNull().hasSize(2);
        assertThat(compoundId.objectIdentifiers().get(0)).isNotNull().isInstanceOf(NameObjectIdentifier.class);
        assertThat(compoundId.objectIdentifiers().get(1)).isNotNull().isInstanceOf(NameObjectIdentifier.class);
        NameObjectIdentifier nameObjectIdentifier1 = (NameObjectIdentifier) compoundId.objectIdentifiers().get(0);
        NameObjectIdentifier nameObjectIdentifier2 = (NameObjectIdentifier) compoundId.objectIdentifiers().get(1);
        assertThat(nameObjectIdentifier1.name()).isEqualTo("Measures");
        assertThat(nameObjectIdentifier1.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
        assertThat(nameObjectIdentifier2.name()).isEqualTo("Internet Sales Amount");
        assertThat(nameObjectIdentifier2.quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
    }
}