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

import org.eclipse.daanse.mdx.model.api.select.SelectDimensionPropertyListClause;
import org.eclipse.daanse.mdx.model.record.select.SelectDimensionPropertyListClauseR;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleUnparserSelectDimensionPropertyListClauseTest {

    private SimpleUnparser unparser = new SimpleUnparser();

    @Test
    public void test() {
        SelectDimensionPropertyListClause selectDimensionPropertyListClause =
            new SelectDimensionPropertyListClauseR(List.of("BACK_COLOR", "FORE_COLOR"));

        assertThat(unparser.unparseSelectDimensionPropertyListClause(selectDimensionPropertyListClause)).asString()
            .isEqualTo("DIMENSION\r\n PROPERTIES BACK_COLOR\r\n, FORE_COLOR");
    }
}
