package org.eclipse.daanse.xmla.api.common.enums;

public enum AuthenticationModeEnum {

    Unauthenticated, // No user ID or password has to be sent.
    Authenticated, // User ID and password MUST be included in the information required to connect to the data source.
    Integrated; // The data source uses the underlying security to determine authorization.

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
