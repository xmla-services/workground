/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.test;

import static mondrian.enums.DatabaseProduct.getDatabaseProduct;
import static org.opencube.junit5.TestUtil.assertQueryReturns;
import static org.opencube.junit5.TestUtil.getDialect;
import static org.opencube.junit5.TestUtil.withSchema;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.rolap.mapping.api.model.CatalogMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.enums.DataType;
import org.eclipse.daanse.rolap.mapping.api.model.enums.MeasureAggregatorType;
import org.eclipse.daanse.rolap.mapping.instance.complex.foodmart.FoodmartMappingSupplier;
import org.eclipse.daanse.rolap.mapping.modifier.pojo.PojoMappingModifier;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionConnectorMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.HierarchyMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableColumnDefinitionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableRowCellMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.InlineTableRowMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinQueryMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.JoinedQueryElementMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.LevelMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.StandardDimensionMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalog;

import mondrian.enums.DatabaseProduct;

/**
 * Unit test for the InlineTable element, defining tables whose values are held
 * in the Mondrian schema file, not in the database.
 *
 * @author jhyde
 */
class InlineTableTest {

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalog.class, dataloader = FastFoodmardDataLoader.class )
    void testInlineTable(Context context) {
        final String cubeName = "Sales_inline";
        class TestInlineTableModifier extends PojoMappingModifier {

            public TestInlineTableModifier(CatalogMapping catalog) {
                super(catalog);
            }
            
            protected List<CubeMapping> cubes(List<? extends CubeMapping> cubes) {
                List<CubeMapping> result = new ArrayList<>();
                result.addAll(super.cubes(cubes));
                result.add(PhysicalCubeMappingImpl.builder()
                    .withName(cubeName)
                    .withQuery(TableQueryMappingImpl.builder().withName("sales_fact_1997").build())
                    .withDimensionConnectors(List.of(
                    	DimensionConnectorMappingImpl.builder()
                    		.withOverrideDimensionName("Time")                            
                            .withForeignKey("time_id")
                            .withDimension(FoodmartMappingSupplier.DIMENSION_TIME)
                            .build(),
                        DimensionConnectorMappingImpl.builder()
                        	.withOverrideDimensionName("Alternative Promotion")
                        	.withForeignKey("promotion_id")
                        	.withDimension(
                        		StandardDimensionMappingImpl.builder()
                        			.withName("Alternative Promotion")                            
                        			.withHierarchies(List.of(
                        				HierarchyMappingImpl.builder()
                        					.withHasAll(true)
                        					.withPrimaryKey("promo_id")
                        					.withQuery(InlineTableQueryMappingImpl.builder()
                        							.withAlias("alt_promotion")
                        							.withColumnDefinitions(List.of(
                        								InlineTableColumnDefinitionMappingImpl.builder()
                        									.withName("promo_id")
                        									.withType(DataType.NUMERIC)
                        									.build(),
                        								InlineTableColumnDefinitionMappingImpl.builder()
                        									.withName("promo_name")
                        									.withType(DataType.STRING)
                        									.build()
                        							))
                        							.withRows(List.of(
                        								InlineTableRowMappingImpl.builder()
                        									.withCells(List.of(
                        										InlineTableRowCellMappingImpl.builder()
                        										.withColumnName("promo_id")
                        										.withValue("0")
                        										.build(),
                        										InlineTableRowCellMappingImpl.builder()
                        										.withColumnName("promo_name")
                        										.withValue("Promo0")
                        										.build()
                        									))
                        									.build(),
                        								InlineTableRowMappingImpl.builder()
                        									.withCells(List.of(
                        										InlineTableRowCellMappingImpl.builder()
                        										.withColumnName("promo_id")
                        										.withValue("1")
                        										.build(),
                        										InlineTableRowCellMappingImpl.builder()
                        										.withColumnName("promo_name")
                        										.withValue("Promo1")
                        										.build()
                        									))
                        									.build()
                        							))
                        							.build()
                        					)
                        					.withLevels(List.of(
                        						LevelMappingImpl.builder()
                        							.withName("Alternative Promotion").withColumn("promo_id")
                        							.withNameColumn("promo_name")
                        							.withUniqueMembers(true)
                        							.build()
                        					))
                        					.build()
                            ))
                            .build()
                        ).build()
                    ))
                    .withMeasureGroups(List.of(
                    	MeasureGroupMappingImpl.builder()
                    	.withMeasures(List.of(
                            MeasureMappingImpl.builder()
                                .withName("Unit Sales")
                                .withColumn("unit_sales")
                                .withAggregatorType(MeasureAggregatorType.SUM)
                                .withFormatString("Standard")
                                .withVisible(true)
                                .build(),
                            MeasureMappingImpl.builder()
                                .withName("Store Sales")
                                .withColumn("store_sales")
                                .withAggregatorType(MeasureAggregatorType.SUM)                                
                                .withFormatString("#,###.00")
                                .build()                    			
                    	))
                    	.build()
                    ))
                    .build());
                return result;
            }
        }
            
        /*
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
            null,
            "<Cube name=\"" + cubeName + "\">\n"
            + "  <Table name=\"sales_fact_1997\"/>\n"
            + "  <DimensionUsage name=\"Time\" source=\"Time\" foreignKey=\"time_id\"/>\n"
            + "  <Dimension name=\"Alternative Promotion\" foreignKey=\"promotion_id\">\n"
            + "    <Hierarchy hasAll=\"true\" primaryKey=\"promo_id\">\n"
            + "      <InlineTable alias=\"alt_promotion\">\n"
            + "        <ColumnDefs>\n"
            + "          <ColumnDef name=\"promo_id\" type=\"Numeric\"/>\n"
            + "          <ColumnDef name=\"promo_name\" type=\"String\"/>\n"
            + "        </ColumnDefs>\n"
            + "        <Rows>\n"
            + "          <Row>\n"
            + "            <Value column=\"promo_id\">0</Value>\n"
            + "            <Value column=\"promo_name\">Promo0</Value>\n"
            + "          </Row>\n"
            + "          <Row>\n"
            + "            <Value column=\"promo_id\">1</Value>\n"
            + "            <Value column=\"promo_name\">Promo1</Value>\n"
            + "          </Row>\n"
            + "        </Rows>\n"
            + "      </InlineTable>\n"
            + "      <Level name=\"Alternative Promotion\" column=\"promo_id\" nameColumn=\"promo_name\" uniqueMembers=\"true\"/> \n"
            + "    </Hierarchy>\n"
            + "  </Dimension>\n"
            + "  <Measure name=\"Unit Sales\" column=\"unit_sales\" aggregator=\"sum\"\n"
            + "      formatString=\"Standard\" visible=\"false\"/>\n"
            + "  <Measure name=\"Store Sales\" column=\"store_sales\" aggregator=\"sum\"\n"
            + "      formatString=\"#,###.00\"/>\n"
            + "</Cube>",
            null,
            null,
            null,
            null);
        withSchema(context, schema);
         */
        withSchema(context, TestInlineTableModifier::new);
        assertQueryReturns(context.getConnection(),
            "select {[Alternative Promotion].[All Alternative Promotions].children} ON COLUMNS\n"
            + "from [" + cubeName + "] ",
            "Axis #0:\n"
            + "{}\n"
            + "Axis #1:\n"
            + "{[Alternative Promotion].[Promo0]}\n"
            + "{[Alternative Promotion].[Promo1]}\n"
            + "Row #0: 195,448\n"
            + "Row #0: \n");
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalog.class, dataloader = FastFoodmardDataLoader.class )
    void testInlineTableInSharedDim(Context context) {
        final String cubeName = "Sales_inline_shared";

        class TestInlineTableInSharedDimModifier extends PojoMappingModifier {

            public TestInlineTableInSharedDimModifier(CatalogMapping catalog) {
                super(catalog);
            }
                        
            private static final StandardDimensionMappingImpl d = StandardDimensionMappingImpl.builder()
                    .withName("Shared Alternative Promotion")
                    .withHierarchies(List.of(
                        HierarchyMappingImpl.builder()
                            .withHasAll(true)
                            .withPrimaryKey("promo_id")
                            .withQuery(InlineTableQueryMappingImpl.builder()                            		
                                .withAlias("alt_promotion")
                                .withColumnDefinitions(List.of(
                                	InlineTableColumnDefinitionMappingImpl.builder()
                                        .withName("promo_id")
                                        .withType(DataType.NUMERIC)
                                        .build(),
                                    InlineTableColumnDefinitionMappingImpl.builder()
                                        .withName("promo_name")
                                        .withType(DataType.STRING)
                                        .build()
                                    ))
                                .withRows(List.of(
                                	InlineTableRowMappingImpl.builder()
                                		.withCells(List.of(
                                			InlineTableRowCellMappingImpl.builder()                                			
                                                .withColumnName("promo_id")
                                                .withValue("0")
                                                .build(),
                                            InlineTableRowCellMappingImpl.builder()
                                                .withColumnName("promo_name")
                                                .withValue("First promo")
                                                .build()
                                        ))
                                        .build(),
                                    InlineTableRowMappingImpl.builder()
                                    	.withCells(List.of(
                                        	InlineTableRowCellMappingImpl.builder()
                                        		.withColumnName("promo_id")
                                        		.withValue("1")
                                                .build(),
                                            InlineTableRowCellMappingImpl.builder()
                                            	.withColumnName("promo_name")
                                            	.withValue("Second promo")
                                                .build()
                                        ))
                                        .build()
                                ))
                                .build())
                            .withLevels(List.of(
                                LevelMappingImpl.builder()
                                    .withName("Alternative Promotion")
                                    .withColumn("promo_id")
                                    .withNameColumn("promo_name")
                                    .withUniqueMembers(true)
                                    .build()
                            ))
                            .build())).build();

            @Override
            protected List<CubeMapping> cubes(List<? extends CubeMapping> cubes) {
                List<CubeMapping> result = new ArrayList<>();
                result.addAll(super.cubes(cubes));
                result.add(PhysicalCubeMappingImpl.builder()
                    .withName(cubeName)
                    .withQuery(TableQueryMappingImpl.builder().withName("sales_fact_1997").build())
                    .withDimensionConnectors(List.of(
                    	DimensionConnectorMappingImpl.builder()
                    		.withOverrideDimensionName("Time")
                    		.withDimension((DimensionMappingImpl) look(FoodmartMappingSupplier.DIMENSION_TIME))                            
                            .withForeignKey("time_id")
                            .build(),
                        DimensionConnectorMappingImpl.builder()
                            .withOverrideDimensionName("Shared Alternative Promotion")
                            .withDimension(d)
                            .withForeignKey("promotion_id")
                            .build()
                    ))
                    .withMeasureGroups(List.of(
                    	MeasureGroupMappingImpl.builder()
                    	.withMeasures(List.of(
                            MeasureMappingImpl.builder()
                                .withName("Unit Sales")
                                .withColumn("unit_sales")
                                .withAggregatorType(MeasureAggregatorType.SUM)                                
                                .withFormatString("Standard")
                                .withVisible(false)
                                .build(),
                            MeasureMappingImpl.builder()
                                .withName("Store Sales")
                                .withColumn("store_sales")
                                .withAggregatorType(MeasureAggregatorType.SUM)
                                .withFormatString("#,###.00")
                                .build()                    			
                    	))
                    	.build()	
                        ))
                    .build());
                return result;
            }
            
            }

       /*
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
            null,
            "  <Dimension name=\"Shared Alternative Promotion\">\n"
            + "    <Hierarchy hasAll=\"true\" primaryKey=\"promo_id\">\n"
            + "      <InlineTable alias=\"alt_promotion\">\n"
            + "        <ColumnDefs>\n"
            + "          <ColumnDef name=\"promo_id\" type=\"Numeric\"/>\n"
            + "          <ColumnDef name=\"promo_name\" type=\"String\"/>\n"
            + "        </ColumnDefs>\n"
            + "        <Rows>\n"
            + "          <Row>\n"
            + "            <Value column=\"promo_id\">0</Value>\n"
            + "            <Value column=\"promo_name\">First promo</Value>\n"
            + "          </Row>\n"
            + "          <Row>\n"
            + "            <Value column=\"promo_id\">1</Value>\n"
            + "            <Value column=\"promo_name\">Second promo</Value>\n"
            + "          </Row>\n"
            + "        </Rows>\n"
            + "      </InlineTable>\n"
            + "      <Level name=\"Alternative Promotion\" column=\"promo_id\" nameColumn=\"promo_name\" uniqueMembers=\"true\"/> \n"
            + "    </Hierarchy>\n"
            + "  </Dimension>\n"
            + "<Cube name=\""
            + cubeName
            + "\">\n"
            + "  <Table name=\"sales_fact_1997\"/>\n"
            + "  <DimensionUsage name=\"Time\" source=\"Time\" foreignKey=\"time_id\"/>\n"
            + "  <DimensionUsage name=\"Shared Alternative Promotion\" source=\"Shared Alternative Promotion\" foreignKey=\"promotion_id\"/>\n"
            + "  <Measure name=\"Unit Sales\" column=\"unit_sales\" aggregator=\"sum\"\n"
            + "      formatString=\"Standard\" visible=\"false\"/>\n"
            + "  <Measure name=\"Store Sales\" column=\"store_sales\" aggregator=\"sum\"\n"
            + "      formatString=\"#,###.00\"/>\n"
            + "</Cube>",
            null,
            null,
            null,
            null);
        withSchema(context, schema);
        */
        withSchema(context, TestInlineTableInSharedDimModifier::new);
        assertQueryReturns(context.getConnection(),
            "select {[Shared Alternative Promotion].[All Shared Alternative Promotions].children} ON COLUMNS\n"
            + "from [" + cubeName + "] ",
            "Axis #0:\n"
            + "{}\n"
            + "Axis #1:\n"
            + "{[Shared Alternative Promotion].[First promo]}\n"
            + "{[Shared Alternative Promotion].[Second promo]}\n"
            + "Row #0: 195,448\n"
            + "Row #0: \n");
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalog.class, dataloader = FastFoodmardDataLoader.class )
    void testInlineTableSnowflake(Context context) {
        if (getDatabaseProduct(getDialect(context.getConnection()).getDialectName())
            == DatabaseProduct.INFOBRIGHT)
        {
            // Infobright has a bug joining an inline table. Gives error
            // "Illegal mix of collations (ascii_bin,IMPLICIT) and
            // (utf8_general_ci,COERCIBLE) for operation '='".
            return;
        }
        final String cubeName = "Sales_inline_snowflake";
        class TestInlineTableSnowflakeModifier extends PojoMappingModifier {

            public TestInlineTableSnowflakeModifier(CatalogMapping catalog) {
                super(catalog);
            }
            
            protected List<CubeMapping> cubes(List<? extends CubeMapping> cubes) {
                List<CubeMapping> result = new ArrayList<>();
                result.addAll(super.cubes(cubes));
                JoinQueryMappingImpl j = JoinQueryMappingImpl.builder()
                		.withLeft(JoinedQueryElementMappingImpl.builder()
                				.withKey("store_country")
                				.withQuery(TableQueryMappingImpl.builder().withName("store").build())
                				.build())
                		.withRight(JoinedQueryElementMappingImpl.builder()
                				.withKey("nation_name")
                				.withQuery(InlineTableQueryMappingImpl.builder()
                                        .withAlias("nation")
                                        .withColumnDefinitions(List.of(
                                        	InlineTableColumnDefinitionMappingImpl.builder()
                                        		.withName("nation_name")
                                                .withType(DataType.STRING)
                                                .build(),
                                            InlineTableColumnDefinitionMappingImpl.builder()
                                                .withName("nation_shortcode")
                                                .withType(DataType.STRING)
                                                .build()
                                        ))
                                        .withRows(List.of(
                                            InlineTableRowMappingImpl.builder()
                                            	.withCells(List.of(
                                                    InlineTableRowCellMappingImpl.builder()
                                                    	.withColumnName("nation_name")
                                                        .withValue("USA")
                                                        .build(),
                                                    InlineTableRowCellMappingImpl.builder()
                                                        .withColumnName("nation_shortcode")
                                                        .withValue("US")
                                                        .build()
                                                ))
                                                .build(),
                                            InlineTableRowMappingImpl.builder()
                                            	.withCells(List.of(
                                                    InlineTableRowCellMappingImpl.builder()
                                                        .withColumnName("nation_name")
                                                        .withValue("Mexico")
                                                        .build(),
                                                    InlineTableRowCellMappingImpl.builder()
                                                        .withColumnName("nation_shortcode")
                                                        .withValue("MX")
                                                        .build()
                                                ))
                                                .build(),
                                            InlineTableRowMappingImpl.builder()
                                            	.withCells(List.of(
                                            		InlineTableRowCellMappingImpl.builder()
                                                        .withColumnName("nation_name")
                                                        .withValue("Canada")
                                                        .build(),
                                                    InlineTableRowCellMappingImpl.builder()
                                                        .withColumnName("nation_shortcode")
                                                        .withValue("CA")
                                                        .build()
                                                ))
                                                .build()

                                        )).build())
                				.build())
                		.build();
                
                result.add(PhysicalCubeMappingImpl.builder()
                    .withName(cubeName)
                    .withQuery(TableQueryMappingImpl.builder().withName("sales_fact_1997").build())
                    .withDimensionConnectors(List.of(
                    	DimensionConnectorMappingImpl.builder()
                    		.withOverrideDimensionName("Time")
                    		.withDimension((DimensionMappingImpl) look(FoodmartMappingSupplier.DIMENSION_TIME))
                            .withForeignKey("time_id")
                            .build(),
                      	DimensionConnectorMappingImpl.builder()
                    		.withOverrideDimensionName("Store")                    		
                            .withForeignKey("store_id")
                            .withDimension(                            
                            	StandardDimensionMappingImpl.builder()
                            		.withName("Store")
                            		.withHierarchies(List.of(
                            			HierarchyMappingImpl.builder()
                            				.withHasAll(true)
                            				.withPrimaryKeyTable("store")
                            				.withPrimaryKey("store_id")
                            				.withQuery(j)
                                    .withLevels(List.of(
                                        LevelMappingImpl.builder()
                                            .withName("Store Country")
                                            .withTable("nation")
                                            .withColumn("nation_name")
                                            .withNameColumn("nation_shortcode")
                                            .withUniqueMembers(true)
                                            .build(),
                                        LevelMappingImpl.builder()
                                            .withName("Store State")
                                            .withTable("store")
                                            .withColumn("store_state")
                                            .withUniqueMembers(true)
                                            .build(),
                                        LevelMappingImpl.builder()
                                            .withName("Store City")
                                            .withTable("store")
                                            .withColumn("store_city")
                                            .withUniqueMembers(false)
                                            .build(),
                                        LevelMappingImpl.builder()
                                            .withName("Store Name")
                                            .withTable("store")
                                            .withColumn("store_name")
                                            .withUniqueMembers(true)
                                            .build()
                                        ))
                                    .build()
                            ))
                            .build()
                          ).build()
                    ))
                    .withMeasureGroups(List.of(
                    		MeasureGroupMappingImpl.builder()
                    		.withMeasures(List.of(
                                MeasureMappingImpl.builder()
                                    .withName("Unit Sales")
                                    .withColumn("unit_sales")
                                    .withAggregatorType(MeasureAggregatorType.SUM)
                                    .withFormatString("Standard")
                                    .withVisible(false)
                                    .build(),
                                MeasureMappingImpl.builder()
                                    .withName("Store Sales")
                                    .withColumn("store_sales")
                                    .withAggregatorType(MeasureAggregatorType.SUM)
                                    .withFormatString("#,###.00")
                                    .build()                    				
                    				))
                    		.build()
                    		))
                    .build());
                return result;            	
            }
            
        }

        /*
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
            null,
            "<Cube name=\"" + cubeName + "\">\n"
            + "  <Table name=\"sales_fact_1997\"/>\n"
            + "  <DimensionUsage name=\"Time\" source=\"Time\" foreignKey=\"time_id\"/>\n"
            + "  <Dimension name=\"Store\" foreignKeyTable=\"store\" foreignKey=\"store_id\">\n"
            + "    <Hierarchy hasAll=\"true\" primaryKeyTable=\"store\" primaryKey=\"store_id\">\n"
            + "      <Join leftKey=\"store_country\" rightKey=\"nation_name\">\n"
            + "      <Table name=\"store\"/>\n"
            + "        <InlineTable alias=\"nation\">\n"
            + "          <ColumnDefs>\n"
            + "            <ColumnDef name=\"nation_name\" type=\"String\"/>\n"
            + "            <ColumnDef name=\"nation_shortcode\" type=\"String\"/>\n"
            + "          </ColumnDefs>\n"
            + "          <Rows>\n"
            + "            <Row>\n"
            + "              <Value column=\"nation_name\">USA</Value>\n"
            + "              <Value column=\"nation_shortcode\">US</Value>\n"
            + "            </Row>\n"
            + "            <Row>\n"
            + "              <Value column=\"nation_name\">Mexico</Value>\n"
            + "              <Value column=\"nation_shortcode\">MX</Value>\n"
            + "            </Row>\n"
            + "            <Row>\n"
            + "              <Value column=\"nation_name\">Canada</Value>\n"
            + "              <Value column=\"nation_shortcode\">CA</Value>\n"
            + "            </Row>\n"
            + "          </Rows>\n"
            + "        </InlineTable>\n"
            + "      </Join>\n"
            + "      <Level name=\"Store Country\" table=\"nation\" column=\"nation_name\" nameColumn=\"nation_shortcode\" uniqueMembers=\"true\"/>\n"
            + "      <Level name=\"Store State\" table=\"store\" column=\"store_state\" uniqueMembers=\"true\"/>\n"
            + "      <Level name=\"Store City\" table=\"store\" column=\"store_city\" uniqueMembers=\"false\"/>\n"
            + "      <Level name=\"Store Name\" table=\"store\" column=\"store_name\" uniqueMembers=\"true\"/>\n"
            + "    </Hierarchy>\n"
            + "  </Dimension>\n"
            + "  <Measure name=\"Unit Sales\" column=\"unit_sales\" aggregator=\"sum\"\n"
            + "      formatString=\"Standard\" visible=\"false\"/>\n"
            + "  <Measure name=\"Store Sales\" column=\"store_sales\" aggregator=\"sum\"\n"
            + "      formatString=\"#,###.00\"/>\n"
            + "</Cube>",
            null,
            null,
            null,
            null);
        withSchema(context, schema);
         */
        withSchema(context, TestInlineTableSnowflakeModifier::new);
        assertQueryReturns(context.getConnection(),
            "select {[Store].children} ON COLUMNS\n"
            + "from [" + cubeName + "] ",
            "Axis #0:\n"
            + "{}\n"
            + "Axis #1:\n"
            + "{[Store].[CA]}\n"
            + "{[Store].[MX]}\n"
            + "{[Store].[US]}\n"
            + "Row #0: \n"
            + "Row #0: \n"
            + "Row #0: 266,773\n");
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalog.class, dataloader = FastFoodmardDataLoader.class )
    void testInlineTableDate(Context context) {
        final String cubeName = "Sales_Inline_Date";
        class TestInlineTableDateModifier extends PojoMappingModifier {

            public TestInlineTableDateModifier(CatalogMapping catalog) {
                super(catalog);
            }
            
            @Override
            protected List<CubeMapping> cubes(List<? extends CubeMapping> cubes) {
                List<CubeMapping> result = new ArrayList<>();
                result.addAll(super.cubes(cubes));

                JoinQueryMappingImpl j = JoinQueryMappingImpl.builder()
                		.withLeft(JoinedQueryElementMappingImpl.builder()
                				.withKey("store_country")
                				.withQuery(TableQueryMappingImpl.builder().withName("store").build())
                				.build())
                		.withRight(JoinedQueryElementMappingImpl.builder()
                				.withKey("nation_name")
                				.withQuery(InlineTableQueryMappingImpl.builder()
                                        .withAlias("nation")
                                        .withColumnDefinitions(List.of(
                                        	InlineTableColumnDefinitionMappingImpl.builder()
                                        		.withName("nation_name")
                                                .withType(DataType.STRING)
                                                .build(),
                                            InlineTableColumnDefinitionMappingImpl.builder()
                                                .withName("nation_shortcode")
                                                .withType(DataType.STRING)
                                                .build()
                                        ))
                                        .withRows(List.of(
                                            InlineTableRowMappingImpl.builder()
                                            	.withCells(List.of(
                                                    InlineTableRowCellMappingImpl.builder()
                                                    	.withColumnName("nation_name")
                                                        .withValue("USA")
                                                        .build(),
                                                    InlineTableRowCellMappingImpl.builder()
                                                        .withColumnName("nation_shortcode")
                                                        .withValue("US")
                                                        .build()
                                                ))
                                                .build(),
                                            InlineTableRowMappingImpl.builder()
                                            	.withCells(List.of(
                                                    InlineTableRowCellMappingImpl.builder()
                                                        .withColumnName("nation_name")
                                                        .withValue("Mexico")
                                                        .build(),
                                                    InlineTableRowCellMappingImpl.builder()
                                                        .withColumnName("nation_shortcode")
                                                        .withValue("MX")
                                                        .build()
                                                ))
                                                .build(),
                                            InlineTableRowMappingImpl.builder()
                                            	.withCells(List.of(
                                            		InlineTableRowCellMappingImpl.builder()
                                                        .withColumnName("nation_name")
                                                        .withValue("Canada")
                                                        .build(),
                                                    InlineTableRowCellMappingImpl.builder()
                                                        .withColumnName("nation_shortcode")
                                                        .withValue("CA")
                                                        .build()
                                                ))
                                                .build()

                                        )).build())
                				.build())
                		.build();
                
                result.add(PhysicalCubeMappingImpl.builder()
                    .withName(cubeName)
                    .withQuery(TableQueryMappingImpl.builder().withName("sales_fact_1997").build())
                    .withDimensionConnectors(List.of(
                    	DimensionConnectorMappingImpl.builder()
                    		.withOverrideDimensionName("Time")
                    		.withDimension((DimensionMappingImpl) look(FoodmartMappingSupplier.DIMENSION_TIME))
                            .withForeignKey("time_id")
                            .build(),
                        DimensionConnectorMappingImpl.builder()
                        	.withOverrideDimensionName("Alternative Promotion")
                        	.withForeignKey("promotion_id")
                        	.withDimension(StandardDimensionMappingImpl.builder()
                            .withName("Alternative Promotion")
                            .withHierarchies(List.of(
                                HierarchyMappingImpl.builder()
                                    .withHasAll(true)
                                    .withPrimaryKey("id")
                                    .withQuery(InlineTableQueryMappingImpl.builder()
                                        .withAlias("inline_promo")
                                        .withColumnDefinitions(List.of(
                                            InlineTableColumnDefinitionMappingImpl.builder()
                                            	.withName("id")
                                                .withType(DataType.NUMERIC)
                                                .build(),
                                            InlineTableColumnDefinitionMappingImpl.builder()
                                                .withName("date")
                                                .withType(DataType.DATE)
                                                .build()
                                        ))
                                        .withRows(List.of(
                                        	InlineTableRowMappingImpl.builder()
                                        		.withCells(List.of(
                                                    InlineTableRowCellMappingImpl.builder()
                                                    	.withColumnName("id")
                                                        .withValue("1")
                                                        .build(),
                                                    InlineTableRowCellMappingImpl.builder()
                                                    	.withColumnName("date")
                                                        .withValue("2008-04-29")
                                                        .build()
                                                ))
                                                .build(),
                                            InlineTableRowMappingImpl.builder()
                                                .withCells(List.of(
                                                    InlineTableRowCellMappingImpl.builder()
                                                        .withColumnName("id")
                                                        .withValue("2")
                                                        .build(),
                                                    InlineTableRowCellMappingImpl.builder()
                                                        .withColumnName("date")
                                                        .withValue("2007-01-20")
                                                        .build()
                                                ))
                                                .build()

                                        ))
                                        .build()
                                    )
                                    .withLevels(List.of(
                                        LevelMappingImpl.builder()
                                            .withName("Alternative Promotion")
                                            .withColumn("id")
                                            .withNameColumn("date")
                                            .withUniqueMembers(true)
                                            .build()
                                    ))
                                    .build()
                            ))
                            .build()
                        ).build()
                    ))
                   .withMeasureGroups(List.of(
                		   MeasureGroupMappingImpl.builder()
                		   .withMeasures(List.of(
                               MeasureMappingImpl.builder()
                                   .withName("Unit Sales")
                                   .withColumn("unit_sales")
                                   .withAggregatorType(MeasureAggregatorType.SUM)
                                   .withFormatString("Standard")
                                   .withVisible(false)
                                   .build(),
                               MeasureMappingImpl.builder()
                                   .withName("Store Sales")
                                   .withColumn("store_sales")
                                   .withAggregatorType(MeasureAggregatorType.SUM)
                                   .withFormatString("#,###.00")
                                   .build()
                			))
                		   .build()
                	))
                    .build());
                return result;
            	
            }
        }
        /*
        String baseSchema = TestUtil.getRawSchema(context);
        String schema = SchemaUtil.getSchema(baseSchema,
            null,
            "<Cube name=\"" + cubeName + "\">\n"
            + "  <Table name=\"sales_fact_1997\"/>\n"
            + "  <DimensionUsage name=\"Time\" source=\"Time\" foreignKey=\"time_id\"/>\n"
            + "  <Dimension name=\"Alternative Promotion\" foreignKey=\"promotion_id\">\n"
            + "    <Hierarchy hasAll=\"true\" primaryKey=\"id\">\n"
            + "        <InlineTable alias=\"inline_promo\">\n"
            + "          <ColumnDefs>\n"
            + "            <ColumnDef name=\"id\" type=\"Numeric\"/>\n"
            + "            <ColumnDef name=\"date\" type=\"Date\"/>\n"
            + "          </ColumnDefs>\n"
            + "          <Rows>\n"
            + "            <Row>\n"
            + "              <Value column=\"id\">1</Value>\n"
            + "              <Value column=\"date\">2008-04-29</Value>\n"
            + "            </Row>\n"
            + "            <Row>\n"
            + "              <Value column=\"id\">2</Value>\n"
            + "              <Value column=\"date\">2007-01-20</Value>\n"
            + "            </Row>\n"
            + "          </Rows>\n"
            + "        </InlineTable>\n"
            + "      <Level name=\"Alternative Promotion\" column=\"id\" nameColumn=\"date\" uniqueMembers=\"true\"/> \n"
            + "    </Hierarchy>\n"
            + "  </Dimension>\n"
            + "  <Measure name=\"Unit Sales\" column=\"unit_sales\" aggregator=\"sum\"\n"
            + "      formatString=\"Standard\" visible=\"false\"/>\n"
            + "  <Measure name=\"Store Sales\" column=\"store_sales\" aggregator=\"sum\"\n"
            + "      formatString=\"#,###.00\"/>\n"
            + "</Cube>",
            null,
            null,
            null,
            null);
        withSchema(context, schema);
        */

        // With grouping sets, mondrian will join fact table to the inline
        // dimension table, them sum to compute the 'all' value. That semi-joins
        // away too many fact table rows, and the 'all' value comes out too low
        // (zero, in fact). It causes a test exception, but is valid mondrian
        // behavior. (Behavior is unspecified if schema does not have
        // referential integrity.)
        if (context.getConfig().enableGroupingSets()) {
            return;
        }
        withSchema(context, TestInlineTableDateModifier::new);
        assertQueryReturns(context.getConnection(),
            "select {[Alternative Promotion].Members} ON COLUMNS\n"
            + "from [" + cubeName + "] ",
            "Axis #0:\n"
            + "{}\n"
            + "Axis #1:\n"
            + "{[Alternative Promotion].[All Alternative Promotions]}\n"
            + "{[Alternative Promotion].[2008-04-29]}\n"
            + "{[Alternative Promotion].[2007-01-20]}\n"
            + "Row #0: 266,773\n"
            + "Row #0: \n"
            + "Row #0: \n");
    }
}
