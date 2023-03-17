package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.jdbc;

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
import org.eclipse.daanse.olap.rolap.dbmapper.api.Table;
import org.eclipse.daanse.olap.rolap.dbmapper.api.VirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Verifyer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.CUBE_MUST_CONTAIN_MEASURES;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.FACT_TABLE_0_DOES_NOT_EXIST_IN_DATABASE;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.NOT_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.SCHEMA_;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.SchemaWalkerMessages.SCHEMA_NAME_MUST_BE_SET;
import static org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.description.DescriptionVerifyerTest.setupDummyListAnswer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
public class DatabaseVerifyerTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.jdbc.DatabaseVerifyer";
    @InjectService(filter = "(component.name=" + COMPONENT_NAME + ")")
    Verifyer verifyer;

    DataSource dataSource = mock(DataSource.class);
    Connection connection = mock(Connection.class);
    DatabaseMetaData metaData = mock(DatabaseMetaData.class);
    ResultSet rs = mock(ResultSet.class);

    Schema schema = mock(Schema.class);
    Cube cube = mock(Cube.class);
    VirtualCube virtualCube = mock(VirtualCube.class);
    CubeDimension dimension = mock(PrivateDimension.class);
    CalculatedMemberProperty calculatedMemberProperty = mock(CalculatedMemberProperty.class);
    CalculatedMember calculatedMember = mock(CalculatedMember.class);
    Measure measure = mock(Measure.class);
    Hierarchy hierarchyConsumer = mock(Hierarchy.class);
    Level level = mock(Level.class);
    org.eclipse.daanse.olap.rolap.dbmapper.api.Property propertyConsumer = mock(org.eclipse.daanse.olap.rolap.dbmapper.api.Property.class);
    NamedSet namedSet = mock(NamedSet.class);
    Parameter parameter = mock(Parameter.class);
    DrillThroughAction drillThroughAction = mock(DrillThroughAction.class);
    Action action = mock(Action.class);
    SharedDimension sharedDimension = mock(SharedDimension.class);
    Table table = mock(Table.class);

    @BeforeEach
    void beforeEach() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getMetaData()).thenReturn(metaData);
    }

    @Test
    void testSchema() {
        List<VerificationResult> result = verifyer.verify(schema, dataSource);
        assertThat(result).isNotNull()
            .hasSize(0);
    }

    @Test
    void testCube() throws Exception {
        when(metaData.getTables(any(), any(), any(), any())).thenReturn(rs);
        when(rs.next()).thenReturn(false);
        when(schema.cube()).thenAnswer(setupDummyListAnswer(cube));
        when(cube.fact()).thenReturn(table);
        when(table.name()).thenReturn("name");
        when(table.schema()).thenReturn("schema");
        List<VerificationResult> result = verifyer.verify(schema, dataSource);
        assertThat(result).isNotNull()
            .hasSize(2);

        assertThat(result)
            .extracting(VerificationResult::description)
            .contains(String.format(CUBE_MUST_CONTAIN_MEASURES, NOT_SET))
            .contains(String.format(FACT_TABLE_0_DOES_NOT_EXIST_IN_DATABASE, "name",
                SCHEMA_ + "schema"));
        assertThat(result).extracting(VerificationResult::title)
            .contains(CUBE);
        assertThat(result).extracting(VerificationResult::level)
            .containsOnly(org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Level.ERROR);
    }

}
