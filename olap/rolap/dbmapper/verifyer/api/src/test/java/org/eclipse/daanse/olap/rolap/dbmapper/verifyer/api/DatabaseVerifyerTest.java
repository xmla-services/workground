package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Schema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DatabaseVerifyerTest {

    @Mock
    DatabaseMetaData meta;
    
    @Mock
    Connection connection;

    @Mock
    Schema schema;

    @Test
    public void test() throws Exception {

    }

}
