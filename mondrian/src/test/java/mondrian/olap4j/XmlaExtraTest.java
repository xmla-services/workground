/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/
package mondrian.olap4j;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import mondrian.olap.MondrianServer;
import mondrian.olap.Util;
import mondrian.olap.Util.PropertyList;
import mondrian.rolap.RolapConnection;
import mondrian.rolap.RolapConnectionProperties;

public class XmlaExtraTest{
    /**
     * This test makes sure that the value of
     * {@link RolapConnectionProperties#JdbcPassword} isn't leaked through
     * the XmlaExtra interface.
     */
    @Test
     void testGetDataSourceDoesntLeakPassword() throws Exception {
        final List<Map<String, Object>> expectedList =
            new ArrayList<>();
        final Map<String, Object> expectedMap =
            new HashMap<>();
        expectedMap.put(
            "DataSourceInfo",
            "Provider=Mondrian;Jdbc=foo;JdbcPassword=bar;JdbcUser=bacon");
        expectedList.add(expectedMap);

        final MondrianServer server = mock(MondrianServer.class);
        final RolapConnection rConn = mock(RolapConnection.class);
        final MondrianOlap4jConnection conn =
            mock(MondrianOlap4jConnection.class);
        final MondrianOlap4jExtra extra =MondrianOlap4jExtra.INSTANCE;

        doReturn(expectedList).when(server).getDatabases(rConn);
        doReturn(server).when(rConn).getServer();
        doReturn(rConn).when(conn).getMondrianConnection();

        for (Map<String, Object> ds : extra.getDataSources(conn)) {
            final PropertyList props =
                Util.parseConnectString(
                    String.valueOf(ds.get("DataSourceInfo")));
            assertNull(
                props.get(RolapConnectionProperties.Jdbc.name()));
            assertNull(
                props.get(RolapConnectionProperties.JdbcUser.name()));
            assertNull(
                props.get(RolapConnectionProperties.JdbcPassword.name()));
        }
     }
}
//End XmlaExtraTest.java
