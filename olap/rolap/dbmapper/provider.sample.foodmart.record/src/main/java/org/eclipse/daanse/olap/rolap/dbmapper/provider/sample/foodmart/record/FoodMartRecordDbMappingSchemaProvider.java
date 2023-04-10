package org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.foodmart.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.AggTable;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.DimensionUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.AccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.HideMemberIfEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MemberGrantAccessEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.PropertyTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AggExcludeR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.AnnotationR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CalculatedMemberPropertyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CalculatedMemberR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ClosureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CubeR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.ExpressionViewR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.HierarchyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.JoinR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.LevelR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.MeasureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.NamedSetR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.PrivateDimensionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.PropertyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.RoleR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.TableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.VirtualCubeDimensionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.VirtualCubeMeasureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.VirtualCubeR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggColumnNameRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggExcludeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggForeignKeyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggLevelRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggMeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggNameRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AnnotationRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CalculatedMemberPropertyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CalculatedMemberRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.ClosureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.DimensionUsageRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.ExpressionViewRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.FormulaRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.LevelRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MemberGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.NamedSetRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PrivateDimensionRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PropertyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.RoleRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SQLRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaGrantRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.VirtualCubeDimensionRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.VirtualCubeMeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.VirtualCubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DbMappingSchemaProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@Component(service = DbMappingSchemaProvider.class, scope = ServiceScope.SINGLETON, property = { "sample.name=FoodMart",
		"sample.type=record" })
public class FoodMartRecordDbMappingSchemaProvider implements DbMappingSchemaProvider {

    private static String SCHEMA_NAME = "FoodMart";
    private static final String CUBE_NAME_1 = "Sales";

    public static TableR
        TABLE_1 = new TableR("promotion"),
        TABLE_2 = TABLE_1,
        TABLE_3 = new TableR("customer"),
        TABLE_4 = TABLE_3,
        TABLE_5 = TABLE_3,
        TABLE_6 = TABLE_3,
        TABLE_7 = TABLE_3,

    TABLE_4_1 = new TableR("time_by_day"),
        TABLE_4_5 = new TableR("employee"),
        TABLE_4_6 = new TableR("department"),
        TABLE_4_7 = new TableR("employee"),

    TABLE_5_1 = new TableR("store_ragged"),
        TABLE_5_2 = TABLE_5_1,
        TABLE_5_3 = new TableR("promotion"),
        TABLE_5_4 = TABLE_5_3,
        TABLE_5_5 = new TableR("customer"),
        TABLE_5_6 = TABLE_5_5,
        TABLE_5_7 = TABLE_5_5,
        TABLE_5_8 = TABLE_5_5,
        TABLE_5_9 = TABLE_5_5,

    TABLE_6_1 = TABLE_5_5,

    TABLE_SHARED_1 = new TableR("store"),
        TABLE_SHARED_2 = new TableR("store"),
        TABLE_SHARED_3 = new TableR("store"),
        TABLE_SHARED_4_A = new TableR("time_by_day"),
        TABLE_SHARED_4_B = new TableR("time_by_day"),
        TABLE_SHARED_6 = new TableR("warehouse"),

    TABLE_JOIN_1_A = new TableR("product"),
        TABLE_JOIN_1_B = new TableR("product_class"),
        TABLE_JOIN_4_2_A = new TableR("employee"),
        TABLE_JOIN_4_2_B = new TableR("store"),
        TABLE_JOIN_4_3_A = new TableR("employee"),
        TABLE_JOIN_4_3_B = new TableR("position"),
        TABLE_JOIN_4_4_A = new TableR("employee"),
        TABLE_JOIN_4_4_B = new TableR("store"),

    TABLE_CLOSURE_4_7 = new TableR("employee_closure");

