package org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.steelwheels.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.HideMemberIfEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CubeR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.HierarchyR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.LevelR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.MeasureR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.PrivateDimensionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.TableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.LevelRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PrivateDimensionRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DbMappingSchemaProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@Component(service = DbMappingSchemaProvider.class, scope = ServiceScope.SINGLETON, property = { "sample.name=SteelWheels",
		"sample.type=record" })
public class SteelWheelRecordDbMappingSchemaProvider implements DbMappingSchemaProvider {

    private static String SCHEMA_NAME = "SteelWheels";
    private static final String CUBE_NAME_1 = "SteelWheelsSales";

    private static LevelR
        LEVEL_1_1 = LevelRBuilder
        .builder()
        .name("Territory")
        .column("TERRITORY")
        .type(TypeEnum.STRING)
        .uniqueMembers(true)
        .levelType(LevelTypeEnum.REGULAR)
        .hideMemberIf(HideMemberIfEnum.NEVER)
        .build(),
        LEVEL_1_2 = LevelRBuilder
            .builder()
            .name("Country")
            .column("COUNTRY")
            .type(TypeEnum.STRING)
            .uniqueMembers(false)
            .levelType(LevelTypeEnum.REGULAR)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .build(),
        LEVEL_1_3 = LevelRBuilder
            .builder()
            .name("State Province")
            .column("STATE")
            .type(TypeEnum.STRING)
            .uniqueMembers(true)
            .levelType(LevelTypeEnum.REGULAR)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .build(),
        LEVEL_1_4 = LevelRBuilder
            .builder()
            .name("City")
            .column("CITY")
            .type(TypeEnum.STRING)
            .uniqueMembers(true)
            .levelType(LevelTypeEnum.REGULAR)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .build(),
        LEVEL_2 = LevelRBuilder
            .builder()
            .name("Customer")
            .column("CUSTOMERNAME")
            .type(TypeEnum.STRING)
            .uniqueMembers(true)
            .levelType(LevelTypeEnum.REGULAR)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .build(),
    LEVEL_3_1 = LevelRBuilder
            .builder()
            .name("Linie")
            .table("products")
            .column("PRODUCTLINE")
            .type(TypeEnum.STRING)
            .uniqueMembers(false)
            .levelType(LevelTypeEnum.REGULAR)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .build(),
        LEVEL_3_2 = LevelRBuilder
            .builder()
            .name("Vendor")
            .table("products")
            .column("PRODUCTVENTOR")
            .type(TypeEnum.STRING)
            .uniqueMembers(false)
            .levelType(LevelTypeEnum.REGULAR)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .build(),
        LEVEL_3_3 = LevelRBuilder
            .builder()
            .name("Product")
            .table("products")
            .column("PRODUCTNAME")
            .type(TypeEnum.STRING)
            .uniqueMembers(true)
            .levelType(LevelTypeEnum.REGULAR)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .build(),
        LEVEL_4_1 = LevelRBuilder
            .builder()
            .name("Years")
            .column("YEAR_ID")
            .type(TypeEnum.INTEGER)
            .uniqueMembers(true)
            .levelType(LevelTypeEnum.TIME_YEARS)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .build(),
        LEVEL_4_2 = LevelRBuilder
            .builder()
            .name("Quarters")
            .column("QTR_NAME")
            .ordinalColumn("QTR_ID")
            .type(TypeEnum.STRING)
            .uniqueMembers(false)
            .levelType(LevelTypeEnum.TIME_QUARTERS)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .build(),
        LEVEL_4_3 = LevelRBuilder
            .builder()
            .name("Month")
            .column("MONTH_NAME")
            .ordinalColumn("MONTH_ID")
            .type(TypeEnum.STRING)
            .uniqueMembers(false)
            .levelType(LevelTypeEnum.TIME_MONTHS)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .build(),
        LEVEL_5 = LevelRBuilder
            .builder()
            .name("Type")
            .column("STATUS")
            .type(TypeEnum.STRING)
            .uniqueMembers(true)
            .levelType(LevelTypeEnum.REGULAR)
            .hideMemberIf(HideMemberIfEnum.NEVER)
            .build();

