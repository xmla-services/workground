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

import org.eclipse.daanse.mdx.model.api.MdxStatement;
import org.eclipse.daanse.mdx.model.api.SelectStatement;
import org.eclipse.daanse.mdx.model.api.expression.Expression;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAsteriskClause;
import org.eclipse.daanse.mdx.model.api.select.SelectQueryAxesClause;
import org.eclipse.daanse.mdx.parser.api.MdxParserException;

public class MdxParserWrapper implements org.eclipse.daanse.mdx.parser.api.MdxParser {
    private MdxParser delegate;

    public MdxParserWrapper(CharSequence mdx) throws MdxParserException {

        if (mdx == null) {

            throw new MdxParserException("statement must not be null");
        } else if (mdx.length() == 0) {

            throw new MdxParserException("statement must not be empty");
        }
        try {
            delegate = new MdxParser(mdx);
        } catch (Exception e) {

            throw new MdxParserException("statement must not be empty");
        }
    }

    @Override
    public MdxStatement parseMdxStatement() throws MdxParserException {
        try {
            return delegate.parseMdxStatement();

        } catch (Throwable e) {
            throw new MdxParserException(e);
        } finally {
            dump();
        }

    }

    private void dump() {
        Node root = delegate.rootNode();
        if (root != null) {
            System.out.println("Dumping the AST...");
            root.dump();
        }
    }

    @Override
    public SelectQueryAsteriskClause parseSelectQueryAsteriskClause() throws MdxParserException {
        try {
            return delegate.parseSelectQueryAsteriskClause();

        } catch (Throwable e) {
            throw new MdxParserException(e);
        } finally {
            dump();
        }

    }

    @Override
    public SelectStatement parseSelectStatement() throws MdxParserException {
        try {
            return delegate.parseSelectStatement();

        } catch (Throwable e) {
            throw new MdxParserException(e);
        } finally {
            dump();
        }
    }

    @Override
    public SelectQueryAxesClause parseSelectQueryAxesClause() throws MdxParserException {
        try {
            return delegate.parseSelectQueryAxesClause();

        } catch (Throwable e) {
            throw new MdxParserException(e);
        } finally {
            dump();
        }
    }

    @Override
    public Expression parseExpression() throws MdxParserException {
        try {
            return delegate.parseExpression();

        } catch (Throwable e) {
            throw new MdxParserException(e);
        } finally {
            dump();
        }

    }
}
