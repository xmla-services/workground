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
import org.eclipse.daanse.xmla.api.discover.dbschematables.DiscoverDbSchemaTablesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschematables.DiscoverDbSchemaTablesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discoverproperties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discoverproperties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.enumerators.DiscoverEnumeratorsRequest;
import org.eclipse.daanse.xmla.api.discover.enumerators.DiscoverEnumeratorsResponseRow;
import org.eclipse.daanse.xmla.api.discover.keywords.DiscoverKeywordsRequest;
import org.eclipse.daanse.xmla.api.discover.keywords.DiscoverKeywordsResponseRow;
import org.eclipse.daanse.xmla.api.discover.literals.DiscoverLiteralsRequest;
import org.eclipse.daanse.xmla.api.discover.literals.DiscoverLiteralsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschemaactions.DiscoverMdSchemaActionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschemaactions.DiscoverMdSchemaActionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschemacubes.DiscoverMdSchemaCubesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschemacubes.DiscoverMdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschemademensions.DiscoverMdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschemademensions.DiscoverMdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschemafunctions.DiscoverMdSchemaFunctionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschemafunctions.DiscoverMdSchemaFunctionsResponseRow;
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

    private static final String MDSCHEMA_FUNCTIONS = "MDSCHEMA_FUNCTIONS";
    private static final String MDSCHEMA_DIMENSIONS = "MDSCHEMA_DIMENSIONS";
    private static final String MDSCHEMA_CUBES = "MDSCHEMA_CUBES";
    private static final String MDSCHEMA_ACTIONS = "MDSCHEMA_ACTIONS";
    private static final String DBSCHEMA_TABLES = "DBSCHEMA_TABLES";
    private static final String DISCOVER_LITERALS = "DISCOVER_LITERALS";
    private static final String DISCOVER_KEYWORDS = "DISCOVER_KEYWORDS";
    private static final String DISCOVER_ENUMERATORS = "DISCOVER_ENUMERATORS";
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
        case MDSCHEMA_FUNCTIONS -> discoverResponse = handleDiscoverMdSchemaFunctions(discover);
        case MDSCHEMA_DIMENSIONS -> discoverResponse = handleDiscoverMdSchemaDimensions(discover);
        case MDSCHEMA_CUBES -> discoverResponse = handleDiscoverMdSchemaCubes(discover);
        case MDSCHEMA_ACTIONS -> discoverResponse = handleDiscoverMdSchemaActions(discover);
        case DBSCHEMA_TABLES -> discoverResponse = handleDiscoverDbSchemaTables(discover);
        case DISCOVER_LITERALS -> discoverResponse = handleDiscoverLiterals(discover);
        case DISCOVER_KEYWORDS -> discoverResponse = handleDiscoverKeywords(discover);
        case DISCOVER_ENUMERATORS -> discoverResponse = handleDiscoverEnumerators(discover);
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

    private DiscoverResponse handleDiscoverEnumerators(Discover requestWs) {

        DiscoverEnumeratorsRequest requestApi = Convert.fromDiscoverEnumerators(requestWs);
        List<DiscoverEnumeratorsResponseRow> responseApi = xmlaService.discoverEnumerators(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverEnumerators(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverKeywords(Discover requestWs) {

        DiscoverKeywordsRequest requestApi = Convert.fromDiscoverKeywords(requestWs);
        List<DiscoverKeywordsResponseRow> responseApi = xmlaService.discoverKeywords(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverKeywords(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverLiterals(Discover requestWs) {

        DiscoverLiteralsRequest requestApi = Convert.fromDiscoverLiterals(requestWs);
        List<DiscoverLiteralsResponseRow> responseApi = xmlaService.discoverLiterals(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverLiterals(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverDbSchemaTables(Discover requestWs) {

        DiscoverDbSchemaTablesRequest requestApi = Convert.fromDiscoverDbSchemaTables(requestWs);
        List<DiscoverDbSchemaTablesResponseRow> responseApi = xmlaService.discoverDbSchemaTables(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverDbSchemaTables(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverMdSchemaActions(Discover requestWs) {

        DiscoverMdSchemaActionsRequest requestApi = Convert.fromDiscoverMdSchemaActions(requestWs);
        List<DiscoverMdSchemaActionsResponseRow> responseApi = xmlaService.discoverMdSchemaActions(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverMdSchemaActions(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverMdSchemaCubes(Discover requestWs) {

        DiscoverMdSchemaCubesRequest requestApi = Convert.fromDiscoverMdSchemaCubes(requestWs);
        List<DiscoverMdSchemaCubesResponseRow> responseApi = xmlaService.discoverMdSchemaCubes(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverMdSchemaCubes(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverMdSchemaDimensions(Discover requestWs) {

        DiscoverMdSchemaDimensionsRequest requestApi = Convert.fromDiscoverMdSchemaDimensions(requestWs);
        List<DiscoverMdSchemaDimensionsResponseRow> responseApi = xmlaService.discoverMdSchemaDimensions(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverMdSchemaDimensions(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverMdSchemaFunctions(Discover requestWs) {

        DiscoverMdSchemaFunctionsRequest requestApi = Convert.fromDiscoverMdSchemaFunctions(requestWs);
        List<DiscoverMdSchemaFunctionsResponseRow> responseApi = xmlaService.discoverMdSchemaFunctions(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverMdSchemaFunctions(responseApi);

        return responseWs;
    }
}
