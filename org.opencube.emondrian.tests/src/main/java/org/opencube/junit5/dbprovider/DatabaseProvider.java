package org.opencube.junit5.dbprovider;

import java.io.Closeable;

import javax.sql.DataSource;

public interface DatabaseProvider extends Closeable{
	
public DataSource	activate() ;

public String getJdbcUrl();

}
