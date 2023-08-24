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
package org.eclipse.daanse.grabber;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectResolver;
import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataServiceFactory;
import org.eclipse.daanse.db.jdbc.util.api.DatabaseCreatorService;
import org.eclipse.daanse.db.jdbc.util.impl.Column;
import org.eclipse.daanse.db.jdbc.util.impl.DBStructure;
import org.eclipse.daanse.grabber.api.GrabberInitData;
import org.eclipse.daanse.grabber.api.GrabberService;
import org.eclipse.daanse.grabber.api.StructureProviderService;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.xmla.api.common.enums.LevelTypeEnum;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.measures.MdSchemaMeasuresResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.api.mddataset.CellType;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.eclipse.daanse.grabber.GrabberUtils.getDimensionNameByUniqueName;
import static org.eclipse.daanse.grabber.GrabberUtils.getFactColumns;
import static org.eclipse.daanse.grabber.GrabberUtils.getHierarchyNameByUniqueName;
import static org.eclipse.daanse.grabber.GrabberUtils.getPropertyColumns;
import static org.eclipse.daanse.grabber.MdxQueryProvider.getMdxDictionaryQuery;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.executeStatement;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaCubes;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaDimensions;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaHierarchies;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaLevels;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaMeasures;
import static org.eclipse.daanse.grabber.XmlaServiceClientHelper.getMdSchemaProperties;

