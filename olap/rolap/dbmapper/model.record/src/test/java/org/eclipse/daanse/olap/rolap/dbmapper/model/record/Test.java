package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.MeasureRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaRBuilder;

public class Test {
    @org.junit.jupiter.api.Test
    void testName() throws Exception {
        SchemaRBuilder.builder()
                .name("foo")
                .description("bar")
                .cube(List.of(CubeRBuilder.builder()
                        .name("FooCube")
                        .measure(List.of(MeasureRBuilder.builder()
                                .column("saldo")
                                .build()))
                        .build()));
    }
}
