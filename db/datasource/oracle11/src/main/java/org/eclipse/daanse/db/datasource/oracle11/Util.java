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
package org.eclipse.daanse.db.datasource.oracle11;

import java.util.Properties;

public class Util {

    private Util() {
        // constructor
    }

    public static Properties transformConfig(Oracle11Config config)  {
        Properties properties = new Properties();

        if (config.username() != null) {
            properties.put("usernmame", config.username());

        }
        return properties;
    }
}
