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
package org.eclipse.daanse.mdx.parser.api;

import org.eclipse.daanse.mdx.model.api.MdxStatement;
import org.eclipse.daanse.mdx.model.api.SelectStatement;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAsteriskClause;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAxesClause;

public interface MdxParser {

    public MdxStatement parseMdxStatement() throws MdxParserException;

    public SelectStatement parseSelectStatement() throws MdxParserException;

    public SelectQueryAsteriskClause parseSelectQueryAsteriskClause() throws MdxParserException;

    public SelectQueryAxesClause parseSelectQueryAxesClause() throws MdxParserException;

}
