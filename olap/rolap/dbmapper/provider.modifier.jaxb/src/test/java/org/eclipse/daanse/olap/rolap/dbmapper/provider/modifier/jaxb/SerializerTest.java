package org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier.jaxb;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.SchemaImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.foodmart.record.FoodMartRecordDbMappingSchemaProvider;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class SerializerTest {

    private String xml = """
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Schema name="FoodMart">
    <Annotations/>
    <Dimension name="Store" type="StandardDimension" highCardinality="false" visible="true">
        <Annotations/>
        <Hierarchy hasAll="true" primaryKey="store_id" visible="true">
            <Table name="store"/>
            <Level name="Store Country" column="store_country" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="Store State" column="store_state" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="Store City" column="store_city" type="String" uniqueMembers="false" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="Store Name" column="store_name" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
                <Property name="Store Type" column="store_type" type="String" dependsOnLevelValue="false"/>
                <Property name="Store Manager" column="store_manager" type="String" dependsOnLevelValue="false"/>
                <Property name="Store Sqft" column="store_sqft" type="Numeric" dependsOnLevelValue="false"/>
                <Property name="Grocery Sqft" column="grocery_sqft" type="Numeric" dependsOnLevelValue="false"/>
                <Property name="Frozen Sqft" column="frozen_sqft" type="Numeric" dependsOnLevelValue="false"/>
                <Property name="Meat Sqft" column="meat_sqft" type="Numeric" dependsOnLevelValue="false"/>
                <Property name="Has coffee bar" column="coffee_bar" type="Boolean" dependsOnLevelValue="false"/>
                <Property name="Street Address" column="store_street_address" type="String" dependsOnLevelValue="false"/>
            </Level>
            <Annotations/>
        </Hierarchy>
    </Dimension>
    <Dimension name="Store Size in SQFT" type="StandardDimension" highCardinality="false" visible="true">
        <Annotations/>
        <Hierarchy hasAll="true" primaryKey="store_id" visible="true">
            <Table name="store"/>
            <Level name="Store Sqft" column="store_sqft" type="Numeric" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Annotations/>
        </Hierarchy>
    </Dimension>
    <Dimension name="Store Type" type="StandardDimension" highCardinality="false" visible="true">
        <Annotations/>
        <Hierarchy hasAll="true" primaryKey="store_id" visible="true">
            <Table name="store"/>
            <Level name="Store Type" column="store_type" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Annotations/>
        </Hierarchy>
    </Dimension>
    <Dimension name="Time" type="TimeDimension" highCardinality="false" visible="true">
        <Annotations/>
        <Hierarchy hasAll="false" primaryKey="time_id" visible="true">
            <Table name="time_by_day"/>
            <Level name="Year" column="the_year" type="Numeric" uniqueMembers="true" levelType="TimeYears" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="Quarter" column="quarter" type="String" uniqueMembers="false" levelType="TimeQuarters" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="Month" column="month_of_year" type="Numeric" uniqueMembers="false" levelType="TimeMonths" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Annotations/>
        </Hierarchy>
        <Hierarchy name="Weekly" hasAll="true" primaryKey="time_id" visible="true">
            <Table name="time_by_day"/>
            <Level name="Year" column="the_year" type="Numeric" uniqueMembers="true" levelType="TimeYears" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="Week" column="week_of_year" type="Numeric" uniqueMembers="false" levelType="TimeWeeks" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="Day" column="day_of_month" type="Numeric" uniqueMembers="false" levelType="TimeDays" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Annotations/>
        </Hierarchy>
    </Dimension>
    <Dimension name="Product" type="StandardDimension" highCardinality="false" visible="true">
        <Annotations/>
        <Hierarchy hasAll="true" primaryKey="product_id" primaryKeyTable="product" visible="true">
            <Join leftKey="product_class_id" rightKey="product_class_id">
                <Table name="product"/>
                <Table name="product_class"/>
            </Join>
            <Level name="Product Family" table="product_class" column="product_family" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="Product Department" table="product_class" column="product_department" type="String" uniqueMembers="false" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="Product Category" table="product_class" column="product_category" type="String" uniqueMembers="false" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="Product Subcategory" table="product_class" column="product_subcategory" type="String" uniqueMembers="false" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="Brand Name" table="product" column="brand_name" type="String" uniqueMembers="false" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="Product Name" table="product" column="product_name" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Annotations/>
        </Hierarchy>
    </Dimension>
    <Dimension name="Warehouse" type="StandardDimension" highCardinality="false" visible="true">
        <Annotations/>
        <Hierarchy hasAll="true" primaryKey="warehouse_id" visible="true">
            <Table name="warehouse"/>
            <Level name="Country" column="warehouse_country" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="State Province" column="warehouse_state_province" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="City" column="warehouse_city" type="String" uniqueMembers="false" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Level name="Warehouse Name" column="warehouse_name" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                <Annotations/>
            </Level>
            <Annotations/>
        </Hierarchy>
    </Dimension>
    <Cube name="Sales" defaultMeasure="Unit Sales" cache="true" enabled="true" visible="true">
        <Table name="sales_fact_1997">
            <AggExclude name="agg_c_special_sales_fact_1997" ignorecase="true"/>
            <AggExclude name="agg_lc_100_sales_fact_1997" ignorecase="true"/>
            <AggExclude name="agg_lc_10_sales_fact_1997" ignorecase="true"/>
            <AggExclude name="agg_pc_10_sales_fact_1997" ignorecase="true"/>
            <AggName name="agg_c_special_sales_fact_1997" ignorecase="true">
                <AggFactCount column="FACT_COUNT"/>
                <AggIgnoreColumn column="foo"/>
                <AggIgnoreColumn column="bar"/>
                <AggForeignKey factColumn="product_id" aggColumn="PRODUCT_ID"/>
                <AggForeignKey factColumn="customer_id" aggColumn="CUSTOMER_ID"/>
                <AggForeignKey factColumn="promotion_id" aggColumn="PROMOTION_ID"/>
                <AggForeignKey factColumn="store_id" aggColumn="STORE_ID"/>
                <AggMeasure column="UNIT_SALES_SUM" name="[Measures].[Unit Sales]"/>
                <AggMeasure column="STORE_COST_SUM" name="[Measures].[Store Cost]"/>
                <AggMeasure column="STORE_SALES_SUM" name="[Measures].[Store Sales]"/>
                <AggLevel column="TIME_YEAR" name="[Time].[Year]" ordinalColumn="TIME_YEAR" collapsed="true"/>
                <AggLevel column="TIME_QUARTER" name="[Time].[Quarter]" ordinalColumn="TIME_QUARTER" collapsed="true"/>
                <AggLevel column="TIME_MONTH" name="[Time].[Month]" ordinalColumn="TIME_MONTH" collapsed="true"/>
            </AggName>
        </Table>
        <DimensionUsage name="Store" source="Store" foreignKey="store_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <DimensionUsage name="Store Size in SQFT" source="Store Size in SQFT" foreignKey="store_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <DimensionUsage name="Store Type" source="Store Type" foreignKey="store_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <DimensionUsage name="Time" source="Time" foreignKey="time_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <DimensionUsage name="Product" source="Product" foreignKey="product_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <Dimension name="Promotion Media" type="StandardDimension" foreignKey="promotion_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" allMemberName="All Media" primaryKey="promotion_id" defaultMember="All Media" visible="true">
                <Table name="promotion"/>
                <Level name="Media Type" column="media_type" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Promotions" type="StandardDimension" foreignKey="promotion_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" allMemberName="All Promotions" primaryKey="promotion_id" defaultMember="[All Promotions]" visible="true">
                <Table name="promotion"/>
                <Level name="Promotion Name" column="promotion_name" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Customers" type="StandardDimension" foreignKey="customer_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" allMemberName="All Customers" primaryKey="customer_id" visible="true">
                <Table name="customer"/>
                <Level name="Country" column="country" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="State Province" column="state_province" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="City" column="city" type="String" uniqueMembers="false" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="Name" column="customer_id" type="Numeric" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                    <NameExpression>
                        <SQL dialect="oracle">"fname" || ' ' || "lname"</SQL>
                        <SQL dialect="hive">`customer`.`fullname`</SQL>
                        <SQL dialect="hsqldb">"fname" || ' ' || "lname"</SQL>
                        <SQL dialect="access">fname + ' ' + lname</SQL>
                        <SQL dialect="postgres">"fname" || ' ' || "lname"</SQL>
                        <SQL dialect="mysql">CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)</SQL>
                        <SQL dialect="mariadb">CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)</SQL>
                        <SQL dialect="mssql">fname + ' ' + lname</SQL>
                        <SQL dialect="derby">"customer"."fullname"</SQL>
                        <SQL dialect="db2">CONCAT(CONCAT("customer"."fname", ' '), "customer"."lname")</SQL>
                        <SQL dialect="luciddb">"fname" || ' ' || "lname"</SQL>
                        <SQL dialect="neoview">"customer"."fullname"</SQL>
                        <SQL dialect="teradata">"fname" || ' ' || "lname"</SQL>
                        <SQL dialect="snowflake">"customer"."fullname"</SQL>
                        <SQL dialect="generic">fullname</SQL>
                    </NameExpression>
                    <OrdinalExpression>
                        <SQL dialect="oracle">"fname" || ' ' || "lname"</SQL>
                        <SQL dialect="hsqldb">"fname" || ' ' || "lname"</SQL>
                        <SQL dialect="access">fname + ' ' + lname</SQL>
                        <SQL dialect="postgres">"fname" || ' ' || "lname"</SQL>
                        <SQL dialect="mysql">CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)</SQL>
                        <SQL dialect="mariadb">CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)</SQL>
                        <SQL dialect="mssql">fname + ' ' + lname</SQL>
                        <SQL dialect="neoview">"customer"."fullname"</SQL>
                        <SQL dialect="derby">"customer"."fullname"</SQL>
                        <SQL dialect="db2">CONCAT(CONCAT("customer"."fname", ' '), "customer"."lname")</SQL>
                        <SQL dialect="luciddb">"fname" || ' ' || "lname"</SQL>
                        <SQL dialect="snowflake">"customer"."fullname"</SQL>
                        <SQL dialect="generic">fullname</SQL>
                    </OrdinalExpression>
                    <Property name="Gender" column="gender" type="String" dependsOnLevelValue="false"/>
                    <Property name="Marital Status" column="marital_status" type="String" dependsOnLevelValue="false"/>
                    <Property name="Education" column="education" type="String" dependsOnLevelValue="false"/>
                    <Property name="Yearly Income" column="yearly_income" type="String" dependsOnLevelValue="false"/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Education Level" type="StandardDimension" foreignKey="customer_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" primaryKey="customer_id" visible="true">
                <Table name="customer"/>
                <Level name="Education Level" column="education" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Gender" type="StandardDimension" foreignKey="customer_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" allMemberName="All Gender" primaryKey="customer_id" visible="true">
                <Table name="customer"/>
                <Level name="Gender" column="gender" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Marital Status" type="StandardDimension" foreignKey="customer_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" allMemberName="All Merital Status" primaryKey="customer_id" visible="true">
                <Table name="customer"/>
                <Level approxRowCount="111" name="Marital Status" column="marital_status" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Yearly Income" type="StandardDimension" foreignKey="customer_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" primaryKey="customer_id" visible="true">
                <Table name="customer"/>
                <Level name="Yearly Income" column="yearly_income" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Measure name="Unit Sales" column="unit_sales" formatString="Standard" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Store Cost" column="store_cost" formatString="#,###.00" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Store Sales" column="store_sales" formatString="#,###.00" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Sales Count" column="product_id" formatString="#,###" aggregator="count" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Customer Count" column="customer_id" formatString="#,###" aggregator="distinct-count" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Promotion Sales" formatString="#,###.00" aggregator="sum" visible="true">
            <Annotations/>
            <MeasureExpression>
                <SQL dialect="access">Iif("sales_fact_1997"."promotion_id" = 0, 0, "sales_fact_1997"."store_sales")</SQL>
                <SQL dialect="oracle">(case when "sales_fact_1997"."promotion_id" = 0 then 0 else "sales_fact_1997"."store_sales" end)</SQL>
                <SQL dialect="hsqldb">(case when "sales_fact_1997"."promotion_id" = 0 then 0 else "sales_fact_1997"."store_sales" end)</SQL>
                <SQL dialect="postgres">(case when "sales_fact_1997"."promotion_id" = 0 then 0 else "sales_fact_1997"."store_sales" end)</SQL>
                <SQL dialect="mysql">(case when `sales_fact_1997`.`promotion_id` = 0 then 0 else `sales_fact_1997`.`store_sales` end)</SQL>
                <SQL dialect="mariadb">(case when `sales_fact_1997`.`promotion_id` = 0 then 0 else `sales_fact_1997`.`store_sales` end)</SQL>
                <SQL dialect="neoview">(case when "sales_fact_1997"."promotion_id" = 0 then 0 else "sales_fact_1997"."store_sales" end)</SQL>
                <SQL dialect="infobright">`sales_fact_1997`.`store_sales`</SQL>
                <SQL dialect="derby">(case when "sales_fact_1997"."promotion_id" = 0 then 0 else "sales_fact_1997"."store_sales" end)</SQL>
                <SQL dialect="luciddb">(case when "sales_fact_1997"."promotion_id" = 0 then 0 else "sales_fact_1997"."store_sales" end)</SQL>
                <SQL dialect="db2">(case when "sales_fact_1997"."promotion_id" = 0 then 0 else "sales_fact_1997"."store_sales" end)</SQL>
                <SQL dialect="snowflake">(case when "sales_fact_1997"."promotion_id" = 0 then 0 else "sales_fact_1997"."store_sales" end)</SQL>
                <SQL dialect="generic">(case when sales_fact_1997.promotion_id = 0 then 0 else sales_fact_1997.store_sales end)</SQL>
            </MeasureExpression>
        </Measure>
        <Annotations>
            <Annotation name="caption.de_DE">Verkaufen</Annotation>
            <Annotation name="caption.fr_FR">Ventes</Annotation>
            <Annotation name="description.fr_FR">Cube des ventes</Annotation>
            <Annotation name="description.de">Cube Verkaufen</Annotation>
            <Annotation name="description.de_AT">Cube den Verkaufen</Annotation>
        </Annotations>
        <CalculatedMember name="Profit" dimension="Measures" visible="true">
            <Annotations/>
            <CalculatedMemberProperty name="FORMAT_STRING" value="$#,##0.00"/>
            <Formula>[Measures].[Store Sales] - [Measures].[Store Cost]</Formula>
        </CalculatedMember>
        <CalculatedMember formula="COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER),    Measures.[Profit])" name="Profit last Period" dimension="Measures" visible="false">
            <Annotations/>
            <CalculatedMemberProperty name="FORMAT_STRING" value="$#,##0.00"/>
            <CalculatedMemberProperty name="MEMBER_ORDINAL" value="18"/>
        </CalculatedMember>
        <CalculatedMember formula="([Measures].[Profit] - [Measures].[Profit last Period]) / [Measures].[Profit last Period]" name="Profit Growth" caption="Gewinn-Wachstum" dimension="Measures" visible="true">
            <Annotations/>
            <CalculatedMemberProperty name="FORMAT_STRING" value="0.0%"/>
        </CalculatedMember>
    </Cube>
    <Cube name="Warehouse" cache="true" enabled="true" visible="true">
        <Table name="inventory_fact_1997"/>
        <DimensionUsage name="Store" source="Store" foreignKey="store_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <DimensionUsage name="Store Size in SQFT" source="Store Size in SQFT" foreignKey="store_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <DimensionUsage name="Store Type" source="Store Type" foreignKey="store_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <DimensionUsage name="Time" source="Time" foreignKey="time_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <DimensionUsage name="Product" source="Product" foreignKey="product_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <DimensionUsage name="Warehouse" source="Warehouse" foreignKey="warehouse_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <Measure name="Store Invoice" column="store_invoice" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Supply Time" column="supply_time" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Warehouse Cost" column="warehouse_cost" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Warehouse Sales" column="warehouse_sales" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Units Shipped" column="units_shipped" formatString="#.0" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Units Ordered" column="units_ordered" formatString="#.0" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Warehouse Profit" aggregator="sum" visible="true">
            <Annotations/>
            <MeasureExpression>
                <SQL dialect="mysql">`warehouse_sales` - `inventory_fact_1997`.`warehouse_cost`</SQL>
                <SQL dialect="mariadb">`warehouse_sales` - `inventory_fact_1997`.`warehouse_cost`</SQL>
                <SQL dialect="infobright">`warehouse_sales` - `inventory_fact_1997`.`warehouse_cost`</SQL>
                <SQL dialect="generic">&amp;quot;warehouse_sales&amp;quot; - &amp;quot;inventory_fact_1997&amp;quot;.&amp;quot;warehouse_cost&amp;quot;</SQL>
            </MeasureExpression>
        </Measure>
        <Annotations/>
        <CalculatedMember formula="[Measures].[Warehouse Sales] / [Measures].[Warehouse Cost]" name="Average Warehouse Sale" dimension="Measures" visible="true">
            <Annotations/>
            <CalculatedMemberProperty name="FORMAT_STRING" value="$#,##0.00"/>
        </CalculatedMember>
        <NamedSet name="Top Sellers">
            <Annotations/>
            <Formula>TopCount([Warehouse].[Warehouse Name].MEMBERS, 5, [Measures].[Warehouse Sales])</Formula>
        </NamedSet>
    </Cube>
    <Cube name="Store" cache="true" enabled="true" visible="true">
        <Table name="store"/>
        <Dimension name="Store Type" type="StandardDimension" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" visible="true">
                <Level name="Store Type" column="store_type" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Has coffee bar" type="StandardDimension" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" visible="true">
                <Level name="Has coffee bar" column="coffee_bar" type="Boolean" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <DimensionUsage name="Store" source="Store" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <Measure name="Store Sqft" column="store_sqft" formatString="#,###" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Grocery Sqft" column="grocery_sqft" formatString="#,###" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Annotations/>
    </Cube>
    <Cube name="HR" cache="true" enabled="true" visible="true">
        <Table name="salary"/>
        <Dimension name="Time" type="TimeDimension" foreignKey="pay_date" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="false" primaryKey="the_date" visible="true">
                <Table name="time_by_day"/>
                <Level name="Year" column="the_year" type="Numeric" uniqueMembers="true" levelType="TimeYears" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="Quarter" column="quarter" type="String" uniqueMembers="false" levelType="TimeQuarters" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="Month" column="month_of_year" nameColumn="the_month" type="Numeric" uniqueMembers="false" levelType="TimeMonths" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Store" type="StandardDimension" foreignKey="employee_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" primaryKey="employee_id" primaryKeyTable="employee" visible="true">
                <Join leftKey="store_id" rightKey="store_id">
                    <Table name="employee"/>
                    <Table name="store"/>
                </Join>
                <Level name="Store Country" table="store" column="store_country" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="Store State" table="store" column="store_state" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="Store City" table="store" column="store_city" type="String" uniqueMembers="false" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="Store Name" table="store" column="store_name" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                    <Property name="Store Type" column="store_type" type="String" dependsOnLevelValue="false"/>
                    <Property name="Store Manager" column="store_manager" type="String" dependsOnLevelValue="false"/>
                    <Property name="Store Sqft" column="stoe_sqft" type="Numeric" dependsOnLevelValue="false"/>
                    <Property name="Grocery Sqft" column="grocery_sqft" type="Numeric" dependsOnLevelValue="false"/>
                    <Property name="Frozen Sqft" column="frozen_sqft" type="Numeric" dependsOnLevelValue="false"/>
                    <Property name="Meat Sqft" column="meat_sqft" type="Numeric" dependsOnLevelValue="false"/>
                    <Property name="Has coffee bar" column="coffee_bar" type="Boolean" dependsOnLevelValue="false"/>
                    <Property name="Street address" column="store_street_address" type="String" dependsOnLevelValue="false"/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Pay Type" type="StandardDimension" foreignKey="employee_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" primaryKey="employee_id" primaryKeyTable="employee" visible="true">
                <Join leftKey="position_id" rightKey="position_id">
                    <Table name="employee"/>
                    <Table name="position"/>
                </Join>
                <Level name="Pay Type" table="position" column="pay_type" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Store Type" type="StandardDimension" foreignKey="employee_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" primaryKey="employee_id" primaryKeyTable="employee" visible="true">
                <Join leftKey="store_id" rightKey="store_id">
                    <Table name="employee"/>
                    <Table name="store"/>
                </Join>
                <Level name="Store Type" table="store" column="store_type" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Position" type="StandardDimension" foreignKey="employee_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" allMemberName="All Position" primaryKey="employee_id" visible="true">
                <Table name="employee"/>
                <Level name="Management Role" column="management_role" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="Position Title" column="position_title" ordinalColumn="position_id" type="String" uniqueMembers="false" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Department" type="StandardDimension" foreignKey="department_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" primaryKey="department_id" visible="true">
                <Table name="department"/>
                <Level name="Department Description" column="department_id" type="Numeric" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Employees" type="StandardDimension" foreignKey="employee_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" allMemberCaption="All Employees" primaryKey="employee_id" visible="true">
                <Table name="employee"/>
                <Level name="Employee Id" column="employee_id" nameColumn="full_name" parentColumn="supervisor_id" nullParentValue="0" type="Numeric" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                    <Closure parentColumn="supervisor_id" childColumn="employee_id">
                        <Table name="employee_closure"/>
                    </Closure>
                    <Property name="Marital Status" column="marital_status" type="String" dependsOnLevelValue="false"/>
                    <Property name="Position Title" column="position_title" type="String" dependsOnLevelValue="false"/>
                    <Property name="Gender" column="gender" type="String" dependsOnLevelValue="false"/>
                    <Property name="Salary" column="salary" type="String" dependsOnLevelValue="false"/>
                    <Property name="Education Level" column="education_level" type="String" dependsOnLevelValue="false"/>
                    <Property name="Management Role" column="management_role" type="String" dependsOnLevelValue="false"/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Measure name="Org Salary" column="salary_paid" formatString="Currency" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Count" column="employee_id" formatString="#,#" aggregator="count" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Number of Employees" column="employee_id" formatString="#,#" aggregator="distinct-count" visible="true">
            <Annotations/>
        </Measure>
        <Annotations/>
        <CalculatedMember formula="([Employees].currentmember.datamember, [Measures].[Org Salary])" name="Employee Salary" formatString="Currency" dimension="Measures" visible="true">
            <Annotations/>
        </CalculatedMember>
        <CalculatedMember formula="[Measures].[Org Salary]/[Measures].[Number of Employees]" name="Avg Salary" formatString="Currency" dimension="Measures" visible="true">
            <Annotations/>
        </CalculatedMember>
    </Cube>
    <Cube name="Sales Ragged" cache="true" enabled="true" visible="true">
        <Table name="sales_fact_1997">
            <AggExclude name="agg_pc_10_sales_fact_1997" ignorecase="true"/>
            <AggExclude name="agg_lc_10_sales_fact_1997" ignorecase="true"/>
        </Table>
        <Dimension name="Store" type="StandardDimension" foreignKey="store_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" primaryKey="store_id" visible="true">
                <Table name="store_ragged"/>
                <Level name="Store Country" column="store_country" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="Store State" column="store_state" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="IfParentsName" visible="true">
                    <Annotations/>
                </Level>
                <Level name="Store City" column="store_city" type="String" uniqueMembers="false" levelType="Regular" hideMemberIf="IfBlankName" visible="true">
                    <Annotations/>
                </Level>
                <Level name="Store Name" column="store_name" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                    <Property name="Store Type" column="store_type" type="String" dependsOnLevelValue="false"/>
                    <Property name="Store Manager" column="store_manager" type="String" dependsOnLevelValue="false"/>
                    <Property name="Store Sqft" column="store_sqft" type="Numeric" dependsOnLevelValue="false"/>
                    <Property name="Grocery Sqft" column="grocery_sqft" type="Numeric" dependsOnLevelValue="false"/>
                    <Property name="Frozen Sqft" column="frozen_sqft" type="Numeric" dependsOnLevelValue="false"/>
                    <Property name="Meat Sqft" column="meat_sqft" type="Numeric" dependsOnLevelValue="false"/>
                    <Property name="Has coffee bar" column="coffee_bar" type="Boolean" dependsOnLevelValue="false"/>
                    <Property name="Street Address" column="store_street_address" type="String" dependsOnLevelValue="false"/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Geography" type="StandardDimension" foreignKey="store_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" primaryKey="store_id" visible="true">
                <Table name="store_ragged"/>
                <Level name="Country" column="store_country" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="State" column="store_state" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="IfBlankName" visible="true">
                    <Annotations/>
                </Level>
                <Level name="City" column="store_city" type="String" uniqueMembers="false" levelType="Regular" hideMemberIf="IfBlankName" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Promotion Media" type="StandardDimension" foreignKey="promotion_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" allMemberName="All Media" primaryKey="promotion_id" visible="true">
                <Table name="promotion"/>
                <Level name="Media Type" column="media_type" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Promotions" type="StandardDimension" foreignKey="promotion_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" allMemberName="All Promotions" primaryKey="promotion_id" visible="true">
                <Table name="promotion"/>
                <Level name="Promotion Name" column="promotion_name" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Customers" type="StandardDimension" foreignKey="customer_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" allMemberName="All Customers" primaryKey="customer_id" visible="true">
                <Table name="customer"/>
                <Level name="Country" column="country" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="State Province" column="state_province" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="City" column="city" type="String" uniqueMembers="false" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Level name="Name" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                    <KeyExpression>
                        <SQL dialect="oracle">"fname" || ' ' || "lname"</SQL>
                        <SQL dialect="hsqldb">"fname" || ' ' || "lname"</SQL>
                        <SQL dialect="access">fname + ' ' + lname</SQL>
                        <SQL dialect="postgres">"fname" || ' ' || "lname"</SQL>
                        <SQL dialect="mysql">CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)</SQL>
                        <SQL dialect="mariadb">CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)</SQL>
                        <SQL dialect="mssql">fname + ' ' + lname</SQL>
                        <SQL dialect="derby">"customer"."fullname"</SQL>
                        <SQL dialect="db2">CONCAT(CONCAT("customer"."fname", ' '), "customer"."lname")</SQL>
                        <SQL dialect="luciddb">"fname" || ' ' || "lname"</SQL>
                        <SQL dialect="neoview">"customer"."fullname"</SQL>
                        <SQL dialect="snowflake">"customer"."fullname"</SQL>
                        <SQL dialect="generic">fullname</SQL>
                    </KeyExpression>
                    <Property name="Gender" column="gender" type="String" dependsOnLevelValue="false"/>
                    <Property name="Marital Status" column="marital_status" type="String" dependsOnLevelValue="false"/>
                    <Property name="Education" column="education" type="String" dependsOnLevelValue="false"/>
                    <Property name="Yearly Income" column="yearly_income" type="String" dependsOnLevelValue="false"/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Education Level" type="StandardDimension" foreignKey="customer_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" primaryKey="customer_id" visible="true">
                <Table name="customer"/>
                <Level name="Education Level" column="education" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Gender" type="StandardDimension" foreignKey="customer_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" allMemberName="All Gender" primaryKey="customer_id" visible="true">
                <Table name="customer"/>
                <Level name="Gender" column="gender" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Marital Status" type="StandardDimension" foreignKey="customer_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" allMemberName="All Marital Status" primaryKey="customer_id" visible="true">
                <Table name="customer"/>
                <Level name="Marital Status" column="marital_status" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Dimension name="Yearly Income" type="StandardDimension" foreignKey="customer_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" primaryKey="customer_id" visible="true">
                <Table name="customer"/>
                <Level name="Yearly Income" column="yearly_income" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <DimensionUsage name="Store Size in SQFT" source="Store Size in SQFT" foreignKey="store_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <DimensionUsage name="Store Type" source="Store Type" foreignKey="store_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <DimensionUsage name="Time" source="Time" foreignKey="time_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <DimensionUsage name="Product" source="Product" foreignKey="product_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <Measure name="Unit Sales" column="unit_sales" formatString="Standard" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Store Cost" column="store_cost" formatString="#,###.00" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Store Sales" column="store_sales" formatString="#,###.00" aggregator="sum" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Sales Count" column="product_id" formatString="#,###" aggregator="count" visible="true">
            <Annotations/>
        </Measure>
        <Measure name="Customer Count" column="customer_id" formatString="#,###" aggregator="distinct-count" visible="true">
            <Annotations/>
        </Measure>
        <Annotations/>
    </Cube>
    <Cube name="Sales 2" cache="true" enabled="true" visible="true">
        <Table name="sales_fact_1997"/>
        <DimensionUsage name="Time" source="Time" foreignKey="time_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <DimensionUsage name="Product" source="Product" foreignKey="product_id" highCardinality="false" visible="true">
            <Annotations/>
        </DimensionUsage>
        <Dimension name="Gender" type="StandardDimension" foreignKey="customer_id" highCardinality="false" visible="true">
            <Annotations/>
            <Hierarchy hasAll="true" allMemberName="All Gender" primaryKey="customer_id" visible="true">
                <Table name="customer"/>
                <Level name="Gender" column="gender" type="String" uniqueMembers="true" levelType="Regular" hideMemberIf="Never" visible="true">
                    <Annotations/>
                </Level>
                <Annotations/>
            </Hierarchy>
        </Dimension>
        <Measure name="Sales Count" column="product_id" formatString="#,###" aggregator="count" visible="true">
            <Annotations/>
            <CalculatedMemberProperty name="MEMBER_ORDINAL" value="1"/>
        </Measure>
        <Measure name="Unit Sales" column="unit_sales" formatString="Standard" aggregator="sum" visible="true">
            <Annotations/>
            <CalculatedMemberProperty name="MEMBER_ORDINAL" value="2"/>
        </Measure>
        <Measure name="Store Sales" column="store_sales" formatString="#,###.00" aggregator="sum" visible="true">
            <Annotations/>
            <CalculatedMemberProperty name="MEMBER_ORDINAL" value="3"/>
        </Measure>
        <Measure name="Store Cost" column="store_cost" formatString="#,###.00" aggregator="sum" visible="true">
            <Annotations/>
            <CalculatedMemberProperty name="MEMBER_ORDINAL" value="6"/>
        </Measure>
        <Measure name="Customer Count" column="customer_id" formatString="#,###" aggregator="distinct-count" visible="true">
            <Annotations/>
            <CalculatedMemberProperty name="MEMBER_ORDINAL" value="7"/>
        </Measure>
        <Annotations/>
        <CalculatedMember name="Profit" dimension="Measures" visible="true">
            <Annotations/>
            <CalculatedMemberProperty name="FORMAT_STRING" value="$#,##0.00"/>
            <CalculatedMemberProperty name="MEMBER_ORDINAL" value="4"/>
            <Formula>[Measures].[Store Sales] - [Measures].[Store Cost]</Formula>
        </CalculatedMember>
        <CalculatedMember formula="COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER),    Measures.[Profit])" name="Profit last Period" dimension="Measures" visible="false">
            <Annotations/>
            <CalculatedMemberProperty name="MEMBER_ORDINAL" value="5"/>
        </CalculatedMember>
    </Cube>
    <VirtualCube enabled="true" name="Warehouse and Sales" defaultMeasure="Store Sales" visible="true">
        <Annotations/>
        <CubeUsages/>
        <VirtualCubeDimension cubeName="Sales" name="Customers" highCardinality="false" visible="true"/>
        <VirtualCubeDimension cubeName="Sales" name="Education Level" highCardinality="false" visible="true"/>
        <VirtualCubeDimension cubeName="Sales" name="Gender" highCardinality="false" visible="true"/>
        <VirtualCubeDimension cubeName="Sales" name="Marital Status" highCardinality="false" visible="true"/>
        <VirtualCubeDimension name="Product" highCardinality="false" visible="true"/>
        <VirtualCubeDimension cubeName="Sales" name="Promotion Media" highCardinality="false" visible="true"/>
        <VirtualCubeDimension cubeName="Sales" name="Promotions" highCardinality="false" visible="true"/>
        <VirtualCubeDimension name="Store" highCardinality="false" visible="true"/>
        <VirtualCubeDimension name="Time" highCardinality="false" visible="true"/>
        <VirtualCubeDimension cubeName="Sales" name="Yearly Income" highCardinality="false" visible="true"/>
        <VirtualCubeDimension cubeName="Warehouse" name="Warehouse" highCardinality="false" visible="true"/>
        <VirtualCubeMeasure cubeName="Sales" name="[Measures].[Sales Count]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <VirtualCubeMeasure cubeName="Sales" name="[Measures].[Store Cost]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <VirtualCubeMeasure cubeName="Sales" name="[Measures].[Store Sales]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <VirtualCubeMeasure cubeName="Sales" name="[Measures].[Unit Sales]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <VirtualCubeMeasure cubeName="Sales" name="[Measures].[Profit]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <VirtualCubeMeasure cubeName="Sales" name="[Measures].[Profit Growth]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <VirtualCubeMeasure cubeName="Warehouse" name="[Measures].[Store Invoice]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <VirtualCubeMeasure cubeName="Warehouse" name="[Measures].[Supply Time]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <VirtualCubeMeasure cubeName="Warehouse" name="[Measures].[Units Ordered]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <VirtualCubeMeasure cubeName="Warehouse" name="[Measures].[Units Shipped]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <VirtualCubeMeasure cubeName="Warehouse" name="[Measures].[Warehouse Cost]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <VirtualCubeMeasure cubeName="Warehouse" name="[Measures].[Warehouse Profit]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <VirtualCubeMeasure cubeName="Warehouse" name="[Measures].[Warehouse Sales]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <VirtualCubeMeasure cubeName="Warehouse" name="[Measures].[Average Warehouse Sale]" visible="true">
            <Annotations/>
        </VirtualCubeMeasure>
        <CalculatedMember name="Profit Per Unit Shipped" dimension="Measures" visible="true">
            <Annotations/>
            <Formula>[Measures].[Profit] / [Measures].[Units Shipped]</Formula>
        </CalculatedMember>
    </VirtualCube>
    <Role name="California manager">
        <Annotations/>
        <SchemaGrant access="none">
            <CubeGrant cube="Sales" access="all">
                <HierarchyGrant hierarchy="[Store]" access="custom" topLevel="[Store].[Store Country]" rollupPolicy="full">
                    <MemberGrant member="[Store].[USA].[CA]" access="all"/>
                    <MemberGrant member="[Store].[USA].[CA].[Los Angeles]" access="none"/>
                </HierarchyGrant>
                <HierarchyGrant hierarchy="[Customers]" access="custom" topLevel="[Customers].[State Province]" bottomLevel="[Customers].[City]" rollupPolicy="full">
                    <MemberGrant member="[Customers].[USA].[CA]" access="all"/>
                    <MemberGrant member="[Customers].[USA].[CA].[Los Angeles]" access="none"/>
                </HierarchyGrant>
                <HierarchyGrant hierarchy="[Gender]" access="none" rollupPolicy="full"/>
            </CubeGrant>
        </SchemaGrant>
    </Role>
    <Role name="No HR Cube">
        <Annotations/>
        <SchemaGrant access="all">
            <CubeGrant cube="HR" access="none"/>
        </SchemaGrant>
    </Role>
    <Role name="Administrator">
        <Annotations/>
        <SchemaGrant access="all"/>
    </Role>
</Schema>
        """;

	@Test
	void test1() throws Exception {
		FoodMartRecordDbMappingSchemaProvider provider=new FoodMartRecordDbMappingSchemaProvider();
		SerializerModifier sm=new SerializerModifier(provider.get());
        String xml = sm.getXML();
        assertThat(xml).isNotNull();
		System.out.println(xml);
	}

    @Test
    void testCompare() throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(SchemaImpl.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        SchemaImpl schema = (SchemaImpl) jaxbUnmarshaller.unmarshal(new StringReader(xml));

        SerializerModifier sm = new SerializerModifier(schema);
        String xmlNew = sm.getXML();
        System.out.println(xmlNew);
        assertThat(xmlNew).isNotNull().isEqualTo(xml);
        
		FoodMartRecordDbMappingSchemaProvider provider=new FoodMartRecordDbMappingSchemaProvider();
		sm=new SerializerModifier(provider.get());
        xmlNew = sm.getXML();
        assertThat(xmlNew).isNotNull().isEqualTo(xml);
        System.out.println(xmlNew);

    }
}
