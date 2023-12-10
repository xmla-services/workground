/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2019 Hitachi Vantara..  All rights reserved.
*/

package mondrian.rolap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.opencube.junit5.Constants.PROJECT_DIR;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.context.TestContextWrapper;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import mondrian.olap.Util;
import mondrian.olap.Util.PropertyList;

/**
 * Test for {@link RolapSchemaPool}.
 */
class RolapSchemaPoolTest {

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testBasicSchemaFetch(TestContextWrapper context) {
        RolapSchemaPool schemaPool = RolapSchemaPool.instance();
        schemaPool.clear();

        String catalogUrl = getFoodmartCatalogUrl().toString();
        PropertyList connectInfo =
            Util.parseConnectString(context.getOlapConnectString());

        RolapSchema schema =
            schemaPool.get(
                catalogUrl,
                "connectionKeyA",
                context.getContext(),
                connectInfo);
        RolapSchema schemaA =
            schemaPool.get(
                catalogUrl,
                "connectionKeyA",
                context.getContext(),
                connectInfo);
        //same arguments, same object
        assertTrue(schema == schemaA);
    }

 

    protected URL getFoodmartCatalogUrl() {
        // Works if we are running in root directory of source tree
        File file = new File(PROJECT_DIR + "testfiles/catalogs/FoodMart.xml");
        if (!file.exists()) {
            // Works if we are running in bin directory of runtime env
            file = new File(PROJECT_DIR + "../testfiles/catalogs/FoodMart.xml");
        }

        try {
            return Util.toURL(file);
        } catch (MalformedURLException e) {
            throw new Error(e.getMessage());
        }
    }


}
