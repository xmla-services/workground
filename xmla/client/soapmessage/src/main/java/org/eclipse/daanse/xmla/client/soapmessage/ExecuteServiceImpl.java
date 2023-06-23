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
import org.eclipse.daanse.xmla.api.engine.ImpersonationInfo;
import org.eclipse.daanse.xmla.api.engine200_200.ExpressionBinding;
import org.eclipse.daanse.xmla.api.engine200_200.RowNumberBinding;
import org.eclipse.daanse.xmla.api.engine300.CalculationPropertiesVisualizationProperties;
import org.eclipse.daanse.xmla.api.engine300.HierarchyVisualizationProperties;
import org.eclipse.daanse.xmla.api.engine300.RelationshipEndVisualizationProperties;
import org.eclipse.daanse.xmla.api.engine300_300.Relationship;
import org.eclipse.daanse.xmla.api.engine300_300.RelationshipEnd;
import org.eclipse.daanse.xmla.api.engine300_300.RelationshipEndTranslation;
import org.eclipse.daanse.xmla.api.engine300_300.Relationships;
import org.eclipse.daanse.xmla.api.engine300_300.XEvent;
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
import org.eclipse.daanse.xmla.api.xmla.AndOrType;
import org.eclipse.daanse.xmla.api.xmla.AndOrTypeEnum;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Assembly;
import org.eclipse.daanse.xmla.api.xmla.Attach;
import org.eclipse.daanse.xmla.api.xmla.AttributeBinding;
import org.eclipse.daanse.xmla.api.xmla.AttributePermission;
import org.eclipse.daanse.xmla.api.xmla.AttributeRelationship;
import org.eclipse.daanse.xmla.api.xmla.AttributeTranslation;
import org.eclipse.daanse.xmla.api.xmla.Backup;
import org.eclipse.daanse.xmla.api.xmla.Batch;
import org.eclipse.daanse.xmla.api.xmla.BeginTransaction;
import org.eclipse.daanse.xmla.api.xmla.Binding;
import org.eclipse.daanse.xmla.api.xmla.BoolBinop;
import org.eclipse.daanse.xmla.api.xmla.CalculatedMeasureBinding;
import org.eclipse.daanse.xmla.api.xmla.CalculationProperty;
import org.eclipse.daanse.xmla.api.xmla.Cancel;
import org.eclipse.daanse.xmla.api.xmla.CellPermission;
import org.eclipse.daanse.xmla.api.xmla.ClearCache;
import org.eclipse.daanse.xmla.api.xmla.CloneDatabase;
import org.eclipse.daanse.xmla.api.xmla.ColumnBinding;
import org.eclipse.daanse.xmla.api.xmla.Command;
import org.eclipse.daanse.xmla.api.xmla.CommitTransaction;
import org.eclipse.daanse.xmla.api.xmla.Create;
import org.eclipse.daanse.xmla.api.xmla.Cube;
import org.eclipse.daanse.xmla.api.xmla.CubeAttribute;
import org.eclipse.daanse.xmla.api.xmla.CubeAttributeBinding;
import org.eclipse.daanse.xmla.api.xmla.CubeDimension;
import org.eclipse.daanse.xmla.api.xmla.CubeDimensionBinding;
import org.eclipse.daanse.xmla.api.xmla.CubeDimensionPermission;
import org.eclipse.daanse.xmla.api.xmla.CubeHierarchy;
import org.eclipse.daanse.xmla.api.xmla.CubePermission;
import org.eclipse.daanse.xmla.api.xmla.DBCC;
import org.eclipse.daanse.xmla.api.xmla.DSVTableBinding;
import org.eclipse.daanse.xmla.api.xmla.DataItem;
import org.eclipse.daanse.xmla.api.xmla.DataMiningMeasureGroupDimension;
import org.eclipse.daanse.xmla.api.xmla.DataSource;
import org.eclipse.daanse.xmla.api.xmla.DataSourcePermission;
import org.eclipse.daanse.xmla.api.xmla.DataSourceView;
import org.eclipse.daanse.xmla.api.xmla.DataSourceViewBinding;
import org.eclipse.daanse.xmla.api.xmla.Database;
import org.eclipse.daanse.xmla.api.xmla.DegenerateMeasureGroupDimension;
import org.eclipse.daanse.xmla.api.xmla.Delete;
import org.eclipse.daanse.xmla.api.xmla.DesignAggregations;
import org.eclipse.daanse.xmla.api.xmla.Detach;
import org.eclipse.daanse.xmla.api.xmla.Dimension;
import org.eclipse.daanse.xmla.api.xmla.DimensionAttribute;
import org.eclipse.daanse.xmla.api.xmla.DimensionBinding;
import org.eclipse.daanse.xmla.api.xmla.DimensionPermission;
import org.eclipse.daanse.xmla.api.xmla.DrillThroughAction;
import org.eclipse.daanse.xmla.api.xmla.Drop;
import org.eclipse.daanse.xmla.api.xmla.ErrorConfiguration;
import org.eclipse.daanse.xmla.api.xmla.Event;
import org.eclipse.daanse.xmla.api.xmla.EventColumnID;
import org.eclipse.daanse.xmla.api.xmla.EventSession;
import org.eclipse.daanse.xmla.api.xmla.EventType;
import org.eclipse.daanse.xmla.api.xmla.FoldingParameters;
import org.eclipse.daanse.xmla.api.xmla.Group;
import org.eclipse.daanse.xmla.api.xmla.Hierarchy;
import org.eclipse.daanse.xmla.api.xmla.ImageLoad;
import org.eclipse.daanse.xmla.api.xmla.ImageSave;
import org.eclipse.daanse.xmla.api.xmla.IncrementalProcessingNotification;
import org.eclipse.daanse.xmla.api.xmla.InheritedBinding;
import org.eclipse.daanse.xmla.api.xmla.Insert;
import org.eclipse.daanse.xmla.api.xmla.Kpi;
import org.eclipse.daanse.xmla.api.xmla.Level;
import org.eclipse.daanse.xmla.api.xmla.Lock;
import org.eclipse.daanse.xmla.api.xmla.MajorObject;
import org.eclipse.daanse.xmla.api.xmla.ManyToManyMeasureGroupDimension;
import org.eclipse.daanse.xmla.api.xmla.MdxScript;
import org.eclipse.daanse.xmla.api.xmla.Measure;
import org.eclipse.daanse.xmla.api.xmla.MeasureBinding;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroup;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroupAttribute;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroupBinding;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroupDimension;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroupDimensionBinding;
import org.eclipse.daanse.xmla.api.xmla.Member;
import org.eclipse.daanse.xmla.api.xmla.MergePartitions;
import org.eclipse.daanse.xmla.api.xmla.MiningModel;
import org.eclipse.daanse.xmla.api.xmla.MiningModelColumn;
import org.eclipse.daanse.xmla.api.xmla.MiningModelPermission;
import org.eclipse.daanse.xmla.api.xmla.MiningModelingFlag;
import org.eclipse.daanse.xmla.api.xmla.MiningStructure;
import org.eclipse.daanse.xmla.api.xmla.MiningStructureColumn;
import org.eclipse.daanse.xmla.api.xmla.MiningStructurePermission;
import org.eclipse.daanse.xmla.api.xmla.NotType;
import org.eclipse.daanse.xmla.api.xmla.NotifyTableChange;
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
import org.eclipse.daanse.xmla.api.xmla.ReferenceMeasureGroupDimension;
import org.eclipse.daanse.xmla.api.xmla.RegularMeasureGroupDimension;
import org.eclipse.daanse.xmla.api.xmla.ReportAction;
import org.eclipse.daanse.xmla.api.xmla.ReportFormatParameter;
import org.eclipse.daanse.xmla.api.xmla.ReportParameter;
import org.eclipse.daanse.xmla.api.xmla.Restore;
import org.eclipse.daanse.xmla.api.xmla.Role;
import org.eclipse.daanse.xmla.api.xmla.RollbackTransaction;
import org.eclipse.daanse.xmla.api.xmla.RowBinding;
import org.eclipse.daanse.xmla.api.xmla.ScalarMiningStructureColumn;
import org.eclipse.daanse.xmla.api.xmla.Server;
import org.eclipse.daanse.xmla.api.xmla.ServerProperty;
import org.eclipse.daanse.xmla.api.xmla.SetAuthContext;
import org.eclipse.daanse.xmla.api.xmla.StandardAction;
import org.eclipse.daanse.xmla.api.xmla.Statement;
import org.eclipse.daanse.xmla.api.xmla.Subscribe;
import org.eclipse.daanse.xmla.api.xmla.Synchronize;
import org.eclipse.daanse.xmla.api.xmla.TableBinding;
import org.eclipse.daanse.xmla.api.xmla.TableMiningStructureColumn;
import org.eclipse.daanse.xmla.api.xmla.TabularBinding;
import org.eclipse.daanse.xmla.api.xmla.TimeAttributeBinding;
import org.eclipse.daanse.xmla.api.xmla.TimeBinding;
import org.eclipse.daanse.xmla.api.xmla.Trace;
import org.eclipse.daanse.xmla.api.xmla.TraceFilter;
import org.eclipse.daanse.xmla.api.xmla.Translation;
import org.eclipse.daanse.xmla.api.xmla.Unlock;
import org.eclipse.daanse.xmla.api.xmla.Unsubscribe;
import org.eclipse.daanse.xmla.api.xmla.Update;
import org.eclipse.daanse.xmla.api.xmla.UpdateCells;
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
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Trace");
            addChildElement(chElement, "LogFileName", it.logFileName());
            addChildElement(chElement, "LogFileAppend", String.valueOf(it.logFileAppend()));
            addChildElement(chElement, "LogFileSize", String.valueOf(it.logFileSize()));
            addChildElement(chElement, "Audit", String.valueOf(it.audit()));
            addChildElement(chElement, "LogFileRollover", String.valueOf(it.logFileRollover()));
            addChildElement(chElement, "AutoRestart", String.valueOf(it.autoRestart()));
            addChildElement(chElement, "StopTime", convertInstant(it.stopTime()));
            setTraceFilter(chElement, it.filter());
            setEventType(chElement, it.eventType());
        }
    }

    private void setEventType(SOAPElement element, EventType it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "EventType");
            setEventList(chElement, it.events());
            setXEvent(chElement, it.xEvent());
        }
    }

    private void setXEvent(SOAPElement element, XEvent it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "XEvent");
            setEventSession(chElement, it.eventSession());
        }
    }

    private void setEventSession(SOAPElement element, EventSession it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "event_session");
            addChildElement(chElement, "name", it.name());
            addChildElement(chElement, "maxMemory", String.valueOf(it.maxMemory()));
            addChildElement(chElement, "eventRetentionMode", it.eventRetentionMode() == null ? null :
                it.eventRetentionMode().value());
            addChildElement(chElement, "dispatchLatency", String.valueOf(it.dispatchLatency()));
            addChildElement(chElement, "maxEventSize", String.valueOf(it.maxEventSize()));
            addChildElement(chElement, "memoryPartitionMode", it.eventRetentionMode() == null ? null :
                it.eventRetentionMode().value());
            addChildElement(chElement, "trackCausality", String.valueOf(it.trackCausality()));
        }
    }

    private void setEventList(SOAPElement element, List<Event> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Events");
            list.forEach(it -> setEvent(chElement, it));
        }
    }

    private void setEvent(SOAPElement element, Event it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Event");
            addChildElement(chElement, "EventID", it.eventID());
            setEventColumnID(chElement, it.columns());
        }
    }

    private void setEventColumnID(SOAPElement element, EventColumnID it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Columns");
            setColumnID(chElement, it.columnID());
        }

    }

    private void setColumnID(SOAPElement element, List<String> list) {
        if (list != null) {
            list.forEach(it -> addChildElement(element, "ColumnID").setTextContent(it));
        }
    }

    private void setTraceFilter(SOAPElement element, TraceFilter it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Filter");
            setNotType(chElement, "Not", it.not());
            setAndOrType(chElement, "Or", it.or());
            setAndOrType(chElement, "And", it.and());
            setBoolBinop(chElement, "Equal", it.isEqual());
            setBoolBinop(chElement, "NotEqual", it.notEqual());
            setBoolBinop(chElement, "Less", it.less());
            setBoolBinop(chElement, "LessOrEqual", it.lessOrEqual());
            setBoolBinop(chElement, "Greater", it.greater());
            setBoolBinop(chElement, "GreaterOrEqual", it.greaterOrEqual());
            setBoolBinop(chElement, "Like", it.like());
            setBoolBinop(chElement, "NotLike", it.notLike());
        }
    }

    private void setBoolBinop(SOAPElement element, String nodeName, BoolBinop it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, nodeName);
            addChildElement(chElement, "ColumnID", it.columnID());
            addChildElement(chElement, "Value", it.value());
        }
    }

    private void setAndOrType(SOAPElement element, String nodeName, AndOrType it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, nodeName);
            setAndOrTypeEnumList(chElement, it.notOrOrOrAnd());
        }
    }

    private void setAndOrTypeEnumList(SOAPElement element, List<AndOrTypeEnum> list) {
        if (list != null) {
            list.forEach(it -> addChildElement(element, it.name()));
        }
    }

    private void setNotType(SOAPElement element, String nodeName, NotType it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, nodeName);
            setNotType(chElement, "Not", it.not());
            setAndOrType(chElement, "Or", it.or());
            setAndOrType(chElement, "And", it.and());
            setBoolBinop(chElement, "Equal", it.isEqual());
            setBoolBinop(chElement, "NotEqual", it.notEqual());
            setBoolBinop(chElement, "Less", it.less());
            setBoolBinop(chElement, "LessOrEqual", it.lessOrEqual());
            setBoolBinop(chElement, "Greater", it.greater());
            setBoolBinop(chElement, "GreaterOrEqual", it.greaterOrEqual());
            setBoolBinop(chElement, "Like", it.like());
            setBoolBinop(chElement, "NotLike", it.notLike());
        }
    }

    private void setServer(SOAPElement element, Server it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Server");
            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "ID", it.id());
            addChildElement(chElement, "CreatedTimestamp", String.valueOf(it.createdTimestamp()));
            addChildElement(chElement, "LastSchemaUpdate", String.valueOf(it.lastSchemaUpdate()));
            addChildElement(chElement, "Description", it.description());
            setAnnotationList(chElement, it.annotations());
            addChildElement(chElement, "ProductName", it.productName());
            addChildElement(chElement, "Edition", it.edition());
            addChildElement(chElement, "EditionID", String.valueOf(it.editionID()));
            addChildElement(chElement, "Version", it.version());
            addChildElement(chElement, "ServerMode", it.serverMode());
            addChildElement(chElement, "ProductLevel", it.productLevel());
            addChildElement(chElement, "DefaultCompatibilityLevel", String.valueOf(it.defaultCompatibilityLevel()));
            addChildElement(chElement, "SupportedCompatibilityLevels", it.supportedCompatibilityLevels());
            setDatabaseList(chElement, it.databases());
            setAssemblyList(chElement, it.assemblies());
            setTraceList(chElement, it.traces());
            setRoleList(chElement, it.roles());
            setServerPropertyList(chElement, it.serverProperties());
        }
    }

    private void setServerPropertyList(SOAPElement element, List<ServerProperty> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "ServerProperties");
            list.forEach(it -> setServerProperty(chElement, it));
        }
    }

    private void setServerProperty(SOAPElement element, ServerProperty it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "ServerProperty");
            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "Value", it.value());
            addChildElement(chElement, "RequiresRestart", String.valueOf(it.requiresRestart()));
            addChildElement(chElement, "PendingValue", it.pendingValue().toString());
            addChildElement(chElement, "DefaultValue", it.defaultValue().toString());
            addChildElement(chElement, "DisplayFlag", String.valueOf(it.displayFlag()));
            addChildElement(chElement, "Type", it.type());
        }
    }

    private void setRoleList(SOAPElement element, List<Role> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Roles");
            list.forEach(it -> setRole(chElement, it));
        }
    }

    private void setTraceList(SOAPElement element, List<Trace> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Traces");
            list.forEach(it -> setTrace(chElement, it));
        }
    }

    private void setAssemblyList(SOAPElement element, List<Assembly> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Assemblies");
            list.forEach(it -> setAssembly(chElement, it));
        }
    }

    private void setDatabaseList(SOAPElement element, List<Database> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Databases");
            list.forEach(it -> setDatabase(chElement, it));
        }
    }

    private void setRole(SOAPElement element, Role it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Role");
            addChildElement(chElement, "Name", it.name());
            it.id().ifPresent(v -> addChildElement(chElement, "ID", v));
            it.createdTimestamp().ifPresent(v -> addChildElement(chElement, "CreatedTimestamp", convertInstant(v)));
            it.lastSchemaUpdate().ifPresent(v -> addChildElement(chElement, "LastSchemaUpdate", convertInstant(v)));
            it.description().ifPresent(v -> addChildElement(chElement, "Description", v));
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
            it.members().ifPresent(v -> setMemberList(chElement, v));
        }
    }

    private void setMemberList(SOAPElement element, List<Member> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Members");
            list.forEach(it -> setMember(chElement, it));
        }
    }

    private void setMember(SOAPElement element, Member it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Member");
            it.name().ifPresent(v -> addChildElement(chElement, "Name", v));
            it.sid().ifPresent(v -> addChildElement(chElement, "Sid", v));
        }
    }

    private void setPerspective(SOAPElement element, Perspective it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Perspective");
            setTranslationList(chElement, "Translations", "Translation", it.translations());
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
            it.attributeHierarchyVisible().ifPresent(v -> addChildElement(chElement, "AttributeHierarchyVisible",
             String.valueOf(v)));
            it.defaultMember().ifPresent(v -> addChildElement(chElement, "DefaultMember", v));
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
        }
    }

    private void setPermission(SOAPElement element, Permission it) {
        //TODO instanceof
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
            addChildElement(chElement, "StringStoresCompatibilityLevel",
                String.valueOf(it.stringStoresCompatibilityLevel()));
            addChildElement(chElement, "CurrentStringStoresCompatibilityLevel",
             String.valueOf(it.currentStringStoresCompatibilityLevel()));
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
            it.keyColumns().ifPresent(v -> setDataItemList(chElement, "KeyColumns", "KeyColumn", v));
        }
    }

    private void setDataItemList(SOAPElement element, String wrapperName, String tagName, List<DataItem> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, wrapperName);
            list.forEach(it -> setDataItem(chElement, tagName, it));
        }
    }

    private void setDataItem(SOAPElement element, String tagName, DataItem it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, tagName);
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
            it.translations().ifPresent(v -> setTranslationList(chElement, "Translations", "Translation", v));
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
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Columns");
            list.forEach(it -> setMiningStructureColumn(chElement, it));
        }
    }

    private void setMiningStructureColumn(SOAPElement element, MiningStructureColumn it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Column");
            if (it instanceof ScalarMiningStructureColumn smsc) {
                addChildElement(chElement, "Name", smsc.name());
                smsc.id().ifPresent(v -> addChildElement(chElement, "ID", v));
                smsc.description().ifPresent(v -> addChildElement(chElement, "Description", v));
                smsc.type().ifPresent(v -> addChildElement(chElement, "Type", v));
                smsc.annotations().ifPresent(v -> setAnnotationList(chElement, v));
                smsc.isKey().ifPresent(v -> addChildElement(chElement, "IsKey", String.valueOf(v)));
                smsc.source().ifPresent(v -> setBinding(chElement, v));
                smsc.description().ifPresent(v -> addChildElement(chElement, "Distribution", v));
                smsc.modelingFlags().ifPresent(v -> setMiningModelingFlagList(chElement, v));
                addChildElement(chElement, "Content", smsc.content());
                smsc.classifiedColumns().ifPresent(v -> setClassifiedColumnList(chElement, v));
                smsc.discretizationMethod().ifPresent(v -> addChildElement(chElement, "DiscretizationMethod", v));
                smsc.discretizationBucketCount().ifPresent(v -> addChildElement(chElement, "DiscretizationBucketCount"
                , String.valueOf(v)));
                smsc.keyColumns().ifPresent(v -> setDataItemList(chElement, "KeyColumns", "KeyColumn", v));
                smsc.nameColumn().ifPresent(v -> setDataItem(chElement, "NameColumn", v));
                smsc.translations().ifPresent(v -> setTranslationList(chElement, "Translations", "Translation", v));
            }
            if (it instanceof TableMiningStructureColumn tmsc) {
                tmsc.foreignKeyColumns().ifPresent(v -> setDataItemList(chElement, "ForeignKeyColumns",
"ForeignKeyColumn", v));
                tmsc.sourceMeasureGroup().ifPresent(v -> setMeasureGroupBinding(chElement, v));
                tmsc.columns().ifPresent(v -> setMiningStructureColumnList(chElement, v));
                tmsc.translations().ifPresent(v -> setTranslationList(chElement, "Translations", "Translation", v));
            }
        }
    }

    private void setMeasureGroupBinding(SOAPElement element, MeasureGroupBinding it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "SourceMeasureGroup");
            addChildElement(chElement, "DataSourceID", it.dataSourceID());
            addChildElement(chElement, "CubeID", it.cubeID());
            addChildElement(chElement, "MeasureGroupID", it.measureGroupID());
            it.persistence().ifPresent(v -> addChildElement(chElement, "Persistence", v.getValue()));
            it.refreshPolicy().ifPresent(v -> addChildElement(chElement, "RefreshPolicy", v.getValue()));
            it.refreshInterval().ifPresent(v -> addChildElement(chElement, "RefreshInterval", convertDuration(v)));
            it.filter().ifPresent(v -> addChildElement(chElement, "Filter", v));
        }
    }

    private void setClassifiedColumnList(SOAPElement element, List<String> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "ClassifiedColumns");
            list.forEach(it -> addChildElement(chElement, "ClassifiedColumn").setTextContent(it));
        }
    }

    private void setMiningModelingFlagList(SOAPElement element, List<MiningModelingFlag> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "ModelingFlags");
            list.forEach(it -> setMiningModelingFlag(chElement, it));
        }
    }

    private void setMiningModelingFlag(SOAPElement element, MiningModelingFlag it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "ModelingFlags");
            it.modelingFlag().ifPresent(v -> addChildElement(chElement, "ModelingFlag", v));
        }
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
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "MiningModelPermissions");
            list.forEach(it -> setMiningModelPermission(chElement, it));
        }
    }

    private void setMiningModelPermission(SOAPElement element, MiningModelPermission it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "MiningModelPermission");
            addChildElement(chElement, "RoleID", it.roleID());
            it.process().ifPresent(v -> addChildElement(chElement, "Process", String.valueOf(v)));
            it.readDefinition().ifPresent(v -> addChildElement(chElement, "ReadDefinition", v.getValue()));
            it.read().ifPresent(v -> addChildElement(chElement, "Read", v.getValue()));
            it.write().ifPresent(v -> addChildElement(chElement, "Write", v.getValue()));
            it.allowDrillThrough().ifPresent(v -> addChildElement(chElement, "AllowDrillThrough", String.valueOf(v)));
            it.allowBrowsing().ifPresent(v -> addChildElement(chElement, "AllowBrowsing", String.valueOf(v)));
        }
    }

    private void setFoldingParameters(SOAPElement element, FoldingParameters it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "FoldingParameters");
            addChildElement(chElement, "FoldIndex", String.valueOf(it.foldIndex()));
            addChildElement(chElement, "FoldCount", String.valueOf(it.foldCount()));
            it.foldMaxCases().ifPresent(v -> addChildElement(chElement, "FoldMaxCases", String.valueOf(v)));
            it.foldTargetAttribute().ifPresent(v -> addChildElement(chElement, "FoldTargetAttribute", v));
        }
    }

    private void setMiningModelColumnList(SOAPElement element, List<MiningModelColumn> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Columns");
            list.forEach(it -> setMiningModelColumn(chElement, it));
        }
    }

    private void setMiningModelColumn(SOAPElement element, MiningModelColumn it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Column");
            addChildElement(chElement, "Name", it.name());
            it.id().ifPresent(v -> addChildElement(chElement, "ID", v));
            it.description().ifPresent(v -> addChildElement(chElement, "Description", v));
            it.sourceColumnID().ifPresent(v -> addChildElement(chElement, "SourceColumnID", v));
            it.usage().ifPresent(v -> addChildElement(chElement, "Usage", v));
            it.filter().ifPresent(v -> addChildElement(chElement, "Filter", v));
            it.translations().ifPresent(v -> setTranslationList(chElement, "Translations", "Translation", v));
            it.columns().ifPresent(v -> setMiningModelColumnList(chElement, v));
            it.modelingFlags().ifPresent(v -> setMiningModelingFlagList(chElement, v));
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
        }
    }

    private void setAttributeTranslationList(SOAPElement element, List<AttributeTranslation> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Translations");
            list.forEach(it -> setAttributeTranslation(chElement, it));
        }
    }

    private void setAttributeTranslation(SOAPElement element, AttributeTranslation it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Translation");
            addChildElement(chElement, "Language", String.valueOf(it.language()));
            it.caption().ifPresent(v -> addChildElement(chElement, "Caption", v));
            it.description().ifPresent(v -> addChildElement(chElement, "Description", v));
            it.displayFolder().ifPresent(v -> addChildElement(chElement, "DisplayFolder", v));
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
            it.captionColumn().ifPresent(v -> setDataItem(chElement, "CaptionColumn", v));
            it.membersWithDataCaption().ifPresent(v -> addChildElement(chElement, "MembersWithDataCaption", v));
        }
    }

    private void setAlgorithmParameterList(SOAPElement element, List<AlgorithmParameter> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "AlgorithmParameters");
            list.forEach(it -> setAlgorithmParameter(chElement, it));
        }
    }

    private void setAlgorithmParameter(SOAPElement element, AlgorithmParameter it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "AlgorithmParameter");
            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "Value", it.value().toString());
        }
    }

    private void setMeasureGroup(SOAPElement element, MeasureGroup it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "MeasureGroup");
            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "ID", it.id());
            addChildElement(chElement, "CreatedTimestamp", String.valueOf(it.createdTimestamp()));
            addChildElement(chElement, "LastSchemaUpdate", String.valueOf(it.lastSchemaUpdate()));
            addChildElement(chElement, "Description", it.description());
            setAnnotationList(chElement, it.annotations());
            addChildElement(chElement, "LastProcessed", String.valueOf(it.lastProcessed()));
            setTranslationList(chElement, "Translations", "Translation", it.translations());
            addChildElement(chElement, "Type", it.type());
            addChildElement(chElement, "State", it.state());
            setMeasureList(chElement, it.measures());
            addChildElement(chElement, "DataAggregation", it.dataAggregation());
            setMeasureGroupBinding(chElement, it.source());
            setMeasureGroupStorageMode(chElement, it.storageMode());
            addChildElement(chElement, "StorageLocation", it.storageLocation());
            addChildElement(chElement, "IgnoreUnrelatedDimensions", String.valueOf(it.ignoreUnrelatedDimensions()));
            setProactiveCaching(chElement, it.proactiveCaching());
            addChildElement(chElement, "EstimatedRows", String.valueOf(it.estimatedRows()));
            setErrorConfiguration(chElement, it.errorConfiguration());
            addChildElement(chElement, "EstimatedSize", String.valueOf(it.estimatedSize()));
            addChildElement(chElement, "ProcessingMode", it.processingMode());
            setMeasureGroupDimensionList(chElement, it.dimensions());
            setPartitionList(chElement, it.partitions());
            addChildElement(chElement, "AggregationPrefix", it.aggregationPrefix());
            addChildElement(chElement, "ProcessingPriority", String.valueOf(it.processingPriority()));
            setAggregationDesignList(chElement, it.aggregationDesigns());
        }
    }

    private void setAggregationDesignList(SOAPElement element, List<AggregationDesign> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "AggregationDesigns");
            list.forEach(it -> setAggregationDesign(chElement, it));
        }
    }

    private void setPartitionList(SOAPElement element, List<Partition> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Partitions");
            list.forEach(it -> setPartition(chElement, it));
        }
    }

    private void setMeasureGroupDimensionList(SOAPElement element, List<MeasureGroupDimension> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Dimensions");
            list.forEach(it -> setMeasureGroupDimension(chElement, it));
        }
    }

    private void setMeasureGroupDimension(SOAPElement element, MeasureGroupDimension it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Dimension");
            if (it instanceof ManyToManyMeasureGroupDimension d) {
                addChildElement(chElement, "MeasureGroupID", String.valueOf(d.measureGroupID()));
                addChildElement(chElement, "DirectSlice", String.valueOf(d.directSlice()));
                addChildElement(chElement, "CubeDimensionID", String.valueOf(d.cubeDimensionID()));
                setAnnotationList(chElement, d.annotations());
                setMeasureGroupDimensionBinding(chElement, d.source());
            }
            if (it instanceof RegularMeasureGroupDimension d) {
                addChildElement(chElement, "Cardinality", d.cardinality());
                setMeasureGroupAttributeList(chElement, d.attributes());
                addChildElement(chElement, "CubeDimensionID", String.valueOf(d.cubeDimensionID()));
                setAnnotationList(chElement, d.annotations());
                setMeasureGroupDimensionBinding(chElement, d.source());

            }
            if (it instanceof ReferenceMeasureGroupDimension d) {
                addChildElement(chElement, "IntermediateCubeDimensionID", d.intermediateCubeDimensionID());
                addChildElement(chElement, "IntermediateGranularityAttributeID",
                    d.intermediateGranularityAttributeID());
                addChildElement(chElement, "Materialization", d.materialization());
                addChildElement(chElement, "ProcessingState", d.processingState());
                addChildElement(chElement, "CubeDimensionID", String.valueOf(d.cubeDimensionID()));
                setAnnotationList(chElement, d.annotations());
                setMeasureGroupDimensionBinding(chElement, d.source());

            }
            if (it instanceof DegenerateMeasureGroupDimension d) {
                addChildElement(chElement, "ShareDimensionStorage", d.shareDimensionStorage());
                addChildElement(chElement, "CubeDimensionID", String.valueOf(d.cubeDimensionID()));
                setAnnotationList(chElement, d.annotations());
                setMeasureGroupDimensionBinding(chElement, d.source());
            }
            if (it instanceof DataMiningMeasureGroupDimension d) {
                addChildElement(chElement, "CaseCubeDimensionID", d.caseCubeDimensionID());
                addChildElement(chElement, "CubeDimensionID", String.valueOf(d.cubeDimensionID()));
                setAnnotationList(chElement, d.annotations());
                setMeasureGroupDimensionBinding(chElement, d.source());
            }
        }
    }

    private void setMeasureGroupAttributeList(SOAPElement element, List<MeasureGroupAttribute> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Attributes");
            list.forEach(it -> setMeasureGroupAttribute(chElement, it));
        }
    }

    private void setMeasureGroupAttribute(SOAPElement element, MeasureGroupAttribute it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Attribute");
            addChildElement(chElement, "AttributeID", it.attributeID());
            setDataItemList(chElement, "KeyColumns", "KeyColumn", it.keyColumns());
            addChildElement(chElement, "Type", it.type());
            setAnnotationList(chElement, it.annotations());
        }
    }

    private void setMeasureGroupDimensionBinding(SOAPElement element, MeasureGroupDimensionBinding it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Source");
            addChildElement(chElement, "CubeDimensionID", it.cubeDimensionID());
        }
    }

    private void setMeasureGroupStorageMode(SOAPElement element, MeasureGroup.StorageMode it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "StorageMode");
            addChildElement(chElement, "valuens", it.valuens());
            addChildElement(chElement, "value", it.value() == null ? null : it.value().value());
        }

    }

    private void setMeasureList(SOAPElement element, List<Measure> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Measures");
            list.forEach(it -> setMeasure(chElement, it));
        }

    }

    private void setMeasure(SOAPElement element, Measure it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Measure");

            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "ID", it.id());
            addChildElement(chElement, "Description", it.description());
            addChildElement(chElement, "AggregateFunction", it.aggregateFunction());
            addChildElement(chElement, "DataType", it.dataType());
            setDataItem(chElement, "Source", it.source());
            addChildElement(chElement, "Visible", String.valueOf(it.visible()));
            addChildElement(chElement, "MeasureExpression", it.measureExpression());
            addChildElement(chElement, "DisplayFolder", it.displayFolder());
            addChildElement(chElement, "FormatString", it.formatString());
            addChildElement(chElement, "BackColor", it.backColor());
            addChildElement(chElement, "ForeColor", it.foreColor());
            addChildElement(chElement, "FontName", it.fontName());
            addChildElement(chElement, "FontSize", it.fontSize());
            addChildElement(chElement, "FontFlags", it.fontFlags());
            setTranslationList(chElement, "Translations", "Translation", it.translations());
            setAnnotationList(chElement, it.annotations());
        }
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
            setTranslationList(chElement, "Translations", "Translation", it.translations());
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

    private void setCalculationPropertiesVisualizationProperties(
        SOAPElement element,
        CalculationPropertiesVisualizationProperties it
    ) {
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
            if (it instanceof Backup) {

            }
            if (it instanceof Batch) {

            }
            if (it instanceof BeginTransaction) {

            }
            if (it instanceof Cancel) {

            }
            if (it instanceof ClearCache) {

            }
            if (it instanceof CloneDatabase) {

            }
            if (it instanceof CommitTransaction) {

            }
            if (it instanceof Create) {

            }
            if (it instanceof DBCC) {

            }
            if (it instanceof Delete) {

            }
            if (it instanceof DesignAggregations) {

            }
            if (it instanceof Detach) {

            }
            if (it instanceof Drop) {

            }
            if (it instanceof ImageLoad) {

            }
            if (it instanceof ImageSave) {

            }
            if (it instanceof Insert) {

            }
            if (it instanceof Lock) {

            }
            if (it instanceof MergePartitions) {

            }
            if (it instanceof NotifyTableChange) {

            }
            if (it instanceof Process) {

            }
            if (it instanceof Restore) {

            }
            if (it instanceof RollbackTransaction) {

            }
            if (it instanceof SetAuthContext) {

            }
            if (it instanceof Statement) {

            }
            if (it instanceof Subscribe) {

            }
            if (it instanceof Synchronize) {

            }
            if (it instanceof Unlock) {

            }
            if (it instanceof Unsubscribe) {

            }
            if (it instanceof Update) {

            }
            if (it instanceof UpdateCells) {

            }

        }
    }

    private void setDimension(SOAPElement element, Dimension it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Dimension");
            setBinding(chElement, it.source());
            addChildElement(chElement, "MiningModelID", it.miningModelID());
            addChildElement(chElement, "Type", it.type());
            setDimensionUnknownMember(chElement, it.unknownMember());
            addChildElement(chElement, "MdxMissingMemberMode", it.mdxMissingMemberMode());
            setErrorConfiguration(chElement, it.errorConfiguration());
            addChildElement(chElement, "StorageMode", it.storageMode());
            addChildElement(chElement, "WriteEnabled", String.valueOf(it.writeEnabled()));
            addChildElement(chElement, "ProcessingPriority", String.valueOf(it.processingPriority()));
            addChildElement(chElement, "LastProcessed", convertInstant(it.lastProcessed()));
            setDimensionPermissionList(chElement, it.dimensionPermissions());
            addChildElement(chElement, "DependsOnDimensionID", it.dependsOnDimensionID());
            addChildElement(chElement, "Language", String.valueOf(it.language()));
            addChildElement(chElement, "Collation", it.collation());
            addChildElement(chElement, "UnknownMemberName", it.unknownMemberName());
            setTranslationList(chElement, "Translations", "Translation", it.translations());
            addChildElement(chElement, "State", it.state());
            setProactiveCaching(chElement, it.proactiveCaching());
            addChildElement(chElement, "ProcessingMode", it.processingMode());
            addChildElement(chElement, "ProcessingGroup", it.processingGroup());
            setDimensionCurrentStorageMode(chElement, it.currentStorageMode());
            setTranslationList(chElement, "Translations", "Translation", it.translations());
            setDimensionAttributeList(chElement, it.attributes());
            addChildElement(chElement, "AttributeAllMemberName", it.attributeAllMemberName());
            setTranslationList(chElement, "AttributeAllMemberTranslations", "AttributeAllMemberTranslation",
                it.translations());
            setHierarchyList(chElement, it.hierarchies());
            addChildElement(chElement, "ProcessingRecommendation", it.processingRecommendation());
            setRelationships(chElement, it.relationships());
            addChildElement(chElement, "StringStoresCompatibilityLevel",
                String.valueOf(it.stringStoresCompatibilityLevel()));
            addChildElement(chElement, "CurrentStringStoresCompatibilityLevel",
                String.valueOf(it.currentStringStoresCompatibilityLevel()));
        }
    }

    private void setRelationships(SOAPElement element, Relationships it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Relationships");
            setRelationshipList(chElement, it.relationship());
        }
    }

    private void setRelationshipList(SOAPElement element, List<Relationship> list) {
        if (list != null) {
            list.forEach(it -> setRelationship(element, it));
        }
    }

    private void setRelationship(SOAPElement element, Relationship it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Relationship");
            addChildElement(chElement, "ID", it.id());
            addChildElement(chElement, "Visible", String.valueOf(it.visible()));
            setRelationshipEnd(chElement, "FromRelationshipEnd", it.fromRelationshipEnd());
            setRelationshipEnd(chElement, "ToRelationshipEnd", it.toRelationshipEnd());
        }
    }

    private void setRelationshipEnd(SOAPElement element, String nodeName, RelationshipEnd it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, nodeName);
            addChildElement(chElement, "Role", it.role());
            addChildElement(chElement, "Multiplicity", it.multiplicity());
            addChildElement(chElement, "DimensionID", it.dimensionID());
            setRelationshipEndAttributes(chElement, it.attributes());
            setRelationshipEndTranslations(chElement, it.translations());
            setRelationshipEndVisualizationProperties(chElement, it.visualizationProperties());
        }
    }

    private void setRelationshipEndVisualizationProperties(
        SOAPElement element,
        RelationshipEndVisualizationProperties it
    ) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "VisualizationProperties");
            addChildElement(chElement, "FolderPosition", String.valueOf(it.folderPosition()));
            addChildElement(chElement, "ContextualNameRule", it.contextualNameRule());
            addChildElement(chElement, "DefaultDetailsPosition", String.valueOf(it.defaultDetailsPosition()));
            addChildElement(chElement, "DisplayKeyPosition", String.valueOf(it.displayKeyPosition()));
            addChildElement(chElement, "CommonIdentifierPosition", String.valueOf(it.commonIdentifierPosition()));
            addChildElement(chElement, "IsDefaultMeasure", String.valueOf(it.isDefaultMeasure()));
            addChildElement(chElement, "IsDefaultImage", String.valueOf(it.isDefaultImage()));
            addChildElement(chElement, "SortPropertiesPosition", String.valueOf(it.sortPropertiesPosition()));
        }
    }

    private void setRelationshipEndTranslations(SOAPElement element, List<RelationshipEndTranslation> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Translations");
            list.forEach(it -> setRelationshipEndTranslation(chElement, it));
        }

    }

    private void setRelationshipEndTranslation(SOAPElement element, RelationshipEndTranslation it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Translation");
            addChildElement(chElement, "Language", String.valueOf(it.language()));
            addChildElement(chElement, "Caption", it.caption());
            addChildElement(chElement, "Description", it.description());
            addChildElement(chElement, "DisplayFolder", it.displayFolder());
            setAnnotationList(chElement, it.annotations());
            addChildElement(chElement, "CollectionCaption", it.collectionCaption());
        }
    }

    private void setRelationshipEndAttributes(SOAPElement element, List<String> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Attributes");
            list.forEach(it -> setRelationshipEndAttribute(chElement, it));
        }
    }

    private void setRelationshipEndAttribute(SOAPElement element, String it) {
        if (it != null) {
            addChildElement(addChildElement(element, "Attribute"), "AttributeID", it);
        }
    }

    private void setHierarchyList(SOAPElement element, List<Hierarchy> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Hierarchies");
            list.forEach(it -> setHierarchy(chElement, it));
        }
    }

    private void setHierarchy(SOAPElement element, Hierarchy it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Hierarchy");
            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "ID", it.id());
            addChildElement(chElement, "Description", it.description());
            addChildElement(chElement, "ProcessingState", it.processingState());
            addChildElement(chElement, "StructureType", it.structureType());
            addChildElement(chElement, "DisplayFolder", it.displayFolder());
            setTranslationList(chElement, "Translations", "Translation", it.translations());
            addChildElement(chElement, "AllMemberName", it.allMemberName());
            setAnnotationList(chElement, it.annotations());
            setTranslationList(chElement, "AllMemberTranslations", "AllMemberTranslations", it.allMemberTranslations());
            addChildElement(chElement, "MemberNamesUnique", String.valueOf(it.memberNamesUnique()));
            addChildElement(chElement, "MemberKeysUnique", String.valueOf(it.memberKeysUnique()));
            addChildElement(chElement, "AllowDuplicateNames", String.valueOf(it.allowDuplicateNames()));
            setLevelList(chElement, it.levels());
            setAnnotationList(chElement, it.annotations());
            setHierarchyVisualizationProperties(chElement, it.visualizationProperties());
        }
    }

    private void setHierarchyVisualizationProperties(SOAPElement element, HierarchyVisualizationProperties it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "VisualizationProperties");
            addChildElement(chElement, "ContextualNameRule", it.contextualNameRule());
            addChildElement(chElement, "FolderPosition", String.valueOf(it.folderPosition()));
        }
    }

    private void setLevelList(SOAPElement element, List<Level> list) {
        //            @XmlElement(name = "Level", required = true)
        //            @XmlElementWrapper(name = "Levels", required = true)
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Levels");
            list.forEach(it -> setLevel(chElement, it));
        }

    }

    private void setLevel(SOAPElement element, Level it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Level");
            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "ID", it.id());
            addChildElement(chElement, "Description", it.description());
            addChildElement(chElement, "SourceAttributeID", it.sourceAttributeID());
            addChildElement(chElement, "HideMemberIf", it.hideMemberIf());
            setTranslationList(chElement, "Translations", "Translation", it.translations());
            setAnnotationList(chElement, it.annotations());
        }
    }

    private void setDimensionAttributeList(SOAPElement element, List<DimensionAttribute> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "Attributes");
            list.forEach(it -> setDimensionAttribute(chElement, it));
        }
    }

    private void setDimensionAttribute(SOAPElement element, DimensionAttribute it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Attribute");
            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "ID", it.id());
            addChildElement(chElement, "Description", it.description());
            setDimensionAttributeType(chElement, it.type());
            addChildElement(chElement, "Usage", it.usage());
            setBinding(chElement, it.source());
            addChildElement(chElement, "EstimatedCount", String.valueOf(it.estimatedCount()));
            setDataItemList(chElement, "KeyColumns", "KeyColumn", it.keyColumns());
            setDataItem(chElement, "NameColumn", it.nameColumn());
            setDataItem(chElement, "ValueColumn", it.valueColumn());
            setAttributeTranslationList(chElement, it.translations());
            setAttributeRelationshipList(chElement, it.attributeRelationships());
            addChildElement(chElement, "DiscretizationMethod", it.discretizationMethod());
            addChildElement(chElement, "DiscretizationBucketCount", String.valueOf(it.discretizationBucketCount()));
            addChildElement(chElement, "RootMemberIf", it.rootMemberIf());
            addChildElement(chElement, "OrderBy", it.orderBy());
            addChildElement(chElement, "DefaultMember", it.defaultMember());
            addChildElement(chElement, "OrderByAttributeID", it.orderByAttributeID());
            setDataItem(chElement, "SkippedLevelsColumn", it.skippedLevelsColumn());
            addChildElement(chElement, "NamingTemplate", it.namingTemplate());
            addChildElement(chElement, "MembersWithData", it.membersWithData());
            addChildElement(chElement, "MembersWithDataCaption", it.membersWithDataCaption());
            setTranslationList(chElement, "NamingTemplateTranslations", "NamingTemplateTranslation",
                it.namingTemplateTranslations());
            setDataItem(chElement, "CustomRollupColumn", it.customRollupColumn());
            setDataItem(chElement, "CustomRollupPropertiesColumn", it.customRollupPropertiesColumn());
            setDataItem(chElement, "UnaryOperatorColumn", it.unaryOperatorColumn());
            addChildElement(chElement, "AttributeHierarchyOrdered", String.valueOf(it.attributeHierarchyOrdered()));
            addChildElement(chElement, "MemberNamesUnique", String.valueOf(it.memberNamesUnique()));
            addChildElement(chElement, "IsAggregatable", String.valueOf(it.isAggregatable()));
            addChildElement(chElement, "AttributeHierarchyEnabled", String.valueOf(it.attributeHierarchyEnabled()));
            addChildElement(chElement, "AttributeHierarchyOptimizedState", it.attributeHierarchyOptimizedState());
            addChildElement(chElement, "AttributeHierarchyVisible", String.valueOf(it.attributeHierarchyVisible()));
            addChildElement(chElement, "AttributeHierarchyDisplayFolder", it.attributeHierarchyDisplayFolder());
            addChildElement(chElement, "KeyUniquenessGuarantee", String.valueOf(it.keyUniquenessGuarantee()));
            addChildElement(chElement, "GroupingBehavior", it.groupingBehavior());
            addChildElement(chElement, "InstanceSelection", it.instanceSelection());
            setAnnotationList(chElement, it.annotations());
            addChildElement(chElement, "ProcessingState", it.processingState());
            addChildElement(chElement, "AttributeHierarchyProcessingState",
             it.attributeHierarchyProcessingState() == null ? null : it.attributeHierarchyProcessingState().value());
            addChildElement(chElement, "ExtendedType", it.extendedType());
        }
    }

    private void setAttributeRelationshipList(SOAPElement element, List<AttributeRelationship> list) {
        //            @XmlElement(name = "AttributeRelationship")
        //            @XmlElementWrapper(name = "AttributeRelationships")
        //            protected List<AttributeRelationship> attributeRelationships;
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "AttributeRelationships");
            list.forEach(it -> setAttributeRelationship(chElement, it));
        }

    }

    private void setAttributeRelationship(SOAPElement element, AttributeRelationship it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "AttributeRelationship");
            addChildElement(chElement, "AttributeID", it.attributeID());
            addChildElement(chElement, "RelationshipType", it.relationshipType());
            addChildElement(chElement, "Cardinality", it.cardinality());
            addChildElement(chElement, "Optionality", it.optionality());
            addChildElement(chElement, "OverrideBehavior", it.overrideBehavior());
            setAnnotationList(chElement, it.annotations());
            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "Visible", String.valueOf(it.visible()));
            setTranslationList(chElement, "Translations", "Translation", it.translations());
        }
    }

    private void setDimensionAttributeType(SOAPElement element, DimensionAttribute.Type it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "Type");
            addChildElement(chElement, "valuens", it.valuens());
            addChildElement(chElement, "value", it.value() == null ? null : it.value().value());
        }
    }

    private void setDimensionCurrentStorageMode(SOAPElement element, Dimension.CurrentStorageMode it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "CurrentStorageMode");
            addChildElement(chElement, "valuens", it.valuens());
            addChildElement(chElement, "value", it.value() == null ? null : it.value().value());
        }
    }

    private void setDimensionPermissionList(SOAPElement element, List<DimensionPermission> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "DimensionPermissions");
            list.forEach(it -> setDimensionPermission(chElement, it));
        }

    }

    private void setDimensionPermission(SOAPElement element, DimensionPermission it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "DimensionPermission");
            addChildElement(chElement, "RoleID", it.roleID());
            it.process().ifPresent(v -> addChildElement(chElement, "Process", String.valueOf(v)));
            it.readDefinition().ifPresent(v -> addChildElement(chElement, "ReadDefinition", v.getValue()));
            it.read().ifPresent(v -> addChildElement(chElement, "Read", v.getValue()));
            it.write().ifPresent(v -> addChildElement(chElement, "Write", v.getValue()));

            addChildElement(chElement, "Name", it.name());
            it.id().ifPresent(v -> addChildElement(chElement, "ID", v));
            it.createdTimestamp().ifPresent(v -> addChildElement(chElement, "CreatedTimestamp", convertInstant(v)));
            it.lastSchemaUpdate().ifPresent(v -> addChildElement(chElement, "LastSchemaUpdate", convertInstant(v)));
            it.description().ifPresent(v -> addChildElement(chElement, "Description", v));
            it.annotations().ifPresent(v -> setAnnotationList(chElement, v));
            it.attributePermissions().ifPresent(v -> setAttributePermissionList(chElement, v));

            it.allowedRowsExpression().ifPresent(v -> addChildElement(chElement, "AllowedRowsExpression", v));
        }
    }

    private void setDimensionUnknownMember(SOAPElement element, Dimension.UnknownMember it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "UnknownMember");
            addChildElement(chElement, "valuens", it.valuens());
            addChildElement(chElement, "value", it.value() == null ? null : it.value().value());
        }
    }

    private void setDataSourceView(SOAPElement element, DataSourceView it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "DataSourceView");
            addChildElement(chElement, "DataSourceID", it.dataSourceID());

            addChildElement(chElement, "Name", it.name());
            addChildElement(chElement, "ID", it.id());
            addChildElement(chElement, "CreatedTimestamp", convertInstant(it.createdTimestamp()));
            addChildElement(chElement, "LastSchemaUpdate", convertInstant(it.lastSchemaUpdate()));
            addChildElement(chElement, "Description", it.description());
            setAnnotationList(chElement, it.annotations());
        }
    }


    private void setDataSource(SOAPElement element, DataSource it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "DataSource");
            addChildElement(chElement, "ManagedProvider", it.managedProvider());
            addChildElement(chElement, "ConnectionString", it.connectionString());
            addChildElement(chElement, "ConnectionStringSecurity", it.connectionStringSecurity());
            setImpersonationInfo(chElement, "ImpersonationInfo", it.impersonationInfo());
            addChildElement(chElement, "Isolation", it.isolation());
            addChildElement(chElement, "MaxActiveConnections", String.valueOf(it.maxActiveConnections()));
            addChildElement(chElement, "Timeout", convertDuration(it.timeout()));
            setDataSourcePermissionList(chElement, it.dataSourcePermissions());
            setImpersonationInfo(chElement, "QueryImpersonationInfo", it.queryImpersonationInfo());
            addChildElement(chElement, "QueryHints", it.queryHints());
        }
    }

    private void setDataSourcePermissionList(SOAPElement element, List<DataSourcePermission> list) {
        if (list != null) {
            SOAPElement chElement = addChildElement(element, "DataSourcePermissions");
            list.forEach(it -> setDataSourcePermission(chElement, it));
        }
    }

    private void setDataSourcePermission(SOAPElement element, DataSourcePermission it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, "DataSourcePermission");
            addChildElement(chElement, "RoleID", it.roleID());
            it.process().ifPresent(v -> addChildElement(chElement, "Process", String.valueOf(v)));
            it.readDefinition().ifPresent(v -> addChildElement(chElement, "ReadDefinition", v.getValue()));
            it.read().ifPresent(v -> addChildElement(chElement, "Read", v.getValue()));
            it.write().ifPresent(v -> addChildElement(chElement, "Write", v.getValue()));
        }
    }

    private void setImpersonationInfo(SOAPElement element, String tagName, ImpersonationInfo it) {
        if (it != null) {
            SOAPElement chElement = addChildElement(element, tagName);
            addChildElement(chElement, "ImpersonationMode", it.impersonationMode());
            it.account().ifPresent(v -> addChildElement(chElement, "Account", v));
            addChildElement(chElement, "ImpersonationMode", it.impersonationMode());
            it.account().ifPresent(v -> addChildElement(chElement, "Account", v));
            it.password().ifPresent(v -> addChildElement(chElement, "Password", v));
            it.impersonationInfoSecurity().ifPresent(v -> addChildElement(chElement, "ImpersonationInfoSecurity", v));
        }
    }

    private void setDatabase(SOAPElement objectDefinitionElement, Database it) {
        //TODO
    }

    private void setCube(SOAPElement objectDefinitionElement, Cube cube) throws SOAPException {
        if (cube != null) {
            SOAPElement cubeelement = objectDefinitionElement.addChildElement("Cube");
            addChildElement(cubeelement, "Language", String.valueOf(cube.language()));
            addChildElement(cubeelement, "Collation", String.valueOf(cube.collation()));
            setTranslationList(cubeelement, "Translations", "Translation", cube.translations());
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
            it.translations().ifPresent(v -> setTranslationList(chElement, "Translations", "Translation", v));
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
            SOAPElement chElement = addChildElement(element, "Source");
            if (it instanceof ColumnBinding cb) {
                addChildElement(chElement, "TableID", cb.tableID());
                addChildElement(chElement, "ColumnID", cb.columnID());
            }
            if (it instanceof RowBinding rb) {
                addChildElement(chElement, "TableID", rb.tableID());
            }
            if (it instanceof DataSourceViewBinding dsvb) {
                addChildElement(chElement, "DataSourceViewID", dsvb.dataSourceViewID());
            }
            if (it instanceof AttributeBinding ab) {
                addChildElement(chElement, "AttributeID", ab.attributeID());
                addChildElement(chElement, "Type", ab.type() == null ? null : ab.type().getValue());
                addChildElement(chElement, "Ordinal", String.valueOf(ab.ordinal()));
            }
            if (it instanceof UserDefinedGroupBinding udgb) {
                addChildElement(chElement, "AttributeID", udgb.attributeID());
                udgb.groups().ifPresent(v -> setGroupList(chElement, v));
            }
            if (it instanceof MeasureBinding mb) {
                addChildElement(chElement, "MeasureID", mb.measureID());
            }
            if (it instanceof CubeAttributeBinding cab) {
                addChildElement(chElement, "CubeID", cab.cubeID());
                addChildElement(chElement, "CubeDimensionID", cab.cubeDimensionID());
                addChildElement(chElement, "AttributeID", cab.attributeID());
                addChildElement(chElement, "Type", cab.type() == null ? null : cab.type().getValue());
                cab.ordinal().ifPresent(v -> setCubeAttributeBindingOrdinalList(chElement, v));
            }
            if (it instanceof DimensionBinding db) {
                addChildElement(chElement, "DataSourceID", db.dataSourceID());
                addChildElement(chElement, "DimensionID", db.dimensionID());
                db.persistence().ifPresent(v -> addChildElement(chElement, "Persistence", v.getValue()));
                db.refreshPolicy().ifPresent(v -> addChildElement(chElement, "RefreshPolicy", v.getValue()));
                db.refreshInterval().ifPresent(v -> addChildElement(chElement, "RefreshInterval", String.valueOf(v)));
            }
            if (it instanceof CubeDimensionBinding cdb) {
                addChildElement(chElement, "DataSourceID", cdb.dataSourceID());
                addChildElement(chElement, "CubeID", cdb.cubeID());
                addChildElement(chElement, "CubeDimensionID", cdb.cubeDimensionID());
                cdb.filter().ifPresent(v -> addChildElement(chElement, "Filter", v));
            }
            if (it instanceof MeasureGroupBinding mgb) {
                addChildElement(chElement, "DataSourceID", mgb.dataSourceID());
                addChildElement(chElement, "CubeID", mgb.cubeID());
                addChildElement(chElement, "MeasureGroupID", mgb.measureGroupID());
                mgb.persistence().ifPresent(v -> addChildElement(chElement, "Persistence", v.getValue()));
                mgb.refreshPolicy().ifPresent(v -> addChildElement(chElement, "RefreshPolicy", v.getValue()));
                mgb.refreshInterval().ifPresent(v -> addChildElement(chElement, "RefreshInterval", convertDuration(v)));
                mgb.filter().ifPresent(v -> addChildElement(chElement, "Filter", v));
            }
            if (it instanceof MeasureGroupDimensionBinding mgdb) {
                addChildElement(chElement, "CubeDimensionID", mgdb.cubeDimensionID());
            }
            if (it instanceof TimeBinding tb) {
                addChildElement(chElement, "CalendarStartDate", convertInstant(tb.calendarStartDate()));
                addChildElement(chElement, "CalendarEndDate", convertInstant(tb.calendarEndDate()));
                tb.firstDayOfWeek().ifPresent(v -> addChildElement(chElement, "FirstDayOfWeek", String.valueOf(v)));
                tb.calendarLanguage().ifPresent(v -> addChildElement(chElement, "CalendarLanguage", String.valueOf(v)));
                tb.fiscalFirstMonth().ifPresent(v -> addChildElement(chElement, "FiscalFirstMonth", String.valueOf(v)));
                tb.fiscalFirstDayOfMonth().ifPresent(v -> addChildElement(chElement, "FiscalFirstDayOfMonth",
                 String.valueOf(v)));
                tb.fiscalYearName().ifPresent(v -> addChildElement(chElement, "FiscalYearName", v.getValue()));
                tb.reportingFirstMonth().ifPresent(v -> addChildElement(chElement, "ReportingFirstMonth",
                 String.valueOf(v)));
                tb.reportingFirstWeekOfMonth().ifPresent(v -> addChildElement(chElement, "ReportingFirstWeekOfMonth",
                    v));
                tb.reportingWeekToMonthPattern().ifPresent(v -> addChildElement(chElement,
                    "ReportingWeekToMonthPattern", v.getValue()));
                tb.manufacturingFirstMonth().ifPresent(v -> addChildElement(chElement, "ManufacturingFirstMonth",
                 String.valueOf(v)));
                tb.manufacturingFirstWeekOfMonth().ifPresent(v -> addChildElement(chElement,
                "ManufacturingFirstWeekOfMonth", String.valueOf(v)));
                tb.manufacturingExtraMonthQuarter().ifPresent(v -> addChildElement(chElement,
                "ManufacturingExtraMonthQuarter", String.valueOf(v)));
            }
            if (it instanceof TimeAttributeBinding) {
            }
            if (it instanceof InheritedBinding) {
            }
            if (it instanceof CalculatedMeasureBinding cmb) {
                addChildElement(chElement, "MeasureName", cmb.measureName());
            }
            if (it instanceof RowNumberBinding) {
            }
            if (it instanceof ExpressionBinding eb) {
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
            setTranslationList(chElement, "Translations", "Translation", it.translations());
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
            proactiveCaching.silenceInterval().ifPresent(v -> addChildElement(chElement, "SilenceInterval",
             convertDuration1(v)));
            proactiveCaching.latency().ifPresent(v -> addChildElement(chElement, "Latency", convertDuration1(v)));
            proactiveCaching.silenceOverrideInterval().ifPresent(v -> addChildElement(chElement,
"SilenceOverrideInterval", convertDuration1(v)));
            proactiveCaching.forceRebuildInterval().ifPresent(v -> addChildElement(chElement, "ForceRebuildInterval",
                convertDuration1(v)));
            proactiveCaching.enabled().ifPresent(v -> addChildElement(chElement, "Enabled", String.valueOf(v)));
        }
    }

    private void setProactiveCachingBinding(SOAPElement element, ProactiveCachingBinding source) {
        if (source != null) {
            SOAPElement chElement = addChildElement(element, "Source");
            if (source instanceof ProactiveCachingIncrementalProcessingBinding pcipb) {
                pcipb.refreshInterval().ifPresent(v -> addChildElement(chElement, "RefreshInterval",
                    convertDuration(v)));
                setIncrementalProcessingNotificationList(chElement, pcipb.incrementalProcessingNotifications());
            }
            if (source instanceof ProactiveCachingQueryBinding pcqb) {
                pcqb.refreshInterval().ifPresent(v -> addChildElement(chElement, "RefreshInterval",
                    convertDuration(v)));
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

    private void setIncrementalProcessingNotificationList(
        SOAPElement element,
        List<IncrementalProcessingNotification> list
    ) {
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
        //TODO investigate why use different Duration
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
        setTranslationList(dimensionElement, "Translations", "Translation", cubeDimension.translations());
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
        addChildElement(hierarchyElement, "HierarchyID", v.hierarchyID());
        addChildElement(hierarchyElement, "OptimizedState", v.optimizedState());
        addChildElement(hierarchyElement, "Visible", String.valueOf(v.visible()));
        addChildElement(hierarchyElement, "Enabled", String.valueOf(v.enabled()));
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

    private void setTranslationList(
        SOAPElement cubeElement,
        String wrapperName,
        String tagName,
        List<Translation> translations
    ) {
        if (translations != null) {
            SOAPElement translationsElement = addChildElement(cubeElement, wrapperName);
            translations.forEach(it -> setTranslation(translationsElement, tagName, it));
        }
    }

    private void setTranslation(SOAPElement translationsElement, String tagName, Translation it) {
        if (it != null) {
            SOAPElement translationElement = addChildElement(translationsElement, tagName);
            addChildElement(translationElement, "Language", String.valueOf(it.language()));
            addChildElement(translationElement, "Caption", String.valueOf(it.caption()));
            addChildElement(translationElement, "Description", String.valueOf(it.description()));
            addChildElement(translationElement, "DisplayFolder", String.valueOf(it.displayFolder()));
            setAnnotationList(translationElement, it.annotations());
        }
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
        AggregationDesign aggregationDesign
    ) {
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
