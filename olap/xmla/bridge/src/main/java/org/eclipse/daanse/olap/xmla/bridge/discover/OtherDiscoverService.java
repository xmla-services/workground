/*
* Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.olap.xmla.bridge.discover;

import java.util.List;

import org.eclipse.daanse.olap.xmla.bridge.ContextListSupplyer;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesResponseRow;
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
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataRequest;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataResponseRow;

public class OtherDiscoverService {
	private ContextListSupplyer contextsListSupplyer;

	public OtherDiscoverService(ContextListSupplyer contextsListSupplyer) {
		this.contextsListSupplyer = contextsListSupplyer;

	}

	
	public List<DiscoverDataSourcesResponseRow> dataSources(DiscoverDataSourcesRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	public List<DiscoverEnumeratorsResponseRow> discoverEnumerators(DiscoverEnumeratorsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<DiscoverKeywordsResponseRow> discoverKeywords(DiscoverKeywordsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<DiscoverLiteralsResponseRow> discoverLiterals(DiscoverLiteralsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<DiscoverPropertiesResponseRow> discoverProperties(DiscoverPropertiesRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<DiscoverSchemaRowsetsResponseRow> discoverSchemaRowsets(DiscoverSchemaRowsetsRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<DiscoverXmlMetaDataResponseRow> xmlMetaData(DiscoverXmlMetaDataRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
