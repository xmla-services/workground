package org.eclipse.daanse.db.jdbc.dataloader.ods;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectResolver;
import org.eclipse.daanse.db.jdbc.dataloader.api.OdsDataLoadService;
import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataService;
import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataServiceFactory;
import org.eclipse.daanse.db.jdbc.metadata.impl.JdbcMetaDataServiceFactoryLiveImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.common.service.ServiceAware;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.osgi.test.common.dictionary.Dictionaries.dictionaryOf;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
@RequireConfigurationAdmin
class OdsDataLoadServiceImplTest {

    @TempDir
    Path path;
    public static final String COMPONENT_NAME = "org.eclipse.daanse.db.jdbc.dataloader.ods.OdsDataLoadServiceImpl";
    @InjectBundleContext
    BundleContext bc;
    DialectResolver dialectResolver = mock(DialectResolver.class);
    Dialect dialect = mock(Dialect.class);
    Connection connection = mock(Connection.class);
    JdbcMetaDataServiceFactory jmdsf = mock(JdbcMetaDataServiceFactory.class);
    JdbcMetaDataService jmds = mock(JdbcMetaDataService.class);
    PreparedStatement preparedStatement = mock(PreparedStatement.class);

    DataSource dataSource = mock(DataSource.class);
    @InjectService
    ConfigurationAdmin ca;

    Configuration conf;

    @BeforeEach
    void beforeEach() throws SQLException, IOException {
        copy("test.ods");
        bc.registerService(JdbcMetaDataServiceFactory.class, jmdsf, dictionaryOf("jmdsf", "1"));
        bc.registerService(DialectResolver.class, dialectResolver, dictionaryOf("dr", "2"));
        when(jmdsf.create(any())).thenReturn(jmds);
        when(jmds.getColumnDataType("testSchema", "test", "col0"))
            .thenReturn(Optional.of(4));
        when(jmds.getColumnDataType("testSchema", "test", "col1"))
            .thenReturn(Optional.of(12));
        when(jmds.getColumnDataType("testSchema", "test", "col2"))
            .thenReturn(Optional.of(91));
        when(jmds.getColumnDataType("testSchema", "test", "col3"))
            .thenReturn(Optional.of(4));
        when(jmds.getColumnDataType("testSchema", "test", "col4"))
            .thenReturn(Optional.of(4));
        when(jmds.getColumnDataType("testSchema", "test", "col5"))
            .thenReturn(Optional.of(4));
        when(jmds.getColumnDataType("testSchema", "test", "col6"))
            .thenReturn(Optional.of(16));
        when(jmds.getColumnDataType("testSchema", "test", "col7"))
            .thenReturn(Optional.of(92));
        when(jmds.getColumnDataType("testSchema", "test", "col8"))
            .thenReturn(Optional.of(3));
        when(jmds.getColumnDataType("testSchema", "test", "col9"))
            .thenReturn(Optional.of(93));


        when(dialectResolver.resolve(any(DataSource.class))).thenReturn(Optional.of(dialect));

        when(dialect.getDialectName()).thenReturn("MYSQL");
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        when(connection.getSchema()).thenReturn("testSchema");
        when(preparedStatement.getConnection()).thenReturn(connection);
    }

    private void copy(String... files) throws IOException {

        for (String file : files) {
            InputStream is = bc.getBundle().getResource(file).openConnection().getInputStream();
            Files.copy(is, path.resolve(file));
        }
    }

    @AfterEach
    void afterEach() throws IOException {
        if (conf != null) {
            conf.delete();
        }
    }

    @Test
    void testBatch(
        @InjectService(cardinality = 0, filter = "(component.name=" + COMPONENT_NAME  + ")") ServiceAware<OdsDataLoadService> odsDataLoadServiceAware
    ) throws IOException, SQLException, InterruptedException {
        setupOdsDataLoadServiceImpl(path, "test", ".ods", "", "UTF-8", true);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        when(dialect.supportBatchOperations()).thenReturn(true);
        OdsDataLoadService odsDataLoadService = odsDataLoadServiceAware.waitForService(1000);
        //odsDataLoadService.setJmdsf(jmdsf);
        odsDataLoadService.loadData(dataSource);

        verify(connection, (times(1))).prepareStatement(stringCaptor.capture());
        verify(preparedStatement, (times(6))).addBatch();
        verify(preparedStatement, (times(1))).executeBatch();
    }

    @Test
    void test(
        @InjectService(cardinality = 0, filter = "(component.name=" + COMPONENT_NAME  + ")") ServiceAware<OdsDataLoadService> odsDataLoadServiceAware
    ) throws IOException, SQLException, InterruptedException {
        setupOdsDataLoadServiceImpl(path, "test", ".ods", "", "UTF-8", true);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        when(dialect.supportBatchOperations()).thenReturn(false);
        OdsDataLoadService odsDataLoadService = odsDataLoadServiceAware.waitForService(1000);
        //odsDataLoadService.setJmdsf(jmdsf);
        odsDataLoadService.loadData(dataSource);

        verify(connection, (times(1))).prepareStatement(stringCaptor.capture());
        verify(preparedStatement, (times(5))).executeUpdate();
    }

    private void setupOdsDataLoadServiceImpl(Path odsFolderPath, String odsFileName,  String odsFileSuffix,
                                             String odsFilePrefix, String encoding, Boolean clearTableBeforeLoad)
        throws IOException {
        conf = ca.getFactoryConfiguration(OdsDataLoadServiceImplTest.COMPONENT_NAME, "1", "?");
        Dictionary<String, Object> dict = new Hashtable<>();
        if (odsFolderPath != null) {
            dict.put("odsFolderPath", odsFolderPath.toAbsolutePath().toString());
        }
        if (odsFileSuffix != null) {
            dict.put("odsFileSuffix", odsFileSuffix);
        }
        if (odsFilePrefix != null) {
            dict.put("odsFilePrefix", odsFilePrefix);
        }
        if (odsFileName != null) {
            dict.put("odsFileName", odsFileName);
        }
        if (encoding != null) {
            dict.put("encoding", encoding);
        }
        if (clearTableBeforeLoad != null) {
            dict.put("clearTableBeforeLoad", clearTableBeforeLoad);
        }
        conf.update(dict);
    }

}
