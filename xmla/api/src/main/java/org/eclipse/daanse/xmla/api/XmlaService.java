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
import org.eclipse.daanse.xmla.api.discover.discoverproperties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discoverproperties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.schemarowsets.DiscoverSchemaRowsetsRequest;
import org.eclipse.daanse.xmla.api.discover.schemarowsets.DiscoverSchemaRowsetsResponseRow;

public interface XmlaService {

    List<DiscoverSchemaRowsetsResponseRow> discoverSchemaRowsets(DiscoverSchemaRowsetsRequest request);

    List<DiscoverPropertiesResponseRow> discoverProperties(DiscoverPropertiesRequest request);

    DiscoverDbSchemaCatalogsResponse dbSchemaCatalogs(DiscoverDbSchemaCatalogsRequest requestApi);

}
