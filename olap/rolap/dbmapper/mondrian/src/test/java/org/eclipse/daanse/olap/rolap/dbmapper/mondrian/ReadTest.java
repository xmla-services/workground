/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.mondrian;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.eclipse.daanse.olap.rolap.dbmapper.api.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReadTest {

    private static final String AGGREGATOR = "aggregator";
	private static final String FORMAT_STRING = "formatString";
	private static final String ORDINAL_COLUMN = "ordinalColumn";
	private static final String LEVEL = "level";
	private static final String HAS_ALL = "hasAll";
	private static final String HIDE_MEMBER_IF = "hideMemberIf";
	private static final String LEVEL_TYPE = "levelType";
	private static final String UNIQUE_MEMBERS = "uniqueMembers";
	private static final String COLUMN = "column";
	private static final String CAPTION = "caption";
	private static final String HIERARCHY = "hierarchy";
	private static final String TABLE = "table";
    private static final String PRIMARY_KEY_TABLE = "primaryKeyTable";
    private static final String PRIMARY_KEY = "primaryKey";
    private static final String ALL_MEMBER_NAME = "allMemberName";
    private static final String NAME = "name";
    private static final String FOREIGN_KEY = "foreignKey";
    private static final String TYPE = "type";
    private static final File FILE_STEEL_WHEEL = new File("./src/test/resources/SteelWheels.xml");
    private static final File F_FOOD_MART = new File("./src/test/resources/FoodMart.xml");

    // Hierarchy0
    private static final Map<String, Object> steelWheelLevel00 = new HashMap<>();
    static {
        steelWheelLevel00.put(NAME, "Territory");
        steelWheelLevel00.put(TABLE, null);
        steelWheelLevel00.put(COLUMN,"TERRITORY");
        steelWheelLevel00.put(ORDINAL_COLUMN,null);
        steelWheelLevel00.put(TYPE,"String");
        steelWheelLevel00.put(UNIQUE_MEMBERS,"true");
        steelWheelLevel00.put(LEVEL_TYPE,"Regular");
        steelWheelLevel00.put(HIDE_MEMBER_IF,"Never");
    }
    private static final Map<String, Object> steelWheelLevel01 = new HashMap<>();
    static {
        steelWheelLevel01.put(NAME, "Country");
        steelWheelLevel01.put(TABLE, null);
        steelWheelLevel01.put(COLUMN,"COUNTRY");
        steelWheelLevel01.put(ORDINAL_COLUMN,null);
        steelWheelLevel01.put(TYPE,"String");
        steelWheelLevel01.put(UNIQUE_MEMBERS,"false");
        steelWheelLevel01.put(LEVEL_TYPE,"Regular");
        steelWheelLevel01.put(HIDE_MEMBER_IF,"Never");
    }
    private static final Map<String, Object> steelWheelLevel02 = new HashMap<>();
    static {
        steelWheelLevel02.put(NAME, "State Province");
        steelWheelLevel02.put(TABLE, null);
        steelWheelLevel02.put(COLUMN,"STATE");
        steelWheelLevel02.put(ORDINAL_COLUMN,null);
        steelWheelLevel02.put(TYPE,"String");
        steelWheelLevel02.put(UNIQUE_MEMBERS,"true");
        steelWheelLevel02.put(LEVEL_TYPE,"Regular");
        steelWheelLevel02.put(HIDE_MEMBER_IF,"Never");
    }
    private static final Map<String, Object> steelWheelLevel03 = new HashMap<>();
    static {
        steelWheelLevel03.put(NAME, "City");
        steelWheelLevel03.put(TABLE, null);
        steelWheelLevel03.put(COLUMN,"CITY");
        steelWheelLevel03.put(ORDINAL_COLUMN,null);
        steelWheelLevel03.put(TYPE,"String");
        steelWheelLevel03.put(UNIQUE_MEMBERS,"true");
        steelWheelLevel03.put(LEVEL_TYPE,"Regular");
        steelWheelLevel03.put(HIDE_MEMBER_IF,"Never");
    }
    private static final List<Map<String, Object>> steelWheelLevelList0 = List.of(
        steelWheelLevel00,
        steelWheelLevel01,
        steelWheelLevel02,
        steelWheelLevel03
    );
    private static final Map<String, Object> steelWheelHierarchy0 = new HashMap<>();
    static {
        steelWheelHierarchy0.put(ALL_MEMBER_NAME, "All Markets");
        steelWheelHierarchy0.put(PRIMARY_KEY, "CUSTOMERNUMBER");
        steelWheelHierarchy0.put(PRIMARY_KEY_TABLE, "");
        steelWheelHierarchy0.put(CAPTION, null);
        steelWheelHierarchy0.put(TABLE, "customer_w_ter");
        steelWheelHierarchy0.put(NAME, null);
        steelWheelHierarchy0.put(HAS_ALL, "true");
        steelWheelHierarchy0.put(LEVEL, steelWheelLevelList0);
    }
    private static final List<Map<String, Object>> steelWheelHierarchyList0 = List.of(
        steelWheelHierarchy0
    );
    // Hierarchy1
    private static final Map<String, Object> steelWheelLevel10 = new HashMap<>();
    static {
        steelWheelLevel10.put(NAME, "Customer");
        steelWheelLevel10.put(COLUMN,"CUSTOMERNAME");
        steelWheelLevel10.put(ORDINAL_COLUMN,null);
        steelWheelLevel10.put(TYPE,"String");
        steelWheelLevel10.put(UNIQUE_MEMBERS,"true");
        steelWheelLevel10.put(LEVEL_TYPE,"Regular");
        steelWheelLevel10.put(HIDE_MEMBER_IF,"Never");
    }
    private static final List<Map<String, Object>> steelWheelLevelList1 = List.of(
        steelWheelLevel10
    );
    private static final Map<String, Object> steelWheelHierarchy1 = new HashMap<>();
    static {
        steelWheelHierarchy1.put(ALL_MEMBER_NAME, "All Customers");
        steelWheelHierarchy1.put(PRIMARY_KEY, "CUSTOMERNUMBER");
        steelWheelHierarchy1.put(PRIMARY_KEY_TABLE, null);
        steelWheelHierarchy1.put(CAPTION, null);
        steelWheelHierarchy1.put(TABLE, "customer_w_ter");
        steelWheelHierarchy1.put(NAME, null);
        steelWheelHierarchy1.put(HAS_ALL, "true");
        steelWheelHierarchy1.put(LEVEL, steelWheelLevelList1);
    }
    private static final List<Map<String, Object>> steelWheelHierarchyList1 = List.of(
        steelWheelHierarchy1
    );

    // Hierarchy2
    private static final Map<String, Object> steelWheelLevel20 = new HashMap<>();
    static {
        steelWheelLevel20.put(NAME, "Line");
        steelWheelLevel20.put(TABLE, "products");
        steelWheelLevel20.put(COLUMN,"PRODUCTLINE");
        steelWheelLevel20.put(ORDINAL_COLUMN,null);
        steelWheelLevel20.put(TYPE,"String");
        steelWheelLevel20.put(UNIQUE_MEMBERS,"false");
        steelWheelLevel20.put(LEVEL_TYPE,"Regular");
        steelWheelLevel20.put(HIDE_MEMBER_IF,"Never");
    }
    private static final Map<String, Object> steelWheelLevel21 = new HashMap<>();
    static {
        steelWheelLevel21.put(NAME, "Vendor");
        steelWheelLevel21.put(TABLE, "products");
        steelWheelLevel21.put(COLUMN,"PRODUCTVENDOR");
        steelWheelLevel21.put(ORDINAL_COLUMN,null);
        steelWheelLevel21.put(TYPE,"String");
        steelWheelLevel21.put(UNIQUE_MEMBERS,"false");
        steelWheelLevel21.put(LEVEL_TYPE,"Regular");
        steelWheelLevel21.put(HIDE_MEMBER_IF,"Never");
    }
    private static final Map<String, Object> steelWheelLevel22 = new HashMap<>();
    static {
        steelWheelLevel22.put(NAME, "Product");
        steelWheelLevel22.put(TABLE, "products");
        steelWheelLevel22.put(COLUMN,"PRODUCTNAME");
        steelWheelLevel22.put(ORDINAL_COLUMN,null);
        steelWheelLevel22.put(TYPE,"String");
        steelWheelLevel22.put(UNIQUE_MEMBERS,"true");
        steelWheelLevel22.put(LEVEL_TYPE,"Regular");
        steelWheelLevel22.put(HIDE_MEMBER_IF,"Never");
    }
    private static final List<Map<String, Object>> steelWheelLevelList2 = List.of(
        steelWheelLevel20,
        steelWheelLevel21,
        steelWheelLevel22
    );

    private static final Map<String, Object> steelWheelHierarchy2 = new HashMap<>();
    static {
        steelWheelHierarchy2.put(ALL_MEMBER_NAME, "All Products");
        steelWheelHierarchy2.put(PRIMARY_KEY, "PRODUCTCODE");
        steelWheelHierarchy2.put(PRIMARY_KEY_TABLE, "products");
        steelWheelHierarchy2.put(CAPTION, "");
        steelWheelHierarchy2.put(TABLE, "products");
        steelWheelHierarchy2.put(NAME, "");
        steelWheelHierarchy2.put(HAS_ALL, "true");
        steelWheelHierarchy2.put(LEVEL, steelWheelLevelList2);
    }
    private static final List<Map<String, Object>> steelWheelHierarchyList2 = List.of(
        steelWheelHierarchy2
    );

    // Hierarchy3
    private static final Map<String, Object> steelWheelLevel30 = new HashMap<>();
    static {
        steelWheelLevel30.put(NAME, "Years");
        steelWheelLevel30.put(TABLE, null);
        steelWheelLevel30.put(COLUMN,"YEAR_ID");
        steelWheelLevel30.put(ORDINAL_COLUMN,null);
        steelWheelLevel30.put(TYPE,"Integer");
        steelWheelLevel30.put(UNIQUE_MEMBERS,"true");
        steelWheelLevel30.put(LEVEL_TYPE,"TimeYears");
        steelWheelLevel30.put(HIDE_MEMBER_IF,"Never");
    }
    private static final Map<String, Object> steelWheelLevel31 = new HashMap<>();
    static {
        steelWheelLevel31.put(NAME, "Quarters");
        steelWheelLevel31.put(TABLE, null);
        steelWheelLevel31.put(COLUMN,"QTR_NAME");
        steelWheelLevel31.put(ORDINAL_COLUMN,"QTR_ID");
        steelWheelLevel31.put(TYPE,"String");
        steelWheelLevel31.put(UNIQUE_MEMBERS,"false");
        steelWheelLevel31.put(LEVEL_TYPE,"TimeQuarters");
        steelWheelLevel31.put(HIDE_MEMBER_IF,"Never");
    }
    private static final Map<String, Object> steelWheelLevel32 = new HashMap<>();
    static {
        steelWheelLevel32.put(NAME, "Months");
        steelWheelLevel32.put(TABLE, null);
        steelWheelLevel32.put(COLUMN,"MONTH_NAME");
        steelWheelLevel32.put(ORDINAL_COLUMN,"MONTH_ID");
        steelWheelLevel32.put(TYPE,"String");
        steelWheelLevel32.put(UNIQUE_MEMBERS,"false");
        steelWheelLevel32.put(LEVEL_TYPE,"TimeMonths");
        steelWheelLevel32.put(HIDE_MEMBER_IF,"Never");
    }
    private static final List<Map<String, Object>> steelWheelLevelList3 = List.of(
        steelWheelLevel30,
        steelWheelLevel31,
        steelWheelLevel32
    );
    private static final Map<String, Object> steelWheelHierarchy3 = new HashMap<>();
    static {
        steelWheelHierarchy3.put(ALL_MEMBER_NAME, "All Years");
        steelWheelHierarchy3.put(PRIMARY_KEY, "TIME_ID");
        steelWheelHierarchy3.put(PRIMARY_KEY_TABLE, null);
        steelWheelHierarchy3.put(CAPTION, null);
        steelWheelHierarchy3.put(TABLE, "time");
        steelWheelHierarchy3.put(NAME, null);
        steelWheelHierarchy3.put(HAS_ALL, "true");
        steelWheelHierarchy3.put(LEVEL, steelWheelLevelList3);
    }
    private static final List<Map<String, Object>> steelWheelHierarchyList3 = List.of(
        steelWheelHierarchy3
    );

    // Hierarchy4
    private static final Map<String, Object> steelWheelLevel40 = new HashMap<>();
    static {
        steelWheelLevel40.put(NAME, "Type");
        steelWheelLevel40.put(TABLE, null);
        steelWheelLevel40.put(COLUMN,"STATUS");
        steelWheelLevel40.put(ORDINAL_COLUMN,null);
        steelWheelLevel40.put(TYPE,"String");
        steelWheelLevel40.put(UNIQUE_MEMBERS,"true");
        steelWheelLevel40.put(LEVEL_TYPE,"Regular");
        steelWheelLevel40.put(HIDE_MEMBER_IF,"Never");
    }
    private static final List<Map<String, Object>> steelWheelLevelList4 = List.of(
        steelWheelLevel40
    );
    private static final Map<String, Object> steelWheelHierarchy4 = new HashMap<>();
    static {
        steelWheelHierarchy4.put(ALL_MEMBER_NAME, "All Status Types");
        steelWheelHierarchy4.put(PRIMARY_KEY, "STATUS");
        steelWheelHierarchy4.put(PRIMARY_KEY_TABLE, null);
        steelWheelHierarchy4.put(CAPTION, null);
        steelWheelHierarchy4.put(TABLE, null);
        steelWheelHierarchy4.put(NAME, null);
        steelWheelHierarchy4.put(HAS_ALL, "true");
        steelWheelHierarchy4.put(LEVEL, steelWheelLevelList4);
    }

    private static final List<Map<String, Object>> steelWheelHierarchyList4 = List.of(
        steelWheelHierarchy4
    );

    // Dimension0
    private static final Map<String, Object> steelWheelDimension0 = new HashMap<>();
    static {
        steelWheelDimension0.put(TYPE, "Standard");
        steelWheelDimension0.put(FOREIGN_KEY, "CUSTOMERNUMBER");
        steelWheelDimension0.put(NAME, "Markets");
        steelWheelDimension0.put(HIERARCHY, steelWheelHierarchyList0);
    }

    // Dimension1
    private static final Map<String, Object> steelWheelDimension1 = new HashMap<>();
    static {
        steelWheelDimension1.put(TYPE, "Standard");
        steelWheelDimension1.put(FOREIGN_KEY, "CUSTOMERNUMBER");
        steelWheelDimension1.put(NAME, "Customers");
        steelWheelDimension1.put(HIERARCHY, steelWheelHierarchyList1);
    }

    // Dimension2
    private static final Map<String, Object> steelWheelDimension2 = new HashMap<>();
    static {
        steelWheelDimension2.put(TYPE, "Standard");
        steelWheelDimension2.put(FOREIGN_KEY, "PRODUCTCODE");
        steelWheelDimension2.put(NAME, "Product");
        steelWheelDimension2.put(HIERARCHY, steelWheelHierarchyList2);
    }

    // Dimension3
    private static final Map<String, Object> steelWheelDimension3 = new HashMap<>();
    static {
        steelWheelDimension3.put(TYPE, "TimeDimension");
        steelWheelDimension3.put(FOREIGN_KEY, "TIME_ID");
        steelWheelDimension3.put(NAME, "Time");
        steelWheelDimension3.put(HIERARCHY, steelWheelHierarchyList3);
    }

    // Dimension4
    private static final Map<String, Object> steelWheelDimension4 = new HashMap<>();
    static {
        steelWheelDimension4.put(TYPE, "Standard");
        steelWheelDimension4.put(FOREIGN_KEY, "STATUS");
        steelWheelDimension4.put(NAME, "Order Status");
        steelWheelDimension4.put(HIERARCHY, steelWheelHierarchyList4);
    }

    private static final List<Map<String, Object>> steelWheelDimensionList = List.of(
        steelWheelDimension0,
        steelWheelDimension1,
        steelWheelDimension2,
        steelWheelDimension3,
        steelWheelDimension4
    );

    private static final Map<String, Object> steelWheelMeasure0 = new HashMap<>();
    static {
        steelWheelMeasure0.put(NAME, "Quantity");
        steelWheelMeasure0.put(COLUMN, "QUANTITYORDERED");
        steelWheelMeasure0.put(FORMAT_STRING, "#,###");
        steelWheelMeasure0.put(AGGREGATOR, "sum");
    }
    private static final Map<String, Object> steelWheelMeasure1 = new HashMap<>();
    static {
        steelWheelMeasure1.put(NAME, "Sales");
        steelWheelMeasure1.put(COLUMN, "TOTALPRICE");
        steelWheelMeasure1.put(FORMAT_STRING, "#,###");
        steelWheelMeasure1.put(AGGREGATOR, "sum");
    }

    private static final List<Map<String, Object>> steelWheelMeasureList = List.of(
        steelWheelMeasure0,
        steelWheelMeasure1
    );

    @Test
    void test_SteelWheel() throws Exception {
        Schema schema = extracted(FILE_STEEL_WHEEL);
        assertThat(schema).isNotNull();
        assertEquals("SteelWheels", schema.name());
        assertThat(schema.cube()).isNotNull();
        assertEquals(schema.cube().size(), 1);

        Cube cube = schema.cube().get(0);
        assertThat(cube).isNotNull();
        assertEquals("SteelWheelsSales", cube.name());
        assertNull(cube.annotations());
        assertNotNull(cube.calculatedMember());
        assertNull(cube.caption());
        assertNull(cube.defaultMeasure());
        assertNull(cube.description());
        assertNotNull(cube.drillThroughAction());
        assertNotNull(cube.namedSet());
        assertNull(cube.view());
        assertNotNull(cube.writebackTable());
        assertTrue(cube.cache());
        assertTrue(cube.enabled());
        assertNotNull(cube.measure());
        checkMeasure(cube.measure(), steelWheelMeasureList);

        Table table = cube.table();
        assertThat(table).isNotNull();
        assertEquals("orderfact", table.name());
        assertNotNull(table.aggExclude());
        assertNotNull(table.aggTable());
        assertNull(table.alias());
        assertNotNull(table.hint());
        assertNull(table.schema());
        assertNull(table.sql());

        List<? extends Object> dimensions = cube.dimensionUsageOrDimension();
        assertThat(dimensions).isNotNull();
        assertEquals(dimensions.size(), steelWheelDimensionList.size());
        for (int i = 0; i < dimensions.size(); i++) {
            checkDimension(dimensions.get(i), steelWheelDimensionList.get(i));
        }

        List<? extends Object> measuries = cube.measure();
        assertThat(measuries).isNotNull();
        assertEquals(measuries.size(), 2);

    }

    @Test
    void test_Foodmart() throws Exception {

        Schema schema = extracted(F_FOOD_MART);
        assertThat(schema).isNotNull();
    }

    private static Schema extracted(File file) throws JAXBException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SchemaImpl.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (SchemaImpl) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void checkDimension(Object object, Map<String, Object> map) {
        assertTrue(object instanceof PrivateDimension);
        PrivateDimension dimension = (PrivateDimension) object;
        assertEquals(map.get(NAME), dimension.name());
        assertEquals(map.get(FOREIGN_KEY), dimension.foreignKey());
        assertEquals(map.get(TYPE), dimension.type());
        dimension.annotations();
        dimension.caption();
        dimension.description();
        checkHierarchy(dimension.hierarchy(), (List)map.get(HIERARCHY));
    }

    private void checkHierarchy(List<? extends Hierarchy> hierarchy, List<Map<String, Object>> listHierarchy) {
        assertThat(hierarchy).isNotNull();
        assertEquals(hierarchy.size(), listHierarchy.size());
        for (int i = 0; i < hierarchy.size(); i++) {
            checkHierarchyItem(hierarchy.get(i), listHierarchy.get(i));
        }
    }

    private void checkHierarchyItem(Hierarchy hierarchy, Map<String, Object> map) {
        assertNull(hierarchy.annotations());
        if (map.get(TABLE) == null) {
            assertNull(hierarchy.table());
        } else {
            assertEquals(map.get(TABLE), hierarchy.table().name());
        }
        assertNull(hierarchy.view());
        assertNull(hierarchy.join());
        assertNull(hierarchy.inlineTable());
        assertNotNull(hierarchy.level());
        checkLevel(hierarchy.level(), (List)map.get(LEVEL));
        assertNotNull(hierarchy.memberReaderParameter());
        assertEquals(map.get(NAME), hierarchy.name());
        assertEquals(Boolean.valueOf((String)map.get(HAS_ALL)), hierarchy.hasAll());
        assertEquals(map.get(ALL_MEMBER_NAME), hierarchy.allMemberName());
        assertNull(hierarchy.allMemberCaption());
        assertNull(hierarchy.allLevelName());
        assertEquals(map.get(PRIMARY_KEY), hierarchy.primaryKey());
        assertEquals(map.get(PRIMARY_KEY_TABLE), hierarchy.primaryKeyTable());
        assertNull(hierarchy.defaultMember());
        assertNull(hierarchy.memberReaderClass());
        assertEquals(map.get(CAPTION), hierarchy.caption());
        assertNull(hierarchy.description());
        assertNull(hierarchy.uniqueKeyLevelName());
    }

    private void checkLevel(List<? extends Level> level, List<Map<String, Object>> list) {
        assertThat(level).isNotNull();
        assertEquals(level.size(), list.size());
        for (int i = 0; i < level.size(); i++) {
            checkLevelItem(level.get(i), list.get(i));
        }
    }

    private void checkLevelItem(Level level, Map<String, Object> map) {
        assertNull(level.annotations());
        assertNull(level.keyExpression());
        assertNull(level.nameExpression());
        assertNull(level.captionExpression());
        assertNull(level.ordinalExpression());
        assertNull(level.parentExpression());
        assertNull(level.closure());
        assertNotNull(level.property());
        assertNull(level.approxRowCount());
        assertEquals(map.get(NAME), level.name());
        assertEquals(map.get(TABLE), level.table());
        assertEquals(map.get(COLUMN), level.column());
        assertNull(level.nameColumn());
        assertEquals(map.get(ORDINAL_COLUMN), level.ordinalColumn());
        assertNull(level.parentColumn());
        assertNull(level.nullParentValue());
        assertEquals(map.get(TYPE), level.type());
        assertEquals(Boolean.valueOf((String) map.get(UNIQUE_MEMBERS)), level.uniqueMembers());
        assertEquals(map.get(LEVEL_TYPE), level.levelType());
        assertEquals(map.get(HIDE_MEMBER_IF), level.hideMemberIf());
        assertNull(level.formatter());
        assertNull(level.caption());
        assertNull(level.description());
        assertNull(level.captionColumn());
    }

    private void checkMeasure(List<? extends Measure> measureList, List<Map<String, Object>> list) {
        assertThat(measureList).isNotNull();
        assertEquals(measureList.size(), list.size());
        for (int i = 0; i < measureList.size(); i++) {
            checkMeasureItem(measureList.get(i), list.get(i));
        }
    }

    private void checkMeasureItem(Measure measure, Map<String, Object> map) {
        assertNull(measure.annotations());
        assertNull(measure.measureExpression());
        assertNotNull(measure.calculatedMemberProperty());
        assertEquals(map.get(NAME), measure.name());
        assertEquals(map.get(COLUMN), measure.column());
        assertNull(measure.datatype());
        assertEquals(map.get(FORMAT_STRING), measure.formatString());
        assertEquals(map.get(AGGREGATOR), measure.aggregator());
        assertNull(measure.formatter());
        assertNull(measure.caption());
        assertNull(measure.description());
        assertTrue(measure.visible());
        assertNull(measure.displayFolder());
    }
}
