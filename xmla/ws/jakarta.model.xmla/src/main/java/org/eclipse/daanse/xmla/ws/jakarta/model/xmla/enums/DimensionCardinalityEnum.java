package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

import java.util.EnumSet;

/**
 * The number of instances a dimension
 * member can have for a single instance of a
 * measure group measure
 */
@XmlType(name = "DimensionCardinality")
@XmlEnum
public enum DimensionCardinalityEnum {

    @XmlEnumValue("ONE")
    ONE,

    @XmlEnumValue("MANY")
    MANY;

    public static DimensionCardinalityEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        return EnumSet.allOf(DimensionCardinalityEnum.class).stream().filter(e -> (e.name().equals(v))).findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                new StringBuilder("DimensionCardinalityEnum Illegal argument ").append(v)
                    .toString())
            );
    }
}
