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
package mondrian.rolap.aggmatcher;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.TableR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggColumnNameRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggForeignKeyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggMeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.AggNameRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.LevelRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PrivateDimensionRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier.record.RDbMappingSchemaModifier;

public class BUG_1541077Modifier extends RDbMappingSchemaModifier {

    public BUG_1541077Modifier(MappingSchema mappingSchema) {
        super(mappingSchema);
    }

    /*
        return "<Cube name='Cheques'>\n"
               + "<Table name='cheques'>\n"
               + "<AggName name='agg_lp_xxx_cheques'>\n"
               + "<AggFactCount column='FACT_COUNT'/>\n"
               + "<AggForeignKey factColumn='store_id' aggColumn='store_id' />\n"
               + "<AggMeasure name='[Measures].[Avg Amount]'\n"
               + "   column='amount_AVG' />\n"
               + "</AggName>\n"
                   + "</Table>\n"
                   + "<Dimension name='StoreX' foreignKey='store_id'>\n"
                   + " <Hierarchy hasAll='true' primaryKey='store_id'>\n"
                   + " <Table name='store_x'/>\n"
                   + " <Level name='Store Value' column='value' uniqueMembers='true'/>\n"
                   + " </Hierarchy>\n"
                   + "</Dimension>\n"
                   + "<Dimension name='ProductX' foreignKey='prod_id'>\n"
                   + " <Hierarchy hasAll='true' primaryKey='prod_id'>\n"
                   + " <Table name='product_x'/>\n"
                   + " <Level name='Store Name' column='name' uniqueMembers='true'/>\n"
                   + " </Hierarchy>\n"
                   + "</Dimension>\n"

                   + "<Measure name='Sales Count' \n"
                   + "    column='prod_id' aggregator='count'\n"
                   + "   formatString='#,###'/>\n"
                   + "<Measure name='Store Count' \n"
                   + "    column='store_id' aggregator='distinct-count'\n"
                   + "   formatString='#,###'/>\n"
                   + "<Measure name='Total Amount' \n"
                   + "    column='amount' aggregator='sum'\n"
                   + "   formatString='#,###'/>\n"
                   + "<Measure name='Avg Amount' \n"
                   + "    column='amount' aggregator='avg'\n"
                   + "   formatString='00.0'/>\n"
                   + "</Cube>";

     */

    @Override
    protected List<MappingCube> schemaCubes(MappingSchema mappingSchemaOriginal) {
        List<MappingCube> result = new ArrayList<>();
        result.addAll(super.schemaCubes(mappingSchemaOriginal));
        result.add(CubeRBuilder.builder()
            .name("Cheques")
            .fact(new TableR("cheques",
                List.of(),
                List.of(
                    AggNameRBuilder.builder()
                        .name("agg_lp_xxx_cheques")
                        .aggFactCount(AggColumnNameRBuilder.builder()
                            .column("FACT_COUNT")
                            .build())
                        .aggForeignKeys(List.of(
                            AggForeignKeyRBuilder.builder()
                                .factColumn("store_id")
                                .aggColumn("store_id")
                                .build()
                        ))
                        .aggMeasures(List.of(
                            AggMeasureRBuilder.builder()
                                .name("[Measures].[Avg Amount]")
                                .column("amount_AVG")
                                .build()
                        ))
                        .build()
                )))
            .dimensionUsageOrDimensions(List.of(
                PrivateDimensionRBuilder.builder()
                    .name("StoreX")
                    .foreignKey("store_id")
                    .hierarchies(List.of(
                        HierarchyRBuilder.builder()
                            .hasAll(true)
                            .primaryKey("store_id")
                            .relation(new TableR("store_x"))
                            .levels(List.of(
                                LevelRBuilder.builder()
                                    .name("Store Value")
                                    .column("value")
                                    .uniqueMembers(true)
                                    .build()
                            ))
                            .build()
                    ))
                    .build(),
                PrivateDimensionRBuilder.builder()
                    .name("ProductX")
                    .foreignKey("prod_id")
                    .hierarchies(List.of(
                        HierarchyRBuilder.builder()
                            .hasAll(true)
                            .primaryKey("prod_id")
                            .relation(new TableR("product_x"))
                            .levels(List.of(
                                LevelRBuilder.builder()
                                    .name("Store Name")
                                    .column("name")
                                    .uniqueMembers(true)
                                    .build()
                            ))
                            .build()
                    ))
                    .build()
            ))
            .measures(List.of(
                MeasureRBuilder.builder()
                    .name("Sales Count")
                    .column("prod_id")
                    .aggregator("count")
                    .formatString("#,###")
                    .build(),
                MeasureRBuilder.builder()
                    .name("Store Count")
                    .column("store_id")
                    .aggregator("distinct-count")
                    .formatString("#,###")
                    .build(),
                MeasureRBuilder.builder()
                    .name("Total Amount")
                    .column("amount")
                    .aggregator("sum")
                    .formatString("#,###")
                    .build(),
                MeasureRBuilder.builder()
                    .name("Avg Amount")
                    .column("amount")
                    .aggregator("avg")
                    .formatString("00.0")
                    .build()
            ))
            .build());
        return result;
    }

}
