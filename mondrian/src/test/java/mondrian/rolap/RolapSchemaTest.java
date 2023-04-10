/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (c) 2015-2017 Hitachi Vantara..  All rights reserved.
*/
package mondrian.rolap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.List;

import org.eclipse.daanse.olap.api.access.Access;
import org.eclipse.daanse.olap.api.access.RollupPolicy;
import org.eclipse.daanse.olap.api.model.Dimension;
import org.eclipse.daanse.olap.api.model.Hierarchy;
import org.eclipse.daanse.olap.api.model.Level;
import org.eclipse.daanse.olap.api.model.Member;
import org.eclipse.daanse.olap.api.model.OlapElement;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.CubeGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.HierarchyGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MemberGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Relation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.AccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MemberGrantAccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.CubeGrantImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.DimensionGrantImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.HierarchyGrantImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.RoleImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.RoleUsageImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.SchemaGrantImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.TableImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.UnionImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.MemberGrantR;
import org.eigenbase.xom.DOMWrapper;
import org.eigenbase.xom.Parser;
import org.eigenbase.xom.XOMException;
import org.eigenbase.xom.XOMUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opencube.junit5.SchemaUtil;

import mondrian.olap.Category;
import mondrian.olap.MondrianException;
import mondrian.olap.MondrianServer;
import mondrian.olap.SchemaReader;
import mondrian.resource.MondrianResource;
import mondrian.rolap.RolapSchema.RolapStarRegistry;
import mondrian.rolap.agg.AggregationManager;
import mondrian.rolap.agg.SegmentCacheManager;
import mondrian.rolap.util.RelationUtil;
import mondrian.test.PropertySaver5;
import mondrian.util.ByteString;

/**
 * @author Andrey Khayrutdinov
 */
class RolapSchemaTest {
  private RolapSchema schemaSpy;
  private static RolapStar rlStarMock = mock(RolapStar.class);

    private PropertySaver5 propSaver;
    @BeforeEach
    public void beforeEach() {
        propSaver = new PropertySaver5();
        schemaSpy = spy(createSchema());
    }

    @AfterEach
    public void afterEach() {
        propSaver.reset();
    }

    private RolapSchema createSchema() {
        SchemaKey key = new SchemaKey(
            mock(SchemaContentKey.class), mock(ConnectionKey.class));

        ByteString md5 = new ByteString("test schema".getBytes());
        //noinspection deprecation
        //mock rolap connection to eliminate calls for cache loading
        MondrianServer mServerMock = mock(MondrianServer.class);
        RolapConnection rolapConnectionMock = mock(RolapConnection.class);
        AggregationManager aggManagerMock = mock(AggregationManager.class);
        SegmentCacheManager scManagerMock = mock(SegmentCacheManager.class);
        when(rolapConnectionMock.getServer()).thenReturn(mServerMock);
        when(mServerMock.getAggregationManager()).thenReturn(aggManagerMock);
        when(aggManagerMock.getCacheMgr(rolapConnectionMock)).thenReturn(scManagerMock);
        return new RolapSchema(key, md5, rolapConnectionMock);
    }

    private SchemaReader mockSchemaReader(int category, OlapElement element) {
        SchemaReader reader = mock(SchemaReader.class);
        when(reader.withLocus()).thenReturn(reader);
        when(reader.lookupCompound(
            any(OlapElement.class), anyList(),
            anyBoolean(), eq(category)))
            .thenReturn(element);
        return reader;
    }

    private RolapCube mockCube(RolapSchema schema) {
        RolapCube cube = mock(RolapCube.class);
        when(cube.getSchema()).thenReturn(schema);
        return cube;
    }

    @Test
    void testCreateUnionRole_ThrowsException_WhenSchemaGrantsExist() {
        RoleImpl role = new RoleImpl();
        role.setSchemaGrant(
            List.of(new SchemaGrantImpl()));
        role.setUnion(new UnionImpl());

        try {
            createSchema().createUnionRole(role);
        } catch (MondrianException ex) {
            assertMondrianException(
                MondrianResource.instance().RoleUnionGrants.ex(), ex);
            return;
        }
        fail("Should fail if union and schema grants exist simultaneously");
    }

    @Test
    void testCreateUnionRole_ThrowsException_WhenRoleNameIsUnknown() {
        final String roleName = "non-existing role name";
        RoleUsageImpl usage = new RoleUsageImpl();
        usage.setRoleName(roleName);

        RoleImpl role = new RoleImpl();
        UnionImpl unionImpl = new UnionImpl();
        unionImpl.setRoleUsage(List.of(usage));
        role.setUnion(unionImpl);

        try {
            createSchema().createUnionRole(role);
        } catch (MondrianException ex) {
            assertMondrianException(
                MondrianResource.instance().UnknownRole.ex(roleName), ex);
            return;
        }
        fail("Should fail if union and schema grants exist simultaneously");
    }


