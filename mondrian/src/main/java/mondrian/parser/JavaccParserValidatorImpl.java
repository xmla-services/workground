/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.parser;

import mondrian.olap.Exp;
import mondrian.olap.FunTable;
import mondrian.olap.MondrianException;
import mondrian.olap.Parser;
import mondrian.olap.Util;
import mondrian.olap.api.QueryPart;
import mondrian.server.Statement;

/**
 * Default implementation of {@link MdxParserValidator}, using the
 * <a href="http://java.net/projects/javacc/">JavaCC</a> parser generator.
 *
 * @author jhyde
 */
public class JavaccParserValidatorImpl implements MdxParserValidator {
    private final QueryPartFactory factory;

    /**
     * Creates a JavaccParserValidatorImpl.
     */
    public JavaccParserValidatorImpl() {
        this(new Parser.FactoryImpl());
    }

    /**
     * Creates a JavaccParserValidatorImpl with an explicit factory for parse
     * tree nodes.
     *
     * @param factory Factory for parse tree nodes
     */
    public JavaccParserValidatorImpl(QueryPartFactory factory) {
        this.factory = factory;
    }

    @Override
	public QueryPart parseInternal(
        Statement statement,
        String queryString,
        boolean debug,
        FunTable funTable,
        boolean strictValidation)
    {
        final MdxParserImpl mdxParser =
            new MdxParserImpl(
                factory,
                statement,
                queryString,
                debug,
                funTable,
                strictValidation);
        try {
            return mdxParser.statementEof();
        } catch (ParseException e) {
            throw convertException(queryString, e);
        }
    }

    @Override
	public Exp parseExpression(
        Statement statement,
        String queryString,
        boolean debug,
        FunTable funTable)
    {
        final MdxParserImpl mdxParser =
            new MdxParserImpl(
                factory,
                statement,
                queryString,
                debug,
                funTable,
                false);
        try {
            return mdxParser.expressionEof();
        } catch (ParseException e) {
            throw convertException(queryString, e);
        }
    }

    /**
     * Converts the exception so that it looks like the exception produced by
     * JavaCUP. (Not that that format is ideal, but it minimizes test output
     * changes during the transition from JavaCUP to JavaCC.)
     *
     * @param queryString MDX query string
     * @param pe JavaCC parse exception
     * @return Wrapped exception
     */
    private RuntimeException convertException(
        String queryString,
        ParseException pe)
    {
        Exception e;
        if (pe.getMessage().startsWith("Encountered ")) {
            e = new MondrianException(
                new StringBuilder("Syntax error at line ")
                .append(pe.currentToken.next.beginLine)
                .append(", column ")
                .append(pe.currentToken.next.beginColumn)
                .append(", token '")
                .append(pe.currentToken.next.image)
                .append("'").toString());
        } else {
            e = pe;
        }
        return Util.newError(e, "While parsing " + queryString);
    }
}
