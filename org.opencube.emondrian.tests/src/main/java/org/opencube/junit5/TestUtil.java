package org.opencube.junit5;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.olap4j.CellSet;
import org.olap4j.CellSetAxis;
import org.olap4j.OlapConnection;
import org.olap4j.OlapStatement;
import org.olap4j.impl.CoordinateIterator;

import mondrian.calc.TupleList;
import mondrian.calc.impl.UnaryTupleList;
import mondrian.olap.Cell;
import mondrian.olap.Connection;
import mondrian.olap.Cube;
import mondrian.olap.Id;
import mondrian.olap.Member;
import mondrian.olap.MondrianProperties;
import mondrian.olap.Query;
import mondrian.olap.Result;
import mondrian.olap.SchemaReader;
import mondrian.olap.Util;
import mondrian.olap.fun.FunUtil;
import mondrian.server.Execution;
import mondrian.server.Statement;
import mondrian.spi.Dialect;
import mondrian.spi.DialectManager;

public class TestUtil {
	
	
	  /**
	   * Executes the expression in the context of the cube indicated by
	   * <code>cubeName</code>, and returns the result as a Cell.
	   *
	   * @param expression The expression to evaluate
	   * @return Cell which is the result of the expression
	   */
	  public static Cell executeExprRaw(Connection connection, String cubeName , String expression ) {
	    final String queryString = generateExpression( cubeName,expression );
	    Result result = executeQuery( connection,queryString );
	    return result.getCell( new int[] { 0 } );
	  }

	  public static String generateExpression(String cubeName , String expression ) {
	    if ( cubeName.indexOf( ' ' ) >= 0 ) {
	      cubeName = Util.quoteMdxIdentifier( cubeName );
	    }
	    return
	      "with member [Measures].[Foo] as "
	        + Util.singleQuoteString( expression )
	        + " select {[Measures].[Foo]} on columns from " + cubeName;
	  }
	  
	   public static TupleList productMembersPotScrubbersPotsAndPans(
		        SchemaReader salesCubeSchemaReader)
		    {
		        return new UnaryTupleList(Arrays.asList(
		            member(
		                Id.Segment.toList(
		                    "Product", "All Products", "Non-Consumable", "Household",
		                    "Kitchen Products", "Pot Scrubbers", "Cormorant"),
		                salesCubeSchemaReader),
		            member(
		                Id.Segment.toList(
		                    "Product", "All Products", "Non-Consumable", "Household",
		                    "Kitchen Products", "Pot Scrubbers", "Denny"),
		                salesCubeSchemaReader),
		            member(
		                Id.Segment.toList(
		                    "Product", "All Products", "Non-Consumable", "Household",
		                    "Kitchen Products", "Pot Scrubbers", "Red Wing"),
		                salesCubeSchemaReader),
		            member(
		                Id.Segment.toList(
		                    "Product", "All Products", "Non-Consumable", "Household",
		                    "Kitchen Products", "Pots and Pans", "Cormorant"),
		                salesCubeSchemaReader),
		            member(
		                Id.Segment.toList(
		                    "Product", "All Products", "Non-Consumable", "Household",
		                    "Kitchen Products", "Pots and Pans", "Denny"),
		                salesCubeSchemaReader),
		            member(
		                Id.Segment.toList(
		                    "Product", "All Products", "Non-Consumable", "Household",
		                    "Kitchen Products", "Pots and Pans", "High Quality"),
		                salesCubeSchemaReader),
		            member(
		                Id.Segment.toList(
		                    "Product", "All Products", "Non-Consumable", "Household",
		                    "Kitchen Products", "Pots and Pans", "Red Wing"),
		                salesCubeSchemaReader),
		            member(
		                Id.Segment.toList(
		                    "Product", "All Products", "Non-Consumable", "Household",
		                    "Kitchen Products", "Pots and Pans", "Sunset"),
		                salesCubeSchemaReader)));
		    }
	    public static Member member(
	            List<Id.Segment> segmentList,
	            SchemaReader salesCubeSchemaReader)
	        {
	            return salesCubeSchemaReader.getMemberByUniqueName(segmentList, true);
	        }
	    /**
	     * Executes a query with a given expression on an axis, and asserts that it throws an error which matches a particular
	     * pattern. The expression is evaulated against the default cube.
	     */
	    public static void assertAxisThrows(Connection connection,
	      String expression,
	      String pattern ,
	      String cubeName) {
	      Throwable throwable = null;
	      try {
	        final String queryString =
	          "select {" + expression + "} on columns from " + cubeName;
	        executeQuery(connection, queryString);
	      } catch ( Throwable e ) {
	        throwable = e;
	      }
	      checkThrowable( throwable, pattern );
	    }

	    public static void checkThrowable( Throwable throwable, String pattern ) {
	      if ( throwable == null ) {
	        Assertions.fail( "query did not yield an exception" );
	      }
	      String stackTrace = getStackTrace( throwable );
	      if ( stackTrace.indexOf( pattern ) < 0 ) {
	    	  Assertions.fail(
	          "query's error does not match pattern '" + pattern
	            + "'; error is [" + stackTrace + "]" );
	      }
	    }
	    /**
	     * Converts a {@link Throwable} to a stack trace.
	     */
	    //TODO: using stream api
	    public static String getStackTrace( Throwable e ) {
	      StringWriter sw = new StringWriter();
	      PrintWriter pw = new PrintWriter( sw );
	      e.printStackTrace( pw );
	      pw.flush();
	      return sw.toString();
	    }
	
