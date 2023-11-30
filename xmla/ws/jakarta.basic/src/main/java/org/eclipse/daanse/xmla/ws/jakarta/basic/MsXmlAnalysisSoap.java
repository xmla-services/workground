/*
* Copyright (c) 2023 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.xmla.ws.jakarta.basic;

import org.eclipse.daanse.ws.api.whiteboard.annotations.RequireSoapWhiteboard;
import org.eclipse.daanse.ws.api.whiteboard.prototypes.SOAPWhiteboardEndpoint;
import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.ext.Authenticate;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.ext.AuthenticateResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.BeginSession;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Discover;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DiscoverResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.EndSession;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Execute;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ExecuteResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Session;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceContext;

@WebService(name = "MsXmlAnalysisSoapPortType", portName = "MsXmlAnalysisSoapPort", serviceName = "MsXmlAnalysisService") // ,
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({ org.eclipse.daanse.xmla.ws.jakarta.model.xmla.msxmla.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.ext.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine300_300.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_mddataset.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine100.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine2.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine200.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine300.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine400.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine600.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine800.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_empty.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_multipleresults.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_rowset.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine100_100.ObjectFactory.class,
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine200_200.ObjectFactory.class })
@Component(service = MsXmlAnalysisSoap.class, name = "org.eclipse.daanse.msxmlanalysisservice")
@RequireSoapWhiteboard
@Designate(factory = true, ocd = MsXmlAnalysisSoap.Config.class)
@SOAPWhiteboardEndpoint(contextpath = "/xmla")
public class MsXmlAnalysisSoap {

    @ObjectClassDefinition()
    @interface Config {

        @AttributeDefinition(required = true)
        String osgiSoapEndpointContextPath();
    }

    @jakarta.annotation.Resource
    WebServiceContext wsContext;

    private WsAdapter wsAdapter;

    @Reference
    private XmlaService xmlaService;

    @Activate
    public void activate() {
        wsAdapter = new ApiXmlaWsAdapter(xmlaService);
    }

    @WebMethod(operationName = "Authenticate")
    @WebResult(name = "AuthenticateResponse", targetNamespace = "http://schemas.microsoft.com/analysisservices/2003/ext", partName = "parameters")
    public AuthenticateResponse authenticate(
            @WebParam(name = "Authenticate", targetNamespace = "http://schemas.microsoft.com/analysisservices/2003/ext", partName = "parameters") Authenticate authenticate) {

        wsContext.getUserPrincipal();
        return wsAdapter.authenticate(authenticate);
    }

    @WebMethod(operationName = "Discover", action = "urn:schemas-microsoft-com:xml-analysis:Discover")
    @WebResult(name = "DiscoverResponse", targetNamespace = "urn:schemas-microsoft-com:xml-analysis", partName = "parameters")
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public DiscoverResponse discover(
            @WebParam(name = "Discover", targetNamespace = "urn:schemas-microsoft-com:xml-analysis", partName = "parameters") Discover parameters,
            @WebParam(name = "Session", targetNamespace = "urn:schemas-microsoft-com:xml-analysis", header = true, mode = WebParam.Mode.INOUT) Holder<Session> session,
            @WebParam(name = "BeginSession", targetNamespace = "urn:schemas-microsoft-com:xml-analysis", header = true) BeginSession beginSession,
            @WebParam(name = "EndSession", targetNamespace = "urn:schemas-microsoft-com:xml-analysis", header = true) EndSession endSession) {

        return wsAdapter.discover(parameters, session, beginSession, endSession);
    }

    @WebMethod(operationName = "Execute", action = "urn:schemas-microsoft-com:xml-analysis:Execute")
    @WebResult(name = "ExecuteResponse", targetNamespace = "urn:schemas-microsoft-com:xml-analysis", partName = "parameters")
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public ExecuteResponse execute(
            @WebParam(name = "Execute", targetNamespace = "urn:schemas-microsoft-com:xml-analysis") Execute parameters,
            @WebParam(name = "Session", targetNamespace = "urn:schemas-microsoft-com:xml-analysis", header = true, mode = WebParam.Mode.INOUT) Holder<Session> session,
            @WebParam(name = "BeginSession", targetNamespace = "urn:schemas-microsoft-com:xml-analysis", header = true) BeginSession beginSession,
            @WebParam(name = "EndSession", targetNamespace = "urn:schemas-microsoft-com:xml-analysis", header = true) EndSession endSession) {

        return wsAdapter.execute(parameters, session, beginSession, endSession);
    }

}
