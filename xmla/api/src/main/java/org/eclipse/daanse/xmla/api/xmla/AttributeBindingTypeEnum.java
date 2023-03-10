package org.eclipse.daanse.xmla.api.xmla;

/**
 * @return Indicates the part of the Attribute to bind to. Enumeration values are as
 * follows:
 * All: All Level
 * Key: Member keys
 * Name: Member name
 * Value: Member value
 * Translation: Member translations
 * UnaryOperator: Unary operators
 * SkippedLevels: Skipped levels
 * CustomRollup: Custom rollup formulas
 * CustomRollupProperties: Custom rollup properties
 */
public enum AttributeBindingTypeEnum {

    /**
     * All Level
     */
     All,

    /**
     * Member keys
     */
    Key,

    /**
     * Member name
     */
    Name,

    /**
     * Member value
     */
    Value,

    /**
     * Member translations
     */
    Translation,

    /**
     * Unary operators
     */
    UnaryOperator,

    /**
     * Skipped levels
     */
    SkippedLevels,

    /**
     * Custom rollup formulas
     */
    CustomRollup,

    /**
     * Custom rollup properties
     */
    CustomRollupProperties;

    public static AttributeBindingTypeEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        for (AttributeBindingTypeEnum e : AttributeBindingTypeEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("AttributeBindingTypeEnum Illegal argument ")
            .append(v).toString());
    }

}
