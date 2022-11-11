/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.rolap.sql;

import mondrian.olap.Connection;
import mondrian.rolap.BatchTestCase;
import org.eclipse.daanse.sql.dialect.api.DatabaseProduct;
import org.eclipse.daanse.sql.dialect.api.Dialect;
import mondrian.test.PropertySaver5;
import mondrian.test.SqlPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.SchemaUtil;
import org.opencube.junit5.TestUtil;
import org.opencube.junit5.context.Context;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import static org.opencube.junit5.TestUtil.getDialect;
import static org.opencube.junit5.TestUtil.withSchema;

/**
 * Test that various values of {@link Dialect#allowsSelectNotInGroupBy}
 * produce correctly optimized SQL.
 *
 * @author Eric McDermid
 */
public class SelectNotInGroupByTest extends BatchTestCase {

    public static final String storeDimensionLevelIndependent =
        "<Dimension name=\"CustomStore\">\n"
        + "  <Hierarchy hasAll=\"true\" primaryKey=\"store_id\">\n"
        + "    <Table name=\"store\"/>\n"
        + "    <Level name=\"Store Country\" column=\"store_country\" uniqueMembers=\"true\"/>\n"
        + "    <Level name=\"Store City\" column=\"store_city\" uniqueMembers=\"false\">\n"
        + "      <Property name=\"Store State\" column=\"store_state\"/>\n"
        + "    </Level>\n"
        + "    <Level name=\"Store Name\" column=\"store_name\" uniqueMembers=\"true\"/>\n"
        + "  </Hierarchy>\n"
        + "</Dimension>";

    public static final String storeDimensionLevelDependent =
        "<Dimension name=\"CustomStore\">\n"
        + "  <Hierarchy hasAll=\"true\" primaryKey=\"store_id\">\n"
        + "    <Table name=\"store\"/>\n"
        + "    <Level name=\"Store Country\" column=\"store_country\" uniqueMembers=\"true\"/>\n"
        + "    <Level name=\"Store City\" column=\"store_city\" uniqueMembers=\"false\">\n"
        + "      <Property name=\"Store State\" column=\"store_state\" dependsOnLevelValue=\"true\"/>\n"
        + "    </Level>\n"
        + "    <Level name=\"Store Name\" column=\"store_name\" uniqueMembers=\"true\"/>\n"
        + "  </Hierarchy>\n"
        + "</Dimension>";

    public static final String storeDimensionUniqueLevelDependentProp =
        "<Dimension name=\"CustomStore\">\n"
        + "  <Hierarchy hasAll=\"true\" primaryKey=\"store_id\" uniqueKeyLevelName=\"Store Name\">\n"
        + "    <Table name=\"store\"/>\n"
        + "    <Level name=\"Store Country\" column=\"store_country\" uniqueMembers=\"true\"/>\n"
        + "    <Level name=\"Store City\" column=\"store_city\" uniqueMembers=\"false\">\n"
        + "      <Property name=\"Store State\" column=\"store_state\" dependsOnLevelValue=\"true\"/>\n"
        + "    </Level>\n"
        + "    <Level name=\"Store Name\" column=\"store_name\" uniqueMembers=\"true\"/>\n"
        + "  </Hierarchy>\n"
        + "</Dimension>";

    public static final String storeDimensionUniqueLevelIndependentProp =
        "<Dimension name=\"CustomStore\">\n"
        + "  <Hierarchy hasAll=\"true\" primaryKey=\"store_id\" uniqueKeyLevelName=\"Store Name\">\n"
        + "    <Table name=\"store\"/>\n"
        + "    <Level name=\"Store Country\" column=\"store_country\" uniqueMembers=\"true\"/>\n"
        + "    <Level name=\"Store City\" column=\"store_city\" uniqueMembers=\"false\">\n"
        + "      <Property name=\"Store State\" column=\"store_state\" dependsOnLevelValue=\"false\"/>\n"
        + "    </Level>\n"
        + "    <Level name=\"Store Name\" column=\"store_name\" uniqueMembers=\"true\"/>\n"
        + "  </Hierarchy>\n"
        + "</Dimension>";


    public static final String cubeA =
        "<Cube name=\"CustomSales\">\n"
        + "  <Table name=\"sales_fact_1997\"/>\n"
        + "  <DimensionUsage name=\"CustomStore\" source=\"CustomStore\" foreignKey=\"store_id\"/>\n"
        + "  <Measure name=\"Custom Store Sales\" column=\"store_sales\" aggregator=\"sum\" formatString=\"#,###.00\"/>\n"
        + "  <Measure name=\"Custom Store Cost\" column=\"store_cost\" aggregator=\"sum\"/>\n"
        + "  <Measure name=\"Sales Count\" column=\"product_id\" aggregator=\"count\"/>\n"
        + "</Cube>";

    public static final String queryCubeA =
        "select {[Measures].[Custom Store Sales],[Measures].[Custom Store Cost]} on columns, {[CustomStore].[Store Name].Members} on rows from CustomSales";

