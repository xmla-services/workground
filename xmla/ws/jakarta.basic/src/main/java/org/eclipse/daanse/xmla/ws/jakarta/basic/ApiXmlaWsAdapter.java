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
package org.eclipse.daanse.xmla.ws.jakarta.basic;

import java.util.List;

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.discover.dbschemacatalogs.DiscoverDbSchemaCatalogsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschemacatalogs.DiscoverDbSchemaCatalogsResponse;
import org.eclipse.daanse.xmla.api.discover.discoverproperties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discoverproperties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.schemarowsets.DiscoverSchemaRowsetsRequest;
import org.eclipse.daanse.xmla.api.discover.schemarowsets.DiscoverSchemaRowsetsResponseRow;
import org.eclipse.daanse.xmla.ws.jakarta.model.ext.Authenticate;
import org.eclipse.daanse.xmla.ws.jakarta.model.ext.AuthenticateResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.BeginSession;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.Discover;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.DiscoverResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.EndSession;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.Execute;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.ExecuteResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.Session;

import jakarta.xml.ws.Holder;

public class ApiXmlaWsAdapter implements WsAdapter {

    XmlaService xmlaService;

    public ApiXmlaWsAdapter(XmlaService xmlaService) {
        this.xmlaService = xmlaService;
    }

    private static final String DISCOVER_SCHEMA_ROWSETS = "DISCOVER_SCHEMA_ROWSETS";
    private static final String DISCOVER_PROPERTIES = "DISCOVER_PROPERTIES";
    private static final String DBSCHEMA_CATALOGS = "DBSCHEMA_CATALOGS";

    @Override
    public AuthenticateResponse authenticate(Authenticate authenticate) {
        return null;
    }

    @Override
    public DiscoverResponse discover(Discover discover, Holder<Session> session, BeginSession beginSession,
            EndSession endSession) {

        if (discover == null) {
            return null;
        }
        DiscoverResponse discoverResponse = null;
        switch (discover.getRequestType()) {
        case DISCOVER_SCHEMA_ROWSETS -> discoverResponse = handleDiscoverSchemaRowsets(discover);
        case DISCOVER_PROPERTIES -> discoverResponse = handleDiscoverProperties(discover);
        case DBSCHEMA_CATALOGS -> discoverResponse = handleDbSchemaCatalogs(discover);
        default -> throw new IllegalArgumentException("Unexpected value: " + discover.getRequestType());
        }

        return discoverResponse;
    }

    @Override
    public ExecuteResponse execute(Execute parameters, Holder<Session> session, BeginSession beginSession,
            EndSession endSession) {
        return null;
    }

    private DiscoverResponse handleDbSchemaCatalogs(Discover requestWs) {
        DiscoverDbSchemaCatalogsRequest requestApi = Convert.fromDiscoverDbSchemaCatalogs(requestWs);
        DiscoverDbSchemaCatalogsResponse responseApi = xmlaService.dbSchemaCatalogs(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverDbSchemaCatalogs(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverProperties(Discover requestWs) {

        DiscoverPropertiesRequest requestApi = Convert.fromDiscoverProperties(requestWs);
        List<DiscoverPropertiesResponseRow> responseApi = xmlaService.discoverProperties(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverProperties(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverSchemaRowsets(Discover requestWs) {

        DiscoverSchemaRowsetsRequest requestApi = Convert.fromDiscoverSchemaRowsets(requestWs);
        List<DiscoverSchemaRowsetsResponseRow> responseApi = xmlaService.discoverSchemaRowsets(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverSchemaRowsets(responseApi);

        return responseWs;
    }

}
