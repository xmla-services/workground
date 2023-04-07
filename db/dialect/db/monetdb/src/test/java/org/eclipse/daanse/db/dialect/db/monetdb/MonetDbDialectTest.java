/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (c) 2015-2021 Hitachi Vantara.
// All rights reserved.
 */
package org.eclipse.daanse.db.dialect.db.monetdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MonetDbDialectTest {
    private Connection connection = mock(Connection.class);
    private DatabaseMetaData metaData = mock(DatabaseMetaData.class);

    private MonetDbDialect dialect;
    private static final String JUL_2017_SP1_DB_VERSION = "11.27.5";
    private static final String AUG_2011_SP2_DB_VERSION = "11.5.7";
    private static final String AUG_2011_SP1_DB_VERSION = "11.5.3";
    private static final String CURRENT_DB_VERSION = JUL_2017_SP1_DB_VERSION;

    @BeforeEach
    protected void setUp() throws Exception {
        when(metaData.getDatabaseProductName()).thenReturn("MONETDB");
        when(metaData.getDatabaseProductVersion()).thenReturn(CURRENT_DB_VERSION);
        when(connection.getMetaData()).thenReturn(metaData);
        dialect = new MonetDbDialect();
        dialect.initialize(connection);
    }

    @Test
    void testAllowsCountDistinctFalse_BeforeAug2011SP2() throws Exception {
        // set up the version before Aug2011 SP2
        when(metaData.getDatabaseProductVersion()).thenReturn(AUG_2011_SP1_DB_VERSION);
        dialect = new MonetDbDialect();
        dialect.initialize(connection);
        assertFalse(dialect.allowsCountDistinct());
    }

    @Test
    void testAllowsCountDistinctTrue_StartingFromAug2011SP2() throws Exception {
        // set up the version starting from Aug2011 SP2
        when(metaData.getDatabaseProductVersion()).thenReturn(AUG_2011_SP2_DB_VERSION);
        dialect = new MonetDbDialect();
        dialect.initialize(connection);
        assertTrue(dialect.allowsCountDistinct());
    }

    @Test
    void testAllowsCountDistinct() throws Exception {
        assertTrue(dialect.allowsCountDistinct());
    }

    @Test
    void testAllowsCompoundCountDistinct() {
        assertFalse(dialect.allowsCompoundCountDistinct());
    }

    @Test
    void testAllowsMultipleCountDistinct() {
        assertFalse(dialect.allowsMultipleCountDistinct());
    }

    @Test
    void testAllowsMultipleDistinctSqlMeasures() {
        assertFalse(dialect.allowsMultipleDistinctSqlMeasures());
    }

    @Test
    void testAllowsCountDistinctWithOtherAggs() {
        assertFalse(dialect.allowsCountDistinctWithOtherAggs());
    }

    @Test
    void testRequiresAliasForFromQuery() {
        assertTrue(dialect.requiresAliasForFromQuery());
    }

    @Test
    void testSupportsGroupByExpressions() {
        assertFalse(dialect.supportsGroupByExpressions());
    }

    @Test
    void testRequiresHavingAlias() {
        assertFalse(dialect.requiresHavingAlias());
    }

    @Test
    void testRequiresGroupByAlias() {
        assertFalse(dialect.requiresGroupByAlias());
    }

    @Test
    void testRequiresOrderByAlias() {
        assertFalse(dialect.requiresOrderByAlias());
    }

    @Test
    void testAllowsOrderByAlias() {
        assertFalse(dialect.allowsOrderByAlias());
    }

    @Test
    void testCompareVersions_Equals() {
        assertEquals(0, dialect.compareVersions(AUG_2011_SP2_DB_VERSION, AUG_2011_SP2_DB_VERSION));
    }

    @Test
    void testCompareVersions_SubStringLeft_Less() {
        assertEquals(-1, dialect.compareVersions("11.3", "11.3.28"));
    }

    @Test
    void testCompareVersions_SubStringRight_More() {
        assertEquals(1, dialect.compareVersions("11.3.28", "11.3"));
    }

    @Test
    void testCompareVersions_LessOnMajor() {
        assertEquals(-1, dialect.compareVersions("3.3.28", "11.3.28"));
    }

    @Test
    void testCompareVersions_MoreOnMajor() {
        assertEquals(1, dialect.compareVersions("10.3.28", "2.3.28"));
    }

    @Test
    void testCompareVersions_LessOnMinor() {
        assertEquals(-1, dialect.compareVersions("11.3.28", "11.11.28"));
    }

    @Test
    void testCompareVersions_MoreOnMinor() {
        assertEquals(1, dialect.compareVersions("11.11.28", "11.3.28"));
    }

    @Test
    void testCompareVersions_LessOnPatch() {
        assertEquals(-1, dialect.compareVersions("11.10.3", "11.10.28"));
    }

    @Test
    void testCompareVersions_MoreOnPatch() {
        assertEquals(1, dialect.compareVersions("11.10.28", "11.10.3"));
    }

    @Test
    void testCompareVersions_OnlyMajor() {
        assertEquals(-1, dialect.compareVersions("7", "10"));
    }

}
