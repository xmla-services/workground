/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/
package org.eclipse.daanse.db.dialect.db.pdidataservice;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.BestFitColumnType;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.common.JdbcDialectImpl;
import org.eclipse.daanse.db.dialect.db.common.factory.JdbcDialectFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@ServiceProvider(value = Dialect.class, attribute = { "database.dialect.type:String='PDI'",
		"database.product:String='PDI'" })
@Component(service = Dialect.class, scope = ServiceScope.SINGLETON)
public class PdiDataServiceDialect extends JdbcDialectImpl {

  public static final JdbcDialectFactory FACTORY =
    new JdbcDialectFactory(PdiDataServiceDialect.class);

  public PdiDataServiceDialect(Connection connection) throws SQLException {
    super(connection);
  }

  public PdiDataServiceDialect() {
  }

  @Override
  public BestFitColumnType getType(ResultSetMetaData metaData, int columnIndex)
    throws SQLException
  {
    int type = metaData.getColumnType(columnIndex + 1);
    if (type == Types.DECIMAL) {
      return BestFitColumnType.OBJECT;
    } else {
      return super.getType(metaData, columnIndex);
    }
  }
}
// End PdiDataServiceDialect.java
