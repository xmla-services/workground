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
        PrivateDimension d = getPrivateDimension(s.dimensions(), "Dimension state");
        assertThat(d).isNotNull();
        assertThat(d.hierarchies()).isNotNull().hasSize(1);
    }

    private PrivateDimension getPrivateDimension(List<PrivateDimension> dimensions, String name) {
        return dimensions.stream().filter(d -> name.equals(d.name()))
            .findAny()
            .orElse(null);
    }

}
