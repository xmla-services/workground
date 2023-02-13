package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesResponseRowXml;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsResponseRowXml;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsResponseRowXml;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataResponseRowXml;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.dbschema.*;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.discover.DiscoverEnumeratorsResponseRowXml;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.discover.DiscoverLiteralsResponseRowXml;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.discover.DiscoverPropertiesResponseRowXml;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.mdschema.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "row")
@XmlSeeAlso({
    //dbschema
    DbSchemaCatalogsResponseRowXml.class,
    DbSchemaColumnsResponseRowXml.class,
    DbSchemaProviderTypesResponseRowXml.class,
    DbSchemaSchemataResponseRowXml.class,
    DbSchemaSourceTablesResponseRowXml.class,
    DbSchemaTablesInfoResponseRowXml.class,
    DbSchemaTablesResponseRowXml.class,
    //discover
    DiscoverDataSourcesResponseRowXml.class,
    DiscoverEnumeratorsResponseRowXml.class,
    DiscoverKeywordsResponseRowXml.class,
    DiscoverLiteralsResponseRowXml.class,
    DiscoverPropertiesResponseRowXml.class,
    DiscoverSchemaRowsetsResponseRowXml.class,
    DiscoverXmlMetaDataResponseRowXml.class,
    //mdschema
    MdSchemaActionsResponseRowXml.class,
    MdSchemaCubesResponseRowXml.class,
    MdSchemaDimensionsResponseRowXml.class,
    MdSchemaFunctionsResponseRowXml.class,
    MdSchemaHierarchiesResponseRowXml.class,
    MdSchemaKpisResponseRowXml.class,
    MdSchemaLevelsResponseRowXml.class,
    MdSchemaMeasureGroupDimensionsResponseRowXml.class,
    MdSchemaMeasureGroupsResponseRowXml.class,
    MdSchemaMeasuresResponseRowXml.class,
    MdSchemaMembersResponseRowXml.class,
    MdSchemaPropertiesResponseRowXml.class,
    MdSchemaSetsResponseRowXml.class
})
public abstract class Row {
}
