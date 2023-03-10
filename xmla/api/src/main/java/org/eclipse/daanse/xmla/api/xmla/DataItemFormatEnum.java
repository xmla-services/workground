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
    TrimRight,
    /**
     * The value is trimmed on the left.
     */
    TrimLeft,
    /**
     * The value is trimmed on the left and the right.
     */
    TrimAll,
    /**
     * The value is not trimmed.
     */
    TrimNone;

    public static DataItemFormatEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        for (DataItemFormatEnum e : DataItemFormatEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("DataItemFormatEnum Illegal argument ")
            .append(v).toString());
    }

}