    public static PropertyR
        PROPERTY_1_A = PropertyRBuilder
        .builder()
        .name("Store Type")
        .column("store_type")
        .build(),
        PROPERTY_1_B = PropertyRBuilder
            .builder()
            .name("Store Manager")
            .column("store_manager")
            .build(),
        PROPERTY_1_C = PropertyRBuilder
            .builder()
            .name("Store Sqft")
            .column("store_sqft")
            .type(PropertyTypeEnum.NUMERIC)
            .build(),
        PROPERTY_1_D = PropertyRBuilder
            .builder()
            .name("Grocery Sqft")
            .column("grocery_sqft")
            .type(PropertyTypeEnum.NUMERIC)
            .build(),
        PROPERTY_1_E = PropertyRBuilder
            .builder()
            .name("Frozen Sqft")
            .column("frozen_sqft")
            .type(PropertyTypeEnum.NUMERIC)
            .build(),
        PROPERTY_1_F = PropertyRBuilder
            .builder()
            .name("Meat Sqft")
            .column("meat_sqft")
            .type(PropertyTypeEnum.NUMERIC)
            .build(),
        PROPERTY_1_G = PropertyRBuilder
            .builder()
            .name("Has coffee bar")
            .column("coffee_bar")
            .type(PropertyTypeEnum.BOOLEAN)
            .build(),
        PROPERTY_1_H = PropertyRBuilder
            .builder()
            .name("Street Address")
            .column("store_street_address")
            .type(PropertyTypeEnum.STRING)
            .build(),
        PROPERTY_3_4_1 = PropertyRBuilder
            .builder()
            .name("Gender")
            .column("gender")
            .build(),
        PROPERTY_3_4_2 = PropertyRBuilder
            .builder()
            .name("Material Status")
            .column("material_status")
            .build(),
        PROPERTY_3_4_3 = PropertyRBuilder
            .builder()
            .name("Education")
            .column("education")
            .build(),
        PROPERTY_3_4_4 = PropertyRBuilder
            .builder()
            .name("Yearly Income")
            .column("yearly_income")
            .build(),
        PROPERTY_4_2_D_ = PropertyRBuilder
            .builder()
            .build(),
        PROPERTY_4_2_D_a = PropertyRBuilder
            .builder()
            .name("Store Type")
            .column("store_type")
            .build(),
        PROPERTY_4_2_D_b = PropertyRBuilder
            .builder()
            .name("Store Manager")
            .column("store_manager")
            .build(),
        PROPERTY_4_2_D_c = PropertyRBuilder
            .builder()
            .name("Store Sqft")
            .column("stoe_sqft")
            .type(PropertyTypeEnum.NUMERIC)
            .build(),
        PROPERTY_4_2_D_d = PropertyRBuilder
            .builder()
            .name("Grocery Sqft")
            .column("grocery_sqft")
            .type(PropertyTypeEnum.NUMERIC)
            .build(),
        PROPERTY_4_2_D_e = PropertyRBuilder
            .builder()
            .name("Frozen Sqft")
            .column("frozen_sqft")
            .type(PropertyTypeEnum.NUMERIC)
            .build(),
        PROPERTY_4_2_D_f = PropertyRBuilder
            .builder()
            .name("Meat Sqft")
            .column("meat_sqft")
            .type(PropertyTypeEnum.NUMERIC)
            .build(),
        PROPERTY_4_2_D_g = PropertyRBuilder
            .builder()
            .name("Has coffee bar")
            .column("coffee_bar")
            .type(PropertyTypeEnum.BOOLEAN)
            .build(),
        PROPERTY_4_2_D_h = PropertyRBuilder
            .builder()
            .name("Street address")
            .column("store_street_address")
            .type(PropertyTypeEnum.STRING)
            .build(),
        PROPERTY_4_7_A = PropertyRBuilder
            .builder()
            .name("Marital Status")
            .column("marital_status")
            .build(),
        PROPERTY_4_7_B = PropertyRBuilder
            .builder()
            .name("Position Title")
            .column("position_title")
            .build(),
        PROPERTY_4_7_C = PropertyRBuilder
            .builder()
            .name("Gender")
            .column("gender")
            .build(),
        PROPERTY_4_7_D = PropertyRBuilder
            .builder()
            .name("Salary")
            .column("salary")
            .build(),
        PROPERTY_4_7_E = PropertyRBuilder
            .builder()
            .name("Education Level")
            .column("education_level")
            .build(),
        PROPERTY_4_7_F = PropertyRBuilder
            .builder()
            .name("Management Role")
            .column("management_role")
            .build(),
        PROPERTY_5_1_D_1 = PropertyRBuilder
            .builder()
            .name("Store Type")
            .column("store_type")
            .build(),
        PROPERTY_5_1_D_2 = PropertyRBuilder
            .builder()
            .name("Store Manager")
            .column("store_manager")
            .build(),
        PROPERTY_5_1_D_3 = PropertyRBuilder
            .builder()
            .name("Store Sqft")
            .column("store_sqft")
            .type(PropertyTypeEnum.NUMERIC)
            .build(),
        PROPERTY_5_1_D_4 = PropertyRBuilder
            .builder()
            .name("Grocery Sqft")
            .column("grocery_sqft")
            .type(PropertyTypeEnum.NUMERIC)
            .build(),
        PROPERTY_5_1_D_5 = PropertyRBuilder
            .builder()
            .name("Frozen Sqft")
            .column("frozen_sqft")
            .type(PropertyTypeEnum.NUMERIC)
            .build(),
        PROPERTY_5_1_D_6 = PropertyRBuilder
            .builder()
            .name("Meat Sqft")
            .column("meat_sqft")
            .type(PropertyTypeEnum.NUMERIC)
            .build(),
        PROPERTY_5_1_D_7 = PropertyRBuilder
            .builder()
            .name("Has coffee bar")
            .column("coffee_bar")
            .type(PropertyTypeEnum.BOOLEAN)
            .build(),
        PROPERTY_5_1_D_8 = PropertyRBuilder
            .builder()
            .name("Street Address")
            .column("store_street_address")
            .type(PropertyTypeEnum.STRING)
            .build(),
        PROPERTY_5_5_D_1 = PropertyRBuilder
            .builder()
            .name("Gender")
            .column("gender")
            .build(),
        PROPERTY_5_5_D_2 = PropertyRBuilder
            .builder()
            .name("Marital Status")
            .column("marital_status")
            .build(),
        PROPERTY_5_5_D_3 = PropertyRBuilder
            .builder()
            .name("Education")
            .column("education")
            .build(),
        PROPERTY_5_5_D_4 = PropertyRBuilder
            .builder()
            .name("Yearly Income")
            .column("yearly_income")
            .build();
    public static ExpressionViewR
        NAMEEXPRESSION_1 = ExpressionViewRBuilder
        .builder()
        .sql(List.of(
            SQLRBuilder.builder().dialect("oracle").content("\"fname\" || ' ' || \"lname\"").build(),
            SQLRBuilder.builder().dialect("hive").content("`customer`.`fullname`").build(),
            SQLRBuilder.builder().dialect("hsqldb").content("\"fname\" || ' ' || \"lname\"").build(),
            SQLRBuilder.builder().dialect("access").content("fname + ' ' + lname").build(),
            SQLRBuilder.builder().dialect("postgres").content("\"fname\" || ' ' || \"lname\"").build(),
            SQLRBuilder.builder().dialect("mysql").content("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)").build(),
            SQLRBuilder.builder().dialect("mariadb").content("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)").build(),
            SQLRBuilder.builder().dialect("mssql").content("fname + ' ' + lname").build(),
            SQLRBuilder.builder().dialect("derby").content("\"customer\".\"fullname\"").build(),
            SQLRBuilder.builder().dialect("db2").content("CONCAT(CONCAT(\"customer\".\"fname\", ' '), " +
                "\"customer\".\"lname\")").build(),
            SQLRBuilder.builder().dialect("luciddb").content("\"fname\" || ' ' || \"lname\"").build(),
            SQLRBuilder.builder().dialect("neoview").content("\"customer\".\"fullname\"").build(),
            SQLRBuilder.builder().dialect("teradata").content("\"fname\" || ' ' || \"lname\"").build(),
            SQLRBuilder.builder().dialect("snowflake").content("\"customer\".\"fullname\"").build(),
            SQLRBuilder.builder().dialect("generic").content("fullname").build()
        ))
        .build(),
        ORDINAL_EXPRESSION_1 = ExpressionViewRBuilder
            .builder()
            .sql(List.of(
                SQLRBuilder.builder().dialect("oracle").content("\"fname\" || ' ' || \"lname\"").build(),
                SQLRBuilder.builder().dialect("hsqldb").content("\"fname\" || ' ' || \"lname\"").build(),
                SQLRBuilder.builder().dialect("access").content("fname + ' ' + lname").build(),
                SQLRBuilder.builder().dialect("postgres").content("\"fname\" || ' ' || \"lname\"").build(),
                SQLRBuilder.builder().dialect("mysql").content("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)").build(),
                SQLRBuilder.builder().dialect("mariadb").content("CONCAT(`customer`.`fname`, ' ', `customer`.`lname`)"
                ).build(),
                SQLRBuilder.builder().dialect("mssql").content("fname + ' ' + lname").build(),
                SQLRBuilder.builder().dialect("neoview").content("\"customer\".\"fullname\"").build(),
                SQLRBuilder.builder().dialect("derby").content("\"customer\".\"fullname\"").build(),
                SQLRBuilder.builder().dialect("db2").content("CONCAT(CONCAT(\"customer\".\"fname\", ' '), " +
                    "\"customer\".\"lname\")").build(),
                SQLRBuilder.builder().dialect("luciddb").content("\"fname\" || ' ' || \"lname\"").build(),
                SQLRBuilder.builder().dialect("snowflake").content("\"customer\".\"fullname\"").build(),
                SQLRBuilder.builder().dialect("generic").content("fullname").build()
            ))
            .build(),
        MEASUREEXPRESSION_1 = ExpressionViewRBuilder
            .builder()
            .sql(List.of(
                SQLRBuilder.builder().dialect("access")
                    .content("Iif(\"sales_fact_1997\".\"promotion_id\" = 0, 0, \"sales_fact_1997\".\"store_sales\")")
                    .build(),
                SQLRBuilder.builder().dialect("oracle")
                    .content("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\"" +
                        ".\"store_sales\" end)")
                    .build(),
                SQLRBuilder.builder().dialect("hsqldb")
                    .content("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\"" +
                        ".\"store_sales\" end)")
                    .build(),
                SQLRBuilder.builder().dialect("postgres")
                    .content("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\"" +
                        ".\"store_sales\" end)")
                    .build(),
                SQLRBuilder.builder().dialect("mysql")
                    .content("(case when `sales_fact_1997`.`promotion_id` = 0 then 0 else `sales_fact_1997`" +
                        ".`store_sales` end)")
                    .build(),
                SQLRBuilder.builder().dialect("mariadb")
                    .content("(case when `sales_fact_1997`.`promotion_id` = 0 then 0 else `sales_fact_1997`" +
                        ".`store_sales` end)")
                    .build(),
                SQLRBuilder.builder().dialect("neoview")
                    .content("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\"" +
                        ".\"store_sales\" end)")
                    .build(),
                SQLRBuilder.builder().dialect("infobright")
                    .content("`sales_fact_1997`.`store_sales`")
                    .build(),
                SQLRBuilder.builder().dialect("derby")
                    .content("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\"" +
                        ".\"store_sales\" end)")
                    .build(),
                SQLRBuilder.builder().dialect("luciddb")
                    .content("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\"" +
                        ".\"store_sales\" end)")
                    .build(),
                SQLRBuilder.builder().dialect("db2")
                    .content("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\"" +
                        ".\"store_sales\" end)")
                    .build(),
                SQLRBuilder.builder().dialect("snowflake")
                    .content("(case when \"sales_fact_1997\".\"promotion_id\" = 0 then 0 else \"sales_fact_1997\"" +
                        ".\"store_sales\" end)")
                    .build(),
                SQLRBuilder.builder().dialect("generic")
                    .content("(case when sales_fact_1997.promotion_id = 0 then 0 else sales_fact_1997.store_sales end)")
                    .build()
            ))
            .build(),
        MEASUREEXPRESSION_2_7 = ExpressionViewRBuilder
            .builder()
            .sql(List.of(
                SQLRBuilder.builder().dialect("mysql")
                    .content("`warehouse_sales` - `inventory_fact_1997`.`warehouse_cost`")
                    .build(),
                SQLRBuilder.builder().dialect("mariadb")
                    .content("`warehouse_sales` - `inventory_fact_1997`.`warehouse_cost`")
                    .build(),
                SQLRBuilder.builder().dialect("infobright")
                    .content("`warehouse_sales` - `inventory_fact_1997`.`warehouse_cost`")
                    .build(),
                SQLRBuilder.builder().dialect("generic")
                    .content("&quot;warehouse_sales&quot; - &quot;inventory_fact_1997&quot;.&quot;warehouse_cost&quot;")
                    .build()
            ))
            .build();
    public static ClosureR
        CLOSURE_4_7 = ClosureRBuilder
        .builder()
        .parentColumn("supervisor_id")
        .childColumn("employee_id")
        .table(TABLE_CLOSURE_4_7)
        .build();

