package org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.foodmart.record;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.provider.api.DbMappingSchemaProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@Component(service = DbMappingSchemaProvider.class, scope = ServiceScope.SINGLETON)
public class FoodMartRecordDbMappingSchemaProvider implements DbMappingSchemaProvider {

    @Override
    public Schema get() {
        return null;
    }

}
