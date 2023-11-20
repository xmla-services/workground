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

import jakarta.xml.bind.JAXBException;
import mondrian.olap.MondrianServer;
import mondrian.xmla.PropertyDefinition;
import mondrian.xmla.XmlaConstants;
import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier.jaxb.SerializerModifier;
import org.eclipse.daanse.olap.xmla.bridge.ContextListSupplyer;
import org.eclipse.daanse.xmla.api.annotation.Operation;
import org.eclipse.daanse.xmla.api.common.enums.LiteralNameEnumValueEnum;
import org.eclipse.daanse.xmla.api.discover.dbschema.catalogs.DbSchemaCatalogsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.columns.DbSchemaColumnsRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.providertypes.DbSchemaProviderTypesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.schemata.DbSchemaSchemataRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.sourcetables.DbSchemaSourceTablesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.tables.DbSchemaTablesRequest;
import org.eclipse.daanse.xmla.api.discover.dbschema.tablesinfo.DbSchemaTablesInfoRequest;
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
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.kpis.MdSchemaKpisRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroupdimensions.MdSchemaMeasureGroupDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.measuregroups.MdSchemaMeasureGroupsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.measures.MdSchemaMeasuresRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.members.MdSchemaMembersRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.sets.MdSchemaSetsRequest;
import org.eclipse.daanse.xmla.api.xmla.Restriction;
import org.eclipse.daanse.xmla.model.record.discover.discover.datasources.DiscoverDataSourcesResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.keywords.DiscoverKeywordsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.literals.DiscoverLiteralsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.properties.DiscoverPropertiesResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.schemarowsets.DiscoverSchemaRowsetsResponseRowR;
import org.eclipse.daanse.xmla.model.record.discover.discover.xmlmetadata.DiscoverXmlMetaDataResponseRowR;
import org.eclipse.daanse.xmla.model.record.xmla.RestrictionR;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OtherDiscoverService {

    private static final String DBLITERAL = "DBLITERAL_";
    private static List<Class> classes = List.of(
        //DbSchema
        DbSchemaCatalogsRequest.class,
        DbSchemaColumnsRequest.class,
        DbSchemaProviderTypesRequest.class,
        DbSchemaSchemataRequest.class,
        DbSchemaSourceTablesRequest.class,
        DbSchemaTablesRequest.class,
        DbSchemaTablesInfoRequest.class,
        //Discover
        DiscoverDataSourcesRequest.class,
        DiscoverEnumeratorsRequest.class,
        DiscoverKeywordsRequest.class,
        DiscoverLiteralsRequest.class,
        DiscoverPropertiesRequest.class,
        DiscoverSchemaRowsetsRequest.class,
        DiscoverXmlMetaDataRequest.class,
        //MdSchema
        MdSchemaActionsRequest.class,
        MdSchemaCubesRequest.class,
        MdSchemaDimensionsRequest.class,
        MdSchemaFunctionsRequest.class,
        MdSchemaHierarchiesRequest.class,
        MdSchemaKpisRequest.class,
        MdSchemaLevelsRequest.class,
        MdSchemaMeasureGroupDimensionsRequest.class,
        MdSchemaMeasureGroupsRequest.class,
        MdSchemaMeasuresRequest.class,
        MdSchemaMembersRequest.class,
        MdSchemaPropertiesRequest.class,
        MdSchemaSetsRequest.class
    );
    private ContextListSupplyer contextsListSupplyer;


    public OtherDiscoverService(ContextListSupplyer contextsListSupplyer) {
        this.contextsListSupplyer = contextsListSupplyer;

    }

    public List<DiscoverDataSourcesResponseRow> dataSources(DiscoverDataSourcesRequest request) {
        List<DiscoverDataSourcesResponseRow> result = new ArrayList<>();
        List<Context> contexts = this.contextsListSupplyer.get();
        for (Context context : contexts) {
            DataSource dataSource = context.getDataSource();

            result.add(new DiscoverDataSourcesResponseRowR(
                context.getName(),
                context.getDescription(),
                //TODO
                Optional.empty(),
                Optional.empty(),
                null,
                Optional.empty(),
                Optional.empty()
            ));
        }
        return result;
    }

    public List<DiscoverEnumeratorsResponseRow> discoverEnumerators(DiscoverEnumeratorsRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<DiscoverKeywordsResponseRow> discoverKeywords(DiscoverKeywordsRequest request) {
        List<DiscoverKeywordsResponseRow> result = new ArrayList<>();
        MondrianServer mondrianServer = MondrianServer.forId(null);
        for (String keyword : mondrianServer.getKeywords()) {
            result.add(new DiscoverKeywordsResponseRowR(keyword));
        }
        return result;
    }

    public List<DiscoverLiteralsResponseRow> discoverLiterals(DiscoverLiteralsRequest request) {
        List<DiscoverLiteralsResponseRow> result = new ArrayList<>();
        for (XmlaConstants.Literal anEnum : XmlaConstants.Literal.values()) {
            result.add(new DiscoverLiteralsResponseRowR(DBLITERAL + anEnum.name(),
                anEnum.getLiteralValue(),
                anEnum.getLiteralInvalidChars(),
                anEnum.getLiteralInvalidStartingChars(),
                anEnum.getLiteralMaxLength(),
                LiteralNameEnumValueEnum.fromValue(anEnum.getLiteralNameEnumValue())));
        }
        return result;
    }

    public List<DiscoverPropertiesResponseRow> discoverProperties(DiscoverPropertiesRequest request) {
        Optional<String> propertyName = request.restrictions().propertyName();
        Optional<String> properetyCatalog = request.properties().catalog();
        List<DiscoverPropertiesResponseRow> result = new ArrayList<>();
        for (PropertyDefinition propertyDefinition
            : PropertyDefinition.class.getEnumConstants()) {
            if (propertyName.isPresent() && !propertyName.get().equals(propertyDefinition.name())) {
                continue;
            }
            String propertyValue = "";
            if (propertyDefinition.name().equals(PropertyDefinition.Catalog.name())) {
                List<String> catalogs = contextsListSupplyer.get().stream()
                    .map(c -> c.getConnection().getCatalogName()).toList();
                if (properetyCatalog.isPresent()) {
                    for (String catalog : catalogs) {
                        if (catalog.equals(properetyCatalog.get())) {
                            propertyValue = catalog;
                            break;
                        }
                    }
                } else if (!catalogs.isEmpty()) {
                    propertyValue = catalogs.get(0);
                }
            } else {
                propertyValue = propertyDefinition.getValue();
            }
            result.add(new DiscoverPropertiesResponseRowR(
                propertyDefinition.name(),
                Optional.ofNullable(propertyDefinition.getDescription()),
                Optional.of(propertyDefinition.getType().name()),
                propertyDefinition.getAccess().name(),
                Optional.of(false),
                Optional.ofNullable(propertyValue)));
        }
        return result;
    }

    public List<DiscoverSchemaRowsetsResponseRow> discoverSchemaRowsets(DiscoverSchemaRowsetsRequest request) {
        Optional<String> schemaName = request.restrictions().schemaName();
        List<DiscoverSchemaRowsetsResponseRow> result = new ArrayList<>();
        String desc =null;
        //TODO get properties from resources
        for (Class c : classes) {
            Operation o = (Operation) c.getAnnotation(
                Operation.class);
            if(!schemaName.isPresent() || schemaName.get().equals(o.name())) {
                Method[] methods = c.getMethods();
                List<Restriction> restrictions = new ArrayList<>();
                for (Method method : methods) {
                    if (method.getName().equals("restrictions")) {
                        Class cl = method.getReturnType();
                        Method[] restrictionMethods = cl.getMethods();
                        for (Method m : restrictionMethods) {
                            if (m.isAnnotationPresent(org.eclipse.daanse.xmla.api.annotation.Restriction.class)) {
                                org.eclipse.daanse.xmla.api.annotation.Restriction restriction =
                                    m.getAnnotation(org.eclipse.daanse.xmla.api.annotation.Restriction.class);
                                restrictions.add(new RestrictionR(restriction.name(), restriction.type()));
                            }
                        }
                    }
                }
                result.add(new DiscoverSchemaRowsetsResponseRowR(o.name(),
                    Optional.ofNullable(o.guid()),
                    Optional.ofNullable(restrictions),
                    Optional.of((desc == null) ? "" : desc),
                    Optional.empty()));
            }
        }
        return result;
    }

    public List<DiscoverXmlMetaDataResponseRow> xmlMetaData(DiscoverXmlMetaDataRequest request) {
        List<DiscoverXmlMetaDataResponseRow> result = new ArrayList<>();
        Optional<String> databaseId = request.restrictions().databaseId();
        if (databaseId.isPresent()) {
            Optional<Context> oContext = contextsListSupplyer.tryGetFirstByName(databaseId.get());
            if (oContext.isPresent()) {
                Context context = oContext.get();
                for (DatabaseMappingSchemaProvider p : context.getDatabaseMappingSchemaProviders()) {
                    MappingSchema schema = p.get();
                    SerializerModifier serializerModifier = new SerializerModifier(schema);
                    try {
                        result.add(new DiscoverXmlMetaDataResponseRowR(serializerModifier.getXML()));
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}
