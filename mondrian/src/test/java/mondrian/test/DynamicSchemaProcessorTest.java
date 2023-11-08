/*
 * This software is subject to the terms of the Eclipse Public License v1.0
 * Agreement, available at the following URL:
 * http://www.eclipse.org/legal/epl-v10.html.
 * You must accept the terms of that agreement to use this software.
 *
 * Copyright (c) 2002-2019 Hitachi Vantara..  All rights reserved.
 */

package mondrian.test;

import mondrian.olap.Util;
import mondrian.rolap.RolapSchemaPool;
import mondrian.spi.DynamicSchemaProcessor;
import org.eclipse.daanse.olap.api.Connection;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.TableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggExcludeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.LevelRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PrivateDimensionRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier.record.RDbMappingSchemaModifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.context.TestContext;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test DynamicSchemaProcessor. Tests availability of properties that DSP's
 * are called, and used to modify the resulting Mondrian schema
 *
 * @author ngoodman
 */
class DynamicSchemaProcessorTest
{

    public static final String FRAGMENT_ONE =
            "<?xml version=\"1.0\"?>\n"
                    + "<Schema name=\"";

    public static final String FRAGMENT_TWO =
            "\">\n"
                    + "<Cube name=\"Sales\">\n"
                    + " <Table name=\"sales_fact_1997\">"
                    + "   <AggExclude name=\"agg_pl_01_sales_fact_1997\" />"
                    + "   <AggExclude name=\"agg_ll_01_sales_fact_1997\" />"
                    + "   <AggExclude name=\"agg_lc_100_sales_fact_1997\" />"
                    + "   <AggExclude name=\"agg_lc_06_sales_fact_1997\" />"
                    + "   <AggExclude name=\"agg_l_04_sales_fact_1997\" />"
                    + "   <AggExclude name=\"agg_l_03_sales_fact_1997\" />"
                    + "   <AggExclude name=\"agg_g_ms_pcat_sales_fact_1997\" />"
                    + "   <AggExclude name=\"agg_c_10_sales_fact_1997\" />"
                    + "</Table>"
                    + " <Dimension name=\"Fake\"><Hierarchy hasAll=\"true\">"
                    + "  <Level name=\"blah\" column=\"store_id\"/>"
                    + " </Hierarchy></Dimension>"
                    + " <Measure name=\"c\" column=\"store_id\" aggregator=\"count\"/>"
                    + "</Cube>\n" + "</Schema>\n";

    public static final String TEMPLATE_SCHEMA =
            FRAGMENT_ONE
                    + "REPLACEME"
                    + FRAGMENT_TWO;
    public static String RETURNTRUESTRING = "true";

    /**
     * Tests to make sure that our base DynamicSchemaProcessor works, with no
     * replacement. Does not test Mondrian is able to connect with the schema
     * definition.
     */
    @Test
    void testDSPBasics() {
        DynamicSchemaProcessor dsp = new BaseDsp();
        Util.PropertyList dummy = new Util.PropertyList();
        String processedSchema = "";
        try {
            processedSchema = dsp.processSchema("", dummy);
        } catch (Exception e) {
            // TODO some other assert failure message
            assertEquals(0, 1);
        }
        assertEquals(TEMPLATE_SCHEMA, processedSchema);
    }

    /**
     * Tests to make sure that our base DynamicSchemaProcessor works, and
     * Mondrian is able to parse and connect to FoodMart with it
     */
    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testFoodmartDsp(TestContext context) {
        RolapSchemaPool.instance().clear();
        MappingSchema schema = context.getDatabaseMappingSchemaProviders().get(0).get();
        context.setDatabaseMappingSchemaProviders(List.of(new BaseDspModifier(schema, "REPLACEME")));
        final Connection monConnection = context.getConnection();
        assertEquals(monConnection.getSchema().getName(), "REPLACEME");
    }

    public static class BaseDspModifier extends RDbMappingSchemaModifier {

        private final String name;

        public BaseDspModifier(MappingSchema mappingSchema, String name) {
            super(mappingSchema);
            this.name = name;
        }
        protected MappingSchema modifyMappingSchema(MappingSchema mappingSchemaOriginal) {
            return SchemaRBuilder.builder()
                .name(name)
                .cubes(List.of(
                    CubeRBuilder.builder()
                        .name("Sales")
                        .fact(new TableR("sales_fact_1997",
                            List.of(
                                AggExcludeRBuilder.builder().name("agg_pl_01_sales_fact_1997").build(),
                                AggExcludeRBuilder.builder().name("agg_ll_01_sales_fact_1997").build(),
                                AggExcludeRBuilder.builder().name("agg_lc_100_sales_fact_1997").build(),
                                AggExcludeRBuilder.builder().name("agg_lc_06_sales_fact_1997").build(),
                                AggExcludeRBuilder.builder().name("agg_l_04_sales_fact_1997").build(),
                                AggExcludeRBuilder.builder().name("agg_l_03_sales_fact_1997").build(),
                                AggExcludeRBuilder.builder().name("agg_g_ms_pcat_sales_fact_1997").build(),
                                AggExcludeRBuilder.builder().name("agg_c_10_sales_fact_1997").build()
                            ),
                            List.of()))
                        .dimensionUsageOrDimensions(List.of(
                            PrivateDimensionRBuilder.builder()
                                .name("Fake")
                                .hierarchies(List.of(
                                    HierarchyRBuilder.builder()
                                        .hasAll(true)
                                        .levels(List.of(
                                            LevelRBuilder.builder()
                                                .name("blah")
                                                .column("store_id")
                                                .build()
                                        ))
                                        .build()
                                ))
                                .build()
                        ))
                        .measures(List.of(
                            MeasureRBuilder.builder()
                                .name("c")
                                .column("store_id")
                                .aggregator("count")
                                .build()
                        ))
                        .build()
                ))
                .build();
        }
    }
    /**
     * Our base, token replacing schema processor.
     *
     * @author ngoodman
     */
    public static class BaseDsp implements DynamicSchemaProcessor {
        // Determines the "cubeName"
        protected String replaceToken = "REPLACEME";

