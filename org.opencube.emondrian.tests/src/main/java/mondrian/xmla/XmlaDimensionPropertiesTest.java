/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2005-2018 Hitachi Vantara and others
// All Rights Reserved.
*/
package mondrian.xmla;

import mondrian.olap.Connection;
import mondrian.olap4j.MondrianOlap4jDriver;
import mondrian.test.DiffRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.context.Context;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import java.util.Properties;

/**
 * Test of dimension properties in xmla response.
 * Checks each property is added to its own hierarchy.
 *  - fix for MONDRIAN-2302 issue.
 *
 * @author Yury_Bakhmutski.
 */
public class XmlaDimensionPropertiesTest extends XmlaBaseTestCase {

	@BeforeEach
    public void beforeEach() throws ClassNotFoundException {
        Class.forName(MondrianOlap4jDriver.class.getName());
    }
	
    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testOneHierarchyProperties(Context context) throws Exception {
        executeTest(context.createConnection(), "HR");
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testTwoHierarchiesProperties(Context context) throws Exception {
        executeTest(context.createConnection(),"HR");
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testMondrian2342(Context context) throws Exception {
        executeTest(context.createConnection(), "Sales");
    }

    private void executeTest(Connection connection, String cubeName) throws Exception {
        //TestContext context = getTestContext().withCube(cubeName);
        String requestType = "EXECUTE";
        Properties props = getDefaultRequestProperties(requestType);
        props.setProperty(CUBE_NAME_PROP, cubeName);
        doTest( requestType, props, connection);
    }

    @Override
    protected DiffRepository getDiffRepos() {
        return DiffRepository.lookup(XmlaDimensionPropertiesTest.class);
    }

    @Override
    protected Class<? extends XmlaRequestCallback> getServletCallbackClass() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String getSessionId(Action action) {
        throw new UnsupportedOperationException();
    }
}

// End XmlaDimensionPropertiesTest.java
