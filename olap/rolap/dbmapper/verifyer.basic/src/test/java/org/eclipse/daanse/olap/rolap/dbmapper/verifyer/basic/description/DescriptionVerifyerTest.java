package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.description;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMember;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMemberProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDrillThroughAction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Measure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.NamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Parameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.SharedDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.VirtualCube;
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
    MappingCube cube = mock(MappingCube.class);
    VirtualCube virtualCube = mock(VirtualCube.class);
    MappingCubeDimension dimension = mock(PrivateDimension.class);
    PrivateDimension privateDimension = mock(PrivateDimension.class);
    MappingCalculatedMemberProperty calculatedMemberProperty = mock(MappingCalculatedMemberProperty.class);
    MappingCalculatedMember calculatedMember = mock(MappingCalculatedMember.class);
    Measure measure = mock(Measure.class);
    MappingHierarchy hierarchy = mock(MappingHierarchy.class);
    MappingLevel level = mock(MappingLevel.class);
    org.eclipse.daanse.olap.rolap.dbmapper.model.api.Property property = mock(org.eclipse.daanse.olap.rolap.dbmapper.model.api.Property.class);
    NamedSet namedSet = mock(NamedSet.class);
    Parameter parameter = mock(Parameter.class);
    MappingDrillThroughAction drillThroughAction = mock(MappingDrillThroughAction.class);
    MappingAction action = mock(MappingAction.class);
    SharedDimension sharedDimension = mock(SharedDimension.class);

    @Test
    void testSchema() {

        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(schema.virtualCubes()).thenAnswer(setupDummyListAnswer(virtualCube));
        when(schema.dimensions()).thenAnswer(setupDummyListAnswer(dimension));
        when(schema.namedSets()).thenAnswer(setupDummyListAnswer(namedSet));
        when(schema.parameters()).thenAnswer(setupDummyListAnswer(parameter));

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

        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.calculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));
        when(cube.dimensionUsageOrDimensions()).thenAnswer(setupDummyListAnswer(dimension));
        when(cube.measures()).thenAnswer(setupDummyListAnswer(measure));
        when(cube.actions()).thenAnswer(setupDummyListAnswer(action));


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

        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(schema.virtualCubes()).thenAnswer(setupDummyListAnswer(virtualCube));
        when(schema.dimensions()).thenAnswer(setupDummyListAnswer(dimension));
        when(cube.dimensionUsageOrDimensions()).thenAnswer(setupDummyListAnswer(dimension));
        when(virtualCube.virtualCubeDimensions()).thenAnswer(setupDummyListAnswer(dimension));


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

        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.measures()).thenAnswer(setupDummyListAnswer(measure));

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

        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.measures()).thenAnswer(setupDummyListAnswer(measure));
        when(measure.calculatedMemberProperties()).thenAnswer(setupDummyListAnswer(calculatedMemberProperty));

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

        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(schema.virtualCubes()).thenAnswer(setupDummyListAnswer(virtualCube));
        when(cube.calculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));
        when(virtualCube.calculatedMembers()).thenAnswer(setupDummyListAnswer(calculatedMember));

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

        when(schema.dimensions()).thenAnswer(setupDummyListAnswer(privateDimension));
        when(privateDimension.hierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));


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
        when(schema.dimensions()).thenAnswer(setupDummyListAnswer(privateDimension));
        when(privateDimension.hierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.levels()).thenAnswer(setupDummyListAnswer(level));

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

        when(schema.dimensions()).thenAnswer(setupDummyListAnswer(privateDimension));
        when(privateDimension.hierarchies()).thenAnswer(setupDummyListAnswer(hierarchy));
        when(hierarchy.levels()).thenAnswer(setupDummyListAnswer(level));
        when(level.properties()).thenAnswer(setupDummyListAnswer(property));

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

        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(schema.namedSets()).thenAnswer(setupDummyListAnswer(namedSet));
        when(cube.namedSets()).thenAnswer(setupDummyListAnswer(namedSet));

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

        when(schema.parameters()).thenAnswer(setupDummyListAnswer(parameter));

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

        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.drillThroughActions()).thenAnswer(setupDummyListAnswer(drillThroughAction));

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
        when(schema.cubes()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.actions()).thenAnswer(setupDummyListAnswer(action));

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
        final List<N> someList = new ArrayList<>(Arrays.asList(values));

        Answer<List<N>> answer = new Answer<>() {
            @Override
			public List<N> answer(InvocationOnMock invocation) throws Throwable {
                return someList;
            }
        };
        return answer;
    }

}
