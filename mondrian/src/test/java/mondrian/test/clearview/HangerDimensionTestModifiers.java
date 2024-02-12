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
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */

package mondrian.test.clearview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.ColumnDefRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.HierarchyRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.InlineTableRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.LevelRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.PrivateDimensionRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.RowRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.ValueRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier.record.RDbMappingSchemaModifier;

public class HangerDimensionTestModifiers {

    public static class HangerDimensionTestModifier1 extends RDbMappingSchemaModifier {

        public HangerDimensionTestModifier1(MappingSchema mappingSchema) {
            super(mappingSchema);
        }

        /*
        <Dimension name="Le System-Trend Hanger" foreignKey="store_id">
  <Hierarchy hasAll="true" primaryKey="HANGER_KEY">
    <InlineTable alias="LE_SYSTEM_TREND_HANGER">
      <ColumnDefs>
        <ColumnDef name="HANGER_KEY" type="Numeric"/>
      </ColumnDefs>
      <Rows>
        <Row>
          <Value column="HANGER_KEY">1</Value>
        </Row>
      </Rows>
    </InlineTable>
    <Level name="Hanger Level" column="HANGER_KEY" uniqueMembers="true"/>
  </Hierarchy>
</Dimension>

         */
        @Override
        protected List<MappingCubeDimension> cubeDimensionUsageOrDimensions(MappingCube cube) {
            List<MappingCubeDimension> result = new ArrayList<>();
            result.addAll(super.cubeDimensionUsageOrDimensions(cube)
                .stream().filter(d -> !"Le System-Trend Hanger".equals(d.name())).toList());
            if (cube.name().equals("Sales"))
            result.add(PrivateDimensionRBuilder.builder()
                .name("Le System-Trend Hanger")
                .foreignKey("store_id")
                .hierarchies(List.of(
                    HierarchyRBuilder.builder()
                        .hasAll(true)
                        .primaryKey("HANGER_KEY")
                        .relation(InlineTableRBuilder.builder()
                            .alias("LE_SYSTEM_TREND_HANGER")
                            .columnDefs(List.of(
                                ColumnDefRBuilder.builder()
                                    .name("HANGER_KEY")
                                    .type(TypeEnum.NUMERIC)
                                    .build()
                            ))
                            .rows(List.of(
                                RowRBuilder.builder()
                                    .values(List.of(
                                        ValueRBuilder.builder()
                                            .column("HANGER_KEY")
                                            .content("1")
                                            .build()
                                    ))
                                    .build()
                            ))
                            .build())
                        .levels(List.of(
                            LevelRBuilder.builder()
                                .name("Hanger Level")
                                .column("HANGER_KEY")
                                .uniqueMembers(true)
                                .build()
                        ))
                        .build()))
                .build());
            return result;
        }
    }

}
