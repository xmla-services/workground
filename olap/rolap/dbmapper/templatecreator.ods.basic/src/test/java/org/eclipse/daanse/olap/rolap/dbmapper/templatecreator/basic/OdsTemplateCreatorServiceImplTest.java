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
package org.eclipse.daanse.olap.rolap.dbmapper.templatecreator.basic;

import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DatabaseMappingSchemaProvider;
import org.eclipse.daanse.olap.rolap.dbmapper.templatecreator.api.TemplateCreatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@RequireConfigurationAdmin
class OdsTemplateCreatorServiceImplTest {
    public static final String COMPONENT_NAME = "org.eclipse.daanse.olap.rolap.dbmapper.templatecreator.basic.OdsTemplateCreatorServiceImpl";
    @TempDir
    Path path;
    @InjectService
    ConfigurationAdmin ca;
    Configuration conf;

    @Test
    @SuppressWarnings("java:S5961")
    void testTemplateForPopulationSchema(
        @InjectService(timeout = 15000, filter = "(&(sample.type=record)(sample.name=Population))") DatabaseMappingSchemaProvider provider,
        @InjectService(cardinality = 0, filter = "(component.name=" + COMPONENT_NAME  + ")") ServiceAware<TemplateCreatorService> odsTemplateCreatorServiceAware
        ) throws InterruptedException, IOException {
        setupOdsDataLoadServiceImpl(path, "test", ".ods", "UTF-8");
        TemplateCreatorService templateCreatorService = odsTemplateCreatorServiceAware.waitForService(1000);
        templateCreatorService.createTemplate(provider.get());
        Path p = Paths.get(new StringBuilder(path.toAbsolutePath().toString())
                .append("test.ods").toString());
        SpreadSheet spread = new SpreadSheet(p.toFile());
        assertThat(spread).isNotNull();
        assertThat(spread.getSheets()).hasSize(7);
        assertThat(spread.getSheets())
            .extracting(Sheet::getName)
            .contains("continent")
            .contains("country")
            .contains("gender")
            .contains("year")
            .contains("state")
            .contains("ageGroups")
            .contains("population");

        Sheet sheet = spread.getSheet("continent");
        assertThat(sheet).isNotNull();
        assertThat(sheet.getMaxRows()).isEqualTo(2);
        assertThat(sheet.getMaxColumns()).isEqualTo(2);
        assertThat(sheet.getRange(0, 0).getValue()).isEqualTo("name");
        assertThat(sheet.getRange(0, 1).getValue()).isEqualTo("id");

        sheet = spread.getSheet("country");
        assertThat(sheet).isNotNull();
        assertThat(sheet.getMaxRows()).isEqualTo(2);
        assertThat(sheet.getMaxColumns()).isEqualTo(3);
        assertThat(sheet.getRange(0, 0).getValue()).isEqualTo("continent_id");
        assertThat(sheet.getRange(0, 1).getValue()).isEqualTo("name");
        assertThat(sheet.getRange(0, 2).getValue()).isEqualTo("id");

        sheet = spread.getSheet("gender");
        assertThat(sheet).isNotNull();
        assertThat(sheet.getMaxRows()).isEqualTo(2);
        assertThat(sheet.getMaxColumns()).isEqualTo(2);
        assertThat(sheet.getRange(0, 0).getValue()).isEqualTo("name");
        assertThat(sheet.getRange(0, 1).getValue()).isEqualTo("gender_id");


        sheet = spread.getSheet("year");
        assertThat(sheet).isNotNull();
        assertThat(sheet.getMaxRows()).isEqualTo(2);
        assertThat(sheet.getMaxColumns()).isEqualTo(2);
        assertThat(sheet.getRange(0, 0).getValue()).isEqualTo("year");
        assertThat(sheet.getRange(0, 1).getValue()).isEqualTo("ordinal");

        sheet = spread.getSheet("state");
        assertThat(sheet).isNotNull();
        assertThat(sheet.getMaxRows()).isEqualTo(2);
        assertThat(sheet.getMaxColumns()).isEqualTo(3);
        assertThat(sheet.getRange(0, 0).getValue()).isEqualTo("contry_id");
        assertThat(sheet.getRange(0, 1).getValue()).isEqualTo("name");
        assertThat(sheet.getRange(0, 2).getValue()).isEqualTo("id");

        sheet = spread.getSheet("ageGroups");
        assertThat(sheet).isNotNull();
        assertThat(sheet.getMaxRows()).isEqualTo(2);
        assertThat(sheet.getMaxColumns()).isEqualTo(7);
        assertThat(sheet.getRange(0, 0).getValue()).isEqualTo("H9");
        assertThat(sheet.getRange(0, 1).getValue()).isEqualTo("H9_Order");
        assertThat(sheet.getRange(0, 3).getValue()).isEqualTo("H1");
        assertThat(sheet.getRange(0, 4).getValue()).isEqualTo("H2");
        assertThat(sheet.getRange(0, 5).getValue()).isEqualTo("age");
        assertThat(sheet.getRange(0, 6).getValue()).isEqualTo("H2_Order");

        sheet = spread.getSheet("population");
        assertThat(sheet).isNotNull();

        assertThat(sheet.getMaxRows()).isEqualTo(2);
        assertThat(sheet.getMaxColumns()).isEqualTo(4);
        assertThat(sheet.getRange(0, 0).getValue()).isEqualTo("year");
        assertThat(sheet.getRange(0, 1).getValue()).isEqualTo("state_id");
        assertThat(sheet.getRange(0, 2).getValue()).isEqualTo("gender_id");
        assertThat(sheet.getRange(0, 3).getValue()).isEqualTo("age");

    }

    private void setupOdsDataLoadServiceImpl(
        Path odsFolderPath, String odsFileName, String odsFileSuffix, String encoding)
        throws IOException {
        conf = ca.getFactoryConfiguration(OdsTemplateCreatorServiceImplTest.COMPONENT_NAME, "1", "?");
        Dictionary<String, Object> dict = new Hashtable<>();
        if (odsFolderPath != null) {
            dict.put("odsFolderPath", odsFolderPath.toAbsolutePath().toString());
        }
        if (odsFileName != null) {
            dict.put("odsFileName", odsFileName);
        }
        if (odsFileSuffix != null) {
            dict.put("odsFileSuffix", odsFileSuffix);
        }

        if (encoding != null) {
            dict.put("encoding", encoding);
        }
        conf.update(dict);
    }

}
