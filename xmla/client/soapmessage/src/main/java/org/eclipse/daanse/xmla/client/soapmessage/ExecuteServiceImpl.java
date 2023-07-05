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
package org.eclipse.daanse.xmla.client.soapmessage;

import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import org.eclipse.daanse.xmla.api.discover.Properties;
import org.eclipse.daanse.xmla.api.execute.ExecuteParameter;
import org.eclipse.daanse.xmla.api.execute.ExecuteService;
import org.eclipse.daanse.xmla.api.execute.alter.AlterRequest;
import org.eclipse.daanse.xmla.api.execute.alter.AlterResponse;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelRequest;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelResponse;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheRequest;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheResponse;
import org.eclipse.daanse.xmla.api.execute.statement.StatementRequest;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.api.xmla.Alter;
import org.eclipse.daanse.xmla.api.xmla.Cancel;
import org.eclipse.daanse.xmla.api.xmla.ClearCache;
import org.eclipse.daanse.xmla.api.xmla.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.eclipse.daanse.xmla.client.soapmessage.Constants.COMMAND;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.EXECUTE;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.PROPERTIES;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.PROPERTY_LIST;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.SOAP_ACTION_EXECUTE;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToAlterResponse;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToCancelResponse;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToClearCacheResponse;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToStatementResponse;
import static org.eclipse.daanse.xmla.client.soapmessage.SoapUtil.addChildElementAlter;
import static org.eclipse.daanse.xmla.client.soapmessage.SoapUtil.addChildElementCancel;
import static org.eclipse.daanse.xmla.client.soapmessage.SoapUtil.addChildElementClearCache;
import static org.eclipse.daanse.xmla.client.soapmessage.SoapUtil.addChildElementParameterList;
import static org.eclipse.daanse.xmla.client.soapmessage.SoapUtil.addChildElementPropertyList;

public class ExecuteServiceImpl implements ExecuteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteServiceImpl.class);
    private SoapClient soapClient;

    public ExecuteServiceImpl(SoapClient soapClient) {
        this.soapClient = soapClient;
    }

    @Override
    public StatementResponse statement(StatementRequest statementRequest) {
        try {
            Consumer<SOAPMessage> msg = getConsumer(statementRequest);
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_EXECUTE), msg);
            return convertToStatementResponse(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("ExecuteService statement error", e);
        }
        return null;
    }

    @Override
    public AlterResponse alter(AlterRequest alterRequest) {
        try {
            Consumer<SOAPMessage> msg = getConsumer(alterRequest);
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_EXECUTE), msg);
            return convertToAlterResponse(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("ExecuteService alter error", e);
        }
        return null;
    }

    @Override
    public ClearCacheResponse clearCache(ClearCacheRequest clearCacheRequest) {
        try {
            Consumer<SOAPMessage> msg = getConsumer(clearCacheRequest);
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_EXECUTE), msg);
            return convertToClearCacheResponse(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("ExecuteService clearCache error", e);
        }
        return null;
    }


    @Override
    public CancelResponse cancel(CancelRequest cancelRequest) {
        try {
            Consumer<SOAPMessage> msg = getConsumer(cancelRequest);
            SOAPMessage message = soapClient.callSoapWebService(Optional.of(SOAP_ACTION_EXECUTE), msg);
            return convertToCancelResponse(message.getSOAPBody());
        } catch (SOAPException e) {
            LOGGER.error("ExecuteService cancel error", e);
        }
        return null;
    }


    private Consumer<SOAPMessage> getConsumer(CancelRequest requestApi) {
        return message -> {
            try {
                Cancel cancel = requestApi.command();
                Properties properties = requestApi.properties();
                List<ExecuteParameter> executeParameterList = requestApi.parameters();

                SOAPElement execute = message.getSOAPBody()
                    .addChildElement(EXECUTE);
                SOAPElement commandElement = execute.addChildElement(COMMAND);

                addChildElementCancel(commandElement, cancel);

                SOAPElement propertyList = execute.addChildElement(PROPERTIES)
                    .addChildElement(PROPERTY_LIST);
                addChildElementPropertyList(propertyList, properties);
                addChildElementParameterList(execute, executeParameterList);

            } catch (SOAPException e) {
                LOGGER.error("ExecuteService ClearCacheRequest accept error", e);
            }
        };
    }

    private Consumer<SOAPMessage> getConsumer(ClearCacheRequest requestApi) {
        return message -> {
            try {
                ClearCache clearCache = requestApi.command();
                Properties properties = requestApi.properties();
                List<ExecuteParameter> executeParameterList = requestApi.parameters();

                SOAPElement execute = message.getSOAPBody()
                    .addChildElement(EXECUTE);
                SOAPElement commandElement = execute.addChildElement(COMMAND);
                addChildElementClearCache(commandElement, clearCache);

                SOAPElement propertyList = execute.addChildElement(PROPERTIES)
                    .addChildElement(PROPERTY_LIST);
                addChildElementPropertyList(propertyList, properties);
                addChildElementParameterList(execute, executeParameterList);

            } catch (SOAPException e) {
                LOGGER.error("ExecuteService ClearCacheRequest accept error", e);
            }
        };
    }

    private Consumer<SOAPMessage> getConsumer(AlterRequest requestApi) {
        return message -> {
            try {
                Alter alter = requestApi.command();
                Properties properties = requestApi.properties();
                List<ExecuteParameter> executeParameterList = requestApi.parameters();

                SOAPElement execute = message.getSOAPBody()
                    .addChildElement(EXECUTE);
                SOAPElement commandElement = execute.addChildElement(COMMAND);
                addChildElementAlter(commandElement, alter);

                SOAPElement propertyList = execute.addChildElement(PROPERTIES)
                    .addChildElement(PROPERTY_LIST);
                addChildElementPropertyList(propertyList, properties);
                addChildElementParameterList(execute, executeParameterList);

            } catch (SOAPException e) {
                LOGGER.error("ExecuteService AlterRequest accept error", e);
            }
        };
    }


    private Consumer<SOAPMessage> getConsumer(StatementRequest requestApi) {
        return message -> {
            try {
                Statement statement = requestApi.command();
                Properties properties = requestApi.properties();

                SOAPElement execute = message.getSOAPBody()
                    .addChildElement(EXECUTE);
                execute.addChildElement(COMMAND)
                    .addChildElement("Statement").setTextContent(statement.statement());

                SOAPElement propertyList = execute.addChildElement(PROPERTIES)
                    .addChildElement(PROPERTY_LIST);
                addChildElementPropertyList(propertyList, properties);

            } catch (SOAPException e) {
                LOGGER.error("ExecuteService StatementRequest accept error", e);
            }
        };

    }
}