    private static TableR
        TABLE_1 = new TableR(null, "customer_w_ter", null, List.of()),
        TABLE_2 = new TableR(null, "customer_w_ter", null, List.of()),
        TABLE_3 = new TableR(null, "products", null, List.of()),
        TABLE_4 = new TableR(null, "time", null, List.of()),
        TABLE_CUBE = new TableR(null, "time", null, List.of());

    private static MeasureR
        MEASURE_1 = MeasureRBuilder
        .builder()
        .name("Quantity")
        .column("QUANTITYORDERED")
        .formatString("#,###")
        .aggregator("sum")
        .build(),
        MEASURE_2 = MeasureRBuilder
            .builder()
            .name("Sales")
            .column("TOTALPRICE")
            .formatString("#,###")
            .aggregator("sum")
            .build();

    private static HierarchyR
        HIERARCHY_1 = HierarchyRBuilder
        .builder()
        .allMemberName("All Markets")
        .hasAll(true)
        .primaryKey("CUSTOMERNUMBER")
        .primaryKeyTable("")
        .table(TABLE_1)
        .level(List.of(LEVEL_1_1, LEVEL_1_2, LEVEL_1_3, LEVEL_1_4))
        .build(),
        HIERARCHY_2 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Customers")
            .primaryKey("CUSTOMMERNUMBER")
            .table(TABLE_2)
            .level(List.of(LEVEL_2))
            .build(),
        HIERARCHY_3 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Products")
            .primaryKey("PRODUCTCODE")
            .primaryKeyTable("product")
            .caption("")
            .table(TABLE_3)
            .level(List.of(LEVEL_3_1, LEVEL_3_2, LEVEL_3_3))
            .build(),
        HIERARCHY_4 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Years")
            .primaryKey("TIME_ID")
            .table(TABLE_4)
            .level(List.of(LEVEL_4_1, LEVEL_4_2, LEVEL_4_3))
            .build(),
        HIERARCHY_5 = HierarchyRBuilder
            .builder()
            .hasAll(true)
            .allMemberName("All Status Types")
            .primaryKey("STATUS")
            .level(List.of(LEVEL_5))
            .build();
    private static PrivateDimensionR
        DIMENSION_1 = PrivateDimensionRBuilder
        .builder()
        .name("Markets")
        .foreignKey("CUSTOMERNUMBER")
        .hierarchy(List.of(HIERARCHY_1))
        .build(),
        DIMENSION_2 = PrivateDimensionRBuilder
            .builder()
            .name("Customers")
            .foreignKey("CUSTOMMERNUMBER")
            .hierarchy(List.of(HIERARCHY_2))
            .build(),
        DIMENSION_3 = PrivateDimensionRBuilder
            .builder()
            .name("Product")
            .foreignKey("PRODUCTCODE")
            .hierarchy(List.of(HIERARCHY_3))
            .build(),
        DIMENSION_4 = PrivateDimensionRBuilder
            .builder()
            .name("Time")
            .type(DimensionTypeEnum.TIME_DIMENSION)
            .foreignKey("TIME_ID")
            .hierarchy(List.of(HIERARCHY_4))
            .build(),
        DIMENSION_5 = PrivateDimensionRBuilder
            .builder()
            .name("Order Status")
            .foreignKey("STATUS")
            .hierarchy(List.of(HIERARCHY_5))
            .build();

    private static CubeR
        CUBE_1 = CubeRBuilder.builder()
        .name(CUBE_NAME_1)
        .fact(TABLE_CUBE)
        .cache(true)
        .enabled(true)
        .measure(List.of(MEASURE_1, MEASURE_2))
        .dimensionUsageOrDimension(List.of(DIMENSION_1, DIMENSION_2, DIMENSION_3, DIMENSION_4, DIMENSION_5))
        .build();

    private static Schema
        SCHEMA = SchemaRBuilder.builder()
        .name(SCHEMA_NAME)
        .cube(List.of(CUBE_1))
        .build();

    @Override
    public Schema get() {
        return SCHEMA;
    }

}