        public BaseDsp() {}

        @Override
		public String processSchema(
                String schemaUrl,
                Util.PropertyList connectInfo)
                throws Exception
        {
            return getSchema();
        }

        public String getSchema() throws Exception {
            return
                    DynamicSchemaProcessorTest.TEMPLATE_SCHEMA.replaceAll(
                            "REPLACEME",
                            this.replaceToken);
        }
    }

    /**
     * Tests to ensure we have access to Connect properies in a DSP
     */
    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testProviderTestDSP(TestContext context) {
        RolapSchemaPool.instance().clear();
        MappingSchema schema = context.getDatabaseMappingSchemaProviders().get(0).get();
        context.setDatabaseMappingSchemaProviders(List.of(new BaseDspModifier(schema, "mondrian")));
        Connection monConnection = context.getConnection();
        assertEquals(monConnection.getSchema().getName(), "mondrian");
    }

    /**
     * DSP that checks that replaces the Schema Name with the name of the
     * Provider property
     *
     * @author ngoodman
     *
     */
    public static class ProviderTestDSP extends BaseDsp {
        @Override
		public String processSchema(
                String schemaUrl,
                Util.PropertyList connectInfo)
                throws Exception
        {
            this.replaceToken = connectInfo.get("Provider");
            return getSchema();
        }
    }

    /**
     * Tests to ensure we have access to Connect properies in a DSP
     */
    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testDBInfoDSP(TestContext context) {
        RolapSchemaPool.instance().clear();
        MappingSchema schema = context.getDatabaseMappingSchemaProviders().get(0).get();
        context.setDatabaseMappingSchemaProviders(List.of(new BaseDspModifier(schema, "FoodmartFoundInCatalogProperty")));

        final Connection monConnection = context.getConnection();
        assertEquals(monConnection.getSchema().getName(), "FoodmartFoundInCatalogProperty");
    }

    /**
     * Checks to make sure our Catalog property contains our FoodMart.xml VFS
     * URL
     *
     * @author ngoodman
     *
     */
    public static class FoodMartCatalogDsp extends BaseDsp {
        @Override
		public String processSchema(
                String schemaUrl,
                Util.PropertyList connectInfo)
                throws Exception
        {
            if (connectInfo.get("Catalog").indexOf("FoodMart.xml") <= 0) {
                this.replaceToken = "NoFoodmartFoundInCatalogProperty";
            } else {
                this.replaceToken = "FoodmartFoundInCatalogProperty";
            }

            return getSchema();
        }
    }

    /**
     * Tests to ensure we have access to Connect properties in a DSP
     */
    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testCheckJdbcPropertyDsp(TestContext context) {
        RolapSchemaPool.instance().clear();
        MappingSchema schema = context.getDatabaseMappingSchemaProviders().get(0).get();
        context.setDatabaseMappingSchemaProviders(List.of(new BaseDspModifier(schema, RETURNTRUESTRING)));

        Connection monConnection = context.getConnection();
        assertEquals(monConnection.getSchema().getName(), RETURNTRUESTRING);
    }

    /**
     * Ensures we have access to the JDBC URL. Note, since Foodmart can run on
     * multiple databases all we check in schema name is the first four
     * characters (JDBC)
     *
     * @author ngoodman
     *
     */
    public static class CheckJdbcPropertyDsp extends BaseDsp {
        public static String RETURNTRUESTRING = "true";
        public static String RETURNFALSESTRING = "false";

        @Override
		public String processSchema(
                String schemaUrl,
                Util.PropertyList connectInfo)
                throws Exception
        {
            String dataSource = null;
            String jdbc = null;

            dataSource = connectInfo.get("DataSource");
            jdbc = connectInfo.get("Jdbc");

            // If we're using a DataSource we might not get a Jdbc= property
            // trivially return true.
            if (dataSource != null && dataSource.length() > 0) {
                this.replaceToken = RETURNTRUESTRING;
                return getSchema();
            }

            // IF we're here, we don't have a DataSource and
            // our JDBC property should have jdbc: in the URL
            if (jdbc == null || !jdbc.startsWith("jdbc")) {
                this.replaceToken = RETURNFALSESTRING;
                return getSchema();
            } else {
                // If we're here, we have a JDBC url
                this.replaceToken = RETURNTRUESTRING;
                return getSchema();
            }
        }
    }

}
