package org.opencube.junit5;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
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
import org.olap4j.OlapException;
import org.olap4j.OlapStatement;
import org.olap4j.impl.CoordinateIterator;
import org.opencube.junit5.context.Context;

import mondrian.calc.TupleList;
import mondrian.calc.impl.UnaryTupleList;
import mondrian.olap.Axis;
import mondrian.olap.Cell;
import mondrian.olap.Connection;
import mondrian.olap.Cube;
import mondrian.olap.Id;
import mondrian.olap.Member;
import mondrian.olap.MondrianProperties;
import mondrian.olap.Position;
import mondrian.olap.Query;
import mondrian.olap.Result;
import mondrian.olap.SchemaReader;
import mondrian.olap.Util;
import mondrian.olap.fun.FunUtil;
import mondrian.olap4j.MondrianOlap4jConnection;
import mondrian.rolap.RolapConnectionProperties;
import mondrian.server.Execution;
import mondrian.server.Statement;
import mondrian.spi.Dialect;
import mondrian.spi.DialectManager;
import mondrian.spi.impl.FilterDynamicSchemaProcessor;
import mondrian.xmla.XmlaException;

import java.util.regex.Pattern;

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
	    
	    public static void assertAxisThrows(Connection connection,
	  	      String expression,
	  	      String pattern) {
	    	assertAxisThrows(connection, expression, pattern, getDefaultCubeName());
	    }
	    
		/**
		 * Executes a query, and asserts that it throws an exception which contains the
		 * given pattern.
		 *
		 * @param queryString Query string
		 * @param pattern     Pattern which exception must match
		 */
		public static void assertQueryThrows(Connection connection, String queryString, String pattern) {
			Throwable throwable;
			try {
				Result result = executeQuery(connection, queryString);
				Util.discard(result);
				throwable = null;
			} catch (Throwable e) {
				throwable = e;
			}
			checkThrowable(throwable, pattern);
		}
	    
		/**
		 * Executes an expression, and asserts that it gives an error which contains a
		 * particular pattern. The error might occur during parsing, or might be
		 * contained within the cell value.
		 */
		public static void assertExprThrows(Connection connection, String expression, String pattern) {
			Throwable throwable = null;
			try {
				String cubeName = getDefaultCubeName();
				if (cubeName.indexOf(' ') >= 0) {
					cubeName = Util.quoteMdxIdentifier(cubeName);
				}
				expression = Util.replace(expression, "'", "''");
				Result result = executeQuery(connection, "with member [Measures].[Foo] as '" + expression
						+ "' select {[Measures].[Foo]} on columns from " + cubeName);
				Cell cell = result.getCell(new int[] { 0 });
				if (cell.isError()) {
					throwable = (Throwable) cell.getValue();
				}
			} catch (Throwable e) {
				throwable = e;
			}
			checkThrowable(throwable, pattern);
		}	    

		/**
		 * Checks that an actual string matches an expected string.
		 *
		 * <p>
		 * If they do not, throws a {@link junit.framework.ComparisonFailure} and prints
		 * the difference, including the actual string as an easily pasted Java string
		 * literal.
		 */
		public static void assertEqualsVerbose(String expected, String actual) {
			assertEqualsVerbose(expected, actual, true, null);
		}

		/**
		 * Checks that an actual string matches an expected string.
		 *
		 * <p>
		 * If they do not, throws a {@link ComparisonFailure} and prints the difference,
		 * including the actual string as an easily pasted Java string literal.
		 *
		 * @param expected Expected string
		 * @param actual   Actual string
		 * @param java     Whether to generate actual string as a Java string literal if
		 *                 the values are not equal
		 * @param message  Message to display, optional
		 */
		public static void assertEqualsVerbose(String expected, String actual, boolean java, String message) {
			assertEqualsVerbose(fold(expected), actual, java, message);
		}
		
		/**
		 * Wrapper around a string that indicates that all line endings have been
		 * converted to platform-specific line endings.
		 *
		 * @see TestContext#fold
		 */
		public static class SafeString {
			public final String s;

			private SafeString(String s) {
				this.s = s;
			}
		}

		/**
		 * Replaces line-endings in a string with the platform-dependent equivalent. If
		 * the input string already has platform-dependent line endings, no replacements
		 * are made.
		 *
		 * @param string String whose line endings are to be made platform- dependent.
		 *               Typically these are constant "expected value" string
		 *               expressions where the linefeed is represented as linefeed "\n",
		 *               but sometimes this method will receive strings created
		 *               dynamically where the line endings are already appropriate for
		 *               the platform.
		 * @return String where all linefeeds have been converted to platform-specific
		 *         (CR+LF on Windows, LF on Unix/Linux)
		 */
		public static SafeString fold(String string) {
			if (string == null) {
				return null;
			}
			if (nl.equals("\n") || string.indexOf(nl) != -1) {
				return new SafeString(string);
			}
			return new SafeString(Util.replace(string, "\n", nl));
		}
		
		protected static final String nl = Util.nl;
		private static final String indent = "                ";
		private static final Pattern LineBreakPattern = Pattern.compile("\r\n|\r|\n");
		private static final Pattern TabPattern = Pattern.compile("\t");
		private static final String lineBreak2 = "\\\\n\"" + nl + indent + "+ \"";

		private static String toJavaString(String s) {
			// Convert [string with "quotes" split
			// across lines]
			// into ["string with \"quotes\" split\n"
			// + "across lines
			//
			s = Util.replace(s, "\"", "\\\"");
			s = LineBreakPattern.matcher(s).replaceAll(lineBreak2);
			s = TabPattern.matcher(s).replaceAll("\\\\t");
			s = "\"" + s + "\"";
			String spurious = nl + indent + "+ \"\"";
			if (s.endsWith(spurious)) {
				s = s.substring(0, s.length() - spurious.length());
			}
			return s;
		}
		
		/**
		 * Checks that an actual string matches an expected string.
		 *
		 * <p>
		 * If they do not, throws a {@link ComparisonFailure} and prints the difference,
		 * including the actual string as an easily pasted Java string literal.
		 *
		 * @param safeExpected Expected string, where all line endings have been
		 *                     converted into platform-specific line endings
		 * @param actual       Actual string
		 * @param java         Whether to generate actual string as a Java string
		 *                     literal if the values are not equal
		 * @param message      Message to display, optional
		 */
		public static void assertEqualsVerbose(SafeString safeExpected, String actual, boolean java, String message) {
			String expected = safeExpected == null ? null : safeExpected.s;
			if ((expected == null) && (actual == null)) {
				return;
			}
			if ((expected != null) && expected.equals(actual)) {
				return;
			}
			if (message == null) {
				message = "";
			} else {
				message += nl;
			}
			message += "Expected:" + nl + expected + nl + "Actual:" + nl + actual + nl;
			if (java) {
				message += "Actual java:" + nl + toJavaString(actual) + nl;
			}
			assertEquals(expected, actual, message);
		}		
		
		/**
		 * Executes an expression and asserts that it returns a given result.
		 */
		public static void assertExprReturns(Connection connection, String cubeName, String expression, String expected) {
			final Cell cell = executeExprRaw(connection, cubeName, expression);
			if (expected == null) {
				expected = ""; // null values are formatted as empty string
			}
			assertEqualsVerbose(expected, cell.getFormattedValue());
		}
		
		/**
		 * Executes an expression and asserts that it returns a given result.
		 */
		public static void assertExprReturns(Connection connection, String expression, String expected) {
			assertExprReturns(connection, getDefaultCubeName(), expression, expected);
		}
		
		public static String getDefaultCubeName() {
			return "Sales";
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

		if (connection == null) {
			return;
		}

		mondrian.olap.CacheControl cc = connection.getCacheControl(null);

		if (cc == null) {
			return;
		}
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

	/**
	 * Executes a query with a given expression on an axis, and returns the whole
	 * axis.
	 */
	public static Axis executeAxis(Connection connection, String cubeName, String expression) {
		Result result = executeQuery(connection, "select {" + expression + "} on columns from " + cubeName);
		return result.getAxes()[0];
	}
	
	public static Axis executeAxis(Connection connection, String expression) {
		return executeAxis(connection, getDefaultCubeName(), expression);
	}
	/**
	 * Converts a set of positions into a string. Useful if you want to check that
	 * an axis has the results you expected.
	 */
	public static String toString(List<Position> positions) {
		StringBuilder buf = new StringBuilder();
		int i = 0;
		for (Position position : positions) {
			if (i > 0) {
				buf.append(nl);
			}
			if (position.size() != 1) {
				buf.append("{");
			}
			for (int j = 0; j < position.size(); j++) {
				Member member = position.get(j);
				if (j > 0) {
					buf.append(", ");
				}
				buf.append(member.getUniqueName());
			}
			if (position.size() != 1) {
				buf.append("}");
			}
			i++;
		}
		return buf.toString();
	}

	/**
	 * Converts a {@link mondrian.olap.Result} to text in traditional format.
	 *
	 * <p>
	 * For more exotic formats, see {@link org.olap4j.layout.CellSetFormatter}.
	 *
	 * @param result Query result
	 * @return Result as text
	 */
	public static String toString(Result result) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		result.print(pw);
		pw.flush();
		return sw.toString();
	}	
	
	/**
	 * Executes a query with a given expression on an axis, and asserts that it
	 * returns the expected string.
	 */
	public static void assertAxisReturns(Connection connection, String cubeName, String expression, String expected) {
		Axis axis = executeAxis(connection, cubeName, expression);
		assertEqualsVerbose(expected, upgradeActual(toString(axis.getPositions())));
	}
	
	public static void assertAxisReturns(Connection connection, String expression, String expected) {
		assertAxisReturns(connection, getDefaultCubeName(), expression, expected);
	}
	
	/**
	 * Massages the actual result of executing a query to handle differences in
	 * unique names betweeen old and new behavior.
	 *
	 * <p>
	 * Even though the new naming is not enabled by default, reference logs should
	 * be in terms of the new naming.
	 *
	 * @param actual Actual result
	 * @return Expected result massaged for backwards compatibility
	 * @see mondrian.olap.MondrianProperties#SsasCompatibleNaming
	 */
	public static String upgradeActual(String actual) {
		if (!MondrianProperties.instance().SsasCompatibleNaming.get()) {
			actual = Util.replace(actual, "[Time.Weekly]", "[Time].[Weekly]");
			actual = Util.replace(actual, "[All Time.Weeklys]", "[All Weeklys]");
			actual = Util.replace(actual, "<HIERARCHY_NAME>Time.Weekly</HIERARCHY_NAME>",
					"<HIERARCHY_NAME>Weekly</HIERARCHY_NAME>");

			// for a few tests in SchemaTest
			actual = Util.replace(actual, "[Store.MyHierarchy]", "[Store].[MyHierarchy]");
			actual = Util.replace(actual, "[All Store.MyHierarchys]", "[All MyHierarchys]");
			actual = Util.replace(actual, "[Store2].[All Store2s]", "[Store2].[Store].[All Stores]");
			actual = Util.replace(actual, "[Store Type 2.Store Type 2].[All Store Type 2.Store Type 2s]",
					"[Store Type 2].[All Store Type 2s]");
			actual = Util.replace(actual, "[TIME.CALENDAR]", "[TIME].[CALENDAR]");
			actual = Util.replace(actual, "<Store>true</Store>", "<Store>1</Store>");
			actual = Util.replace(actual, "<Employees>80000.0000</Employees>", "<Employees>80000</Employees>");
		}
		return actual;
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

	/**
	 * Executes a query and checks that the result is a given string, displaying a
	 * message if result does not match desiredResult.
	 */
	public static void assertQueryReturns(Connection connection, String message, String query, String desiredResult) {
		Result result = executeQuery(connection, query);
		String resultString = toString(result);
		if (desiredResult != null) {
			assertEqualsVerbose(desiredResult, upgradeActual(resultString), true, message);
		}
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
	
		public static Member executeSingletonAxis(Connection connection, String expression) {
			final String cubeName = getDefaultCubeName();
			Result result = executeQuery(connection, "select {" + expression + "} on columns from " + cubeName);
			Axis axis = result.getAxes()[0];
			switch (axis.getPositions().size()) {
			case 0:
				// The mdx "{...}" operator eliminates null members (that is,
				// members for which member.isNull() is true). So if "expression"
				// yielded just the null member, the array will be empty.
				return null;
			case 1:
				// Java nulls should never happen during expression evaluation.
				Position position = axis.getPositions().get(0);
				Util.assertTrue(position.size() == 1);
				Member member = position.get(0);
				Util.assertTrue(member != null);
				return member;
			default:
				throw Util.newInternal(
						"expression " + expression + " yielded " + axis.getPositions().size() + " positions");
			}
		}

		static String rawSchema = null;
		
		public static String getRawSchema(Context context) {
			if(rawSchema == null) {
				try {
					OlapConnection connection = context.createOlap4jConnection();
					final String catalogUrl = ((MondrianOlap4jConnection) connection).getMondrianConnection()
							.getCatalogName();
			    	String schema;
				    synchronized ( TestUtil.class ) {
				    	schema = Util.readVirtualFileAsString(catalogUrl);
				    }
					rawSchema = schema;
				} catch (Exception e) {
					throw new RuntimeException("getRawSchema exception", e);
				}
			}
			return rawSchema;
		}
		
		public static void withSchema(Context context, String schema) {
			context.setProperty(RolapConnectionProperties.CatalogContent.name(), schema);
		}

		public static void withRole(Context context, String roleName) {
			context.setProperty(RolapConnectionProperties.Role.name(), roleName);
		}

}
