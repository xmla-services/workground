package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

import java.util.EnumSet;

/**
 * The cardinality of the property.
 * Possible values include the
 * following strings:
 */
@XmlType(name = "PropertyCardinality")
@XmlEnum
public enum PropertyCardinalityEnum {

    @XmlEnumValue("ONE")
    ONE,

    @XmlEnumValue("MANY")
    MANY;

    public static PropertyCardinalityEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        return EnumSet.allOf(PropertyCardinalityEnum.class).stream().filter(e -> (e.name().equals(v))).findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                new StringBuilder("PropertyCardinalityEnum Illegal argument ").append(v)
                    .toString())
            );
    }
}
