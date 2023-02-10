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

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.daanse.mdx.model.api.MdxStatement;
import org.eclipse.daanse.mdx.model.record.SelectStatementR;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.junit.jupiter.api.Test;

public class MdxStatementTest {

    @Test
    public void testTypeSelectStatement() throws MdxParserException {
        String mdx = """
                SELECT [Customer].[Gender].[Gender].Membmers ON COLUMNS,
                         {[Customer].[Customer].[Aaron A. Allen],
                          [Customer].[Customer].[Abigail Clark]} ON ROWS
                   FROM [Adventure Works]
                   WHERE [Measures].[Internet Sales Amount]
                """;

        MdxStatement clause = new MdxParserWrapper(mdx).parseMdxStatement();
        assertThat(clause).isNotNull()
                .isInstanceOf(SelectStatementR.class);

    }

}
