/*
* Copyright (c) 2022 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.db.datasource.postgresql;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition()
public interface PostgresConfig {
	// https://docs.oracle.com/cd/E13222_01/wls/docs81/jdbc_drivers/oracle.html
	@AttributeDefinition(description = "user")
	default String user() {
		return null;
	}

	/**
	 * OSGi Service Component Spec.:
	 *
	 * Component properties whose names start with full stop are available to the
	 * component instance but are not available as service properties of the
	 * registered service.
	 *
	 * A single low line ('_' \u005F) is converted into a full stop ('.' \u002E)
	 *
	 * @return password
	 */
	@AttributeDefinition(description = "password", type = AttributeType.PASSWORD)
	default String _password() {
		return null;
	}

	@AttributeDefinition(description = "")
	default String host() {
		return null;
	}

	@AttributeDefinition(description = "")
	default String dbname() {
		return null;
	}

	@AttributeDefinition(description = "" ,defaultValue = {"5432"})
	 Integer port() ;

}