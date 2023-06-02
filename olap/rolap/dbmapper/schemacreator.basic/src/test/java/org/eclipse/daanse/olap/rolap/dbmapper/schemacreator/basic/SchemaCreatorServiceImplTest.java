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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
class SchemaCreatorServiceImplTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.olap.rolap.dbmapper.schemacreator.basic.SchemaCreatorServiceFactoryImpl";
    SQLiteDataSource dataSource;


    @BeforeEach
    void beforeEach() throws SQLException {
        dataSource = new SQLiteDataSource();
        //dataSource.setUrl("jdbc:sqlite:/home/oem/repod/deMondrian/olap/rolap/dbmapper/schemacreator.basic/src/test/resources/population.sqlite");
        dataSource.setUrl("jdbc:sqlite:../../../../src/test/resources/population.sqlite");
    }

    @Test
    void testPrivateDimension(@InjectService(filter = "(component.name=" + COMPONENT_NAME + ")") SchemaCreatorServiceFactory schemaCreatorServiceFactory) throws SQLException {
        SchemaCreatorService schemaCreatorService = schemaCreatorServiceFactory.create(dataSource);
        SchemaInitData schemaInitData = new SchemaInitData();
        schemaInitData.setFactTables(List.of("population"));
        Schema s = schemaCreatorService.createSchema(schemaInitData);
        assertThat(s).isNotNull();
        assertThat(s.dimensions()).isNotNull().hasSize(4);

        PrivateDimension d = getPrivateDimension(s.dimensions(), "Dimension State");
        assertThat(d).isNotNull();
        assertThat(d.hierarchies()).isNotNull().hasSize(1);
        List <Level> levels = d.hierarchies().get(0).levels();
        assertThat(levels).isNotNull().hasSize(3);
        // check level0
        Level level = levels.get(0);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("Continent");
        assertThat(level.column()).isEqualTo("id");
        assertThat(level.table()).isEqualTo("continent");
        assertThat(level.nameColumn()).isEqualTo("name");
        assertThat(level.description()).isEqualTo("Continent");
        // check level1
        level = levels.get(1);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("Country");
        assertThat(level.column()).isEqualTo("id");
        assertThat(level.table()).isEqualTo("country");
        assertThat(level.nameColumn()).isEqualTo("name");
        assertThat(level.description()).isEqualTo("Country");
        // check level2
        level = levels.get(2);
        assertThat(level).isNotNull();
        assertThat(level.name()).isEqualTo("State");
        assertThat(level.column()).isEqualTo("id");
        assertThat(level.table()).isEqualTo("state");
        assertThat(level.nameColumn()).isEqualTo("name");
        assertThat(level.description()).isEqualTo("State");

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

    }

    private PrivateDimension getPrivateDimension(List<PrivateDimension> dimensions, String name) {
        return dimensions.stream().filter(d -> name.equals(d.name()))
            .findAny()
            .orElse(null);
    }

}
