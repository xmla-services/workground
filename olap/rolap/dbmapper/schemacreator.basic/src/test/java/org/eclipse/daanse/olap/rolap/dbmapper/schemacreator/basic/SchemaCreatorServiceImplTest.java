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
package org.eclipse.daanse.olap.rolap.dbmapper.schemacreator.basic;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.DimensionUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Hierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.schemacreator.api.SchemaCreatorService;
import org.eclipse.daanse.olap.rolap.dbmapper.schemacreator.api.SchemaCreatorServiceFactory;
import org.eclipse.daanse.olap.rolap.dbmapper.schemacreator.api.SchemaInitData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;
import org.sqlite.SQLiteDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
class SchemaCreatorServiceImplTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.olap.rolap.dbmapper.schemacreator.basic.SchemaCreatorServiceFactoryImpl";

    @Test
    void testPopulationDatabase(@InjectService(filter = "(component.name=" + COMPONENT_NAME + ")") SchemaCreatorServiceFactory schemaCreatorServiceFactory) throws SQLException {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        //dataSource.setUrl("jdbc:sqlite:/home/oem/repod/deMondrian/olap/rolap/dbmapper/schemacreator.basic/src/test/resources/population.sqlite");
        dataSource.setUrl("jdbc:sqlite:../../../../src/test/resources/population.sqlite");
        SchemaCreatorService schemaCreatorService = schemaCreatorServiceFactory.create(dataSource);
        SchemaInitData schemaInitData = new SchemaInitData();
        schemaInitData.setFactTables(List.of("population"));
        Schema s = schemaCreatorService.createSchema(schemaInitData);
        assertThat(s).isNotNull();
        assertThat(s.dimensions()).isNotNull().hasSize(4);

        PrivateDimension d = getPrivateDimension(s.dimensions(), "Dimension State");
        assertThat(d).isNotNull();
        assertThat(d.hierarchies()).isNotNull().hasSize(1);
        Hierarchy h =  d.hierarchies().get(0);
        List <Level> levels = h.levels();
        assertThat(levels).isNotNull().hasSize(3);
        // check level0
        Level level = levels.get(0);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("Continent");
        assertThat(level.column()).isEqualTo("id");
        assertThat(level.table()).isEqualTo("continent");
        assertThat(level.nameColumn()).isEqualTo("name");
        assertThat(level.description()).isEqualTo("Continent");
        assertThat(level.type()).isEqualTo(TypeEnum.INTEGER);
        // check level1
        level = levels.get(1);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("Country");
        assertThat(level.column()).isEqualTo("id");
        assertThat(level.table()).isEqualTo("country");
        assertThat(level.nameColumn()).isEqualTo("name");
        assertThat(level.description()).isEqualTo("Country");
        assertThat(level.type()).isEqualTo(TypeEnum.INTEGER);
        // check level2
        level = levels.get(2);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("State");
        assertThat(level.column()).isEqualTo("id");
        assertThat(level.table()).isEqualTo("state");
        assertThat(level.nameColumn()).isEqualTo("name");
        assertThat(level.description()).isEqualTo("State");
        assertThat(level.type()).isEqualTo(TypeEnum.INTEGER);

        d = getPrivateDimension(s.dimensions(), "Dimension Gender");
        assertThat(d).isNotNull();
        assertThat(d.hierarchies()).isNotNull().hasSize(1);
        levels = d.hierarchies().get(0).levels();
        assertThat(levels).isNotNull().hasSize(1);
        // check level0
        level = levels.get(0);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("Gender");
        assertThat(level.column()).isEqualTo("gender_id");
        assertThat(level.table()).isEqualTo("gender");
        assertThat(level.nameColumn()).isEqualTo("name");
        assertThat(level.description()).isEqualTo("Gender");
        assertThat(level.type()).isEqualTo(TypeEnum.INTEGER);

        d = getPrivateDimension(s.dimensions(), "Dimension Year");
        assertThat(d).isNotNull();
        assertThat(d.hierarchies()).isNotNull().hasSize(1);
        levels = d.hierarchies().get(0).levels();
        assertThat(levels).isNotNull().hasSize(1);
        // check level0
        level = levels.get(0);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("Year");
        assertThat(level.column()).isEqualTo("year");
        assertThat(level.table()).isEqualTo("year");
        assertThat(level.nameColumn()).isNull();
        assertThat(level.description()).isEqualTo("Year");
        assertThat(level.type()).isEqualTo(TypeEnum.INTEGER);

        d = getPrivateDimension(s.dimensions(), "Dimension AgeGroups");
        assertThat(d).isNotNull();
        assertThat(d.hierarchies()).isNotNull().hasSize(1);
        levels = d.hierarchies().get(0).levels();
        assertThat(levels).isNotNull().hasSize(1);
        // check level0
        level = levels.get(0);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("AgeGroups");
        assertThat(level.column()).isEqualTo("age");
        assertThat(level.table()).isEqualTo("ageGroups");
        assertThat(level.nameColumn()).isNull();
        assertThat(level.description()).isEqualTo("AgeGroups");
        assertThat(level.type()).isEqualTo(TypeEnum.INTEGER);

        // check cubes
        assertThat(s.cubes()).isNotNull().hasSize(1);
        Cube c = s.cubes().get(0);
        assertThat(c.name()).isEqualTo("Population");
        assertThat(c.description()).isEqualTo("Population");
        assertThat(c.caption()).isEqualTo("Population");
        assertThat(c.dimensionUsageOrDimensions()).isNotNull().hasSize(4);
        List<DimensionUsage> dimensionUsageList = c.dimensionUsageOrDimensions()
            .stream().filter(du -> (du instanceof DimensionUsage))
            .map(du -> (DimensionUsage) du).collect(Collectors.toList());
        assertThat(dimensionUsageList).hasSize(4);

        DimensionUsage du = getDimensionUsage(dimensionUsageList, "Dimension State");
        assertThat(du).isNotNull();
        assertThat(du.source()).isEqualTo("Dimension State");
        assertThat(du.foreignKey()).isEqualTo("state_id");

        du = getDimensionUsage(dimensionUsageList, "Dimension Gender");
        assertThat(du).isNotNull();
        assertThat(du.source()).isEqualTo("Dimension Gender");
        assertThat(du.foreignKey()).isEqualTo("gender_id");

        du = getDimensionUsage(dimensionUsageList, "Dimension Year");
        assertThat(du).isNotNull();
        assertThat(du.source()).isEqualTo("Dimension Year");
        assertThat(du.foreignKey()).isEqualTo("year");

        du = getDimensionUsage(dimensionUsageList, "Dimension AgeGroups");
        assertThat(du).isNotNull();
        assertThat(du.source()).isEqualTo("Dimension AgeGroups");
        assertThat(du.foreignKey()).isEqualTo("age");

    }

    @Test
    void testEmployeesDatabase(@InjectService(filter = "(component.name=" + COMPONENT_NAME + ")") SchemaCreatorServiceFactory schemaCreatorServiceFactory) throws SQLException {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:../../../../src/test/resources/employees.sqlite");
        SchemaCreatorService schemaCreatorService = schemaCreatorServiceFactory.create(dataSource);
        SchemaInitData schemaInitData = new SchemaInitData();
        schemaInitData.setFactTables(List.of("employees"));
        Schema s = schemaCreatorService.createSchema(schemaInitData);
        assertThat(s).isNotNull();
        assertThat(s.dimensions()).isNotNull().hasSize(3);

        PrivateDimension d = getPrivateDimension(s.dimensions(), "Dimension Jobs");
        assertThat(d).isNotNull();
        assertThat(d.hierarchies()).isNotNull().hasSize(1);
        Hierarchy h =  d.hierarchies().get(0);
        List <Level> levels = h.levels();
        assertThat(levels).isNotNull().hasSize(1);
        // check level0
        Level level = levels.get(0);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("Jobs");
        assertThat(level.column()).isEqualTo("job_id");
        assertThat(level.table()).isEqualTo("jobs");
        assertThat(level.nameColumn()).isNull();
        assertThat(level.description()).isEqualTo("Jobs");
        assertThat(level.type()).isEqualTo(TypeEnum.STRING);

        d = getPrivateDimension(s.dimensions(), "Dimension Departments");
        assertThat(d).isNotNull();
        assertThat(d.hierarchies()).isNotNull().hasSize(1);
        h =  d.hierarchies().get(0);
        levels = h.levels();
        assertThat(levels).isNotNull().hasSize(4);
        // check level0
        level = levels.get(0);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("Regions");
        assertThat(level.column()).isEqualTo("region_id");
        assertThat(level.table()).isEqualTo("regions");
        assertThat(level.nameColumn()).isNull();
        assertThat(level.description()).isEqualTo("Regions");
        assertThat(level.type()).isEqualTo(TypeEnum.NUMERIC);
        // check level1
        level = levels.get(1);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("Countries");
        assertThat(level.column()).isEqualTo("country_id");
        assertThat(level.table()).isEqualTo("countries");
        assertThat(level.nameColumn()).isNull();
        assertThat(level.description()).isEqualTo("Countries");
        assertThat(level.type()).isEqualTo(TypeEnum.STRING);
        // check level2
        level = levels.get(2);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("Locations");
        assertThat(level.column()).isEqualTo("location_id");
        assertThat(level.table()).isEqualTo("locations");
        assertThat(level.nameColumn()).isNull();
        assertThat(level.description()).isEqualTo("Locations");
        assertThat(level.type()).isEqualTo(TypeEnum.NUMERIC);
        // check level3
        level = levels.get(3);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("Departments");
        assertThat(level.column()).isEqualTo("department_id");
        assertThat(level.table()).isEqualTo("departments");
        assertThat(level.nameColumn()).isNull();
        assertThat(level.description()).isEqualTo("Departments");
        assertThat(level.type()).isEqualTo(TypeEnum.NUMERIC);

        d = getPrivateDimension(s.dimensions(), "Dimension Employees");
        assertThat(d).isNotNull();
        assertThat(d.hierarchies()).isNotNull().hasSize(2);

        h =  d.hierarchies().get(0);
        levels = h.levels();
        assertThat(levels).isNotNull().hasSize(5);

        h =  d.hierarchies().get(1);
        levels = h.levels();
        assertThat(levels).isNotNull().hasSize(2);

    }
    private PrivateDimension getPrivateDimension(List<PrivateDimension> dimensions, String name) {
        return dimensions.stream().filter(d -> name.equals(d.name()))
            .findAny()
            .orElse(null);
    }

    private DimensionUsage getDimensionUsage(List<DimensionUsage> dimensions, String name) {
        return dimensions.stream().filter(d -> name.equals(d.name()))
            .findAny()
            .orElse(null);
    }

}
