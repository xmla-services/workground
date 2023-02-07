package org.eclipse.daanse.xmla.client.soapmessage;

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.discover.DiscoverService;
import org.eclipse.daanse.xmla.api.execute.ExecuteService;

public class XmlaServiceClientImpl implements XmlaService {


    private DiscoverServiceImpl ds;
    private ExecuteServiceImpl es;

    public XmlaServiceClientImpl(String endPointurl) {
        SoapClient client = new SoapClient(endPointurl);
        ds = new DiscoverServiceImpl(client);
        es = new ExecuteServiceImpl(client);
    }

    @Override
    public DiscoverService discover() {
        return ds;
    }

    @Override
    public ExecuteService execute() {
        return es;
    }

}
