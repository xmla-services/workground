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
package org.eclipse.daanse.db.jdbc.dataloader.ods;

import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectResolver;
import org.eclipse.daanse.db.jdbc.dataloader.api.OdsDataLoadService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Designate(ocd = OdsDataLoadServiceConfig.class, factory = true)
@Component(service = OdsDataLoadService.class, scope = ServiceScope.SINGLETON)
public class OdsDataLoadServiceImpl implements OdsDataLoadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OdsDataLoadServiceImpl.class);
    public static final Converter CONVERTER = Converters.standardConverter();
    @Reference
    private DialectResolver dialectResolver;

    private OdsDataLoadServiceConfig config;

    @Activate
    public void activate(Map<String, Object> configMap) {
        this.config = CONVERTER.convert(configMap)
            .to(OdsDataLoadServiceConfig.class);
    }

    @Deactivate
    public void deactivate() {
        config = null;
    }

    public void loadData(DataSource dataSource) {
        try {
            Path p = Paths.get(config.odsFolderPath()).resolve(new StringBuilder().append(config.odsFilePrefix())
                .append(config.odsFileName()).append(config.odsFileSuffix()).toString());

            if (!p.toFile().exists()) {
                String fileName = config.odsFileName();
                LOGGER.warn("File does not exist - {}.", fileName);
                return;
            }
            Optional<Dialect> dialectOptional = dialectResolver.resolve(dataSource);
            if (dialectOptional.isPresent()) {
                Dialect dialect = dialectOptional.get();
                try (Connection connection = dataSource.getConnection()) {
                    SpreadSheet spread = new SpreadSheet(p.toFile());
                    int numSheets = spread.getNumSheets();
                    LOGGER.debug("Number of sheets: {}", numSheets);
                    List<Sheet> sheets = spread.getSheets();
                    String schemaName = connection.getSchema();
                    if (dialect.supportParallelLoading()) {
                        sheets.parallelStream().forEach(sheet -> loadSheet(connection, dialect, schemaName, sheet));
                    } else {
                        sheets.stream().forEach(sheet -> loadSheet(connection, dialect, schemaName, sheet));
                    }
                } catch (SQLException e) {
                    throw new OdsDataLoadException("Database connection error", e);
                }
            }

        } catch (IOException e){
            throw new OdsDataLoadException("OdsDataLoaderService loadData error", e);
        }
    }

    private void loadSheet(Connection connection, Dialect dialect, String schemaName, Sheet sheet) {
        List<String> headers = getHeaders(sheet);
        StringBuilder b = new StringBuilder();
        b.append("INSERT INTO ");
        b.append(dialect.quoteIdentifier(sheet.getName(), schemaName));
        b.append(" ( ");
        b.append(headers.stream().map(dialect::quoteIdentifier).collect(Collectors.joining(",")));
        b.append(" ) VALUES ");
        b.append(" ( ");
        b.append(headers.stream().map(i -> "?").collect(Collectors.joining(",")));
        b.append(" ) ");

    }

    private List<String> getHeaders(Sheet sheet) {
        List<String> result = new ArrayList<>();
        int columns = sheet.getMaxColumns();
        for (int i = 0; i < columns; i++) {
            Range range = sheet.getRange(0, i);
            if ( hasContent(range) ) {
                result.add(range.getValue().toString());
            }
        }
        return result;
    }

    private boolean hasContent(Range range) {
        Object value = range.getValue();
        return  value != null && !value.toString().isEmpty();
    }
}
