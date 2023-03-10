package org.eclipse.daanse.xmla.api.xmla;

public enum NullProcessingEnum {

    /**
     * The server determines how null processing
     * is handled.<97> "Automatic" uses the default processing
     * that is appropriate for the element
     */
    Automatic,

    /**
     * Generates an unknown member. This
     * value MUST NOT be used if the column is associated with a
     * measure.
     */
    UnknownMember,

    /**
     * Converts the null value to zero (for
     * numeric data items) or a blank string (for string data items)
     */
    ZeroOrBlank,

    /**
     * Preserves the null value.
     */
    Preserve,

    /**
     * Raises an error. Value "Error" is not supported for
     * measures. This value MUST NOT be used if the column is
     * associated with a measure
     */
     Error;

    public static NullProcessingEnum fromValue(String v) {
        if (v == null) {
            return NullProcessingEnum.Automatic;
        }
        for (NullProcessingEnum e : NullProcessingEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("NullProcessingEnum Illegal argument ")
            .append(v).toString());
    }

}
