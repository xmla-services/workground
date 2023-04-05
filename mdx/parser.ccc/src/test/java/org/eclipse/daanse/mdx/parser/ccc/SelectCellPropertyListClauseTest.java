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

import org.assertj.core.data.Index;
import org.eclipse.daanse.mdx.model.api.select.SelectCellPropertyListClause;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectCellPropertyListClauseTest {

    @Test
    public void test1() throws MdxParserException {
        SelectCellPropertyListClause selectCellPropertyListClause =
            new MdxParserWrapper("CELL PROPERTIES BACK_COLOR, FORE_COLOR")
                .parseSelectCellPropertyListClause();
        assertThat(selectCellPropertyListClause).isNotNull();
        assertThat(selectCellPropertyListClause.cell()).isEqualTo(true);
        assertThat(selectCellPropertyListClause.properties()).isNotNull().hasSize(2);
        assertThat(selectCellPropertyListClause.properties()).contains("BACK_COLOR", Index.atIndex(0));
        assertThat(selectCellPropertyListClause.properties()).contains("FORE_COLOR", Index.atIndex(1));
    }

    @Test
    public void test2() throws MdxParserException {
        SelectCellPropertyListClause selectCellPropertyListClause =
            new MdxParserWrapper("PROPERTIES BACK_COLOR, FORE_COLOR, TEST")
                .parseSelectCellPropertyListClause();
        assertThat(selectCellPropertyListClause).isNotNull();
        assertThat(selectCellPropertyListClause.cell()).isEqualTo(false);
        assertThat(selectCellPropertyListClause.properties()).isNotNull().hasSize(3);
        assertThat(selectCellPropertyListClause.properties()).contains("BACK_COLOR", Index.atIndex(0));
        assertThat(selectCellPropertyListClause.properties()).contains("FORE_COLOR", Index.atIndex(1));
        assertThat(selectCellPropertyListClause.properties()).contains("TEST", Index.atIndex(2));
    }
}
