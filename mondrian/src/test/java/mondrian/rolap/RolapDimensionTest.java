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
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.CubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Relation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.HideMemberIfEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.HierarchyImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.LevelImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.PrivateDimensionImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import mondrian.test.PropertySaver5;

class RolapDimensionTest {

  private RolapSchema schema;
  private RolapCube cube;
  private PrivateDimensionImpl xmlDimension;
  private CubeDimension xmlCubeDimension;
  private HierarchyImpl hierarchy;

  private PropertySaver5 propSaver;
  @BeforeEach
  public void beforeEach() {
    propSaver = new PropertySaver5();
    schema = Mockito.mock(RolapSchema.class);
    cube = Mockito.mock(RolapCube.class);
    Relation fact = Mockito.mock(Relation.class);

    Mockito.when(cube.getSchema()).thenReturn(schema);
    Mockito.when(cube.getFact()).thenReturn(fact);

    xmlDimension = new PrivateDimensionImpl();
    hierarchy = new HierarchyImpl();
    LevelImpl level = new LevelImpl();
    xmlCubeDimension = new PrivateDimensionImpl();

    xmlDimension.setName("dimensionName");
    xmlDimension.setVisible(true);
    xmlDimension.setHighCardinality(true);
    xmlDimension.setHierarchies(List.of(hierarchy));


    hierarchy.setVisible(true);
    hierarchy.setHasAll(false);
    hierarchy.setLevel(List.of(level));

    level.setVisible(true);
    level.setProperties(List.of());
    level.setUniqueMembers(true);
    level.setType(TypeEnum.STRING);
    level.setHideMemberIf(HideMemberIfEnum.NEVER);
    level.setLevelType(LevelTypeEnum.REGULAR);
  }

  @AfterEach
  public void afterEach() {
    propSaver.reset();
  }

  @Disabled //disabled for CI build
  @Test
  void testHierarchyRelation() {
    Relation hierarchyTable = Mockito
            .mock(Relation.class);
    hierarchy.setRelation(hierarchyTable);

    new RolapDimension(schema, cube, xmlDimension, xmlCubeDimension);
    assertNotNull(hierarchy);
    assertEquals(hierarchyTable, hierarchy.relation());
  }

  /**
   * Check that hierarchy.relation is not set to cube.fact
   */
  @Disabled //disabled for CI build
  @Test
  void testHierarchyRelationNotSet() {
    new RolapDimension(schema, cube, xmlDimension, xmlCubeDimension);

    assertNotNull(hierarchy);
    assertNull(hierarchy.relation());
  }

}
