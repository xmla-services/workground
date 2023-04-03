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
import org.eclipse.daanse.mdx.model.api.expression.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.ObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClauseStatement;
import org.eclipse.daanse.mdx.model.record.expression.CallExpressionR;
import org.eclipse.daanse.mdx.model.record.expression.KeyObjectIdentifierR;
import org.eclipse.daanse.mdx.model.record.expression.NameObjectIdentifierR;
import org.eclipse.daanse.mdx.model.record.select.AxisR;
import org.eclipse.daanse.mdx.model.record.select.SelectDimensionPropertyListClauseR;
import org.eclipse.daanse.mdx.model.record.select.SelectQueryAxesClauseR;
import org.eclipse.daanse.mdx.model.record.select.SelectQueryAxisClauseR;
import org.eclipse.daanse.mdx.model.record.select.SelectSubcubeClauseNameR;
import org.eclipse.daanse.mdx.model.record.select.SelectSubcubeClauseStatementR;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleUnparserSelectSubCubeClauseTest {

    private SimpleUnparser unparser = new SimpleUnparser();

    @Nested
    public class SelectSubCubeClauseNameTestTest {

        @Test
        public void testUnQuoted() {
            NameObjectIdentifier cubeName = new NameObjectIdentifierR("subCube", ObjectIdentifier.Quoting.UNQUOTED);
            SelectSubcubeClause selectSubcubeClauseName = new SelectSubcubeClauseNameR(cubeName);
            assertThat(unparser.unparseSelectSubcubeClause(selectSubcubeClauseName)).asString()
                .isEqualTo("subCube");
        }

        @Test
        public void testQuoted() {
            NameObjectIdentifier cubeName = new NameObjectIdentifierR("subCube", ObjectIdentifier.Quoting.QUOTED);
            SelectSubcubeClause selectSubcubeClauseName = new SelectSubcubeClauseNameR(cubeName);
            assertThat(unparser.unparseSelectSubcubeClause(selectSubcubeClauseName)).asString()
                .isEqualTo("[subCube]");
        }
    }

    @Nested
    public class SelectSubCubeClauseStatementTest {

        @Test
        public void testSingleSubCube() {
            String mdx = " ( \r\n  SELECT \r\n{[Date],[Calendar],[Calendar Year],&[2001]} ON COLUMNS FROM \r\n[Adventure Works]\r\n ) \r\n";
            SelectSubcubeClauseStatement selectSubcubeClauseStatement =
                new SelectSubcubeClauseStatementR(
                    new SelectQueryAxesClauseR(List.of(new SelectQueryAxisClauseR(false,
                        new CallExpressionR("{}",
                            CallExpression.Type.Braces,
                            List.of(
                                new NameObjectIdentifierR("Date", ObjectIdentifier.Quoting.QUOTED),
                                new NameObjectIdentifierR("Calendar", ObjectIdentifier.Quoting.QUOTED),
                                new NameObjectIdentifierR("Calendar Year", ObjectIdentifier.Quoting.QUOTED),
                                new KeyObjectIdentifierR(List.of(new NameObjectIdentifierR("2001", ObjectIdentifier.Quoting.QUOTED)))
                            )),
                        new AxisR(0, true),
                        new SelectDimensionPropertyListClauseR()))),
                    new SelectSubcubeClauseNameR(new NameObjectIdentifierR("Adventure Works", ObjectIdentifier.Quoting.QUOTED)),
                    Optional.ofNullable(null));

            assertThat(unparser.unparseSelectSubcubeClause(selectSubcubeClauseStatement).toString())
                .isEqualTo(mdx);
        }

        @Test
        public void testMultiSubCube() {
            String mdx =
                " ( \r\n  SELECT \r\n{[Date],[Calendar],[Calendar Year],&[2001]} ON COLUMNS FROM \r\n ( \r\n  SELECT \r\n{test} ON COLUMNS FROM \r\n[cube]\r\n ) \r\n\r\n ) \r\n";
            SelectSubcubeClauseStatement selectSubcubeClauseStatement =
                new SelectSubcubeClauseStatementR(
                    new SelectQueryAxesClauseR(List.of(new SelectQueryAxisClauseR(false,
                        new CallExpressionR("{}",
                            CallExpression.Type.Braces,
                            List.of(
                                new NameObjectIdentifierR("Date", ObjectIdentifier.Quoting.QUOTED),
                                new NameObjectIdentifierR("Calendar", ObjectIdentifier.Quoting.QUOTED),
                                new NameObjectIdentifierR("Calendar Year", ObjectIdentifier.Quoting.QUOTED),
                                new KeyObjectIdentifierR(List.of(new NameObjectIdentifierR("2001", ObjectIdentifier.Quoting.QUOTED)))
                            )),
                        new AxisR(0, true),
                        new SelectDimensionPropertyListClauseR()))),

                    new SelectSubcubeClauseStatementR(
                        new SelectQueryAxesClauseR(List.of(new SelectQueryAxisClauseR(false,
                            new CallExpressionR("{}",
                                CallExpression.Type.Braces,
                                List.of(
                                    new NameObjectIdentifierR("test", ObjectIdentifier.Quoting.UNQUOTED)
                                )),
                            new AxisR(0, true),
                            new SelectDimensionPropertyListClauseR()))),
                        new SelectSubcubeClauseNameR(new NameObjectIdentifierR("cube", ObjectIdentifier.Quoting.QUOTED)),
                        Optional.ofNullable(null)),
                    Optional.ofNullable(null));

            assertThat(unparser.unparseSelectSubcubeClause(selectSubcubeClauseStatement).toString())
                .isEqualTo(mdx);
        }
    }
}
