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
package org.eclipse.daanse.mdx.unparser.simple;

import org.eclipse.daanse.mdx.model.api.expression.CallExpression;
import org.eclipse.daanse.mdx.model.api.expression.KeyObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.ObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.select.CreateSetBodyClause;
import org.eclipse.daanse.mdx.model.record.expression.CallExpressionR;
import org.eclipse.daanse.mdx.model.record.expression.CompoundIdR;
import org.eclipse.daanse.mdx.model.record.expression.KeyObjectIdentifierR;
import org.eclipse.daanse.mdx.model.record.expression.NameObjectIdentifierR;
import org.eclipse.daanse.mdx.model.record.select.CreateSetBodyClauseR;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleUnparserSelectWithClauseTest {

    private SimpleUnparser unparser = new SimpleUnparser();

    @Nested
    public class CreateSetBodyClauseTest {

        @Test
        public void testCreateSetBodyClause() {
            NameObjectIdentifier nameObjectIdentifier = new NameObjectIdentifierR("MySet", ObjectIdentifier.Quoting.UNQUOTED);

            NameObjectIdentifier nameObjectIdentifier11 = new NameObjectIdentifierR("Customer", ObjectIdentifier.Quoting.QUOTED);
            NameObjectIdentifier nameObjectIdentifier12 = new NameObjectIdentifierR("Gender", ObjectIdentifier.Quoting.QUOTED);

            NameObjectIdentifier nameObjectIdentifier21 = new NameObjectIdentifierR("Customer", ObjectIdentifier.Quoting.QUOTED);
            NameObjectIdentifier nameObjectIdentifier22 = new NameObjectIdentifierR("Gender", ObjectIdentifier.Quoting.QUOTED);
            NameObjectIdentifier nameObjectIdentifier3 = new NameObjectIdentifierR("F", ObjectIdentifier.Quoting.QUOTED);
            KeyObjectIdentifier keyObjectIdentifier = new KeyObjectIdentifierR(List.of(nameObjectIdentifier3));

            CallExpression callExpression1 = new CallExpressionR("Members", CallExpression.Type.Property,
                List.of(new CompoundIdR(List.of(nameObjectIdentifier11, nameObjectIdentifier12))));

            CallExpression callExpression2 = new CallExpressionR("{}", CallExpression.Type.Braces,
                List.of(new CompoundIdR(List.of(nameObjectIdentifier21, nameObjectIdentifier22, keyObjectIdentifier))));

            CallExpression callExpression = new CallExpressionR("Union", CallExpression.Type.Function, List.of(callExpression1, callExpression2));
            CreateSetBodyClause createSetBodyClause = new CreateSetBodyClauseR(new CompoundIdR(List.of(nameObjectIdentifier)), callExpression);
            assertThat(unparser.unparseCreateSetBodyClause(createSetBodyClause)).asString()
                .isEqualTo("SET MySet AS Union([Customer].[Gender].Members,{[Customer].[Gender].&[F]})");
        }
    }
}