    @Test
    void testHandleSchemaGrant() {
        RolapSchema schema = createSchema();
        schema = spy(schema);
        doNothing().when(schema)
            .handleCubeGrant(
                any(mondrian.olap.RoleImpl.class), any(CubeGrant.class));

        SchemaGrantImpl grant = new SchemaGrantImpl();
        grant.setAccess(AccessEnum.CUSTOM);
        grant.setCubeGrant(List.of(new CubeGrantImpl(), new CubeGrantImpl()));

        mondrian.olap.RoleImpl role = new mondrian.olap.RoleImpl();

        schema.handleSchemaGrant(role, grant);
        assertEquals(Access.CUSTOM, role.getAccess(schema));
        verify(schema, times(2))
            .handleCubeGrant(eq(role), any(CubeGrant.class));
    }


    @Test
    void testHandleCubeGrant_ThrowsException_WhenCubeIsUnknown() {
        RolapSchema schema = createSchema();
        schema = spy(schema);
        doReturn(null).when(schema).lookupCube(anyString());

        CubeGrantImpl grant = new CubeGrantImpl();
        grant.setCube("cube");

        try {
            schema.handleCubeGrant(new mondrian.olap.RoleImpl(), grant);
        } catch (MondrianException e) {
            String message = e.getMessage();
            assertTrue(message.contains(grant.cube()), message);
            return;
        }
        fail("Should fail if cube is unknown");
    }

    @Test
    void testHandleCubeGrant_GrantsCubeDimensionsAndHierarchies() {
        RolapSchema schema = createSchema();
        schema = spy(schema);
        doNothing().when(schema)
            .handleHierarchyGrant(
                any(mondrian.olap.RoleImpl.class),
                any(RolapCube.class),
                any(SchemaReader.class),
                any(HierarchyGrant.class));

        final Dimension dimension = mock(Dimension.class);
        SchemaReader reader = mockSchemaReader(mondrian.olap.Category.Dimension, dimension);

        RolapCube cube = mockCube(schema);
        when(cube.getSchemaReader(any())).thenReturn(reader);
        doReturn(cube).when(schema).lookupCube("cube");

        DimensionGrantImpl dimensionGrant =
            new DimensionGrantImpl();
        dimensionGrant.setDimension("dimension");
        dimensionGrant.setAccess(AccessEnum.NONE);

        CubeGrantImpl grant = new CubeGrantImpl();
        grant.setCube("cube");
        grant.setAccess(Access.CUSTOM.toString());
        grant.setDimensionGrant(List.of(dimensionGrant));
        grant.setHierarchyGrant(List.of(new HierarchyGrantImpl()));

        mondrian.olap.RoleImpl role = new mondrian.olap.RoleImpl();

        schema.handleCubeGrant(role, grant);

        assertEquals(Access.CUSTOM, role.getAccess(cube));
        assertEquals(Access.NONE, role.getAccess(dimension));
        verify(schema, times(1))
            .handleHierarchyGrant(
                eq(role),
                eq(cube),
                eq(reader),
                any(HierarchyGrant.class));
    }

    @Test
    void testHandleHierarchyGrant_ValidMembers() {
        doTestHandleHierarchyGrant(Access.CUSTOM, Access.ALL);
    }

    @Test
    void testHandleHierarchyGrant_NoValidMembers() {
        doTestHandleHierarchyGrant(Access.NONE, null);
    }

    @Test
    void testEmptyRolapStarRegistryCreatedForTheNewSchema()
        throws Exception {
      RolapSchema schema = createSchema();
      RolapStarRegistry rolapStarRegistry = schema.getRolapStarRegistry();
      assertNotNull(rolapStarRegistry);
      assertTrue(rolapStarRegistry.getStars().isEmpty());
    }

    @Test
    void testGetOrCreateStar_StarCreatedAndUsed()
        throws Exception {
      //Create the test fact
      Relation fact =
          SchemaUtil.parse(getFactTableWithSQLFilter(), TableImpl.class);
      List<String> rolapStarKey = RolapUtil.makeRolapStarKey(fact);
      //Expected result star
      RolapStar expectedStar = rlStarMock;
      RolapStarRegistry rolapStarRegistry =
          getStarRegistryLinkedToRolapSchemaSpy(schemaSpy, fact);


      //Test that a new rolap star has created and put to the registry
      RolapStar actualStar = rolapStarRegistry.getOrCreateStar(fact);
      assertSame(expectedStar, actualStar);
      assertEquals(1, rolapStarRegistry.getStars().size());
      assertEquals(expectedStar, rolapStarRegistry.getStar(rolapStarKey));
      verify(schemaSpy, times(1)).makeRolapStar(fact);
      //test that no new rolap star has created,
      //but extracted already existing one from the registry
      RolapStar actualStar2 = rolapStarRegistry.getOrCreateStar(fact);
      verify(schemaSpy, times(1)).makeRolapStar(fact);
      assertSame(expectedStar, actualStar2);
      assertEquals(1, rolapStarRegistry.getStars().size());
      assertEquals(expectedStar, rolapStarRegistry.getStar(rolapStarKey));
    }

