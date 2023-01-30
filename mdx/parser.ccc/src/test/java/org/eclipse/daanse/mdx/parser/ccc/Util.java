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

import org.eclipse.daanse.mdx.model.MdxStatement;
import org.eclipse.daanse.mdx.model.SelectStatement;
import org.eclipse.daanse.mdx.model.select.SelectQueryAsteriskClause;
import org.eclipse.daanse.mdx.model.select.SelectQueryAxesClause;
import org.eclipse.daanse.mdx.parser.api.MdxParser;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;
import org.eclipse.daanse.mdx.parser.ccc.MdxParserWrapper;

public class Util {

    public static MdxParser newParser(String input) throws MdxParserException {
        return new MdxParserWrapper(input);
    }

    public static SelectQueryAsteriskClause parseSelectQueryAsteriskClause(String input) throws MdxParserException {

        return newParser(input).parseSelectQueryAsteriskClause();

    }

    public static SelectQueryAxesClause parseSelectQueryAxesClause(String input) throws MdxParserException {

        return newParser(input).parseSelectQueryAxesClause();

    }

    public static MdxStatement parseMdxStatement(String input) throws MdxParserException {

        return newParser(input).parseMdxStatement();

    }

    public static SelectStatement parseSelectStatement(String input) throws MdxParserException {

        return newParser(input).parseSelectStatement();

    }
}