    public static LevelR
        LEVEL_1_1 = LevelRBuilder
        .builder()
        .name("Media Type")
        .column("media_type")
        .uniqueMembers(true)
        .build(),
        LEVEL_1_2 = LevelRBuilder
            .builder()
            .name("Promotion Name")
            .column("promotion_name")
            .uniqueMembers(true)
            .build(),
        LEVEL_1_3_A = LevelRBuilder
            .builder()
            .name("Country")
            .column("country")
            .uniqueMembers(true)
            .build(),
        LEVEL_1_3_B = LevelRBuilder
            .builder()
            .name("State Province")
            .column("state_province")
            .uniqueMembers(true)
            .build(),
        LEVEL_1_3_C = LevelRBuilder
            .builder()
            .name("City")
            .column("city")
            .uniqueMembers(false)
            .build(),
        LEVEL_1_3_D = LevelRBuilder
            .builder()
            .name("Name")
            .column("customer_id")
            .uniqueMembers(true)
            .type(TypeEnum.NUMERIC)
            .nameExpression(NAMEEXPRESSION_1)
            .ordinalExpression(ORDINAL_EXPRESSION_1)
            .property(List.of(
                PROPERTY_3_4_1,
                PROPERTY_3_4_2,
                PROPERTY_3_4_3,
                PROPERTY_3_4_4))
            .build(),
        LEVEL_1_4 = LevelRBuilder
            .builder()
            .name("Education Level")
            .column("education")
            .uniqueMembers(true)
            .build(),
        LEVEL_1_5 = LevelRBuilder
            .builder()
            .name("Gender")
            .column("gender")
            .uniqueMembers(true)
            .build(),
        LEVEL_1_6 = LevelRBuilder
            .builder()
            .name("Marital Status")
            .column("marital_status")
            .uniqueMembers(true)
            .approxRowCount("111")
            .build(),
        LEVEL_1_7 = LevelRBuilder
            .builder()
            .name("Yearly Income")
            .column("yearly_income")
            .uniqueMembers(true)
            .build(),
        LEVEL_3_1 = LevelRBuilder
            .builder()
            .name("Store Type")
            .column("store_type")
            .uniqueMembers(true)
            .build(),
        LEVEL_3_2 = LevelRBuilder
            .builder()
            .name("Has coffee bar")
            .column("coffee_bar")
            .uniqueMembers(true)
            .type(TypeEnum.BOOLEAN)
            .build(),
        LEVEL_4_1_A = LevelRBuilder
            .builder()
            .name("Year")
            .column("the_year")
            .type(TypeEnum.NUMERIC)
            .uniqueMembers(true)
            .levelType(LevelTypeEnum.TIME_YEARS)
            .build(),
        LEVEL_4_1_B = LevelRBuilder
            .builder()
            .name("Quarter")
            .column("quarter")
            .uniqueMembers(false)
            .levelType(LevelTypeEnum.TIME_QUARTERS)
            .build(),
        LEVEL_4_1_C = LevelRBuilder
            .builder()
            .name("Month")
            .column("month_of_year")
            .nameColumn("the_month")
            .uniqueMembers(false)
            .type(TypeEnum.NUMERIC)
            .levelType(LevelTypeEnum.TIME_MONTHS)
            .build(),
        LEVEL_4_2_A = LevelRBuilder
            .builder()
            .name("Store Country")
            .table("store")
            .column("store_country")
            .uniqueMembers(true)
            .build(),
        LEVEL_4_2_B = LevelRBuilder
            .builder()
            .name("Store State")
            .table("store")
            .column("store_state")
            .uniqueMembers(true)
            .build(),
        LEVEL_4_2_C = LevelRBuilder
            .builder()
            .name("Store City")
            .table("store")
            .column("store_city")
            .uniqueMembers(false)
            .build(),
        LEVEL_4_2_D = LevelRBuilder
            .builder()
            .name("Store Name")
            .table("store")
            .column("store_name")
            .uniqueMembers(true)
            .property(List.of(
                PROPERTY_4_2_D_a,
                PROPERTY_4_2_D_b,
                PROPERTY_4_2_D_c,
                PROPERTY_4_2_D_d,
                PROPERTY_4_2_D_e,
                PROPERTY_4_2_D_f,
                PROPERTY_4_2_D_g,
                PROPERTY_4_2_D_h))
            .build(),
        LEVEL_4_3 = LevelRBuilder
            .builder()
            .name("Pay Type")
            .table("position")
            .column("pay_type")
            .uniqueMembers(true)
            .build(),
        LEVEL_4_4 = LevelRBuilder
            .builder()
            .name("Store Type")
            .table("store")
            .column("store_type")
            .uniqueMembers(true)
            .build(),
        LEVEL_4_5_A = LevelRBuilder
            .builder()
            .name("Management Role")
            .uniqueMembers(true)
            .column("management_role")
            .build(),
        LEVEL_4_5_B = LevelRBuilder
            .builder()
            .name("Position Title")
            .uniqueMembers(false)
            .column("position_title")
            .ordinalColumn("position_id")
            .build(),
        LEVEL_4_6 = LevelRBuilder
            .builder()
            .name("Department Description")
            .type(TypeEnum.NUMERIC)
            .uniqueMembers(true)
            .column("department_id")
            .build(),
        LEVEL_4_7 = LevelRBuilder
            .builder()
            .name("Employee Id")
            .type(TypeEnum.NUMERIC)
            .uniqueMembers(true)
            .column("employee_id")
            .parentColumn("supervisor_id")
            .nameColumn("full_name")
            .nullParentValue("0")
            .closure(CLOSURE_4_7)
            .property(List.of(PROPERTY_4_7_A,
                PROPERTY_4_7_B,
                PROPERTY_4_7_C,
                PROPERTY_4_7_D,
                PROPERTY_4_7_E,
                PROPERTY_4_7_F))
            .build(),
        LEVEL_5_1_A = LevelRBuilder
            .builder()
            .name("Store Country")
            .column("store_country")
            .uniqueMembers(true)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .build(),
        LEVEL_5_1_B = LevelRBuilder
            .builder()
            .name("Store State")
            .column("store_state")
            .uniqueMembers(true)
            .hideMemberIf(HideMemberIfEnum.IF_PARENTS_NAME)
            .build(),
        LEVEL_5_1_C = LevelRBuilder
            .builder()
            .name("Store City")
            .column("store_city")
            .uniqueMembers(false)
            .hideMemberIf(HideMemberIfEnum.IF_BLANK_NAME)
            .build(),
        LEVEL_5_1_D = LevelRBuilder
            .builder()
            .name("Store Name")
            .column("store_name")
            .uniqueMembers(true)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .property(List.of(PROPERTY_5_1_D_1,
                PROPERTY_5_1_D_2,
                PROPERTY_5_1_D_3,
                PROPERTY_5_1_D_4,
                PROPERTY_5_1_D_5,
                PROPERTY_5_1_D_6,
                PROPERTY_5_1_D_7,
                PROPERTY_5_1_D_8))
            .build(),
        LEVEL_5_2_A = LevelRBuilder
            .builder()
            .name("Country")
            .column("store_country")
            .uniqueMembers(true)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .build(),
        LEVEL_5_2_B = LevelRBuilder
            .builder()
            .name("State")
            .column("store_state")
            .uniqueMembers(true)
            .hideMemberIf(HideMemberIfEnum.IF_BLANK_NAME)
            .build(),
        LEVEL_5_2_C = LevelRBuilder
            .builder()
            .name("City")
            .column("store_city")
            .uniqueMembers(false)
            .hideMemberIf(HideMemberIfEnum.IF_BLANK_NAME)
            .build(),
        LEVEL_5_3 = LevelRBuilder
            .builder()
            .name("Media Type")
            .column("media_type")
            .uniqueMembers(true)
            .build(),
        LEVEL_5_4 = LevelRBuilder
            .builder()
            .name("Promotion Name")
            .column("promotion_name")
            .uniqueMembers(true)
            .build(),
        LEVEL_5_5_A = LevelRBuilder
            .builder()
            .name("Country")
            .column("country")
            .uniqueMembers(true)
            .build(),
        LEVEL_5_5_B = LevelRBuilder
            .builder()
            .name("State Province")
            .column("state_province")
            .uniqueMembers(true)
            .build(),
        LEVEL_5_5_C = LevelRBuilder
            .builder()
            .name("City")
            .column("city")
            .uniqueMembers(false)
            .build(),
        LEVEL_5_5_D = LevelRBuilder
            .builder()
            .name("Name")
            .uniqueMembers(true)
            //KeyExpressions missing
            .keyExpression(ExpressionViewRBuilder.builder()
                .sql(List.of(
                    SQLRBuilder.builder().dialect("oracle").content("\"fname\" || ' ' || \"lname\"").build(),
                    SQLRBuilder.builder().dialect("hsqldb").content("\"fname\" || ' ' || \"lname\"").build(),
                    SQLRBuilder.builder().dialect("access").content("fname + ' ' + lname").build(),
                    SQLRBuilder.builder().dialect("postgres").content("\"fname\" || ' ' || \"lname\"").build(),
                    SQLRBuilder.builder().dialect("mysql").content("CONCAT(`customer`.`fname`, ' ', `customer`" +
                        ".`lname`)").build(),
                    SQLRBuilder.builder().dialect("mariadb").content("CONCAT(`customer`.`fname`, ' ', `customer`" +
                        ".`lname`)").build(),
                    SQLRBuilder.builder().dialect("mssql").content("fname + ' ' + lname").build(),
                    SQLRBuilder.builder().dialect("derby").content("\"customer\".\"fullname\"").build(),
                    SQLRBuilder.builder().dialect("db2").content("CONCAT(CONCAT(\"customer\".\"fname\", ' '), " +
                        "\"customer\".\"lname\")").build(),
                    SQLRBuilder.builder().dialect("luciddb").content("\"fname\" || ' ' || \"lname\"").build(),
                    SQLRBuilder.builder().dialect("neoview").content("\"customer\".\"fullname\"").build(),
                    SQLRBuilder.builder().dialect("snowflake").content("\"customer\".\"fullname\"").build(),
                    SQLRBuilder.builder().dialect("generic").content("fullname").build()
                ))
                .build())
            .property(List.of(
                PROPERTY_5_5_D_1,
                PROPERTY_5_5_D_2,
                PROPERTY_5_5_D_3,
                PROPERTY_5_5_D_4))
            .build(),
        LEVEL_5_6 = LevelRBuilder
            .builder()
            .name("Education Level")
            .column("education")
            .uniqueMembers(true)
            .build(),
        LEVEL_5_7 = LevelRBuilder
            .builder()
            .name("Gender")
            .column("gender")
            .uniqueMembers(true)
            .build(),
        LEVEL_5_8 = LevelRBuilder
            .builder()
            .name("Marital Status")
            .column("marital_status")
            .uniqueMembers(true)
            .build(),
        LEVEL_5_9 = LevelRBuilder
            .builder()
            .name("Yearly Income")
            .column("yearly_income")
            .uniqueMembers(true)
            .build(),
        LEVEL_6_1 = LevelRBuilder
            .builder()
            .name("Gender")
            .column("gender")
            .uniqueMembers(true)
            .build(),

