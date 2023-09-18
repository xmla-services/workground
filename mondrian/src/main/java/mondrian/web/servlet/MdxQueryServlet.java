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

package mondrian.web.servlet;

import mondrian.olap.DriverManager;
import mondrian.olap.MondrianProperties;
import mondrian.olap.QueryImpl;
import mondrian.olap.Util;
import mondrian.spi.CatalogLocator;
import mondrian.spi.impl.ServletContextCatalogLocator;
import mondrian.web.taglib.ResultCache;

import org.eclipse.daanse.olap.api.element.Member;
import org.eclipse.daanse.olap.api.result.Cell;
import org.eclipse.daanse.olap.api.result.Position;
import org.eclipse.daanse.olap.api.result.Result;
import org.eigenbase.xom.StringEscaper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * <code>MdxQueryServlet</code> is a servlet which receives MDX queries,
 * executes them, and formats the results in an HTML table.
 *
 * @author  Sean McCullough
 * @since 13 February, 2002
 */
public class MdxQueryServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(MdxQueryServlet.class);
    public static final String TD = "</td>";
    public static final String TR = "</tr>";
    public static final String QUERY = "query";
    private String connectString;
    private CatalogLocator locator;

    /**
     * Initializes the servlet.
     */
    @Override
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connectString = config.getInitParameter("connectString");
        Enumeration<String> initParameterNames = config.getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            String name = initParameterNames.nextElement();
            String value = config.getInitParameter(name);
            MondrianProperties.instance().setProperty(name, value);
        }
        locator = new ServletContextCatalogLocator(config.getServletContext());
    }

    /**
     * Destroys the servlet.
     */
    @Override
	public void destroy() {
        // destroy
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, java.io.IOException
    {
        String queryName = request.getParameter(QUERY);
        request.setAttribute(QUERY, queryName);
        if (queryName != null) {
            processTransform(request, response);
            return;
        }
        String queryString = request.getParameter("queryString");
        request.setAttribute("queryString", queryString);
        org.eclipse.daanse.olap.api.Connection mdxConnection = null;
        StringBuilder html = new StringBuilder();

        // execute the query
        try {
            mdxConnection = DriverManager.getConnection(connectString, locator);
            QueryImpl q = mdxConnection.parseQuery(queryString);
            Result result = mdxConnection.execute(q);
            List<Position> slicers = result.getSlicerAxis().getPositions();
            html.append("<table class='resulttable' cellspacing=1 border=0>");
            html.append(Util.NL);

            List<Position> columns = result.getAxes()[0].getPositions();
            List<Position> rows = null;
            if (result.getAxes().length == 2) {
                rows = result.getAxes()[1].getPositions();
            }
            int columnWidth = columns.get(0).size();
            int rowWidth = 0;
            if (result.getAxes().length == 2) {
                rowWidth = result.getAxes()[1].getPositions().get(0).size();
            }
            for (int j = 0; j < columnWidth; j++) {
                html.append("<tr>");

                // if it has more than 1 dimension
                if (j == 0 && result.getAxes().length > 1) {
                    // Print the top-left cell, and fill it with slicer members.
                    html.append("<td nowrap class='slicer' rowspan='")
                        .append(columnWidth)
                        .append("' colspan='")
                        .append(rowWidth)
                        .append("'>");
                    for (Position position : slicers) {
                        int k = 0;
                        for (Member member : position) {
                            if (k > 0) {
                                html.append("<br/>");
                            }
                            html.append(member.getUniqueName());
                            k++;
                        }
                    }
                    html.append("&nbsp;</td>").append(Util.NL);
                }

                // Print the column headings.
                for (int i = 0; i < columns.size(); i++) {
                    Position position = columns.get(i);
                    Member member = position.get(j);
                    int width = 1;
                    while ((i + 1) < columns.size()
                        && columns.get(i + 1).get(j) == member)
                    {
                        i++;
                        width++;
                    }
                    html.append("<td nowrap class='columnheading' colspan='")
                        .append(width).append("'>")
                        .append(member.getUniqueName()).append(TD);
                }
                html.append(TR).append(Util.NL);
            }
            //if is two axes, show
            if (result.getAxes().length > 1) {
                for (int i = 0; i < rows.size(); i++) {
                    html.append("<tr>");
                    final Position row = rows.get(i);
                    for (Member member : row) {
                        html.append("<td nowrap class='rowheading'>")
                            .append(member.getUniqueName())
                            .append(TD);
                    }
                    for (int j = 0; j < columns.size(); j++) {
                        showCell(html, result.getCell(new int[] {j, i}));
                    }
                    html.append(TR);
                }
            } else {
                html.append("<tr>");
                for (int i = 0; i < columns.size(); i++) {
                    showCell(html, result.getCell(new int[] {i}));
                }
                html.append(TR);
            }
            html.append("</table>");
        } catch (Exception e) {
            final String[] strings = Util.convertStackToString(e);
            html.append("Error:<pre><blockquote>");
            for (String string : strings) {
                html.append(StringEscaper.htmlEscaper.escapeString(string));
            }
            html.append("</blockquote></pre>");
        } finally {
            if (mdxConnection != null) {
                mdxConnection.close();
            }
        }

        request.setAttribute("result", html.toString());
        response.setHeader("Content-Type", "text/html");
        getServletContext().getRequestDispatcher("/adhoc.jsp").include(
            request, response);
    }

    private void showCell(StringBuilder out, Cell cell) {
        out.append("<td class='cell'>")
            .append(cell.getFormattedValue())
            .append(TD);
    }

    private void processTransform(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String queryName = request.getParameter(QUERY);
        ResultCache rc =
            ResultCache.getInstance(
                request.getSession(), getServletContext(), queryName);
        QueryImpl query = rc.getQuery();
        query = new QueryImpl(query);
        rc.setDirty();
        String operation = request.getParameter("operation");
        if (operation.equals("expand")) {
            String memberName = request.getParameter("member");
            boolean fail = true;
            query.getSchemaReader(true).getMemberByUniqueName(
                Util.parseIdentifier(memberName), fail);
            if (true) {
                throw new UnsupportedOperationException(
                    "query.toggleDrillState(member) has been de-supported");
            }
        } else {
            throw Util.newInternal(new StringBuilder("unkown operation '").append(operation).append("'").toString());
        }
        rc.setQuery(query);
        String redirect = request.getParameter("redirect");
        if (redirect == null) {
            redirect = "/adhoc.jsp";
        }
        response.setHeader("Content-Type", "text/html");
        getServletContext().getRequestDispatcher(redirect).include(
            request, response);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
	protected void doGet(
        HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException
    {
        try {
            processRequest(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.debug("Internal server error {}", e.getMessage());
            getServletContext().log("Internal server error", e);
            response.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
	protected void doPost(
        HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            processRequest(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.debug("Internal server error {}", e.getMessage());
            getServletContext().log("Internal server error", e);
            response.setStatus(SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns a short description of the servlet.
     */
    @Override
	public String getServletInfo() {
        return "Process an MDX query and return the result formatted as an HTML table";
    }

}