    @Test
    void testGetStarFromRegistryByStarKey() throws Exception {
      //Create the test fact
      Relation fact =
          SchemaUtil.parse(getFactTableWithSQLFilter(), TableImpl.class);
      List<String> rolapStarKey = RolapUtil.makeRolapStarKey(fact);
      //Expected result star
      RolapStarRegistry rolapStarRegistry =
          getStarRegistryLinkedToRolapSchemaSpy(schemaSpy, fact);
      //Put rolap star to the registry
      rolapStarRegistry.getOrCreateStar(fact);

      RolapStar actualStar = schemaSpy.getStar(rolapStarKey);
      assertSame(rlStarMock, actualStar);
    }

    @Test
    void testGetStarFromRegistryByFactTableName() throws Exception {
      //Create the test fact
      Relation fact =
          SchemaUtil.parse(getFactTable(), TableImpl.class);
      //Expected result star
      RolapStarRegistry rolapStarRegistry =
          getStarRegistryLinkedToRolapSchemaSpy(schemaSpy, fact);
      //Put rolap star to the registry
      rolapStarRegistry.getOrCreateStar(fact);

      RolapStar actualStar = schemaSpy.getStar(RelationUtil.getAlias(fact));
      assertSame(rlStarMock, actualStar);
    }

    private static RolapStarRegistry getStarRegistryLinkedToRolapSchemaSpy(
        RolapSchema schemaSpy, Relation fact) throws Exception
    {
      //the rolap star registry is linked to the origin rolap schema,
      //not to the schemaSpy
      RolapStarRegistry rolapStarRegistry = schemaSpy.getRolapStarRegistry();
      //the star mock
      doReturn(rlStarMock).when(schemaSpy).makeRolapStar(fact);
      //Set the schema spy to be linked with the rolap star registry
      assertTrue(
              replaceRolapSchemaLinkedToStarRegistry(
              rolapStarRegistry,
              schemaSpy),
              "For testing purpose object this$0 in the inner class "
                      + "should be replaced to the rolap schema spy "
                      + "but this not happend");
      verify(schemaSpy, times(0)).makeRolapStar(fact);
      return rolapStarRegistry;
    }

     private static boolean replaceRolapSchemaLinkedToStarRegistry(
         RolapStarRegistry innerClass,
         RolapSchema sSpy) throws Exception
     {
       Field field = innerClass.getClass().getDeclaredField("this$0");
       if (field != null) {
         field.setAccessible(true);
         field.set(innerClass, sSpy);
         RolapSchema outerMocked = (RolapSchema) field.get(innerClass);
         return outerMocked == sSpy;
       }
       return false;
      }

    private static String getFactTableWithSQLFilter() {
      String fact =
          "<Table name=\"sales_fact_1997\" alias=\"TableAlias\">\n"
          + " <SQL dialect=\"mysql\">\n"
          + "     `TableAlias`.`promotion_id` = 112\n"
          + " </SQL>\n"
          + "</Table>";
      return fact;
    }

    private static String getFactTable() {
      String fact =
          "<Table name=\"sales_fact_1997\" alias=\"TableAlias\"/>";
      return fact;
    }

    private static DOMWrapper wrapStrSources(String resStr)
        throws XOMException {
      final Parser xmlParser = XOMUtil.createDefaultParser();
      final DOMWrapper def = xmlParser.parse(resStr);
      return def;
    }

    private void doTestHandleHierarchyGrant(
        Access expectedHierarchyAccess,
        Access expectedMemberAccess)
    {
        propSaver.set(propSaver.properties.IgnoreInvalidMembers, true);

        RolapSchema schema = createSchema();
        RolapCube cube = mockCube(schema);
        mondrian.olap.RoleImpl role = new mondrian.olap.RoleImpl();

        MemberGrant memberGrant = new MemberGrantR("member", MemberGrantAccessEnum.ALL);

        HierarchyGrantImpl grant = new HierarchyGrantImpl();
        grant.setAccess(AccessEnum.CUSTOM);
        grant.setRollupPolicy(RollupPolicy.FULL.toString());
        grant.setHierarchy("hierarchy");
        grant.setMemberGrant(List.of(memberGrant));

        Level level = mock(Level.class);
        Hierarchy hierarchy = mock(Hierarchy.class);
        when(hierarchy.getLevels()).thenReturn(new Level[]{level});
        when(level.getHierarchy()).thenReturn(hierarchy);

        Dimension dimension = mock(Dimension.class);
        when(hierarchy.getDimension()).thenReturn(dimension);

        SchemaReader reader = mockSchemaReader(Category.Hierarchy, hierarchy);

        Member member = mock(Member.class);
        when(member.getHierarchy()).thenReturn(hierarchy);
        when(member.getLevel()).thenReturn(level);

        if (expectedMemberAccess != null) {
            when(reader.getMemberByUniqueName(
                anyList(), anyBoolean())).thenReturn(member);
        }

        schema.handleHierarchyGrant(role, cube, reader, grant);
        assertEquals(expectedHierarchyAccess, role.getAccess(hierarchy));
        if (expectedMemberAccess != null) {
            assertEquals(expectedMemberAccess, role.getAccess(member));
        }
    }

    private void assertMondrianException(
        MondrianException expected,
        MondrianException actual)
    {
        assertEquals(expected.getMessage(), actual.getMessage());
    }
}
