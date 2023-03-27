package org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.steelwheels.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.SchemaRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.builder.CubeRBuilder;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DbMappingSchemaProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@Component(service = DbMappingSchemaProvider.class, scope = ServiceScope.SINGLETON)
public class SteelWheelRecordDbMappingSchemaProvider implements DbMappingSchemaProvider {

    private static String SCHEMA_NAME = "";
    private static String SCHEMA_DESCRIPTION = "";
    private static final String CUBE_NAME_1 = "";

    public static Schema SCHEMA = SchemaRBuilder.builder()
            .name(SCHEMA_NAME)
            .description(SCHEMA_DESCRIPTION)
            .cube(List.of(CubeRBuilder.builder()
                    .name(CUBE_NAME_1)
                    .build()))
            .build();

    @Override
    public Schema get() {
        return SCHEMA;
    }

}
