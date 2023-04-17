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
     ALL("All"),

    /**
     * Member keys
     */
    KEY("Key"),

    /**
     * Member name
     */
    NAME("Name"),

    /**
     * Member value
     */
    VALUE("Value"),

    /**
     * Member translations
     */
    TRANSLATION("Translation"),

    /**
     * Unary operators
     */
    UNARY_OPERATOR("UnaryOperator"),

    /**
     * Skipped levels
     */
    SKIPPED_LEVELS("SkippedLevels"),

    /**
     * Custom rollup formulas
     */
    CUSTOM_ROLLUP("CustomRollup"),

    /**
     * Custom rollup properties
     */
    CUSTOM_ROLLUP_PROPERTIES("CustomRollupProperties");

    private final String value;

    AttributeBindingTypeEnum(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }

    public static AttributeBindingTypeEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        for (AttributeBindingTypeEnum e : AttributeBindingTypeEnum.values()) {
            if (e.getValue().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("AttributeBindingTypeEnum Illegal argument ")
            .append(v).toString());
    }

}
