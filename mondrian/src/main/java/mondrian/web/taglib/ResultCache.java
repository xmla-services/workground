/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2002-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
*/

package mondrian.web.taglib;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.daanse.olap.api.Connection;
import org.eclipse.daanse.olap.api.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import mondrian.olap.DriverManager;
import mondrian.olap.QueryImpl;
import mondrian.spi.impl.ServletContextCatalogLocator;

import java.io.Serializable;

/**
 * Holds a query/result pair in the user's session.
 *
 * @author Andreas Voss, 22 March, 2002
 */
public class ResultCache implements HttpSessionBindingListener, Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultCache.class);
    private static final String ATTR_NAME = "mondrian.web.taglib.ResultCache.";
    private QueryImpl query = null;
    private Result result = null;
    private Document document = null;
    private ServletContext servletContext;
    private Connection connection;

    private ResultCache(ServletContext context) {
        this.servletContext = context;
    }


    /**
     * Retrieves a cached query. It is identified by its name and the
     * current session. The servletContext parameter is necessary because
     * HttpSession.getServletContext was not added until J2EE 1.3.
     */
    public static ResultCache getInstance(
        HttpSession session,
        ServletContext servletContext,
        String name)
    {
        String fqname = ATTR_NAME + name;
        ResultCache resultCache = (ResultCache) session.getAttribute(fqname);
        if (resultCache == null) {
            resultCache = new ResultCache(servletContext);
            session.setAttribute(fqname, resultCache);
        }
        return resultCache;
    }

    public void parse(String mdx) {
        if (connection != null) {
            query = connection.parseQuery(mdx);
            setDirty();
        } else {
            LOGGER.error("null connection");
        }
    }

    public Result getResult() {
        if (result == null) {
            long t1 = System.currentTimeMillis();
            result = connection.execute(query);
            long t2 = System.currentTimeMillis();
            LOGGER.debug(
                "Execute query took {} millisec", (t2 - t1));
        }
        return result;
    }

    public Document getDOM() {
        try {
            if (document == null) {
                document = DomBuilder.build(getResult());
            }
            return document;
        } catch (ParserConfigurationException e) {
            LOGGER.error("ResultCache getDOM error");
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the {@link QueryImpl}. If you modify the query, call
     * <code>{@link #setDirty}(true)</code>.
     */
    public QueryImpl getQuery() {
        return query;
    }

    /**
     * Sets the query. Automatically calls <code>{@link #setDirty}(true)</code>.
     */
    public void setQuery(QueryImpl query) {
        this.query = query;
        setDirty();
    }
    /**
     * set to dirty after you have modified the query to force a recalcuation
     */
    public void setDirty() {
        result = null;
        document = null;
    }

    /**
     * create a new connection to Mondrian
     */
    @Override
	public void valueBound(HttpSessionBindingEvent ev) {
        String connectString =
            servletContext.getInitParameter("connectString");
        LOGGER.debug("connectString: {}", connectString);
        this.connection =
            DriverManager.getConnection(
                connectString,
                new ServletContextCatalogLocator(servletContext));
        if (this.connection == null) {
            throw new RuntimeException(
                "No ROLAP connection from connectString: "
                    + connectString);
        }
    }

    /**
     * close connection
     */
    @Override
	public void valueUnbound(HttpSessionBindingEvent ev) {
        if (connection != null) {
            connection.close();
        }
    }


}
