/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (c) 2015-2017 Hitachi Vantara.
// All rights reserved.
 */
package org.eclipse.daanse.db.dialect.db.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JdbcDialectImplTest{
  private static final String ILLEGAL_BOOLEAN_LITERAL =
      "illegal for base dialect implemetation boolean literal";
  private static final String ILLEGAL_BOOLEAN_LITERAL_MESSAGE =
      "Illegal BOOLEAN literal:  ";
  private static final String BOOLEAN_LITERAL_TRUE = "True";
  private static final String BOOLEAN_LITERAL_FALSE = "False";
  private static final String BOOLEAN_LITERAL_ONE = "1";
  private static final String BOOLEAN_LITERAL_ZERO = "0";

  private JdbcDialectImpl jdbcDialect = new JdbcDialectImpl() {

      @Override
      public String getDialectName() {
          return null;
      }

      @Override
      protected boolean isSupportedProduct(String productName, String productVersion) {
          return true;
      }
  };
  private static StringBuilder buf;

  @BeforeEach
  protected void setUp() throws Exception {
    buf = new StringBuilder();
  }

  @Test
  void testAllowsRegularExpressionInWhereClause() {
    assertFalse(jdbcDialect.allowsRegularExpressionInWhereClause());
  }

  @Test
  void testGenerateRegularExpression() {
    assertNull(jdbcDialect.generateRegularExpression(null, null));
  }

  @Test
  void testQuoteBooleanLiteral_True() throws Exception {
    assertEquals(0, buf.length());
    jdbcDialect.quoteBooleanLiteral(buf, BOOLEAN_LITERAL_TRUE);
    assertEquals(BOOLEAN_LITERAL_TRUE, buf.toString());
  }

  @Test
  void testQuoteBooleanLiteral_False() throws Exception {
    assertEquals(0, buf.length());
    jdbcDialect.quoteBooleanLiteral(buf, BOOLEAN_LITERAL_FALSE);
    assertEquals(BOOLEAN_LITERAL_FALSE, buf.toString());
  }

  @Test
  void testQuoteBooleanLiteral_OneIllegaLiteral() throws Exception {
    assertEquals(0, buf.length());
    try {
      jdbcDialect.quoteBooleanLiteral(buf, BOOLEAN_LITERAL_ONE);
    fail(
        "The illegal boolean literal exception should appear BUT it was not.");
    } catch (NumberFormatException e) {
      assertEquals(
          ILLEGAL_BOOLEAN_LITERAL_MESSAGE
          + BOOLEAN_LITERAL_ONE,
          e.getMessage());
    }
  }

  @Test
  void testQuoteBooleanLiteral_ZeroIllegaLiteral() throws Exception {
    assertEquals(0, buf.length());
    try {
      jdbcDialect.quoteBooleanLiteral(buf, BOOLEAN_LITERAL_ZERO);
    fail(
        "The illegal boolean literal exception should appear BUT it was not.");
    } catch (NumberFormatException e) {
      assertEquals(
          ILLEGAL_BOOLEAN_LITERAL_MESSAGE
          + BOOLEAN_LITERAL_ZERO,
          e.getMessage());
    }
  }

  @Test
  void testQuoteBooleanLiteral_TrowsExceptionOnIllegaLiteral()
      throws Exception {
    assertEquals(0, buf.length());
    try {
      jdbcDialect.quoteBooleanLiteral(buf, ILLEGAL_BOOLEAN_LITERAL);
    fail(
        "The illegal boolean literal exception should appear BUT it was not.");
    } catch (NumberFormatException e) {
      assertEquals(
          ILLEGAL_BOOLEAN_LITERAL_MESSAGE
          + ILLEGAL_BOOLEAN_LITERAL,
          e.getMessage());
    }
  }

}
