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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.dbcreator.basic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.osgi.test.common.dictionary.Dictionaries.dictionaryOf;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectResolver;
import org.eclipse.daanse.db.jdbc.util.impl.Column;
import org.eclipse.daanse.db.jdbc.util.impl.Constraint;
import org.eclipse.daanse.db.jdbc.util.impl.DBStructure;
import org.eclipse.daanse.db.jdbc.util.impl.Type;
import org.eclipse.daanse.olap.rolap.dbmapper.dbcreator.api.DbCreatorService;
import org.eclipse.daanse.olap.rolap.dbmapper.dbcreator.api.DbCreatorServiceFactory;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.ColumnDef;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.DimensionUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Hierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.InlineTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Measure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Property;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Table;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MeasureDataTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.PropertyTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
class DbCreatorServiceImplTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.olap.rolap.dbmapper.creator.impl.DbCreatorServiceFactoryImpl";
    @InjectBundleContext
    BundleContext bc;
    DialectResolver dialectResolver = mock(DialectResolver.class);

    DbCreatorService dbCreatorService;
    DataSource dataSource = mock(DataSource.class);
    Schema schema = mock(Schema.class);
    Dialect dialect = mock(Dialect.class);
    Connection connection = mock(Connection.class);
    Statement statement = mock(Statement.class);
    PrivateDimension privateDimension = mock(PrivateDimension.class);
    Hierarchy hierarchy = mock(Hierarchy.class);
    Table table = mock(Table.class);
    Table tableFact = mock(Table.class);
    Level level1 = mock(Level.class);
    Level level2 = mock(Level.class);
    Property property1 = mock(Property.class);
    Property property11 = mock(Property.class);
    Cube cube = mock(Cube.class);
    DimensionUsage dimensionUsage = mock(DimensionUsage.class);
    Measure measure1 = mock(Measure.class);
    Measure measure2 = mock(Measure.class);
    InlineTable inlineTable = mock(InlineTable.class);
    ColumnDef columnDef1 = mock(ColumnDef.class);
    ColumnDef columnDef2 = mock(ColumnDef.class);


    @BeforeEach
    void beforeEach() throws SQLException {
        bc.registerService(DialectResolver.class, dialectResolver, dictionaryOf("ds", "1"));
        when(dialectResolver.resolve(any())).thenReturn(Optional.of(dialect));
        when(dialect.getDialectName()).thenReturn("MYSQL");
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
    }

    @Test
    void testPrivateDimension(@InjectService(filter = "(component.name=" + COMPONENT_NAME + ")") DbCreatorServiceFactory dbCreatorServiceFactory) throws SQLException {
        when(schema.name()).thenReturn("schemaName");
        when(schema.dimension()).thenAnswer(setupDummyListAnswer(privateDimension));
        when(privateDimension.hierarchy()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.relation()).thenReturn(table);
        when(table.name()).thenReturn("tableName");
        when(hierarchy.level()).thenAnswer(setupDummyListAnswer(level1, level2));
        when(hierarchy.primaryKey()).thenReturn("id");
        when(level1.type()).thenReturn(TypeEnum.INTEGER);
        when(level2.type()).thenReturn(TypeEnum.NUMERIC);
        when(level1.column()).thenReturn("column1");
        when(level2.column()).thenReturn("column2");
        when(level1.ordinalColumn()).thenReturn("ordinalColumn1");
        when(level2.ordinalColumn()).thenReturn("ordinalColumn2");
        when(level1.captionColumn()).thenReturn("captionColumn1");
        when(level2.captionColumn()).thenReturn("captionColumn2");
        when(level1.parentColumn()).thenReturn("parentColumn1");
        when(level2.parentColumn()).thenReturn("parentColumn2");
        when(level1.nameColumn()).thenReturn("nameColumn1");
        when(level1.property()).thenAnswer(setupDummyListAnswer(property1, property11));
        when(property1.column()).thenReturn("property1Column");
        when(property11.column()).thenReturn("property11Column");
        when(property1.type()).thenReturn(PropertyTypeEnum.INTEGER);



        dbCreatorService = dbCreatorServiceFactory.create(dataSource);
        DBStructure dbStructure = dbCreatorService.createSchema(schema);
        assertThat(dbStructure).isNotNull().extracting(DBStructure::getName)
            .isNotNull().isEqualTo("schemaName");
        assertThat(dbStructure).isNotNull().extracting(DBStructure::getTables).isNotNull();
        assertThat(dbStructure.getTables()).isNotNull().hasSize(1);
        assertThat(dbStructure.getTables().get(0)).isNotNull();
        assertThat(dbStructure.getTables().get(0))
            .extracting(org.eclipse.daanse.db.jdbc.util.impl.Table::getTableName).isNotNull().isEqualTo("tableName");
        assertThat(dbStructure.getTables().get(0).getColumns()).isNotNull().hasSize(12);
        assertThat(dbStructure.getTables().get(0).getColumns())
            .extracting(Column::name)
            .contains("id")
            .contains("column1")
            .contains("column2")
            .contains("ordinalColumn1")
            .contains("ordinalColumn2")
            .contains("captionColumn1")
            .contains("captionColumn2")
            .contains("parentColumn1")
            .contains("parentColumn2")
            .contains("nameColumn1")
            .contains("property1Column")
            .contains("property11Column");
        assertThat(dbStructure.getTables().get(0).getColumns())
            .extracting(Column::type)
            .contains(Type.INTEGER)
            .contains(Type.NUMERIC)
            .contains(Type.STRING);
        assertThat(dbStructure.getTables().get(0).getConstraints())
            .extracting(Constraint::name)
            .contains("id");
        assertThat(dbStructure.getTables().get(0).getConstraints())
            .extracting(Constraint::unique)
            .contains(true);
        assertThat(dbStructure.getTables().get(0).getConstraints())
            .extracting(Constraint::unique)
            .contains(true);
        assertThat(dbStructure.getTables().get(0).getConstraints())
            .isNotNull().hasSize(1);
        assertThat(dbStructure.getTables().get(0).getConstraints().get(0).columnNames())
            .contains("id");

    }

    @Test
    void testCube(@InjectService(filter = "(component.name=" + COMPONENT_NAME + ")") DbCreatorServiceFactory dbCreatorServiceFactory) throws SQLException {
        when(schema.name()).thenReturn("schemaName");
        when(privateDimension.hierarchy()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.relation()).thenReturn(table);
        when(table.name()).thenReturn("tableName");
        when(hierarchy.level()).thenAnswer(setupDummyListAnswer(level1, level2));
        when(hierarchy.primaryKey()).thenReturn("id");
        when(level1.type()).thenReturn(TypeEnum.INTEGER);
        when(level2.type()).thenReturn(TypeEnum.NUMERIC);
        when(level1.column()).thenReturn("column1");
        when(level2.column()).thenReturn("column2");
        when(level1.ordinalColumn()).thenReturn("ordinalColumn1");
        when(level2.ordinalColumn()).thenReturn("ordinalColumn2");
        when(level1.captionColumn()).thenReturn("captionColumn1");
        when(level2.captionColumn()).thenReturn("captionColumn2");
        when(level1.parentColumn()).thenReturn("parentColumn1");
        when(level2.parentColumn()).thenReturn("parentColumn2");
        when(level1.nameColumn()).thenReturn("nameColumn1");
        when(level1.property()).thenAnswer(setupDummyListAnswer(property1, property11));
        when(property1.column()).thenReturn("property1Column");
        when(property11.column()).thenReturn("property11Column");
        when(property1.type()).thenReturn(PropertyTypeEnum.INTEGER);
        when(schema.cube()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.fact()).thenReturn(tableFact);
        when(cube.dimensionUsageOrDimension()).thenAnswer(setupDummyListAnswer(dimensionUsage, privateDimension));
        when(cube.measure()).thenAnswer(setupDummyListAnswer(measure1, measure2));
        when(measure1.column()).thenReturn("measure1Column");
        when(measure2.column()).thenReturn("measure2Column");
        when(measure1.datatype()).thenReturn(MeasureDataTypeEnum.NUMERIC);



        when(tableFact.name()).thenReturn("tableFact");
        when(dimensionUsage.foreignKey()).thenReturn("tableFactId");

        dbCreatorService = dbCreatorServiceFactory.create(dataSource);
        DBStructure dbStructure = dbCreatorService.createSchema(schema);
        assertThat(dbStructure).isNotNull().extracting(DBStructure::getName)
            .isNotNull().isEqualTo("schemaName");
        assertThat(dbStructure).isNotNull().extracting(DBStructure::getTables).isNotNull();
        assertThat(dbStructure.getTables()).isNotNull().hasSize(2);
        assertThat(dbStructure.getTables()).extracting(org.eclipse.daanse.db.jdbc.util.impl.Table::getTableName).contains("tableName", "tableFact");
        org.eclipse.daanse.db.jdbc.util.impl.Table table = dbStructure.getTables().get(0);
        assertThat(table.getColumns()).isNotNull().hasSize(3);
        assertThat(table.getColumns())
            .extracting(Column::name)
            .contains("tableFactId")
            .contains("measure1Column")
            .contains("measure2Column");
        assertThat(table.getColumns())
            .extracting(Column::type)
            .contains(Type.INTEGER)
            .contains(Type.STRING)
            .contains(Type.NUMERIC);
        assertThat(table.getConstraints())
            .extracting(Constraint::name)
            .contains("tableFactId");
        assertThat(table.getConstraints())
            .extracting(Constraint::unique)
            .contains(true);
        assertThat(table.getConstraints())
            .extracting(Constraint::unique)
            .contains(true);
        assertThat(table.getConstraints())
            .isNotNull().hasSize(1);
        assertThat(table.getConstraints().get(0).columnNames())
            .contains("tableFactId");

        table = dbStructure.getTables().get(1);
        assertThat(table.getColumns()).isNotNull().hasSize(12);
        assertThat(table.getColumns())
            .extracting(Column::name)
            .contains("id")
            .contains("column1")
            .contains("column2")
            .contains("ordinalColumn1")
            .contains("ordinalColumn2")
            .contains("captionColumn1")
            .contains("captionColumn2")
            .contains("parentColumn1")
            .contains("parentColumn2")
            .contains("nameColumn1")
            .contains("property1Column")
            .contains("property11Column");
        assertThat(table.getColumns())
            .extracting(Column::type)
            .contains(Type.INTEGER)
            .contains(Type.NUMERIC)
            .contains(Type.STRING);
        assertThat(table.getConstraints())
            .extracting(Constraint::name)
            .contains("id");
        assertThat(table.getConstraints())
            .extracting(Constraint::unique)
            .contains(true);
        assertThat(table.getConstraints())
            .extracting(Constraint::unique)
            .contains(true);
        assertThat(table.getConstraints())
            .isNotNull().hasSize(1);
        assertThat(table.getConstraints().get(0).columnNames())
            .contains("id");
    }

    @Test
    void testInlineTableColumnDef(@InjectService(filter = "(component.name=" + COMPONENT_NAME + ")") DbCreatorServiceFactory dbCreatorServiceFactory) throws SQLException {
        when(schema.name()).thenReturn("schemaName");
        when(privateDimension.hierarchy()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.relation()).thenReturn(table);
        when(table.name()).thenReturn("tableName");
        when(hierarchy.level()).thenAnswer(setupDummyListAnswer(level1, level2));
        when(hierarchy.primaryKey()).thenReturn("id");
        when(level1.type()).thenReturn(TypeEnum.INTEGER);
        when(level2.type()).thenReturn(TypeEnum.NUMERIC);
        when(level1.column()).thenReturn("column1");
        when(level2.column()).thenReturn("column2");
        when(level1.ordinalColumn()).thenReturn("ordinalColumn1");
        when(level2.ordinalColumn()).thenReturn("ordinalColumn2");
        when(level1.captionColumn()).thenReturn("captionColumn1");
        when(level2.captionColumn()).thenReturn("captionColumn2");
        when(level1.parentColumn()).thenReturn("parentColumn1");
        when(level2.parentColumn()).thenReturn("parentColumn2");
        when(level1.nameColumn()).thenReturn("nameColumn1");
        when(level1.property()).thenAnswer(setupDummyListAnswer(property1, property11));
        when(property1.column()).thenReturn("property1Column");
        when(property11.column()).thenReturn("property11Column");
        when(property1.type()).thenReturn(PropertyTypeEnum.INTEGER);
        when(schema.cube()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.fact()).thenReturn(inlineTable);
        when(inlineTable.alias()).thenReturn("inlineTableName");
        when(inlineTable.columnDefs()).thenAnswer(setupDummyListAnswer(columnDef1, columnDef2));
        when(columnDef1.name()).thenReturn("columnDef1Name");
        when(columnDef2.name()).thenReturn("columnDef2Name");
        when(columnDef1.type()).thenReturn(TypeEnum.BOOLEAN);
        when(cube.dimensionUsageOrDimension()).thenAnswer(setupDummyListAnswer(dimensionUsage, privateDimension));
        when(cube.measure()).thenAnswer(setupDummyListAnswer(measure1, measure2));
        when(measure1.column()).thenReturn("measure1Column");
        when(measure2.column()).thenReturn("measure2Column");
        when(measure1.datatype()).thenReturn(MeasureDataTypeEnum.NUMERIC);
        when(dimensionUsage.foreignKey()).thenReturn("tableFactId");

        dbCreatorService = dbCreatorServiceFactory.create(dataSource);
        DBStructure dbStructure = dbCreatorService.createSchema(schema);

        assertThat(dbStructure).isNotNull().extracting(DBStructure::getName)
            .isNotNull().isEqualTo("schemaName");
        assertThat(dbStructure).isNotNull().extracting(DBStructure::getTables).isNotNull();
        assertThat(dbStructure.getTables()).isNotNull().hasSize(2);
        assertThat(dbStructure.getTables()).extracting(org.eclipse.daanse.db.jdbc.util.impl.Table::getTableName).contains("tableName", "inlineTableName");
        org.eclipse.daanse.db.jdbc.util.impl.Table table = dbStructure.getTables().get(0);
        assertThat(table.getColumns()).isNotNull().hasSize(5);
        assertThat(table.getColumns())
            .extracting(Column::name)
            .contains("tableFactId")
            .contains("measure1Column")
            .contains("measure2Column")
            .contains("columnDef1Name")
            .contains("columnDef2Name");
        assertThat(table.getColumns())
            .extracting(Column::type)
            .contains(Type.INTEGER)
            .contains(Type.STRING)
            .contains(Type.NUMERIC)
            .contains(Type.BOOLEAN);
        assertThat(table.getConstraints())
            .extracting(Constraint::name)
            .contains("tableFactId");
        assertThat(table.getConstraints())
            .extracting(Constraint::unique)
            .contains(true);
        assertThat(table.getConstraints())
            .extracting(Constraint::unique)
            .contains(true);
        assertThat(table.getConstraints())
            .isNotNull().hasSize(1);
        assertThat(table.getConstraints().get(0).columnNames())
            .contains("tableFactId");

        table = dbStructure.getTables().get(1);
        assertThat(table.getColumns()).isNotNull().hasSize(12);
        assertThat(table.getColumns())
            .extracting(Column::name)
            .contains("id")
            .contains("column1")
            .contains("column2")
            .contains("ordinalColumn1")
            .contains("ordinalColumn2")
            .contains("captionColumn1")
            .contains("captionColumn2")
            .contains("parentColumn1")
            .contains("parentColumn2")
            .contains("nameColumn1")
            .contains("property1Column")
            .contains("property11Column");
        assertThat(table.getColumns())
            .extracting(Column::type)
            .contains(Type.INTEGER)
            .contains(Type.NUMERIC)
            .contains(Type.STRING);
        assertThat(table.getConstraints())
            .extracting(Constraint::name)
            .contains("id");
        assertThat(table.getConstraints())
            .extracting(Constraint::unique)
            .contains(true);
        assertThat(table.getConstraints())
            .extracting(Constraint::unique)
            .contains(true);
        assertThat(table.getConstraints())
            .isNotNull().hasSize(1);
        assertThat(table.getConstraints().get(0).columnNames())
            .contains("id");
    }

    private static  <N> Answer<List<N>> setupDummyListAnswer(N... values) {
        final List<N> someList = new ArrayList<>(Arrays.asList(values));

        Answer<List<N>> answer = new Answer<>() {
            @Override
			public List<N> answer(InvocationOnMock invocation) throws Throwable {
                return someList;
            }
        };
        return answer;
    }

}
