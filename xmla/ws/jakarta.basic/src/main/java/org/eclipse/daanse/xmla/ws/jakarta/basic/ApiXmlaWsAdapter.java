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

import java.io.IOException;
import java.util.List;

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsResponse;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.literals.DiscoverLiteralsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.literals.DiscoverLiteralsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsResponseRow;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.ext.Authenticate;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.ext.AuthenticateResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.BeginSession;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Discover;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DiscoverResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.EndSession;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Execute;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ExecuteResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Session;

import jakarta.xml.bind.JAXBException;
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

        try {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return discoverResponse;
    }

    @Override
    public ExecuteResponse execute(Execute parameters, Holder<Session> session, BeginSession beginSession,
            EndSession endSession) {
        return null;
    }

    private DiscoverResponse handleDbSchemaCatalogs(Discover requestWs) {
        DbSchemaCatalogsRequest requestApi = Convert.fromDiscoverDbSchemaCatalogs(requestWs);
        DbSchemaCatalogsResponse responseApi = xmlaService.discover().dbSchemaCatalogs(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverDbSchemaCatalogs(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverProperties(Discover requestWs) throws JAXBException, IOException {

        DiscoverPropertiesRequest requestApi = Convert.fromDiscoverProperties(requestWs);
        List<DiscoverPropertiesResponseRow> responseApi = xmlaService.discover().discoverProperties(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverProperties(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverSchemaRowsets(Discover requestWs) {

        DiscoverSchemaRowsetsRequest requestApi = Convert.fromDiscoverSchemaRowsets(requestWs);
        List<DiscoverSchemaRowsetsResponseRow> responseApi = xmlaService.discover().discoverSchemaRowsets(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverSchemaRowsets(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverEnumerators(Discover requestWs) {

        DiscoverEnumeratorsRequest requestApi = Convert.fromDiscoverEnumerators(requestWs);
        List<DiscoverEnumeratorsResponseRow> responseApi = xmlaService.discover().discoverEnumerators(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverEnumerators(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverKeywords(Discover requestWs) {

        DiscoverKeywordsRequest requestApi = Convert.fromDiscoverKeywords(requestWs);
        List<DiscoverKeywordsResponseRow> responseApi = xmlaService.discover().discoverKeywords(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverKeywords(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverLiterals(Discover requestWs) {

        DiscoverLiteralsRequest requestApi = Convert.fromDiscoverLiterals(requestWs);
        List<DiscoverLiteralsResponseRow> responseApi = xmlaService.discover().discoverLiterals(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverLiterals(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverDbSchemaTables(Discover requestWs) {

        DbSchemaTablesRequest requestApi = Convert.fromDiscoverDbSchemaTables(requestWs);
        List<DbSchemaTablesResponseRow> responseApi = xmlaService.discover().dbSchemaTables(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverDbSchemaTables(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverMdSchemaActions(Discover requestWs) {

        MdSchemaActionsRequest requestApi = Convert.fromDiscoverMdSchemaActions(requestWs);
        List<MdSchemaActionsResponseRow> responseApi = xmlaService.discover().mdSchemaActions(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverMdSchemaActions(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverMdSchemaCubes(Discover requestWs) {

        MdSchemaCubesRequest requestApi = Convert.fromDiscoverMdSchemaCubes(requestWs);
        List<MdSchemaCubesResponseRow> responseApi = xmlaService.discover().mdSchemaCubes(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverMdSchemaCubes(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverMdSchemaDimensions(Discover requestWs) {

        MdSchemaDimensionsRequest requestApi = Convert.fromDiscoverMdSchemaDimensions(requestWs);
        List<MdSchemaDimensionsResponseRow> responseApi = xmlaService.discover().mdSchemaDimensions(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverMdSchemaDimensions(responseApi);

        return responseWs;
    }

    private DiscoverResponse handleDiscoverMdSchemaFunctions(Discover requestWs) {

        MdSchemaFunctionsRequest requestApi = Convert.fromDiscoverMdSchemaFunctions(requestWs);
        List<MdSchemaFunctionsResponseRow> responseApi = xmlaService.discover().mdSchemaFunctions(requestApi);
        DiscoverResponse responseWs = Convert.toDiscoverMdSchemaFunctions(responseApi);

        return responseWs;
    }
}
