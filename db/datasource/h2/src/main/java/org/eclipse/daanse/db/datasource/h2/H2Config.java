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
package org.eclipse.daanse.db.datasource.h2;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
@ObjectClassDefinition()
public interface H2Config {

	@AttributeDefinition(description = "username")
	default String username() {
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
	@SuppressWarnings("java:S100")
	@AttributeDefinition(description = "password", type = AttributeType.PASSWORD)
	default String _password() {
		return null;
	}
	@AttributeDefinition(description = "url")
	String url();


}
