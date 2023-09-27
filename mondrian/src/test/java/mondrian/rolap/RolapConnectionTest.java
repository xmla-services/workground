/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2001-2005 Julian Hyde
// Copyright (C) 2005-2019 Hitachi Vantara and others
// All Rights Reserved.
*/
package mondrian.rolap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.opencube.junit5.TestUtil.assertExprReturns;
import static org.opencube.junit5.TestUtil.assertQueryReturns;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.opencube.junit5.ContextSource;
import org.opencube.junit5.context.TestingContext;
import org.opencube.junit5.dataloader.FastFoodmardDataLoader;
import org.opencube.junit5.propupdator.AppandFoodMartCatalogAsFile;

import mondrian.olap.DriverManager;
import mondrian.olap.MondrianException;
import mondrian.olap.Util;

/**
 * Unit test for {@link RolapConnection}.
 *
 * @author jng
 * @since 16 April, 2004
 */
class RolapConnectionTest {
    private static final ThreadLocal<InitialContext> THREAD_INITIAL_CONTEXT =
        new ThreadLocal<>();

    @BeforeAll
    protected static void beforeAll() throws Exception {
        if (!NamingManager.hasInitialContextFactoryBuilder()) {
            NamingManager.setInitialContextFactoryBuilder(
                new InitialContextFactoryBuilder() {
                    @Override
					public InitialContextFactory createInitialContextFactory(
                        Hashtable<?, ?> environment)
                        throws NamingException
                    {
                        return new InitialContextFactory() {
                            @Override
							public javax.naming.Context getInitialContext(
                                Hashtable<?, ?> environment)
                                throws NamingException
                            {
                                return THREAD_INITIAL_CONTEXT.get();
                            }
                        };
                    }
                }
           );
        }
    }

	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testPooledConnectionWithProperties(TestingContext context) throws SQLException {
        Util.PropertyList properties = baseProperties(context);

        // Only the JDBC-ODBC bridge gives the error necessary for this
        // test to succeed. So trivially succeed for all other JDBC
        // drivers.
        final String jdbc = properties.get("Jdbc");
        if (jdbc != null
            && !jdbc.startsWith("jdbc:odbc:"))
        {
            return;
        }

        // JDBC-ODBC driver does not support UTF-16, so this test succeeds
        // because creating the connection from the DataSource will fail.
        properties.put("jdbc.charSet", "UTF-16");

        final StringBuilder buf = new StringBuilder();
        DataSource dataSource = null;
        //TODO Commented by reason context implementation
        //DataSource dataSource =
        //    RolapConnection.createDataSource(null, properties, buf);
        final String desc = buf.toString();
        assertTrue(desc.startsWith("Jdbc="));

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            fail("Expected exception");
        } catch (SQLException e) {

          if (e.getClass() == SQLException.class
                && e.getCause() == null
                && e.getMessage() != null
                && e.getMessage().equals(""))
            {
                // This is expected, from a later version of Dbcp.
            } else {
                fail("Expected exception, but got a different one: " + e);
            }
        } catch (IllegalArgumentException e) {
            handleIllegalArgumentException(properties, e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

	private Util.PropertyList baseProperties(TestingContext context) {
		Util.PropertyList properties =Util.parseConnectString( context.getOlapConnectString());
		return properties;
	}

	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testNonPooledConnectionWithProperties(TestingContext context) {
        Util.PropertyList properties =baseProperties(context);

        // Only the JDBC-ODBC bridge gives the error necessary for this
        // test to succeed. So trivially succeed for all other JDBC
        // drivers.
        final String jdbc = properties.get("Jdbc");
        if (jdbc != null
            && !jdbc.startsWith("jdbc:odbc:"))
            {
            return;
        }

        // This test is just like the test testPooledConnectionWithProperties
        // except with non-pooled connections.
        properties.put("jdbc.charSet", "UTF-16");
        properties.put(RolapConnectionProperties.PoolNeeded.name(), "false");

        final StringBuilder buf = new StringBuilder();
        //TODO Commented by reason context implementation
        DataSource dataSource = null;
        //DataSource dataSource =
        //    RolapConnection.createDataSource(null, properties, buf);
        final String desc = buf.toString();
        assertTrue(desc.startsWith("Jdbc="));

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            fail("Expected exception");
        } catch (SQLException se) {
            // this is expected
        } catch (IllegalArgumentException e) {
            handleIllegalArgumentException(properties, e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * Handle an {@link IllegalArgumentException} which occurs when have
     * tried to create a connection with an illegal charset.
     */
    private void handleIllegalArgumentException(
        Util.PropertyList properties,
        IllegalArgumentException e)
    {
        // Workaround Java bug #6504538 (see http://bugs.sun.com) with synopsis
        // "DriverManager.getConnection throws IllegalArgumentException".
        if (System.getProperties().getProperty("java.version")
            .startsWith("1.6."))
        {
            properties.remove("jdbc.charSet");

            final StringBuilder buf = new StringBuilder();
            //TODO Commented by reason context implementation
            DataSource dataSource = null;
            //DataSource dataSource =
            //    RolapConnection.createDataSource(null, properties, buf);
            final String desc = buf.toString();
            assertTrue(desc.startsWith("Jdbc="));

            try {
                Connection connection1 = dataSource.getConnection();
                connection1.close();
            } catch (SQLException e1) {
                // ignore
            }
        } else {
            fail("Expect IllegalArgumentException only in JDK 1.6, got " + e);
        }
    }

    /**
     * Tests that the FORMAT function uses the connection's locale.
     */
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testFormatLocale(TestingContext context) {
        String expr = "FORMAT(1234.56, \"#,##.#\")";
        checkLocale(context, "es_ES", expr, "1.234,6", false);
        checkLocale(context, "es_MX", expr, "1,234.6", false);
        checkLocale(context, "en_US", expr, "1,234.6", false);
    }

    /**
     * Tests that measures are formatted using the connection's locale.
     */
	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testFormatStringLocale(TestingContext context) {
        checkLocale(context, "es_ES", "1234.56", "1.234,6", true);
        checkLocale(context, "es_MX", "1234.56", "1,234.6", true);
        checkLocale(context, "en_US", "1234.56", "1,234.6", true);
    }

    private static void checkLocale(TestingContext context,
        final String localeName, String expr, String expected, boolean isQuery)
    {
    	//TODO:
    	//fail("uncomment here");
//        TestContext testContextSpain = new TestContext() {
//            public mondrian.olap.Connection getConnection() {
//                Util.PropertyList properties =
//                    Util.parseConnectString(getConnectString());
//                properties.put(
//                    RolapConnectionProperties.Locale.name(),
//                    localeName);
//                return DriverManager.getConnection(properties, null);
//            }
//        };
          context.setProperty(RolapConnectionProperties.Locale.name(),
                    localeName);
        if (isQuery) {
            String query = "WITH MEMBER [Measures].[Foo] AS '" + expr + "',\n"
                + " FORMAT_STRING = '#,##.#' \n"
                + "SELECT {[MEasures].[Foo]} ON COLUMNS FROM [Sales]";
            String expected2 =
                "Axis #0:\n"
                + "{}\n"
                + "Axis #1:\n"
                + "{[Measures].[Foo]}\n"
                + "Row #0: " + expected + "\n";
            assertQueryReturns(context.createConnection(), query, expected2);
        } else {
            assertExprReturns(context.createConnection(), expr, expected);
        }
    }

	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testConnectSansCatalogFails(TestingContext context) {
        Util.PropertyList properties =baseProperties(context);
        properties.remove(RolapConnectionProperties.Catalog.name());
        properties.remove(RolapConnectionProperties.CatalogContent.name());

        if (RolapUtil.SQL_LOGGER.isDebugEnabled()) {
            RolapUtil.SQL_LOGGER.debug(
                 "\n  [Connection Properties | " + properties
                + "]\n");
        } else {
            System.out.println(properties);
        }

        try {
            DriverManager.getConnection(
                properties,
                null);
            fail("expected exception");
        } catch (MondrianException e) {
            assertTrue(
                e.getMessage().indexOf(
                    "Connect string must contain property 'Catalog' or "
                    + "property 'CatalogContent'")
                >= 0);
        }
    }

	@ParameterizedTest
	@ContextSource(propertyUpdater = AppandFoodMartCatalogAsFile.class, dataloader = FastFoodmardDataLoader.class )
    void testGetJdbcConnectionWhenJdbcIsNull(TestingContext context) {
        final StringBuilder connectInfo = new StringBuilder();
        Util.PropertyList properties =
           baseProperties(context);
        properties.remove(RolapConnectionProperties.Jdbc.name());
        try {
            //TODO Commented by reason context implementation
            DataSource dataSource = null;
            //DataSource dataSource =
            //    RolapConnection.createDataSource(null, properties, connectInfo);
        } catch (RuntimeException ex) {
            String s=connectInfo.toString();
            assertTrue(
                s==null||s.length()==0,
                connectInfo.toString());
        }
    }
}
