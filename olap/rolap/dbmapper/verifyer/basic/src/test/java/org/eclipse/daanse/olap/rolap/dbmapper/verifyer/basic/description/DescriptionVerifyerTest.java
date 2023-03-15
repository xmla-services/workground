package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.description;

import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Verifyer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = DescriptionVerifyerTest.COMPONENT_NAME, name = "1", location = "?", properties = {
        @Property(key = "calculatedMemberProperty", value = "INFO"), //
        @Property(key = "action", value = "INFO"), //
        @Property(key = "calculatedMember", value = "INFO"), //
        @Property(key = "cube", value = "INFO"), //
        @Property(key = "cubeDimension", value = "INFO"), //
        @Property(key = "drillThroughAction", value = "INFO"), //
        @Property(key = "hierarchy", value = "INFO"), //
        @Property(key = "level", value = "INFO"), //
        @Property(key = "measure", value = "INFO"), //
        @Property(key = "namedSet", value = "INFO"), //
        @Property(key = "parameter", value = "INFO"), //
        @Property(key = "dimension", value = "INFO"), //
        @Property(key = "property", value = "INFO"), //
        @Property(key = "schema", value = "INFO"), //
        @Property(key = "sharedDimension", value = "INFO"), //
        @Property(key = "virtualCube", value = "INFO") })
public class DescriptionVerifyerTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.description.DescriptionVerifyer";
    @InjectService(filter = "(component.name=" + COMPONENT_NAME + ")")
    Verifyer verifyer;

    @Test
    void testName() throws Exception {
        verifyer.verify(null, null);
    }

}
