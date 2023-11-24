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

import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingPrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRole;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;
import org.eclipse.daanse.olap.xmla.bridge.ContextGroupXmlaServiceConfig;
import org.eclipse.daanse.olap.xmla.bridge.ContextsSupplyerImpl;
import org.eclipse.daanse.xmla.api.discover.Properties;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.datasources.DiscoverDataSourcesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.enumerators.DiscoverEnumeratorsRestrictions;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.keywords.DiscoverKeywordsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.properties.DiscoverPropertiesRestrictions;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsRequest;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsResponseRow;
import org.eclipse.daanse.xmla.api.discover.discover.schemarowsets.DiscoverSchemaRowsetsRestrictions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OtherDiscoverServiceTest {
    @Mock
    private Context context1;

    @Mock
    private Context context2;

    @Mock
    private DatabaseMappingSchemaProvider dmsp1;

    @Mock
    private DatabaseMappingSchemaProvider dmsp2;

    @Mock
    private MappingSchema schema1;

    @Mock
    private MappingSchema schema2;

    @Mock
    private MappingRole role1;

    @Mock
    private MappingRole role2;

    @Mock
    private MappingCube cube1;

    @Mock
    private MappingCube cube2;

    @Mock
    private MappingPrivateDimension dimension1;

    @Mock
    private MappingPrivateDimension dimension2;

    @Mock
    private MappingHierarchy hierarchy1;

    @Mock
    private MappingHierarchy hierarchy2;

    @Mock
    private MappingMeasure measure;

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    private OtherDiscoverService service;

    private ContextsSupplyerImpl cls;

    private ContextGroupXmlaServiceConfig config;


    @BeforeEach
    void setup() {
        cls = Mockito.spy(new ContextsSupplyerImpl(List.of()));
        config = mock(ContextGroupXmlaServiceConfig.class);

        service = new OtherDiscoverService(cls, config);
    }

    @Test
    void dataSources() throws SQLException {
        when(cls.get()).thenReturn(List.of(context1, context2));

        DiscoverDataSourcesRequest request = mock(DiscoverDataSourcesRequest.class);

        when(context1.getName()).thenReturn("bar");
        when(context1.getDescription()).thenReturn(Optional.of("barDescription"));
        when(context2.getName()).thenReturn("foo");
        when(context2.getDescription()).thenReturn(Optional.of("fooDescription"));
        when(context2.getDataSource()).thenReturn(dataSource);

        List<DiscoverDataSourcesResponseRow> rows = service.dataSources(request);
        verify(context1, times(1)).getName();
        verify(context2, times(1)).getName();
        assertThat(rows).isNotNull().hasSize(2);
        DiscoverDataSourcesResponseRow row = rows.get(0);
        assertThat(row).isNotNull();
        assertThat(row.dataSourceName()).isEqualTo("bar");
        assertThat(row.dataSourceDescription()).contains("barDescription");
        assertThat(row.url()).isEmpty();
        assertThat(row.dataSourceInfo()).isEmpty();
        assertThat(row.providerName()).isNull();
        assertThat(row.providerType()).isEmpty();
        assertThat(row.authenticationMode()).isEmpty();
        row = rows.get(1);
        assertThat(row).isNotNull();
        assertThat(row.dataSourceName()).isEqualTo("foo");
        assertThat(row.dataSourceDescription()).contains("fooDescription");
        assertThat(row.url()).isEmpty();
        assertThat(row.dataSourceInfo()).isEmpty();
        assertThat(row.providerName()).isNull();
        assertThat(row.providerType()).isEmpty();
        assertThat(row.authenticationMode()).isEmpty();

    }

    @Test
    void discoverEnumerators() {
        DiscoverEnumeratorsRequest request = mock(DiscoverEnumeratorsRequest.class);
        DiscoverEnumeratorsRestrictions restrictions = mock(DiscoverEnumeratorsRestrictions.class);

        List<DiscoverEnumeratorsResponseRow> rows = service.discoverEnumerators(request);
        assertThat(rows).isNotNull().hasSize(15);
    }

    @Test
    void discoverKeywords() {
        DiscoverKeywordsRequest request = mock(DiscoverKeywordsRequest.class);
        List<DiscoverKeywordsResponseRow> rows = service.discoverKeywords(request);
        assertThat(rows).isNotNull().hasSize(256);

    }

    @Test
    void discoverLiterals() {
        //TODO
    }

    @Test
    void discoverProperties() {
        DiscoverPropertiesRequest request = mock(DiscoverPropertiesRequest.class);
        DiscoverPropertiesRestrictions restrictions = mock(DiscoverPropertiesRestrictions.class);
        Properties properties = mock(Properties.class);
        when(properties.catalog()).thenReturn(Optional.empty());
        when(request.restrictions()).thenReturn(restrictions);
        when(request.properties()).thenReturn(properties);

        List<DiscoverPropertiesResponseRow> rows = service.discoverProperties(request);
        assertThat(rows).isNotNull().hasSize(29);
    }

    @Test
    void discoverSchemaRowsets() {
        //when(cls.get()).thenReturn(List.of(context1, context2));

        DiscoverSchemaRowsetsRequest request = mock(DiscoverSchemaRowsetsRequest.class);
        DiscoverSchemaRowsetsRestrictions restrictions = mock(DiscoverSchemaRowsetsRestrictions.class);
        when(request.restrictions()).thenReturn(restrictions);

        List<DiscoverSchemaRowsetsResponseRow> rows = service.discoverSchemaRowsets(request);
        assertThat(rows).isNotNull().hasSize(27);
    }

    @Test
    void discoverSchemaRowsetsWithRestriction() {
        //when(cls.get()).thenReturn(List.of(context1, context2));

        DiscoverSchemaRowsetsRequest request = mock(DiscoverSchemaRowsetsRequest.class);
        DiscoverSchemaRowsetsRestrictions restrictions = mock(DiscoverSchemaRowsetsRestrictions.class);
        when(restrictions.schemaName()).thenReturn(Optional.of("DISCOVER_SCHEMA_ROWSETS"));
        when(request.restrictions()).thenReturn(restrictions);

        List<DiscoverSchemaRowsetsResponseRow> rows = service.discoverSchemaRowsets(request);
        assertThat(rows).isNotNull().hasSize(1);
        assertThat(rows.get(0)).isNotNull();
        assertThat(rows.get(0).schemaName()).isEqualTo("DISCOVER_SCHEMA_ROWSETS");
    }

    @Test
    void xmlMetaData() {
        //TODO
    }
}
