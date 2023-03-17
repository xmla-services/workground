package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.description;

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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.annotation.Property;
import org.osgi.test.common.annotation.config.WithFactoryConfiguration;
import org.osgi.test.junit5.cm.ConfigurationExtension;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.ACTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.ACTION_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CALCULATED_MEMBER_PROPERTY_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DIMENSIONS;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DIMENSION_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_ACTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.DRILL_THROUGH_ACTION_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.HIERARCHY;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.HIERARCHY_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.LEVEL;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.LEVEL_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.MEASURE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.MEASURE_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.NAMED_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.NAMED_SET_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.PARAMETER;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.PARAMETER_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.PROPERTY;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.PROPERTY_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.SCHEMA;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.SCHEMA_MUST_CONTAIN_DESCRIPTION;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@ExtendWith(ConfigurationExtension.class)
@WithFactoryConfiguration(factoryPid = DescriptionVerifyerTest.COMPONENT_NAME, name = "1", location = "?", properties = {
        @Property(key = "calculatedMemberProperty", value = "INFO"), //
        @Property(key = "action", value = "INFO"), //
        @Property(key = "calculatedMember", value = "INFO"), //
        @Property(key = "cube", value = "INFO"), //
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

    Schema schema = mock(Schema.class);
    Cube cube = mock(Cube.class);
    VirtualCube virtualCube = mock(VirtualCube.class);
    CubeDimension dimension = mock(PrivateDimension.class);
    PrivateDimension privateDimension = mock(PrivateDimension.class);
    CalculatedMemberProperty calculatedMemberProperty = mock(CalculatedMemberProperty.class);
    CalculatedMember calculatedMember = mock(CalculatedMember.class);
    Measure measure = mock(Measure.class);
    Hierarchy hierarchy = mock(Hierarchy.class);
    Level level = mock(Level.class);
    org.eclipse.daanse.olap.rolap.dbmapper.api.Property property = mock(org.eclipse.daanse.olap.rolap.dbmapper.api.Property.class);
    NamedSet namedSet = mock(NamedSet.class);
    Parameter parameter = mock(Parameter.class);
    DrillThroughAction drillThroughAction = mock(DrillThroughAction.class);
    Action action = mock(Action.class);
    SharedDimension sharedDimension = mock(SharedDimension.class);

    @Test
    void testSchema() {

        when(schema.cube()).thenAnswer(setupDummyListAnswer(cube));
        when(schema.virtualCube()).thenAnswer(setupDummyListAnswer(virtualCube));
        when(schema.dimension()).thenAnswer(setupDummyListAnswer(dimension));
        when(schema.namedSet()).thenAnswer(setupDummyListAnswer(namedSet));
        when(schema.parameter()).thenAnswer(setupDummyListAnswer(parameter));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(6);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(DIMENSION_MUST_CONTAIN_DESCRIPTION)
            .contains(NAMED_SET_MUST_CONTAIN_DESCRIPTION)
            .contains(PARAMETER_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(VIRTUAL_CUBE)
            .contains(DIMENSIONS)
            .contains(NAMED_SET)
            .contains(PARAMETER);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.INFO);
    }

    @Test
    void testCube() {

        when(schema.cube()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.calculatedMember()).thenAnswer(setupDummyListAnswer(calculatedMember));
        when(cube.dimensionUsageOrDimension()).thenAnswer(setupDummyListAnswer(dimension));
        when(cube.measure()).thenAnswer(setupDummyListAnswer(measure));
        when(cube.action()).thenAnswer(setupDummyListAnswer(action));


        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(6);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(CALCULATED_MEMBER_MUST_CONTAIN_DESCRIPTION)
            .contains(DIMENSION_MUST_CONTAIN_DESCRIPTION)
            .contains(MEASURE_MUST_CONTAIN_DESCRIPTION)
            .contains(ACTION_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(CALCULATED_MEMBER)
            .contains(DIMENSIONS)
            .contains(MEASURE)
            .contains(ACTION);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.INFO);
    }

    @Test
    void testDimension() {

        when(schema.cube()).thenAnswer(setupDummyListAnswer(cube));
        when(schema.virtualCube()).thenAnswer(setupDummyListAnswer(virtualCube));
        when(schema.dimension()).thenAnswer(setupDummyListAnswer(dimension));
        when(cube.dimensionUsageOrDimension()).thenAnswer(setupDummyListAnswer(dimension));
        when(virtualCube.virtualCubeDimension()).thenAnswer(setupDummyListAnswer(dimension));


        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(6);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(DIMENSION_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(VIRTUAL_CUBE)
            .contains(DIMENSIONS);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.INFO);
    }

    @Test
    void testMeasure() {

        when(schema.cube()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.measure()).thenAnswer(setupDummyListAnswer(measure));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(3);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(MEASURE_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(MEASURE);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.INFO);
    }

    @Test
    void testCalculatedMemberProperty() {

        when(schema.cube()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.measure()).thenAnswer(setupDummyListAnswer(measure));
        when(measure.calculatedMemberProperty()).thenAnswer(setupDummyListAnswer(calculatedMemberProperty));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(4);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(MEASURE_MUST_CONTAIN_DESCRIPTION)
            .contains(CALCULATED_MEMBER_PROPERTY_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(MEASURE);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.INFO);
    }

    @Test
    void testCalculatedMember() {

        when(schema.cube()).thenAnswer(setupDummyListAnswer(cube));
        when(schema.virtualCube()).thenAnswer(setupDummyListAnswer(virtualCube));
        when(cube.calculatedMember()).thenAnswer(setupDummyListAnswer(calculatedMember));
        when(virtualCube.calculatedMember()).thenAnswer(setupDummyListAnswer(calculatedMember));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(5);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(VIRTUAL_CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(CALCULATED_MEMBER_MUST_CONTAIN_DESCRIPTION); //2
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(VIRTUAL_CUBE)
            .contains(CALCULATED_MEMBER);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.INFO);
    }

    @Test
    void testHierarchy() {

        when(schema.dimension()).thenAnswer(setupDummyListAnswer(privateDimension));
        when(privateDimension.hierarchy()).thenAnswer(setupDummyListAnswer(hierarchy));


        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(3);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(DIMENSION_MUST_CONTAIN_DESCRIPTION)
            .contains(HIERARCHY_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(DIMENSIONS)
            .contains(HIERARCHY);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.INFO);
    }

    @Test
    void testLevel() {
        when(schema.dimension()).thenAnswer(setupDummyListAnswer(privateDimension));
        when(privateDimension.hierarchy()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.level()).thenAnswer(setupDummyListAnswer(level));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(4);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(DIMENSION_MUST_CONTAIN_DESCRIPTION)
            .contains(HIERARCHY_MUST_CONTAIN_DESCRIPTION)
            .contains(LEVEL_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(DIMENSIONS)
            .contains(HIERARCHY)
            .contains(LEVEL);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.INFO);
    }

    @Test
    void testProperty() {

        when(schema.dimension()).thenAnswer(setupDummyListAnswer(privateDimension));
        when(privateDimension.hierarchy()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.level()).thenAnswer(setupDummyListAnswer(level));
        when(level.property()).thenAnswer(setupDummyListAnswer(property));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(5);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(DIMENSION_MUST_CONTAIN_DESCRIPTION)
            .contains(HIERARCHY_MUST_CONTAIN_DESCRIPTION)
            .contains(LEVEL_MUST_CONTAIN_DESCRIPTION)
            .contains(PROPERTY_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(DIMENSIONS)
            .contains(HIERARCHY)
            .contains(LEVEL)
            .contains(PROPERTY);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.INFO);
    }

    @Test
    void testNamedSet() {

        when(schema.cube()).thenAnswer(setupDummyListAnswer(cube));
        when(schema.namedSet()).thenAnswer(setupDummyListAnswer(namedSet));
        when(cube.namedSet()).thenAnswer(setupDummyListAnswer(namedSet));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(4);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(NAMED_SET_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(NAMED_SET);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.INFO);
    }

    void testParameter() {

        when(schema.parameter()).thenAnswer(setupDummyListAnswer(parameter));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(2);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(PARAMETER);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(PARAMETER);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.INFO);
    }

    @Test
    void testDrillThroughAction() {

        when(schema.cube()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.drillThroughAction()).thenAnswer(setupDummyListAnswer(drillThroughAction));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(3);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(DRILL_THROUGH_ACTION_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(DRILL_THROUGH_ACTION);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.INFO);
    }

    @Test
    void testAction() {
        when(schema.cube()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.action()).thenAnswer(setupDummyListAnswer(action));

        List<VerificationResult> result = verifyer.verify(schema, null);
        assertThat(result).isNotNull()
            .hasSize(3);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(SCHEMA_MUST_CONTAIN_DESCRIPTION)
            .contains(CUBE_MUST_CONTAIN_DESCRIPTION)
            .contains(ACTION_MUST_CONTAIN_DESCRIPTION);
        assertThat(result).extracting(VerificationResult::title)
            .contains(SCHEMA)
            .contains(CUBE)
            .contains(ACTION);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.INFO);

    }

    public static  <N> Answer<List<N>> setupDummyListAnswer(N... values) {
        final List<N> someList = new ArrayList<N>();

        someList.addAll(Arrays.asList(values));

        Answer<List<N>> answer = new Answer<List<N>>() {
            public List<N> answer(InvocationOnMock invocation) throws Throwable {
                return someList;
            }
        };
        return answer;
    }

}
