package org.eclipse.daanse.xmla.api.common.enums;

public enum AuthenticationModeEnum {

    /**
     * No user ID or password has to be sent.
     */
    UNAUTHENTICATED("Unauthenticated"),

    /**
     * User ID and password MUST be included in the information required to connect to the data source.
     */
    AUTHENTICATED("Authenticated"),

    /**
     * The data source uses the underlying security to determine authorization.
     */
    INTEGRATED("Integrated");

    private final String value;

    AuthenticationModeEnum(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }

    public static AuthenticationModeEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        for (AuthenticationModeEnum e : AuthenticationModeEnum.values()) {
            if (e.value.equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("AuthenticationModeEnum Illegal argument ")
            .append(v).toString());
    }
}
