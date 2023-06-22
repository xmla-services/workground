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
import org.eclipse.daanse.xmla.api.engine200_200.ExpressionBinding;
import org.eclipse.daanse.xmla.api.engine200_200.RowNumberBinding;
import org.eclipse.daanse.xmla.api.engine300.CalculationPropertiesVisualizationProperties;
import org.eclipse.daanse.xmla.api.execute.ExecuteService;
import org.eclipse.daanse.xmla.api.execute.alter.AlterRequest;
import org.eclipse.daanse.xmla.api.execute.alter.AlterResponse;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelRequest;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelResponse;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheRequest;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheResponse;
import org.eclipse.daanse.xmla.api.execute.statement.StatementRequest;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.api.xmla.Action;
import org.eclipse.daanse.xmla.api.xmla.Aggregation;
import org.eclipse.daanse.xmla.api.xmla.AggregationAttribute;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesign;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesignAttribute;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesignDimension;
import org.eclipse.daanse.xmla.api.xmla.AggregationDimension;
import org.eclipse.daanse.xmla.api.xmla.AggregationInstance;
import org.eclipse.daanse.xmla.api.xmla.AggregationInstanceAttribute;
import org.eclipse.daanse.xmla.api.xmla.AggregationInstanceDimension;
import org.eclipse.daanse.xmla.api.xmla.AggregationInstanceMeasure;
import org.eclipse.daanse.xmla.api.xmla.AlgorithmParameter;
import org.eclipse.daanse.xmla.api.xmla.Alter;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Assembly;
import org.eclipse.daanse.xmla.api.xmla.Attach;
import org.eclipse.daanse.xmla.api.xmla.AttributeBinding;
import org.eclipse.daanse.xmla.api.xmla.AttributePermission;
import org.eclipse.daanse.xmla.api.xmla.AttributeTranslation;
import org.eclipse.daanse.xmla.api.xmla.Binding;
import org.eclipse.daanse.xmla.api.xmla.CalculatedMeasureBinding;
import org.eclipse.daanse.xmla.api.xmla.CalculationProperty;
import org.eclipse.daanse.xmla.api.xmla.CellPermission;
import org.eclipse.daanse.xmla.api.xmla.ColumnBinding;
import org.eclipse.daanse.xmla.api.xmla.Command;
import org.eclipse.daanse.xmla.api.xmla.Cube;
import org.eclipse.daanse.xmla.api.xmla.CubeAttribute;
import org.eclipse.daanse.xmla.api.xmla.CubeAttributeBinding;
import org.eclipse.daanse.xmla.api.xmla.CubeDimension;
import org.eclipse.daanse.xmla.api.xmla.CubeDimensionBinding;
import org.eclipse.daanse.xmla.api.xmla.CubeDimensionPermission;
import org.eclipse.daanse.xmla.api.xmla.CubeHierarchy;
import org.eclipse.daanse.xmla.api.xmla.CubePermission;
import org.eclipse.daanse.xmla.api.xmla.DSVTableBinding;
import org.eclipse.daanse.xmla.api.xmla.DataItem;
import org.eclipse.daanse.xmla.api.xmla.DataSource;
import org.eclipse.daanse.xmla.api.xmla.DataSourceView;
import org.eclipse.daanse.xmla.api.xmla.DataSourceViewBinding;
import org.eclipse.daanse.xmla.api.xmla.Database;
import org.eclipse.daanse.xmla.api.xmla.Dimension;
import org.eclipse.daanse.xmla.api.xmla.DimensionBinding;
import org.eclipse.daanse.xmla.api.xmla.DrillThroughAction;
import org.eclipse.daanse.xmla.api.xmla.ErrorConfiguration;
import org.eclipse.daanse.xmla.api.xmla.FoldingParameters;
import org.eclipse.daanse.xmla.api.xmla.Group;
import org.eclipse.daanse.xmla.api.xmla.IncrementalProcessingNotification;
import org.eclipse.daanse.xmla.api.xmla.InheritedBinding;
import org.eclipse.daanse.xmla.api.xmla.Kpi;
import org.eclipse.daanse.xmla.api.xmla.MajorObject;
import org.eclipse.daanse.xmla.api.xmla.MdxScript;
import org.eclipse.daanse.xmla.api.xmla.MeasureBinding;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroup;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroupBinding;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroupDimensionBinding;
import org.eclipse.daanse.xmla.api.xmla.MiningModel;
import org.eclipse.daanse.xmla.api.xmla.MiningModelColumn;
import org.eclipse.daanse.xmla.api.xmla.MiningModelPermission;
import org.eclipse.daanse.xmla.api.xmla.MiningStructure;
import org.eclipse.daanse.xmla.api.xmla.MiningStructureColumn;
import org.eclipse.daanse.xmla.api.xmla.MiningStructurePermission;
import org.eclipse.daanse.xmla.api.xmla.ObjectReference;
import org.eclipse.daanse.xmla.api.xmla.Partition;
import org.eclipse.daanse.xmla.api.xmla.Permission;
import org.eclipse.daanse.xmla.api.xmla.Perspective;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveAction;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveAttribute;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveCalculation;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveDimension;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveHierarchy;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveKpi;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveMeasure;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveMeasureGroup;
import org.eclipse.daanse.xmla.api.xmla.ProactiveCaching;
import org.eclipse.daanse.xmla.api.xmla.ProactiveCachingBinding;
import org.eclipse.daanse.xmla.api.xmla.ProactiveCachingIncrementalProcessingBinding;
import org.eclipse.daanse.xmla.api.xmla.ProactiveCachingQueryBinding;
import org.eclipse.daanse.xmla.api.xmla.QueryBinding;
import org.eclipse.daanse.xmla.api.xmla.QueryNotification;
import org.eclipse.daanse.xmla.api.xmla.ReportAction;
import org.eclipse.daanse.xmla.api.xmla.ReportFormatParameter;
import org.eclipse.daanse.xmla.api.xmla.ReportParameter;
import org.eclipse.daanse.xmla.api.xmla.Role;
import org.eclipse.daanse.xmla.api.xmla.RowBinding;
import org.eclipse.daanse.xmla.api.xmla.Server;
import org.eclipse.daanse.xmla.api.xmla.StandardAction;
import org.eclipse.daanse.xmla.api.xmla.Statement;
import org.eclipse.daanse.xmla.api.xmla.TableBinding;
import org.eclipse.daanse.xmla.api.xmla.TabularBinding;
import org.eclipse.daanse.xmla.api.xmla.TimeAttributeBinding;
import org.eclipse.daanse.xmla.api.xmla.TimeBinding;
import org.eclipse.daanse.xmla.api.xmla.Trace;
import org.eclipse.daanse.xmla.api.xmla.Translation;
import org.eclipse.daanse.xmla.api.xmla.UserDefinedGroupBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.Duration;
import java.math.BigInteger;
import java.time.Instant;
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
                setObject(alterElement, alter.object());
                setObjectDefinition(alterElement, alter.objectDefinition());

                SOAPElement propertyList = execute.addChildElement(PROPERTIES)
                    .addChildElement(PROPERTY_LIST);
                setPropertyList(propertyList, properties);

            } catch (SOAPException e) {
                LOGGER.error("ExecuteService StatementRequest accept error", e);
            }
        };
    }

    private void setObjectDefinition(
        SOAPElement element,
        MajorObject objectDefinition
    ) throws SOAPException {
        if (objectDefinition != null) {
            SOAPElement chElement = element.addChildElement("ObjectDefinition");
            setAggregationDesign(chElement, objectDefinition.aggregationDesign());
            setAssembly(chElement, objectDefinition.assembly());
            setCube(chElement, objectDefinition.cube());
            setDatabase(chElement, objectDefinition.database());
            setDataSource(chElement, objectDefinition.dataSource());
            setDataSourceView(chElement, objectDefinition.dataSourceView());
            setDimension(chElement, objectDefinition.dimension());
            setMdxScript(chElement, objectDefinition.mdxScript());
            setMeasureGroup(chElement, objectDefinition.measureGroup());
            setMiningModel(chElement, objectDefinition.miningModel());
            setMiningStructure(chElement, objectDefinition.miningStructure());
            setPartition(chElement, objectDefinition.partition());
            setPermission(chElement, objectDefinition.permission());
            setPerspective(chElement, objectDefinition.perspective());
            setRole(chElement, objectDefinition.role());
            setServer(chElement, objectDefinition.server());
            setTrace(chElement, objectDefinition.trace());
        }
    }

    private void setTrace(SOAPElement element, Trace it) {
        //TODO
    }

    private void setServer(SOAPElement element, Server it) {
        //TODO
    }

    private void setRole(SOAPElement element, Role it) {
        //TODO
    }

    private void setPerspective(SOAPElement element, Perspective it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Perspective");
            setTranslationList(chElement, it.translations());
            addChildElement(chElement, "DefaultMeasure", it.defaultMeasure());
            setPerspectiveDimensionList(chElement, it.dimensions());
            setPerspectiveMeasureGroupList(chElement, it.measureGroups());
            setPerspectiveCalculationList(chElement, it.calculations());
            setPerspectiveKpiList(chElement, it.kpis());
            setPerspectiveActionList(chElement, it.actions());
        }
    }

    private void setPerspectiveActionList(SOAPElement element, List<PerspectiveAction> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Actions");
            list.forEach(it -> setPerspectiveAction(chElement, it));
        }

    }

    private void setPerspectiveAction(SOAPElement element, PerspectiveAction it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Action");
            addChildElement(chElement, "ActionID", it.actionID());
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
        }
    }

    private void setPerspectiveKpiList(SOAPElement element, List<PerspectiveKpi> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Kpis");
            list.forEach(it -> setPerspectiveKpi(chElement, it));
        }
    }

    private void setPerspectiveKpi(SOAPElement element, PerspectiveKpi it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Kpi");
            addChildElement(chElement, "KpiID", it.kpiID());
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
        }
    }

    private void setPerspectiveCalculationList(SOAPElement element, List<PerspectiveCalculation> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Calculations");
            list.forEach(it -> setPerspectiveCalculation(chElement, it));
        }
    }

    private void setPerspectiveCalculation(SOAPElement element, PerspectiveCalculation it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Calculation");
            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "Type", it.type());
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
        }
    }

    private void setPerspectiveMeasureGroupList(SOAPElement element, List<PerspectiveMeasureGroup> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "MeasureGroups");
            list.forEach(it -> setPerspectiveMeasureGroup(chElement, it));
        }

    }

    private void setPerspectiveMeasureGroup(SOAPElement element, PerspectiveMeasureGroup it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "MeasureGroup");
            addChildElement(chElement, "MeasureGroupID", it.measureGroupID());
            it.measures().ifPresent(v -> setPerspectiveMeasureList(chElement, v));
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
        }
    }

    private void setPerspectiveMeasureList(SOAPElement element, List<PerspectiveMeasure> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Measures");
            list.forEach(it -> setPerspectiveMeasure(chElement, it));
        }

    }

    private void setPerspectiveMeasure(SOAPElement element, PerspectiveMeasure it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Measure");
            addChildElement(chElement, "MeasureID", it.measureID());
            setAnnotationList(chElement, it.annotations());
        }
    }

    private void setPerspectiveDimensionList(SOAPElement element, List<PerspectiveDimension> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Dimensions");
            list.forEach(it -> setPerspectiveDimension(chElement, it));
        }
    }

    private void setPerspectiveDimension(SOAPElement element, PerspectiveDimension it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Dimension");
            addChildElement(chElement, "CubeDimensionID", it.cubeDimensionID());
            it.attributes().ifPresent(v -> setPerspectiveAttributeList(chElement, v));
            it.hierarchies().ifPresent(v -> setPerspectiveHierarchyList(chElement, v));
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
        }
    }

    private void setPerspectiveHierarchyList(SOAPElement element, List<PerspectiveHierarchy> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Hierarchies");
            list.forEach(it -> setPerspectiveHierarchy(chElement, it));
        }
    }

    private void setPerspectiveHierarchy(SOAPElement element, PerspectiveHierarchy it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Hierarchy");
            addChildElement(chElement, "HierarchyID", it.hierarchyID());
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
        }
    }

    private void setPerspectiveAttributeList(SOAPElement element, List<PerspectiveAttribute> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Attributes");
            list.forEach(it -> setPerspectiveAttribute(chElement, it));
        }
    }

    private void setPerspectiveAttribute(SOAPElement element, PerspectiveAttribute it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Attribute");
            addChildElement(chElement, "AttributeID", it.attributeID());
            it.attributeHierarchyVisible().ifPresent(v -> addChildElement(chElement, "AttributeHierarchyVisible", String.valueOf(v)));
            it.defaultMember().ifPresent(v -> addChildElement(chElement, "DefaultMember", v));
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
        }
    }

    private void setPermission(SOAPElement element, Permission it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Permission");
            addChildElement(chElement, "RoleID", it.roleID());
            it.process().ifPresent(v -> addChildElement(chElement, "Process", String.valueOf(v)));
            it.readDefinition().ifPresent(v -> addChildElement(chElement, "ReadDefinition", v.getValue()));
            it.read().ifPresent(v -> addChildElement(chElement, "Read", v.getValue()));
            it.write().ifPresent(v -> addChildElement(chElement, "Write", v.getValue()));
        }
    }

    private void setPartition(SOAPElement element, Partition it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Partition");
            setTabularBinding(chElement, it.source());
            addChildElement(chElement, "ProcessingPriority", String.valueOf(it.processingPriority()));
            addChildElement(chElement, "AggregationPrefix", it.aggregationPrefix());
            setPartitionStorageMode(chElement, it.storageMode());
            addChildElement(chElement, "ProcessingMode", it.processingMode());
            setErrorConfiguration(chElement, it.errorConfiguration());
            addChildElement(chElement, "StorageLocation", it.storageLocation());
            addChildElement(chElement, "RemoteDatasourceID", it.remoteDatasourceID());
            addChildElement(chElement, "Slice", it.slice());
            setProactiveCaching(chElement, it.proactiveCaching());
            addChildElement(chElement, "Type", it.type());
            addChildElement(chElement, "EstimatedSize", String.valueOf(it.estimatedSize()));
            addChildElement(chElement, "EstimatedRows", String.valueOf(it.estimatedRows()));
            setPartitionCurrentStorageMode(chElement, it.currentStorageMode());
            addChildElement(chElement, "AggregationDesignID", it.aggregationDesignID());
            setAggregationInstanceList(chElement, it.aggregationInstances());
            setDataSourceViewBinding(chElement, it.aggregationInstanceSource());
            addChildElement(chElement, "LastProcessed", convertInstant(it.lastProcessed()));
            addChildElement(chElement, "State", it.state());
            addChildElement(chElement, "StringStoresCompatibilityLevel", String.valueOf(it.stringStoresCompatibilityLevel()));
            addChildElement(chElement, "CurrentStringStoresCompatibilityLevel", String.valueOf(it.currentStringStoresCompatibilityLevel()));
            addChildElement(chElement, "DirectQueryUsage", it.directQueryUsage());
        }
    }

    private void setAggregationInstanceList(SOAPElement element, List<AggregationInstance> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "AggregationInstances");
            list.forEach(it -> setAggregationInstance(chElement, it));
        }
    }

    private void setAggregationInstance(SOAPElement element, AggregationInstance it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "AggregationInstance");
            addChildElement(chElement, "AggregationType", it.aggregationType());
            setTabularBinding(chElement, it.source());
            setAggregationInstanceDimensionList(chElement, it.dimensions());
            setAggregationInstanceMeasureList(chElement, it.measures());
        }
    }

    private void setAggregationInstanceMeasureList(SOAPElement element, List<AggregationInstanceMeasure> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Measures");
            list.forEach(it -> setAggregationInstanceMeasure(chElement, it));
        }
    }

    private void setAggregationInstanceMeasure(SOAPElement element, AggregationInstanceMeasure it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Measure");
            addChildElement(chElement, "MeasureID", it.measureID());
            setColumnBinding(chElement, it.source());
        }
    }

    private void setColumnBinding(SOAPElement element, ColumnBinding it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Source");
            addChildElement(chElement, "TableID", it.tableID());
            addChildElement(chElement, "ColumnID", it.columnID());
        }
    }

    private void setAggregationInstanceDimensionList(SOAPElement element, List<AggregationInstanceDimension> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Dimensions");
            list.forEach(it -> setAggregationInstanceDimension(chElement, it));
        }
    }

    private void setAggregationInstanceDimension(SOAPElement element, AggregationInstanceDimension it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Dimension");
            addChildElement(chElement, "CubeDimensionID", it.cubeDimensionID());
            it.attributes().ifPresent(v -> setAggregationInstanceAttributeList(chElement, v));
        }
    }

    private void setAggregationInstanceAttributeList(SOAPElement element, List<AggregationInstanceAttribute> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Attributes");
            list.forEach(it -> setAggregationInstanceAttribute(chElement, it));
        }
    }

    private void setAggregationInstanceAttribute(SOAPElement element, AggregationInstanceAttribute it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Attribute");
            addChildElement(chElement, "AttributeID", it.attributeID());
            it.keyColumns().ifPresent(v -> setDataItemList(chElement, v));
        }
    }

    private void setDataItemList(SOAPElement element, List<DataItem> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "KeyColumns");
            list.forEach(it -> setDataItem(chElement, it));
        }
    }

    private void setDataItem(SOAPElement element, DataItem it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "KeyColumn");
            addChildElement(chElement, "DataType", it.dataType());
            it.dataSize().ifPresent(v -> addChildElement(chElement, "DataSize", String.valueOf(v)));
            it.mimeType().ifPresent(v -> addChildElement(chElement, "MimeType", v));
            it.nullProcessing().ifPresent(v -> addChildElement(chElement, "NullProcessing", v.getValue()));
            it.trimming().ifPresent(v -> addChildElement(chElement, "Trimming", v));
            it.invalidXmlCharacters().ifPresent(v -> addChildElement(chElement, "InvalidXmlCharacters", v.getValue()));
            it.collation().ifPresent(v -> addChildElement(chElement, "Collation", v));
            it.format().ifPresent(v -> addChildElement(chElement, "Format", v.getValue()));
            it.source().ifPresent(v -> setBinding(chElement, v));
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
        }
    }

    private void setPartitionCurrentStorageMode(SOAPElement element, Partition.CurrentStorageMode it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "CurrentStorageMode");
            chElement.setTextContent(it.value().value());
            chElement.setAttribute("valuens", it.valuens());
        }
    }

    private void setPartitionStorageMode(SOAPElement element, Partition.StorageMode it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "StorageMode");
            chElement.setTextContent(it.value().value());
            chElement.setAttribute("valuens", it.valuens());
        }
    }

    private void setTabularBinding(SOAPElement element, TabularBinding it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Source");
            if (it instanceof TableBinding tb) {
                tb.dataSourceID().ifPresent(v -> addChildElement(chElement, "DataSourceID", v));
                addChildElement(chElement, "DbTableName", tb.dbTableName());
                tb.dbSchemaName().ifPresent(v -> addChildElement(chElement, "DbSchemaName", v));
            }
            if (it instanceof QueryBinding qb) {
                qb.dataSourceID().ifPresent(v -> addChildElement(chElement, "DataSourceID", v));
                addChildElement(chElement, "QueryDefinition", qb.queryDefinition());
            }
            if (it instanceof DSVTableBinding dtb) {
                dtb.dataSourceViewID().ifPresent(v -> addChildElement(chElement, "DataSourceViewID", v));
                addChildElement(chElement, "TableID", dtb.tableID());
                dtb.dataEmbeddingStyle().ifPresent(v -> addChildElement(chElement, "DataEmbeddingStyle", v));
            }
        }
    }

    private void setMiningStructure(SOAPElement element, MiningStructure it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "MiningStructure");
            it.source().ifPresent(v -> setBinding(chElement, v));
            it.lastProcessed().ifPresent(v -> addChildElement(chElement, "LastProcessed", convertInstant(v)));
            it.translations().ifPresent(v -> setTranslationList(chElement, v));
            it.language().ifPresent(v -> addChildElement(chElement, "Language", String.valueOf(v)));
            it.collation().ifPresent(v -> addChildElement(chElement, "Collation", v));
            it.errorConfiguration().ifPresent(v -> setErrorConfiguration(chElement, v));
            it.cacheMode().ifPresent(v -> addChildElement(chElement, "CacheMode", v));
            it.holdoutMaxPercent().ifPresent(v -> addChildElement(chElement, "HoldoutMaxPercent", String.valueOf(v)));
            it.holdoutMaxCases().ifPresent(v -> addChildElement(chElement, "HoldoutMaxCases", String.valueOf(v)));
            it.holdoutMaxCases().ifPresent(v -> addChildElement(chElement, "HoldoutSeed", String.valueOf(v)));
            it.holdoutMaxCases().ifPresent(v -> addChildElement(chElement, "HoldoutActualSize", String.valueOf(v)));
            setMiningStructureColumnList(chElement, it.columns());
            it.state().ifPresent(v -> addChildElement(chElement, "State", v));
            it.miningStructurePermissions().ifPresent(v -> setMiningStructurePermissionList(chElement, v));
            it.miningModels().ifPresent(v -> setMiningModelList(chElement, v));
        }
    }

    private void setMiningModelList(SOAPElement element, List<MiningModel> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "MiningModels");
            list.forEach(it -> setMiningModel(chElement, it));
        }
    }

    private void setMiningStructurePermissionList(SOAPElement element, List<MiningStructurePermission> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "MiningStructurePermissions");
            list.forEach(it -> setMiningStructurePermission(chElement, it));

        }

    }

    private void setMiningStructurePermission(SOAPElement element, MiningStructurePermission it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "MiningStructurePermission");
            addChildElement(chElement, "RoleID", it.roleID());
            it.process().ifPresent(v -> addChildElement(chElement, "Process", String.valueOf(v)));
            it.readDefinition().ifPresent(v -> addChildElement(chElement, "ReadDefinition", v.getValue()));
            it.read().ifPresent(v -> addChildElement(chElement, "Read", v.getValue()));
            it.write().ifPresent(v -> addChildElement(chElement, "Write", v.getValue()));
        }
    }

    private void setMiningStructureColumnList(SOAPElement element, List<MiningStructureColumn> list) {
        //            @XmlElement(name = "Column", required = true)
        //            @XmlElementWrapper(name = "Columns", required = true)
        //            protected List<MiningStructureColumn> columns;
    }

    private void setMiningModel(SOAPElement element, MiningModel it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "MiningModel");
            addChildElement(chElement, "Algorithm", it.algorithm());
            it.lastProcessed().ifPresent(v -> addChildElement(chElement, "LastProcessed", convertInstant(v)));
            it.algorithmParameters().ifPresent(v -> setAlgorithmParameterList(chElement, v));
            it.allowDrillThrough().ifPresent(v -> addChildElement(chElement, "AllowDrillThrough", String.valueOf(v)));
            it.translations().ifPresent(v -> setAttributeTranslationList(chElement, v));
            it.columns().ifPresent(v -> setMiningModelColumnList(chElement, v));
            it.state().ifPresent(v -> addChildElement(chElement, "State", v));
            it.foldingParameters().ifPresent(v -> setFoldingParameters(chElement, v));
            it.filter().ifPresent(v -> addChildElement(chElement, "Filter", v));
            it.miningModelPermissions().ifPresent(v -> setMiningModelPermissionList(chElement, v));
            it.language().ifPresent(v -> addChildElement(chElement, "Language", v));
            it.collation().ifPresent(v -> addChildElement(chElement, "Collation", v));
        }
    }

    private void setMiningModelPermissionList(SOAPElement element, List<MiningModelPermission> list) {
        //            @XmlElement(name = "MiningModelPermission")
        //            @XmlElementWrapper(name = "MiningModelPermissions")
        //            protected List<MiningModelPermission> miningModelPermissions;
    }

    private void setFoldingParameters(SOAPElement element, FoldingParameters it) {
        //            @XmlElement(name = "FoldingParameters")
        //            protected FoldingParameters foldingParameters;
    }

    private void setMiningModelColumnList(SOAPElement element, List<MiningModelColumn> list) {
        //            @XmlElement(name = "Column", required = true)
        //            @XmlElementWrapper(name = "Columns", required = true)
        //            protected List<MiningModelColumn> columns;
    }

    private void setAttributeTranslationList(SOAPElement element, List<AttributeTranslation> v) {
        //            @XmlElement(name = "Translation")
        //            @XmlElementWrapper(name = "Translations")
        //            protected List<AttributeTranslation> translations;
    }

    private void setAlgorithmParameterList(SOAPElement element, List<AlgorithmParameter> list) {
        //            @XmlElement(name = "AlgorithmParameter")
        //            @XmlElementWrapper(name = "AlgorithmParameters")
        //            protected List<AlgorithmParameter> algorithmParameters;
    }

    private void setMeasureGroup(SOAPElement element, MeasureGroup it) {
        //TODO
    }

    private void setMdxScript(SOAPElement element, MdxScript it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "MdxScript");
            setCommandList(chElement, it.commands());
            addChildElement(chElement, "DefaultScript", String.valueOf(it.defaultScript()));
            setCalculationPropertyList(chElement, it.calculationProperties());
        }
    }

    private void setCalculationPropertyList(SOAPElement element, List<CalculationProperty> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "CalculationProperties");
            list.forEach(it -> setCalculationProperty(chElement, it));
        }
    }

    private void setCalculationProperty(SOAPElement element, CalculationProperty it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "CalculationProperty");
            addChildElement(chElement, "CalculationReference", it.calculationReference());
            addChildElement(chElement, "CalculationType", it.calculationType());
            setTranslationList(chElement, it.translations());
            addChildElement(chElement, "Description", it.description());
            addChildElement(chElement, "Visible", String.valueOf(it.visible()));
            addChildElement(chElement, "SolveOrder", String.valueOf(it.solveOrder()));
            addChildElement(chElement, "FormatString", it.formatString());
            addChildElement(chElement, "ForeColor", it.foreColor());
            addChildElement(chElement, "BackColor", it.backColor());
            addChildElement(chElement, "FontName", it.fontName());
            addChildElement(chElement, "FontSize", it.fontSize());
            addChildElement(chElement, "FontFlags", it.fontFlags());
            addChildElement(chElement, "NonEmptyBehavior", it.nonEmptyBehavior());
            addChildElement(chElement, "AssociatedMeasureGroupID", it.associatedMeasureGroupID());
            addChildElement(chElement, "DisplayFolder", it.displayFolder());
            addChildElement(chElement, "Language", String.valueOf(it.language()));
            setCalculationPropertiesVisualizationProperties(chElement, it.visualizationProperties());
        }
    }

    private void setCalculationPropertiesVisualizationProperties(SOAPElement element, CalculationPropertiesVisualizationProperties it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "VisualizationProperties");
            addChildElement(chElement, "IsDefaultMeasure", String.valueOf(it.isDefaultMeasure()));
            addChildElement(chElement, "IsSimpleMeasure", String.valueOf(it.isSimpleMeasure()));
        }
    }

    private void setCommandList(SOAPElement element, List<Command> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Commands");
            list.forEach(it -> setCommand(chElement, it));
        }
    }

    private void setCommand(SOAPElement element, Command it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Command");
            //TODO
            if (it instanceof Alter) {
            }
            if (it instanceof Attach) {

            }
            //Backup, Batch, BeginTransaction, Cancel, ClearCache,
            //    CloneDatabase, CommitTransaction, Create, DBCC, Delete, DesignAggregations, Detach, Drop, ImageLoad, ImageSave,
            //    Insert, Lock, MergePartitions, NotifyTableChange, Process, Restore, RollbackTransaction, SetAuthContext,
            //    Statement, Subscribe, Synchronize, Unlock, Unsubscribe, Update, UpdateCells
        }
    }

    private void setDimension(SOAPElement element, Dimension dimension) {
        //TODO
    }

    private void setDataSourceView(SOAPElement objectDefinitionElement, DataSourceView it) {
        //TODO
    }

    private void setDataSource(SOAPElement objectDefinitionElement, DataSource it) {
        //TODO
    }

    private void setDatabase(SOAPElement objectDefinitionElement, Database it) {
        //TODO
    }

    private void setCube(SOAPElement objectDefinitionElement, Cube cube) throws SOAPException {
        if (cube != null) {
            SOAPElement cubeelement = objectDefinitionElement.addChildElement("Cube");
            addChildElement(cubeelement, "Language", String.valueOf(cube.language()));
            addChildElement(cubeelement, "Collation", String.valueOf(cube.collation()));
            setTranslationList(cubeelement, cube.translations());
            setCubeDimensionsList(cubeelement, cube.dimensions());
            setCubePermissionList(cubeelement, cube.cubePermissions());
            setMdxScriptList(cubeelement, cube.mdxScripts());
            setPerspectiveList(cubeelement, cube.perspectives());
            addChildElement(cubeelement, "State", cube.state());
            addChildElement(cubeelement, "DefaultMeasure", cube.defaultMeasure());
            addChildElement(cubeelement, "Visible", String.valueOf(cube.visible()));
            setMeasureGroupList(cubeelement, cube.measureGroups());
            setDataSourceViewBinding(cubeelement, cube.source());
            addChildElement(cubeelement, "AggregationPrefix", cube.aggregationPrefix());
            addChildElement(cubeelement, "ProcessingPriority", String.valueOf(cube.processingPriority()));
            setCubeStorageMode(cubeelement, cube.storageMode());
            addChildElement(cubeelement, "ProcessingMode", cube.processingMode());
            addChildElement(cubeelement, "ScriptCacheProcessingMode", cube.scriptCacheProcessingMode());
            addChildElement(cubeelement, "ScriptErrorHandlingMode", cube.scriptErrorHandlingMode());
            addChildElement(cubeelement, "DaxOptimizationMode", cube.daxOptimizationMode());
            setProactiveCaching(cubeelement, cube.proactiveCaching());
            setKpiList(cubeelement, cube.kpis());
            setErrorConfiguration(cubeelement, cube.errorConfiguration());
            setActionList(cubeelement, cube.actions());
            addChildElement(cubeelement, "StorageLocation", cube.storageLocation());
            addChildElement(cubeelement, "EstimatedRows", String.valueOf(cube.estimatedRows()));
            addChildElement(cubeelement, "LastProcessed", String.valueOf(cube.lastProcessed()));
        }
    }

    private void setActionList(SOAPElement element, List<Action> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Actions");
            list.forEach(it -> setAction(chElement, it));
        }
    }

    private void setAction(SOAPElement element, Action it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Action");
            addChildElement(chElement, "Name", it.name());
            it.id().ifPresent(v -> addChildElement(chElement, "ID", v));
            it.caption().ifPresent(v -> addChildElement(chElement, "Caption", v));
            it.captionIsMdx().ifPresent(v -> addChildElement(chElement, "CaptionIsMdx", String.valueOf(v)));
            it.translations().ifPresent(v -> setTranslationList(chElement, v));
            addChildElement(chElement, "TargetType", it.targetType().getValue());
            it.target().ifPresent(v -> addChildElement(chElement, "Target", v));
            it.condition().ifPresent(v -> addChildElement(chElement, "Condition", v));
            addChildElement(chElement, "Type", it.type().getValue());
            it.invocation().ifPresent(v -> addChildElement(chElement, "Invocation", v));
            it.application().ifPresent(v -> addChildElement(chElement, "Application", v));
            it.description().ifPresent(v -> addChildElement(chElement, "Description", v));
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));

            if (it instanceof DrillThroughAction dta) {
                dta.defaultAction().ifPresent(v -> addChildElement(chElement, "Default", String.valueOf(v)));
                dta.columns().ifPresent(v -> setBindingList(chElement, v));
                dta.maximumRows().ifPresent(v -> addChildElement(chElement, "MaximumRows", String.valueOf(v)));
            }
            if (it instanceof ReportAction ra) {
                addChildElement(chElement, "ReportServer", ra.reportServer());
                ra.path().ifPresent(v -> addChildElement(chElement, "Path", v));
                ra.reportParameters().ifPresent(v -> setReportParameterList(chElement, v));
                ra.reportFormatParameters().ifPresent(v -> setReportFormatParameterList(chElement, v));
            }
            if (it instanceof StandardAction sa) {
                addChildElement(chElement, "Expression", sa.expression());
            }
        }
    }

    private void setReportFormatParameterList(SOAPElement element, List<ReportFormatParameter> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "ReportFormatParameters");
            list.forEach(it -> setReportFormatParameter(chElement, it));
        }

    }

    private void setReportFormatParameter(SOAPElement element, ReportFormatParameter it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "ReportFormatParameter");
            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "Value", it.value());
        }
    }

    private void setReportParameterList(SOAPElement element, List<ReportParameter> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "ReportParameters");
            list.forEach(it -> setReportParameter(chElement, it));
        }
    }

    private void setReportParameter(SOAPElement element, ReportParameter it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "ReportParameter");
            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "Value", it.value());
        }
    }

    private void setBindingList(SOAPElement element, List<Binding> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Columns");
            list.forEach(it -> setBinding(chElement, it));
        }
    }

    private void setBinding(SOAPElement element, Binding it) {
        if (it != null) {
            if (it instanceof ColumnBinding cb) {
                SOAPElement chElement = addChildElement(element, "ColumnBinding");
                addChildElement(chElement, "TableID", cb.tableID());
                addChildElement(chElement, "ColumnID", cb.columnID());
            }
            if (it instanceof RowBinding rb) {
                SOAPElement chElement = addChildElement(element, "RowBinding");
                addChildElement(chElement, "TableID", rb.tableID());
            }
            if (it instanceof DataSourceViewBinding dsvb) {
                SOAPElement chElement = addChildElement(element, "DataSourceViewBinding");
                addChildElement(chElement, "DataSourceViewID", dsvb.dataSourceViewID());
            }
            if (it instanceof AttributeBinding ab) {
                SOAPElement chElement = addChildElement(element, "AttributeBinding");
                addChildElement(chElement, "AttributeID", ab.attributeID());
                addChildElement(chElement, "Type", ab.type() == null ? null : ab.type().getValue());
                addChildElement(chElement, "Ordinal", String.valueOf(ab.ordinal()));
            }
            if (it instanceof UserDefinedGroupBinding udgb) {
                SOAPElement chElement = addChildElement(element, "UserDefinedGroupBinding");
                addChildElement(chElement, "AttributeID", udgb.attributeID());
                udgb.groups().ifPresent(v -> setGroupList(chElement, v));
            }
            if (it instanceof MeasureBinding mb) {
                SOAPElement chElement = addChildElement(element, "MeasureBinding");
                addChildElement(chElement, "MeasureID", mb.measureID());
            }
            if (it instanceof CubeAttributeBinding cab) {
                SOAPElement chElement = addChildElement(element, "CubeAttributeBinding");
                addChildElement(chElement, "CubeID", cab.cubeID());
                addChildElement(chElement, "CubeDimensionID", cab.cubeDimensionID());
                addChildElement(chElement, "AttributeID", cab.attributeID());
                addChildElement(chElement, "Type", cab.type() == null ? null : cab.type().getValue());
                cab.ordinal().ifPresent(v -> setCubeAttributeBindingOrdinalList(chElement, v));
            }
            if (it instanceof DimensionBinding db) {
                SOAPElement chElement = addChildElement(element, "DimensionBinding");
                addChildElement(chElement, "DataSourceID", db.dataSourceID());
                addChildElement(chElement, "DimensionID", db.dimensionID());
                db.persistence().ifPresent(v -> addChildElement(chElement, "Persistence", v.getValue()));
                db.refreshPolicy().ifPresent(v -> addChildElement(chElement, "RefreshPolicy", v.getValue()));
                db.refreshInterval().ifPresent(v -> addChildElement(chElement, "RefreshInterval", String.valueOf(v)));
            }
            if (it instanceof CubeDimensionBinding cdb) {
                SOAPElement chElement = addChildElement(element, "CubeDimensionBinding");
                addChildElement(chElement, "DataSourceID", cdb.dataSourceID());
                addChildElement(chElement, "CubeID", cdb.cubeID());
                addChildElement(chElement, "CubeDimensionID", cdb.cubeDimensionID());
                cdb.filter().ifPresent(v -> addChildElement(chElement, "Filter", v));
            }
            if (it instanceof MeasureGroupBinding mgb) {
                SOAPElement chElement = addChildElement(element, "MeasureGroupBinding");
                addChildElement(chElement, "DataSourceID", mgb.dataSourceID());
                addChildElement(chElement, "CubeID", mgb.cubeID());
                addChildElement(chElement, "MeasureGroupID", mgb.measureGroupID());
                mgb.persistence().ifPresent(v -> addChildElement(chElement, "Persistence", v.getValue()));
                mgb.refreshPolicy().ifPresent(v -> addChildElement(chElement, "RefreshPolicy", v.getValue()));
                mgb.refreshInterval().ifPresent(v -> addChildElement(chElement, "RefreshInterval", convertDuration(v)));
                mgb.filter().ifPresent(v -> addChildElement(chElement, "Filter", v));
            }
            if (it instanceof MeasureGroupDimensionBinding mgdb) {
                SOAPElement chElement = addChildElement(element, "MeasureGroupDimensionBinding");
                addChildElement(chElement, "CubeDimensionID", mgdb.cubeDimensionID());
            }
            if (it instanceof TimeBinding tb) {
                SOAPElement chElement = addChildElement(element, "TimeBinding");
                addChildElement(chElement, "CalendarStartDate", convertInstant(tb.calendarStartDate()));
                addChildElement(chElement, "CalendarEndDate", convertInstant(tb.calendarEndDate()));
                tb.firstDayOfWeek().ifPresent(v -> addChildElement(chElement, "FirstDayOfWeek", String.valueOf(v)));
                tb.calendarLanguage().ifPresent(v -> addChildElement(chElement, "CalendarLanguage", String.valueOf(v)));
                tb.fiscalFirstMonth().ifPresent(v -> addChildElement(chElement, "FiscalFirstMonth", String.valueOf(v)));
                tb.fiscalFirstDayOfMonth().ifPresent(v -> addChildElement(chElement, "FiscalFirstDayOfMonth", String.valueOf(v)));
                tb.fiscalYearName().ifPresent(v -> addChildElement(chElement, "FiscalYearName", v.getValue()));
                tb.reportingFirstMonth().ifPresent(v -> addChildElement(chElement, "ReportingFirstMonth", String.valueOf(v)));
                tb.reportingFirstWeekOfMonth().ifPresent(v -> addChildElement(chElement, "ReportingFirstWeekOfMonth", v));
                tb.reportingWeekToMonthPattern().ifPresent(v -> addChildElement(chElement, "ReportingWeekToMonthPattern", v.getValue()));
                tb.manufacturingFirstMonth().ifPresent(v -> addChildElement(chElement, "ManufacturingFirstMonth", String.valueOf(v)));
                tb.manufacturingFirstWeekOfMonth().ifPresent(v -> addChildElement(chElement, "ManufacturingFirstWeekOfMonth", String.valueOf(v)));
                tb.manufacturingExtraMonthQuarter().ifPresent(v -> addChildElement(chElement, "ManufacturingExtraMonthQuarter", String.valueOf(v)));
            }
            if (it instanceof TimeAttributeBinding) {
                SOAPElement chElement = addChildElement(element, "TimeAttributeBinding");
            }
            if (it instanceof InheritedBinding) {
                SOAPElement chElement = addChildElement(element, "InheritedBinding");

            }
            if (it instanceof CalculatedMeasureBinding cmb) {
                SOAPElement chElement = addChildElement(element, "CalculatedMeasureBinding");
                addChildElement(chElement, "MeasureName", cmb.measureName());
            }
            if (it instanceof RowNumberBinding) {
                SOAPElement chElement = addChildElement(element, "RowNumberBinding");
            }
            if (it instanceof ExpressionBinding eb) {
                SOAPElement chElement = addChildElement(element, "ExpressionBinding");
                addChildElement(chElement, "Expression", eb.expression());
            }
        }
    }

    private String convertInstant(Instant calendarStartDate) {
        //TODO
        return null;
    }

    private void setCubeAttributeBindingOrdinalList(SOAPElement element, List<BigInteger> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Ordinal");
            list.forEach(it -> setCubeAttributeBindingOrdinal(chElement, it));
        }
    }

    private void setCubeAttributeBindingOrdinal(SOAPElement element, BigInteger it) {
        if (it != null) {
            addChildElement(element, "Ordinal", String.valueOf(it));
        }
    }

    private void setGroupList(SOAPElement element, List<Group> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Groups");
            list.forEach(it -> setGroup(chElement, it));
        }
    }

    private void setGroup(SOAPElement element, Group it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Group");
            addChildElement(chElement, "Name", it.name());
            SOAPElement membersElement = addChildElement(chElement, "Members");
            it.members().ifPresent(v -> v.forEach(m -> addChildElement(membersElement, "Member", m)));
        }
    }

    private void setErrorConfiguration(SOAPElement element, ErrorConfiguration it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "ErrorConfiguration");
            it.keyErrorLimit().ifPresent(v -> addChildElement(chElement, "KeyErrorLimit", String.valueOf(v)));
            it.keyErrorLogFile().ifPresent(v -> addChildElement(chElement, "KeyErrorLogFile", v));
            it.keyErrorAction().ifPresent(v -> addChildElement(chElement, "KeyErrorAction", v));
            it.keyErrorLimitAction().ifPresent(v -> addChildElement(chElement, "KeyErrorLimitAction", v));
            it.keyNotFound().ifPresent(v -> addChildElement(chElement, "KeyNotFound", v));
            it.keyDuplicate().ifPresent(v -> addChildElement(chElement, "KeyDuplicate", v));
            it.nullKeyConvertedToUnknown().ifPresent(v -> addChildElement(chElement, "NullKeyConvertedToUnknown", v));
            it.nullKeyNotAllowed().ifPresent(v -> addChildElement(chElement, "NullKeyNotAllowed", v));
            it.calculationError().ifPresent(v -> addChildElement(chElement, "CalculationError", v));
        }
    }

    private void setKpiList(SOAPElement element, List<Kpi> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Kpis");
            list.forEach(it -> setKpi(chElement, it));
        }
    }

    private void setKpi(SOAPElement element, Kpi it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Kpi");
            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "ID", it.id());
            addChildElement(chElement, "Description", it.description());
            setTranslationList(chElement, it.translations());
            addChildElement(chElement, "DisplayFolder", it.displayFolder());
            addChildElement(chElement, "AssociatedMeasureGroupID", it.associatedMeasureGroupID());
            addChildElement(chElement, "Value", it.value());
            addChildElement(chElement, "Goal", it.goal());
            addChildElement(chElement, "Status", it.status());
            addChildElement(chElement, "Trend", it.trend());
            addChildElement(chElement, "Weight", it.weight());
            addChildElement(chElement, "TrendGraphic", it.trendGraphic());
            addChildElement(chElement, "StatusGraphic", it.statusGraphic());
            addChildElement(chElement, "CurrentTimeMember", it.currentTimeMember());
            addChildElement(chElement, "ParentKpiID", it.parentKpiID());
            setAnnotationList(chElement, it.annotations());
        }
    }

    private void setProactiveCaching(SOAPElement element, ProactiveCaching proactiveCaching) {
        if (proactiveCaching != null) {
            SOAPElement chElement = addChildElement(element, "ProactiveCaching");
            proactiveCaching.onlineMode().ifPresent(v -> addChildElement(chElement, "OnlineMode", v));
            proactiveCaching.aggregationStorage().ifPresent(v -> addChildElement(chElement, "AggregationStorage", v));
            proactiveCaching.source().ifPresent(v -> setProactiveCachingBinding(chElement, v));
            proactiveCaching.silenceInterval().ifPresent(v -> addChildElement(chElement, "SilenceInterval", convertDuration1(v)));
            proactiveCaching.latency().ifPresent(v -> addChildElement(chElement, "Latency", convertDuration1(v)));
            proactiveCaching.silenceOverrideInterval().ifPresent(v -> addChildElement(chElement, "SilenceOverrideInterval", convertDuration1(v)));
            proactiveCaching.forceRebuildInterval().ifPresent(v -> addChildElement(chElement, "ForceRebuildInterval", convertDuration1(v)));
            proactiveCaching.enabled().ifPresent(v -> addChildElement(chElement, "Enabled", String.valueOf(v)));
        }
    }

    private void setProactiveCachingBinding(SOAPElement element, ProactiveCachingBinding source) {
        if (source != null) {
            SOAPElement chElement = addChildElement(element, "Source");
            if (source instanceof ProactiveCachingIncrementalProcessingBinding pcipb) {
                pcipb.refreshInterval().ifPresent(v -> addChildElement(chElement, "RefreshInterval", convertDuration(v)));
                setIncrementalProcessingNotificationList(chElement, pcipb.incrementalProcessingNotifications());
            }
            if (source instanceof ProactiveCachingQueryBinding pcqb) {
                pcqb.refreshInterval().ifPresent(v -> addChildElement(chElement, "RefreshInterval", convertDuration(v)));
                setQueryNotificationList(chElement, pcqb.queryNotifications());
            }

        }
    }

    private void setQueryNotificationList(SOAPElement element, List<QueryNotification> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "QueryNotifications");
            list.forEach(it -> setQueryNotification(chElement, it));
        }
    }

    private void setQueryNotification(SOAPElement element, QueryNotification it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "QueryNotification");
            it.query().ifPresent(v -> addChildElement(chElement, "Query", v));
        }
    }

    private void setIncrementalProcessingNotificationList(SOAPElement element, List<IncrementalProcessingNotification> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "IncrementalProcessingNotifications");
            list.forEach(it -> setIncrementalProcessingNotification(chElement, it));
        }
    }

    private void setIncrementalProcessingNotification(SOAPElement element, IncrementalProcessingNotification it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "IncrementalProcessingNotification");
            addChildElement(chElement, "TableID", it.tableID());
            addChildElement(chElement, "ProcessingQuery", it.processingQuery());
        }
    }

    private String convertDuration(java.time.Duration v) {
        //TODO
        return null;
    }

    private String convertDuration1(Duration v) {
        //TODO
        return null;
    }

    private void setCubeStorageMode(SOAPElement element, Cube.StorageMode storageMode) {
        if (storageMode != null) {
            SOAPElement chElement = addChildElement(element, "StorageMode");
            addChildElement(chElement, "valuens", storageMode.valuens());
            addChildElement(chElement, "value", storageMode.value() == null ? null : storageMode.value().value());
        }

    }

    private void setDataSourceViewBinding(SOAPElement element, DataSourceViewBinding source) {
        if (source != null) {
            SOAPElement chElement = addChildElement(element, "Source");
            addChildElement(chElement, "DataSourceViewID", source.dataSourceViewID());
        }
    }

    private void setPerspectiveList(SOAPElement element, List<Perspective> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Perspectives");
            list.forEach(it -> setPerspective(chElement, it));
        }
    }

    private void setMdxScriptList(SOAPElement element, List<MdxScript> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "MdxScripts");
            list.forEach(it -> setMdxScript(chElement, it));
        }
    }

    private void setMeasureGroupList(SOAPElement element, List<MeasureGroup> measureGroups) {
        if (measureGroups != null) {
            SOAPElement chElement = addChildElement(element, "MeasureGroups");
            measureGroups.forEach(it -> setMeasureGroup(chElement, it));
        }
    }

    private void setCubePermissionList(SOAPElement element, List<CubePermission> cubePermissions) {
        if (cubePermissions != null) {
            SOAPElement chElement = addChildElement(element, "CubePermissions");
            cubePermissions.forEach(it -> setCubePermission(chElement, it));
        }

    }

    private void setCubePermission(SOAPElement element, CubePermission cubePermission) {
        SOAPElement dimensionElement = addChildElement(element, "CubePermission");
        cubePermission.readSourceData().ifPresent(v -> addChildElement(dimensionElement, "ReadSourceData", v));
        cubePermission.dimensionPermissions().ifPresent(v -> setCubeDimensionPermissionList(dimensionElement, v));
        cubePermission.cellPermissions().ifPresent(v -> setCellPermissionList(dimensionElement, v));
    }

    private void setCellPermissionList(SOAPElement element, List<CellPermission> v) {
        if (v != null) {
            SOAPElement chElement = addChildElement(element, "CellPermissions");
            v.forEach(it -> setCellPermission(chElement, it));
        }
    }

    private void setCellPermission(SOAPElement element, CellPermission it) {
        SOAPElement chElement = addChildElement(element, "CellPermission");
        it.access().ifPresent(v -> addChildElement(chElement, "Access", v.getValue()));
        it.description().ifPresent(v -> addChildElement(chElement, "Description", v));
        it.expression().ifPresent(v -> addChildElement(chElement, "Expression", v));
        it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
    }

    private void setCubeDimensionPermissionList(SOAPElement element, List<CubeDimensionPermission> v) {
        if (v != null) {
            SOAPElement chElement = addChildElement(element, "DimensionPermissions");
            v.forEach(it -> setCubeDimensionPermission(chElement, it));
        }
    }

    private void setCubeDimensionPermission(SOAPElement element, CubeDimensionPermission it) {
        SOAPElement chElement = addChildElement(element, "DimensionPermission");
        addChildElement(chElement, "CubeDimensionID", it.cubeDimensionID());
        it.description().ifPresent(v -> addChildElement(chElement, "Description", v));
        it.read().ifPresent(v -> addChildElement(chElement, "Read", v.getValue()));
        it.write().ifPresent(v -> addChildElement(chElement, "Write", v.getValue()));
        it.attributePermissions().ifPresent(v -> setAttributePermissionList(chElement, v));
        it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
    }

    private void setAttributePermissionList(SOAPElement element, List<AttributePermission> v) {
        if (v != null) {
            SOAPElement chElement = addChildElement(element, "AttributePermissions");
            v.forEach(it -> setAttributePermission(chElement, it));
        }
    }

    private void setAttributePermission(SOAPElement element, AttributePermission it) {
        SOAPElement chElement = addChildElement(element, "AttributePermission");
        addChildElement(chElement, "AttributeID", it.attributeID());
        it.description().ifPresent(v -> addChildElement(chElement, "Description", v));
        it.defaultMember().ifPresent(v -> addChildElement(chElement, "DefaultMember", v));
        it.visualTotals().ifPresent(v -> addChildElement(chElement, "VisualTotals", v));
        it.allowedSet().ifPresent(v -> addChildElement(chElement, "AllowedSet", v));
        it.deniedSet().ifPresent(v -> addChildElement(chElement, "DeniedSet", v));
        it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
    }

    private void setCubeDimensionsList(SOAPElement cubeElement, List<CubeDimension> dimensions) {
        if (dimensions != null) {
            SOAPElement dimensionsElement = addChildElement(cubeElement, "Dimensions");
            dimensions.forEach(it -> setCubeDimension(dimensionsElement, it));
        }
    }

    private void setCubeDimension(SOAPElement dimensionsElement, CubeDimension cubeDimension) {
        SOAPElement dimensionElement = addChildElement(dimensionsElement, "Dimension");
        addChildElement(dimensionElement, "ID", cubeDimension.id());
        addChildElement(dimensionElement, "Name", cubeDimension.name());
        addChildElement(dimensionElement, "Description", cubeDimension.description());
        setTranslationList(dimensionElement, cubeDimension.translations());
        addChildElement(dimensionElement, "DimensionID", cubeDimension.dimensionID());
        addChildElement(dimensionElement, "Visible", String.valueOf(cubeDimension.visible()));
        addChildElement(dimensionElement, "AllMemberAggregationUsage", cubeDimension.allMemberAggregationUsage());
        addChildElement(dimensionElement, "HierarchyUniqueNameStyle", cubeDimension.hierarchyUniqueNameStyle());
        addChildElement(dimensionElement, "MemberUniqueNameStyle", cubeDimension.memberUniqueNameStyle());
        setCubeAttributeList(dimensionElement, cubeDimension.attributes());
        setCubeHierarchyList(dimensionElement, cubeDimension.hierarchies());
        setAnnotationList(dimensionElement, cubeDimension.annotations());
    }

    private void setCubeHierarchyList(SOAPElement element, List<CubeHierarchy> hierarchies) {
        if (hierarchies != null) {
            SOAPElement hierarchiesElement = addChildElement(element, "Hierarchies");
            hierarchies.forEach(v -> setCubeHierarchy(hierarchiesElement, v));
        }
    }

    private void setCubeHierarchy(SOAPElement element, CubeHierarchy v) {
        SOAPElement hierarchyElement = addChildElement(element, "Hierarchy");
        addChildElement(hierarchyElement, "HierarchyID",  v.hierarchyID());
        addChildElement(hierarchyElement, "OptimizedState",  v.optimizedState());
        addChildElement(hierarchyElement, "Visible",  String.valueOf(v.visible()));
        addChildElement(hierarchyElement, "Enabled",  String.valueOf(v.enabled()));
        setAnnotationList(hierarchyElement, v.annotations());
    }

    private void setCubeAttributeList(SOAPElement element, List<CubeAttribute> attributes) {
        if (attributes != null) {
            SOAPElement attributesElement = addChildElement(element, "Attributes");
            attributes.forEach(v -> setCubeAttribute(attributesElement, v));
        }
    }

    private void setCubeAttribute(SOAPElement element, CubeAttribute v) {
        SOAPElement attributeElement = addChildElement(element, "Attribute");
        addChildElement(attributeElement, "AttributeID", v.attributeID());
        addChildElement(attributeElement, "AggregationUsage", v.aggregationUsage());
        addChildElement(attributeElement, "AttributeHierarchyOptimizedState", v.attributeHierarchyOptimizedState());
        addChildElement(attributeElement, "AttributeHierarchyEnabled", String.valueOf(v.attributeHierarchyEnabled()));
        addChildElement(attributeElement, "AttributeHierarchyVisible", String.valueOf(v.attributeHierarchyVisible()));
        setAnnotationList(attributeElement, v.annotations());
    }

    private void setTranslationList(SOAPElement cubeElement, List<Translation> translations) {
        if (translations != null) {
            SOAPElement translationsElement = addChildElement(cubeElement, "Translations");
            translations.forEach(it -> setTranslation(translationsElement, it));
        }
    }

    private void setTranslation(SOAPElement translationsElement, Translation translation) {
        SOAPElement translationElement = addChildElement(translationsElement, "Translation");
        addChildElement(translationElement, "Language", String.valueOf(translation.language()));
        addChildElement(translationElement, "Caption", String.valueOf(translation.caption()));
        addChildElement(translationElement, "Description", String.valueOf(translation.description()));
        addChildElement(translationElement, "DisplayFolder", String.valueOf(translation.displayFolder()));
        setAnnotationList(translationElement, translation.annotations());
    }

    private void setAssembly(SOAPElement objectDefinitionElement, Assembly assembly) {
        if (assembly != null) {
            SOAPElement assemblyElement = addChildElement(objectDefinitionElement, "Assembly");
            addChildElement(assemblyElement, "Name", assembly.name());
            addChildElement(assemblyElement, "ID", assembly.id());
            addChildElement(assemblyElement, "CreatedTimestamp", assembly.createdTimestamp().toString());
            addChildElement(assemblyElement, "LastSchemaUpdate", assembly.lastSchemaUpdate().toString());
            addChildElement(assemblyElement, "Description", assembly.description());
            setAnnotationList(assemblyElement, assembly.annotations());
        }
    }

    private void setAnnotationList(SOAPElement element, List<Annotation> annotations) {
        if (annotations != null) {
            SOAPElement annotationsElement = addChildElement(element, "Annotations");
            annotations.forEach(it -> setAnnotation(annotationsElement, it));
        }
    }

    private void setAnnotation(SOAPElement annotationsElement, Annotation annotation) {
        if (annotation != null) {
            SOAPElement annotationElement = addChildElement(annotationsElement, "Annotation");
            addChildElement(annotationElement, "Name", annotation.name());
            annotation.visibility().ifPresent(v -> addChildElement(annotationElement, "Visibility", v));
            addChildElement(annotationElement, "Value", annotation.value().toString());
        }
    }

    private void setAggregationDesign(
        SOAPElement objectDefinitionElement,
        AggregationDesign aggregationDesign)  {
        if (aggregationDesign != null) {
            SOAPElement aggregationDesignEl = addChildElement(objectDefinitionElement, "AggregationDesign");
            aggregationDesign.estimatedRows().ifPresent(v -> addChildElement(aggregationDesignEl, "EstimatedRows",
                String.valueOf(v)));
            aggregationDesign.dimensions().ifPresent(v -> setAggregationDesignDimensionList(aggregationDesignEl, v));
            aggregationDesign.aggregations().ifPresent(v -> setAggregationList(aggregationDesignEl, v));
            aggregationDesign.estimatedPerformanceGain().ifPresent(v -> addChildElement(aggregationDesignEl,
                "EstimatedPerformanceGain", String.valueOf(v)));
        }
    }

    private void setAggregationDesignDimensionList(SOAPElement element, List<AggregationDesignDimension> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Dimensions");
            list.forEach(it -> setAggregationDesignDimension(chElement, it));
        }

    }

    private void setAggregationDesignDimension(SOAPElement element, AggregationDesignDimension it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Dimension");
            addChildElement(chElement, "CubeDimensionID", it.cubeDimensionID());
            it.attributes().ifPresent(v -> setAggregationDesignAttributeList(chElement, v));
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
        }
    }

    private void setAggregationDesignAttributeList(SOAPElement element, List<AggregationDesignAttribute> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Attributes");
            list.forEach(it -> setAggregationDesignAttribute(chElement, it));
        }

    }

    private void setAggregationDesignAttribute(SOAPElement element, AggregationDesignAttribute it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Attribute");
            addChildElement(chElement, "AttributeID", it.attributeID());
            it.estimatedCount().ifPresent(v -> addChildElement(chElement, "EstimatedCount", String.valueOf(v)));
        }
    }

    private void setAggregationList(SOAPElement element, List<Aggregation> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Aggregations");
            list.forEach(it -> setAggregation(chElement, it));
        }
    }

    private void setAggregation(SOAPElement element, Aggregation it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Aggregation");
            it.id().ifPresent(v -> addChildElement(chElement, "ID", v));
            addChildElement(chElement, "Name", it.name());
            it.description().ifPresent(v -> addChildElement(chElement, "Description", v));
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
            it.dimensions().ifPresent(v -> setAggregationDimensionList(chElement, v));
        }

    }

    private void setAggregationDimensionList(SOAPElement element, List<AggregationDimension> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Dimensions");
            list.forEach(it -> setAggregationDimension(chElement, it));
        }
    }

    private void setAggregationDimension(SOAPElement element, AggregationDimension it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Dimension");
            addChildElement(chElement, "CubeDimensionID", it.cubeDimensionID());
            it.attributes().ifPresent(v -> setAggregationAttributeList(chElement, v));
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
        }
    }

    private void setAggregationAttributeList(SOAPElement element, List<AggregationAttribute> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Attributes");
            list.forEach(it -> setAggregationAttribute(chElement, it));
        }
    }

    private void setAggregationAttribute(SOAPElement element, AggregationAttribute it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Attribute");
            addChildElement(chElement, "AttributeID", it.attributeID());
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
        }
    }

    private void setObject(SOAPElement element, ObjectReference reference) {
        if (reference != null) {
            SOAPElement chElement = addChildElement(element, "Object");
            addChildElement(chElement, "ServerID", reference.serverID());
            addChildElement(chElement, "DatabaseID", reference.databaseID());
            addChildElement(chElement, "RoleID", reference.roleID());
            addChildElement(chElement, "TraceID", reference.traceID());
            addChildElement(chElement, "AssemblyID", reference.assemblyID());
            addChildElement(chElement, "DimensionID", reference.dimensionID());
            addChildElement(chElement, "DimensionPermissionID", reference.dimensionPermissionID());
            addChildElement(chElement, "DataSourceID", reference.dataSourceID());
            addChildElement(chElement, "DataSourcePermissionID", reference.dataSourcePermissionID());
            addChildElement(chElement, "DatabasePermissionID", reference.databasePermissionID());
            addChildElement(chElement, "DataSourceViewID", reference.dataSourceViewID());
            addChildElement(chElement, "CubeID", reference.cubeID());
            addChildElement(chElement, "MiningStructureID", reference.miningStructureID());
            addChildElement(chElement, "MeasureGroupID", reference.measureGroupID());
            addChildElement(chElement, "PerspectiveID", reference.perspectiveID());
            addChildElement(chElement, "CubePermissionID", reference.cubePermissionID());
            addChildElement(chElement, "MdxScriptID", reference.mdxScriptID());
            addChildElement(chElement, "PartitionID", reference.partitionID());
            addChildElement(chElement, "AggregationDesignID", reference.partitionID());
            addChildElement(chElement, "MiningModelID", reference.miningModelID());
            addChildElement(chElement, "MiningModelPermissionID", reference.miningModelPermissionID());
            addChildElement(chElement, "MiningStructurePermissionID", reference.miningStructureID());
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
