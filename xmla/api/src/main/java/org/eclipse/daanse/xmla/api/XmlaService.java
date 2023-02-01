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
package org.eclipse.daanse.xmla.api;

import java.util.List;

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

public interface XmlaService {

    List<DiscoverSchemaRowsetsResponseRow> discoverSchemaRowsets(DiscoverSchemaRowsetsRequest request);

    List<DiscoverPropertiesResponseRow> discoverProperties(DiscoverPropertiesRequest request);

    DiscoverDbSchemaCatalogsResponse dbSchemaCatalogs(DiscoverDbSchemaCatalogsRequest requestApi);

    List<DiscoverEnumeratorsResponseRow> discoverEnumerators(DiscoverEnumeratorsRequest capture);

    List<DiscoverKeywordsResponseRow> discoverKeywords(DiscoverKeywordsRequest capture);

    List<DiscoverLiteralsResponseRow> discoverLiterals(DiscoverLiteralsRequest capture);

    List<DiscoverDbSchemaTablesResponseRow> discoverDbSchemaTables(DiscoverDbSchemaTablesRequest capture);

    List<DiscoverMdSchemaActionsResponseRow> discoverMdSchemaActions(DiscoverMdSchemaActionsRequest requestApi);

    List<DiscoverMdSchemaCubesResponseRow> discoverMdSchemaCubes(DiscoverMdSchemaCubesRequest capture);

    List<DiscoverMdSchemaDimensionsResponseRow> discoverMdSchemaDimensions(DiscoverMdSchemaDimensionsRequest capture);

    List<DiscoverMdSchemaFunctionsResponseRow> discoverMdSchemaFunctions(DiscoverMdSchemaFunctionsRequest capture);
}
