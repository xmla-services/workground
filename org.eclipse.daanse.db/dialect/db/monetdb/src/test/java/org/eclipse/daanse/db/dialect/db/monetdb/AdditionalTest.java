package org.eclipse.daanse.db.dialect.db.monetdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import org.eclipse.daanse.db.dialect.api.BestFitColumnType;
import org.junit.jupiter.api.Test;

public class AdditionalTest {

    @Test
    public void testMonetBooleanColumn() throws SQLException {
      ResultSetMetaData resultSet = mock(ResultSetMetaData.class);
      when(resultSet.getColumnType(1)).thenReturn(Types.BOOLEAN );

      MonetDbDialect monetDbDialect = new MonetDbDialect();
      BestFitColumnType type = monetDbDialect.getType( resultSet, 0 );
      assertEquals( BestFitColumnType.OBJECT, type );
    }
}
