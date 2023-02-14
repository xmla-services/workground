/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

/**
 * The content type of the
 * property.
 * Built-in values are values listed
 * as follows. This enumeration is
 * extensible and additional values
 * can be added by users.
 */
@XmlType(name = "PropertyContentType")
@XmlEnum
public enum PropertyContentTypeEnum {

    @XmlEnumValue("0x00")
    Regular(0x00),

    @XmlEnumValue("0x001")
    Id(0x01),

    @XmlEnumValue("0x02")
    Relation_to_parent(0x02),

    @XmlEnumValue("0x03")
    Rollup_operator(0x03),

    @XmlEnumValue("0x11")
    Organization_title(0x11),

    @XmlEnumValue("0x21")
    Caption(0x21),

    @XmlEnumValue("0x22")
    Caption_short(0x22),

    @XmlEnumValue("0x23")
    Caption_description(0x23),

    @XmlEnumValue("0x24")
    Caption_abbreviation(0x24),

    @XmlEnumValue("0x31")
    Web_URL(0x31),

    @XmlEnumValue("0x32")
    Web_HTML(0x32),

    @XmlEnumValue("0x33")
    Web_XML_or_XSL(0x33),

    @XmlEnumValue("0x34")
    Web_mail_alias(0x34),

    @XmlEnumValue("0x41")
    Address(0x41),

    @XmlEnumValue("0x42")
    Address_street(0x42),

    @XmlEnumValue("0x43")
    Address_house(0x43),

    @XmlEnumValue("0x44")
    Address_city(0x44),

    @XmlEnumValue("0x45")
    Address_state_or_province(0x45),

    @XmlEnumValue("0x46")
    Address_zip(0x46),

    @XmlEnumValue("0x47")
    Address_quarter(0x47),

    @XmlEnumValue("0x48")
    Address_country(0x48),

    @XmlEnumValue("0x49")
    Address_building(0x49),

    @XmlEnumValue("0x4A")
    Address_room(0x4A),

    @XmlEnumValue("0x4B")
    Address_floor(0x4B),

    @XmlEnumValue("0x4C")
    Address_fax(0x4C),

    @XmlEnumValue("0x4D")
    Address_phone(0x4D),

    @XmlEnumValue("0x61")
    Geography_centroidx(0x61),

    @XmlEnumValue("0x62")
    Geography_centroidy(0x62),

    @XmlEnumValue("0x63")
    Geography_centroidz(0x63),

    @XmlEnumValue("0x64")
    Geography_boundary_top(0x64),

    @XmlEnumValue("0x65")
    Geography_boundary_left(0x65),

    @XmlEnumValue("0x66")
    Geography_boundary_bottom(0x66),

    @XmlEnumValue("0x67")
    Geography_boundary_right(0x67),

    @XmlEnumValue("0x68")
    Geography_boundary_front(0x68),

    @XmlEnumValue("0x69")
    Geography_boundary_rear(0x69),

    @XmlEnumValue("0x6A")
    Geography_boundary_polygon(0x6A),

    @XmlEnumValue("0x71")
    Physical_size(0x71),

    @XmlEnumValue("0x72")
    Physical_color(0x72),

    @XmlEnumValue("0x73")
    Physical_weight(0x73),

    @XmlEnumValue("0x74")
    Physical_height(0x74),

    @XmlEnumValue("0x75")
    Physical_width(0x75),

    @XmlEnumValue("0x76")
    Physical_depth(0x76),

    @XmlEnumValue("0x77")
    Physical_volume(0x77),

    @XmlEnumValue("0x78")
    Physical_density(0x78),

    @XmlEnumValue("0x82")
    Person_full_name(0x82),

    @XmlEnumValue("0x83")
    Person_first_name(0x83),

    @XmlEnumValue("0x84")
    Person_last_name(0x84),

    @XmlEnumValue("0x85")
    Person_middle_name(0x85),

    @XmlEnumValue("0x86")
    Person_demographic(0x86),

    @XmlEnumValue("0x87")
    Person_contact(0x87),

    @XmlEnumValue("0x91")
    Quantity_range_low(0x91),

    @XmlEnumValue("0x92")
    Quantity_range_high(0x92),

    @XmlEnumValue("0xA1")
    Formatting_color(0xA1),

    @XmlEnumValue("0xA2")
    Formatting_order(0xA2),

    @XmlEnumValue("0xA3")
    Formatting_font(0xA3),

    @XmlEnumValue("0xA4")
    Formatting_font_effects(0xA4),

    @XmlEnumValue("0xA5")
    Formatting_font_size(0xA5),

    @XmlEnumValue("0xA6")
    Formatting_sub_total(0xA6),

    @XmlEnumValue("0xB1")
    Date(0xB1),

    @XmlEnumValue("0xB2")
    Date_start(0xB2),

    @XmlEnumValue("0xB3")
    Date_ended(0xB3),

    @XmlEnumValue("0xB4")
    Date_canceled(0xB4),

    @XmlEnumValue("0xB5")
    Date_modified(0xB5),

    @XmlEnumValue("0xB6")
    Date_duration(0xB6),

    @XmlEnumValue("0xC1")
    Version(0xC1);

    private final int value;

    PropertyContentTypeEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static PropertyContentTypeEnum fromValue(int v) {
        for (PropertyContentTypeEnum c : PropertyContentTypeEnum.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("PropertyContentTypeEnum Illegal argument ")
            .append(v).toString());
    }
}
