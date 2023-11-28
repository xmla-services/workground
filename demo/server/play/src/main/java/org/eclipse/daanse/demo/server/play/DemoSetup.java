package org.eclipse.daanse.demo.server.play;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.daanse.olap.core.BasicContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.RequireServiceComponentRuntime;

@Component(immediate = true)
@RequireConfigurationAdmin
@RequireServiceComponentRuntime
public class DemoSetup {
	private static final String TARGET_EXT = ".target";

    public static final String PID_MS_SOAP = "org.eclipse.daanse.msxmlanalysisservice";
    
    public static final String PID_XMLA_SERVICE = "org.eclipse.daanse.olap.xmla.bridge.ContextGroupXmlaService";

	public static final String PID_CONTEXT = "org.eclipse.daanse.olap.core.BasicContext";
	
	public static final String PID_DS = "org.eclipse.daanse.db.datasource.sqlite.ConnectionPoolDataSourceService";

    @Reference
    ConfigurationAdmin configurationAdmin;
    private Configuration cXmlaEndpoint;
    private Configuration cLoggingHandler;

    private Configuration contextGroupXmlaService;
    private Configuration cContext;

	private Configuration cDs;


    @Activate
    public void activate() throws IOException {

        Dictionary<String, Object> dict;
		initXmlaEndPoint();
        //
        initXmlaService();
        
        
        initContext();
    }

	private void initContext() throws IOException {
		cContext = configurationAdmin.getFactoryConfiguration(PID_CONTEXT , "1", "?");
        
		
		cDs = configurationAdmin.getFactoryConfiguration(PID_DS, "1", "?");

		Dictionary<String, Object> propsDS = new Hashtable<>();
		propsDS.put("url", "myNewDB.sqlite");
		cDs.update(propsDS);

		
		
		
		
		
        Dictionary<String, Object> props = new Hashtable<>();
        props.put(BasicContext.REF_NAME_DATA_SOURCE + TARGET_EXT, "(ds=1)");
        props.put(BasicContext.REF_NAME_DIALECT_FACTORY + TARGET_EXT, "(df=2)");
        props.put(BasicContext.REF_NAME_STATISTICS_PROVIDER + TARGET_EXT, "(sp=3)");
        props.put(BasicContext.REF_NAME_EXPRESSION_COMPILER_FACTORY + TARGET_EXT, "(ecf=1)");
        props.put(BasicContext.REF_NAME_DB_MAPPING_SCHEMA_PROVIDER + TARGET_EXT, "(dbmsp=1)");
        //        props.put(BasicContext.REF_NAME_QUERY_PROVIDER+ TARGET_EXT, "(qp=1)");

        String theName = "theName";
        String theDescription = "theDescription";
        
        props.put("name", theName);
        props.put("description", theDescription);
        cContext.update(props);
	}

	private void initXmlaService() throws IOException {
		Dictionary<String, Object> dict;
		contextGroupXmlaService = configurationAdmin.getFactoryConfiguration(PID_XMLA_SERVICE , "1", "?");
        dict = new Hashtable<>();
        dict.put("contextGroup"+TARGET_EXT, "(service.pid=*)");
        contextGroupXmlaService.update(dict);
	}

	private void initXmlaEndPoint() throws IOException {
		cXmlaEndpoint = configurationAdmin.getFactoryConfiguration(PID_MS_SOAP , "1", "?");

        Dictionary<String, Object> dict = new Hashtable<>();
        dict.put("xmlaService.target", "(service.vendor=demo)");
        dict.put( "osgi.soap.endpoint.contextpath","/xmla");

        cXmlaEndpoint.update(dict);

        cLoggingHandler = configurationAdmin
                .getFactoryConfiguration("org.eclipse.daanse.ws.handler.SOAPLoggingHandler","1", "?");

        dict = new Hashtable<>();
        dict.put("osgi.soap.endpoint.selector", "(service.pid=*)");
        cLoggingHandler.update(dict);
	}

    @Deactivate
    public void deactivate() throws IOException {

        if (cXmlaEndpoint != null) {
            cXmlaEndpoint.delete();
        }
        if (cLoggingHandler != null) {
            cLoggingHandler.delete();
        }
    }

}
