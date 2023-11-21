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

import org.eclipse.daanse.olap.api.Connection;
import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.olap.api.element.Cube;
import org.eclipse.daanse.olap.api.element.Dimension;
import org.eclipse.daanse.olap.api.element.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingPrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;
import org.eclipse.daanse.olap.xmla.bridge.ContextsSupplyerImpl;
import org.eclipse.daanse.xmla.api.common.enums.DimensionTypeEnum;
import org.eclipse.daanse.xmla.api.discover.Properties;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.actions.MdSchemaActionsRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRestrictions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MDSchemaDiscoverServiceTest {
    @Mock
    private Context context1;

    @Mock
    private Context context2;

    @Mock
    private DatabaseMappingSchemaProvider dmsp1;

    @Mock
    private DatabaseMappingSchemaProvider dmsp2;

    @Mock
    private MappingSchema mappingSchema1;

    @Mock
    private MappingSchema mappingSchema2;

    @Mock
    private Schema schema1;

    @Mock
    private Schema schema2;

    @Mock
    private MappingCube mappingCube1;

    @Mock
    private MappingCube mappingCube2;

    @Mock
    private Cube cube1;

    @Mock
    private Cube cube2;

    @Mock
    private Dimension dimension1;

    @Mock
    private Dimension dimension2;

    @Mock
    private MappingHierarchy hierarchy1;

    @Mock
    private MappingHierarchy hierarchy2;

    @Mock
    private MappingMeasure measure;

    @Mock
    private DataSource dataSource;

    @Mock
    private java.sql.Connection sqlConnection;

    @Mock
    private Connection connection;

    private MDSchemaDiscoverService service;

    private ContextsSupplyerImpl cls;

    @BeforeEach
    void setup() {
        /*
         * empty list, but override with:
         * when(cls.get()).thenReturn(List.of(context1,context2));`
         */

        cls = Mockito.spy(new ContextsSupplyerImpl(List.of()));
        service = new MDSchemaDiscoverService(cls);
    }

    @Test
    void mdSchemaActions() {
        MdSchemaActionsRequest request = mock(MdSchemaActionsRequest.class);
        MdSchemaActionsRestrictions restrictions = mock(MdSchemaActionsRestrictions.class);

        List<MdSchemaActionsResponseRow> rows = service.mdSchemaActions(request);
        assertThat(rows).isNull();
    }

    @Test
    void mdSchemaCubes() {
        when(cls.get()).thenReturn(List.of(context1, context2));

        MdSchemaCubesRequest request = mock(MdSchemaCubesRequest.class);
        MdSchemaCubesRestrictions restrictions = mock(MdSchemaCubesRestrictions.class);

        when(request.restrictions()).thenReturn(restrictions);
        when(restrictions.catalogName()).thenReturn("foo");

        when(mappingSchema1.name()).thenReturn("schema1Name");

        when(mappingSchema2.name()).thenReturn("schema2Name");

        when(mappingCube1.name()).thenReturn("cube1Name");
        when(mappingCube2.name()).thenReturn("cube2Name");
        when(mappingCube2.description()).thenReturn("cube2description");
        when(mappingCube1.visible()).thenReturn(true);
        when(mappingCube2.visible()).thenReturn(false).thenReturn(true);;

        when(mappingSchema1.cubes()).thenAnswer(setupDummyListAnswer(mappingCube1, mappingCube2));
        when(mappingSchema2.cubes()).thenAnswer(setupDummyListAnswer(mappingCube1, mappingCube2));

        when(dmsp1.get()).thenReturn(mappingSchema1);
        when(dmsp2.get()).thenReturn(mappingSchema2);

        when(context1.getName()).thenReturn("bar");
        when(context2.getName()).thenReturn("foo");
        when(context2.getDatabaseMappingSchemaProviders()).thenAnswer(setupDummyListAnswer(dmsp1, dmsp2));

        List<MdSchemaCubesResponseRow> rows = service.mdSchemaCubes(request);
        verify(context1, times(1)).getName();
        assertThat(rows).isNotNull().hasSize(3);
        MdSchemaCubesResponseRow row = rows.get(0);
        assertThat(row).isNotNull();
        assertThat(row.catalogName()).contains("foo");
        assertThat(row.schemaName()).contains("schema1Name");
        assertThat(row.description()).contains("foo Schema - cube1Name Cube");

        row = rows.get(1);
        assertThat(row).isNotNull();
        assertThat(row.catalogName()).contains("foo");
        assertThat(row.schemaName()).contains("schema2Name");
        assertThat(row.description()).contains("foo Schema - cube1Name Cube");

        row = rows.get(2);
        assertThat(row).isNotNull();
        assertThat(row.catalogName()).contains("foo");
        assertThat(row.schemaName()).contains("schema2Name");
        assertThat(row.description()).contains("cube2description");
    }

    @Test
    void mdSchemaDimensions() {
    	//TODO
        when(cls.get()).thenReturn(List.of(context1, context2));

        MdSchemaDimensionsRequest request = mock(MdSchemaDimensionsRequest.class);
        MdSchemaDimensionsRestrictions restrictions = mock(MdSchemaDimensionsRestrictions.class);
        Properties properties = mock(Properties.class);

        when(properties.deep()).thenReturn(Optional.of(true));

        when(request.restrictions()).thenReturn(restrictions);
        when(restrictions.catalogName()).thenReturn(Optional.of("foo"));
        when(request.properties()).thenReturn(properties);

        when(schema1.getName()).thenReturn("schema1Name");

        when(schema2.getName()).thenReturn("schema2Name");

        when(dimension1.getName()).thenReturn("dimension1Name");
        when(dimension1.getUniqueName()).thenReturn("dimension1UniqueName");
        when(dimension1.getCaption()).thenReturn("dimension1Caption");
        when(dimension2.getName()).thenReturn("dimension2Name");
        when(dimension2.getUniqueName()).thenReturn("dimension2UniqueName");
        when(dimension2.getCaption()).thenReturn("dimension2Caption");


        when(cube1.getName()).thenReturn("cube1Name");
        when(cube2.getName()).thenReturn("cube2Name");
        when(cube1.getDimensions()).thenAnswer(setupDummyArrayAnswer(dimension1, dimension2));
        when(cube2.getDimensions()).thenAnswer(setupDummyArrayAnswer(dimension1, dimension2));
        //when(cube2.getDescription()).thenReturn("cube2description");

        when(schema1.getCubes()).thenAnswer(setupDummyArrayAnswer(cube1, cube2));
        when(schema2.getCubes()).thenAnswer(setupDummyArrayAnswer(cube1, cube2));

        when(connection.getSchemas()).thenAnswer(setupDummyListAnswer(schema1, schema2));

        when(context1.getName()).thenReturn("bar");
        when(context2.getName()).thenReturn("foo");
        //when(context1.getConnection()).thenReturn(connection);
        when(context2.getConnection()).thenReturn(connection);

        //when(context2.getDatabaseMappingSchemaProviders()).thenAnswer(setupDummyListAnswer(dmsp1, dmsp2));

        List<MdSchemaDimensionsResponseRow> rows = service.mdSchemaDimensions(request);
        verify(context1, times(1)).getName();
        //verify(context2, times(1)).getName();
        assertThat(rows).isNotNull().hasSize(8);
        MdSchemaDimensionsResponseRow row = rows.get(0);
        assertThat(row).isNotNull();
        assertThat(row.catalogName()).contains("foo");
        assertThat(row.schemaName()).contains("schema1Name");
        assertThat(row.cubeName()).contains("cube1Name");
        assertThat(row.dimensionName()).contains("dimension1Name");
        assertThat(row.dimensionUniqueName()).contains("dimension1UniqueName");
        assertThat(row.dimensionCaption()).contains("dimension1Caption");
        assertThat(row.dimensionOptional()).contains(0);
        assertThat(row.dimensionType()).isEmpty();
        assertThat(row.dimensionCardinality()).contains(1);
        assertThat(row.defaultHierarchy()).contains("dimension1DefaultHierarchy");
        assertThat(row.description()).contains("cube1Name Cube - dimension1Name Dimension");

        row = rows.get(1);
        assertThat(row).isNotNull();
        assertThat(row.catalogName()).contains("foo");
        assertThat(row.schemaName()).contains("schema1Name");
        assertThat(row.description()).contains("cube1Name Cube - dimension2Name Dimension");

        row = rows.get(2);
        assertThat(row).isNotNull();
        assertThat(row.catalogName()).contains("foo");
        assertThat(row.schemaName()).contains("schema1Name");
        assertThat(row.description()).contains("cube2Name Cube - dimension1Name Dimension");

        row = rows.get(3);
        assertThat(row).isNotNull();
        assertThat(row.catalogName()).contains("foo");
        assertThat(row.schemaName()).contains("schema1Name");
        assertThat(row.description()).contains("cube2Name Cube - dimension2Name Dimension");

    }

    @Test
    void mdSchemaFunctions() {
    	//TODO
    }

    @Test
    void mdSchemaHierarchies() {
    	//TODO
    }

    @Test
    void mdSchemaKpis() {
    	//TODO
    }

    @Test
    void mdSchemaLevels() {
    	//TODO
    }

    @Test
    void mdSchemaMeasureGroupDimensions() {
    	//TODO
    }

    @Test
    void mdSchemaMeasureGroups() {
    	//TODO
    }

    @Test
    void mdSchemaMeasures() {
    	//TODO
    }

    @Test
    void mdSchemaMembers() {
    	//TODO
    }

    @Test
    void mdSchemaProperties() {
    	//TODO
    }

    @Test
    void mdSchemaSets() {
    	//TODO
    }

    private static <N> Answer<List<N>> setupDummyListAnswer(N... values) {
        final List<N> someList = new ArrayList<>(Arrays.asList(values));

        Answer<List<N>> answer = new Answer<>() {
            @Override
            public List<N> answer(InvocationOnMock invocation) throws Throwable {
                return someList;
            }
        };
        return answer;
    }

    private static <N> Answer<N[]> setupDummyArrayAnswer(N... values) {

        Answer<N[]> answer = new Answer<>() {
            @Override
            public N[] answer(InvocationOnMock invocation) throws Throwable {
                return values;
            }
        };
        return answer;
    }

}
