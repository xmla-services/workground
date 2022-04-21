package org.osgi.impl.service.jdbc.mssqlserver.datasource;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition()
public interface MsSqlConfig {
	//https://docs.oracle.com/cd/E13222_01/wls/docs81/jdbc_drivers/oracle.html
	@AttributeDefinition(description = "username")
	default String username() {
		return null;
	}

	@AttributeDefinition(description = "password", type = AttributeType.PASSWORD)
	default String _password() {
		return null;
	}

	

}