    LEVEL_SHARED_1_A = LevelRBuilder
        .builder()
        .name("Store Country")
        .column("store_country")
        .uniqueMembers(true)
        .build(),
        LEVEL_SHARED_1_B = LevelRBuilder
            .builder()
            .name("Store State")
            .column("store_state")
            .uniqueMembers(true)
            .build(),
        LEVEL_SHARED_1_C = LevelRBuilder
            .builder()
            .name("Store City")
            .column("store_city")
            .uniqueMembers(false)
            .build(),
        LEVEL_SHARED_1_D = LevelRBuilder
            .builder()
            .name("Store Name")
            .column("store_name")
            .uniqueMembers(true)
            .property(List.of(PROPERTY_1_A,
                PROPERTY_1_B,
                PROPERTY_1_C,
                PROPERTY_1_D,
                PROPERTY_1_E,
                PROPERTY_1_F,
                PROPERTY_1_G,
                PROPERTY_1_H
            ))
            .build(),
        LEVEL_SHARED_2 = LevelRBuilder
            .builder()
            .name("Store Sqft")
            .column("store_sqft")
            .type(TypeEnum.NUMERIC)
            .uniqueMembers(true)
            .build(),
        LEVEL_SHARED_3 = LevelRBuilder
            .builder()
            .name("Store Type")
            .column("store_type")
            .uniqueMembers(true)
            .build(),
        LEVEL_SHARED_4_A_1 = LevelRBuilder
            .builder()
            .name("Year")
            .column("the_year")
            .uniqueMembers(true)
            .type(TypeEnum.NUMERIC)
            .levelType(LevelTypeEnum.TIME_YEARS)
            .build(),
        LEVEL_SHARED_4_A_2 = LevelRBuilder
            .builder()
            .name("Quarter")
            .column("quarter")
            .uniqueMembers(false)
            .levelType(LevelTypeEnum.TIME_QUARTERS)
            .build(),
        LEVEL_SHARED_4_A_3 = LevelRBuilder
            .builder()
            .name("Month")
            .column("month_of_year")
            .uniqueMembers(false)
            .type(TypeEnum.NUMERIC)
            .levelType(LevelTypeEnum.TIME_MONTHS)
            .build(),
        LEVEL_SHARED_4_B_1 = LevelRBuilder
            .builder()
            .name("Year")
            .column("the_year")
            .type(TypeEnum.NUMERIC)
            .uniqueMembers(true)
            .levelType(LevelTypeEnum.TIME_YEARS)
            .build(),
        LEVEL_SHARED_4_B_2 = LevelRBuilder
            .builder()
            .name("Week")
            .column("week_of_year")
            .type(TypeEnum.NUMERIC)
            .uniqueMembers(false)
            .levelType(LevelTypeEnum.TIME_WEEKS)
            .build(),
        LEVEL_SHARED_4_B_3 = LevelRBuilder
            .builder()
            .name("Day")
            .column("day_of_month")
            .uniqueMembers(false)
            .type(TypeEnum.NUMERIC)
            .build(),
        LEVEL_SHARED_5_1 = LevelRBuilder
            .builder()
            .name("Product Family")
            .table("product_class")
            .column("product_family")
            .uniqueMembers(true)
            .build(),
        LEVEL_SHARED_5_2 = LevelRBuilder
            .builder()
            .name("Product Department")
            .table("product_class")
            .column("product_department")
            .uniqueMembers(false)
            .build(),
        LEVEL_SHARED_5_3 = LevelRBuilder
            .builder()
            .name("Product Category")
            .table("product_class")
            .column("product_category")
            .uniqueMembers(false)
            .build(),
        LEVEL_SHARED_5_4 = LevelRBuilder
            .builder()
            .name("Product Subcategory")
            .table("product_class")
            .column("product_subcategory")
            .uniqueMembers(false)
            .build(),
        LEVEL_SHARED_5_5 = LevelRBuilder
            .builder()
            .name("Brand Name")
            .table("product")
            .column("brand_name")
            .uniqueMembers(false)
            .build(),
        LEVEL_SHARED_5_6 = LevelRBuilder
            .builder()
            .name("Product Name")
            .table("product")
            .column("product_name")
            .uniqueMembers(true)
            .build(),
        LEVEL_SHARED_6_1 = LevelRBuilder
            .builder()
            .name("Country")
            .column("warehouse_country")
            .uniqueMembers(true)
            .build(),
        LEVEL_SHARED_6_2 = LevelRBuilder
            .builder()
            .name("State Province")
            .column("warehouse_state_province")
            .uniqueMembers(true)
            .build(),
        LEVEL_SHARED_6_3 = LevelRBuilder
            .builder()
            .name("City")
            .column("warehouse_city")
            .uniqueMembers(false)
            .build(),
        LEVEL_SHARED_6_4 = LevelRBuilder
            .builder()
            .name("Warehouse Name")
            .column("warehouse_name")
            .uniqueMembers(true)
            .build();

    public static JoinR
        JOIN_SHARED_1 = new JoinR(List.of(TABLE_JOIN_1_A,
        TABLE_JOIN_1_B
    ),
        null,
        "product_class_id",
        null,
        "product_class_id"),
        JOIN_4_2 = new JoinR(List.of(TABLE_JOIN_4_2_A,
            TABLE_JOIN_4_2_B),
            null,
            "store_id",
            null,
            "store_id"),
        JOIN_4_3 = new JoinR(List.of(TABLE_JOIN_4_3_A,
            TABLE_JOIN_4_3_B),
            null,
            "position_id",
            null,
            "position_id"),
        JOIN_4_4 = new JoinR(List.of(TABLE_JOIN_4_4_A,
            TABLE_JOIN_4_4_B),
            null,
            "store_id",
            null,
            "store_id");

    public static CalculatedMemberPropertyR
        CALCULATEDMEMBER_PROPERTY_1 = CalculatedMemberPropertyRBuilder
        .builder()
        .name("FORMAT_STRING")
        .value("$#,##0.00")
        .build(),
        CALCULATEDMEMBER_PROPERTY_2_1 = CalculatedMemberPropertyRBuilder
            .builder()
            .name("FORMAT_STRING")
            .value("$#,##0.00")
            .build(),
        CALCULATEDMEMBER_PROPERTY_2_2 = CalculatedMemberPropertyRBuilder
            .builder()
            .name("MEMBER_ORDINAL")
            .value("18")
            .build(),
        CALCULATEDMEMBER_PROPERTY_3 = CalculatedMemberPropertyRBuilder
            .builder()
            .name("FORMAT_STRING")
            .value("0.0%")
            .build(),
        CALCULATEDMEMBER_PROPERTY_6_1 = CalculatedMemberPropertyRBuilder
            .builder()
            .name("MEMBER_ORDINAL")
            .value("1")
            .build(),
        CALCULATEDMEMBER_PROPERTY_6_2 = CalculatedMemberPropertyRBuilder
            .builder()
            .name("MEMBER_ORDINAL")
            .value("2")
            .build(),
        CALCULATEDMEMBER_PROPERTY_6_3 = CalculatedMemberPropertyRBuilder
            .builder()
            .name("MEMBER_ORDINAL")
            .value("3")
            .build(),
        CALCULATEDMEMBER_PROPERTY_6_4 = CalculatedMemberPropertyRBuilder
            .builder()
            .name("MEMBER_ORDINAL")
            .value("6")
            .build(),
        CALCULATEDMEMBER_PROPERTY_6_5 = CalculatedMemberPropertyRBuilder
            .builder()
            .name("MEMBER_ORDINAL")
            .value("7")
            .build(),
        CALCULATEDMEMBER_PROPERTY_6_1_A = CalculatedMemberPropertyRBuilder
            .builder()
            .name("FORMAT_STRING")
            .value("$#,##0.00")
            .build(),
        CALCULATEDMEMBER_PROPERTY_6_1_B = CalculatedMemberPropertyRBuilder
            .builder()
            .name("MEMBER_ORDINAL")
            .value("4")
            .build(),
        CALCULATEDMEMBER_PROPERTY_6_2_A = CalculatedMemberPropertyRBuilder
            .builder()
            .name("MEMBER_ORDINAL")
            .value("5")
            .build();
    public static MeasureR
        MEASURE_1_1 = MeasureRBuilder
        .builder()
        .name("Unit Sales")
        .column("unit_sales")
        .aggregator("sum")
        .formatString("Standard")
        .build(),
        MEASURE_1_2 = MeasureRBuilder
            .builder()
            .name("Store Cost")
            .column("sotre_cost")
            .aggregator("sum")
            .formatString("#,###.00")
            .build(),
        MEASURE_1_3 = MeasureRBuilder
            .builder()
            .name("Store Sales")
            .column("store_sales")
            .aggregator("sum")
            .formatString("#,###.00")
            .build(),
        MEASURE_1_4 = MeasureRBuilder
            .builder()
            .name("Sales Count")
            .column("product_id")
            .aggregator("count")
            .formatString("#,###")
            .build(),
        MEASURE_1_5 = MeasureRBuilder
            .builder()
            .name("Customer Count")
            .column("customer_id")
            .aggregator("distinct-count")
            .formatString("#,###")
            .build(),
        MEASURE_1_6 = MeasureRBuilder
            .builder()
            .name("Promotion Sales")
            .aggregator("sum")
            .formatString("#,###.00")
            .measureExpression(MEASUREEXPRESSION_1)
            .build(),
        MEASURE_2_1 = MeasureRBuilder
            .builder()
            .name("Store Invoice")
            .column("store_invoice")
            .aggregator("sum")
            .build(),
        MEASURE_2_2 = MeasureRBuilder
            .builder()
            .name("Supply Time")
            .column("supply_time")
            .aggregator("sum")
            .build(),
        MEASURE_2_3 = MeasureRBuilder
            .builder()
            .name("Warehouse Cost")
            .column("warehouse_cost")
            .aggregator("sum")
            .build(),
        MEASURE_2_4 = MeasureRBuilder
            .builder()
            .name("Warehouse Sales")
            .column("warehouse_sales")
            .aggregator("sum")
            .build(),
        MEASURE_2_5 = MeasureRBuilder
            .builder()
            .name("Units Shipped")
            .column("units_shipped")
            .aggregator("sum")
            .formatString("#.0")
            .build(),
        MEASURE_2_6 = MeasureRBuilder
            .builder()
            .name("Units Ordered")
            .column("units_ordered")
            .aggregator("sum")
            .formatString("#.0")
            .build(),
        MEASURE_2_7 = MeasureRBuilder
            .builder()
            .name("Warehouse Profit")
            .aggregator("sum")
            .measureExpression(MEASUREEXPRESSION_2_7)
            .build(),
        MEASURE_3_1 = MeasureRBuilder
            .builder()
            .name("Store Sqft")
            .column("store_sqft")
            .aggregator("sum")
            .formatString("#,###")
            .build(),
        MEASURE_3_2 = MeasureRBuilder
            .builder()
            .name("Grocery Sqft")
            .column("grocery_sqft")
            .aggregator("sum")
            .formatString("#,###")
            .build(),
        MEASURE_4_1 = MeasureRBuilder
            .builder()
            .name("Org Salary")
            .column("salary_paid")
            .aggregator("sum")
            .formatString("Currency")
            .build(),
        MEASURE_4_2 = MeasureRBuilder
            .builder()
            .name("Count")
            .column("employee_id")
            .aggregator("count")
            .formatString("#,#")
            .build(),
        MEASURE_4_3 = MeasureRBuilder
            .builder()
            .name("Number of Employees")
            .column("employee_id")
            .aggregator("distinct-count")
            .formatString("#,#")
            .build(),
        MEASURE_5_1 = MeasureRBuilder
            .builder()
            .name("Unit Sales")
            .column("unit_sales")
            .aggregator("sum")
            .formatString("Standard")
            .build(),
        MEASURE_5_2 = MeasureRBuilder
            .builder()
            .name("Store Cost")
            .column("store_cost")
            .aggregator("sum")
            .formatString("#,###.00")
            .build(),
        MEASURE_5_3 = MeasureRBuilder
            .builder()
            .name("Store Sales")
            .column("store_sales")
            .aggregator("sum")
            .formatString("#,###.00")
            .build(),
        MEASURE_5_4 = MeasureRBuilder
            .builder()
            .name("Sales Count")
            .column("product_id")
            .aggregator("count")
            .formatString("#,###")
            .build(),
        MEASURE_5_5 = MeasureRBuilder
            .builder()
            .name("Customer Count")
            .column("customer_id")
            .aggregator("distinct-count")
            .formatString("#,###")
            .build(),
        MEASURE_6_1 = MeasureRBuilder
            .builder()
            .name("Sales Count")
            .column("product_id")
            .aggregator("count")
            .formatString("#,###")
            .calculatedMemberProperty(List.of(CALCULATEDMEMBER_PROPERTY_6_1))
            .build(),
        MEASURE_6_2 = MeasureRBuilder
            .builder()
            .name("Unit Sales")
            .column("unit_sales")
            .aggregator("sum")
            .formatString("Standard")
            .calculatedMemberProperty(List.of(CALCULATEDMEMBER_PROPERTY_6_2))
            .build(),
        MEASURE_6_3 = MeasureRBuilder
            .builder()
            .name("Store Sales")
            .column("store_sales")
            .aggregator("sum")
            .formatString("#,###.00")
            .calculatedMemberProperty(List.of(CALCULATEDMEMBER_PROPERTY_6_3))
            .build(),
        MEASURE_6_4 = MeasureRBuilder
            .builder()
            .name("Store Cost")
            .column("store_cost")
            .aggregator("sum")
            .formatString("#,###.00")
            .calculatedMemberProperty(List.of(CALCULATEDMEMBER_PROPERTY_6_4))
            .build(),
        MEASURE_6_5 = MeasureRBuilder
            .builder()
            .name("Customer Count")
            .column("customer_id")
            .aggregator("distinct-count")
            .formatString("#,###")
            .calculatedMemberProperty(List.of(CALCULATEDMEMBER_PROPERTY_6_5))
            .build();

