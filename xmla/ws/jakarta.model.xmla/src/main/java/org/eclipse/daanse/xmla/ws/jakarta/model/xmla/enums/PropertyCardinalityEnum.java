package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 *The cardinality of the property.
 *Possible values include the
 *following strings:
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
        for (PropertyCardinalityEnum e : PropertyCardinalityEnum.values()) {
            if (e.name().equals(v)) {
                return e;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("PropertyCardinalityEnum Illegal argument ")
            .append(v).toString());
    }
}
