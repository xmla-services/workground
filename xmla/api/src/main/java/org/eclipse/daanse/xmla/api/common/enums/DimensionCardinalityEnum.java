package org.eclipse.daanse.xmla.api.common.enums;

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
        for (DimensionCardinalityEnum e : DimensionCardinalityEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("DimensionCardinalityEnum Illegal argument ")
            .append(v).toString());
    }
}
