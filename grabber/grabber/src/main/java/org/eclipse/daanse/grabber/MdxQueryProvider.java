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
package org.eclipse.daanse.grabber;

import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesResponseRow;

import java.util.List;
import java.util.Optional;

public class MdxQueryProvider {

    private static final String DICTIONARY_QUERY = """
        withs
        member [Measures].[Key] as
        %s.Properties( "Key" )
        member [Measures].[Caption] as
        %s.Properties( "Caption" )
        %s
        select
        {[Measures].[Key], [Measures].[Caption]%s} on 0,
        %s.Members on 1
        from [%s]
        """;
    private static final String PROPERTY_QUERY = """
        member [Measures].[%s] as
        %s.Properties( "%s" )
        """;

    private MdxQueryProvider() {
        // constructor
    }

    static String getDictionaryQuery(MdSchemaLevelsResponseRow it, List<MdSchemaPropertiesResponseRow> properties) {
        Optional<String> lunOptional = it.levelUniqueName();
        Optional<String> hunOptional = it.hierarchyUniqueName();
        Optional<String> cnOptional = it.cubeName();
        if (lunOptional.isPresent()
            && hunOptional.isPresent()
            && cnOptional.isPresent()) {
            String level = lunOptional.get();
            String hierarchy = hunOptional.get();
            String cubeName = cnOptional.get();
            String propertyQuery = getPropertyQuery(properties, level);
            String propertyNames = getPropertyNames(properties, level);
            return String.format(DICTIONARY_QUERY, hierarchy, hierarchy, propertyQuery, propertyNames, cubeName);
        }
        return null;
    }

    private static String getPropertyNames(List<MdSchemaPropertiesResponseRow> properties, String level) {
        StringBuilder sb = new StringBuilder();
        if (properties != null) {
            properties.stream()
                .filter(it -> (it.levelUniqueName().isPresent() && it.levelUniqueName().get().equals(level))).forEach(
                it -> {
                    if (it.propertyName().isPresent()) {
                        String propertyName = it.propertyName().get();
                        sb.append(", [Measures].[")
                            .append(propertyName)
                            .append("]");
                    }
                }
            );
        }
        return sb.toString();
    }

    private static String getPropertyQuery(List<MdSchemaPropertiesResponseRow> properties, String level) {
        StringBuilder sb = new StringBuilder();
        if (properties != null) {
            properties.stream()
                .filter(it -> (it.levelUniqueName().isPresent() && it.levelUniqueName().get().equals(level))).forEach(
                it -> {
                    if (it.propertyName().isPresent()) {
                        String propertyName = it.propertyName().get();
                        sb.append(", ")
                            .append(String.format(PROPERTY_QUERY,
                                propertyName, level, propertyName));
                    }
                }
            );
        }
        return sb.toString();
    }

}
