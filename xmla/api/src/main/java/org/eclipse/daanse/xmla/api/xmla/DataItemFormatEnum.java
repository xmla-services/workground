package org.eclipse.daanse.xmla.api.xmla;

/**
 * The format of the data item. The valid values are the following:
 * "TrimRight": The value is trimmed on the right.
 * "TrimLeft": The value is trimmed on the left.
 * "TrimAll": The value is trimmed on the left and the right.
 * "TrimNone": The value is not trimmed.
 */
public enum DataItemFormatEnum {
    /**
     * The value is trimmed on the right.
     */
    TRIM_RIGHT("TrimRight"),
    /**
     * The value is trimmed on the left.
     */
    TRIM_LEFT("TrimLeft"),
    /**
     * The value is trimmed on the left and the right.
     */
    TRIM_ALL("TrimAll"),
    /**
     * The value is not trimmed.
     */
    TRIM_NONE("TrimNone");

    private final String value;

    DataItemFormatEnum(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }

    public static DataItemFormatEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        for (DataItemFormatEnum e : DataItemFormatEnum.values()) {
            if (e.getValue().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("DataItemFormatEnum Illegal argument ")
            .append(v).toString());
    }

}
