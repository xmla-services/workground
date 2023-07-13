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

import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DbMappingSchemaProvider;
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
import java.util.Dictionary;
import java.util.Hashtable;

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
    void test(
        @InjectService(timeout = 15000,filter = "(&(sample.type=record)(sample.name=Population))") DbMappingSchemaProvider provider,
        @InjectService(cardinality = 0, filter = "(component.name=" + COMPONENT_NAME  + ")") ServiceAware<TemplateCreatorService> odsTemplateCreatorServiceAware
        ) throws InterruptedException, IOException {
        setupOdsDataLoadServiceImpl(path, "test", ".ods", "UTF-8");
        TemplateCreatorService templateCreatorService = odsTemplateCreatorServiceAware.waitForService(1000);
        templateCreatorService.createTemplate(provider.get());
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
