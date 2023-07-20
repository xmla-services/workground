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

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.Node;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import org.eclipse.daanse.xmla.api.common.enums.ActionTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.AuthenticationModeEnum;
import org.eclipse.daanse.xmla.api.common.enums.ColumnOlapTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.CoordinateTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.CubeSourceEnum;
import org.eclipse.daanse.xmla.api.common.enums.InterfaceNameEnum;
import org.eclipse.daanse.xmla.api.common.enums.InvocationEnum;
import org.eclipse.daanse.xmla.api.common.enums.LevelDbTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.MemberTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.ObjectExpansionEnum;
import org.eclipse.daanse.xmla.api.common.enums.OriginEnum;
import org.eclipse.daanse.xmla.api.common.enums.PropertyOriginEnum;
import org.eclipse.daanse.xmla.api.common.enums.PropertyTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.ProviderTypeEnum;
import org.eclipse.daanse.xmla.api.common.enums.ScopeEnum;
import org.eclipse.daanse.xmla.api.common.enums.TableTypeEnum;
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
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.ParameterInfo;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.kpis.MdSchemaKpisResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MeasureGroupDimension;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroups.MdSchemaMeasureGroupsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measures.MdSchemaMeasuresResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.members.MdSchemaMembersResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.sets.MdSchemaSetsResponseRow;
import org.eclipse.daanse.xmla.api.engine300_300.XEvent;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.api.xmla.Aggregation;
import org.eclipse.daanse.xmla.api.xmla.AggregationAttribute;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesign;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesignAttribute;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesignDimension;
import org.eclipse.daanse.xmla.api.xmla.AggregationDimension;
import org.eclipse.daanse.xmla.api.xmla.AggregationInstance;
import org.eclipse.daanse.xmla.api.xmla.AggregationInstanceDimension;
import org.eclipse.daanse.xmla.api.xmla.AggregationInstanceMeasure;
import org.eclipse.daanse.xmla.api.xmla.AndOrType;
import org.eclipse.daanse.xmla.api.xmla.AndOrTypeEnum;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Assembly;
import org.eclipse.daanse.xmla.api.xmla.BoolBinop;
import org.eclipse.daanse.xmla.api.xmla.Command;
import org.eclipse.daanse.xmla.api.xmla.Cube;
import org.eclipse.daanse.xmla.api.xmla.DataSource;
import org.eclipse.daanse.xmla.api.xmla.DataSourceView;
import org.eclipse.daanse.xmla.api.xmla.DataSourceViewBinding;
import org.eclipse.daanse.xmla.api.xmla.Database;
import org.eclipse.daanse.xmla.api.xmla.Dimension;
import org.eclipse.daanse.xmla.api.xmla.ErrorConfiguration;
import org.eclipse.daanse.xmla.api.xmla.Event;
import org.eclipse.daanse.xmla.api.xmla.EventColumnID;
import org.eclipse.daanse.xmla.api.xmla.EventSession;
import org.eclipse.daanse.xmla.api.xmla.EventType;
import org.eclipse.daanse.xmla.api.xmla.MajorObject;
import org.eclipse.daanse.xmla.api.xmla.MdxScript;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroup;
import org.eclipse.daanse.xmla.api.xmla.Member;
import org.eclipse.daanse.xmla.api.xmla.MiningModel;
import org.eclipse.daanse.xmla.api.xmla.MiningStructure;
import org.eclipse.daanse.xmla.api.xmla.NotType;
import org.eclipse.daanse.xmla.api.xmla.ObjectExpansion;
import org.eclipse.daanse.xmla.api.xmla.ObjectReference;
import org.eclipse.daanse.xmla.api.xmla.Partition;
import org.eclipse.daanse.xmla.api.xmla.PartitionModes;
import org.eclipse.daanse.xmla.api.xmla.Permission;
import org.eclipse.daanse.xmla.api.xmla.Perspective;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveAction;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveAttribute;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveCalculation;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveDimension;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveHierarchy;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveKpi;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveMeasure;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveMeasureGroup;
import org.eclipse.daanse.xmla.api.xmla.ProactiveCaching;
import org.eclipse.daanse.xmla.api.xmla.ReadDefinitionEnum;
import org.eclipse.daanse.xmla.api.xmla.ReadWritePermissionEnum;
import org.eclipse.daanse.xmla.api.xmla.RetentionModes;
import org.eclipse.daanse.xmla.api.xmla.Role;
import org.eclipse.daanse.xmla.api.xmla.Scope;
import org.eclipse.daanse.xmla.api.xmla.Server;
import org.eclipse.daanse.xmla.api.xmla.ServerProperty;
import org.eclipse.daanse.xmla.api.xmla.TabularBinding;
import org.eclipse.daanse.xmla.api.xmla.Trace;
import org.eclipse.daanse.xmla.api.xmla.TraceFilter;
import org.eclipse.daanse.xmla.api.xmla.Translation;
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
import org.eclipse.daanse.xmla.model.record.engine300_300.XEventR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationAttributeR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationDesignAttributeR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationDesignDimensionR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationDesignR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationDimensionR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationInstanceR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationR;
import org.eclipse.daanse.xmla.model.record.xmla.AlterR;
import org.eclipse.daanse.xmla.model.record.xmla.AndOrTypeR;
import org.eclipse.daanse.xmla.model.record.xmla.AnnotationR;
import org.eclipse.daanse.xmla.model.record.xmla.BoolBinopR;
import org.eclipse.daanse.xmla.model.record.xmla.CancelR;
import org.eclipse.daanse.xmla.model.record.xmla.ClearCacheR;
import org.eclipse.daanse.xmla.model.record.xmla.EventColumnIDR;
import org.eclipse.daanse.xmla.model.record.xmla.EventR;
import org.eclipse.daanse.xmla.model.record.xmla.EventSessionR;
import org.eclipse.daanse.xmla.model.record.xmla.EventTypeR;
import org.eclipse.daanse.xmla.model.record.xmla.MajorObjectR;
import org.eclipse.daanse.xmla.model.record.xmla.MemberR;
import org.eclipse.daanse.xmla.model.record.xmla.NotTypeR;
import org.eclipse.daanse.xmla.model.record.xmla.ObjectReferenceR;
import org.eclipse.daanse.xmla.model.record.xmla.PartitionR;
import org.eclipse.daanse.xmla.model.record.xmla.PermissionR;
import org.eclipse.daanse.xmla.model.record.xmla.PerspectiveActionR;
import org.eclipse.daanse.xmla.model.record.xmla.PerspectiveAttributeR;
import org.eclipse.daanse.xmla.model.record.xmla.PerspectiveCalculationR;
import org.eclipse.daanse.xmla.model.record.xmla.PerspectiveDimensionR;
import org.eclipse.daanse.xmla.model.record.xmla.PerspectiveHierarchyR;
import org.eclipse.daanse.xmla.model.record.xmla.PerspectiveKpiR;
import org.eclipse.daanse.xmla.model.record.xmla.PerspectiveMeasureGroupR;
import org.eclipse.daanse.xmla.model.record.xmla.PerspectiveMeasureR;
import org.eclipse.daanse.xmla.model.record.xmla.PerspectiveR;
import org.eclipse.daanse.xmla.model.record.xmla.RoleR;
import org.eclipse.daanse.xmla.model.record.xmla.ServerPropertyR;
import org.eclipse.daanse.xmla.model.record.xmla.ServerR;
import org.eclipse.daanse.xmla.model.record.xmla.StatementR;
import org.eclipse.daanse.xmla.model.record.xmla.TraceFilterR;
import org.eclipse.daanse.xmla.model.record.xmla.TraceR;
import org.eclipse.daanse.xmla.model.record.xmla.TranslationR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
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
        rows.forEach(r ->
            addDiscoverPropertiesResponseRow(root, r)
        );
        return body;
    }

    private static void addDiscoverPropertiesResponseRow(SOAPElement root, DiscoverPropertiesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "PropertyName", r.propertyName());
        r.propertyDescription().ifPresent(v -> addChildElement(row, "PropertyDescription", v));
        r.propertyDescription().ifPresent(v -> addChildElement(row, "PropertyType", v));
        addChildElement(row, "PropertyAccessType", r.propertyAccessType());
        r.required().ifPresent(v -> addChildElement(row, "IsRequired", String.valueOf(v)));
        r.value().ifPresent(v -> addChildElement(row, "Value", v));
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
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaFunctionsResponseRow(root, r)
        );
        return body;
    }

    private static void addMdSchemaFunctionsResponseRow(SOAPElement root, MdSchemaFunctionsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.functionalName().ifPresent(v -> addChildElement(row, "FUNCTION_NAME", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        addChildElement(row, "PARAMETER_LIST", r.parameterList());
        r.returnType().ifPresent(v -> addChildElement(row, "RETURN_TYPE", String.valueOf(v)));
        r.origin().ifPresent(v -> addChildElement(row, "ORIGIN", String.valueOf(v.getValue())));
        r.interfaceName().ifPresent(v -> addChildElement(row, "INTERFACE_NAME", v.name()));
        r.libraryName().ifPresent(v -> addChildElement(row, "LIBRARY_NAME", v));
        r.dllName().ifPresent(v -> addChildElement(row, "DLL_NAME", v));
        r.helpFile().ifPresent(v -> addChildElement(row, "HELP_FILE", v));
        r.helpContext().ifPresent(v -> addChildElement(row, "HELP_CONTEXT", v));
        r.object().ifPresent(v -> addChildElement(row, "OBJECT", v));
        r.caption().ifPresent(v -> addChildElement(row, "CAPTION", v));
        r.parameterInfo().ifPresent(v -> addParameterInfoXmlList(row, v));
        r.directQueryPushable().ifPresent(v -> addChildElement(row, "DIRECTQUERY_PUSHABLE",
            String.valueOf(v.getValue())));
    }

    private static void addParameterInfoXmlList(SOAPElement root, List<ParameterInfo> list) {
        if (list != null) {
            list.forEach(it -> addParameterInfoXml(root, it));
        }
    }

    private static void addParameterInfoXml(SOAPElement root, ParameterInfo it) {
        SOAPElement el = addChildElement(root, "PARAMETERINFO");
        addChildElement(el, "NAME", it.name());
        addChildElement(el, "DESCRIPTION", it.description());
        addChildElement(el, "OPTIONAL", String.valueOf(it.optional()));
        addChildElement(el, "REPEATABLE", String.valueOf(it.repeatable()));
        addChildElement(el, "REPEATGROUP", String.valueOf(it.repeatGroup()));
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
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaDimensionsResponseRow(root, r)
        );
        return body;
    }

    private static void addMdSchemaDimensionsResponseRow(SOAPElement root, MdSchemaDimensionsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.dimensionName().ifPresent(v -> addChildElement(row, "DIMENSION_NAME", v));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.dimensionGuid().ifPresent(v -> addChildElement(row, "DIMENSION_GUID", String.valueOf(v)));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.dimensionCaption().ifPresent(v -> addChildElement(row, "DIMENSION_CAPTION", v));
        r.dimensionOptional().ifPresent(v -> addChildElement(row, "DIMENSION_ORDINAL", String.valueOf(v)));
        r.dimensionType().ifPresent(v -> addChildElement(row, "DIMENSION_TYPE", String.valueOf(v.getValue())));
        r.dimensionCardinality().ifPresent(v -> addChildElement(row, "DIMENSION_CARDINALITY", String.valueOf(v)));
        r.defaultHierarchy().ifPresent(v -> addChildElement(row, "DEFAULT_HIERARCHY", v));
        r.defaultHierarchy().ifPresent(v -> addChildElement(row, "DEFAULT_HIERARCHY", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.isVirtual().ifPresent(v -> addChildElement(row, "IS_VIRTUAL", String.valueOf(v)));
        r.isReadWrite().ifPresent(v -> addChildElement(row, "IS_READWRITE", String.valueOf(v)));
        r.dimensionUniqueSetting().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_SETTINGS",
            String.valueOf(v.getValue())));
        r.dimensionMasterName().ifPresent(v -> addChildElement(row, "DIMENSION_MASTER_NAME", v));
        r.dimensionIsVisible().ifPresent(v -> addChildElement(row, "DIMENSION_IS_VISIBLE", String.valueOf(v)));
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
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaCubesResponseRow(root, r)
        );
        return body;
    }

    private static void addMdSchemaCubesResponseRow(SOAPElement root, MdSchemaCubesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "CATALOG_NAME", r.catalogName());
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.cubeType().ifPresent(v -> addChildElement(row, "CUBE_TYPE", v.name()));
        r.cubeGuid().ifPresent(v -> addChildElement(row, "CUBE_GUID", String.valueOf(v)));
        r.createdOn().ifPresent(v -> addChildElement(row, "CREATED_ON", String.valueOf(v)));
        r.lastSchemaUpdate().ifPresent(v -> addChildElement(row, "LAST_SCHEMA_UPDATE", String.valueOf(v)));
        r.schemaUpdatedBy().ifPresent(v -> addChildElement(row, "SCHEMA_UPDATED_BY", v));
        r.lastDataUpdate().ifPresent(v -> addChildElement(row, "LAST_DATA_UPDATE", String.valueOf(v)));
        r.dataUpdateDBy().ifPresent(v -> addChildElement(row, "DATA_UPDATED_BY", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.isDrillThroughEnabled().ifPresent(v -> addChildElement(row, "IS_DRILLTHROUGH_ENABLED", String.valueOf(v)));
        r.isLinkable().ifPresent(v -> addChildElement(row, "IS_LINKABLE", String.valueOf(v)));
        r.isWriteEnabled().ifPresent(v -> addChildElement(row, "IS_WRITE_ENABLED", String.valueOf(v)));
        r.isSqlEnabled().ifPresent(v -> addChildElement(row, "IS_SQL_ENABLED", String.valueOf(v)));
        r.cubeCaption().ifPresent(v -> addChildElement(row, "CUBE_CAPTION", v));
        r.baseCubeName().ifPresent(v -> addChildElement(row, "BASE_CUBE_NAME", v));
        r.cubeSource().ifPresent(v -> addChildElement(row, "CUBE_SOURCE", String.valueOf(v.getValue())));
        r.preferredQueryPatterns().ifPresent(v -> addChildElement(row, "PREFERRED_QUERY_PATTERNS",
            String.valueOf(v.getValue())));
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
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaMeasureGroupsResponseRow(root, r)
        );
        return body;
    }

    private static void addMdSchemaMeasureGroupsResponseRow(SOAPElement root, MdSchemaMeasureGroupsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.measureGroupName().ifPresent(v -> addChildElement(row, "MEASUREGROUP_NAME", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.isWriteEnabled().ifPresent(v -> addChildElement(row, "IS_WRITE_ENABLED", String.valueOf(v)));
        r.measureGroupCaption().ifPresent(v -> addChildElement(row, "MEASUREGROUP_CAPTION", v));
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
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaKpisResponseRow(root, r)
        );
        return body;
    }

    private static void addMdSchemaKpisResponseRow(SOAPElement root, MdSchemaKpisResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.measureGroupName().ifPresent(v -> addChildElement(row, "MEASUREGROUP_NAME", v));
        r.kpiName().ifPresent(v -> addChildElement(row, "KPI_NAME", v));
        r.kpiCaption().ifPresent(v -> addChildElement(row, "KPI_CAPTION", v));
        r.kpiDescription().ifPresent(v -> addChildElement(row, "KPI_DESCRIPTION", v));
        r.kpiDisplayFolder().ifPresent(v -> addChildElement(row, "KPI_DISPLAY_FOLDER", v));
        r.kpiGoal().ifPresent(v -> addChildElement(row, "KPI_GOAL", v));
        r.kpiStatus().ifPresent(v -> addChildElement(row, "KPI_STATUS", v));
        r.kpiTrend().ifPresent(v -> addChildElement(row, "KPI_TREND", v));
        r.kpiStatusGraphic().ifPresent(v -> addChildElement(row, "KPI_STATUS_GRAPHIC", v));
        r.kpiTrendGraphic().ifPresent(v -> addChildElement(row, "KPI_TREND_GRAPHIC", v));
        r.kpiWight().ifPresent(v -> addChildElement(row, "KPI_WEIGHT", v));
        r.kpiCurrentTimeMember().ifPresent(v -> addChildElement(row, "KPI_CURRENT_TIME_MEMBER", v));
        r.kpiParentKpiName().ifPresent(v -> addChildElement(row, "KPI_PARENT_KPI_NAME", v));
        r.annotation().ifPresent(v -> addChildElement(row, "ANNOTATIONS", v));
        r.scope().ifPresent(v -> addChildElement(row, "ANNOTATIONS", String.valueOf(v.getValue())));
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
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaSetsResponseRow(root, r)
        );
        return body;
    }

    private static void addMdSchemaSetsResponseRow(SOAPElement root, MdSchemaSetsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.setName().ifPresent(v -> addChildElement(row, "SET_NAME", v));
        r.scope().ifPresent(v -> addChildElement(row, "SCOPE", String.valueOf(v.getValue())));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.expression().ifPresent(v -> addChildElement(row, "EXPRESSION", v));
        r.dimension().ifPresent(v -> addChildElement(row, "DIMENSIONS", v));
        r.setCaption().ifPresent(v -> addChildElement(row, "SET_CAPTION", v));
        r.setDisplayFolder().ifPresent(v -> addChildElement(row, "SET_DISPLAY_FOLDER", v));
        r.setEvaluationContext().ifPresent(v -> addChildElement(row, "SET_EVALUATION_CONTEXT",
            String.valueOf(v.getValue())));
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
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaPropertiesResponseRow(root, r)
        );
        return body;
    }

    private static void addMdSchemaPropertiesResponseRow(SOAPElement root, MdSchemaPropertiesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.hierarchyUniqueName().ifPresent(v -> addChildElement(row, "HIERARCHY_UNIQUE_NAME", v));
        r.levelUniqueName().ifPresent(v -> addChildElement(row, "LEVEL_UNIQUE_NAME", v));
        r.memberUniqueName().ifPresent(v -> addChildElement(row, "MEMBER_UNIQUE_NAME", v));
        r.propertyType().ifPresent(v -> addChildElement(row, "PROPERTY_TYPE", String.valueOf(v.getValue())));
        r.propertyName().ifPresent(v -> addChildElement(row, "PROPERTY_NAME", v));
        r.propertyCaption().ifPresent(v -> addChildElement(row, "PROPERTY_CAPTION", v));
        r.dataType().ifPresent(v -> addChildElement(row, "DATA_TYPE", String.valueOf(v.getValue())));
        r.characterMaximumLength().ifPresent(v -> addChildElement(row, "CHARACTER_MAXIMUM_LENGTH", String.valueOf(v)));
        r.characterOctetLength().ifPresent(v -> addChildElement(row, "CHARACTER_OCTET_LENGTH", String.valueOf(v)));
        r.numericPrecision().ifPresent(v -> addChildElement(row, "NUMERIC_PRECISION", String.valueOf(v)));
        r.numericScale().ifPresent(v -> addChildElement(row, "NUMERIC_SCALE", String.valueOf(v)));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.propertyContentType().ifPresent(v -> addChildElement(row, "PROPERTY_CONTENT_TYPE",
            String.valueOf(v.getValue())));
        r.sqlColumnName().ifPresent(v -> addChildElement(row, "SQL_COLUMN_NAME", v));
        r.language().ifPresent(v -> addChildElement(row, "LANGUAGE", String.valueOf(v)));
        r.propertyOrigin().ifPresent(v -> addChildElement(row, "PROPERTY_ORIGIN", String.valueOf(v.getValue())));
        r.propertyAttributeHierarchyName().ifPresent(v -> addChildElement(row, "PROPERTY_ATTRIBUTE_HIERARCHY_NAME", v));
        r.propertyCardinality().ifPresent(v -> addChildElement(row, "PROPERTY_CARDINALITY", v.name()));
        r.propertyIsVisible().ifPresent(v -> addChildElement(row, "PROPERTY_IS_VISIBLE", String.valueOf(v)));
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
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaMembersResponseRow(root, r)
        );
        return body;
    }

    private static void addMdSchemaMembersResponseRow(SOAPElement root, MdSchemaMembersResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.hierarchyUniqueName().ifPresent(v -> addChildElement(row, "HIERARCHY_UNIQUE_NAME", v));
        r.levelUniqueName().ifPresent(v -> addChildElement(row, "LEVEL_UNIQUE_NAME", v));
        r.levelNumber().ifPresent(v -> addChildElement(row, "LEVEL_NUMBER", String.valueOf(v)));
        r.memberOrdinal().ifPresent(v -> addChildElement(row, "MEMBER_ORDINAL", String.valueOf(v)));
        r.memberName().ifPresent(v -> addChildElement(row, "MEMBER_NAME", v));
        r.memberUniqueName().ifPresent(v -> addChildElement(row, "MEMBER_UNIQUE_NAME", v));
        r.memberType().ifPresent(v -> addChildElement(row, "MEMBER_TYPE", String.valueOf(v.getValue())));
        r.memberGuid().ifPresent(v -> addChildElement(row, "MEMBER_GUID", String.valueOf(v)));
        r.memberCaption().ifPresent(v -> addChildElement(row, "MEMBER_CAPTION", v));
        r.childrenCardinality().ifPresent(v -> addChildElement(row, "CHILDREN_CARDINALITY", String.valueOf(v)));
        r.parentLevel().ifPresent(v -> addChildElement(row, "PARENT_LEVEL", String.valueOf(v)));
        r.parentUniqueName().ifPresent(v -> addChildElement(row, "PARENT_UNIQUE_NAME", v));
        r.parentCount().ifPresent(v -> addChildElement(row, "PARENT_COUNT", String.valueOf(v)));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.expression().ifPresent(v -> addChildElement(row, "EXPRESSION", v));
        r.memberKey().ifPresent(v -> addChildElement(row, "MEMBER_KEY", v));
        r.isPlaceHolderMember().ifPresent(v -> addChildElement(row, "IS_PLACEHOLDERMEMBER", String.valueOf(v)));
        r.isDataMember().ifPresent(v -> addChildElement(row, "IS_DATAMEMBER", String.valueOf(v)));
        r.scope().ifPresent(v -> addChildElement(row, "SCOPE", String.valueOf(v.getValue())));
    }

    public static MdSchemaMeasuresRestrictionsR discoverMdSchemaMeasuresRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new MdSchemaMeasuresRestrictionsR(
            Optional.ofNullable(m.get("CATALOG_NAME")),
            Optional.ofNullable(m.get("SCHEMA_NAME")),
            Optional.ofNullable(m.get("CUBE_NAME")),
            Optional.ofNullable(m.get("MEASURE_NAME")),
            Optional.ofNullable(m.get("MEASURE_UNIQUE_NAME")),
            Optional.ofNullable(m.get("MEASUREGROUP_NAME")),
            Optional.ofNullable(CubeSourceEnum.fromValue(m.get("CUBE_SOURCE"))),
            Optional.ofNullable(VisibilityEnum.fromValue(m.get("MEASURE_VISIBILITY")))
        );
    }

    public static SOAPBody toMdSchemaMeasures(List<MdSchemaMeasuresResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaMeasuresResponseRow(root, r)
        );
        return body;
    }

    private static void addMdSchemaMeasuresResponseRow(SOAPElement root, MdSchemaMeasuresResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));
        r.measureName().ifPresent(v -> addChildElement(row, "MEASURE_NAME", v));
        r.measureUniqueName().ifPresent(v -> addChildElement(row, "MEASURE_UNIQUE_NAME", v));
        r.measureCaption().ifPresent(v -> addChildElement(row, "MEASURE_CAPTION", v));
        r.measureGuid().ifPresent(v -> addChildElement(row, "MEASURE_GUID", String.valueOf(v)));
        r.measureAggregator().ifPresent(v -> addChildElement(row, "MEASURE_AGGREGATOR", String.valueOf(v.getValue())));
        r.dataType().ifPresent(v -> addChildElement(row, "DATA_TYPE", String.valueOf(v.getValue())));
        r.numericPrecision().ifPresent(v -> addChildElement(row, "NUMERIC_PRECISION", String.valueOf(v)));
        r.numericScale().ifPresent(v -> addChildElement(row, "NUMERIC_SCALE", String.valueOf(v)));
        r.measureUnits().ifPresent(v -> addChildElement(row, "MEASURE_UNITS", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.expression().ifPresent(v -> addChildElement(row, "EXPRESSION", v));
        r.measureIsVisible().ifPresent(v -> addChildElement(row, "MEASURE_IS_VISIBLE", String.valueOf(v)));
        r.levelsList().ifPresent(v -> addChildElement(row, "LEVELS_LIST", v));
        r.measureNameSqlColumnName().ifPresent(v -> addChildElement(row, "MEASURE_NAME_SQL_COLUMN_NAME", v));
        r.measureUnqualifiedCaption().ifPresent(v -> addChildElement(row, "MEASURE_UNQUALIFIED_CAPTION", v));
        r.measureGroupName().ifPresent(v -> addChildElement(row, "MEASUREGROUP_NAME", v));
        r.defaultFormatString().ifPresent(v -> addChildElement(row, "DEFAULT_FORMAT_STRING", v));
    }

    public static MdSchemaMeasureGroupDimensionsRestrictionsR discoverMdSchemaMeasureGroupDimensionsRestrictions(
        SOAPElement restriction
    ) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new MdSchemaMeasureGroupDimensionsRestrictionsR(
            Optional.ofNullable(m.get("CATALOG_NAME")),
            Optional.ofNullable(m.get("SCHEMA_NAME")),
            Optional.ofNullable(m.get("CUBE_NAME")),
            Optional.ofNullable(m.get("MEASUREGROUP_NAME")),
            Optional.ofNullable(m.get("DIMENSION_UNIQUE_NAME")),
            Optional.ofNullable(VisibilityEnum.fromValue(m.get("DIMENSION_VISIBILITY")))
        );
    }

    public static SOAPBody toMdSchemaMeasureGroupDimensions(List<MdSchemaMeasureGroupDimensionsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaMeasureGroupDimensionsResponseRow(root, r)
        );
        return body;
    }

    private static void addMdSchemaMeasureGroupDimensionsResponseRow(
        SOAPElement root,
        MdSchemaMeasureGroupDimensionsResponseRow r
    ) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));

        r.measureGroupName().ifPresent(v -> addChildElement(row, "MEASUREGROUP_NAME", v));
        r.measureGroupCardinality().ifPresent(v -> addChildElement(row, "MEASUREGROUP_CARDINALITY", v));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.dimensionCardinality().ifPresent(v -> addChildElement(row, "DIMENSION_CARDINALITY", v.name()));
        r.dimensionIsVisible().ifPresent(v -> addChildElement(row, "DIMENSION_IS_VISIBLE", String.valueOf(v)));
        r.dimensionIsFactDimension().ifPresent(v -> addChildElement(row, "DIMENSION_IS_FACT_DIMENSION",
            String.valueOf(v)));
        r.dimensionPath().ifPresent(v -> addMeasureGroupDimensionXmlList(row, v));
        r.dimensionGranularity().ifPresent(v -> addChildElement(row, "DIMENSION_GRANULARITY", v));
    }

    private static void addMeasureGroupDimensionXmlList(SOAPElement el, List<MeasureGroupDimension> list) {
        if (list != null) {
            SOAPElement e = addChildElement(el, "DIMENSION_PATH");
            list.forEach(it -> addMeasureGroupDimensionXml(e, it));
        }
    }

    private static void addMeasureGroupDimensionXml(SOAPElement el, MeasureGroupDimension it) {
        addChildElement(el, "MeasureGroupDimension", it.measureGroupDimension());
    }

    public static MdSchemaLevelsRestrictionsR discoverMdSchemaLevelsRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new MdSchemaLevelsRestrictionsR(
            Optional.ofNullable(m.get("CATALOG_NAME")),
            Optional.ofNullable(m.get("SCHEMA_NAME")),
            Optional.ofNullable(m.get("CUBE_NAME")),
            Optional.ofNullable(m.get("DIMENSION_UNIQUE_NAME")),
            Optional.ofNullable(m.get("HIERARCHY_UNIQUE_NAME")),
            Optional.ofNullable(m.get("LEVEL_NAME")),
            Optional.ofNullable(m.get("LEVEL_UNIQUE_NAME")),
            Optional.ofNullable(CubeSourceEnum.fromValue(m.get("CUBE_SOURCE"))),
            Optional.ofNullable(VisibilityEnum.fromValue(m.get("DIMENSION_VISIBILITY")))
        );
    }

    public static SOAPBody toMdSchemaLevels(List<MdSchemaLevelsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaLevelsResponseRow(root, r)
        );
        return body;
    }

    private static void addMdSchemaLevelsResponseRow(SOAPElement root, MdSchemaLevelsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.hierarchyUniqueName().ifPresent(v -> addChildElement(row, "HIERARCHY_UNIQUE_NAME", v));
        r.levelName().ifPresent(v -> addChildElement(row, "LEVEL_NAME", v));
        r.levelUniqueName().ifPresent(v -> addChildElement(row, "LEVEL_UNIQUE_NAME", v));
        r.levelGuid().ifPresent(v -> addChildElement(row, "LEVEL_GUID", String.valueOf(v)));
        r.levelCaption().ifPresent(v -> addChildElement(row, "LEVEL_CAPTION", v));
        r.levelNumber().ifPresent(v -> addChildElement(row, "LEVEL_NUMBER", String.valueOf(v)));
        r.levelCardinality().ifPresent(v -> addChildElement(row, "LEVEL_CARDINALITY", String.valueOf(v)));
        r.levelType().ifPresent(v -> addChildElement(row, "LEVEL_TYPE", String.valueOf(v.getValue())));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.customRollupSetting().ifPresent(v -> addChildElement(row, "CUSTOM_ROLLUP_SETTINGS",
            String.valueOf(v.getValue())));
        r.levelUniqueSettings().ifPresent(v -> addChildElement(row, "LEVEL_UNIQUE_SETTINGS",
            String.valueOf(v.getValue())));
        r.levelIsVisible().ifPresent(v -> addChildElement(row, "LEVEL_IS_VISIBLE", String.valueOf(v)));
        r.levelOrderingProperty().ifPresent(v -> addChildElement(row, "LEVEL_ORDERING_PROPERTY", v));
        r.levelDbType().ifPresent(v -> addChildElement(row, "LEVEL_DBTYPE", String.valueOf(v.getValue())));
        r.levelMasterUniqueName().ifPresent(v -> addChildElement(row, "LEVEL_MASTER_UNIQUE_NAME", v));
        r.levelNameSqlColumnName().ifPresent(v -> addChildElement(row, "LEVEL_NAME_SQL_COLUMN_NAME", v));
        r.levelKeySqlColumnName().ifPresent(v -> addChildElement(row, "LEVEL_KEY_SQL_COLUMN_NAME", v));
        r.levelUniqueNameSqlColumnName().ifPresent(v -> addChildElement(row, "LEVEL_UNIQUE_NAME_SQL_COLUMN_NAME", v));
        r.levelAttributeHierarchyName().ifPresent(v -> addChildElement(row, "LEVEL_ATTRIBUTE_HIERARCHY_NAME", v));
        r.levelKeyCardinality().ifPresent(v -> addChildElement(row, "LEVEL_KEY_CARDINALITY", String.valueOf(v)));
        r.levelOrigin().ifPresent(v -> addChildElement(row, "LEVEL_ORIGIN", String.valueOf(v.getValue())));
    }

    public static MdSchemaHierarchiesRestrictionsR discoverMdSchemaHierarchiesRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new MdSchemaHierarchiesRestrictionsR(
            Optional.ofNullable(m.get("CATALOG_NAME")),
            Optional.ofNullable(m.get("SCHEMA_NAME")),
            Optional.ofNullable(m.get("CUBE_NAME")),
            Optional.ofNullable(m.get("DIMENSION_UNIQUE_NAME")),
            Optional.ofNullable(m.get("HIERARCHY_NAME")),
            Optional.ofNullable(m.get("HIERARCHY_UNIQUE_NAME")),
            Optional.ofNullable(Integer.decode(m.get("HIERARCHY_ORIGIN"))),
            Optional.ofNullable(CubeSourceEnum.fromValue(m.get("CUBE_SOURCE"))),
            Optional.ofNullable(VisibilityEnum.fromValue(m.get("HIERARCHY_VISIBILITY")))
        );
    }

    public static SOAPBody toMdSchemaHierarchies(List<MdSchemaHierarchiesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaHierarchiesResponseRow(root, r)
        );
        return body;
    }

    private static void addMdSchemaHierarchiesResponseRow(SOAPElement root, MdSchemaHierarchiesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "SCHEMA_NAME", v));
        r.cubeName().ifPresent(v -> addChildElement(row, "CUBE_NAME", v));
        r.dimensionUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_NAME", v));
        r.hierarchyName().ifPresent(v -> addChildElement(row, "HIERARCHY_NAME", v));
        r.hierarchyUniqueName().ifPresent(v -> addChildElement(row, "HIERARCHY_UNIQUE_NAME", v));
        r.hierarchyGuid().ifPresent(v -> addChildElement(row, "HIERARCHY_GUID", String.valueOf(v)));
        r.hierarchyCaption().ifPresent(v -> addChildElement(row, "HIERARCHY_CAPTION", v));
        r.dimensionType().ifPresent(v -> addChildElement(row, "DIMENSION_TYPE", String.valueOf(v.getValue())));
        r.hierarchyCardinality().ifPresent(v -> addChildElement(row, "HIERARCHY_CARDINALITY", String.valueOf(v)));
        r.defaultMember().ifPresent(v -> addChildElement(row, "DEFAULT_MEMBER", v));
        r.allMember().ifPresent(v -> addChildElement(row, "ALL_MEMBER", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.structure().ifPresent(v -> addChildElement(row, "STRUCTURE", String.valueOf(v.getValue())));
        r.isVirtual().ifPresent(v -> addChildElement(row, "IS_VIRTUAL", String.valueOf(v)));
        r.isReadWrite().ifPresent(v -> addChildElement(row, "IS_READWRITE", String.valueOf(v)));
        r.dimensionUniqueSettings().ifPresent(v -> addChildElement(row, "DIMENSION_UNIQUE_SETTINGS",
            String.valueOf(v.getValue())));
        r.dimensionMasterUniqueName().ifPresent(v -> addChildElement(row, "DIMENSION_MASTER_UNIQUE_NAME", v));
        r.dimensionIsVisible().ifPresent(v -> addChildElement(row, "DIMENSION_IS_VISIBLE", String.valueOf(v)));
        r.hierarchyOrdinal().ifPresent(v -> addChildElement(row, "HIERARCHY_ORDINAL", String.valueOf(v)));
        r.dimensionIsShared().ifPresent(v -> addChildElement(row, "DIMENSION_IS_SHARED", String.valueOf(v)));
        r.hierarchyIsVisible().ifPresent(v -> addChildElement(row, "HIERARCHY_IS_VISIBLE", String.valueOf(v)));
        r.hierarchyOrigin().ifPresent(v -> addChildElement(row, "HIERARCHY_ORIGIN", String.valueOf(v)));
        r.hierarchyDisplayFolder().ifPresent(v -> addChildElement(row, "HIERARCHY_DISPLAY_FOLDER", v));
        r.instanceSelection().ifPresent(v -> addChildElement(row, "INSTANCE_SELECTION", String.valueOf(v.getValue())));
        r.groupingBehavior().ifPresent(v -> addChildElement(row, "GROUPING_BEHAVIOR", String.valueOf(v.getValue())));
        r.structureType().ifPresent(v -> addChildElement(row, "STRUCTURE_TYPE", String.valueOf(v.getValue())));
    }

    public static DbSchemaTablesInfoRestrictionsR discoverDbSchemaTablesInfo(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DbSchemaTablesInfoRestrictionsR(
            Optional.ofNullable(m.get("TABLE_CATALOG")),
            Optional.ofNullable(m.get("TABLE_SCHEMA")),
            m.get("TABLE_NAME"),
            TableTypeEnum.fromValue(m.get("TABLE_TYPE"))
        );
    }

    public static SOAPBody toDbSchemaTablesInfo(List<DbSchemaTablesInfoResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaTablesInfoResponseRow(root, r)
        );
        return body;
    }

    private static void addDbSchemaTablesInfoResponseRow(SOAPElement root, DbSchemaTablesInfoResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "TABLE_CATALOG", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "TABLE_SCHEMA", v));
        addChildElement(row, "TABLE_NAME", r.tableName());
        addChildElement(row, "TABLE_TYPE", r.tableType());
        r.tableGuid().ifPresent(v -> addChildElement(row, "TABLE_TYPE", String.valueOf(v)));
        r.bookmarks().ifPresent(v -> addChildElement(row, "BOOKMARKS", String.valueOf(v)));
        r.bookmarkType().ifPresent(v -> addChildElement(row, "BOOKMARK_TYPE", String.valueOf(v)));
        r.bookmarkDataType().ifPresent(v -> addChildElement(row, "BOOKMARK_DATA_TYPE", String.valueOf(v)));
        r.bookmarkMaximumLength().ifPresent(v -> addChildElement(row, "BOOKMARK_MAXIMUM_LENGTH", String.valueOf(v)));
        r.bookmarkInformation().ifPresent(v -> addChildElement(row, "BOOKMARK_INFORMATION", String.valueOf(v)));
        r.tableVersion().ifPresent(v -> addChildElement(row, "TABLE_VERSION", String.valueOf(v)));
        r.cardinality().ifPresent(v -> addChildElement(row, "CARDINALITY", String.valueOf(v)));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.tablePropId().ifPresent(v -> addChildElement(row, "TABLE_PROP_ID", String.valueOf(v)));
    }

    public static DbSchemaSourceTablesRestrictionsR discoverDbSchemaSourceTablesRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DbSchemaSourceTablesRestrictionsR(
            Optional.ofNullable(m.get("TABLE_CATALOG")),
            Optional.ofNullable(m.get("TABLE_SCHEMA")),
            m.get("TABLE_NAME"),
            TableTypeEnum.fromValue(m.get("TABLE_TYPE"))
        );
    }

    public static SOAPBody toDbSchemaSourceTables(List<DbSchemaSourceTablesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaSourceTablesResponseRow(root, r)
        );
        return body;
    }

    private static void addDbSchemaSourceTablesResponseRow(SOAPElement root, DbSchemaSourceTablesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "TABLE_CATALOG", v));
        r.schemaName().ifPresent(v -> addChildElement(row, "TABLE_SCHEMA", v));
        addChildElement(row, "TABLE_NAME", r.tableName());
        addChildElement(row, "TABLE_TYPE", r.tableType().getValue());
    }

    public static DbSchemaSchemataRestrictionsR discoverDbSchemaSchemataRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DbSchemaSchemataRestrictionsR(
            m.get("CATALOG_NAME"),
            m.get("SCHEMA_NAME"),
            m.get("SCHEMA_OWNER")
        );
    }

    public static SOAPBody toDbSchemaSchemata(List<DbSchemaSchemataResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaSchemataResponseRow(root, r)
        );
        return body;
    }

    private static void addDbSchemaSchemataResponseRow(SOAPElement root, DbSchemaSchemataResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "CATALOG_NAME", r.catalogName());
        addChildElement(row, "SCHEMA_NAME", r.schemaName());
        addChildElement(row, "SCHEMA_OWNER", r.schemaOwner());
    }

    public static DbSchemaProviderTypesRestrictionsR discoverDbSchemaProviderTypesRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DbSchemaProviderTypesRestrictionsR(
            Optional.ofNullable(LevelDbTypeEnum.fromValue(m.get("DATA_TYPE"))),
            Optional.ofNullable(Boolean.valueOf(m.get("BEST_MATCH")))
        );
    }

    public static SOAPBody toDbSchemaProviderTypes(List<DbSchemaProviderTypesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaProviderTypesResponseRow(root, r)
        );
        return body;
    }

    private static void addDbSchemaProviderTypesResponseRow(SOAPElement root, DbSchemaProviderTypesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.typeName().ifPresent(v -> addChildElement(row, "TYPE_NAME", v));
        r.dataType().ifPresent(v -> addChildElement(row, "DATA_TYPE", String.valueOf(v.getValue())));
        r.columnSize().ifPresent(v -> addChildElement(row, "COLUMN_SIZE", String.valueOf(v)));
        r.literalPrefix().ifPresent(v -> addChildElement(row, "LITERAL_PREFIX", v));
        r.literalSuffix().ifPresent(v -> addChildElement(row, "LITERAL_SUFFIX", v));
        r.createParams().ifPresent(v -> addChildElement(row, "CREATE_PARAMS", v));
        r.isNullable().ifPresent(v -> addChildElement(row, "IS_NULLABLE", String.valueOf(v)));
        r.caseSensitive().ifPresent(v -> addChildElement(row, "CASE_SENSITIVE", String.valueOf(v)));
        r.searchable().ifPresent(v -> addChildElement(row, "SEARCHABLE", String.valueOf(v.getValue())));
        r.unsignedAttribute().ifPresent(v -> addChildElement(row, "UNSIGNED_ATTRIBUTE", String.valueOf(v)));
        r.fixedPrecScale().ifPresent(v -> addChildElement(row, "FIXED_PREC_SCALE", String.valueOf(v)));
        r.autoUniqueValue().ifPresent(v -> addChildElement(row, "AUTO_UNIQUE_VALUE", String.valueOf(v)));
        r.localTypeName().ifPresent(v -> addChildElement(row, "LOCAL_TYPE_NAME", v));
        r.minimumScale().ifPresent(v -> addChildElement(row, "MINIMUM_SCALE", String.valueOf(v)));
        r.maximumScale().ifPresent(v -> addChildElement(row, "MAXIMUM_SCALE", String.valueOf(v)));
        r.guid().ifPresent(v -> addChildElement(row, "GUID", String.valueOf(v)));
        r.typeLib().ifPresent(v -> addChildElement(row, "TYPE_LIB", v));
        r.version().ifPresent(v -> addChildElement(row, "VERSION", v));
        r.isLong().ifPresent(v -> addChildElement(row, "IS_LONG", String.valueOf(v)));
        r.bestMatch().ifPresent(v -> addChildElement(row, "BEST_MATCH", String.valueOf(v)));
        r.isFixedLength().ifPresent(v -> addChildElement(row, "IS_FIXEDLENGTH", String.valueOf(v)));
    }

    public static DbSchemaColumnsRestrictionsR discoverDbSchemaColumnsRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DbSchemaColumnsRestrictionsR(
            Optional.ofNullable(m.get("TABLE_CATALOG")),
            Optional.ofNullable(m.get("TABLE_SCHEMA")),
            Optional.ofNullable(m.get("TABLE_NAME")),
            Optional.ofNullable(m.get("COLUMN_NAME")),
            Optional.ofNullable(ColumnOlapTypeEnum.fromValue(m.get("COLUMN_OLAP_TYPE")))
        );
    }

    public static SOAPBody toDbSchemaColumns(List<DbSchemaColumnsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaColumnsResponseRow(root, r)
        );
        return body;
    }

    private static void addDbSchemaColumnsResponseRow(SOAPElement root, DbSchemaColumnsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.tableCatalog().ifPresent(v -> addChildElement(row, "TABLE_CATALOG", v));
        r.tableSchema().ifPresent(v -> addChildElement(row, "TABLE_SCHEMA", v));
        r.tableName().ifPresent(v -> addChildElement(row, "TABLE_NAME", v));
        r.columnName().ifPresent(v -> addChildElement(row, "COLUMN_NAME", v));
        r.columnGuid().ifPresent(v -> addChildElement(row, "COLUMN_GUID", String.valueOf(v)));
        r.columnPropId().ifPresent(v -> addChildElement(row, "COLUMN_PROPID", String.valueOf(v)));
        r.ordinalPosition().ifPresent(v -> addChildElement(row, "ORDINAL_POSITION", String.valueOf(v)));
        r.columnHasDefault().ifPresent(v -> addChildElement(row, "COLUMN_HAS_DEFAULT", String.valueOf(v)));
        r.columnDefault().ifPresent(v -> addChildElement(row, "COLUMN_DEFAULT", v));
        r.columnFlags().ifPresent(v -> addChildElement(row, "COLUMN_FLAG", String.valueOf(v.getValue())));
        r.isNullable().ifPresent(v -> addChildElement(row, "IS_NULLABLE", String.valueOf(v)));
        r.dataType().ifPresent(v -> addChildElement(row, "DATA_TYPE", String.valueOf(v)));
        r.typeGuid().ifPresent(v -> addChildElement(row, "TYPE_GUID", String.valueOf(v)));
        r.characterMaximum().ifPresent(v -> addChildElement(row, "CHARACTER_MAXIMUM_LENGTH", String.valueOf(v)));
        r.characterOctetLength().ifPresent(v -> addChildElement(row, "CHARACTER_OCTET_LENGTH", String.valueOf(v)));
        r.numericPrecision().ifPresent(v -> addChildElement(row, "NUMERIC_PRECISION", String.valueOf(v)));
        r.dateTimePrecision().ifPresent(v -> addChildElement(row, "DATETIME_PRECISION", String.valueOf(v)));
        r.characterSetCatalog().ifPresent(v -> addChildElement(row, "CHARACTER_SET_CATALOG", v));
        r.characterSetSchema().ifPresent(v -> addChildElement(row, "CHARACTER_SET_SCHEMA", v));
        r.characterSetName().ifPresent(v -> addChildElement(row, "CHARACTER_SET_NAME", v));
        r.collationCatalog().ifPresent(v -> addChildElement(row, "COLLATION_CATALOG", v));
        r.collationSchema().ifPresent(v -> addChildElement(row, "COLLATION_SCHEMA", v));
        r.collationName().ifPresent(v -> addChildElement(row, "COLLATION_NAME", v));
        r.domainCatalog().ifPresent(v -> addChildElement(row, "DOMAIN_CATALOG", v));
        r.domainSchema().ifPresent(v -> addChildElement(row, "DOMAIN_SCHEMA", v));
        r.domainName().ifPresent(v -> addChildElement(row, "DOMAIN_NAME", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.columnOlapType().ifPresent(v -> addChildElement(row, "COLUMN_OLAP_TYPE", v.name()));
    }

    public static DiscoverXmlMetaDataRestrictionsR discoverDiscoverXmlMetaDataRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DiscoverXmlMetaDataRestrictionsR(
            Optional.ofNullable(m.get("DatabaseID")),
            Optional.ofNullable(m.get("DimensionID")),
            Optional.ofNullable(m.get("CubeID")),
            Optional.ofNullable(m.get("MeasureGroupID")),
            Optional.ofNullable(m.get("PartitionID")),
            Optional.ofNullable(m.get("PerspectiveID")),
            Optional.ofNullable(m.get("DimensionPermissionID")),
            Optional.ofNullable(m.get("RoleID")),
            Optional.ofNullable(m.get("DatabasePermissionID")),
            Optional.ofNullable(m.get("MiningModelID")),
            Optional.ofNullable(m.get("MiningModelPermissionID")),
            Optional.ofNullable(m.get("DataSourceID")),
            Optional.ofNullable(m.get("MiningStructureID")),
            Optional.ofNullable(m.get("AggregationDesignID")),
            Optional.ofNullable(m.get("TraceID")),
            Optional.ofNullable(m.get("MiningStructurePermissionID")),
            Optional.ofNullable(m.get("CubePermissionID")),
            Optional.ofNullable(m.get("AssemblyID")),
            Optional.ofNullable(m.get("MdxScriptID")),
            Optional.ofNullable(m.get("DataSourceViewID")),
            Optional.ofNullable(m.get("DataSourcePermissionID")),
            Optional.ofNullable(ObjectExpansionEnum.fromValue(m.get("ObjectExpansion")))
        );
    }

    public static SOAPBody toDiscoverXmlMetaData(List<DiscoverXmlMetaDataResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDiscoverXmlMetaDataResponseRow(root, r)
        );
        return body;
    }

    private static void addDiscoverXmlMetaDataResponseRow(SOAPElement root, DiscoverXmlMetaDataResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "MetaData", r.metaData());
    }

    public static DiscoverDataSourcesRestrictionsR discoverDiscoverDataSourcesRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DiscoverDataSourcesRestrictionsR(
            m.get("DataSourceName"),
            Optional.ofNullable(m.get("DataSourceDescription")),
            Optional.ofNullable(m.get("URL")),
            Optional.ofNullable(m.get("DataSourceInfo")),
            m.get("ProviderName"),
            Optional.ofNullable(ProviderTypeEnum.fromValue(m.get("ProviderType"))),
            Optional.ofNullable(AuthenticationModeEnum.fromValue(m.get("AuthenticationMode")))
        );
    }

    public static SOAPBody toDiscoverDataSources(List<DiscoverDataSourcesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDiscoverDataSourcesResponseRow(root, r)
        );
        return body;
    }

    private static void addDiscoverDataSourcesResponseRow(SOAPElement root, DiscoverDataSourcesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "DataSourceName", r.dataSourceName());
        r.dataSourceDescription().ifPresent(v -> addChildElement(row, "DataSourceDescription", v));
        r.url().ifPresent(v -> addChildElement(row, "URL", v));
        r.dataSourceInfo().ifPresent(v -> addChildElement(row, "DataSourceInfo", v));
        addChildElement(row, "ProviderName", r.providerName());
        r.providerType().ifPresent(v -> addChildElement(row, "ProviderType", v.name()));
        r.authenticationMode().ifPresent(v -> addChildElement(row, "AuthenticationMode", v.name()));
    }

    public static DbSchemaCatalogsRestrictionsR discoverDbSchemaCatalogsRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DbSchemaCatalogsRestrictionsR(
            Optional.ofNullable(m.get("CATALOG_NAME"))
        );
    }

    public static SOAPBody toDbSchemaCatalogs(List<DbSchemaCatalogsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaCatalogsResponseRow(root, r)
        );
        return body;
    }

    private static void addDbSchemaCatalogsResponseRow(SOAPElement root, DbSchemaCatalogsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.catalogName().ifPresent(v -> addChildElement(row, "CATALOG_NAME", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.roles().ifPresent(v -> addChildElement(row, "ROLES", v));
        r.dateModified().ifPresent(v -> addChildElement(row, "DATE_MODIFIED", String.valueOf(v)));
        r.compatibilityLevel().ifPresent(v -> addChildElement(row, "COMPATIBILITY_LEVEL", String.valueOf(v)));
        r.type().ifPresent(v -> addChildElement(row, "TYPE", String.valueOf(v.getValue())));
        r.version().ifPresent(v -> addChildElement(row, "VERSION", String.valueOf(v)));
        r.databaseId().ifPresent(v -> addChildElement(row, "DATABASE_ID", v));
        r.dateQueried().ifPresent(v -> addChildElement(row, "DATE_QUERIED", String.valueOf(v)));
        r.currentlyUsed().ifPresent(v -> addChildElement(row, "CURRENTLY_USED", String.valueOf(v)));
        r.popularity().ifPresent(v -> addChildElement(row, "POPULARITY", String.valueOf(v)));
        r.weightedPopularity().ifPresent(v -> addChildElement(row, "WEIGHTEDPOPULARITY", String.valueOf(v)));
        r.clientCacheRefreshPolicy().ifPresent(v -> addChildElement(row, "CLIENTCACHEREFRESHPOLICY",
            String.valueOf(v.getValue())));
    }

    public static DiscoverSchemaRowsetsRestrictionsR discoverSchemaRowsetsRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DiscoverSchemaRowsetsRestrictionsR(
            Optional.ofNullable(m.get("SchemaName"))
        );
    }

    public static SOAPBody toDiscoverSchemaRowsets(List<DiscoverSchemaRowsetsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDiscoverSchemaRowsetsResponseRow(root, r)
        );
        return body;
    }

    private static void addDiscoverSchemaRowsetsResponseRow(SOAPElement root, DiscoverSchemaRowsetsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "SchemaName", r.schemaName());
        r.schemaGuid().ifPresent(v -> addChildElement(row, "SchemaGuid", v));
        r.restrictions().ifPresent(v -> addChildElement(row, "Restrictions", v));
        r.restrictions().ifPresent(v -> addChildElement(row, "Restrictions", v));
        r.description().ifPresent(v -> addChildElement(row, "Description", v));
        r.restrictionsMask().ifPresent(v -> addChildElement(row, "Description", String.valueOf(v)));
    }

    public static DiscoverEnumeratorsRestrictionsR discoverDiscoverEnumerators(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DiscoverEnumeratorsRestrictionsR(
            Optional.ofNullable(m.get("EnumName"))
        );
    }

    public static SOAPBody toDiscoverEnumerators(List<DiscoverEnumeratorsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDiscoverEnumeratorsResponseRow(root, r)
        );
        return body;
    }

    private static void addDiscoverEnumeratorsResponseRow(SOAPElement root, DiscoverEnumeratorsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "EnumName", r.elementName());
        r.elementDescription().ifPresent(v -> addChildElement(row, "EnumDescription", v));
        addChildElement(row, "EnumType", r.enumType());
        addChildElement(row, "ElementName", r.elementName());
        r.elementDescription().ifPresent(v -> addChildElement(row, "ElementDescription", v));
        r.elementValue().ifPresent(v -> addChildElement(row, "ElementValue", v));
    }

    public static DiscoverKeywordsRestrictionsR discoverKeywordsRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DiscoverKeywordsRestrictionsR(
            Optional.ofNullable(m.get("Keyword"))
        );
    }

    public static SOAPBody toDiscoverKeywords(List<DiscoverKeywordsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDiscoverKeywordsResponseRow(root, r)
        );
        return body;
    }

    private static void addDiscoverKeywordsResponseRow(SOAPElement root, DiscoverKeywordsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "Keyword", r.keyword());
    }

    public static DiscoverLiteralsRestrictionsR discoverLiteralsRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DiscoverLiteralsRestrictionsR(
            Optional.ofNullable(m.get("LiteralName"))
        );
    }

    public static SOAPBody toDiscoverLiterals(List<DiscoverLiteralsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDiscoverLiteralsResponseRow(root, r)
        );
        return body;
    }

    private static void addDiscoverLiteralsResponseRow(SOAPElement root, DiscoverLiteralsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        addChildElement(row, "LiteralName", r.literalName());
        addChildElement(row, "LiteralValue", r.literalValue());
        addChildElement(row, "LiteralInvalidChars", r.literalInvalidChars());
        addChildElement(row, "LiteralInvalidStartingChars", r.literalInvalidStartingChars());
        addChildElement(row, "LiteralMaxLength", String.valueOf(r.literalMaxLength()));
        addChildElement(row, "LiteralNameValue", String.valueOf(r.literalNameEnumValue().getValue()));
    }

    public static DbSchemaTablesRestrictionsR discoverDbSchemaTablesRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new DbSchemaTablesRestrictionsR(
            Optional.ofNullable(m.get("TABLE_CATALOG")),
            Optional.ofNullable(m.get("TABLE_SCHEMA")),
            Optional.ofNullable(m.get("TABLE_NAME")),
            Optional.ofNullable(m.get("TABLE_TYPE"))
        );
    }

    public static SOAPBody toDbSchemaTables(List<DbSchemaTablesResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addDbSchemaTablesResponseRow(root, r)
        );
        return body;
    }

    private static void addDbSchemaTablesResponseRow(SOAPElement root, DbSchemaTablesResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.tableCatalog().ifPresent(v -> addChildElement(row, "TABLE_CATALOG", v));
        r.tableSchema().ifPresent(v -> addChildElement(row, "TABLE_SCHEMA", v));
        r.tableName().ifPresent(v -> addChildElement(row, "TABLE_NAME", v));
        r.tableType().ifPresent(v -> addChildElement(row, "TABLE_TYPE", v));
        r.tableGuid().ifPresent(v -> addChildElement(row, "TABLE_GUID", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.tablePropId().ifPresent(v -> addChildElement(row, "TABLE_PROP_ID", String.valueOf(v)));
        r.dateCreated().ifPresent(v -> addChildElement(row, "DATE_CREATED", String.valueOf(v)));
        r.dateModified().ifPresent(v -> addChildElement(row, "DATE_MODIFIED", String.valueOf(v)));
    }

    public static MdSchemaActionsRestrictionsR discoverMdSchemaActionsRestrictions(SOAPElement restriction) {
        Map<String, String> m = getMapValuesByTag(restriction, "RestrictionList");
        return new MdSchemaActionsRestrictionsR(
            Optional.ofNullable(m.get("CATALOG_NAME")),
            Optional.ofNullable(m.get("SCHEMA_NAME")),
            m.get("CUBE_NAME"),
            Optional.ofNullable(m.get("ACTION_NAME")),
            Optional.ofNullable(ActionTypeEnum.fromValue(m.get("ACTION_TYPE"))),
            Optional.ofNullable(m.get("COORDINATE")),
            CoordinateTypeEnum.fromValue(m.get("COORDINATE_TYPE")),
            InvocationEnum.fromValue(m.get("INVOCATION")),
            Optional.ofNullable(CubeSourceEnum.fromValue(m.get("CUBE_SOURCE")))
        );
    }

    public static SOAPBody toMdSchemaActions(List<MdSchemaActionsResponseRow> rows) {
        SOAPBody body = createSOAPBody();
        SOAPElement root = addRoot(body);
        rows.forEach(r ->
            addMdSchemaActionsResponseRow(root, r)
        );
        return body;
    }

    public static Command commandtoCommand(SOAPElement element) {
        NodeList nodeList = element.getElementsByTagName("Command");
        if (nodeList != null && nodeList.getLength() > 0) {
            return getCommand(nodeList.item(0).getChildNodes());
        }
        return null;
    }

    public static SOAPBody toStatementResponse(StatementResponse statementResponse) {
        //TODO
        return null;
    }

    private static Command getCommand(NodeList nl) {
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node n = nl.item(i);
            String nodeName = n.getNodeName();
            if ("Statement".equals(nodeName)) {
                return new StatementR(n.getTextContent());
            }
            if ("Alter".equals(nodeName)) {
                return getAlterCommand(n.getChildNodes());
            }
            if ("ClearCache".equals(nodeName)) {
                return getClearCacheCommand(n.getChildNodes());
            }
            if ("Cancel".equals(nodeName)) {
                return getCancelCommand(n.getChildNodes());
            }
        }
        throw new IllegalArgumentException("Illegal command");
    }

    private static Command getCancelCommand(NodeList nl) {
        Map<String, String> map = getMapValues(nl);
        BigInteger connectionID = toBigInteger(map.get("ConnectionID"));
        String sessionID = map.get("SessionID");
        BigInteger spid = toBigInteger(map.get("SPID"));
        Boolean cancelAssociated = toBoolean(map.get("CancelAssociated"));

        return new CancelR(connectionID, sessionID, spid, cancelAssociated);
    }

    private static Boolean toBoolean(String it) {
        return it != null ? Boolean.valueOf(it) : null;
    }

    private static Long toLong(String it) {
        return it != null ? Long.valueOf(it) : null;
    }

    private static Integer toInteger(String it) {
        return it != null ? Integer.valueOf(it) : null;
    }

    private static BigInteger toBigInteger(String it) {
        return it != null ? new BigInteger(it) : null;
    }

    private static Command getClearCacheCommand(NodeList nl) {
        ObjectReference object = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null && "Object".equals(node.getNodeName())) {
                object = getObjectReference(node.getChildNodes());
                break;
            }
        }
        return new ClearCacheR(object);
    }

    private static ObjectReference getObjectReference(NodeList nl) {
        Map<String, String> map = getMapValues(nl);
        return new ObjectReferenceR(
            map.get("ServerID"),
            map.get("DatabaseID"),
            map.get("RoleID"),
            map.get("TraceID"),
            map.get("AssemblyID"),
            map.get("DimensionID"),
            map.get("DimensionPermissionID"),
            map.get("DataSourceID"),
            map.get("DataSourcePermissionID"),
            map.get("DatabasePermissionID"),
            map.get("DataSourceViewID"),
            map.get("CubeID"),
            map.get("MiningStructureID"),
            map.get("MeasureGroupID"),
            map.get("PerspectiveID"),
            map.get("CubePermissionID"),
            map.get("MdxScriptID"),
            map.get("PartitionID"),
            map.get("AggregationDesignID"),
            map.get("MiningModelID"),
            map.get("MiningModelPermissionID"),
            map.get("MiningStructurePermissionID")
        );
    }

    private static Command getAlterCommand(NodeList nl) {
        ObjectReference object = null;
        MajorObject objectDefinition = null;
        Scope scope = null;
        Boolean allowCreate = null;
        ObjectExpansion objectExpansion = null;

        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Object".equals(node.getNodeName())) {
                    object = getObjectReference(node.getChildNodes());
                }
                if ("ObjectDefinition".equals(node.getNodeName())) {
                    objectDefinition = getMajorObject(nl);
                }
                if ("Scope".equals(node.getNodeName())) {
                    scope = Scope.fromValue(node.getTextContent());
                }
                if ("AllowCreate".equals(node.getNodeName())) {
                    allowCreate = toBoolean(node.getTextContent());
                }
                if ("ObjectExpansion".equals(node.getNodeName())) {
                    objectExpansion = ObjectExpansion.fromValue(node.getTextContent());
                }
            }

        }
        return new AlterR(object,
            objectDefinition,
            scope,
            allowCreate,
            objectExpansion);
    }

    private static MajorObject getMajorObject(NodeList nl) {
        AggregationDesign aggregationDesign = null;
        Assembly assembly = null;
        Cube cube = null;
        Database database = null;
        DataSource dataSource = null;
        DataSourceView dataSourceView = null;
        Dimension dimension = null;
        MdxScript mdxScript = null;
        MeasureGroup measureGroup = null;
        MiningModel miningModel = null;
        MiningStructure miningStructure = null;
        Partition partition = null;
        Permission permission = null;
        Perspective perspective = null;
        Role role = null;
        Server server = null;
        Trace trace = null;

        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("AggregationDesign".equals(node.getNodeName())) {
                    aggregationDesign = getAggregationDesign(node.getChildNodes());
                }
                if ("Assembly".equals(node.getNodeName())) {
                    assembly = getAssembly(node.getChildNodes());
                }
                if ("Cube".equals(node.getNodeName())) {
                    cube = getCube(node.getChildNodes());
                }
                if ("Database".equals(node.getNodeName())) {
                    database = getDatabase(node.getChildNodes());
                }
                if ("DataSource".equals(node.getNodeName())) {
                    dataSource = getDataSource(node.getChildNodes());
                }
                if ("DataSourceView".equals(node.getNodeName())) {
                    dataSourceView = getDataSourceView(node.getChildNodes());
                }
                if ("Dimension".equals(node.getNodeName())) {
                    dimension = getDimension(node.getChildNodes());
                }
                if ("MdxScript".equals(node.getNodeName())) {
                    mdxScript = getMdxScript(node.getChildNodes());
                }
                if ("MeasureGroup".equals(node.getNodeName())) {
                    measureGroup = getMeasureGroup(node.getChildNodes());
                }
                if ("MiningModel".equals(node.getNodeName())) {
                    miningModel = getMiningModel(node.getChildNodes());
                }
                if ("MiningStructure".equals(node.getNodeName())) {
                    miningStructure = getMiningStructure(node.getChildNodes());
                }
                if ("Partition".equals(node.getNodeName())) {
                    partition = getPartition(node.getChildNodes());
                }
                if ("Permission".equals(node.getNodeName())) {
                    permission = getPermission(node.getChildNodes());
                }
                if ("Perspective".equals(node.getNodeName())) {
                    perspective = getPerspective(node.getChildNodes());
                }
                if ("Role".equals(node.getNodeName())) {
                    role = getRole(node.getChildNodes());
                }
                if ("Server".equals(node.getNodeName())) {
                    server = getServer(node.getChildNodes());
                }
                if ("Trace".equals(node.getNodeName())) {
                    trace = getTrace(node.getChildNodes());
                }
            }
        }
        return new MajorObjectR(
            aggregationDesign,
            assembly,
            cube,
            database,
            dataSource,
            dataSourceView,
            dimension,
            mdxScript,
            measureGroup,
            miningModel,
            miningStructure,
            partition,
            permission,
            perspective,
            role,
            server,
            trace
        );
    }

    private static Trace getTrace(NodeList nl) {
        String name = null;
        String id = null;
        Instant createdTimestamp = null;
        Instant lastSchemaUpdate = null;
        String description = null;
        List<Annotation> annotations = null;
        String logFileName = null;
        Boolean logFileAppend = null;
        Long logFileSize = null;
        Boolean audit = null;
        Boolean logFileRollover = null;
        Boolean autoRestart = null;
        Instant stopTime = null;
        TraceFilter filter = null;
        EventType eventType = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("LogFileName".equals(node.getNodeName())) {
                    logFileName = node.getTextContent();
                }
                if ("LogFileAppend".equals(node.getNodeName())) {
                    logFileAppend = toBoolean(node.getTextContent());
                }
                if ("LogFileSize".equals(node.getNodeName())) {
                    logFileSize = toLong(node.getTextContent());
                }
                if ("Audit".equals(node.getNodeName())) {
                    audit = toBoolean(node.getTextContent());
                }
                if ("LogFileRollover".equals(node.getNodeName())) {
                    logFileRollover = toBoolean(node.getTextContent());
                }
                if ("AutoRestart".equals(node.getNodeName())) {
                    autoRestart = toBoolean(node.getTextContent());
                }
                if ("StopTime".equals(node.getNodeName())) {
                    stopTime = toInstant(node.getTextContent());
                }
                if ("Filter".equals(node.getNodeName())) {
                    filter = getTraceFilter(node.getChildNodes());
                }
                if ("EventType".equals(node.getNodeName())) {
                    eventType = getEventType(node.getChildNodes());
                }
            }
        }
        return new TraceR(
            name,
            id,
            createdTimestamp,
            lastSchemaUpdate,
            description,
            annotations,
            logFileName,
            logFileAppend,
            logFileSize,
            audit,
            logFileRollover,
            autoRestart,
            stopTime,
            filter,
            eventType
        );
    }

    private static EventType getEventType(NodeList nl) {
        List<Event> events = null;
        XEvent xEvent = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Events".equals(node.getNodeName())) {
                    events = getEventList(node.getChildNodes());
                }
                if ("XEvent".equals(node.getNodeName())) {
                    xEvent = getXEvent(node.getChildNodes());
                }
            }
        }
        return new EventTypeR(events, xEvent);
    }

    private static XEvent getXEvent(NodeList nl) {
        EventSession eventSession = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("event_session".equals(node.getNodeName())) {
                    eventSession = getEventSession(node.getChildNodes());
                }
            }
        }
        return new XEventR(eventSession);
    }

    private static EventSession getEventSession(NodeList nl) {
        String templateCategory = null;
        String templateName = null;
        String templateDescription = null;
        List<Object> event = new ArrayList<>();
        List<java.lang.Object> target = new ArrayList<>();
        String name = null;
        BigInteger maxMemory = null;
        RetentionModes eventRetentionMode = null;
        Long dispatchLatency = null;
        Long maxEventSize = null;
        PartitionModes memoryPartitionMode = null;
        Boolean trackCausality = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("templateCategory".equals(node.getNodeName())) {
                    templateCategory = node.getTextContent();
                }
                if ("templateName".equals(node.getNodeName())) {
                    templateName = node.getTextContent();
                }
                if ("templateDescription".equals(node.getNodeName())) {
                    templateDescription = node.getTextContent();
                }
                if ("event".equals(node.getNodeName())) {
                    event.add(node.getTextContent());
                }
                if ("target".equals(node.getNodeName())) {
                    target.add(node.getTextContent());
                }
                NamedNodeMap nm = node.getAttributes();
                name = getAttribute(nm, "name");
                maxMemory = toBigInteger(getAttribute(nm, "maxMemory"));
                eventRetentionMode = RetentionModes.fromValue(getAttribute(nm, "eventRetentionMode"));
                dispatchLatency = toLong(getAttribute(nm, "dispatchLatency"));
                maxEventSize = toLong(getAttribute(nm, "maxEventSize"));
                memoryPartitionMode = PartitionModes.fromValue(getAttribute(nm, "memoryPartitionMode"));
                trackCausality = toBoolean(getAttribute(nm, "trackCausality"));
            }
        }
        return new EventSessionR(
            templateCategory,
            templateName,
            templateDescription,
            event,
            target,
            name,
            maxMemory,
            eventRetentionMode,
            dispatchLatency,
            maxEventSize,
            memoryPartitionMode,
            trackCausality
        );
    }

    private static List<Event> getEventList(NodeList nl) {
        List<Event> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Event".equals(node.getNodeName())) {
                    list.add(getEvent(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static Event getEvent(NodeList nl) {
        String eventID = null;
        EventColumnID columns = null;

        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("EventID".equals(node.getNodeName())) {
                    eventID = node.getTextContent();
                }
                if ("Columns".equals(node.getNodeName())) {
                    columns = getEventColumnID(node.getChildNodes());
                }
            }
        }
        return new EventR(eventID, columns);
    }

    private static EventColumnID getEventColumnID(NodeList nl) {
        List<String> columnID = new ArrayList();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("EventID".equals(node.getNodeName())) {
                    columnID.add(node.getTextContent());
                }
            }
        }
        return new EventColumnIDR(columnID);
    }

    private static TraceFilter getTraceFilter(NodeList nl) {
        NotType not = null;
        AndOrType or = null;
        AndOrType and = null;
        BoolBinop isEqual = null;
        BoolBinop notEqual = null;
        BoolBinop less = null;
        BoolBinop lessOrEqual = null;
        BoolBinop greater = null;
        BoolBinop greaterOrEqual = null;
        BoolBinop like = null;
        BoolBinop notLike = null;

        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Not".equals(node.getNodeName())) {
                    not = getNotType(node.getChildNodes());
                }
                if ("Or".equals(node.getNodeName())) {
                    or = getAndOrType(node.getChildNodes());
                }
                if ("Or".equals(node.getNodeName())) {
                    or = getAndOrType(node.getChildNodes());
                }
                if ("And".equals(node.getNodeName())) {
                    and = getAndOrType(node.getChildNodes());
                }
                if ("Equal".equals(node.getNodeName())) {
                    isEqual = getBoolBinop(node.getChildNodes());
                }
                if ("NotEqual".equals(node.getNodeName())) {
                    notEqual = getBoolBinop(node.getChildNodes());
                }
                if ("Less".equals(node.getNodeName())) {
                    less = getBoolBinop(node.getChildNodes());
                }
                if ("LessOrEqual".equals(node.getNodeName())) {
                    lessOrEqual = getBoolBinop(node.getChildNodes());
                }
                if ("Greater".equals(node.getNodeName())) {
                    greater = getBoolBinop(node.getChildNodes());
                }
                if ("GreaterOrEqual".equals(node.getNodeName())) {
                    greaterOrEqual = getBoolBinop(node.getChildNodes());
                }
                if ("Like".equals(node.getNodeName())) {
                    like = getBoolBinop(node.getChildNodes());
                }
                if ("NotLike".equals(node.getNodeName())) {
                    notLike = getBoolBinop(node.getChildNodes());
                }
            }
        }
        return new TraceFilterR(
            not,
            or,
            and,
            isEqual,
            notEqual,
            less,
            lessOrEqual,
            greater,
            greaterOrEqual,
            like,
            notLike
        );
    }

    private static BoolBinop getBoolBinop(NodeList nl) {
        String columnID = null;
        String value = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("ColumnID".equals(node.getNodeName())) {
                    columnID = node.getTextContent();
                }
                if ("Value".equals(node.getNodeName())) {
                    value = node.getTextContent();
                }
            }
        }
        return new BoolBinopR(columnID, value);
    }

    private static AndOrType getAndOrType(NodeList nl) {
        List<AndOrTypeEnum> notOrOrOrAnd = new ArrayList();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Not".equals(node.getNodeName())) {
                    notOrOrOrAnd.add(AndOrTypeEnum.Not);
                }
                if ("Or".equals(node.getNodeName())) {
                    notOrOrOrAnd.add(AndOrTypeEnum.Or);
                }
                if ("And".equals(node.getNodeName())) {
                    notOrOrOrAnd.add(AndOrTypeEnum.And);
                }
                if ("Equal".equals(node.getNodeName())) {
                    notOrOrOrAnd.add(AndOrTypeEnum.Equal);
                }
                if ("NotEqual".equals(node.getNodeName())) {
                    notOrOrOrAnd.add(AndOrTypeEnum.NotEqual);
                }
                if ("Less".equals(node.getNodeName())) {
                    notOrOrOrAnd.add(AndOrTypeEnum.Less);
                }
                if ("LessOrEqual".equals(node.getNodeName())) {
                    notOrOrOrAnd.add(AndOrTypeEnum.LessOrEqual);
                }
                if ("Greater".equals(node.getNodeName())) {
                    notOrOrOrAnd.add(AndOrTypeEnum.Greater);
                }
                if ("GreaterOrEqual".equals(node.getNodeName())) {
                    notOrOrOrAnd.add(AndOrTypeEnum.GreaterOrEqual);
                }
                if ("Like".equals(node.getNodeName())) {
                    notOrOrOrAnd.add(AndOrTypeEnum.Like);
                }
                if ("NotLike".equals(node.getNodeName())) {
                    notOrOrOrAnd.add(AndOrTypeEnum.NotLike);
                }
            }
        }
        return new AndOrTypeR(notOrOrOrAnd);
    }

    private static NotType getNotType(NodeList nl) {
        NotType not = null;
        AndOrType or = null;
        AndOrType and = null;
        BoolBinop isEqual = null;
        BoolBinop notEqual = null;
        BoolBinop less = null;
        BoolBinop lessOrEqual = null;
        BoolBinop greater = null;
        BoolBinop greaterOrEqual = null;
        BoolBinop like = null;
        BoolBinop notLike = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Not".equals(node.getNodeName())) {
                    not = getNotType(node.getChildNodes());
                }
                if ("Or".equals(node.getNodeName())) {
                    or = getAndOrType(node.getChildNodes());
                }
                if ("And".equals(node.getNodeName())) {
                    and = getAndOrType(node.getChildNodes());
                }
                if ("Equal".equals(node.getNodeName())) {
                    isEqual = getBoolBinop(node.getChildNodes());
                }
                if ("NotEqual".equals(node.getNodeName())) {
                    notEqual = getBoolBinop(node.getChildNodes());
                }
                if ("Less".equals(node.getNodeName())) {
                    less = getBoolBinop(node.getChildNodes());
                }
                if ("LessOrEqual".equals(node.getNodeName())) {
                    lessOrEqual = getBoolBinop(node.getChildNodes());
                }
                if ("Greater".equals(node.getNodeName())) {
                    greater = getBoolBinop(node.getChildNodes());
                }
                if ("GreaterOrEqual".equals(node.getNodeName())) {
                    greaterOrEqual = getBoolBinop(node.getChildNodes());
                }
                if ("Like".equals(node.getNodeName())) {
                    like = getBoolBinop(node.getChildNodes());
                }
                if ("NotLike".equals(node.getNodeName())) {
                    notLike = getBoolBinop(node.getChildNodes());
                }

            }
        }
        return new NotTypeR(
            not,
            or,
            and,
            isEqual,
            notEqual,
            less,
            lessOrEqual,
            greater,
            greaterOrEqual,
            like,
            notLike);
    }

    private static Instant toInstant(String it) {
        return it != null ? Instant.parse(it) : null;
    }

    private static Server getServer(NodeList nl) {
        String name = null;
        String id = null;
        Instant createdTimestamp = null;
        Instant lastSchemaUpdate = null;
        String description = null;
        List<Annotation> annotations = null;
        String productName = null;
        String edition = null;
        Long editionID = null;
        String version = null;
        String serverMode = null;
        String productLevel = null;
        Long defaultCompatibilityLevel = null;
        String supportedCompatibilityLevels = null;
        List<Database> databases = null;
        List<Assembly> assemblies = null;
        List<Trace> traces = null;
        List<Role> roles = null;
        List<ServerProperty> serverProperties = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("ProductName".equals(node.getNodeName())) {
                    productName = node.getTextContent();
                }
                if ("Edition".equals(node.getNodeName())) {
                    edition = node.getTextContent();
                }
                if ("EditionID".equals(node.getNodeName())) {
                    editionID = toLong(node.getTextContent());
                }
                if ("Version".equals(node.getNodeName())) {
                    version = node.getTextContent();
                }
                if ("ServerMode".equals(node.getNodeName())) {
                    serverMode = node.getTextContent();
                }
                if ("ProductLevel".equals(node.getNodeName())) {
                    productLevel = node.getTextContent();
                }
                if ("DefaultCompatibilityLevel".equals(node.getNodeName())) {
                    defaultCompatibilityLevel = toLong(node.getTextContent());
                }
                if ("SupportedCompatibilityLevels".equals(node.getNodeName())) {
                    supportedCompatibilityLevels = node.getTextContent();
                }
                if ("Databases".equals(node.getNodeName())) {
                    databases = getDatabaseList(node.getChildNodes());
                }
                if ("Assemblies".equals(node.getNodeName())) {
                    assemblies = getAssemblyList(node.getChildNodes());
                }
                if ("Traces".equals(node.getNodeName())) {
                    traces = getTraceList(node.getChildNodes());
                }
                if ("Roles".equals(node.getNodeName())) {
                    roles = getRoleList(node.getChildNodes());
                }
                if ("ServerProperties".equals(node.getNodeName())) {
                    serverProperties = getServerPropertyList(node.getChildNodes());
                }
            }
        }
        return new ServerR(
            name,
            id,
            createdTimestamp,
            lastSchemaUpdate,
            description,
            annotations,
            productName,
            edition,
            editionID,
            version,
            serverMode,
            productLevel,
            defaultCompatibilityLevel,
            supportedCompatibilityLevels,
            databases,
            assemblies,
            traces,
            roles,
            serverProperties
        );
    }

    private static List<Role> getRoleList(NodeList nl) {
        List<Role> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Role".equals(node.getNodeName())) {
                    list.add(getRole(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static List<ServerProperty> getServerPropertyList(NodeList nl) {
        List<ServerProperty> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("ServerProperty".equals(node.getNodeName())) {
                    list.add(getServerProperty(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static ServerProperty getServerProperty(NodeList nl) {
        String name = null;
        String value = null;
        Boolean requiresRestart = null;
        java.lang.Object pendingValue = null;
        java.lang.Object defaultValue = null;
        Boolean displayFlag = null;
        String type = null;

        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Name".equals(node.getNodeName())) {
                    name = node.getTextContent();
                }
                if ("Value".equals(node.getNodeName())) {
                    value = node.getTextContent();
                }
                if ("RequiresRestart".equals(node.getNodeName())) {
                    requiresRestart = toBoolean(node.getTextContent());
                }
                if ("PendingValue".equals(node.getNodeName())) {
                    pendingValue = node.getTextContent();
                }
                if ("DefaultValue".equals(node.getNodeName())) {
                    pendingValue = node.getTextContent();
                }
                if ("DisplayFlag".equals(node.getNodeName())) {
                    displayFlag = toBoolean(node.getTextContent());
                }
                if ("Type".equals(node.getNodeName())) {
                    type = node.getTextContent();
                }
            }
        }
        return new ServerPropertyR(
            name,
            value,
            requiresRestart,
            pendingValue,
            defaultValue,
            displayFlag,
            type);
    }

    private static List<Trace> getTraceList(NodeList nl) {
        List<Trace> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Trace".equals(node.getNodeName())) {
                    list.add(getTrace(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static List<Assembly> getAssemblyList(NodeList nl) {
        List<Assembly> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Assembly".equals(node.getNodeName())) {
                    list.add(getAssembly(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static List<Database> getDatabaseList(NodeList nl) {
        List<Database> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Database".equals(node.getNodeName())) {
                    list.add(getDatabase(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static Role getRole(NodeList nl) {
        String name = null;
        String id = null;
        Instant createdTimestamp = null;
        Instant lastSchemaUpdate = null;
        String description = null;
        List<Annotation> annotations = null;
        List<Member> members = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Name".equals(node.getNodeName())) {
                    name = node.getTextContent();
                }
                if ("ID".equals(node.getNodeName())) {
                    id = node.getTextContent();
                }
                if ("CreatedTimestamp".equals(node.getNodeName())) {
                    createdTimestamp = toInstant(node.getTextContent());
                }
                if ("LastSchemaUpdate".equals(node.getNodeName())) {
                    lastSchemaUpdate = toInstant(node.getTextContent());
                }
                if ("Description".equals(node.getNodeName())) {
                    description = node.getTextContent();
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = getAnnotationList(node.getChildNodes());
                }

                if ("Members".equals(node.getNodeName())) {
                    members = getMemberList(node.getChildNodes());
                }
            }
        }
        return new RoleR(
            name,
            Optional.ofNullable(id),
            Optional.ofNullable(createdTimestamp),
            Optional.ofNullable(lastSchemaUpdate),
            Optional.ofNullable(description),
            Optional.ofNullable(annotations),
            Optional.ofNullable(members)
        );
    }

    private static List<Member> getMemberList(NodeList nl) {
        List<Member> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Member".equals(node.getNodeName())) {
                    list.add(getMember(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static Member getMember(NodeList nl) {
        String name = null;
        String sid = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Name".equals(node.getNodeName())) {
                    name = node.getTextContent();
                }
                if ("Sid".equals(node.getNodeName())) {
                    sid = node.getTextContent();
                }
            }
        }
        return new MemberR(Optional.ofNullable(name),
            Optional.ofNullable(sid));
    }

    private static Perspective getPerspective(NodeList nl) {
        String name = null;
        String id = null;
        Instant createdTimestamp = null;
        Instant lastSchemaUpdate = null;
        String description = null;
        List<Annotation> annotations = null;
        List<Translation> translations = null;
        String defaultMeasure = null;
        List<PerspectiveDimension> dimensions = null;
        List<PerspectiveMeasureGroup> measureGroups = null;
        List<PerspectiveCalculation> calculations = null;
        List<PerspectiveKpi> kpis = null;
        List<PerspectiveAction> actions = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Name".equals(node.getNodeName())) {
                    name = node.getTextContent();
                }
                if ("ID".equals(node.getNodeName())) {
                    id = node.getTextContent();
                }
                if ("CreatedTimestamp".equals(node.getNodeName())) {
                    createdTimestamp = toInstant(node.getTextContent());
                }
                if ("LastSchemaUpdate".equals(node.getNodeName())) {
                    lastSchemaUpdate = toInstant(node.getTextContent());
                }
                if ("Description".equals(node.getNodeName())) {
                    description = node.getTextContent();
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = getAnnotationList(node.getChildNodes());
                }
                if ("Translations".equals(node.getNodeName())) {
                    translations = getTranslationList(node.getChildNodes());
                }
                if ("DefaultMeasure".equals(node.getNodeName())) {
                    defaultMeasure = node.getTextContent();
                }
                if ("Dimensions".equals(node.getNodeName())) {
                    dimensions = getPerspectiveDimensionList(node.getChildNodes());
                }
                if ("MeasureGroups".equals(node.getNodeName())) {
                    measureGroups = getPerspectiveMeasureGroupList(node.getChildNodes());
                }
                if ("Calculations".equals(node.getNodeName())) {
                    calculations = getPerspectiveCalculationList(node.getChildNodes());
                }
                if ("Kpis".equals(node.getNodeName())) {
                    kpis = getPerspectiveKpiList(node.getChildNodes());
                }
                if ("Actions".equals(node.getNodeName())) {
                    actions = getPerspectiveActionList(node.getChildNodes());
                }
            }
        }
        return new PerspectiveR(
            name,
            id,
            createdTimestamp,
            lastSchemaUpdate,
            description,
            annotations,
            translations,
            defaultMeasure,
            dimensions,
            measureGroups,
            calculations,
            kpis,
            actions);
    }

    private static List<PerspectiveAction> getPerspectiveActionList(NodeList nl) {
        List<PerspectiveAction> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Action".equals(node.getNodeName())) {
                    list.add(getPerspectiveAction(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static PerspectiveAction getPerspectiveAction(NodeList nl) {
        String actionID = null;
        List<Annotation> annotations = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("ActionID".equals(node.getNodeName())) {
                    actionID = node.getTextContent();
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = getAnnotationList(node.getChildNodes());
                }
            }
        }
        return new PerspectiveActionR(actionID, Optional.ofNullable(annotations));
    }

    private static List<PerspectiveKpi> getPerspectiveKpiList(NodeList nl) {
        List<PerspectiveKpi> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Action".equals(node.getNodeName())) {
                    list.add(getPerspectiveKpi(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static PerspectiveKpi getPerspectiveKpi(NodeList nl) {
        String kpiID = null;
        List<Annotation> annotations = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("KpiID".equals(node.getNodeName())) {
                    kpiID = node.getTextContent();
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = getAnnotationList(node.getChildNodes());
                }
            }
        }
        return new PerspectiveKpiR(kpiID, Optional.ofNullable(annotations));
    }

    private static List<PerspectiveCalculation> getPerspectiveCalculationList(NodeList nl) {
        List<PerspectiveCalculation> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Calculation".equals(node.getNodeName())) {
                    list.add(getPerspectiveCalculation(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static PerspectiveCalculation getPerspectiveCalculation(NodeList nl) {

        String name = null;
        String type = null;
        List<Annotation> annotations = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Name".equals(node.getNodeName())) {
                    name = node.getTextContent();
                }
                if ("Type".equals(node.getNodeName())) {
                    type = node.getTextContent();
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = getAnnotationList(node.getChildNodes());
                }
            }
        }
        return new PerspectiveCalculationR(name, type, Optional.ofNullable(annotations));
    }

    private static List<PerspectiveMeasureGroup> getPerspectiveMeasureGroupList(NodeList nl) {
        List<PerspectiveMeasureGroup> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("MeasureGroup".equals(node.getNodeName())) {
                    list.add(getPerspectiveMeasureGroup(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static PerspectiveMeasureGroup getPerspectiveMeasureGroup(NodeList nl) {
        String measureGroupID = null;
        List<PerspectiveMeasure> measures = null;
        List<Annotation> annotations = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("MeasureGroupID".equals(node.getNodeName())) {
                    measureGroupID = node.getTextContent();
                }
                if ("Measures".equals(node.getNodeName())) {
                    measures = getPerspectiveMeasureList(node.getChildNodes());
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = getAnnotationList(node.getChildNodes());
                }
            }
        }
        return new PerspectiveMeasureGroupR(
            measureGroupID,
            Optional.ofNullable(measures),
            Optional.ofNullable(annotations)
        );
    }

    private static List<PerspectiveMeasure> getPerspectiveMeasureList(NodeList nl) {
        List<PerspectiveMeasure> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Measure".equals(node.getNodeName())) {
                    list.add(getPerspectiveMeasure(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static PerspectiveMeasure getPerspectiveMeasure(NodeList nl) {
        String measureID = null;
        List<Annotation> annotations = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("MeasureID".equals(node.getNodeName())) {
                    measureID = node.getTextContent();
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = getAnnotationList(node.getChildNodes());
                }
            }
        }
        return new PerspectiveMeasureR(measureID,
            annotations);
    }

    private static List<PerspectiveDimension> getPerspectiveDimensionList(NodeList nl) {
        List<PerspectiveDimension> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Dimension".equals(node.getNodeName())) {
                    list.add(getPerspectiveDimension(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static PerspectiveDimension getPerspectiveDimension(NodeList nl) {
        String cubeDimensionID = null;
        List<PerspectiveAttribute> attributes = null;
        List<PerspectiveHierarchy> hierarchies = null;
        List<Annotation> annotations = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("CubeDimensionID".equals(node.getNodeName())) {
                    cubeDimensionID = node.getTextContent();
                }
                if ("Attributes".equals(node.getNodeName())) {
                    attributes = getPerspectiveAttributeList(node.getChildNodes());
                }
                if ("Hierarchies".equals(node.getNodeName())) {
                    hierarchies = getPerspectiveHierarchyList(node.getChildNodes());
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = getAnnotationList(node.getChildNodes());
                }
            }
        }
        return new PerspectiveDimensionR(cubeDimensionID,
            Optional.ofNullable(attributes),
            Optional.ofNullable(hierarchies),
            Optional.ofNullable(annotations));
    }

    private static List<PerspectiveHierarchy> getPerspectiveHierarchyList(NodeList nl) {
        List<PerspectiveHierarchy> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Hierarchy".equals(node.getNodeName())) {
                    list.add(getPerspectiveHierarchy(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static PerspectiveHierarchy getPerspectiveHierarchy(NodeList nl) {
        String hierarchyID = null;
        List<Annotation> annotations = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("HierarchyID".equals(node.getNodeName())) {
                    hierarchyID = node.getTextContent();
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = getAnnotationList(node.getChildNodes());
                }
            }
        }
        return new PerspectiveHierarchyR(hierarchyID,
            Optional.ofNullable(annotations));
    }

    private static List<PerspectiveAttribute> getPerspectiveAttributeList(NodeList nl) {
        List<PerspectiveAttribute> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Attribute".equals(node.getNodeName())) {
                    list.add(getPerspectiveAttribute(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static PerspectiveAttribute getPerspectiveAttribute(NodeList nl) {
        String attributeID = null;
        Boolean attributeHierarchyVisible = null;
        String defaultMember = null;
        List<Annotation> annotations = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("AttributeID".equals(node.getNodeName())) {
                    attributeID = node.getTextContent();
                }
                if ("AttributeHierarchyVisible".equals(node.getNodeName())) {
                    attributeHierarchyVisible = toBoolean(node.getTextContent());
                }
                if ("DefaultMember".equals(node.getNodeName())) {
                    defaultMember = node.getTextContent();
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = getAnnotationList(node.getChildNodes());
                }
            }
        }
        return new PerspectiveAttributeR(
            attributeID,
            Optional.ofNullable(attributeHierarchyVisible),
            Optional.ofNullable(defaultMember),
            Optional.ofNullable(annotations)
        );
    }

    private static List<Translation> getTranslationList(NodeList nl) {
        List<Translation> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Translation".equals(node.getNodeName())) {
                    list.add(getTranslation(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static Translation getTranslation(NodeList nl) {
        long language = 0;
        String caption = null;
        String description = null;
        String displayFolder = null;
        List<Annotation> annotations = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Language".equals(node.getNodeName())) {
                    language = toLong(node.getTextContent());
                }
                if ("Caption".equals(node.getNodeName())) {
                    caption = node.getTextContent();
                }
                if ("Description".equals(node.getNodeName())) {
                    description = node.getTextContent();
                }
                if ("DisplayFolder".equals(node.getNodeName())) {
                    displayFolder = node.getTextContent();
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = getAnnotationList(node.getChildNodes());
                }
            }
        }
        return new TranslationR(
            language,
            caption,
            description,
            displayFolder,
            annotations);
    }

    private static Permission getPermission(NodeList nl) {
        String name = null;
        String id = null;
        Instant createdTimestamp = null;
        Instant lastSchemaUpdate = null;
        String description = null;
        List<Annotation> annotations = null;
        String roleID = null;
        Boolean process = null;
        ReadDefinitionEnum readDefinition = null;
        ReadWritePermissionEnum read = null;
        ReadWritePermissionEnum write = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("RoleID".equals(node.getNodeName())) {
                    roleID = node.getTextContent();
                }
                if ("Process".equals(node.getNodeName())) {
                    process = toBoolean(node.getTextContent());
                }
                if ("ReadDefinition".equals(node.getNodeName())) {
                    readDefinition = ReadDefinitionEnum.fromValue(node.getTextContent());
                }
                if ("Read".equals(node.getNodeName())) {
                    read = ReadWritePermissionEnum.fromValue(node.getTextContent());
                }
                if ("Write".equals(node.getNodeName())) {
                    write = ReadWritePermissionEnum.fromValue(node.getTextContent());
                }
            }
        }
        return new PermissionR(
            name,
            Optional.ofNullable(id),
            Optional.ofNullable(createdTimestamp),
            Optional.ofNullable(lastSchemaUpdate),
            Optional.ofNullable(description),
            Optional.ofNullable(annotations),
            roleID,
            Optional.ofNullable(process),
            Optional.ofNullable(readDefinition),
            Optional.ofNullable(read),
            Optional.ofNullable(write));
    }

    private static Partition getPartition(NodeList nl) {
        String name = null;
        String id = null;
        Instant createdTimestamp = null;
        Instant lastSchemaUpdate = null;
        String description = null;
        List<Annotation> annotations = null;
        TabularBinding source = null;
        BigInteger processingPriority = null;
        String aggregationPrefix = null;
        Partition.StorageMode storageMode = null;
        String processingMode = null;
        ErrorConfiguration errorConfiguration = null;
        String storageLocation = null;
        String remoteDatasourceID = null;
        String slice = null;
        ProactiveCaching proactiveCaching = null;
        String type = null;
        Long estimatedSize = null;
        Long estimatedRows = null;
        Partition.CurrentStorageMode currentStorageMode = null;
        String aggregationDesignID = null;
        List<AggregationInstance> aggregationInstances = null;
        DataSourceViewBinding aggregationInstanceSource = null;
        Instant lastProcessed = null;
        String state = null;
        Integer stringStoresCompatibilityLevel = null;
        Integer currentStringStoresCompatibilityLevel = null;
        String directQueryUsage = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Source".equals(node.getNodeName())) {
                    source = getTabularBinding(node.getChildNodes());
                }
                if ("ProcessingPriority".equals(node.getNodeName())) {
                    processingPriority = toBigInteger(node.getTextContent());
                }
                if ("AggregationPrefix".equals(node.getNodeName())) {
                    aggregationPrefix = node.getTextContent();
                }
                if ("StorageMode".equals(node.getNodeName())) {
                    storageMode = getPartitionStorageMode(node.getChildNodes());
                }
                if ("ProcessingMode".equals(node.getNodeName())) {
                    processingMode = node.getTextContent();
                }
                if ("ErrorConfiguration".equals(node.getNodeName())) {
                    errorConfiguration = getErrorConfiguration(node.getChildNodes());
                }
                if ("StorageLocation".equals(node.getNodeName())) {
                    storageLocation = node.getTextContent();
                }
                if ("RemoteDatasourceID".equals(node.getNodeName())) {
                    remoteDatasourceID = node.getTextContent();
                }
                if ("Slice".equals(node.getNodeName())) {
                    slice = node.getTextContent();
                }
                if ("ProactiveCaching".equals(node.getNodeName())) {
                    proactiveCaching = getProactiveCaching(node.getChildNodes());
                }
                if ("Type".equals(node.getNodeName())) {
                    type = node.getTextContent();
                }
                if ("EstimatedSize".equals(node.getNodeName())) {
                    estimatedSize = toLong(node.getTextContent());
                }
                if ("EstimatedRows".equals(node.getNodeName())) {
                    estimatedRows = toLong(node.getTextContent());
                }
                if ("CurrentStorageMode".equals(node.getNodeName())) {
                    currentStorageMode = getPartitionCurrentStorageMode(node.getChildNodes());
                }
                if ("AggregationDesignID".equals(node.getNodeName())) {
                    aggregationDesignID = node.getTextContent();
                }
                if ("AggregationInstances".equals(node.getNodeName())) {
                    aggregationInstances = getAggregationInstanceList(node.getChildNodes());
                }
                if ("AggregationInstanceSource".equals(node.getNodeName())) {
                    aggregationInstanceSource = getDataSourceViewBinding(node.getChildNodes());
                }
                if ("LastProcessed".equals(node.getNodeName())) {
                    lastProcessed = toInstant(node.getTextContent());
                }
                if ("State".equals(node.getNodeName())) {
                    state = node.getTextContent();
                }
                if ("StringStoresCompatibilityLevel".equals(node.getNodeName())) {
                    stringStoresCompatibilityLevel = toInteger(node.getTextContent());
                }
                if ("CurrentStringStoresCompatibilityLevel".equals(node.getNodeName())) {
                    currentStringStoresCompatibilityLevel = toInteger(node.getTextContent());
                }
                if ("DirectQueryUsage".equals(node.getNodeName())) {
                    directQueryUsage = node.getTextContent();
                }
            }
        }

        return new PartitionR(
            name,
            id,
            createdTimestamp,
            lastSchemaUpdate,
            description,
            annotations,
            source,
            processingPriority,
            aggregationPrefix,
            storageMode,
            processingMode,
            errorConfiguration,
            storageLocation,
            remoteDatasourceID,
            slice,
            proactiveCaching,
            type,
            estimatedSize,
            estimatedRows,
            currentStorageMode,
            aggregationDesignID,
            aggregationInstances,
            aggregationInstanceSource,
            lastProcessed,
            state,
            stringStoresCompatibilityLevel,
            currentStringStoresCompatibilityLevel,
            directQueryUsage
        );
    }

    private static DataSourceViewBinding getDataSourceViewBinding(NodeList childNodes) {
        //TODO
        return null;
    }

    private static List<AggregationInstance> getAggregationInstanceList(NodeList nl) {
        List<AggregationInstance> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("AggregationInstance".equals(node.getNodeName())) {
                    list.add(getAggregationInstance(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static AggregationInstance getAggregationInstance(NodeList nl) {
        //TODO
        String id = null;
        String name = null;
        String aggregationType = null;
        TabularBinding source = null;
        List<AggregationInstanceDimension> dimensions = null;
        List<AggregationInstanceMeasure> measures = null;
        List<Annotation> annotations = null;
        String description = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("AggregationType".equals(node.getNodeName())) {
                    aggregationType = node.getTextContent();
                }
                if ("Source".equals(node.getNodeName())) {
                    source = getTabularBinding(node.getChildNodes());
                }
                if ("Dimensions".equals(node.getNodeName())) {
                    dimensions = getAggregationInstanceDimensionList(node.getChildNodes());
                }
                if ("Measures".equals(node.getNodeName())) {
                    measures = getAggregationInstanceMeasureList(node.getChildNodes());
                }
            }
        }
        return new AggregationInstanceR(
            id,
            name,
            aggregationType,
            source,
            dimensions,
            measures,
            annotations,
            description);
    }

    private static List<AggregationInstanceMeasure> getAggregationInstanceMeasureList(NodeList childNodes) {
        //TODO
        return null;
    }

    private static List<AggregationInstanceDimension> getAggregationInstanceDimensionList(NodeList childNodes) {
        //TODO
        return null;
    }

    private static Partition.CurrentStorageMode getPartitionCurrentStorageMode(NodeList childNodes) {
        //TODO
        return null;
    }

    private static ProactiveCaching getProactiveCaching(NodeList childNodes) {
        //TODO
        return null;
    }

    private static ErrorConfiguration getErrorConfiguration(NodeList childNodes) {
        //TODO
        return null;
    }

    private static Partition.StorageMode getPartitionStorageMode(NodeList childNodes) {
        //TODO
        return null;
    }

    private static TabularBinding getTabularBinding(NodeList childNodes) {
        //TODO
        return null;
    }

    private static MiningStructure getMiningStructure(NodeList childNodes) {
        //TODO
        return null;
    }

    private static MiningModel getMiningModel(NodeList childNodes) {
        //TODO
        return null;
    }

    private static MeasureGroup getMeasureGroup(NodeList childNodes) {
        //TODO
        return null;
    }

    private static MdxScript getMdxScript(NodeList childNodes) {
        //TODO
        return null;
    }

    private static Dimension getDimension(NodeList childNodes) {
        //TODO
        return null;
    }

    private static DataSourceView getDataSourceView(NodeList childNodes) {
        //TODO
        return null;
    }

    private static DataSource getDataSource(NodeList childNodes) {
        //TODO
        return null;
    }

    private static Database getDatabase(NodeList childNodes) {
        //TODO
        return null;
    }

    private static Cube getCube(NodeList childNodes) {
        //TODO
        return null;
    }

    private static Assembly getAssembly(NodeList childNodes) {
        //TODO
        return null;
    }

    private static AggregationDesign getAggregationDesign(NodeList nl) {
        String name = null;
        Optional<String> id = Optional.empty();
        Optional<Instant> createdTimestamp = Optional.empty();
        Optional<Instant> lastSchemaUpdate = Optional.empty();
        Optional<String> description = Optional.empty();
        Optional<List<Annotation>> annotations = Optional.empty();
        Optional<Long> estimatedRows = Optional.empty();
        Optional<List<AggregationDesignDimension>> dimensions = Optional.empty();
        Optional<List<Aggregation>> aggregations = Optional.empty();
        Optional<Integer> estimatedPerformanceGain = Optional.empty();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Name".equals(node.getNodeName())) {
                    name = node.getTextContent();
                }
                if ("ID".equals(node.getNodeName())) {
                    id = Optional.ofNullable(node.getTextContent());
                }
                if ("CreatedTimestamp".equals(node.getNodeName())) {
                    createdTimestamp = Optional.ofNullable(toInstant(node.getTextContent()));
                }
                if ("LastSchemaUpdate".equals(node.getNodeName())) {
                    lastSchemaUpdate = Optional.ofNullable(toInstant(node.getTextContent()));
                }
                if ("Description".equals(node.getNodeName())) {
                    description = Optional.ofNullable(node.getTextContent());
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = Optional.ofNullable(getAnnotationList(node.getChildNodes()));
                }
                if ("EstimatedRows".equals(node.getNodeName())) {
                    estimatedRows = Optional.ofNullable(toLong(node.getTextContent()));
                }
                if ("Dimensions".equals(node.getNodeName())) {
                    dimensions = Optional.ofNullable(getAggregationDesignDimensionList(node.getChildNodes()));
                }
                if ("Aggregations".equals(node.getNodeName())) {
                    aggregations = Optional.ofNullable(getAggregationList(node.getChildNodes()));
                }
                if ("EstimatedPerformanceGain".equals(node.getNodeName())) {
                    estimatedPerformanceGain = Optional.ofNullable(toInteger(node.getTextContent()));
                }
            }
        }
        return new AggregationDesignR(
            name,
            id,
            createdTimestamp,
            lastSchemaUpdate,
            description,
            annotations,
            estimatedRows,
            dimensions,
            aggregations,
            estimatedPerformanceGain);
    }

    private static List<Annotation> getAnnotationList(NodeList nl) {
        List<Annotation> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Aggregation".equals(node.getNodeName())) {
                    list.add(getAnnotation(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static Annotation getAnnotation(NodeList nl) {
        String name = null;
        Optional<String> visibility = Optional.empty();
        Optional<java.lang.Object> value = Optional.empty();

        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Name".equals(node.getNodeName())) {
                    name = node.getTextContent();
                }
                if ("Visibility".equals(node.getNodeName())) {
                    name = node.getTextContent();
                }
                if ("Value".equals(node.getNodeName())) {
                    value = Optional.ofNullable(node.getTextContent());
                }
            }
        }

        return new AnnotationR(
            name,
            visibility,
            value);
    }

    private static List<Aggregation> getAggregationList(NodeList nl) {
        List<Aggregation> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Aggregation".equals(node.getNodeName())) {
                    list.add(getAggregation(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static Aggregation getAggregation(NodeList nl) {
        Optional<String> id = Optional.empty();
        String name = null;
        Optional<List<AggregationDimension>> dimensions = Optional.empty();
        Optional<List<Annotation>> annotations = Optional.empty();
        Optional<String> description = Optional.empty();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("ID".equals(node.getNodeName())) {
                    id = Optional.ofNullable(node.getTextContent());
                }
                if ("Name".equals(node.getNodeName())) {
                    name = node.getTextContent();
                }
                if ("Description".equals(node.getNodeName())) {
                    description = Optional.ofNullable(node.getTextContent());
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = Optional.ofNullable(getAnnotationList(node.getChildNodes()));
                }
                if ("Dimensions".equals(node.getNodeName())) {
                    dimensions = Optional.ofNullable(getAggregationDimensionList(node.getChildNodes()));
                }
            }
        }
        return new AggregationR(id, name, dimensions, annotations, description);
    }

    private static List<AggregationDimension> getAggregationDimensionList(NodeList nl) {
        List<AggregationDimension> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("Dimension".equals(node.getNodeName())) {
                    list.add(getAggregationDimension(node.getChildNodes()));
                }
            }
        }
        return list;
    }

    private static AggregationDimension getAggregationDimension(NodeList nl) {
        String cubeDimensionID = null;
        Optional<List<AggregationAttribute>> attributes = Optional.empty();
        Optional<List<Annotation>> annotations = Optional.empty();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("CubeDimensionID".equals(node.getNodeName())) {
                    cubeDimensionID = node.getTextContent();
                }
                if ("Attributes".equals(node.getNodeName())) {
                    attributes = Optional.ofNullable(getAggregationAttributeList(node.getChildNodes()));
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = Optional.ofNullable(getAnnotationList(node.getChildNodes()));
                }
            }
        }
        return new AggregationDimensionR(cubeDimensionID, attributes, annotations);
    }

    private static List<AggregationAttribute> getAggregationAttributeList(NodeList nl) {
        List<AggregationAttribute> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null && "Attribute".equals(node.getNodeName())) {
                list.add(getAggregationAttribute(node.getChildNodes()));
            }
        }
        return list;
    }

    private static AggregationAttribute getAggregationAttribute(NodeList nl) {
        String attributeID = null;
        List<Annotation> annotations = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("AttributeID".equals(node.getNodeName())) {
                    attributeID = node.getTextContent();
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = getAnnotationList(node.getChildNodes());
                }
            }
        }
        return new AggregationAttributeR(attributeID, Optional.ofNullable(annotations));
    }

    private static List<AggregationDesignDimension> getAggregationDesignDimensionList(NodeList nl) {
        List<AggregationDesignDimension> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null && "Dimension".equals(node.getNodeName())) {
                list.add(getAggregationDesignDimension(node.getChildNodes()));
            }
        }
        return list;
    }

    private static AggregationDesignDimension getAggregationDesignDimension(NodeList nl) {
        String cubeDimensionID = null;
        List<AggregationDesignAttribute> attributes = null;
        List<Annotation> annotations = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("CubeDimensionID".equals(node.getNodeName())) {
                    cubeDimensionID = node.getTextContent();
                }
                if ("Attributes".equals(node.getNodeName())) {
                    attributes = getAggregationDesignAttributeList(node.getChildNodes());
                }
                if ("Annotations".equals(node.getNodeName())) {
                    annotations = getAnnotationList(node.getChildNodes());
                }
            }
        }
        return new AggregationDesignDimensionR(
            cubeDimensionID,
            Optional.ofNullable(attributes),
            Optional.ofNullable(annotations)
        );
    }

    private static List<AggregationDesignAttribute> getAggregationDesignAttributeList(NodeList nl) {
        List<AggregationDesignAttribute> list = new ArrayList<>();
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null && "Attribute".equals(node.getNodeName())) {
                list.add(getAggregationDesignAttribute(node.getChildNodes()));
            }
        }
        return list;
    }

    private static AggregationDesignAttribute getAggregationDesignAttribute(NodeList nl) {
        String attributeID = null;
        Long estimatedCount = null;
        for (int i = 0; i < nl.getLength(); i++) {
            org.w3c.dom.Node node = nl.item(i);
            if (node != null) {
                if ("AttributeID".equals(node.getNodeName())) {
                    attributeID = node.getTextContent();
                }
                if ("EstimatedCount".equals(node.getNodeName())) {
                    estimatedCount = toLong(node.getTextContent());
                }
            }
        }
        return new AggregationDesignAttributeR(attributeID, Optional.ofNullable(estimatedCount));
    }

    private static void addMdSchemaActionsResponseRow(SOAPElement root, MdSchemaActionsResponseRow r) {
        SOAPElement row = addChildElement(root, ROW);
        r.actionName().ifPresent(v -> addChildElement(row, "ACTION_NAME", v));
        r.actionType().ifPresent(v -> addChildElement(row, "ACTION_TYPE", String.valueOf(v.getValue())));
        addChildElement(row, "COORDINATE", r.coordinate());
        addChildElement(row, "COORDINATE_TYPE", String.valueOf(r.coordinateType().getValue()));
        r.actionCaption().ifPresent(v -> addChildElement(row, "ACTION_CAPTION", v));
        r.description().ifPresent(v -> addChildElement(row, "DESCRIPTION", v));
        r.content().ifPresent(v -> addChildElement(row, "CONTENT", v));
        r.application().ifPresent(v -> addChildElement(row, "APPLICATION", v));
        r.invocation().ifPresent(v -> addChildElement(row, "INVOCATION", String.valueOf(v.getValue())));
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

    private static String getAttribute(NamedNodeMap namedNodeMap, String name) {
        if (namedNodeMap != null) {
            org.w3c.dom.Node nameNode = namedNodeMap.getNamedItem(name);
            if (nameNode != null) {
                return nameNode.getTextContent();
            }
        }
        return null;
    }
}