    public static HierarchyR
        HIERARCHY_1 = HierarchyRBuilder
        .builder()
        .allMemberName("All Media")
        .hasAll(true)
        .primaryKey("promotion_id")
        .defaultMember("All Media")
        .table(TABLE_1)
        .level(List.of(LEVEL_1_1))
        .build(),
        HIERARCHY_2 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Promotions")
            .primaryKey("promotion_id")
            .defaultMember("[All Promotions]")
            .table(TABLE_2)
            .level(List.of(LEVEL_1_2))
            .build(),
        HIERARCHY_3 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Customors")
            .primaryKey("customor_id")
            .table(TABLE_3)
            .level(List.of(LEVEL_1_3_A,
                LEVEL_1_3_B,
                LEVEL_1_3_C,
                LEVEL_1_3_D))
            .build(),
        HIERARCHY_4 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("customer_id")
            .table(TABLE_4)
            .level(List.of(LEVEL_1_4))
            .build(),
        HIERARCHY_5 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Genders")
            .primaryKey("customer_id")
            .table(TABLE_5)
            .level(List.of(LEVEL_1_5))
            .build(),
        HIERARCHY_6 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Merital Status")
            .primaryKey("customer_id")
            .table(TABLE_6)
            .level(List.of(LEVEL_1_6))
            .build(),
        HIERARCHY_7 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("customer_id")
            .table(TABLE_7)
            .level(List.of(LEVEL_1_7))
            .build(),
        HIERARCHY_3_1 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .level(List.of(LEVEL_3_1))
            .build(),
        HIERARCHY_3_2 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .level(List.of(LEVEL_3_2))
            .build(),
        HIERARCHY_4_1 = HierarchyRBuilder
            .builder()
            .hasAll(false)
            .primaryKey("the_date")
            .table(TABLE_4_1)
            .level(List.of(
                LEVEL_4_1_A,
                LEVEL_4_1_B,
                LEVEL_4_1_C))
            .build(),
        HIERARCHY_4_2 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("employee_id")
            .primaryKeyTable("employee")
            .join(JOIN_4_2)
            .level(List.of(
                LEVEL_4_2_A,
                LEVEL_4_2_B,
                LEVEL_4_2_C,
                LEVEL_4_2_D))
            .build(),
        HIERARCHY_4_3 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("employee_id")
            .primaryKeyTable("employee")
            .join(JOIN_4_3)
            .level(List.of(LEVEL_4_3))
            .build(),
        HIERARCHY_4_4 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKeyTable("employee")
            .primaryKey("employee_id")
            .join(JOIN_4_4)
            .level(List.of(LEVEL_4_4))
            .build(),
        HIERARCHY_4_5 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Position")
            .primaryKey("employee_id")
            .table(TABLE_4_5)
            .level(List.of(LEVEL_4_5_A,
                LEVEL_4_5_B))
            .build(),
        HIERARCHY_4_6 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("department_id")
            .table(TABLE_4_6)
            .level(List.of(LEVEL_4_6))
            .build(),
        HIERARCHY_4_7 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberCaption("All Employees")
            .primaryKey("employee_id")
            .table(TABLE_4_7)
            .level(List.of(LEVEL_4_7))
            .build(),
        HIERARCHY_5_1 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("store_id")
            .table(TABLE_5_1)
            .level(List.of(LEVEL_5_1_A,
                LEVEL_5_1_B,
                LEVEL_5_1_C,
                LEVEL_5_1_D))
            .build(),
        HIERARCHY_5_2 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("store_id")
            .table(TABLE_5_2)
            .level(List.of(LEVEL_5_2_A,
                LEVEL_5_2_B,
                LEVEL_5_2_C))
            .build(),
        HIERARCHY_5_3 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Media")
            .primaryKey("promotion_id")
            .table(TABLE_5_3)
            .level(List.of(LEVEL_5_3))
            .build(),
        HIERARCHY_5_4 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Promotions")
            .primaryKey("promotion_id")
            .table(TABLE_5_4)
            .level(List.of(LEVEL_5_4))
            .build(),
        HIERARCHY_5_5 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Customers")
            .primaryKey("customer_id")
            .table(TABLE_5_5)
            .level(List.of(LEVEL_5_5_A,
                LEVEL_5_5_B,
                LEVEL_5_5_C,
                LEVEL_5_5_D))
            .build(),
        HIERARCHY_5_6 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("customer_id")
            .table(TABLE_5_6)
            .level(List.of(LEVEL_5_6))
            .build(),
        HIERARCHY_5_7 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Gender")
            .primaryKey("customer_id")
            .table(TABLE_5_7)
            .level(List.of(LEVEL_5_7))
            .build(),
        HIERARCHY_5_8 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Marital Status")
            .primaryKey("customer_id")
            .table(TABLE_5_8)
            .level(List.of(LEVEL_5_8))
            .build(),
        HIERARCHY_5_9 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("customer_id")
            .table(TABLE_5_9)
            .level(List.of(LEVEL_5_9))
            .build(),
        HIERARCHY_6_1 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Gender")
            .primaryKey("customer_id")
            .table(TABLE_6_1)
            .level(List.of(LEVEL_6_1))
            .build(),
        HIERARCHY_SCHEMA_1 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("store_id")
            .table(TABLE_SHARED_1)
            .level(List.of(LEVEL_SHARED_1_A,
                LEVEL_SHARED_1_B,
                LEVEL_SHARED_1_C,
                LEVEL_SHARED_1_D
            ))
            .build(),
        HIERARCHY_SCHEMA_2 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("store_id")
            .table(TABLE_SHARED_2)
            .level(List.of(LEVEL_SHARED_2))
            .build(),
        HIERARCHY_SCHEMA_3 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("store_id")
            .table(TABLE_SHARED_3)
            .level(List.of(LEVEL_SHARED_3))
            .build(),
        HIERARCHY_SCHEMA_4_1 = HierarchyRBuilder
            .builder()
            .hasAll(false)
            .primaryKey("time_id")
            .table(TABLE_SHARED_4_A)
            .level(List.of(LEVEL_SHARED_4_A_1,
                LEVEL_SHARED_4_A_2,
                LEVEL_SHARED_4_A_3
            ))
            .build(),
        HIERARCHY_SCHEMA_4_2 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("time_id")
            .table(TABLE_SHARED_4_B)
            .level(List.of(LEVEL_SHARED_4_B_1,
                LEVEL_SHARED_4_B_2,
                LEVEL_SHARED_4_B_3
            ))
            .build(),
        HIERARCHY_SCHEMA_5 = HierarchyRBuilder
            .builder()
            .level(List.of(LEVEL_SHARED_5_1,
                LEVEL_SHARED_5_2,
                LEVEL_SHARED_5_3,
                LEVEL_SHARED_5_4,
                LEVEL_SHARED_5_5,
                LEVEL_SHARED_5_6))
            .join(JOIN_SHARED_1)
            .build(),
        HIERARCHY_SCHEMA_6 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .primaryKey("warehouse_id")
            .table(TABLE_SHARED_6)
            .level(List.of(LEVEL_SHARED_6_1,
                LEVEL_SHARED_6_2,
                LEVEL_SHARED_6_3,
                LEVEL_SHARED_6_4))
            .build();

