package org.eclipse.daanse.xmla.client.soapmessage;

import java.util.List;

import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesResponseRow;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.discover.datasources.DiscoverDataSourcesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.datasources.DiscoverDataSourcesRestrictionsR;
import org.junit.jupiter.api.Test;

public class ClientDiscoverTest {
    XmlaServiceClientImpl client = new XmlaServiceClientImpl("");
    // Register a Provider using whiteboardpattern and xmlassert to check xml

    @Test
    void testdataSources_simple() throws Exception {

        PropertiesR properties = new PropertiesR();
        DiscoverDataSourcesRestrictionsR restrictions = new DiscoverDataSourcesRestrictionsR(null, null, null, null,
                null, null, null);
        DiscoverDataSourcesRequest dataSourcesRequest = new DiscoverDataSourcesRequestR(properties, restrictions);

        List<DiscoverDataSourcesResponseRow> rows = client.discover()
                .dataSources(dataSourcesRequest);
    }
}
