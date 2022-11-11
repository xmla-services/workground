/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (c) 2015-2017 Hitachi Vantara..  All rights reserved.
*/
package mondrian.rolap;

import mondrian.olap.MondrianDef;
import org.eclipse.daanse.sql.dialect.api.Datatype;
import mondrian.test.PropertySaver5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RolapDimensionTest {

  private RolapSchema schema;
  private RolapCube cube;
  private MondrianDef.Dimension xmlDimension;
  private MondrianDef.CubeDimension xmlCubeDimension;
  private MondrianDef.Hierarchy hierarchy;

  private PropertySaver5 propSaver;
  @BeforeEach
  public void beforeEach() {
    propSaver = new PropertySaver5();
    schema = Mockito.mock(RolapSchema.class);
    cube = Mockito.mock(RolapCube.class);
    MondrianDef.Relation fact = Mockito.mock(MondrianDef.Relation.class);

    Mockito.when(cube.getSchema()).thenReturn(schema);
    Mockito.when(cube.getFact()).thenReturn(fact);

    xmlDimension = new MondrianDef.Dimension();
    hierarchy = new MondrianDef.Hierarchy();
    MondrianDef.Level level = new MondrianDef.Level();
    xmlCubeDimension = new MondrianDef.Dimension();

    xmlDimension.name = "dimensionName";
    xmlDimension.visible = true;
    xmlDimension.highCardinality = true;
    xmlDimension.hierarchies = new MondrianDef.Hierarchy[] {hierarchy};


    hierarchy.visible = true;
    hierarchy.hasAll = false;
    hierarchy.levels = new MondrianDef.Level[]{level};

    level.visible = true;
    level.properties = new MondrianDef.Property[0];
    level.uniqueMembers = true;
    level.type = Datatype.String.name();
    level.hideMemberIf = "Never";
    level.levelType = "Regular";
  }

  @AfterEach
  public void afterEach() {
    propSaver.reset();
  }

  @Test
  public void testHierarchyRelation() {
    MondrianDef.Relation hierarchyTable = Mockito
            .mock(MondrianDef.Relation.class);
    hierarchy.relation = hierarchyTable;

    new RolapDimension(schema, cube, xmlDimension, xmlCubeDimension);
    assertNotNull(hierarchy);
    assertEquals(hierarchyTable, hierarchy.relation);
  }

  /**
   * Check that hierarchy.relation is not set to cube.fact
   */
  @Test
  public void testHierarchyRelationNotSet() {
    new RolapDimension(schema, cube, xmlDimension, xmlCubeDimension);

    assertNotNull(hierarchy);
    assertNull(hierarchy.relation);
  }

}

// End RolapDimensionTest.java
