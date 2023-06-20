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
import org.eclipse.daanse.xmla.api.execute.ExecuteService;
import org.eclipse.daanse.xmla.api.execute.alter.AlterRequest;
import org.eclipse.daanse.xmla.api.execute.alter.AlterResponse;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelRequest;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelResponse;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheRequest;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheResponse;
import org.eclipse.daanse.xmla.api.execute.statement.StatementRequest;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.api.xmla.Aggregation;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesign;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesignDimension;
import org.eclipse.daanse.xmla.api.xmla.Alter;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Assembly;
import org.eclipse.daanse.xmla.api.xmla.Cube;
import org.eclipse.daanse.xmla.api.xmla.MajorObject;
import org.eclipse.daanse.xmla.api.xmla.ObjectReference;
import org.eclipse.daanse.xmla.api.xmla.Statement;
import org.eclipse.daanse.xmla.api.xmla.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.eclipse.daanse.xmla.client.soapmessage.Constants.PROPERTIES;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.PROPERTY_LIST;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.SOAP_ACTION_EXECUTE;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToAlterResponse;
import static org.eclipse.daanse.xmla.client.soapmessage.Convertor.convertToStatementResponse;

public class ExecuteServiceImpl extends AbstractService implements ExecuteService {

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
        // TODO Auto-generated stub
        return null;
    }

    @Override
    public CancelResponse cancel(CancelRequest capture) {
        // TODO Auto-generated stub
        return null;
    }

    private Consumer<SOAPMessage> getConsumer(AlterRequest requestApi) {
        return message -> {
            try {
                Alter alter = requestApi.command();
                Properties properties = requestApi.properties();

                SOAPElement execute = message.getSOAPBody()
                    .addChildElement("Execute");
                SOAPElement alterElement = execute.addChildElement("Command")
                    .addChildElement("Alter");
                setObject(alterElement.addChildElement("Object"), alter.object());
                setObjectDefinition(alterElement.addChildElement("ObjectDefinition"), alter.objectDefinition());

                SOAPElement propertyList = execute.addChildElement(PROPERTIES)
                    .addChildElement(PROPERTY_LIST);
                setPropertyList(propertyList, properties);

            } catch (SOAPException e) {
                LOGGER.error("ExecuteService StatementRequest accept error", e);
            }
        };
    }

    private void setObjectDefinition(SOAPElement objectDefinitionElement, MajorObject objectDefinition) throws SOAPException {
        if (objectDefinition != null) {
            setAggregationDesign(objectDefinitionElement, objectDefinition);
            setAssembly(objectDefinitionElement, objectDefinition);
            setCube(objectDefinitionElement, objectDefinition);
            setDatabase(objectDefinitionElement, objectDefinition);
            setDataSource(objectDefinitionElement, objectDefinition);
            setDataSourceView(objectDefinitionElement, objectDefinition);
            setDimension(objectDefinitionElement, objectDefinition);
            setMdxScript(objectDefinitionElement, objectDefinition);
            setMeasureGroup(objectDefinitionElement, objectDefinition);
            setMiningModel(objectDefinitionElement, objectDefinition);
            setMiningStructure(objectDefinitionElement, objectDefinition);
            setPartition(objectDefinitionElement, objectDefinition);
            setPermission(objectDefinitionElement, objectDefinition);
            setPerspective(objectDefinitionElement, objectDefinition);
            setRole(objectDefinitionElement, objectDefinition);
            setServer(objectDefinitionElement, objectDefinition);
            setTrace(objectDefinitionElement, objectDefinition);
        }
    }

    private void setTrace(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setServer(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setRole(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setPerspective(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setPermission(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setPartition(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setMiningStructure(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setMiningModel(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setMeasureGroup(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setMdxScript(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setDimension(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setDataSourceView(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setDataSource(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setDatabase(SOAPElement objectDefinitionElement, MajorObject objectDefinition) {
        //TODO
    }

    private void setCube(SOAPElement objectDefinitionElement, MajorObject objectDefinition) throws SOAPException {
        Cube cube = objectDefinition.cube();
        if (cube != null) {
            SOAPElement cubeelement = objectDefinitionElement.addChildElement("Cube");
            addChildElement(cubeelement, "Language", String.valueOf(cube.language()));
            addChildElement(cubeelement, "Collation", String.valueOf(cube.collation()));
            setTranslation(cubeelement, cube.translations());
            //TODO
        }
    }

    private void setTranslation(SOAPElement cubeelement, List<Translation> translations) {
    }

    private void setAssembly(SOAPElement objectDefinitionElement, MajorObject objectDefinition) throws SOAPException {
        Assembly assembly = objectDefinition.assembly();
        if (assembly != null) {
            SOAPElement assemblyElement = objectDefinitionElement.addChildElement("Assembly");
            addChildElement(assemblyElement, "Name", assembly.name());
            addChildElement(assemblyElement, "ID", assembly.id());
            addChildElement(assemblyElement, "CreatedTimestamp", assembly.createdTimestamp().toString());
            addChildElement(assemblyElement, "LastSchemaUpdate", assembly.lastSchemaUpdate().toString());
            addChildElement(assemblyElement, "Description", assembly.description());
            setAnnotations(assemblyElement,  assembly.annotations());
        }
    }

    private void setAnnotations(SOAPElement objectDefinitionElement, List<Annotation> annotations) {
        //TODO
    }

    private void setAggregationDesign(SOAPElement objectDefinitionElement, MajorObject objectDefinition) throws SOAPException {
        AggregationDesign aggregationDesign = objectDefinition.aggregationDesign();
        if (aggregationDesign != null) {
            SOAPElement aggregationDesignEl = objectDefinitionElement.addChildElement("AggregationDesign");
            aggregationDesign.estimatedRows().ifPresent(v -> addChildElement(aggregationDesignEl, "EstimatedRows", String.valueOf(v)));
            aggregationDesign.dimensions().ifPresent(v -> setDimensions(aggregationDesignEl, v));
            aggregationDesign.aggregations().ifPresent(v -> setAggregations(aggregationDesignEl, v));
            aggregationDesign.estimatedPerformanceGain().ifPresent(v -> addChildElement(aggregationDesignEl, "EstimatedPerformanceGain", String.valueOf(v)));
        }
    }

    private void setAggregations(SOAPElement aggregationDesignEl, List<Aggregation> v) {
    }

    private void setDimensions(SOAPElement aggregationDesignEl, List<AggregationDesignDimension> v) {
        //TODO
    }

    private void setObject(SOAPElement objectElement, ObjectReference reference) {
        if (reference != null) {
            addChildElement(objectElement, "ServerID", reference.serverID());
            addChildElement(objectElement, "DatabaseID", reference.databaseID());
            addChildElement(objectElement, "RoleID", reference.roleID());
            addChildElement(objectElement, "TraceID", reference.traceID());
            addChildElement(objectElement, "AssemblyID", reference.assemblyID());
            addChildElement(objectElement, "DimensionID", reference.dimensionID());
            addChildElement(objectElement, "DimensionPermissionID", reference.dimensionPermissionID());
            addChildElement(objectElement, "DataSourceID", reference.dataSourceID());
            addChildElement(objectElement, "DataSourcePermissionID", reference.dataSourcePermissionID());
            addChildElement(objectElement, "DatabasePermissionID", reference.databasePermissionID());
            addChildElement(objectElement, "DataSourceViewID", reference.dataSourceViewID());
            addChildElement(objectElement, "CubeID", reference.cubeID());
            addChildElement(objectElement, "MiningStructureID", reference.miningStructureID());
            addChildElement(objectElement, "MeasureGroupID", reference.measureGroupID());
            addChildElement(objectElement, "PerspectiveID", reference.perspectiveID());
            addChildElement(objectElement, "CubePermissionID", reference.cubePermissionID());
            addChildElement(objectElement, "MdxScriptID", reference.mdxScriptID());
            addChildElement(objectElement, "PartitionID", reference.partitionID());
            addChildElement(objectElement, "AggregationDesignID", reference.partitionID());
            addChildElement(objectElement, "MiningModelID", reference.miningModelID());
            addChildElement(objectElement, "MiningModelPermissionID", reference.miningModelPermissionID());
            addChildElement(objectElement, "MiningStructurePermissionID", reference.miningStructureID());
        }
    }

    private Consumer<SOAPMessage> getConsumer(StatementRequest requestApi) {
        return message -> {
            try {
                Statement statement = requestApi.command();
                Properties properties = requestApi.properties();

                SOAPElement execute = message.getSOAPBody()
                    .addChildElement("Execute");
                execute.addChildElement("Command")
                    .addChildElement("Statement").setTextContent(statement.statement());

                SOAPElement propertyList = execute.addChildElement(PROPERTIES)
                    .addChildElement(PROPERTY_LIST);
                setPropertyList(propertyList, properties);

            } catch (SOAPException e) {
                LOGGER.error("ExecuteService StatementRequest accept error", e);
            }
        };

    }
}
