/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.xmla;

import org.eclipse.daanse.olap.api.Connection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.context.TestingContext;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import mondrian.olap4j.MondrianOlap4jDriver;
import mondrian.test.DiffRepository;

/**
 * Test XMLA output in tabular (flattened) format.
 *
 * @author Julio Caub&iacute;n, jhyde
 */
public class XmlaTabularTest extends XmlaBaseTestCase {

    @AfterEach
    public void afterEach() {
        tearDown();
    }

    @BeforeAll
    public static void beforeAll() throws ClassNotFoundException {
        Class.forName(MondrianOlap4jDriver.class.getName());
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testTabularOneByOne(TestingContext context) throws Exception {
        executeMDX(context.createConnection());
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testTabularOneByTwo(TestingContext context) throws Exception {
        executeMDX(context.createConnection());
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testTabularTwoByOne(TestingContext context) throws Exception {
        executeMDX(context.createConnection());
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testTabularTwoByTwo(TestingContext context) throws Exception {
        executeMDX(context.createConnection());
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testTabularZeroByZero(TestingContext context) throws Exception {
        executeMDX(context.createConnection());
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testTabularVoid(TestingContext context) throws Exception {
        executeMDX(context.createConnection());
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testTabularThreeAxes(TestingContext context) throws Exception {
        executeMDX(context.createConnection());
    }

    private void executeMDX(Connection connection) throws Exception {
        String requestType = "EXECUTE";
        doTest(
            requestType,
            getDefaultRequestProperties(requestType),
            connection);
    }

    @Override
	protected DiffRepository getDiffRepos() {
        return DiffRepository.lookup(XmlaTabularTest.class);
    }

    @Override
	protected Class<? extends XmlaRequestCallback> getServletCallbackClass() {
        return null;
    }

    @Override
	protected String getSessionId(Action action) {
        return null;
    }
}
