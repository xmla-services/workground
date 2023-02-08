package org.eclipse.daanse.xmla.api.common.enums;

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
        for (PropertyCardinalityEnum e : PropertyCardinalityEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("AuthenticationModeEnum Illegal argument ")
            .append(v).toString());
    }
}
