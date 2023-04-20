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
package org.eclipse.daanse.db.datasource.mysql;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;

public interface ConfigBase {
    @AttributeDefinition(name = "%username.name", description = "%username.description")
    default String username() {
        return null;
    }

    @AttributeDefinition(name = "%password.name", description = "%password.description", type = AttributeType.PASSWORD)
    default String _password() {
        return null;
    }

    @AttributeDefinition(name = "%port.name", description = "%port.description")
    default Integer port() {
        return null;
    }

    @AttributeDefinition(name = "%url.name", description = "%url.description")
    default String url() {
        return null;
    }

    @AttributeDefinition(name = "%databaseName.name", description = "%databaseName.description")
    default String databaseName() {
        return null;
    }

    @AttributeDefinition(name = "%serverName.name", description = "%serverName.description")
    default String serverName() {
        return null;
    }

}
