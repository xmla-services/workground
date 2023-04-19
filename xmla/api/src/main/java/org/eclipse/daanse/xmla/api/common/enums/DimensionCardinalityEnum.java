package org.eclipse.daanse.xmla.api.common.enums;

import java.util.stream.Stream;

/**
 * The number of instances a dimension
 * member can have for a single instance of a
 * measure group measure
 */
public enum DimensionCardinalityEnum {

    ONE,
    MANY;

    public static DimensionCardinalityEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        return Stream.of(DimensionCardinalityEnum.values())
            .filter(e -> (e.name().equals(v)))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                new StringBuilder("DimensionCardinalityEnum Illegal argument ").append(v)
                    .toString())
            );
    }
}
