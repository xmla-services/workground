package org.eclipse.daanse.xmla.demo.server;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

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
    public static final String PID_MS_SOAP = "org.eclipse.daanse.msxmlanalysisservice";

    @Reference
    ConfigurationAdmin configurationAdmin;
    private Configuration cXmlaService;

    private Configuration cLoggingHandler;

    @Activate
    public void activate() throws IOException {

        cXmlaService = configurationAdmin.getFactoryConfiguration(PID_MS_SOAP , "1", "?");

        Dictionary<String, Object> dict = new Hashtable<>();
        dict.put("xmlaService.target", "(service.vendor=demo)");
        dict.put( "osgi.soap.endpoint.contextpath","/xmla");

        cXmlaService.update(dict);

        cLoggingHandler = configurationAdmin
                .getFactoryConfiguration("org.eclipse.daanse.ws.handler.SOAPLoggingHandler","1", "?");

        dict = new Hashtable<>();
        dict.put("osgi.soap.endpoint.selector", "(service.pid=*)");
        cLoggingHandler.update(dict);

    }

    @Deactivate
    public void deactivate() throws IOException {

        if (cXmlaService != null) {
            cXmlaService.delete();
        }
        if (cLoggingHandler != null) {
            cLoggingHandler.delete();
        }
    }

}
