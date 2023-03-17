package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.mandantory;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Action;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CalculatedMemberProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.api.CubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.api.DrillThroughAction;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Hierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Level;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Measure;
import org.eclipse.daanse.olap.rolap.dbmapper.api.NamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Parameter;
import org.eclipse.daanse.olap.rolap.dbmapper.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.api.SharedDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.api.VirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Verifyer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE_NAME_MUST_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE_WITH_NAME_MUST_CONTAIN_;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DIMENSIONS;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.FACT_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.MEASURE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.NOT_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.SCHEMA;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.SCHEMA_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.description.DescriptionVerifyerTest.setupDummyListAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
public class MandantoriesVerifyerTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.mandantory.MandantoriesVerifyer";
    @InjectService(filter = "(component.name=" + COMPONENT_NAME + ")")
    Verifyer verifyer;

    Schema schema = mock(Schema.class);
    Cube cube = mock(Cube.class);
    VirtualCube virtualCube = mock(VirtualCube.class);
    CubeDimension dimension = mock(PrivateDimension.class);
    CalculatedMemberProperty calculatedMemberProperty = mock(CalculatedMemberProperty.class);
    CalculatedMember calculatedMember = mock(CalculatedMember.class);
    Measure measure = mock(Measure.class);
    Hierarchy hierarchyConsumer = mock(Hierarchy.class);
    Level level = mock(Level.class);
    org.eclipse.daanse.olap.rolap.dbmapper.api.Property propertyConsumer =
        mock(org.eclipse.daanse.olap.rolap.dbmapper.api.Property.class);
    NamedSet namedSet = mock(NamedSet.class);
    Parameter parameter = mock(Parameter.class);
    DrillThroughAction drillThroughAction = mock(DrillThroughAction.class);
    Action action = mock(Action.class);
    SharedDimension sharedDimension = mock(SharedDimension.class);

    @Test
    void testSchema() {

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(1);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.ERROR);
    }

    @Test
    void testCube() {
        when(schema.cube()).thenAnswer(setupDummyListAnswer(cube));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(5);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_NAME_MUST_BE_SET)
            .contains(CUBE_NAME_MUST_SET)
            .contains(String.format(FACT_NAME_MUST_BE_SET, NOT_SET))
            .contains(String.format(CUBE_WITH_NAME_MUST_CONTAIN_, NOT_SET, DIMENSIONS))
            .contains(String.format(CUBE_WITH_NAME_MUST_CONTAIN_, NOT_SET, MEASURE));
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(DIMENSIONS)
            .contains(MEASURE);

        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.ERROR);
    }
}