    public static DimensionUsage
        DIMENSION_USAGE_1 = DimensionUsageRBuilder
        .builder()
        .name("Store")
        .source("Store")
        .foreignKey("store_id")
        .build(),
        DIMENSION_USAGE_2 = DimensionUsageRBuilder
            .builder()
            .name("Store Size in SQFT")
            .source("Store Size in SQFT")
            .foreignKey("store_id")
            .build(),
        DIMENSION_USAGE_3 = DimensionUsageRBuilder
            .builder()
            .name("Store Type")
            .source("Store Type")
            .foreignKey("store_id")
            .build(),
        DIMENSION_USAGE_4 = DimensionUsageRBuilder
            .builder()
            .name("Time")
            .source("Time")
            .foreignKey("time_id")
            .build(),
        DIMENSION_USAGE_5 = DimensionUsageRBuilder
            .builder()
            .name("Product")
            .source("Product")
            .foreignKey("product_id")
            .build(),
        DIMENSION_USAGE_6 = DimensionUsageRBuilder
            .builder()
            .name("Warehouse")
            .source("Warehouse")
            .foreignKey("warehouse_id")
            .build(),
        DIMENSION_USAGE_3_1 = DimensionUsageRBuilder
            .builder()
            .name("Store")
            .source("Store")
            .build();

    public static PrivateDimensionR
        DIMENSION_1 = PrivateDimensionRBuilder
        .builder()
        .name("Promotion Media")
        .foreignKey("promotion_id")
        .hierarchy(List.of(HIERARCHY_1))
        .build(),
        DIMENSION_2 = PrivateDimensionRBuilder
            .builder()
            .name("Promotions")
            .foreignKey("promotion_id")
            .hierarchy(List.of(HIERARCHY_2))
            .build(),
        DIMENSION_3 = PrivateDimensionRBuilder
            .builder()
            .name("Customers")
            .foreignKey("customer_id")
            .hierarchy(List.of(HIERARCHY_3))
            .build(),
        DIMENSION_4 = PrivateDimensionRBuilder
            .builder()
            .name("Education Level")
            .foreignKey("customer_id")
            .hierarchy(List.of(HIERARCHY_4))
            .build(),
        DIMENSION_5 = PrivateDimensionRBuilder
            .builder()
            .name("Gender")
            .foreignKey("customer_id")
            .hierarchy(List.of(HIERARCHY_5))
            .build(),
        DIMENSION_6 = PrivateDimensionRBuilder
            .builder()
            .name("Material Status")
            .foreignKey("customer_id")
            .hierarchy(List.of(HIERARCHY_6))
            .build(),
        DIMENSION_7 = PrivateDimensionRBuilder
            .builder()
            .name("Yearly Income")
            .foreignKey("customer_id")
            .hierarchy(List.of(HIERARCHY_7))
            .build(),
        DIMENSION_3_1 = PrivateDimensionRBuilder
            .builder()
            .name("Store Type")
            .hierarchy(List.of(HIERARCHY_3_1))
            .build(),
        DIMENSION_3_2 = PrivateDimensionRBuilder
            .builder()
            .name("Has coffee bar")
            .hierarchy(List.of(HIERARCHY_3_2))
            .build(),
        DIMENSION_4_1 = PrivateDimensionRBuilder
            .builder()
            .name("Time")
            .type(DimensionTypeEnum.TIME_DIMENSION)
            .foreignKey("pay_date")
            .hierarchy(List.of(HIERARCHY_4_1))
            .build(),
        DIMENSION_4_2 = PrivateDimensionRBuilder
            .builder()
            .name("Store")
            .foreignKey("employee_id")
            .hierarchy(List.of(HIERARCHY_4_2))
            .build(),
        DIMENSION_4_3 = PrivateDimensionRBuilder
            .builder()
            .name("Pay Type")
            .foreignKey("employee_id")
            .hierarchy(List.of(HIERARCHY_4_3))
            .build(),
        DIMENSION_4_4 = PrivateDimensionRBuilder
            .builder()
            .name("Store Type")
            .foreignKey("employee_id")
            .hierarchy(List.of(HIERARCHY_4_4))
            .build(),
        DIMENSION_4_5 = PrivateDimensionRBuilder
            .builder()
            .name("Position")
            .foreignKey("employee_id")
            .hierarchy(List.of(HIERARCHY_4_5))
            .build(),
        DIMENSION_4_6 = PrivateDimensionRBuilder
            .builder()
            .name("Department")
            .foreignKey("department_id")
            .hierarchy(List.of(HIERARCHY_4_6))
            .build(),
        DIMENSION_4_7 = PrivateDimensionRBuilder
            .builder()
            .name("Employees")
            .foreignKey("employee_id")
            .hierarchy(List.of(HIERARCHY_4_7))
            .build(),
        DIMENSION_5_1 = PrivateDimensionRBuilder
            .builder()
            .name("Store")
            .foreignKey("store_id")
            .hierarchy(List.of(HIERARCHY_5_1))
            .build(),
        DIMENSION_5_2 = PrivateDimensionRBuilder
            .builder()
            .name("Geography")
            .foreignKey("store_id")
            .hierarchy(List.of(HIERARCHY_5_2))
            .build(),
        DIMENSION_5_3 = PrivateDimensionRBuilder
            .builder()
            .name("Promotion Media")
            .foreignKey("promotion_id")
            .hierarchy(List.of(HIERARCHY_5_3))
            .build(),
        DIMENSION_5_4 = PrivateDimensionRBuilder
            .builder()
            .name("Promotions")
            .foreignKey("promotion_id")
            .hierarchy(List.of(HIERARCHY_5_4))
            .build(),
        DIMENSION_5_5 = PrivateDimensionRBuilder
            .builder()
            .name("Customers")
            .foreignKey("customer_id")
            .hierarchy(List.of(HIERARCHY_5_5))
            .build(),
        DIMENSION_5_6 = PrivateDimensionRBuilder
            .builder()
            .name("Education Level")
            .foreignKey("customer_id")
            .hierarchy(List.of(HIERARCHY_5_6))
            .build(),
        DIMENSION_5_7 = PrivateDimensionRBuilder
            .builder()
            .name("Gender")
            .foreignKey("customer_id")
            .hierarchy(List.of(HIERARCHY_5_7))
            .build(),
        DIMENSION_5_8 = PrivateDimensionRBuilder
            .builder()
            .name("Marital Status")
            .foreignKey("customer_id")
            .hierarchy(List.of(HIERARCHY_5_8))
            .build(),
        DIMENSION_5_9 = PrivateDimensionRBuilder
            .builder()
            .name("Yearly Income")
            .foreignKey("customer_id")
            .hierarchy(List.of(HIERARCHY_5_9))
            .build(),
        DIMENSION_6_1 = PrivateDimensionRBuilder
            .builder()
            .name("Gender")
            .foreignKey("customer_id")
            .hierarchy(List.of(HIERARCHY_6_1))
            .build(),
    DIMENSION_SCHEMA_1 = PrivateDimensionRBuilder
        .builder()
        .name("Store")
        .hierarchy(List.of(HIERARCHY_SCHEMA_1))
        .build(),
    DIMENSION_SCHEMA_2 = PrivateDimensionRBuilder
        .builder()
        .name("Store Size in SQFT")
        .hierarchy(List.of(HIERARCHY_SCHEMA_2))
        .build(),
    DIMENSION_SCHEMA_3 = PrivateDimensionRBuilder
        .builder()
        .name("Store Type")
        .hierarchy(List.of(HIERARCHY_SCHEMA_3))
        .build(),
    DIMENSION_SCHEMA_4 = PrivateDimensionRBuilder
        .builder()
        .name("Time")
        .type( DimensionTypeEnum.TIME_DIMENSION)
        .hierarchy(List.of(HIERARCHY_SCHEMA_4_1, HIERARCHY_SCHEMA_4_2))
        .build(),
    DIMENSION_SCHEMA_5 = PrivateDimensionRBuilder
        .builder()
        .name("Product")
        .hierarchy(List.of(HIERARCHY_SCHEMA_5))
        .build(),
    DIMENSION_SCHEMA_6 = PrivateDimensionRBuilder
        .builder()
        .name("Warehouse")
        .hierarchy(List.of(HIERARCHY_SCHEMA_6))
        .build();


    public static AnnotationR
        ANNOTATION_1 = AnnotationRBuilder
        .builder()
        .name("caption.de_DE")
        .content("Verkaufen")
        .build(),
        ANNOTATION_2 = AnnotationRBuilder
            .builder()
            .name("caption.fr_FR")
            .content("Ventes")
            .build(),
        ANNOTATION_3 = AnnotationRBuilder
            .builder()
            .name("description.fr_FR")
            .content("Cube des ventes")
            .build(),
        ANNOTATION_4 = AnnotationRBuilder
            .builder()
            .name("description.de")
            .content("Cube Verkaufen")
            .build(),
        ANNOTATION_5 = AnnotationRBuilder
            .builder()
            .name("description.de_AT")
            .content("Cube den Verkaufen")
            .build();

    public static AggExcludeR
        CUBE1_TABLE_AGG_EXCLUDE1 = AggExcludeRBuilder
        .builder()
        .name("agg_c_special_sales_fact_1997")
        .build(),
        CUBE1_TABLE_AGG_EXCLUDE2 = AggExcludeRBuilder
            .builder()
            .name("agg_lc_100_sales_fact_1997")
            .build(),
        CUBE1_TABLE_AGG_EXCLUDE3 = AggExcludeRBuilder
            .builder()
            .name("agg_lc_10_sales_fact_1997")
            .build(),
        CUBE1_TABLE_AGG_EXCLUDE4 = AggExcludeRBuilder
            .builder()
            .name("agg_pc_10_sales_fact_1997")
            .build();

