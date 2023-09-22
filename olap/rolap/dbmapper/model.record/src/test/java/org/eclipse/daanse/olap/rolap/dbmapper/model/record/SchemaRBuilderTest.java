package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaRBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SchemaRBuilderTest {
    @Test
    void testName() {
        MappingSchema schema = SchemaRBuilder.builder()
                .name("foo")
                .description("bar")
                .cubes(List.of(CubeRBuilder.builder()
                        .name("FooCube")
                        .measures(List.of(MeasureRBuilder.builder()
                                .column("saldo")
                                .build()))
                        .build())).build();
        assertThat(schema).isNotNull();
    }
}
