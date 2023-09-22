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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package mondrian.rolap.util;

import java.util.Map;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingTable;

public class TableUtil {
    public static Map<String,String> getHintMap(MappingTable table) {
        java.util.Map<String,String> h =
            new java.util.HashMap<>();
        if (table.hints() != null) {
            int size = table.hints().size();
            for (int i = 0; i < size; i++) {
                h.put(table.hints().get(i).type(), table.hints().get(i).content());
            }
        }
        return h;
    }

    public static String getFilter(MappingTable table) {
        return (table.sql() == null) ? null : table.sql().content();
    }
}
