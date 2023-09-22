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
import org.eclipse.daanse.mdx.model.api.expression.MdxExpression;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAsteriskClause;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAxesClause;
import org.eclipse.daanse.mdx.model.api.select.SelectSubcubeClause;

public interface MdxParser {

    MdxStatement parseMdxStatement() throws MdxParserException;

    SelectStatement parseSelectStatement() throws MdxParserException;

    SelectQueryAsteriskClause parseSelectQueryAsteriskClause() throws MdxParserException;

    SelectQueryAxesClause parseSelectQueryAxesClause() throws MdxParserException;

    MdxExpression parseExpression() throws MdxParserException;

    SelectSubcubeClause parseSelectSubcubeClause() throws MdxParserException;
}
