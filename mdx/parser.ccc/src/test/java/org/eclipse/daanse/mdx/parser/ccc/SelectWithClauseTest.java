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
import org.eclipse.daanse.mdx.model.api.select.CreateSetBodyClause;
import org.eclipse.daanse.mdx.model.api.select.SelectWithClause;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectWithClauseTest {
    @Nested
    public class CreateSetBodyClauseTest {

        @Test
        public void testCreateSetBodyClause() throws MdxParserException {
            String mdx = """
                SET MySet AS
                        Union([Customer].[Gender].Members, {[Customer].[Gender].&[F]})
                """;

            SelectWithClause selectWithClause = new MdxParserWrapper(mdx).parseSelectWithClause();
            assertThat(selectWithClause).isNotNull().isInstanceOf(CreateSetBodyClause.class);
            CreateSetBodyClause createSetBodyClause = (CreateSetBodyClause) selectWithClause;
            assertThat(createSetBodyClause.compoundId()).isNotNull();
            assertThat(createSetBodyClause.compoundId().objectIdentifiers()).hasSize(1);
            assertThat(createSetBodyClause.compoundId().objectIdentifiers().get(0)).isInstanceOf(NameObjectIdentifier.class);
            NameObjectIdentifier nameObjectIdentifier = (NameObjectIdentifier) createSetBodyClause.compoundId().objectIdentifiers().get(0);
            assertThat(nameObjectIdentifier.name()).isEqualTo("MySet");
            assertThat(nameObjectIdentifier.quoting()).isEqualTo(ObjectIdentifier.Quoting.UNQUOTED);

            assertThat(createSetBodyClause.expression()).isNotNull().isInstanceOf(CallExpression.class);
            CallExpression callExpression = (CallExpression) createSetBodyClause.expression();
            assertThat(callExpression.name()).isEqualTo("Union");
            assertThat(callExpression.type()).isEqualTo(CallExpression.Type.Function);
            assertThat(callExpression.expressions()).hasSize(2);
            assertThat(callExpression.expressions().get(0)).isInstanceOf(CallExpression.class);
            assertThat(callExpression.expressions().get(1)).isInstanceOf(CallExpression.class);
            CallExpression callExpression1 = (CallExpression) callExpression.expressions().get(0);
            CallExpression callExpression2 = (CallExpression) callExpression.expressions().get(1);
            assertThat(callExpression1.name()).isEqualTo("Members");
            assertThat(callExpression1.type()).isEqualTo(CallExpression.Type.Property);
            assertThat(callExpression1.expressions()).hasSize(1);
            assertThat(callExpression1.expressions().get(0)).isInstanceOf(CompoundId.class);
            CompoundId compoundId1 = (CompoundId) callExpression1.expressions().get(0);
            assertThat(compoundId1.objectIdentifiers()).hasSize(2);
            assertThat(compoundId1.objectIdentifiers().get(0)).isInstanceOf(NameObjectIdentifier.class);
            assertThat(((NameObjectIdentifier)compoundId1.objectIdentifiers().get(0)).name()).isEqualTo("Customer");
            assertThat((compoundId1.objectIdentifiers().get(0)).quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
            assertThat(compoundId1.objectIdentifiers().get(1)).isInstanceOf(NameObjectIdentifier.class);
            assertThat(((NameObjectIdentifier)compoundId1.objectIdentifiers().get(1)).name()).isEqualTo("Gender");
            assertThat((compoundId1.objectIdentifiers().get(1)).quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);

            assertThat(callExpression2.name()).isEqualTo("{}");
            assertThat(callExpression2.type()).isEqualTo(CallExpression.Type.Braces);
            assertThat(callExpression2.expressions()).hasSize(1);
            CompoundId compoundId2 = (CompoundId) callExpression2.expressions().get(0);
            assertThat(compoundId2.objectIdentifiers()).hasSize(3);
            assertThat(compoundId2.objectIdentifiers().get(0)).isInstanceOf(NameObjectIdentifier.class);
            assertThat(compoundId2.objectIdentifiers().get(1)).isInstanceOf(NameObjectIdentifier.class);
            assertThat(compoundId2.objectIdentifiers().get(2)).isInstanceOf(KeyObjectIdentifier.class);

            assertThat(((NameObjectIdentifier)compoundId2.objectIdentifiers().get(0)).name()).isEqualTo("Customer");
            assertThat((compoundId2.objectIdentifiers().get(0)).quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
            assertThat(((NameObjectIdentifier)compoundId2.objectIdentifiers().get(1)).name()).isEqualTo("Gender");
            assertThat((compoundId2.objectIdentifiers().get(1)).quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
            assertThat(((KeyObjectIdentifier)compoundId2.objectIdentifiers().get(2)).nameObjectIdentifiers()).hasSize(1);
            assertThat(((KeyObjectIdentifier)compoundId2.objectIdentifiers().get(2)).nameObjectIdentifiers().get(0)).isInstanceOf(NameObjectIdentifier.class);
            assertThat(((KeyObjectIdentifier)compoundId2.objectIdentifiers().get(2)).nameObjectIdentifiers().get(0).name()).isEqualTo("F");
            assertThat(((KeyObjectIdentifier)compoundId2.objectIdentifiers().get(2)).nameObjectIdentifiers().get(0).quoting()).isEqualTo(ObjectIdentifier.Quoting.QUOTED);
        }
    }
}
