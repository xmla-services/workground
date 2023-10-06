/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.test;

import static org.opencube.junit5.TestUtil.getConnectionProperties;

import org.opencube.junit5.context.TestContextWrapper;

import mondrian.olap.Util;
import mondrian.rolap.RolapConnectionProperties;

/**
 * Unit test against Pentaho's Steel Wheels sample database.
 *
 * <p>It is not required that the Steel Wheels database be present, so each
 * test should check whether the database exists and trivially succeed if it
 * does not.
 *
 * @author jhyde
 * @since 12 March 2009
 */
public class SteelWheelsTestCase {

    /**
     * Creates a SteelwheelsTestCase.
     */
    public SteelWheelsTestCase() {
    }

    /**
     * Creates a TestContext which contains the given schema text.
     *
     * @param context Base test context
     * @param schema A XML schema, or null
     * Used for testing if the connection is valid.
     * @return TestContext which contains the given schema
     */
    public static void createContext(
        TestContextWrapper context,
        final String schema)
    {
        final Util.PropertyList properties =
            Util.PropertyList.newInstance(getConnectionProperties(context.createConnection()));
        //final String jdbc = properties.get(
        //   RolapConnectionProperties.Jdbc.name());
        //context.setProperty(
        //    RolapConnectionProperties.Jdbc.name(),
        //    Util.replace(jdbc, "/foodmart", "/steelwheels"));
        if (schema != null) {
            context.setProperty(
                RolapConnectionProperties.CatalogContent.name(),
                schema);
            context.setProperty(
                RolapConnectionProperties.Catalog.name(), "");
        } else {
            final String catalog =
                properties.get(RolapConnectionProperties.Catalog.name());
            context.setProperty(
                RolapConnectionProperties.Catalog.name(),
                catalog.replace(
                    "FoodMart.xml",
                    "SteelWheels.xml"));
        }
    }

    /**
     * Returns the test context. Override this method if you wish to use a
     * different source for your SteelWheels connection.
     */
    public void getTestContext(TestContextWrapper context) {
        createContext(context, null);

            //withCube("SteelWheelsSales");
    }
}
