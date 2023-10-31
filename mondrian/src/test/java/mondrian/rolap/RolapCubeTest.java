/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2001-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
*/
package mondrian.rolap;

import mondrian.calc.impl.UnaryTupleList;
import mondrian.olap.MondrianProperties;
import mondrian.olap.Util;
import mondrian.test.PropertySaver5;
import org.eclipse.daanse.olap.api.Connection;
import org.eclipse.daanse.olap.api.SchemaReader;
import org.eclipse.daanse.olap.api.Segment;
import org.eclipse.daanse.olap.api.element.Cube;
import org.eclipse.daanse.olap.api.element.Dimension;
import org.eclipse.daanse.olap.api.element.Member;
import org.eclipse.daanse.olap.calc.api.todo.TupleList;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.CalculatedMemberImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.context.TestContextWrapper;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.opencube.junit5.TestUtil.cubeByName;
import static org.opencube.junit5.TestUtil.getDimensionWithName;
import static org.opencube.junit5.TestUtil.withRole;
import static org.opencube.junit5.TestUtil.withSchema;

/**
 * Unit test for {@link RolapCube}.
 *
 * @author mkambol
 * @since 25 January, 2007
 */
class RolapCubeTest {

    private PropertySaver5 propSaver;

    @BeforeEach
    public void beforeEach() {
        propSaver = new PropertySaver5();
        propSaver.set(
                MondrianProperties.instance().SsasCompatibleNaming, false);
    }

