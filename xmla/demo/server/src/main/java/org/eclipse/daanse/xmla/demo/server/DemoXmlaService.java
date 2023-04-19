package org.eclipse.daanse.xmla.demo.server;

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.discover.DiscoverService;
import org.eclipse.daanse.xmla.api.execute.ExecuteService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceVendor;

@Component(service = XmlaService.class, immediate = true)
@ServiceVendor(value = "demo")
public class DemoXmlaService implements XmlaService {

    public DemoXmlaService() {
        // constructor
    }

    private DiscoverService discoverService = new DemoDiscoverService();
    private ExecuteService executeService = new DemoExecuteService();

    @Override
    public DiscoverService discover() {
        return discoverService;
    }

    @Override
    public ExecuteService execute() {
        return executeService;
    }

}
