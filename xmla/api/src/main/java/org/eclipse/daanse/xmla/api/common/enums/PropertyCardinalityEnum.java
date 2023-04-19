package org.eclipse.daanse.xmla.api.common.enums;

import java.util.stream.Stream;

/**
 * The cardinality of the property.
 * Possible values include the
 * following strings:
 */
public enum PropertyCardinalityEnum {

    ONE,
    MANY;

    public static PropertyCardinalityEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        return Stream.of(PropertyCardinalityEnum.values())
            .filter(e -> (e.name().equals(v)))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                new StringBuilder("PropertyCardinalityEnum Illegal argument ").append(v)
                    .toString())
            );
    }
}
