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
package org.eclipse.daanse.xmla.ws.jakarta.provider.soapmessage;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.Node;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import org.eclipse.daanse.xmla.api.common.enums.CubeSourceEnum;
import org.eclipse.daanse.xmla.api.common.enums.InterfaceNameEnum;
import org.eclipse.daanse.xmla.api.common.enums.MemberTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.OriginEnum;
import org.eclipse.daanse.xmla.api.common.enums.PropertyOriginEnum;
import org.eclipse.daanse.xmla.api.common.enums.PropertyTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.ScopeEnum;
import org.eclipse.daanse.xmla.api.common.enums.TreeOpEnum;
import org.eclipse.daanse.xmla.api.common.enums.VisibilityEnum;
import org.eclipse.daanse.xmla.api.common.properties.PropertyListElementDefinition;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.columns.DbSchemaColumnsResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.providertypes.DbSchemaProviderTypesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.schemata.DbSchemaSchemataResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.sourcetables.DbSchemaSourceTablesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesResponseRow;
import org.eclipse.daanse.xmla.api.discover.dbschema.tablesinfo.DbSchemaTablesInfoResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.literals.DiscoverLiteralsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.kpis.MdSchemaKpisResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroups.MdSchemaMeasureGroupsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measures.MdSchemaMeasuresResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.members.MdSchemaMembersResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.sets.MdSchemaSetsResponseRow;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.catalogs.DbSchemaCatalogsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.columns.DbSchemaColumnsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.providertypes.DbSchemaProviderTypesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.schemata.DbSchemaSchemataRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.sourcetables.DbSchemaSourceTablesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tables.DbSchemaTablesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.dbschema.tablesinfo.DbSchemaTablesInfoRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.datasources.DiscoverDataSourcesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.enumerators.DiscoverEnumeratorsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.keywords.DiscoverKeywordsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.literals.DiscoverLiteralsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.schemarowsets.DiscoverSchemaRowsetsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.discover.xmlmetadata.DiscoverXmlMetaDataRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.actions.MdSchemaActionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.cubes.MdSchemaCubesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.functions.MdSchemaFunctionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.hierarchies.MdSchemaHierarchiesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.kpis.MdSchemaKpisRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.levels.MdSchemaLevelsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measuregroups.MdSchemaMeasureGroupsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.measures.MdSchemaMeasuresRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.members.MdSchemaMembersRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.properties.MdSchemaPropertiesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.sets.MdSchemaSetsRestrictionsR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Convert {
    private static final Logger LOGGER = LoggerFactory.getLogger(Convert.class);
    public static final String ROW = "row";

    private Convert() {
        // constructor
    }

    public static DiscoverPropertiesRestrictionsR discoverPropertiesRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DiscoverPropertiesRestrictionsR(
            Optional.ofNullable(m.get("PropertyName"))
        );
    }

    public static PropertiesR propertiestoProperties(SOAPElement propertiesElement) {

        Iterator<Node> nodeIterator = propertiesElement.getChildElements();
        while (nodeIterator.hasNext()) {
            Node node = nodeIterator.next();
            if (node instanceof SOAPElement propertyList
                && Constants.QNAME_MSXMLA_PROPERTYLIST.equals(propertyList.getElementQName())) {
                return propertyListToProperties(propertyList);
            }

        }
        return new PropertiesR();

    }

    private static PropertiesR propertyListToProperties(SOAPElement propertyList) {
        PropertiesR properties = new PropertiesR();

        Iterator<Node> nodeIteratorPropertyList = propertyList.getChildElements();
        while (nodeIteratorPropertyList.hasNext()) {
            Node n = nodeIteratorPropertyList.next();

            if (n instanceof SOAPElement propertyListElement) {
                String name = propertyListElement.getLocalName();
                Optional<PropertyListElementDefinition> opd = PropertyListElementDefinition.byNameValue(name);
                opd.ifPresent(pd -> properties.addProperty(pd, propertyListElement.getTextContent()));
            }
        }
        return properties;
    }

    public static SOAPBody toDiscoverProperties(List<DiscoverPropertiesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r -> {
            addDiscoverPropertiesResponseRow(root, r);
        });

        return body;
    }

    private static void addDiscoverPropertiesResponseRow(SOAPElement root, DiscoverPropertiesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(root, "PropertyName", r.propertyName());
        r.propertyDescription().ifPresent(v -> addChildElement(root, "PropertyDescription", v));
        r.propertyDescription().ifPresent(v -> addChildElement(root, "PropertyType", v));
        addChildElement(root, "PropertyAccessType", r.propertyAccessType());
        r.required().ifPresent(v -> addChildElement(root, "IsRequired", String.valueOf(v)));
        r.value().ifPresent(v -> addChildElement(root, "Value", v));
    }

    public static MdSchemaFunctionsRestrictionsR discoverMdSchemaFunctionsRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new MdSchemaFunctionsRestrictionsR(
            Optional.ofNullable(OriginEnum.fromValue(m.get("ORIGIN"))),
            Optional.ofNullable(InterfaceNameEnum.fromValue(m.get("INTERFACE_NAME"))),
            Optional.ofNullable(m.get("LIBRARY_NAME"))
        );
    }

    public static SOAPBody toMdSchemaFunctions(List<MdSchemaFunctionsResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static MdSchemaDimensionsRestrictionsR discoverMdSchemaDimensionsRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new MdSchemaDimensionsRestrictionsR(
            Optional.ofNullable(m.get("CATALOG_NAME")),
            Optional.ofNullable(m.get("SCHEMA_NAME")),
            Optional.ofNullable(m.get("CUBE_NAME")),
            Optional.ofNullable(m.get("DIMENSION_NAME")),
            Optional.ofNullable(m.get("DIMENSION_UNIQUE_NAME")),
            Optional.ofNullable(CubeSourceEnum.fromValue(m.get("CUBE_SOURCE"))),
            Optional.ofNullable(VisibilityEnum.fromValue(m.get("DIMENSION_VISIBILITY")))
        );
    }

    public static SOAPBody toMdSchemaDimensions(List<MdSchemaDimensionsResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static MdSchemaCubesRestrictionsR discoverMdSchemaCubesRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new MdSchemaCubesRestrictionsR(m.get("CATALOG_NAME"),
            Optional.ofNullable(m.get("SCHEMA_NAME")),
            Optional.ofNullable(m.get("CUBE_NAME")),
            Optional.ofNullable(m.get("BASE_CUBE_NAME")),
            Optional.ofNullable(CubeSourceEnum.fromValue(m.get("CUBE_SOURCE"))));
    }

    public static SOAPBody toMdSchemaCubes(List<MdSchemaCubesResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static MdSchemaMeasureGroupsRestrictionsR discoverMdSchemaMeasureGroups(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new MdSchemaMeasureGroupsRestrictionsR(Optional.ofNullable(m.get("CATALOG_NAME")),
            Optional.ofNullable(m.get("SCHEMA_NAME")),
            Optional.ofNullable(m.get("CUBE_NAME")),
            Optional.ofNullable(m.get("MEASUREGROUP_NAME"))
        );
    }

    public static SOAPBody toMdSchemaMeasureGroups(List<MdSchemaMeasureGroupsResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static MdSchemaKpisRestrictionsR discoverMdSchemaKpisRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new MdSchemaKpisRestrictionsR(Optional.ofNullable(m.get("CATALOG_NAME")),
            Optional.ofNullable(m.get("SCHEMA_NAME")),
            Optional.ofNullable(m.get("CUBE_NAME")),
            Optional.ofNullable(m.get("KPI_NAME")),
            Optional.ofNullable(CubeSourceEnum.fromValue(m.get("CUBE_SOURCE")))
        );
    }

    public static SOAPBody toMdSchemaKpis(List<MdSchemaKpisResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static MdSchemaSetsRestrictionsR discoverMdSchemaSetsRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new MdSchemaSetsRestrictionsR(
            Optional.ofNullable(m.get("CATALOG_NAME")),
            Optional.ofNullable(m.get("SCHEMA_NAME")),
            Optional.ofNullable(m.get("CUBE_NAME")),
            Optional.ofNullable(m.get("SET_NAME")),
            Optional.ofNullable(ScopeEnum.fromValue(m.get("SCOPE"))),
            Optional.ofNullable(CubeSourceEnum.fromValue(m.get("CUBE_SOURCE"))),
            Optional.ofNullable(m.get("HIERARCHY_UNIQUE_NAME"))
        );
    }

    public static SOAPBody toMdSchemaSets(List<MdSchemaSetsResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static MdSchemaPropertiesRestrictionsR discoverMdSchemaPropertiesRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new MdSchemaPropertiesRestrictionsR(
            Optional.ofNullable(m.get("CATALOG_NAME")),
            Optional.ofNullable(m.get("SCHEMA_NAME")),
            Optional.ofNullable(m.get("CUBE_NAME")),
            Optional.ofNullable(m.get("DIMENSION_UNIQUE_NAME")),
            Optional.ofNullable(m.get("HIERARCHY_UNIQUE_NAME")),
            Optional.ofNullable(m.get("LEVEL_UNIQUE_NAME")),
            Optional.ofNullable(m.get("MEMBER_UNIQUE_NAME")),
            Optional.ofNullable(PropertyTypeEnum.fromValue(m.get("PROPERTY_TYPE"))),
            Optional.ofNullable(m.get("PROPERTY_NAME")),
            Optional.ofNullable(PropertyOriginEnum.fromValue(m.get("PROPERTY_ORIGIN"))),
            Optional.ofNullable(CubeSourceEnum.fromValue(m.get("CUBE_SOURCE"))),
            Optional.ofNullable(VisibilityEnum.fromValue(m.get("PROPERTY_VISIBILITY")))
        );
    }

    public static SOAPBody toMdSchemaProperties(List<MdSchemaPropertiesResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static MdSchemaMembersRestrictionsR discoverMdSchemaMembersRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new MdSchemaMembersRestrictionsR(
            Optional.ofNullable(m.get("CATALOG_NAME")),
            Optional.ofNullable(m.get("SCHEMA_NAME")),
            Optional.ofNullable(m.get("CUBE_NAME")),
            Optional.ofNullable(m.get("DIMENSION_UNIQUE_NAME")),
            Optional.ofNullable(m.get("HIERARCHY_UNIQUE_NAME")),
            Optional.ofNullable(m.get("LEVEL_UNIQUE_NAME")),
            Optional.ofNullable(Integer.decode(m.get("LEVEL_NUMBER"))),
            Optional.ofNullable(m.get("MEMBER_NAME")),
            Optional.ofNullable(m.get("MEMBER_UNIQUE_NAME")),
            Optional.ofNullable(MemberTypeEnum.fromValue(m.get("MEMBER_TYPE"))),
            Optional.ofNullable(m.get("MEMBER_CAPTION")),
            Optional.ofNullable(CubeSourceEnum.fromValue(m.get("CUBE_SOURCE"))),
            Optional.ofNullable(TreeOpEnum.fromValue(m.get("TREE_OP")))
        );
    }

    public static SOAPBody toMdSchemaMembers(List<MdSchemaMembersResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static MdSchemaMeasuresRestrictionsR discoverMdSchemaMeasuresRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;

    }

    public static SOAPBody toMdSchemaMeasures(List<MdSchemaMeasuresResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;

    }

    public static MdSchemaMeasureGroupDimensionsRestrictionsR discoverMdSchemaMeasureGroupDimensionsRestrictions(
        SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toMdSchemaMeasureGroupDimensions(List<MdSchemaMeasureGroupDimensionsResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static MdSchemaLevelsRestrictionsR discoverMdSchemaLevelsRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toMdSchemaLevels(List<MdSchemaLevelsResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static MdSchemaHierarchiesRestrictionsR discoverMdSchemaHierarchiesRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toMdSchemaHierarchies(List<MdSchemaHierarchiesResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static DbSchemaTablesInfoRestrictionsR discoverDbSchemaTablesInfo(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toDbSchemaTablesInfo(List<DbSchemaTablesInfoResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static DbSchemaSourceTablesRestrictionsR discoverDbSchemaSourceTablesRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toDbSchemaSourceTables(List<DbSchemaSourceTablesResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static DbSchemaSchemataRestrictionsR discoverDbSchemaSchemataRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toDbSchemaSchemata(List<DbSchemaSchemataResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static DbSchemaProviderTypesRestrictionsR discoverDbSchemaProviderTypesRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toDbSchemaProviderTypes(List<DbSchemaProviderTypesResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static DbSchemaColumnsRestrictionsR discoverDbSchemaColumnsRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toDbSchemaColumns(List<DbSchemaColumnsResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static DiscoverXmlMetaDataRestrictionsR discoverDiscoverXmlMetaDataRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toDiscoverXmlMetaData(List<DiscoverXmlMetaDataResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static DiscoverDataSourcesRestrictionsR discoverDiscoverDataSourcesRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toDiscoverDataSources(List<DiscoverDataSourcesResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static DbSchemaCatalogsRestrictionsR discoverDbSchemaCatalogsRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toDbSchemaCatalogs(List<DbSchemaCatalogsResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static DiscoverSchemaRowsetsRestrictionsR discoverSchemaRowsetsRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toDiscoverSchemaRowsets(List<DiscoverSchemaRowsetsResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static DiscoverEnumeratorsRestrictionsR discoverDiscoverEnumerators(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toDiscoverEnumerators(List<DiscoverEnumeratorsResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static DiscoverKeywordsRestrictionsR discoverKeywordsRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toDiscoverKeywords(List<DiscoverKeywordsResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static DiscoverLiteralsRestrictionsR discoverLiteralsRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toDiscoverLiterals(List<DiscoverLiteralsResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static DbSchemaTablesRestrictionsR discoverDbSchemaTablesRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toDbSchemaTables(List<DbSchemaTablesResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    public static MdSchemaActionsRestrictionsR discoverMdSchemaActionsRestrictions(SOAPElement restriction) {
        // TODO Auto-generated method stub
        return null;
    }

    public static SOAPBody toMdSchemaActions(List<MdSchemaActionsResponseRow> rows) {
        // TODO Auto-generated method stub
        return null;
    }

    private static Map<String, String> getMapValuesByTag(SOAPElement el, String tagName) {
        NodeList nodeList = el.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return getMapValues(nodeList.item(0).getChildNodes());
        }
        return Map.of();
    }

    private static Map<String, String> getMapValues(NodeList nl) {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node n = nl.item(i);
            result.put(n.getNodeName(), n.getTextContent());
        }
        return result;
    }

    private static SOAPElement addRoot(SOAPElement body) {
        SOAPElement response = addChildElement(body, "DiscoverResponse");
        SOAPElement ret = addChildElement(response, "return");
        return addChildElement(ret, "root");
    }

    private static void addChildElement(SOAPElement element, String childElementName, String value) {
        try {
            if (value != null) {
                element.addChildElement(childElementName).setTextContent(value);
            }
        } catch (SOAPException e) {
            LOGGER.error("addChildElement {} error", childElementName);
            throw new RuntimeException("addChildElement error", e);
        }
    }

    private static SOAPElement addChildElement(SOAPElement element, String childElementName) {
        try {
            return element.addChildElement(childElementName);
        } catch (SOAPException e) {
            LOGGER.error("addChildElement {} error", childElementName);
            throw new RuntimeException("addChildElement error", e);
        }
    }

    private static SOAPBody createSOAPBody() {
        try {
            SOAPMessage message = MessageFactory.newInstance().createMessage();
            return message.getSOAPBody();
        } catch (SOAPException e) {
            throw new RuntimeException("create SOAPBody error");
        }
    }

}