	public static Cube cubeByName(Connection connection, String cubeName) {
        SchemaReader reader = connection.getSchemaReader().withLocus();

        Cube[] cubes = reader.getCubes();
        return cubeByName(cubeName, cubes);
    }
	
    public static Cube cubeByName(String cubeName, Cube[] cubes) {
        Cube resultCube = null;
        for (Cube cube : cubes) {
            if (cubeName.equals(cube.getName())) {
                resultCube = cube;
                break;
            }
        }
        return resultCube;
    }

	public static synchronized void flushSchemaCache(Connection connection) {
		// it's pointless to flush the schema cache if we
		// have a handle on the connection object already

		assertThat(connection).isNotNull();

		mondrian.olap.CacheControl cc = connection.getCacheControl(null);
		assertThat(cc).isNotNull();

		cc.flushSchemaCache();
	}

	public static Result executeQuery(Connection connection, String queryString) {
		Query query = parseQuery(connection, queryString);
		assertThat(query).isNotNull();
		Statement statement = query.getStatement();
		assertThat(statement).isNotNull();

		Result result = statement.getMondrianConnection().execute(new Execution(statement, 60000l));
		return result;
	}

	public static Query parseQuery(Connection connection, String queryString) {

		assertThat(connection).isNotNull();
		Query query = connection.parseQuery(queryString);
		return query;
	}

	public static void assertQueryReturns(Connection connection, String queryString, String expectedResult) {

		Result result = executeQuery(connection, queryString);

		assertThat(result).isNotNull();

		java.io.StringWriter sw = new java.io.StringWriter();
		java.io.PrintWriter pw = new java.io.PrintWriter(sw);

		// TODO: switch so other than printwriter
		result.print(pw);

		assertThat(sw.getBuffer().toString()).isNotNull().isEqualTo(expectedResult);

	}
	public static CellSet executeOlap4jQuery(OlapConnection olapConnection, String queryString ) throws SQLException {
	//TODO: may better fix querys then use upgradeQuery
	  //  queryString = upgradeQuery( queryString );
		
		assertThat(olapConnection).isNotNull();
		assertThat(queryString).isNotNull().isNotBlank();
	    
		OlapStatement stmt = olapConnection.createStatement();
	
	    assertThat(stmt).isNotNull();

	    final CellSet cellSet = stmt.executeOlapQuery( queryString );

	    assertThat(cellSet).isNotNull();

	    // If we're deep testing, check that we never return the dummy null
	    // value when cells are null. TestExpDependencies isn't the perfect
	    // switch to enable this, but it will do for now.
	    //TODO: activate this for all tests
	    if ( MondrianProperties.instance().TestExpDependencies.booleanValue() ) {
	      assertCellSetValid( cellSet );
	    }
	    return cellSet;
	  }
	
	  /**
	   * Checks that a {@link CellSet} is valid.
	   *
	   */
	  public static void assertCellSetValid( CellSet cellSet ) {
	    for ( org.olap4j.Cell cell : cellIter( cellSet ) ) {
	      final Object value = cell.getValue();

	      // Check that the dummy value used to represent null cells never
	      // leaks into the outside world.
	      assertNotSame( value, Util.nullValue );
	      assertFalse(
	        value instanceof Number
	          && ( (Number) value ).doubleValue() == FunUtil.DoubleNull );

	      // Similarly empty values.
	      assertNotSame( value, Util.EmptyValue );
	      assertFalse(
	        value instanceof Number
	          && ( (Number) value ).doubleValue() == FunUtil.DoubleEmpty );

	      // Cells should be null if and only if they are null or empty.
	      if ( cell.getValue() == null ) {
	        assertTrue( cell.isNull() );
	      } else {
	        assertFalse( cell.isNull() );
	      }
	    }
	    }
	    /**
	     * Returns an iterator over cells in an olap4j cell set.
	     */
	    static Iterable<org.olap4j.Cell> cellIter( final CellSet cellSet ) {
	      return new Iterable<org.olap4j.Cell>() {
	        public Iterator<org.olap4j.Cell> iterator() {
	          int[] axisDimensions = new int[ cellSet.getAxes().size() ];
	          int k = 0;
	          for ( CellSetAxis axis : cellSet.getAxes() ) {
	            axisDimensions[ k++ ] = axis.getPositions().size();
	          }
	          final CoordinateIterator
	            coordIter = new CoordinateIterator( axisDimensions );
	          return new Iterator<org.olap4j.Cell>() {
	            public boolean hasNext() {
	              return coordIter.hasNext();
	            }

	            public org.olap4j.Cell next() {
	              final int[] ints = coordIter.next();
	              final List<Integer> list =
	                new AbstractList<Integer>() {
	                  public Integer get( int index ) {
	                    return ints[ index ];
	                  }

	                  public int size() {
	                    return ints.length;
	                  }
	                };
	              return cellSet.getCell(
	                list );
	            }

	            public void remove() {
	              throw new UnsupportedOperationException();
	            }
	          };
	        }
	      };
	    }
	    
	static   public Dialect getDialect(Connection connection){
	    	   DataSource dataSource =connection.getDataSource();
	    	    return DialectManager.createDialect( dataSource, null );
	    	
	    }
}
