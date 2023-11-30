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
import org.eclipse.daanse.db.jdbc.util.impl.Column;
import org.eclipse.daanse.db.jdbc.util.impl.DBStructure;
import org.eclipse.daanse.db.jdbc.util.impl.Table;
import org.eclipse.daanse.db.jdbc.util.impl.Type;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.templatecreator.api.TemplateCreatorService;
import org.eclipse.daanse.olap.rolap.dbmapper.utils.Utils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Designate(ocd = OdsTemplateCreatorServiceConfig.class, factory = true)
@Component(service = TemplateCreatorService.class, scope = ServiceScope.SINGLETON)
public class OdsTemplateCreatorServiceImpl implements TemplateCreatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OdsTemplateCreatorServiceImpl.class);
    public static final Converter CONVERTER = Converters.standardConverter();
    private OdsTemplateCreatorServiceConfig config;

    @Activate
    public void activate(Map<String, Object> configMap) {
        LOGGER.debug("activate started");
        this.config = CONVERTER.convert(configMap)
            .to(OdsTemplateCreatorServiceConfig.class);
        LOGGER.debug("activate finished");
    }

    @Deactivate
    public void deactivate() {
        config = null;
    }

    @Override
    public void createTemplate(MappingSchema schema) {
        LOGGER.debug("createTemplate started");
        DBStructure dbStructure =  Utils.getDBStructure(schema);

        Path p = Paths.get(
        new StringBuilder(config.odsFolderPath())
            .append(config.odsFileName()).append(config.odsFileSuffix()).toString());
        try {
            Path path = Files.createFile(p);
            SpreadSheet spread = new SpreadSheet();
            dbStructure.getTables().forEach(t -> createSheet(spread, t));
            spread.save(path.toFile());
            LOGGER.debug("createTemplate finished");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSheet(SpreadSheet spread, Table t) {
        List<Column> columnList = t.getColumns();
        Sheet sheet = new Sheet(t.tableName(), 2, columnList.size());
        List<String> columnName = columnList.stream().map(Column::getName).toList();
        List<String> columnExampleValues = columnList.stream().map(i -> getExampleValue(i.getType())).toList();
        for (int i = 0; i < columnList.size(); i++) {
            sheet.getRange(0, i).setValues(columnName.get(i));
        }
        for (int i = 0; i < columnList.size(); i++) {
            sheet.getRange(1, i).setValues(columnExampleValues.get(i));
        }
        spread.appendSheet(sheet);
    }

    private String getExampleValue(Type type) {
        switch (type) {
            case INTEGER, LONG:
                return "2147483647";
            case SMALLINT:
                return "32767";
            case NUMERIC:
                return "32767.5545";
            case STRING:
                return "string";
            case BOOLEAN:
                return "true";
            case DATE:
                return "2023-07-12";
            case TIMESTAMP:
                return "true";
            case TIME:
                return "true";
            default:
                return "string";
        }
    }
}
