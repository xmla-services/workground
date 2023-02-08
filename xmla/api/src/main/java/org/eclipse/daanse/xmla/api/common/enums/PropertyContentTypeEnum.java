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
package org.eclipse.daanse.xmla.api.common.enums;

/**
 * The content type of the
 * property.
 * Built-in values are values listed
 * as follows. This enumeration is
 * extensible and additional values
 * can be added by users.
 */
public enum PropertyContentTypeEnum {

     Regular(0x00),
     Id(0x01),
     Relation_to_parent(0x02),
     Rollup_operator(0x03),
     Organization_title(0x11),
     Caption(0x21),
     Caption_short(0x22),
     Caption_description(0x23),
     Caption_abbreviation(0x24),
     Web_URL(0x31),
     Web_HTML(0x32),
     Web_XML_or_XSL(0x33),
     Web_mail_alias(0x34),
     Address(0x41),
     Address_street(0x42),
     Address_house(0x43),
     Address_city(0x44),
     Address_state_or_province(0x45),
     Address_zip(0x46),
     Address_quarter(0x47),
     Address_country(0x48),
     Address_building(0x49),
     Address_room(0x4A),
     Address_floor(0x4B),
     Address_fax(0x4C),
     Address_phone(0x4D),
     Geography_centroidx(0x61),
     Geography_centroidy(0x62),
     Geography_centroidz(0x63),
     Geography_boundary_top(0x64),
     Geography_boundary_left(0x65),
     Geography_boundary_bottom(0x66),
     Geography_boundary_right(0x67),
     Geography_boundary_front(0x68),
     Geography_boundary_rear(0x69),
     Geography_boundary_polygon(0x6A),
     Physical_size(0x71),
     Physical_color(0x72),
     Physical_weight(0x73),
     Physical_height(0x74),
     Physical_width(0x75),
     Physical_depth(0x76),
     Physical_volume(0x77),
     Physical_density(0x78),
     Person_full_name(0x82),
     Person_first_name(0x83),
     Person_last_name(0x84),
     Person_middle_name(0x85),
     Person_demographic(0x86),
     Person_contact(0x87),
     Quantity_range_low(0x91),
     Quantity_range_high(0x92),
     Formatting_color(0xA1),
     Formatting_order(0xA2),
     Formatting_font(0xA3),
     Formatting_font_effects(0xA4),
     Formatting_font_size(0xA5),
     Formatting_sub_total(0xA6),
     Date(0xB1),
     Date_start(0xB2),
     Date_ended(0xB3),
     Date_canceled(0xB4),
     Date_modified(0xB5),
     Date_duration(0xB6),
     Version(0xC1);

    private final int value;

    PropertyContentTypeEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static PropertyContentTypeEnum fromValue(String v) {
        if (v == null) {
            return null;
        }
        int vi = Integer.decode(v);
        for (PropertyContentTypeEnum c : PropertyContentTypeEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("HierarchyOriginEnum Illegal argument ")
            .append(v).toString());
    }
}
