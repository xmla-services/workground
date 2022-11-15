/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2016 - 2017 Hitachi Vantara
// All Rights Reserved.
*/

package org.eclipse.daanse.db.dialect.db.mariadb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.eclipse.daanse.db.dialect.db.mysql.MySqlDialect;

@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='MARIADB'",
		"database.product:String='MARIADB'" })
public class MariaDBDialect extends MySqlDialect {

  public static final JdbcDialectFactory FACTORY =
      new JdbcDialectFactory(
          MariaDBDialect.class)
      {
          @Override
          protected boolean acceptsConnection(Connection connection) {
              return super.acceptsConnection(connection);
          }
      };


  public MariaDBDialect( Connection connection ) throws SQLException {
    super( connection );
  }

  @Override
  protected String deduceProductName(DatabaseMetaData databaseMetaData) {
      // It is possible for someone to use the MariaDB JDBC driver with Infobright . . .
      final String productName = super.deduceProductName(databaseMetaData);
      if (isInfobright(databaseMetaData)) {
          return "MySQL (Infobright)";
      }
      return productName;
  }

}