@Designate(ocd = GrabberServiceConfig.class, factory = true)
@Component(service = GrabberService.class, scope = ServiceScope.SINGLETON)
public class GrabberServiceImpl implements GrabberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrabberServiceImpl.class);
    private final DataSource dataSource;
    public static final Converter CONVERTER = Converters.standardConverter();
    private GrabberServiceConfig config;

    @Reference
    private JdbcMetaDataServiceFactory jmdsf;

    @Reference
    private StructureProviderService structureProviderService;

    @Reference
    private DatabaseCreatorService databaseCreatorService;

    @Reference
    private DialectResolver dialectResolver;

    @Activate
    public void activate(Map<String, Object> configMap) {
        LOGGER.debug("activate started");
        this.config = CONVERTER.convert(configMap)
            .to(GrabberServiceConfig.class);
        LOGGER.debug("activate finished");
    }

    @Deactivate
    public void deactivate() {
        config = null;
    }

    public GrabberServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Schema grab(GrabberInitData gid) {
        LOGGER.debug("start grabbing");

        if (gid.getSourcesEndPoints() != null) {
            DBStructure dbStructure = structureProviderService.grabStructure(config.targetSchemaName(),
                gid.getSourcesEndPoints());
            try {
                databaseCreatorService.createDatabaseSchema(dataSource, dbStructure);
            } catch (SQLException exception) {
                throw new GrabberException("grabbing error " + exception.getMessage());
            }
            // load dictionary with sources
            Map<Long, String> sourceMap = loadSourceTable(gid.getSourcesEndPoints());
            sourceMap.entrySet().forEach(
                this::grabData
            );
        }
        LOGGER.debug("end grabbing");
        return null;
    }

    private Map<Long, String> loadSourceTable(List<String> sourcesEndPoints) {
        final AtomicLong counter = new AtomicLong();
        Map<Long, String> map =
            sourcesEndPoints.stream().collect(Collectors.toMap(s -> counter.getAndIncrement(), s -> s));
        String sqlQuery = "INSERT INTO sources (source_id, source) VALUES (?, ?)";
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement ps = connection.prepareStatement(sqlQuery)
        ) {
            ps.clearParameters();
            for (Map.Entry<Long, String> e : map.entrySet()) {
                ps.setLong(1, e.getKey());
                ps.setString(2, e.getValue());
                ps.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return map;
    }

    private void grabData(Map.Entry<Long, String> e) {
        String endPointUrl = e.getValue();
        Long sourceId = e.getKey();
        List<MdSchemaLevelsResponseRow> levels = getMdSchemaLevels(endPointUrl);
        List<MdSchemaPropertiesResponseRow> properties = getMdSchemaProperties(endPointUrl);
        List<MdSchemaDimensionsResponseRow> dimensions = getMdSchemaDimensions(endPointUrl);
        List<MdSchemaHierarchiesResponseRow> hierarchies = getMdSchemaHierarchies(endPointUrl);
        List<MdSchemaMeasuresResponseRow> measures = getMdSchemaMeasures(endPointUrl);
        List<MdSchemaCubesResponseRow> cubes = getMdSchemaCubes(endPointUrl);

        List<MdSchemaLevelsResponseRow> regularLevels = levels.stream()
            .filter(it -> it.levelType().isPresent()
                && it.levelType().get().equals(LevelTypeEnum.REGULAR))
            .toList();
        regularLevels.forEach(it -> {
            String dimensionName = getDimensionNameByUniqueName(dimensions, it.dimensionUniqueName().get());
            String hierarchy = getHierarchyNameByUniqueName(hierarchies, it.hierarchyUniqueName().get());
            List<String> propertyColumns = getPropertyColumns(properties, it).stream().map(Column::getName).toList();
            String mdxQuery = getMdxDictionaryQuery(it, properties);
            String sqlQuery = getInsertDictionaryQuery(it, dimensionName, hierarchy, propertyColumns);
            StatementResponse statementResponse = executeStatement(endPointUrl, mdxQuery);
            // 2 -> key, caption;
            executeTargetDatabaseQuery(statementResponse.mdDataSet().cellData().cell(), 2 + propertyColumns.size(), sqlQuery);
        });
        cubes.forEach(c ->{
            Optional<String> optionalCubeName = c.cubeName();
            if (optionalCubeName.isPresent()) {
                List<Column> factColumns = getFactColumns(optionalCubeName.get(), dimensions, measures);
                String mdxQuery = getMdxFactQuery(c, levels);
                String sqlQuery = getInsertFactQuery(c, factColumns);
                StatementResponse statementResponse = executeStatement(endPointUrl, mdxQuery);

            }
        });


    }

    private String getInsertFactQuery(MdSchemaCubesResponseRow c, List<Column> factColumns) {
        StringBuilder sb = new StringBuilder();
        Optional<String> cubeNameOptional = c.cubeName();
        if (cubeNameOptional.isPresent() && !factColumns.isEmpty()) {
            sb.append("INSERT INTO ").append(cubeNameOptional)
                .append("(");
            sb.append(factColumns.stream().map(Column::getName).collect(Collectors.joining(", ")));
            sb.append(" ) VALUES ");
            sb.append(", ").append(factColumns.stream().map(i -> "?").collect(Collectors.joining(", ")));
            sb.append(" ) ");
        }
        return sb.toString();

    }

    private String getMdxFactQuery(MdSchemaCubesResponseRow c,
                                   List<MdSchemaLevelsResponseRow> levels) {
        List<MdSchemaLevelsResponseRow> regularLevels = levels.stream()
            .filter(it -> it.levelType().isPresent()
                && it.levelType().get().equals(LevelTypeEnum.REGULAR))
            .toList();
        Map<String, List<MdSchemaLevelsResponseRow>> map =
            regularLevels.stream().collect(Collectors.groupingBy(s -> s.hierarchyUniqueName().get()));
        List<MdSchemaLevelsResponseRow> lastLevels = map.values().stream().map(it ->
            it.stream().max(
                Comparator.comparingInt(r -> r.levelNumber().get())
            ).get())
            .toList();

        StringBuilder sb = new StringBuilder("DRILLTHROUGH SELECT FROM [");
        sb.append(c.cubeName().get());
        sb.append("]");
        sb.append(" RETURN ");
        sb.append(lastLevels.stream().map(it -> it.levelUniqueName().get()).toList().stream().collect(Collectors.joining(",")));
        return null;
    }

    private String getInsertDictionaryQuery(
        MdSchemaLevelsResponseRow it, String dimension, String hierarchy,
        List<String> columns
    ) {
        StringBuilder sb = new StringBuilder();
        Optional<String> cubeNameOptional = it.cubeName();
        Optional<String> levelNameOptional = it.levelName();
        if (cubeNameOptional.isPresent() && levelNameOptional.isPresent()) {
            sb.append("insert into ").append(getDictionaryTableName(cubeNameOptional.get(),
                dimension, hierarchy,
                levelNameOptional.get()))
                .append("(key, caption");
            if (!columns.isEmpty()) {
                sb.append(",").append(columns.stream().collect(Collectors.joining(",")));
            }
            sb.append(" ) VALUES ");
            sb.append(" ( ?, ?");
            if (!columns.isEmpty()) {
                sb.append(", ").append(columns.stream().map(i -> "?").collect(Collectors.joining(", ")));
            }
            sb.append(" ) ");
        }
        return sb.toString();
    }

    private String getDictionaryTableName(
        String cubeName,
        String dimensionName,
        String hierarchyName,
        String levelName
    ) {
        return new StringBuilder(cubeName).append("_").append(dimensionName).append("_")
            .append(hierarchyName).append("_").append(levelName).toString();
    }

    private void executeTargetDatabaseQuery(
        List<CellType> cellList,
        int rowPortion,
        String sqlQuery
    ) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement ps = connection.prepareStatement(sqlQuery)
        ) {
            Optional<Dialect> dialectOptional = dialectResolver.resolve(dataSource);
            if (dialectOptional.isPresent()) {
                Dialect dialect = dialectOptional.get();
                if (dialect.supportBatchOperations()) {
                    batchExecute(ps, rowPortion, cellList);
                } else {
                    execute(ps, rowPortion, cellList);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void batchExecute(PreparedStatement ps, int portion, List<CellType> cell) throws SQLException {
        long start = System.currentTimeMillis();
        long startBatch = start;
        List<List<CellType>> subSets = getListPortions(cell, portion);
        ps.clearParameters();
        int count = 0;
        for (List<CellType> cells : subSets) {
            for (int i = 0; i < cells.size(); i++) {
                ps.setString(i + 1, cells.get(i).value().value());
            }
            ps.addBatch();
            count++;
            if (count % config.batchSize() == 0) {
                ps.executeBatch();
                LOGGER.debug("execute batch time {}", (System.currentTimeMillis() - startBatch));
                ps.getConnection().commit();
                LOGGER.debug("execute commit time {}", (System.currentTimeMillis() - startBatch));
                startBatch = System.currentTimeMillis();
            }
        }
        LOGGER.debug("---");
        ps.executeBatch();
        LOGGER.debug("execute batch time {}", (System.currentTimeMillis() - startBatch));

        ps.getConnection().commit();
        LOGGER.debug("execute commit time {}", (System.currentTimeMillis() - startBatch));
        ps.getConnection().setAutoCommit(true);
        LOGGER.debug("---");
        LOGGER.debug("execute time {}", (System.currentTimeMillis() - start));
    }

    private void execute(PreparedStatement ps, int portion, List<CellType> cell) throws SQLException {
        long start = System.currentTimeMillis();
        List<List<CellType>> subSets = getListPortions(cell, portion);
        ps.clearParameters();
        for (List<CellType> cells : subSets) {
            for (int i = 0; i < cells.size(); i++) {
                ps.setString(i + 1, cells.get(i).value().value());
            }
            ps.executeUpdate();
        }
        LOGGER.debug("---");
        LOGGER.debug("execute time {}", (System.currentTimeMillis() - start));
    }

    private List<List<CellType>> getListPortions(List<CellType> cell, int portion) {
        final AtomicInteger counter = new AtomicInteger();
        Map<Integer, List<CellType>> groups =
            cell.stream().collect(Collectors.groupingBy(s -> counter.getAndIncrement() / portion));
        return new ArrayList<>(groups.values());
    }

}
