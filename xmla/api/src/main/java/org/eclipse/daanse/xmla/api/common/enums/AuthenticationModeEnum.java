package org.eclipse.daanse.xmla.api.common.enums;

public enum AuthenticationModeEnum {

    /**
     * No user ID or password has to be sent.
     */
    Unauthenticated,
    /**
     * User ID and password MUST be included in the information required to connect to the data source.
     */
    Authenticated,
    /**
     * The data source uses the underlying security to determine authorization.
     */
    Integrated;

    public static AuthenticationModeEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        for (AuthenticationModeEnum e : AuthenticationModeEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("AuthenticationModeEnum Illegal argument ")
            .append(v).toString());
    }
}
