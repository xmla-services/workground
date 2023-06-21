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
import org.eclipse.daanse.xmla.api.xmla.Action;
import org.eclipse.daanse.xmla.api.xmla.Aggregation;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesign;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesignDimension;
import org.eclipse.daanse.xmla.api.xmla.Alter;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Assembly;
import org.eclipse.daanse.xmla.api.xmla.AttributePermission;
import org.eclipse.daanse.xmla.api.xmla.CalculationProperty;
import org.eclipse.daanse.xmla.api.xmla.CellPermission;
import org.eclipse.daanse.xmla.api.xmla.Command;
import org.eclipse.daanse.xmla.api.xmla.Cube;
import org.eclipse.daanse.xmla.api.xmla.CubeAttribute;
import org.eclipse.daanse.xmla.api.xmla.CubeDimension;
import org.eclipse.daanse.xmla.api.xmla.CubeDimensionPermission;
import org.eclipse.daanse.xmla.api.xmla.CubeHierarchy;
import org.eclipse.daanse.xmla.api.xmla.CubePermission;
import org.eclipse.daanse.xmla.api.xmla.DataSource;
import org.eclipse.daanse.xmla.api.xmla.DataSourceView;
import org.eclipse.daanse.xmla.api.xmla.DataSourceViewBinding;
import org.eclipse.daanse.xmla.api.xmla.Database;
import org.eclipse.daanse.xmla.api.xmla.Dimension;
import org.eclipse.daanse.xmla.api.xmla.DrillThroughAction;
import org.eclipse.daanse.xmla.api.xmla.ErrorConfiguration;
import org.eclipse.daanse.xmla.api.xmla.IncrementalProcessingNotification;
import org.eclipse.daanse.xmla.api.xmla.Kpi;
import org.eclipse.daanse.xmla.api.xmla.MajorObject;
import org.eclipse.daanse.xmla.api.xmla.MdxScript;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroup;
import org.eclipse.daanse.xmla.api.xmla.MiningModel;
import org.eclipse.daanse.xmla.api.xmla.MiningStructure;
import org.eclipse.daanse.xmla.api.xmla.ObjectReference;
import org.eclipse.daanse.xmla.api.xmla.Partition;
import org.eclipse.daanse.xmla.api.xmla.Permission;
import org.eclipse.daanse.xmla.api.xmla.Perspective;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveAction;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveCalculation;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveDimension;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveKpi;
import org.eclipse.daanse.xmla.api.xmla.PerspectiveMeasureGroup;
import org.eclipse.daanse.xmla.api.xmla.ProactiveCaching;
import org.eclipse.daanse.xmla.api.xmla.ProactiveCachingBinding;
import org.eclipse.daanse.xmla.api.xmla.ProactiveCachingIncrementalProcessingBinding;
import org.eclipse.daanse.xmla.api.xmla.ProactiveCachingQueryBinding;
import org.eclipse.daanse.xmla.api.xmla.QueryNotification;
import org.eclipse.daanse.xmla.api.xmla.ReportAction;
import org.eclipse.daanse.xmla.api.xmla.Role;
import org.eclipse.daanse.xmla.api.xmla.Server;
import org.eclipse.daanse.xmla.api.xmla.StandardAction;
import org.eclipse.daanse.xmla.api.xmla.Statement;
import org.eclipse.daanse.xmla.api.xmla.Trace;
import org.eclipse.daanse.xmla.api.xmla.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.Duration;
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

    private void setObjectDefinition(
        SOAPElement objectDefinitionElement,
        MajorObject objectDefinition
    ) throws SOAPException {
        if (objectDefinition != null) {
            setAggregationDesign(objectDefinitionElement, objectDefinition.aggregationDesign());
            setAssembly(objectDefinitionElement, objectDefinition.assembly());
            setCube(objectDefinitionElement, objectDefinition.cube());
            setDatabase(objectDefinitionElement, objectDefinition.database());
            setDataSource(objectDefinitionElement, objectDefinition.dataSource());
            setDataSourceView(objectDefinitionElement, objectDefinition.dataSourceView());
            setDimension(objectDefinitionElement, objectDefinition.dimension());
            setMdxScript(objectDefinitionElement, objectDefinition.mdxScript());
            setMeasureGroup(objectDefinitionElement, objectDefinition.measureGroup());
            setMiningModel(objectDefinitionElement, objectDefinition.miningModel());
            setMiningStructure(objectDefinitionElement, objectDefinition.miningStructure());
            setPartition(objectDefinitionElement, objectDefinition.partition());
            setPermission(objectDefinitionElement, objectDefinition.permission());
            setPerspective(objectDefinitionElement, objectDefinition.perspective());
            setRole(objectDefinitionElement, objectDefinition.role());
            setServer(objectDefinitionElement, objectDefinition.server());
            setTrace(objectDefinitionElement, objectDefinition.trace());
        }
    }

    private void setTrace(SOAPElement objectDefinitionElement, Trace it) {
        //TODO
    }

    private void setServer(SOAPElement objectDefinitionElement, Server it) {
        //TODO
    }

    private void setRole(SOAPElement objectDefinitionElement, Role it) {
        //TODO
    }

    private void setPerspective(SOAPElement element, Perspective it) {
        SOAPElement chElement = addChildElement(element, "Perspective");
        setTranslationList(chElement, it.translations());
        addChildElement(chElement, "DefaultMeasure", it.defaultMeasure());
        setPerspectiveDimensionList(chElement, it.dimensions());
        setPerspectiveMeasureGroupList(chElement, it.measureGroups());
        setPerspectiveCalculationList(chElement, it.calculations());
        setPerspectiveKpiList(chElement, it.kpis());
        setPerspectiveActionList(chElement, it.actions());
    }

    private void setPerspectiveActionList(SOAPElement chElement, List<PerspectiveAction> actions) {
        //        @XmlElement(name = "Action")
        //        @XmlElementWrapper(name = "Actions")
        //        protected List<PerspectiveAction> actions;
    }

    private void setPerspectiveKpiList(SOAPElement chElement, List<PerspectiveKpi> list) {
        //        @XmlElement(name = "Kpi")
        //        @XmlElementWrapper(name = "Kpis")
        //        protected List<PerspectiveKpi> kpis;
    }

    private void setPerspectiveCalculationList(SOAPElement chElement, List<PerspectiveCalculation> list) {
        //        @XmlElement(name = "Calculation")
        //        @XmlElementWrapper(name = "Calculations")
        //        protected List<PerspectiveCalculation> calculations;
    }

    private void setPerspectiveMeasureGroupList(SOAPElement chElement, List<PerspectiveMeasureGroup> list) {
        //        @XmlElement(name = "MeasureGroup")
        //        @XmlElementWrapper(name = "MeasureGroups")
        //        protected List<PerspectiveMeasureGroup> measureGroups;
    }

    private void setPerspectiveDimensionList(SOAPElement chElement, List<PerspectiveDimension> dimensions) {
        //        @XmlElement(name = "Dimension")
        //        @XmlElementWrapper(name = "Dimensions")
        //        protected List<PerspectiveDimension> dimensions;
    }

    private void setPermission(SOAPElement objectDefinitionElement, Permission it) {
        //TODO
    }

    private void setPartition(SOAPElement objectDefinitionElement, Partition it) {
        //TODO
    }

    private void setMiningStructure(SOAPElement objectDefinitionElement, MiningStructure it) {
        //TODO
    }

    private void setMiningModel(SOAPElement objectDefinitionElement, MiningModel it) {
        //TODO
    }

    private void setMeasureGroup(SOAPElement objectDefinitionElement, MeasureGroup it) {
        //TODO
    }

    private void setMdxScript(SOAPElement element, MdxScript it) {
        SOAPElement chElement = addChildElement(element, "MdxScript");
        setCommandList(chElement, it.commands());
        addChildElement(chElement, "DefaultScript", String.valueOf(it.defaultScript()));
        setCalculationPropertyList(chElement, it.calculationProperties());
    }

    private void setCalculationPropertyList(SOAPElement chElement, List<CalculationProperty> calculationProperties) {
        //        @XmlElement(name = "CalculationProperty")
        //        @XmlElementWrapper(name = "CalculationProperties")
        //        protected List<CalculationProperty> calculationProperties;

    }

    private void setCommandList(SOAPElement chElement, List<Command> commands) {
        //        @XmlElement(name = "Command")
        //        @XmlElementWrapper(name = "Commands")
        //        protected List<Command> commands;
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
            it.annotations().ifPresent(v -> setAnnotations(chElement, v));

            if (it instanceof DrillThroughAction dta) {
                // TODO
            }
            if (it instanceof ReportAction ra) {
                // TODO
            }
            }
            if (it instanceof StandardAction sa) {
                // TODO
            }
        }

    private void setErrorConfiguration(SOAPElement cubeelement, ErrorConfiguration errorConfiguration) {
        //            @XmlElement(name = "ErrorConfiguration")
        //            protected ErrorConfiguration errorConfiguration;
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
            setAnnotations(chElement, it.annotations());
        }
    }

    private void setProactiveCaching(SOAPElement element, ProactiveCaching proactiveCaching) {
        if (proactiveCaching != null) {
            SOAPElement chElement = addChildElement(element, "ProactiveCaching");
            proactiveCaching.onlineMode().ifPresent(v -> addChildElement(chElement, "OnlineMode", v));
            proactiveCaching.aggregationStorage().ifPresent(v -> addChildElement(chElement, "AggregationStorage", v));
            proactiveCaching.source().ifPresent(v -> setProactiveCachingBinding(chElement, v));
            proactiveCaching.silenceInterval().ifPresent(v -> addChildElement(chElement, "SilenceInterval", convertDuration(v)));
            proactiveCaching.latency().ifPresent(v -> addChildElement(chElement, "Latency", convertDuration(v)));
            proactiveCaching.silenceOverrideInterval().ifPresent(v -> addChildElement(chElement, "SilenceOverrideInterval", convertDuration(v)));
            proactiveCaching.forceRebuildInterval().ifPresent(v -> addChildElement(chElement, "ForceRebuildInterval", convertDuration(v)));
            proactiveCaching.enabled().ifPresent(v -> addChildElement(chElement, "Enabled", String.valueOf(v)));
        }
    }

    private String convertDuration(Duration v) {
        //TODO
        return null;
    }

    private void setProactiveCachingBinding(SOAPElement element, ProactiveCachingBinding source) {
        if (source != null) {
            SOAPElement chElement = addChildElement(element, "Source");
            if (source instanceof ProactiveCachingIncrementalProcessingBinding pcipb) {
                pcipb.refreshInterval().ifPresent(v -> addChildElement(chElement, "RefreshInterval", convertDuration1(v)));
                setIncrementalProcessingNotificationList(chElement, pcipb.incrementalProcessingNotifications());
            }
            if (source instanceof ProactiveCachingQueryBinding pcqb) {
                pcqb.refreshInterval().ifPresent(v -> addChildElement(chElement, "RefreshInterval", convertDuration1(v)));
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

    private String convertDuration1(java.time.Duration v) {
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
        it.annotations().ifPresent(v -> setAnnotations(chElement, v));
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
        it.annotations().ifPresent(v -> setAnnotations(chElement, v));
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
        it.annotations().ifPresent(v -> setAnnotations(chElement, v));
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
        setAnnotations(dimensionElement, cubeDimension.annotations());
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
        setAnnotations(hierarchyElement, v.annotations());
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
        setAnnotations(attributeElement, v.annotations());
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
        setAnnotations(translationElement, translation.annotations());
    }

    private void setAssembly(SOAPElement objectDefinitionElement, Assembly assembly) {
        if (assembly != null) {
            SOAPElement assemblyElement = addChildElement(objectDefinitionElement, "Assembly");
            addChildElement(assemblyElement, "Name", assembly.name());
            addChildElement(assemblyElement, "ID", assembly.id());
            addChildElement(assemblyElement, "CreatedTimestamp", assembly.createdTimestamp().toString());
            addChildElement(assemblyElement, "LastSchemaUpdate", assembly.lastSchemaUpdate().toString());
            addChildElement(assemblyElement, "Description", assembly.description());
            setAnnotations(assemblyElement, assembly.annotations());
        }
    }

    private void setAnnotations(SOAPElement element, List<Annotation> annotations) {
        if (annotations != null) {
            SOAPElement annotationsElement = addChildElement(element, "Annotations");
            annotations.forEach(it -> setAnnotation(annotationsElement, it));
        }
    }

    private void setAnnotation(SOAPElement annotationsElement, Annotation annotation) {
        SOAPElement annotationElement = addChildElement(annotationsElement, "Annotation");
        addChildElement(annotationElement, "Name", annotation.name());
        annotation.visibility().ifPresent(v -> addChildElement(annotationElement, "Visibility", v));
        addChildElement(annotationElement, "Value", annotation.value().toString());
    }

    private void setAggregationDesign(
        SOAPElement objectDefinitionElement,
        AggregationDesign aggregationDesign
    ) throws SOAPException {
        if (aggregationDesign != null) {
            SOAPElement aggregationDesignEl = objectDefinitionElement.addChildElement("AggregationDesign");
            aggregationDesign.estimatedRows().ifPresent(v -> addChildElement(aggregationDesignEl, "EstimatedRows",
                String.valueOf(v)));
            aggregationDesign.dimensions().ifPresent(v -> setDimensions(aggregationDesignEl, v));
            aggregationDesign.aggregations().ifPresent(v -> setAggregations(aggregationDesignEl, v));
            aggregationDesign.estimatedPerformanceGain().ifPresent(v -> addChildElement(aggregationDesignEl,
                "EstimatedPerformanceGain", String.valueOf(v)));
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
