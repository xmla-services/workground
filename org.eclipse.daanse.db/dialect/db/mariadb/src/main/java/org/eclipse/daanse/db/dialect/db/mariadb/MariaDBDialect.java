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

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.eclipse.daanse.db.dialect.db.mysql.MySqlDialect3;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import aQute.bnd.annotation.spi.ServiceProvider;

@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='MARIADB'",
		"database.product:String='MARIADB'" })
@Component(service = Dialect.class, scope = ServiceScope.SINGLETON)
public class MariaDBDialect extends MySqlDialect3 {

  public static final JdbcDialectFactory FACTORY =
      new JdbcDialectFactory(
          MariaDBDialect.class)
      {
          @Override
          protected boolean acceptsConnection(Connection connection) {
              return super.acceptsConnection(connection);
          }
      };


  public MariaDBDialect() {
  }
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
