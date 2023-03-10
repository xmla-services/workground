package org.eclipse.daanse.xmla.api.xmla;

public enum InvalidXmlCharacterEnum {

    /**
     * Specifies that invalid XML characters are
     * preserved in the character stream.
     */
    Preserve,

    /**
     * Specifies that invalid XML characters are
     * removed.
     */
    Remove,

    /**
     * Specifies that invalid XML characters are
     * replaced with a question mark (?) character.
     */
    Replace;

    public static InvalidXmlCharacterEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        for (InvalidXmlCharacterEnum e : InvalidXmlCharacterEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("InvalidXmlCharacterEnum Illegal argument ")
            .append(v).toString());
    }

}
