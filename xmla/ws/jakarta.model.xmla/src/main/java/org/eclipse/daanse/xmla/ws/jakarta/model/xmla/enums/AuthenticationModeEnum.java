package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

import java.util.stream.Stream;

@XmlType(name = "AuthenticationMode")
@XmlEnum
public enum AuthenticationModeEnum {

    /**
     * No user ID or password has to be sent.
     */
    @XmlEnumValue("Unauthenticated")
    UNAUTHENTICATED("Unauthenticated"),

    /**
     * User ID and password MUST be included in the information required to connect to the data source.
     */
    @XmlEnumValue("Authenticated")
    AUTHENTICATED("Authenticated"),

    /**
     * The data source uses the underlying security to determine authorization.
     */
    @XmlEnumValue("Integrated")
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
        return Stream.of(AuthenticationModeEnum.values()).filter(e -> (e.value.equals(v))).findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                new StringBuilder("AuthenticationModeEnum Illegal argument ").append(v)
                    .toString())
            );
    }
}