    public static final String sqlWithAllGroupBy =
        "select\n"
        + "    `store`.`store_country` as `c0`,\n"
        + "    `store`.`store_city` as `c1`,\n"
        + "    `store`.`store_state` as `c2`,\n"
        + "    `store`.`store_name` as `c3`\n"
        + "from\n"
        + "    `store` as `store`\n"
        + "group by\n"
        + "    `store`.`store_country`,\n"
        + "    `store`.`store_city`,\n"
        + "    `store`.`store_state`,\n"
        + "    `store`.`store_name`\n"
        + "order by\n"
        + "    ISNULL(`c0`) ASC, `c0` ASC,\n"
        + "    ISNULL(`c1`) ASC, `c1` ASC,\n"
        + "    ISNULL(`c3`) ASC, `c3` ASC";

    public static final String sqlWithNoGroupBy =
        "select\n"
        + "    `store`.`store_country` as `c0`,\n"
        + "    `store`.`store_city` as `c1`,\n"
        + "    `store`.`store_state` as `c2`,\n"
        + "    `store`.`store_name` as `c3`\n"
        + "from\n"
        + "    `store` as `store`\n"
        + "order by\n"
        + "    ISNULL(`c0`) ASC, `c0` ASC,\n"
        + "    ISNULL(`c1`) ASC, `c1` ASC,\n"
        + "    ISNULL(`c3`) ASC, `c3` ASC";

    public static final String sqlWithLevelGroupBy =
        "select\n"
        + "    `store`.`store_country` as `c0`,\n"
        + "    `store`.`store_city` as `c1`,\n"
        + "    `store`.`store_state` as `c2`,\n"
        + "    `store`.`store_name` as `c3`\n"
        + "from\n"
        + "    `store` as `store`\n"
        + "group by \n"
        + "    `store`.`store_country`,\n"
        + "    `store`.`store_city`,\n"
        + "    `store`.`store_name`\n"
        + "order by\n"
        + "    ISNULL(`c0`) ASC, `c0` ASC,\n"
        + "    ISNULL(`c1`) ASC, `c1` ASC,\n"
        + "    ISNULL(`c3`) ASC, `c3` ASC";

    private PropertySaver5 propSaver;

    @BeforeEach
    public void beforeEach() {
        propSaver = new PropertySaver5();
        propSaver.set(propSaver.properties.GenerateFormattedSql, true);
        propSaver.set(propSaver.properties.EnableNativeNonEmpty, true);
    }

    @AfterEach
    public void afterEach() {
        propSaver.reset();
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testDependentPropertySkipped(Context context) {
        // Property group by should be skipped only if dialect supports it
        String sqlpat;
        if (dialectAllowsSelectNotInGroupBy(context.createConnection())) {
            sqlpat = sqlWithLevelGroupBy;
        } else {
            sqlpat = sqlWithAllGroupBy;
        }
        SqlPattern[] sqlPatterns = {
            new SqlPattern(DatabaseProduct.MYSQL, sqlpat, sqlpat)
        };

        // Use dimension with level-dependent property
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
            storeDimensionLevelDependent,
            cubeA,
            null,
            null,
            null,
            null);
        withSchema(context, schema);
        assertQuerySqlOrNot(context.createConnection(), queryCubeA, sqlPatterns, false, false, true);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testIndependentPropertyNotSkipped(Context context) {
        SqlPattern[] sqlPatterns = {
            new SqlPattern(
                DatabaseProduct.MYSQL,
                sqlWithAllGroupBy,
                sqlWithAllGroupBy)
        };

        // Use dimension with level-independent property
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
            storeDimensionLevelIndependent,
            cubeA,
            null,
            null,
            null,
            null);
        withSchema(context, schema);
        assertQuerySqlOrNot(context.createConnection(), queryCubeA, sqlPatterns, false, false, true);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testGroupBySkippedIfUniqueLevel(Context context) {
        // If unique level is included and all properties are level
        // dependent, then group by can be skipped regardless of dialect
        SqlPattern[] sqlPatterns = {
            new SqlPattern(
                DatabaseProduct.MYSQL,
                sqlWithNoGroupBy,
                sqlWithNoGroupBy)
        };

        // Use dimension with unique level & level-dependent properties
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
            storeDimensionUniqueLevelDependentProp,
            cubeA,
            null,
            null,
            null,
            null);
        withSchema(context, schema);
        assertQuerySqlOrNot(context.createConnection(), queryCubeA, sqlPatterns, false, false, true);
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testGroupByNotSkippedIfIndependentProperty(Context context) {
        SqlPattern[] sqlPatterns = {
            new SqlPattern(
                DatabaseProduct.MYSQL,
                sqlWithAllGroupBy,
                sqlWithAllGroupBy)
        };

        // Use dimension with unique level but level-indpendent property
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
            storeDimensionUniqueLevelIndependentProp,
            cubeA,
            null,
            null,
            null,
            null);
        withSchema(context, schema);
        assertQuerySqlOrNot(context.createConnection(), queryCubeA, sqlPatterns, false, false, true);
    }

    private boolean dialectAllowsSelectNotInGroupBy(Connection connection) {
        final Dialect dialect = getDialect(connection);
        return dialect.allowsSelectNotInGroupBy();
    }
}

// End SelectNotInGroupByTest.java
