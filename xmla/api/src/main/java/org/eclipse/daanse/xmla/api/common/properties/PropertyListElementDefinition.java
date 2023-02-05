/*
* Copyright (c) 2023 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.xmla.api.common.properties;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public enum PropertyListElementDefinition {

    Content(Type.EnumString, EnumSet.allOf(Content.class), Access.Write,
            org.eclipse.daanse.xmla.api.common.properties.Content.SchemaData.name(), true, true),

    DataSourceInfo(Type.String, null, Access.ReadWrite, "Empty", true, true),

    Format(Type.EnumString, EnumSet.allOf(Format.class), Access.Write,
            org.eclipse.daanse.xmla.api.common.properties.Format.Native.name(), true, true),

    LocaleIdentifier(Type.UnsignedInteger, null, Access.ReadWrite, null, true, true);

    final Type type;
    final Set<? extends Enum> enumSet;
    final Access access;
    final boolean discover;
    final boolean execute;
    final String value;

    PropertyListElementDefinition(Type type, Set<? extends Enum> enumSet, Access access, String value, boolean discover,
            boolean execute) {

        assert (enumSet != null) == type.isEnum();
        this.type = type;
        this.enumSet = enumSet;
        this.access = access;
        this.value = value;
        this.discover = discover;
        this.execute = execute;
    }
    
   public static Optional<PropertyListElementDefinition> byName(String name) {
        return EnumSet.allOf(PropertyListElementDefinition.class).stream().filter(pd->pd.toString().equalsIgnoreCase(name)).findAny();
    }
}
