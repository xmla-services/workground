/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (c) 2015-2017 Hitachi Vantara.
// All rights reserved.
 */
package org.eclipse.daanse.db.dialect.db.impala;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Andrey Khayrutdinov
 */
class ImpalaDialectTest{
  private static Connection connection = mock( Connection.class );
  private static DatabaseMetaData metaData = mock( DatabaseMetaData.class );
  private static ImpalaDialect impalaDialect;

  @BeforeEach
  protected void setUp() throws Exception {
    when( metaData.getDatabaseProductName() ).thenReturn( "IMPALA" );
    when( connection.getMetaData() ).thenReturn( metaData );
    impalaDialect = new ImpalaDialect(connection);
  }

  @Test
  void testAllowsRegularExpressionInWhereClause() {
    assertTrue( impalaDialect.allowsRegularExpressionInWhereClause() );
  }

  @Test
  void testGenerateRegularExpression_InvalidRegex() throws Exception {
    assertNull( impalaDialect.generateRegularExpression( "table.column", "(a" ), "Invalid regex should be ignored" );
  }

  @Test
  void testGenerateRegularExpression_CaseInsensitive() throws Exception {
    String sql = impalaDialect.generateRegularExpression( "table.column", "(?i)|(?u).*a.*" ).toString();
    assertSqlWithRegex( false, sql, "'.*A.*'" );
  }

  @Test
  void testGenerateRegularExpression_CaseSensitive() throws Exception {
    String sql = impalaDialect.generateRegularExpression( "table.column", ".*1.*" ).toString();
    assertSqlWithRegex( true, sql, "'.*1.*'" );
  }

  private void assertSqlWithRegex( boolean isCaseSensitive, String sql, String quotedRegex ) throws Exception {
    assertNotNull( sql, "Sql should be generated" );
    assertEquals(  isCaseSensitive, !sql.contains( "UPPER" ),sql);
    assertTrue( sql.contains( "cast(table.column as string)" ),sql );
    assertTrue( sql.contains( "REGEXP" ),sql );
    assertTrue( sql.contains( quotedRegex ),sql);
  }
}
