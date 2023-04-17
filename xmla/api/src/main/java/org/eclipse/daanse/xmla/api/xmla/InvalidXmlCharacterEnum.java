package org.eclipse.daanse.xmla.api.xmla;

public enum InvalidXmlCharacterEnum {

    /**
     * Specifies that invalid XML characters are
     * preserved in the character stream.
     */
    PRESERVE("Preserve"),

    /**
     * Specifies that invalid XML characters are
     * removed.
     */
    REMOVE("Remove"),

    /**
     * Specifies that invalid XML characters are
     * replaced with a question mark (?) character.
     */
    REPLACE("Replace");

    private final String value;

    InvalidXmlCharacterEnum(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }

    public static InvalidXmlCharacterEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        for (InvalidXmlCharacterEnum e : InvalidXmlCharacterEnum.values()) {
            if (e.getValue().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("InvalidXmlCharacterEnum Illegal argument ")
            .append(v).toString());
    }

}
