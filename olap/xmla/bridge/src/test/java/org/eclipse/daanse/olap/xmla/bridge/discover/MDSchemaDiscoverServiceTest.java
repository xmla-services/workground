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
import org.eclipse.daanse.olap.api.DataType;
import org.eclipse.daanse.olap.api.Syntax;
import org.eclipse.daanse.olap.api.element.Cube;
import org.eclipse.daanse.olap.api.element.Dimension;
import org.eclipse.daanse.olap.api.element.Hierarchy;
import org.eclipse.daanse.olap.api.element.Schema;
import org.eclipse.daanse.olap.api.function.FunctionAtom;
import org.eclipse.daanse.olap.api.function.FunctionMetaData;
import org.eclipse.daanse.olap.api.function.FunctionTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;
import org.eclipse.daanse.olap.xmla.bridge.ContextsSupplyerImpl;
import org.eclipse.daanse.xmla.api.common.enums.OriginEnum;
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
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.functions.MdSchemaFunctionsRestrictions;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesRestrictions;
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
    private Hierarchy hierarchy1;

    @Mock
    private Hierarchy hierarchy2;

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
        when(mappingCube2.visible()).thenReturn(false).thenReturn(true);
        ;

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

        when(hierarchy1.getUniqueName()).thenReturn("hierarchy1UniqueName");
        when(hierarchy1.getUniqueName()).thenReturn("hierarchy1UniqueName");

        //when(hierarchy2.getUniqueName()).thenReturn("hierarchy1UniqueName");

        when(dimension1.getName()).thenReturn("dimension1Name");
        when(dimension1.getUniqueName()).thenReturn("dimension1UniqueName");
        when(dimension1.getCaption()).thenReturn("dimension1Caption");
        when(dimension1.getHierarchies()).thenAnswer(setupDummyArrayAnswer(hierarchy1, hierarchy2));

        when(dimension2.getName()).thenReturn("dimension2Name");
        when(dimension2.getUniqueName()).thenReturn("dimension2UniqueName");
        when(dimension2.getCaption()).thenReturn("dimension2Caption");
        when(dimension2.getHierarchies()).thenAnswer(setupDummyArrayAnswer(hierarchy1, hierarchy2));

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
        checkMdSchemaDimensionsResponseRow(rows.get(0), "foo",
            "schema1Name", "cube1Name",
            "dimension1Name", "dimension1UniqueName",
            "dimension1Caption", 0, 1,
            "hierarchy1UniqueName",
            "cube1Name Cube - dimension1Name Dimension");

        checkMdSchemaDimensionsResponseRow(rows.get(1), "foo",
            "schema1Name", "cube1Name",
            "dimension2Name", "dimension2UniqueName",
            "dimension2Caption", 1, 1,
            "hierarchy1UniqueName",
            "cube1Name Cube - dimension2Name Dimension");

        checkMdSchemaDimensionsResponseRow(rows.get(2), "foo",
            "schema1Name", "cube2Name",
            "dimension1Name", "dimension1UniqueName",
            "dimension1Caption", 0, 1,
            "hierarchy1UniqueName",
            "cube2Name Cube - dimension1Name Dimension");

        checkMdSchemaDimensionsResponseRow(rows.get(3), "foo",
            "schema1Name", "cube2Name",
            "dimension2Name", "dimension2UniqueName",
            "dimension2Caption", 1, 1,
            "hierarchy1UniqueName",
            "cube2Name Cube - dimension2Name Dimension");

        checkMdSchemaDimensionsResponseRow(rows.get(4), "foo",
            "schema2Name", "cube1Name",
            "dimension1Name", "dimension1UniqueName",
            "dimension1Caption", 0, 1,
            "hierarchy1UniqueName",
            "cube1Name Cube - dimension1Name Dimension");

        checkMdSchemaDimensionsResponseRow(rows.get(5), "foo",
            "schema2Name", "cube1Name",
            "dimension2Name", "dimension2UniqueName",
            "dimension2Caption", 1, 1,
            "hierarchy1UniqueName",
            "cube1Name Cube - dimension2Name Dimension");

        checkMdSchemaDimensionsResponseRow(rows.get(6), "foo",
            "schema2Name", "cube2Name",
            "dimension1Name", "dimension1UniqueName",
            "dimension1Caption", 0, 1,
            "hierarchy1UniqueName",
            "cube2Name Cube - dimension1Name Dimension");

        checkMdSchemaDimensionsResponseRow(rows.get(7), "foo",
            "schema2Name", "cube2Name",
            "dimension2Name", "dimension2UniqueName",
            "dimension2Caption", 1, 1,
            "hierarchy1UniqueName",
            "cube2Name Cube - dimension2Name Dimension");

    }

    @Test
    void mdSchemaFunctions() {
        when(cls.get()).thenReturn(List.of(context1));

        MdSchemaFunctionsRequest request = mock(MdSchemaFunctionsRequest.class);
        MdSchemaFunctionsRestrictions restrictions = mock(MdSchemaFunctionsRestrictions.class);
        FunctionTable functionTable = mock(FunctionTable.class);
        FunctionAtom functionAtom1 = mock(FunctionAtom.class);
        FunctionAtom functionAtom2 = mock(FunctionAtom.class);
        when(functionAtom1.name()).thenReturn("functionAtom1Name");
        when(functionAtom1.syntax()).thenReturn(Syntax.Function);

        when(functionAtom2.name()).thenReturn("functionAtom2Name");
        when(functionAtom2.syntax()).thenReturn(Syntax.Method);

        FunctionMetaData functionMetaData1 = mock(FunctionMetaData.class);
        FunctionMetaData functionMetaData2 = mock(FunctionMetaData.class);
        when(functionMetaData1.parameterDataTypes()).thenAnswer(setupDummyArrayAnswer(DataType.INTEGER,
            DataType.MEMBER));
        when(functionMetaData1.returnCategory()).thenReturn(DataType.INTEGER);
        when(functionMetaData1.description()).thenReturn("functionMetaData1Description");
        when(functionMetaData2.parameterDataTypes()).thenAnswer(setupDummyArrayAnswer(DataType.CUBE));
        when(functionMetaData2.returnCategory()).thenReturn(DataType.MEMBER);
        when(functionMetaData2.description()).thenReturn("functionMetaData2Description");

        when(functionMetaData1.functionAtom()).thenReturn(functionAtom1);
        when(functionMetaData2.functionAtom()).thenReturn(functionAtom2);

        when(functionTable.getFunctionMetaDatas()).thenAnswer(setupDummyListAnswer(functionMetaData1,
            functionMetaData2));

        when(request.restrictions()).thenReturn(restrictions);
        when(schema1.getFunTable()).thenReturn(functionTable);
        when(connection.getSchema()).thenReturn(schema1);

        when(context1.getConnection()).thenReturn(connection);


        List<MdSchemaFunctionsResponseRow> rows = service.mdSchemaFunctions(request);
        assertThat(rows).isNotNull().hasSize(2);
        checkMdSchemaFunctionsResponseRow(rows.get(0), "functionAtom1Name",
            "functionMetaData1Description", "Integer, Member",
            2, OriginEnum.MSOLAP, "functionAtom1Name");
        checkMdSchemaFunctionsResponseRow(rows.get(1), "functionAtom2Name",
            "functionMetaData2Description", "Cube",
            12, OriginEnum.MSOLAP, "functionAtom2Name");

    }

    @Test
    void mdSchemaHierarchies() {
    	//TODO
        when(cls.get()).thenReturn(List.of(context1, context2));

        MdSchemaHierarchiesRequest request = mock(MdSchemaHierarchiesRequest.class);
        MdSchemaHierarchiesRestrictions restrictions = mock(MdSchemaHierarchiesRestrictions.class);
        Properties properties = mock(Properties.class);

        when(properties.deep()).thenReturn(Optional.of(true));

        when(request.restrictions()).thenReturn(restrictions);
        when(restrictions.catalogName()).thenReturn(Optional.of("foo"));
        when(request.properties()).thenReturn(properties);

        when(schema1.getName()).thenReturn("schema1Name");

        when(schema2.getName()).thenReturn("schema2Name");

        when(hierarchy1.getUniqueName()).thenReturn("hierarchy1UniqueName");
        when(hierarchy2.getUniqueName()).thenReturn("hierarchy1UniqueName");
        when(hierarchy1.getName()).thenReturn("hierarchy1Name");
        when(hierarchy2.getName()).thenReturn("hierarchy2Name");

        when(dimension1.getName()).thenReturn("dimension1Name");
        when(dimension1.getUniqueName()).thenReturn("dimension1UniqueName");
        when(dimension1.getHierarchies()).thenAnswer(setupDummyArrayAnswer(hierarchy1, hierarchy2));

        when(dimension2.getName()).thenReturn("dimension2Name");
        when(dimension2.getUniqueName()).thenReturn("dimension2UniqueName");
        when(dimension2.getHierarchies()).thenAnswer(setupDummyArrayAnswer(hierarchy1, hierarchy2));

        when(cube1.getName()).thenReturn("cube1Name");
        when(cube2.getName()).thenReturn("cube2Name");
        when(cube1.getDimensions()).thenAnswer(setupDummyArrayAnswer(dimension1, dimension2));
        when(cube2.getDimensions()).thenAnswer(setupDummyArrayAnswer(dimension1, dimension2));

        when(schema1.getCubes()).thenAnswer(setupDummyArrayAnswer(cube1, cube2));
        when(schema2.getCubes()).thenAnswer(setupDummyArrayAnswer(cube1, cube2));

        when(connection.getSchemas()).thenAnswer(setupDummyListAnswer(schema1, schema2));

        when(context1.getName()).thenReturn("bar");
        when(context2.getName()).thenReturn("foo");

        when(context2.getConnection()).thenReturn(connection);


        List<MdSchemaHierarchiesResponseRow> rows = service.mdSchemaHierarchies(request);
        verify(context1, times(1)).getName();
        verify(context2, times(3)).getName();
        assertThat(rows).isNotNull().hasSize(16);
        MdSchemaHierarchiesResponseRow row = rows.get(0);
        assertThat(row).isNotNull();
        assertThat(row.catalogName()).contains("foo");
        assertThat(row.schemaName()).contains("schema1Name");
        assertThat(row.cubeName()).contains("cube1Name");
        assertThat(row.dimensionUniqueName()).contains("dimension1UniqueName");
        assertThat(row.description()).contains("cube1Name Cube - hierarchy1Name Hierarchy");
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

    private void checkMdSchemaDimensionsResponseRow(
        MdSchemaDimensionsResponseRow row,
        String catalogName,
        String schemaName,
        String cubeName,
        String dimensionName,
        String dimensionUniqueName,
        String dimensionCaption,
        int dimensionOrdinal,
        int dimensionCardinality,
        String defaultHierarchy,
        String description
    ) {
        assertThat(row).isNotNull();
        assertThat(row.catalogName()).contains(catalogName);
        assertThat(row.schemaName()).contains(schemaName);
        assertThat(row.cubeName()).contains(cubeName);
        assertThat(row.dimensionName()).contains(dimensionName);
        assertThat(row.dimensionUniqueName()).contains(dimensionUniqueName);
        assertThat(row.dimensionCaption()).contains(dimensionCaption);
        assertThat(row.dimensionOptional()).contains(dimensionOrdinal);
        assertThat(row.dimensionType()).isEmpty();
        assertThat(row.dimensionCardinality()).contains(dimensionCardinality);
        assertThat(row.defaultHierarchy()).contains(defaultHierarchy);
        assertThat(row.description()).contains(description);
    }

    private void checkMdSchemaHierarchiesResponseRow(
        MdSchemaHierarchiesResponseRow row,
        String catalogName,
        String schemaName,
        String cubeName,
        String dimension,
        String dimensionUniqueName,
        String dimensionCaption,
        int i,
        int i1,
        String hierarchy1UniqueName,
        String description
    ) {
        assertThat(row).isNotNull();
        assertThat(row.catalogName()).contains(catalogName);
        assertThat(row.schemaName()).contains(schemaName);
        assertThat(row.cubeName()).contains(cubeName);
        assertThat(row.dimensionUniqueName()).contains(dimensionUniqueName);
        assertThat(row.dimensionType()).isEmpty();
        assertThat(row.description()).contains(description);
    }

    private void checkMdSchemaFunctionsResponseRow(
        MdSchemaFunctionsResponseRow row,
        String functionalName,
        String description,
        String parameterList,
        int returnType,
        OriginEnum origin,
        String caption
    ) {
        assertThat(row).isNotNull();
        assertThat(row.functionalName()).contains(functionalName);
        assertThat(row.description()).contains(description);
        assertThat(row.parameterList()).contains(parameterList);
        assertThat(row.returnType()).contains(returnType);
        assertThat(row.origin()).contains(origin);
        assertThat(row.caption()).contains(caption);
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
