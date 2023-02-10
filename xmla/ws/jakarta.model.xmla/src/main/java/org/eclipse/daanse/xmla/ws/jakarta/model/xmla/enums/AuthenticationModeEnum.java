package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "AuthenticationMode")
@XmlEnum
public enum AuthenticationModeEnum {

    /**
     * No user ID or password has to be sent.
     */
    @XmlEnumValue("Unauthenticated")
    Unauthenticated,

    /**
     * User ID and password MUST be included in the information required to connect to the data source.
     */
    @XmlEnumValue("Authenticated")
    Authenticated,

    /**
     * The data source uses the underlying security to determine authorization.
     */
    @XmlEnumValue("Integrated")
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