    public static AggTable
        CUBE1_TABLE_AGG_TABLE1 = AggNameRBuilder
        .builder()
        .name("agg_c_special_sales_fact_1997")
        .aggFactCount(
            AggColumnNameRBuilder.builder().column("agg_c_special_sales_fact_1997").build())
        .aggIgnoreColumn(List.of(
            AggColumnNameRBuilder.builder().column("foo").build(),
            AggColumnNameRBuilder.builder().column("bar").build()
        ))
        .aggForeignKey(List.of(
            AggForeignKeyRBuilder.builder().factColumn("product_id").aggColumn("PRODUCT_ID").build(),
            AggForeignKeyRBuilder.builder().factColumn("customer_id").aggColumn("CUSTOMER_ID").build(),
            AggForeignKeyRBuilder.builder().factColumn("promotion_id").aggColumn("PROMOTION_ID").build(),
            AggForeignKeyRBuilder.builder().factColumn("store_id").aggColumn("STORE_ID").build()
        ))
        .aggMeasure(List.of(
            AggMeasureRBuilder.builder().name("[Measures].[Unit Sales]").column("UNIT_SALES_SUM").build(),
            AggMeasureRBuilder.builder().name("[Measures].[Store Cost]").column("STORE_COST_SUM").build(),
            AggMeasureRBuilder.builder().name("[Measures].[Store Sales]").column("STORE_SALES_SUM").build()
        ))
        .aggLevel(List.of(
            AggLevelRBuilder.builder().name("[Time].[Year]").column("TIME_YEAR").build(),
            AggLevelRBuilder.builder().name("[Time].[Quarter]").column("TIME_QUARTER").build(),
            AggLevelRBuilder.builder().name("[Time].[Month]").column("TIME_MONTH").build()
        ))
        .build();

    public static TableR
        CUBE1_TABLE = new TableR("sales_fact_1997",
        List.of(
            CUBE1_TABLE_AGG_EXCLUDE1,
            CUBE1_TABLE_AGG_EXCLUDE2,
            CUBE1_TABLE_AGG_EXCLUDE3,
            CUBE1_TABLE_AGG_EXCLUDE4
        ),
        List.of(
            CUBE1_TABLE_AGG_TABLE1
        )),
        CUBE2_TABLE = new TableR("inventory_fact_1997"),
        CUBE3_TABLE = new TableR("store"),
        CUBE4_TABLE = new TableR("salary"),
        CUBE5_TABLE = new TableR("sales_fact_1997",
            List.of(
                AggExcludeRBuilder.builder().name("agg_pc_10_sales_fact_1997").build(),
                AggExcludeRBuilder.builder().name("agg_lc_10_sales_fact_1997").build()
            ),
            null),
        CUBE6_TABLE = new TableR("sales_fact_1997");

    public static CalculatedMemberR
        CALCULATEDMEMBER_1 = CalculatedMemberRBuilder
        .builder()
        .name("Profit")
        .dimension("Measures")
        .formula("[Measures].[Store Sales] - [Measures].[Store Cost]")
        .calculatedMemberProperty(List.of(CALCULATEDMEMBER_PROPERTY_1))
        .build(),
        CALCULATEDMEMBER_2 = CalculatedMemberRBuilder
            .builder()
            .name("Profit last Period")
            .dimension("Measures")
            .formula("COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER),    Measures.[Profit]")
            .visible(false)
            .calculatedMemberProperty(List.of(
                CALCULATEDMEMBER_PROPERTY_2_1,
                CALCULATEDMEMBER_PROPERTY_2_2))
            .build(),
        CALCULATEDMEMBER_3 = CalculatedMemberRBuilder
            .builder()
            .name("Profit Growth")
            .dimension("Measures")
            .formula("([Measures].[Profit] - [Measures].[Profit last Period]) / [Measures].[Profit last Period]")
            .visible(true)
            .caption("Gewinn-Wachstum")
            .calculatedMemberProperty(List.of(CALCULATEDMEMBER_PROPERTY_3))
            .build(),
        CALCULATEDMEMBER_2_1 = CalculatedMemberRBuilder
            .builder()
            .name("Average Warehouse Sale")
            .dimension("Measures")
            .formula("[Measures].[Warehouse Sales] / [Measures].[Warehouse Cost]")
            .calculatedMemberProperty(List.of(CALCULATEDMEMBER_PROPERTY_2_1))
            .build(),
        CALCULATEDMEMBER_4_1 = CalculatedMemberRBuilder
            .builder()
            .name("Employee Salary")
            .dimension("Measures")
            .formatString("Currency")
            .formula("([Employees].currentmember.datamember, [Measures].[Org Salary])")
            .build(),
        CALCULATEDMEMBER_4_2 = CalculatedMemberRBuilder
            .builder()
            .name("Avg Salary")
            .dimension("Measures")
            .formatString("Currency")
            .formula("[Measures].[Org Salary]/[Measures].[Number of Employees]")
            .build(),
        CALCULATEDMEMBER_6_1 = CalculatedMemberRBuilder
            .builder()
            .name("Profit")
            .dimension("Measures")
            .formulaElement(FormulaRBuilder.builder().cdata("[Measures].[Store Sales] - [Measures].[Store Cost]").build())
            .calculatedMemberProperty(List.of(CALCULATEDMEMBER_PROPERTY_6_1_A,
                CALCULATEDMEMBER_PROPERTY_6_1_B))
            .build(),
        CALCULATEDMEMBER_6_2 = CalculatedMemberRBuilder
            .builder()
            .name("Profit last Period")
            .dimension("Measures")
            .formula("COALESCEEMPTY((Measures.[Profit], [Time].[Time].PREVMEMBER),    Measures.[Profit])")
            .visible(false)
            .calculatedMemberProperty(List.of(CALCULATEDMEMBER_PROPERTY_6_2_A))
            .build(),
        VIRTUAL_CALCULATED_MEMBER_1 = CalculatedMemberRBuilder
            .builder()
            .name("Profit Per Unit Shipped")
            .dimension("Measures")
            .formulaElement(FormulaRBuilder
                .builder()
                .cdata("[Measures].[Profit] / [Measures].[Units Shipped]</Formula")
                .build())
            .build();

    public static NamedSetR NAMED_SET_2_1 = NamedSetRBuilder.builder()
        .name("Top Sellers")
        .formulaElement(
            FormulaRBuilder.builder()
                .cdata("TopCount([Warehouse].[Warehouse Name].MEMBERS, 5, [Measures].[Warehouse Sales])").build())
        .build();

    public static CubeR
        CUBE_1 = CubeRBuilder
        .builder()
        .name(CUBE_NAME_1)
        .defaultMeasure("Unit Sales")
        .annotations(List.of(ANNOTATION_1,
            ANNOTATION_2,
            ANNOTATION_3,
            ANNOTATION_4,
            ANNOTATION_5))
        .fact(CUBE1_TABLE)
        .dimensionUsageOrDimension(List.of(
            DIMENSION_USAGE_1,
            DIMENSION_USAGE_2,
            DIMENSION_USAGE_3,
            DIMENSION_USAGE_4,
            DIMENSION_USAGE_5,
            DIMENSION_1,
            DIMENSION_2,
            DIMENSION_3,
            DIMENSION_4,
            DIMENSION_5,
            DIMENSION_6,
            DIMENSION_7))
        .measure(List.of(
            MEASURE_1_1,
            MEASURE_1_2,
            MEASURE_1_3,
            MEASURE_1_4,
            MEASURE_1_5,
            MEASURE_1_6))
        .calculatedMember(List.of(
            CALCULATEDMEMBER_1,
            CALCULATEDMEMBER_2,
            CALCULATEDMEMBER_3))
        .build(),
        CUBE_2 = CubeRBuilder
            .builder()
            .name("Warehouse")
            .fact(CUBE2_TABLE)
            .dimensionUsageOrDimension(List.of(DIMENSION_USAGE_1,
                DIMENSION_USAGE_2,
                DIMENSION_USAGE_3,
                DIMENSION_USAGE_4,
                DIMENSION_USAGE_5,
                DIMENSION_USAGE_6))
            .measure(List.of(
                MEASURE_2_1,
                MEASURE_2_2,
                MEASURE_2_3,
                MEASURE_2_4,
                MEASURE_2_5,
                MEASURE_2_6,
                MEASURE_2_7))
            .calculatedMember(List.of(CALCULATEDMEMBER_2_1))
            .namedSet(List.of(NAMED_SET_2_1))
            .build(),
        CUBE_3 = CubeRBuilder
            .builder()
            .name("Store")
            .fact(CUBE3_TABLE)
            .dimensionUsageOrDimension(List.of(
                DIMENSION_3_1,
                DIMENSION_3_2,
                DIMENSION_USAGE_3_1))
            .measure(List.of(
                MEASURE_3_1,
                MEASURE_3_2))
            .build(),
        CUBE_4 = CubeRBuilder
            .builder()
            .name("HR")
            .fact(CUBE4_TABLE)
            .dimensionUsageOrDimension(List.of(
                DIMENSION_4_1,
                DIMENSION_4_2,
                DIMENSION_4_3,
                DIMENSION_4_4,
                DIMENSION_4_5,
                DIMENSION_4_6,
                DIMENSION_4_7))
            .measure(List.of(
                MEASURE_4_1,
                MEASURE_4_2,
                MEASURE_4_3))
            .calculatedMember(List.of(
                CALCULATEDMEMBER_4_1,
                CALCULATEDMEMBER_4_2))
            .build(),
        CUBE_5 = CubeRBuilder
            .builder()
            .name("Sales Ragged")
            .fact(CUBE5_TABLE)
            .measure(List.of(
                MEASURE_5_1,
                MEASURE_5_2,
                MEASURE_5_3,
                MEASURE_5_4,
                MEASURE_5_5))
            .dimensionUsageOrDimension(List.of(
                DIMENSION_5_1,
                DIMENSION_5_2,
                DIMENSION_5_3,
                DIMENSION_5_4,
                DIMENSION_5_5,
                DIMENSION_5_6,
                DIMENSION_5_7,
                DIMENSION_5_8,
                DIMENSION_5_9,
                DIMENSION_USAGE_2,
                DIMENSION_USAGE_3,
                DIMENSION_USAGE_4,
                DIMENSION_USAGE_5))
            .build(),
        CUBE_6 = CubeRBuilder
            .builder()
            .name("Sales 2")
            .fact(CUBE6_TABLE)
            .measure(List.of(MEASURE_6_1,
                MEASURE_6_2,
                MEASURE_6_3,
                MEASURE_6_4,
                MEASURE_6_5))
            .calculatedMember(List.of(CALCULATEDMEMBER_6_1,
                CALCULATEDMEMBER_6_2))
            .dimensionUsageOrDimension(List.of(DIMENSION_USAGE_4,
                DIMENSION_USAGE_5,
                DIMENSION_6_1))
            .build();