    @AfterEach
    public void afterEach() {
        propSaver.reset();
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testProcessFormatStringAttributeToIgnoreNullFormatString(TestContextWrapper context) {
        RolapCube cube =
            (RolapCube) context.createConnection().getSchema().lookupCube("Sales", false);
        StringBuilder builder = new StringBuilder();
        cube.processFormatStringAttribute(
            new CalculatedMemberImpl(), builder);
        assertEquals(0, builder.length());
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testProcessFormatStringAttribute(TestContextWrapper context) {
        RolapCube cube =
            (RolapCube) context.createConnection().getSchema().lookupCube("Sales", false);
        StringBuilder builder = new StringBuilder();
        CalculatedMemberImpl xmlCalcMember =
            new CalculatedMemberImpl();
        String format = "FORMAT";
        xmlCalcMember.setFormatString(format);
        cube.processFormatStringAttribute(xmlCalcMember, builder);
        assertEquals(
            "," + Util.NL + "FORMAT_STRING = \"" + format + "\"",
            builder.toString());
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testGetCalculatedMembersWithNoRole(TestContextWrapper context) {
        String[] expectedCalculatedMembers = {
            "[Measures].[Profit]",
            "[Measures].[Average Warehouse Sale]",
            "[Measures].[Profit Growth]",
            "[Measures].[Profit Per Unit Shipped]"
        };
        Connection connection = context.createConnection();
        try {
            Cube warehouseAndSalesCube =
                cubeByName(connection, "Warehouse and Sales");
            SchemaReader schemaReader =
                warehouseAndSalesCube.getSchemaReader(null);

            List<Member> calculatedMembers =
                schemaReader.getCalculatedMembers();
            assertEquals(
                expectedCalculatedMembers.length,
                calculatedMembers.size());
            assertCalculatedMemberExists(
                expectedCalculatedMembers,
                calculatedMembers);
        } finally {
            connection.close();
        }
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testGetCalculatedMembersForCaliforniaManager(TestContextWrapper context) {
    	RolapSchemaPool.instance().clear();
        String[] expectedCalculatedMembers = new String[] {
            "[Measures].[Profit]", "[Measures].[Profit last Period]",
            "[Measures].[Profit Growth]"
        };
        withRole(context, "California manager");
        Connection connection = context.getContext().getConnection();

        try {
            Cube salesCube = cubeByName(connection, "Sales");
            SchemaReader schemaReader = salesCube
                .getSchemaReader(connection.getRole());

            List<Member> calculatedMembers =
                schemaReader.getCalculatedMembers();
            assertEquals(
                expectedCalculatedMembers.length,
                calculatedMembers.size());
            assertCalculatedMemberExists(
                expectedCalculatedMembers,
                calculatedMembers);
        } finally {
            connection.close();
        }
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testGetCalculatedMembersReturnsOnlyAccessibleMembers(TestContextWrapper context) {
        String[] expectedCalculatedMembers = {
            "[Measures].[Profit]",
            "[Measures].[Profit last Period]",
            "[Measures].[Profit Growth]",
            "[Product].[~Missing]"
        };


        createTestContextWithAdditionalMembersAndARole(context);

        Connection connection = context.createConnection();

        try {
            Cube salesCube = cubeByName(connection, "Sales");
            SchemaReader schemaReader =
                salesCube.getSchemaReader(connection.getRole());
            List<Member> calculatedMembers =
                schemaReader.getCalculatedMembers();
            assertEquals(
                expectedCalculatedMembers.length,
                calculatedMembers.size());
            assertCalculatedMemberExists(
                expectedCalculatedMembers,
                calculatedMembers);
        } finally {
            connection.close();
        }
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testGetCalculatedMembersReturnsOnlyAccessibleMembersForHierarchy(TestContextWrapper context)
    {
        String[] expectedCalculatedMembersFromProduct = {
            "[Product].[~Missing]"
        };
        //TestContext testContext =
        //    createTestContextWithAdditionalMembersAndARole();
        createTestContextWithAdditionalMembersAndARole(context);
        Connection connection = context.createConnection();

        try {
            Cube salesCube = cubeByName(connection, "Sales");
            SchemaReader schemaReader =
                salesCube.getSchemaReader(connection.getRole());

            // Product.~Missing accessible
            List<Member> calculatedMembers =
                schemaReader.getCalculatedMembers(
                    getDimensionWithName(
                        "Product",
                        salesCube.getDimensions()).getHierarchy());

            assertEquals(
                expectedCalculatedMembersFromProduct.length,
                calculatedMembers.size());

            assertCalculatedMemberExists(
                expectedCalculatedMembersFromProduct,
                calculatedMembers);

            // Gender.~Missing not accessible
            calculatedMembers =
                schemaReader.getCalculatedMembers(
                    getDimensionWithName(
                        "Gender",
                        salesCube.getDimensions()).getHierarchy());
            assertEquals(0, calculatedMembers.size());
        } finally {
            connection.close();
        }
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testGetCalculatedMembersReturnsOnlyAccessibleMembersForLevel(TestContextWrapper context) {
        String[] expectedCalculatedMembersFromProduct = new String[]{
            "[Product].[~Missing]"
        };


        createTestContextWithAdditionalMembersAndARole(context);
        Connection connection = context.createConnection();

        try {
            Cube salesCube = cubeByName(connection, "Sales");
            SchemaReader schemaReader =
                salesCube.getSchemaReader(connection.getRole());

            // Product.~Missing accessible
            List<Member> calculatedMembers =
                schemaReader.getCalculatedMembers(
                    getDimensionWithName(
                        "Product",
                        salesCube.getDimensions())
                    .getHierarchy().getLevels()[0]);

            assertEquals(
                expectedCalculatedMembersFromProduct.length,
                calculatedMembers.size());
            assertCalculatedMemberExists(
                expectedCalculatedMembersFromProduct,
                calculatedMembers);

            // Gender.~Missing not accessible
            calculatedMembers =
                schemaReader.getCalculatedMembers(
                    getDimensionWithName(
                        "Gender",
                        salesCube.getDimensions())
                    .getHierarchy().getLevels()[0]);
            assertEquals(0, calculatedMembers.size());
        } finally {
            connection.close();
        }
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testNonJoiningDimensions(TestContextWrapper context) {

        Connection connection = context.createConnection();

        try {
            RolapCube salesCube = (RolapCube) cubeByName(connection, "Sales");

            RolapCube warehouseAndSalesCube =
                (RolapCube) cubeByName(connection, "Warehouse and Sales");
            SchemaReader readerWarehouseAndSales =
                warehouseAndSalesCube.getSchemaReader().withLocus();

            List<Member> members = new ArrayList<>();
            List<Member> warehouseMembers =
                warehouseMembersCanadaMexicoUsa(readerWarehouseAndSales);
            Dimension warehouseDim = warehouseMembers.get(0).getDimension();
            members.addAll(warehouseMembers);

            List<Member> storeMembers =
                storeMembersCAAndOR(readerWarehouseAndSales).slice(0);
            Dimension storeDim = storeMembers.get(0).getDimension();
            members.addAll(storeMembers);

            Set<Dimension> nonJoiningDims =
                salesCube.nonJoiningDimensions(members.toArray(new Member[0]));
            assertFalse(nonJoiningDims.contains(storeDim));
            assertTrue(nonJoiningDims.contains(warehouseDim));
        } finally {
            connection.close();
        }
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testRolapCubeDimensionEquality(TestContextWrapper context) {


        Connection connection1 = context.createConnection();
        //withSchema(context, null);
        Connection connection2 = context.createConnection();

        try {
            RolapCube salesCube1 = (RolapCube) cubeByName(connection1, "Sales");
            SchemaReader readerSales1 =
                salesCube1.getSchemaReader().withLocus();
            List<Member> storeMembersSales =
                storeMembersCAAndOR(readerSales1).slice(0);
            Dimension storeDim1 = storeMembersSales.get(0).getDimension();
            assertEquals(storeDim1, storeDim1);

            RolapCube salesCube2 = (RolapCube) cubeByName(connection2, "Sales");
            SchemaReader readerSales2 =
                salesCube2.getSchemaReader().withLocus();
            List<Member> storeMembersSales2 =
                storeMembersCAAndOR(readerSales2).slice(0);
            Dimension storeDim2 = storeMembersSales2.get(0).getDimension();
            assertEquals(storeDim1, storeDim2);


            RolapCube warehouseAndSalesCube =
                (RolapCube) cubeByName(connection1, "Warehouse and Sales");
            SchemaReader readerWarehouseAndSales =
                warehouseAndSalesCube.getSchemaReader().withLocus();
            List<Member> storeMembersWarehouseAndSales =
                storeMembersCAAndOR(readerWarehouseAndSales).slice(0);
            Dimension storeDim3 =
                storeMembersWarehouseAndSales.get(0).getDimension();
            assertNotEquals(storeDim1, storeDim3);

            List<Member> warehouseMembers =
                warehouseMembersCanadaMexicoUsa(readerWarehouseAndSales);
            Dimension warehouseDim = warehouseMembers.get(0).getDimension();
            assertNotEquals(storeDim3, warehouseDim);
        } finally {
            connection1.close();
            connection2.close();
        }
    }

    void createTestContextWithAdditionalMembersAndARole(TestContextWrapper context) {
        /*
        String nonAccessibleMember =
            "  <CalculatedMember name=\"~Missing\" dimension=\"Gender\">\n"
            + "    <Formula>100</Formula>\n"
            + "  </CalculatedMember>\n";
        String accessibleMember =
            "  <CalculatedMember name=\"~Missing\" dimension=\"Product\">\n"
            + "    <Formula>100</Formula>\n"
            + "  </CalculatedMember>\n";
        ((BaseTestContext)context).update(SchemaUpdater.createSubstitutingCube(
            "Sales",
            null,
            nonAccessibleMember
            + accessibleMember));
         */
    	withSchema(context.getContext(), SchemaModifiers.RolapCubeTestModifier1::new);
        withRole(context, "California manager");
    }

    private void assertCalculatedMemberExists(
        String[] expectedCalculatedMembers,
        List<Member> calculatedMembers)
    {
        List expectedCalculatedMemberNames =
            Arrays.asList(expectedCalculatedMembers);
        for (Member calculatedMember : calculatedMembers) {
            String calculatedMemberName = calculatedMember.getUniqueName();
            assertTrue(expectedCalculatedMemberNames.contains(calculatedMemberName),
                    "Calculated member name not found: " + calculatedMemberName);
        }
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testBasedCubesForVirtualCube(TestContextWrapper context) {
      Connection connection = context.createConnection();
      RolapCube cubeSales =
          (RolapCube) connection.getSchema().lookupCube("Sales", false);
      RolapCube cubeWarehouse =
          (RolapCube) connection.getSchema().lookupCube(
              "Warehouse", false);
      RolapCube cube =
          (RolapCube) connection.getSchema().lookupCube(
              "Warehouse and Sales", false);
      assertNotNull(cube);
      assertNotNull(cubeSales);
      assertNotNull(cubeWarehouse);
      assertEquals(true, cube.isVirtual());
      List<RolapCube> baseCubes = cube.getBaseCubes();
      assertNotNull(baseCubes);
      assertEquals(2, baseCubes.size());
      assertSame(cubeSales, baseCubes.get(0));
      assertEquals(cubeWarehouse, baseCubes.get(1));
    }

    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    void testBasedCubesForNotVirtualCubeIsThisCube(TestContextWrapper context) {
      RolapCube cubeSales =
          (RolapCube) context.createConnection().getSchema().lookupCube("Sales", false);
      assertNotNull(cubeSales);
      assertEquals(false, cubeSales.isVirtual());
      List<RolapCube> baseCubes = cubeSales.getBaseCubes();
      assertNotNull(baseCubes);
      assertEquals(1, baseCubes.size());
      assertSame(cubeSales, baseCubes.get(0));
    }

    private List<Member> warehouseMembersCanadaMexicoUsa(SchemaReader reader)
    {
        return Arrays.asList(
                member(Segment.toList(
                        "Warehouse", "All Warehouses", "Canada"), reader),
                member(Segment.toList(
                        "Warehouse", "All Warehouses", "Mexico"), reader),
                member(Segment.toList(
                        "Warehouse", "All Warehouses", "USA"), reader));
    }

    private Member member(
            List<Segment> segmentList,
            SchemaReader salesCubeSchemaReader)
    {
        return salesCubeSchemaReader.getMemberByUniqueName(segmentList, true);
    }

    private TupleList storeMembersCAAndOR(
            SchemaReader salesCubeSchemaReader)
    {
        return new UnaryTupleList(Arrays.asList(
                member(
                        Segment.toList(
                                "Store", "All Stores", "USA", "CA", "Alameda"),
                        salesCubeSchemaReader),
                member(
                        Segment.toList(
                                "Store", "All Stores", "USA", "CA", "Alameda", "HQ"),
                        salesCubeSchemaReader),
                member(
                        Segment.toList(
                                "Store", "All Stores", "USA", "CA", "Beverly Hills"),
                        salesCubeSchemaReader),
                member(
                        Segment.toList(
                                "Store", "All Stores", "USA", "CA", "Beverly Hills",
                                "Store 6"),
                        salesCubeSchemaReader),
                member(
                        Segment.toList(
                                "Store", "All Stores", "USA", "CA", "Los Angeles"),
                        salesCubeSchemaReader),
                member(
                        Segment.toList(
                                "Store", "All Stores", "USA", "OR", "Portland"),
                        salesCubeSchemaReader),
                member(
                        Segment.toList(
                                "Store", "All Stores", "USA", "OR", "Portland", "Store 11"),
                        salesCubeSchemaReader),
                member(
                        Segment.toList(
                                "Store", "All Stores", "USA", "OR", "Salem"),
                        salesCubeSchemaReader),
                member(
                        Segment.toList(
                                "Store", "All Stores", "USA", "OR", "Salem", "Store 13"),
                        salesCubeSchemaReader)));
    }
}
