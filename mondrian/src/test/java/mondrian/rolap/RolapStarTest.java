/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2018 Hitachi Vantara..  All rights reserved.
*/

package mondrian.rolap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.daanse.engine.api.Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.context.TestingContext;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import mondrian.olap.Connection;
import mondrian.olap.MondrianDef;
import mondrian.olap.MondrianDef.SQL;
import mondrian.rolap.RolapStar.Column;

/**
 * Unit test for {@link RolapStar}.
 *
 * @author pedrovale
 */
public class RolapStarTest {

    static class RolapStarForTests extends RolapStar {
        public RolapStarForTests(
            final RolapSchema schema,
            final Context context,
            final MondrianDef.Relation fact)
        {
            super(schema, context, fact);
        }

        public MondrianDef.RelationOrJoin cloneRelationForTests(
            MondrianDef.Relation rel,
            String possibleName)
        {
            return cloneRelation(rel, possibleName);
        }
    }

    RolapStar getRolapStar(Connection con, String starName) {
        RolapCube cube =
            (RolapCube) con.getSchema().lookupCube(starName, true);
        return cube.getStar();
    }

    RolapStarForTests getStar(Connection connection, String starName) {
        RolapStar rs =  getRolapStar(connection, starName);

        return new RolapStarForTests(
            rs.getSchema(),
            rs.getContext(),
            rs.getFactTable().getRelation());
    }

    /**
     * Tests that given a {@link MondrianDef.Table}, cloneRelation
     * respects the existing filters.
     */
    @ParameterizedTest
    @ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class)
    public void testCloneRelationWithFilteredTable(TestingContext context) {
      RolapStarForTests rs = getStar(context.createConnection(), "sales");
      MondrianDef.Table original = new MondrianDef.Table();
      original.name = "TestTable";
      original.alias = "Alias";
      original.schema = "Sechema";
      original.filter = new SQL();
      original.filter.dialect = "generic";
      original.filter.cdata = "Alias.clicked = 'true'";

      MondrianDef.Table cloned = (MondrianDef.Table)rs.cloneRelationForTests(
          original,
          "NewAlias");

      assertEquals("NewAlias", cloned.alias);
      assertEquals("TestTable", cloned.name);
      assertNotNull(cloned.filter);
      assertEquals("NewAlias.clicked = 'true'", cloned.filter.cdata);
  }

   //Below there are tests for mondrian.rolap.RolapStar.ColumnComparator
   @Test
   public void testTwoColumnsWithDifferentNamesNotEquals() {
     RolapStar.ColumnComparator colComparator =
         RolapStar.ColumnComparator.instance;
     Column column1 = getColumnMock("Column1", "Table1");
     Column column2 = getColumnMock("Column2", "Table1");
     assertNotSame(column1, column2);
     assertEquals(-1, colComparator.compare(column1, column2));
   }

   @Test
   public void testTwoColumnsWithEqualsNamesButDifferentTablesNotEquals() {
     RolapStar.ColumnComparator colComparator =
         RolapStar.ColumnComparator.instance;
     Column column1 = getColumnMock("Column1", "Table1");
     Column column2 = getColumnMock("Column1", "Table2");
     assertNotSame(column1, column2);
     assertEquals(-1, colComparator.compare(column1, column2));
   }

   @Test
   public void testTwoColumnsEquals() {
     RolapStar.ColumnComparator colComparator =
         RolapStar.ColumnComparator.instance;
     Column column1 = getColumnMock("Column1", "Table1");
     Column column2 = getColumnMock("Column1", "Table1");
     assertNotSame(column1, column2);
     assertEquals(0, colComparator.compare(column1, column2));
   }

   private static Column getColumnMock(
       String columnName,
       String tableName)
   {
     Column colMock = mock(Column.class);
     RolapStar.Table tableMock = mock(RolapStar.Table.class);
     when(colMock.getName()).thenReturn(columnName);
     when(colMock.getTable()).thenReturn(tableMock);
     when(tableMock.getAlias()).thenReturn(tableName);
    return colMock;
   }
}

// End RolapStarTest.java