    public static VirtualCubeDimensionR
        VIRTUAL_DIMENSION_1 = VirtualCubeDimensionRBuilder
        .builder()
        .cubeName("Sales")
        .name("Customers")
        .build(),
        VIRTUAL_DIMENSION_2 = VirtualCubeDimensionRBuilder
            .builder()
            .cubeName("Sales")
            .name("Education Level")
            .build(),
        VIRTUAL_DIMENSION_3 = VirtualCubeDimensionRBuilder
            .builder()
            .cubeName("Sales")
            .name("Gender")
            .build(),
        VIRTUAL_DIMENSION_4 = VirtualCubeDimensionRBuilder
            .builder()
            .cubeName("Sales")
            .name("Marital Status")
            .build(),
        VIRTUAL_DIMENSION_5 = VirtualCubeDimensionRBuilder
            .builder()
            .name("Product")
            .build(),
        VIRTUAL_DIMENSION_6 = VirtualCubeDimensionRBuilder
            .builder()
            .cubeName("Sales")
            .name("Promotion Media")
            .build(),
        VIRTUAL_DIMENSION_7 = VirtualCubeDimensionRBuilder
            .builder()
            .cubeName("Sales")
            .name("Promotions")
            .build(),
        VIRTUAL_DIMENSION_8 = VirtualCubeDimensionRBuilder
            .builder()
            .name("Store")
            .build(),
        VIRTUAL_DIMENSION_9 = VirtualCubeDimensionRBuilder
            .builder()
            .name("Time")
            .build(),
        VIRTUAL_DIMENSION_10 = VirtualCubeDimensionRBuilder
            .builder()
            .cubeName("Sales")
            .name("Yearly Income")
            .build(),
        VIRTUAL_DIMENSION_11 = VirtualCubeDimensionRBuilder
            .builder()
            .cubeName("Warehouse")
            .name("Warehouse")
            .build();

    public static VirtualCubeMeasureR
        VIRTUAL_MEASURE_1 = VirtualCubeMeasureRBuilder
        .builder()
        .cubeName("Sales")
        .name("[Measures].[Sales Count]")
        .build(),
        VIRTUAL_MEASURE_2 = VirtualCubeMeasureRBuilder
            .builder()
            .cubeName("Sales")
            .name("[Measures].[Store Cost]")
            .build(),
        VIRTUAL_MEASURE_3 = VirtualCubeMeasureRBuilder
            .builder()
            .cubeName("Sales")
            .name("[Measures].[Store Sales]")
            .build(),
        VIRTUAL_MEASURE_4 = VirtualCubeMeasureRBuilder
            .builder()
            .cubeName("Sales")
            .name("[Measures].[Unit Sales]")
            .build(),
        VIRTUAL_MEASURE_5 = VirtualCubeMeasureRBuilder
            .builder()
            .cubeName("Sales")
            .name("[Measures].[Profit]")
            .build(),
        VIRTUAL_MEASURE_6 = VirtualCubeMeasureRBuilder
            .builder()
            .cubeName("Sales")
            .name("[Measures].[Profit Growth]")
            .build(),
        VIRTUAL_MEASURE_7 = VirtualCubeMeasureRBuilder
            .builder()
            .cubeName("Warehouse")
            .name("[Measures].[Store Invoice]")
            .build(),
        VIRTUAL_MEASURE_8 = VirtualCubeMeasureRBuilder
            .builder()
            .cubeName("Warehouse")
            .name("[Measures].[Supply Time]")
            .build(),
        VIRTUAL_MEASURE_9 = VirtualCubeMeasureRBuilder
            .builder()
            .cubeName("Warehouse")
            .name("[Measures].[Units Ordered]")
            .build(),
        VIRTUAL_MEASURE_10 = VirtualCubeMeasureRBuilder
            .builder()
            .cubeName("Warehouse")
            .name("[Measures].[Units Shipped]")
            .build(),
        VIRTUAL_MEASURE_11 = VirtualCubeMeasureRBuilder
            .builder()
            .cubeName("Warehouse")
            .name("[Measures].[Warehouse Cost]")
            .build(),
        VIRTUAL_MEASURE_12 = VirtualCubeMeasureRBuilder
            .builder()
            .cubeName("Warehouse")
            .name("[Measures].[Warehouse Profit]")
            .build(),
        VIRTUAL_MEASURE_13 = VirtualCubeMeasureRBuilder
            .builder()
            .cubeName("Warehouse")
            .name("[Measures].[Warehouse Sales]")
            .build(),
        VIRTUAL_MEASURE_14 = VirtualCubeMeasureRBuilder
            .builder()
            .cubeName("Warehouse")
            .name("[Measures].[Average Warehouse Sale]")
            .build();

    public static VirtualCubeR
        VIRTUAL_CUBE_1 = VirtualCubeRBuilder
        .builder()
        .name("Warehouse and Sales")
        .defaultMeasure("Store Sales")
        .calculatedMember(List.of(VIRTUAL_CALCULATED_MEMBER_1))
        .virtualCubeMeasure(List.of(VIRTUAL_MEASURE_1,
            VIRTUAL_MEASURE_2,
            VIRTUAL_MEASURE_3,
            VIRTUAL_MEASURE_4,
            VIRTUAL_MEASURE_5,
            VIRTUAL_MEASURE_6,
            VIRTUAL_MEASURE_7,
            VIRTUAL_MEASURE_8,
            VIRTUAL_MEASURE_9,
            VIRTUAL_MEASURE_10,
            VIRTUAL_MEASURE_11,
            VIRTUAL_MEASURE_12,
            VIRTUAL_MEASURE_13,
            VIRTUAL_MEASURE_14))
        .virtualCubeDimension(List.of(VIRTUAL_DIMENSION_1,
            VIRTUAL_DIMENSION_2,
            VIRTUAL_DIMENSION_3,
            VIRTUAL_DIMENSION_4,
            VIRTUAL_DIMENSION_5,
            VIRTUAL_DIMENSION_6,
            VIRTUAL_DIMENSION_7,
            VIRTUAL_DIMENSION_8,
            VIRTUAL_DIMENSION_9,
            VIRTUAL_DIMENSION_10,
            VIRTUAL_DIMENSION_11))
        .build();

    public static RoleR
        ROLE_1 = RoleRBuilder
        .builder().name("California manager")
        .schemaGrant(List.of(SchemaGrantRBuilder
            .builder()
            .access(AccessEnum.NONE)
            .cubeGrant(List.of(CubeGrantRBuilder
                .builder()
                .cube("Sales")
                .access("all")
                .hierarchyGrant(List.of(HierarchyGrantRBuilder
                        .builder()
                        .hierarchy("[Store]")
                        .access(AccessEnum.CUSTOM)
                        .topLevel("[Store].[Store Country]")
                        .memberGrant(List.of(MemberGrantRBuilder
                                .builder()
                                .member("[Store].[USA].[CA]")
                                .access(MemberGrantAccessEnum.ALL)
                                .build(),
                            MemberGrantRBuilder
                                .builder()
                                .member("[Store].[USA].[CA].[Los Angeles]")
                                .access(MemberGrantAccessEnum.NONE)
                                .build()))
                        .build(),
                    HierarchyGrantRBuilder
                        .builder()
                        .hierarchy("[Customers]")
                        .access(AccessEnum.CUSTOM)
                        .topLevel("[Customers].[State Province]")
                        .bottomLevel("[Customers].[City]")
                        .memberGrant(List.of(MemberGrantRBuilder
                                .builder()
                                .member("[Customers].[USA].[CA]")
                                .access(MemberGrantAccessEnum.ALL)
                                .build(),
                            MemberGrantRBuilder
                                .builder()
                                .member("[Customers].[USA].[CA].[Los Angeles]")
                                .access(MemberGrantAccessEnum.NONE)
                                .build()))
                        .build(),
                    HierarchyGrantRBuilder
                        .builder()
                        .hierarchy("[Gender]")
                        .access(AccessEnum.NONE)
                        .build()))
                .build()))
            .build()))
        .build(),
        ROLE_2 = RoleRBuilder
            .builder()
            .name("No HR Cube")
            .schemaGrant(List.of(SchemaGrantRBuilder
                .builder()
                .access(AccessEnum.ALL)
                .cubeGrant(List.of(CubeGrantRBuilder
                    .builder()
                    .cube("HR")
                    .access("none")
                    .build()))
                .build()))
            .build(),
        ROLE_3 = RoleRBuilder
            .builder()
            .name("Administrator")
            .schemaGrant(List.of(SchemaGrantRBuilder
                .builder()
                .access(AccessEnum.ALL)
                .build()))
            .build();

    public static Schema
        SCHEMA = SchemaRBuilder.builder()
        .name(SCHEMA_NAME)
        .dimension(List.of(
            DIMENSION_SCHEMA_1,
            DIMENSION_SCHEMA_2,
            DIMENSION_SCHEMA_3,
            DIMENSION_SCHEMA_4,
            DIMENSION_SCHEMA_5,
            DIMENSION_SCHEMA_6
        ))
        .cube(List.of(CUBE_1,
            CUBE_2,
            CUBE_3,
            CUBE_4,
            CUBE_5,
            CUBE_6))
        .virtualCube(List.of(VIRTUAL_CUBE_1))
        .roles(List.of(ROLE_1, ROLE_2, ROLE_3))
        .build();

    @Override
    public Schema get() {
        return SCHEMA;
    }

}
