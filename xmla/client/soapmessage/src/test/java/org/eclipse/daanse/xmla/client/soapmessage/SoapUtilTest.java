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

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import org.eclipse.daanse.xmla.api.engine.ImpersonationInfo;
import org.eclipse.daanse.xmla.api.engine300_300.XEvent;
import org.eclipse.daanse.xmla.api.execute.ExecuteParameter;
import org.eclipse.daanse.xmla.api.xmla.Account;
import org.eclipse.daanse.xmla.api.xmla.Action;
import org.eclipse.daanse.xmla.api.xmla.Aggregation;
import org.eclipse.daanse.xmla.api.xmla.AggregationAttribute;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesign;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesignAttribute;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesignDimension;
import org.eclipse.daanse.xmla.api.xmla.AggregationDimension;
import org.eclipse.daanse.xmla.api.xmla.AndOrType;
import org.eclipse.daanse.xmla.api.xmla.AndOrTypeEnum;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Assembly;
import org.eclipse.daanse.xmla.api.xmla.BoolBinop;
import org.eclipse.daanse.xmla.api.xmla.Cancel;
import org.eclipse.daanse.xmla.api.xmla.ClearCache;
import org.eclipse.daanse.xmla.api.xmla.Cube;
import org.eclipse.daanse.xmla.api.xmla.CubeDimension;
import org.eclipse.daanse.xmla.api.xmla.CubePermission;
import org.eclipse.daanse.xmla.api.xmla.DataSource;
import org.eclipse.daanse.xmla.api.xmla.DataSourceView;
import org.eclipse.daanse.xmla.api.xmla.DataSourceViewBinding;
import org.eclipse.daanse.xmla.api.xmla.Database;
import org.eclipse.daanse.xmla.api.xmla.DatabasePermission;
import org.eclipse.daanse.xmla.api.xmla.Dimension;
import org.eclipse.daanse.xmla.api.xmla.ErrorConfiguration;
import org.eclipse.daanse.xmla.api.xmla.Event;
import org.eclipse.daanse.xmla.api.xmla.EventColumnID;
import org.eclipse.daanse.xmla.api.xmla.EventSession;
import org.eclipse.daanse.xmla.api.xmla.EventType;
import org.eclipse.daanse.xmla.api.xmla.IncrementalProcessingNotification;
import org.eclipse.daanse.xmla.api.xmla.Kpi;
import org.eclipse.daanse.xmla.api.xmla.MajorObject;
import org.eclipse.daanse.xmla.api.xmla.MdxScript;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroup;
import org.eclipse.daanse.xmla.api.xmla.MiningModel;
import org.eclipse.daanse.xmla.api.xmla.MiningStructure;
import org.eclipse.daanse.xmla.api.xmla.NotType;
import org.eclipse.daanse.xmla.api.xmla.ObjectReference;
import org.eclipse.daanse.xmla.api.xmla.Partition;
import org.eclipse.daanse.xmla.api.xmla.PartitionModes;
import org.eclipse.daanse.xmla.api.xmla.Permission;
import org.eclipse.daanse.xmla.api.xmla.Perspective;
import org.eclipse.daanse.xmla.api.xmla.ProactiveCaching;
import org.eclipse.daanse.xmla.api.xmla.ProactiveCachingBinding;
import org.eclipse.daanse.xmla.api.xmla.ProactiveCachingIncrementalProcessingBinding;
import org.eclipse.daanse.xmla.api.xmla.RetentionModes;
import org.eclipse.daanse.xmla.api.xmla.Role;
import org.eclipse.daanse.xmla.api.xmla.Server;
import org.eclipse.daanse.xmla.api.xmla.ServerProperty;
import org.eclipse.daanse.xmla.api.xmla.TargetTypeEnum;
import org.eclipse.daanse.xmla.api.xmla.Trace;
import org.eclipse.daanse.xmla.api.xmla.TraceFilter;
import org.eclipse.daanse.xmla.api.xmla.Translation;
import org.eclipse.daanse.xmla.api.xmla.TypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj3.XmlAssert;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.eclipse.daanse.xmla.client.soapmessage.Constants.DESCRIPTION;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.ID;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.NAME;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.NAME_LC;
import static org.eclipse.daanse.xmla.client.soapmessage.Constants.VALUE2;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SoapUtilTest {

    private MessageFactory mf;
    private SOAPMessage message;
    private SOAPElement soapElement;
    private List<Annotation> annotations;

    @BeforeEach
    void beforeEach() throws SOAPException {
        mf = MessageFactory.newInstance();
        message = mf.createMessage();
        soapElement = message.getSOAPBody();
        annotations = createAnnotationList();
    }

    @Test
    void addChildElementCancelTest() throws Exception {

        SoapUtil.addChildElementCancel(soapElement, createCancel());

        XmlAssert xmlAssert = XMLUtil.createAssert(message);
        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/Cancel")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Cancel/ConnectionID")
            .isEqualTo("1");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Cancel/SessionID")
            .isEqualTo("SessionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Cancel/SPID")
            .isEqualTo("10");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/Cancel/CancelAssociated")
            .isEqualTo(true);
    }

    @Test
    void addChildElementExecuteParameterTest() throws Exception {
        SoapUtil.addChildElementExecuteParameter(soapElement, createExecuteParameter());

        XmlAssert xmlAssert = XMLUtil.createAssert(message);
        xmlAssert.hasXPath("/SOAP:Envelope");
        checkExecuteParameter(xmlAssert, "/SOAP:Envelope/SOAP:Body");
    }

    @Test
    void addChildElementTraceTest() throws Exception {
        SoapUtil.addChildElementTrace(soapElement, createTrace());
        XmlAssert xmlAssert = XMLUtil.createAssert(message);
        xmlAssert.hasXPath("/SOAP:Envelope");
        checkTrace(xmlAssert, "/SOAP:Envelope/SOAP:Body");
    }

    @Test
    void addChildElementClearCacheTest() throws Exception {
        SoapUtil.addChildElementClearCache(soapElement, createClearCache());
        XmlAssert xmlAssert = XMLUtil.createAssert(message);

        xmlAssert.hasXPath("/SOAP:Envelope");
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/ClearCache")
            .exist();
        xmlAssert.nodesByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object")
            .exist();
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/ServerID")
            .isEqualTo("serverID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DatabaseID")
            .isEqualTo("databaseID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/RoleID")
            .isEqualTo("roleID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/TraceID")
            .isEqualTo("traceID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/AssemblyID")
            .isEqualTo("assemblyID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DimensionID")
            .isEqualTo("dimensionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DimensionPermissionID")
            .isEqualTo("dimensionPermissionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DataSourceID")
            .isEqualTo("dataSourceID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DataSourcePermissionID")
            .isEqualTo("dataSourcePermissionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DatabasePermissionID")
            .isEqualTo("databasePermissionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/DataSourceViewID")
            .isEqualTo("dataSourceViewID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/CubeID")
            .isEqualTo("cubeID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/MiningStructureID")
            .isEqualTo("miningStructureID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/MeasureGroupID")
            .isEqualTo("measureGroupID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/PerspectiveID")
            .isEqualTo("perspectiveID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/CubePermissionID")
            .isEqualTo("cubePermissionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/MdxScriptID")
            .isEqualTo("mdxScriptID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/PartitionID")
            .isEqualTo("partitionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/AggregationDesignID")
            .isEqualTo("aggregationDesignID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/MiningModelID")
            .isEqualTo("miningModelID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/MiningModelPermissionID")
            .isEqualTo("miningModelPermissionID");
        xmlAssert.valueByXPath("/SOAP:Envelope/SOAP:Body/ClearCache/Object/MiningStructurePermissionID")
            .isEqualTo("miningStructurePermissionID");
    }

    @Test
    void addChildElementMajorObjectTest() throws Exception {
        SoapUtil.addChildElementMajorObject(soapElement, createMajorObject());
        XmlAssert xmlAssert = XMLUtil.createAssert(message);
        checkMajorObject(xmlAssert, "/SOAP:Envelope/SOAP:Body");

    }

    private void checkMajorObject(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/ObjectDefinition").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        checkAggregationDesign(xmlAssert, p);
        checkAssembly(xmlAssert, p);
        checkCube(xmlAssert, p);
        checkDatabase(xmlAssert, p);
        checkDataSource(xmlAssert, p);
        checkDataSourceView(xmlAssert, p);
        checkDimension(xmlAssert, p);
        checkMdxScript(xmlAssert, p);
        checkMeasureGroup(xmlAssert, p);
        checkMiningModel(xmlAssert, p);
        checkMiningStructure(xmlAssert, p);
        checkPartition(xmlAssert, p);
        checkPermission(xmlAssert, p);
        checkPerspective(xmlAssert, p);
        checkRole(xmlAssert, p);
        checkServer(xmlAssert, p);
        checkTrace(xmlAssert, p);
    }

    private MajorObject createMajorObject() {
        MajorObject it = mock(MajorObject.class);
        AggregationDesign aggregationDesign = createAggregationDesign();
        Assembly assembly = createAssembly();
        Cube cube = createCube();
        Database database = createDatabase();
        DataSource dataSource = createDataSource();
        DataSourceView dataSourceView = createDataSourceView();
        Dimension dimension = createDimension();
        MdxScript mdxScript = createMdxScript();
        MeasureGroup measureGroup = createMeasureGroup();
        MiningModel miningModel = createMiningModel();
        MiningStructure miningStructure = createMiningStructure();
        Partition partition = createPartition();
        Permission permission = createPermission();
        Perspective perspective = createPerspective();
        Role role = createRole();
        Server server = createServer();
        Trace trace = createTrace();
        when(it.aggregationDesign()).thenReturn(aggregationDesign);
        when(it.assembly()).thenReturn(assembly);
        when(it.cube()).thenReturn(cube);
        when(it.database()).thenReturn(database);
        when(it.dataSource()).thenReturn(dataSource);
        when(it.dataSourceView()).thenReturn(dataSourceView);
        when(it.dimension()).thenReturn(dimension);
        when(it.mdxScript()).thenReturn(mdxScript);
        when(it.measureGroup()).thenReturn(measureGroup);
        when(it.miningModel()).thenReturn(miningModel);
        when(it.miningStructure()).thenReturn(miningStructure);
        when(it.partition()).thenReturn(partition);
        when(it.permission()).thenReturn(permission);
        when(it.perspective()).thenReturn(perspective);
        when(it.role()).thenReturn(role);
        when(it.server()).thenReturn(server);
        when(it.trace()).thenReturn(trace);
        return it;
    }

    private Server createServer() {
        Server server = mock(Server.class);

        when(server.name()).thenReturn("name");
        when(server.id()).thenReturn("id");
        when(server.createdTimestamp()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(server.lastSchemaUpdate()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(server.description()).thenReturn("description");
        when(server.annotations()).thenReturn(annotations);
        when(server.productName()).thenReturn("productName");
        when(server.edition()).thenReturn("edition");
        when(server.editionID()).thenReturn(10l);
        when(server.version()).thenReturn("version");
        when(server.serverMode()).thenReturn("serverMode");
        when(server.productLevel()).thenReturn("productLevel");
        when(server.defaultCompatibilityLevel()).thenReturn(11l);
        when(server.supportedCompatibilityLevels()).thenReturn("supportedCompatibilityLevels");
        List<Database> databases = List.of(createDatabase());
        when(server.databases()).thenReturn(databases);
        List<Assembly> assemblies = List.of(createAssembly());
        when(server.assemblies()).thenReturn(assemblies);
        List<Trace> traces = List.of(createTrace());
        when(server.traces()).thenReturn(traces);
        List<Role> roles = List.of(createRole());
        when(server.roles()).thenReturn(roles);
        List<ServerProperty> serverProperties = List.of(createServerProperty());
        when(server.serverProperties()).thenReturn(serverProperties);
        return server;
    }

    private AggregationDesign createAggregationDesign() {
        AggregationDesign it = mock(AggregationDesign.class);

        when(it.name()).thenReturn("name");
        when(it.id()).thenReturn(Optional.of("id"));
        when(it.createdTimestamp()).thenReturn(Optional.of(Instant.parse("2024-01-10T10:45:00Z")));
        when(it.lastSchemaUpdate()).thenReturn(Optional.of(Instant.parse("2024-01-10T10:45:00Z")));
        when(it.description()).thenReturn(Optional.of("description"));
        Optional<List<Annotation>> annotations = Optional.of(this.annotations);
        when(it.annotations()).thenReturn(annotations);
        when(it.estimatedRows()).thenReturn(Optional.of(10l));
        Optional<List<AggregationDesignDimension>> dimensions =
            Optional.of(List.of(createAggregationDesignDimension()));
        when(it.dimensions()).thenReturn(dimensions);
        Optional<List<Aggregation>> aggregations = Optional.of(List.of(createAggregation()));
        when(it.aggregations()).thenReturn(aggregations);
        when(it.estimatedPerformanceGain()).thenReturn(Optional.of(11));
        return it;
    }

    private AggregationDesignDimension createAggregationDesignDimension() {
        AggregationDesignDimension it = mock(AggregationDesignDimension.class);
        when(it.cubeDimensionID()).thenReturn("cubeDimensionID");
        Optional<List<AggregationDesignAttribute>> attributes =
            Optional.of(List.of(createAggregationDesignAttribute()));
        when(it.attributes()).thenReturn(attributes);
        Optional<List<Annotation>> annotations = Optional.ofNullable(this.annotations);
        when(it.annotations()).thenReturn(annotations);
        return it;
    }

    private AggregationDesignAttribute createAggregationDesignAttribute() {
        AggregationDesignAttribute it = mock(AggregationDesignAttribute.class);
        when(it.attributeID()).thenReturn("attributeID");
        when(it.estimatedCount()).thenReturn(Optional.of(10l));
        return it;
    }

    private Aggregation createAggregation() {
        Aggregation it = mock(Aggregation.class);
        when(it.id()).thenReturn(Optional.of("id"));
        when(it.name()).thenReturn("name");
        Optional<List<AggregationDimension>> dimensions = Optional.of(List.of(createAggregationDimension()));
        when(it.dimensions()).thenReturn(dimensions);
        Optional<List<Annotation>> annotations = Optional.ofNullable(this.annotations);
        when(it.annotations()).thenReturn(annotations);
        when(it.description()).thenReturn(Optional.of("description"));
        return it;
    }

    private AggregationDimension createAggregationDimension() {
        AggregationDimension it = mock(AggregationDimension.class);
        when(it.cubeDimensionID()).thenReturn("cubeDimensionID");
        Optional<List<AggregationAttribute>> attributes = Optional.of(List.of(createAggregationAttribute()));
        when(it.attributes()).thenReturn(attributes);
        Optional<List<Annotation>> annotations = Optional.of(this.annotations);
        when(it.annotations()).thenReturn(annotations);
        return it;
    }

    private AggregationAttribute createAggregationAttribute() {
        AggregationAttribute it = mock(AggregationAttribute.class);
        when(it.attributeID()).thenReturn("attributeID");
        Optional<List<Annotation>> annotations = Optional.of(this.annotations);
        when(it.annotations()).thenReturn(annotations);
        return it;
    }

    private Cube createCube() {
        Cube it = mock(Cube.class);
        when(it.name()).thenReturn("name");
        when(it.id()).thenReturn("id");
        when(it.createdTimestamp()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(it.lastSchemaUpdate()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(it.description()).thenReturn("description");
        List<Annotation> annotations = createAnnotationList();
        when(it.annotations()).thenReturn(annotations);
        when(it.language()).thenReturn(BigInteger.ONE);
        when(it.collation()).thenReturn("collation");
        List<Translation> translations = List.of(createTranslation());
        when(it.translations()).thenReturn(translations);
        List<CubeDimension> dimensions = List.of(createCubeDimension());
        when(it.dimensions()).thenReturn(dimensions);
        List<CubePermission> cubePermissions = List.of(createCubePermission());
        when(it.cubePermissions()).thenReturn(cubePermissions);
        List<MdxScript> mdxScripts = List.of(createMdxScript());
        when(it.mdxScripts()).thenReturn(mdxScripts);
        List<Perspective> perspectives = List.of(createPerspective());
        when(it.perspectives()).thenReturn(perspectives);
        when(it.state()).thenReturn("state");
        when(it.defaultMeasure()).thenReturn("defaultMeasure");
        when(it.visible()).thenReturn(true);
        List<MeasureGroup> measureGroups = List.of(createMeasureGroup());
        when(it.measureGroups()).thenReturn(measureGroups);
        DataSourceViewBinding source = createDataSourceViewBinding();
        when(it.source()).thenReturn(source);
        when(it.aggregationPrefix()).thenReturn("aggregationPrefix");
        when(it.processingPriority()).thenReturn(BigInteger.TEN);
        Cube.StorageMode storageMode = createCubeStorageMode();
        when(it.storageMode()).thenReturn(storageMode);
        when(it.processingMode()).thenReturn("processingMode");
        when(it.scriptCacheProcessingMode()).thenReturn("scriptCacheProcessingMode");
        when(it.scriptErrorHandlingMode()).thenReturn("scriptErrorHandlingMode");
        when(it.daxOptimizationMode()).thenReturn("daxOptimizationMode");
        ProactiveCaching proactiveCaching = createProactiveCaching();
        when(it.proactiveCaching()).thenReturn(proactiveCaching);
        List<Kpi> kpis = List.of(createKpi());
        when(it.kpis()).thenReturn(kpis);
        ErrorConfiguration errorConfiguration = createErrorConfiguration();
        when(it.errorConfiguration()).thenReturn(errorConfiguration);
        List<Action> actions = List.of(createAction());
        when(it.actions()).thenReturn(actions);
        when(it.storageLocation()).thenReturn("storageLocation");
        when(it.estimatedRows()).thenReturn(10l);
        when(it.lastProcessed()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        return it;
    }

    private Action createAction() {
        Action it = mock(Action.class);
        when(it.name()).thenReturn("name");
        when(it.id()).thenReturn(Optional.of("id"));
        when(it.caption()).thenReturn(Optional.of("caption"));
        when(it.captionIsMdx()).thenReturn(Optional.of(true));
        Optional<List<Translation>> translations = Optional.of(List.of(createTranslation()));
        when(it.translations()).thenReturn(translations);
        when(it.targetType()).thenReturn(TargetTypeEnum.CUBE);
        when(it.target()).thenReturn(Optional.of("target"));
        when(it.condition()).thenReturn(Optional.of("condition"));
        when(it.type()).thenReturn(TypeEnum.REPORT);
        when(it.invocation()).thenReturn(Optional.of("invocation"));
        when(it.application()).thenReturn(Optional.of("application"));
        when(it.description()).thenReturn(Optional.of("description"));
        when(it.annotations()).thenReturn(Optional.of(annotations));
        return it;
    }

    private ErrorConfiguration createErrorConfiguration() {
        ErrorConfiguration it = mock(ErrorConfiguration.class);
        when(it.keyErrorLimit()).thenReturn(Optional.of(10l));
        when(it.keyErrorLogFile()).thenReturn(Optional.of("keyErrorLogFile"));
        when(it.keyErrorAction()).thenReturn(Optional.of("keyErrorAction"));
        when(it.keyErrorLimitAction()).thenReturn(Optional.of("keyErrorLimitAction"));
        when(it.keyNotFound()).thenReturn(Optional.of("keyNotFound"));
        when(it.keyDuplicate()).thenReturn(Optional.of("keyDuplicate"));
        when(it.nullKeyConvertedToUnknown()).thenReturn(Optional.of("nullKeyConvertedToUnknown"));
        when(it.nullKeyNotAllowed()).thenReturn(Optional.of("nullKeyNotAllowed"));
        when(it.calculationError()).thenReturn(Optional.of("calculationError"));
        return it;
    }

    private Kpi createKpi() {
        Kpi it = mock(Kpi.class);
        when(it.name()).thenReturn("name");
        when(it.id()).thenReturn("id");
        when(it.description()).thenReturn("description");
        List<Translation> translations = List.of(createTranslation());
        when(it.translations()).thenReturn(translations);
        when(it.displayFolder()).thenReturn("displayFolder");
        when(it.associatedMeasureGroupID()).thenReturn("associatedMeasureGroupID");
        when(it.value()).thenReturn("value");
        when(it.goal()).thenReturn("goal");
        when(it.status()).thenReturn("status");
        when(it.trend()).thenReturn("trend");
        when(it.weight()).thenReturn("weight");
        when(it.trendGraphic()).thenReturn("trendGraphic");
        when(it.statusGraphic()).thenReturn("statusGraphic");
        when(it.currentTimeMember()).thenReturn("currentTimeMember");
        when(it.parentKpiID()).thenReturn("parentKpiID");
        when(it.annotations()).thenReturn(annotations);
        return it;
    }

    private ProactiveCaching createProactiveCaching() {
        ProactiveCaching it = mock(ProactiveCaching.class);
        when(it.onlineMode()).thenReturn(Optional.of("onlineMode"));
        when(it.aggregationStorage()).thenReturn(Optional.of("aggregationStorage"));
        Optional<ProactiveCachingBinding> source = Optional.of(createProactiveCachingIncrementalProcessingBinding());
        when(it.source()).thenReturn(source);
        when(it.silenceInterval()).thenReturn(Optional.of(Duration.ofDays(10l)));
        when(it.latency()).thenReturn(Optional.of(Duration.ofDays(11l)));
        when(it.silenceOverrideInterval()).thenReturn(Optional.of(Duration.ofDays(12l)));
        when(it.forceRebuildInterval()).thenReturn(Optional.of(Duration.ofDays(14l)));
        when(it.enabled()).thenReturn(Optional.of(true));
        return it;
    }

    private ProactiveCachingIncrementalProcessingBinding createProactiveCachingIncrementalProcessingBinding() {
        ProactiveCachingIncrementalProcessingBinding it = mock(ProactiveCachingIncrementalProcessingBinding.class);
        when(it.refreshInterval()).thenReturn(Optional.of(Duration.ofDays(10l)));
        List<IncrementalProcessingNotification> incrementalProcessingNotifications = List.of(createIncrementalProcessingNotification());
        when(it.incrementalProcessingNotifications()).thenReturn(incrementalProcessingNotifications);
        return it;
    }

    private IncrementalProcessingNotification createIncrementalProcessingNotification() {
        IncrementalProcessingNotification it = mock(IncrementalProcessingNotification.class);
        when(it.tableID()).thenReturn("tableID");
        when(it.processingQuery()).thenReturn("processingQuery");
        return it;
    }

    private Cube.StorageMode createCubeStorageMode() {
        Cube.StorageMode it = mock(Cube.StorageMode.class);
        //TODO
        return it;
    }

    private DataSourceViewBinding createDataSourceViewBinding() {
        DataSourceViewBinding it = mock(DataSourceViewBinding.class);
        //TODO
        return it;
    }

    private CubePermission createCubePermission() {
        CubePermission it = mock(CubePermission.class);
        //TODO
        return it;
    }

    private CubeDimension createCubeDimension() {
        CubeDimension it = mock(CubeDimension.class);
        //TODO
        return it;
    }

    private Translation createTranslation() {
        Translation it = mock(Translation.class);
        //TODO
        return it;
    }

    private DataSource createDataSource() {
        DataSource it = mock(DataSource.class);
        //TODO
        return it;
    }

    private DataSourceView createDataSourceView() {
        DataSourceView it = mock(DataSourceView.class);
        when(it.name()).thenReturn("name");
        when(it.id()).thenReturn("id");
        when(it.createdTimestamp()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(it.lastSchemaUpdate()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(it.description()).thenReturn("description");
        when(it.annotations()).thenReturn(this.annotations);
        when(it.dataSourceID()).thenReturn("dataSourceID");
        return it;
    }

    private Dimension createDimension() {
        Dimension it = mock(Dimension.class);
        //TODO
        return it;
    }

    private MdxScript createMdxScript() {
        MdxScript it = mock(MdxScript.class);
        //TODO
        return it;
    }

    private MeasureGroup createMeasureGroup() {
        MeasureGroup it = mock(MeasureGroup.class);
        //TODO
        return it;
    }

    private MiningModel createMiningModel() {
        MiningModel it = mock(MiningModel.class);
        //TODO
        return it;
    }

    private MiningStructure createMiningStructure() {
        MiningStructure it = mock(MiningStructure.class);
        //TODO
        return it;
    }

    private Partition createPartition() {
        Partition it = mock(Partition.class);
        //TODO
        return it;
    }

    private Permission createPermission() {
        Permission it = mock(Permission.class);
        //TODO
        return it;
    }

    private Perspective createPerspective() {
        Perspective it = mock(Perspective.class);
        //TODO
        return it;
    }

    private ServerProperty createServerProperty() {
        ServerProperty it = mock(ServerProperty.class);
        when(it.name()).thenReturn("name");
        when(it.value()).thenReturn("value");
        when(it.requiresRestart()).thenReturn(true);
        when(it.pendingValue()).thenReturn("pendingValue");
        when(it.defaultValue()).thenReturn("defaultValue");
        when(it.displayFlag()).thenReturn(true);
        when(it.type()).thenReturn("type");
        return it;
    }

    private Role createRole() {
        Role role = mock(Role.class);
        //TODO
        return role;
    }

    private Assembly createAssembly() {
        Assembly it = mock(Assembly.class);
        when(it.id()).thenReturn("id");
        when(it.name()).thenReturn("name");
        when(it.createdTimestamp()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(it.lastSchemaUpdate()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(it.description()).thenReturn("description");
        List<Annotation> annotations = this.annotations;
        when(it.annotations()).thenReturn(annotations);
        ImpersonationInfo impersonationInfo = createImpersonationInfo();
        when(it.impersonationInfo()).thenReturn(impersonationInfo);
        return it;
    }

    private ImpersonationInfo createImpersonationInfo() {
        ImpersonationInfo it = mock(ImpersonationInfo.class);
        when(it.impersonationMode()).thenReturn("impersonationMode");
        when(it.account()).thenReturn(Optional.of("account"));
        when(it.password()).thenReturn(Optional.of("password"));
        when(it.impersonationInfoSecurity()).thenReturn(Optional.of("impersonationInfoSecurity"));
        return it;
    }

    private Database createDatabase() {
        Database it = mock(Database.class);
        when(it.name()).thenReturn("name");
        when(it.id()).thenReturn("id");
        when(it.createdTimestamp()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(it.lastSchemaUpdate()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(it.description()).thenReturn("description");
        when(it.annotations()).thenReturn(annotations);
        when(it.lastUpdate()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(it.state()).thenReturn("state");
        when(it.readWriteMode()).thenReturn("readWriteMode");
        when(it.dbStorageLocation()).thenReturn("dbStorageLocation");
        when(it.aggregationPrefix()).thenReturn("aggregationPrefix");
        when(it.processingPriority()).thenReturn(BigInteger.ONE);
        when(it.estimatedSize()).thenReturn(10l);
        when(it.lastProcessed()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(it.language()).thenReturn(BigInteger.TEN);
        when(it.collation()).thenReturn("collation");
        when(it.visible()).thenReturn(true);
        when(it.masterDataSourceID()).thenReturn("masterDataSourceID");
        ImpersonationInfo dataSourceImpersonationInfo = createImpersonationInfo();
        when(it.dataSourceImpersonationInfo()).thenReturn(dataSourceImpersonationInfo);
        List<Account> accounts = List.of(createAccount());
        when(it.accounts()).thenReturn(accounts);
        List<DataSource> dataSources = List.of(createDataSource());
        when(it.dataSources()).thenReturn(dataSources);
        List<DataSourceView> dataSourceViews = List.of(createDataSourceView());
        when(it.dataSourceViews()).thenReturn(dataSourceViews);
        List<Dimension> dimensions = List.of(createDimension());
        when(it.dimensions()).thenReturn(dimensions);
        List<Cube> cubes = List.of(createCube());
        when(it.cubes()).thenReturn(cubes);
        List<MiningStructure> miningStructures = List.of(createMiningStructure());
        when(it.miningStructures()).thenReturn(miningStructures);
        List<Role> roles = List.of(createRole());
        when(it.roles()).thenReturn(roles);
        List<Assembly> assemblies = List.of(createAssembly());
        when(it.assemblies()).thenReturn(assemblies);
        List<DatabasePermission> databasePermissions = List.of(createDatabasePermission());
        when(it.databasePermissions()).thenReturn(databasePermissions);
        List<Translation> translations = List.of(createTranslation());
        when(it.translations()).thenReturn(translations);
        when(it.storageEngineUsed()).thenReturn("storageEngineUsed");
        when(it.imagePath()).thenReturn("imagePath");
        when(it.imageUrl()).thenReturn("imageUrl");
        when(it.imageUniqueID()).thenReturn("imageUniqueID");
        when(it.imageVersion()).thenReturn("imageVersion");
        when(it.token()).thenReturn("token");
        when(it.compatibilityLevel()).thenReturn(BigInteger.TWO);
        when(it.directQueryMode()).thenReturn("directQueryMode");
        return it;
    }

    private DatabasePermission createDatabasePermission() {
        DatabasePermission it = mock(DatabasePermission.class);
        //TODO
        return it;
    }

    private Account createAccount() {
        Account it = mock(Account.class);
        when(it.accountType()).thenReturn("accountType");
        when(it.aggregationFunction()).thenReturn("aggregationFunction");
        when(it.aliases()).thenReturn(List.of("alias"));
        when(it.annotations()).thenReturn(annotations);
        return it;
    }

    private Trace createTrace() {
        Trace trace = mock(Trace.class);
        when(trace.name()).thenReturn(NAME);
        when(trace.id()).thenReturn(ID);
        when(trace.createdTimestamp()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(trace.lastSchemaUpdate()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        when(trace.description()).thenReturn(DESCRIPTION);
        when(trace.annotations()).thenReturn(this.annotations);
        when(trace.logFileName()).thenReturn("LogFileName");
        when(trace.logFileAppend()).thenReturn(true);
        when(trace.logFileSize()).thenReturn(10L);
        when(trace.audit()).thenReturn(true);
        when(trace.logFileRollover()).thenReturn(true);
        when(trace.autoRestart()).thenReturn(true);
        when(trace.stopTime()).thenReturn(Instant.parse("2024-01-10T10:45:00Z"));
        TraceFilter traceFilter = createTraceFilter();
        when(trace.filter()).thenReturn(traceFilter);
        EventType eventType = createEventType();
        when(trace.eventType()).thenReturn(eventType);
        return trace;
    }

    private ObjectReference createObjectReference() {
        ObjectReference objectReference = mock(ObjectReference.class);
        when(objectReference.serverID()).thenReturn("serverID");
        when(objectReference.databaseID()).thenReturn("databaseID");
        when(objectReference.roleID()).thenReturn("roleID");
        when(objectReference.traceID()).thenReturn("traceID");
        when(objectReference.assemblyID()).thenReturn("assemblyID");
        when(objectReference.dimensionID()).thenReturn("dimensionID");
        when(objectReference.dimensionPermissionID()).thenReturn("dimensionPermissionID");
        when(objectReference.dataSourceID()).thenReturn("dataSourceID");
        when(objectReference.dataSourcePermissionID()).thenReturn("dataSourcePermissionID");
        when(objectReference.databasePermissionID()).thenReturn("databasePermissionID");
        when(objectReference.dataSourceViewID()).thenReturn("dataSourceViewID");
        when(objectReference.cubeID()).thenReturn("cubeID");
        when(objectReference.miningStructureID()).thenReturn("miningStructureID");
        when(objectReference.measureGroupID()).thenReturn("measureGroupID");
        when(objectReference.perspectiveID()).thenReturn("perspectiveID");
        when(objectReference.cubePermissionID()).thenReturn("cubePermissionID");
        when(objectReference.mdxScriptID()).thenReturn("mdxScriptID");
        when(objectReference.partitionID()).thenReturn("partitionID");
        when(objectReference.aggregationDesignID()).thenReturn("aggregationDesignID");
        when(objectReference.miningModelID()).thenReturn("miningModelID");
        when(objectReference.miningModelPermissionID()).thenReturn("miningModelPermissionID");
        when(objectReference.miningStructurePermissionID()).thenReturn("miningStructurePermissionID");

        return objectReference;
    }

    private ClearCache createClearCache() {
        ClearCache clearCache = mock(ClearCache.class);
        ObjectReference objectReference = createObjectReference();
        when(clearCache.object()).thenReturn(objectReference);
        return clearCache;
    }

    private void checkEventType(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/EventType")
            .exist();
        String eventPath = new StringBuilder(path).append("/EventType/Events").toString();
        xmlAssert.nodesByXPath(eventPath)
            .exist();
        String xEventPath = new StringBuilder(path).append("/EventType/XEvent").toString();
        xmlAssert.nodesByXPath(xEventPath)
            .exist();
        checkEvent(xmlAssert, eventPath);
        checkEventSession(xmlAssert, xEventPath);

    }

    private void checkEvent(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/Event")
            .exist();
        xmlAssert.valueByXPath(path + "/Event/EventID")
            .isEqualTo("EventID");
        checkEventEventColumnID(xmlAssert, path + "/Event");
    }

    private void checkEventEventColumnID(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/Columns")
            .exist();
        xmlAssert.valueByXPath(path + "/Columns/ColumnID")
            .isEqualTo("columnID1");

    }

    private void checkEventSession(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/event_session")
            .exist();
        xmlAssert.valueByXPath(path + "/event_session/templateCategory")
            .isEqualTo("templateCategory");
        xmlAssert.valueByXPath(path + "/event_session/templateName")
            .isEqualTo("templateName");
        xmlAssert.valueByXPath(path + "/event_session/templateDescription")
            .isEqualTo("templateDescription");
        checkObjectList(xmlAssert, path + "/event_session/event");
        checkObjectList(xmlAssert, path + "/event_session/target");
        xmlAssert.valueByXPath(path + "/event_session/name")
            .isEqualTo("name");
        xmlAssert.valueByXPath(path + "/event_session/maxMemory")
            .isEqualTo("1");
        xmlAssert.valueByXPath(path + "/event_session/eventRetentionMode")
            .isEqualTo("allowSingleEventLoss");
        xmlAssert.valueByXPath(path + "/event_session/dispatchLatency")
            .isEqualTo("10");
        xmlAssert.valueByXPath(path + "/event_session/maxEventSize")
            .isEqualTo("11");
        xmlAssert.valueByXPath(path + "/event_session/memoryPartitionMode")
            .isEqualTo("allowSingleEventLoss");
        xmlAssert.valueByXPath(path + "/event_session/trackCausality")
            .isEqualTo(true);
    }

    private void checkObjectList(XmlAssert xmlAssert, String path) {
        xmlAssert.valueByXPath(path)
            .isEqualTo("Object1");
    }

    private void checkTraceFilter(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/Filter")
            .exist();
        checkNotType(xmlAssert, path + "/Filter/Not");
        checkAndOrType(xmlAssert, path + "/Filter/Or");
        checkAndOrType(xmlAssert, path + "/Filter/And");
        checkBoolBinop(xmlAssert, path + "/Filter/Equal");
        checkBoolBinop(xmlAssert, path + "/Filter/NotEqual");
        checkBoolBinop(xmlAssert, path + "/Filter/Less");
        checkBoolBinop(xmlAssert, path + "/Filter/LessOrEqual");
        checkBoolBinop(xmlAssert, path + "/Filter/Greater");
        checkBoolBinop(xmlAssert, path + "/Filter/GreaterOrEqual");
        checkBoolBinop(xmlAssert, path + "/Filter/Like");
        checkBoolBinop(xmlAssert, path + "/Filter/NotLike");
    }

    private void checkNotType(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path)
            .exist();
        checkAndOrType(xmlAssert, path + "/Or");
        checkAndOrType(xmlAssert, path + "/And");
        checkBoolBinop(xmlAssert, path + "/Equal");
        checkBoolBinop(xmlAssert, path + "/NotEqual");
        checkBoolBinop(xmlAssert, path + "/Less");
        checkBoolBinop(xmlAssert, path + "/LessOrEqual");
        checkBoolBinop(xmlAssert, path + "/Greater");
        checkBoolBinop(xmlAssert, path + "/GreaterOrEqual");
        checkBoolBinop(xmlAssert, path + "/Like");
        checkBoolBinop(xmlAssert, path + "/NotLike");
    }

    private void checkAndOrType(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path)
            .exist();
        xmlAssert.nodesByXPath(path + "/And")
            .exist();
    }

    private void checkBoolBinop(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path)
            .exist();
        xmlAssert.valueByXPath(path + "/ColumnID")
            .isEqualTo("columnID");
        xmlAssert.valueByXPath(path + "/Value")
            .isEqualTo("value");
    }

    private void checkAnnotationList(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/Annotations")
            .exist();
        xmlAssert.valueByXPath(path + "/Annotations/Annotation/Name")
            .isEqualTo(NAME);
        xmlAssert.valueByXPath(path + "/Annotations/Annotation/Visibility")
            .isEqualTo("Visibility");
        xmlAssert.valueByXPath(path + "/Annotations/Annotation/Value")
            .isEqualTo(VALUE2);
    }

    private List<Annotation> createAnnotationList() {
        Annotation annotation1 = mock(Annotation.class);
        when(annotation1.name()).thenReturn(NAME);
        when(annotation1.value()).thenReturn(Optional.of(VALUE2));
        when(annotation1.visibility()).thenReturn(Optional.of("Visibility"));
        return List.of(annotation1);
    }

    private Cancel createCancel() {
        Cancel cancel = mock(Cancel.class);
        when(cancel.connectionID()).thenReturn(BigInteger.ONE);
        when(cancel.sessionID()).thenReturn("SessionID");
        when(cancel.spid()).thenReturn(BigInteger.TEN);
        when(cancel.cancelAssociated()).thenReturn(true);
        return cancel;
    }

    private EventType createEventType() {
        EventType eventType = mock(EventType.class);
        List<Event> l = createEventList();
        when(eventType.events()).thenReturn(l);
        XEvent xEvent = createXEvent();
        when(eventType.xEvent()).thenReturn(xEvent);
        return eventType;
    }

    private XEvent createXEvent() {
        XEvent xEvent = mock(XEvent.class);
        EventSession eventSession = createEventSession();
        when(xEvent.eventSession()).thenReturn(eventSession);
        return xEvent;
    }

    private EventSession createEventSession() {
        EventSession eventSession = mock(EventSession.class);
        when(eventSession.templateCategory()).thenReturn("templateCategory");
        when(eventSession.templateName()).thenReturn("templateName");
        when(eventSession.templateDescription()).thenReturn("templateDescription");
        when(eventSession.event()).thenReturn(createListObject());
        when(eventSession.target()).thenReturn(createListObject());
        when(eventSession.name()).thenReturn("name");
        when(eventSession.maxMemory()).thenReturn(BigInteger.ONE);
        when(eventSession.eventRetentionMode()).thenReturn(RetentionModes.ALLOW_SINGLE_EVENT_LOSS);
        when(eventSession.dispatchLatency()).thenReturn(10l);
        when(eventSession.maxEventSize()).thenReturn(11l);
        when(eventSession.memoryPartitionMode()).thenReturn(PartitionModes.NONE);
        when(eventSession.trackCausality()).thenReturn(true);
        return eventSession;
    }

    private List<Object> createListObject() {
        return List.of("Object1", "Object2");
    }

    private List<Event> createEventList() {
        Event event = createEvent();
        return List.of(event);
    }

    private Event createEvent() {
        Event event = mock(Event.class);
        when(event.eventID()).thenReturn("EventID");
        EventColumnID ecid = createEventColumnID();
        when(event.columns()).thenReturn(ecid);
        return event;
    }

    private EventColumnID createEventColumnID() {
        EventColumnID eventColumnID = mock(EventColumnID.class);
        when(eventColumnID.columnID()).thenReturn(List.of("columnID1", "columnID2"));
        return eventColumnID;
    }

    TraceFilter createTraceFilter() {
        TraceFilter traceFilter = mock(TraceFilter.class);
        NotType not = createNotType();
        AndOrType or = createAndOrType();
        AndOrType and = or;
        BoolBinop isEqual = createBoolBinop();
        BoolBinop notEqual = isEqual;
        BoolBinop less = isEqual;
        BoolBinop lessOrEqual = isEqual;
        BoolBinop greater = isEqual;
        BoolBinop greaterOrEqual = isEqual;
        BoolBinop like = isEqual;
        BoolBinop notLike = isEqual;
        when(traceFilter.not()).thenReturn(not);
        when(traceFilter.or()).thenReturn(or);
        when(traceFilter.and()).thenReturn(and);
        when(traceFilter.isEqual()).thenReturn(isEqual);
        when(traceFilter.notEqual()).thenReturn(notEqual);
        when(traceFilter.less()).thenReturn(less);
        when(traceFilter.lessOrEqual()).thenReturn(lessOrEqual);
        when(traceFilter.greater()).thenReturn(greater);
        when(traceFilter.greaterOrEqual()).thenReturn(greaterOrEqual);
        when(traceFilter.like()).thenReturn(like);
        when(traceFilter.notLike()).thenReturn(notLike);
        return traceFilter;
    }

    private BoolBinop createBoolBinop() {
        BoolBinop boolBinop = mock(BoolBinop.class);
        when(boolBinop.columnID()).thenReturn("columnID");
        when(boolBinop.value()).thenReturn("value");
        return boolBinop;
    }

    private NotType createNotType() {
        NotType notType = mock(NotType.class);
        AndOrType or = createAndOrType();
        AndOrType and = or;
        BoolBinop isEqual = createBoolBinop();
        BoolBinop notEqual = isEqual;
        BoolBinop less = isEqual;
        BoolBinop lessOrEqual = isEqual;
        BoolBinop greater = isEqual;
        BoolBinop greaterOrEqual = isEqual;
        BoolBinop like = isEqual;
        BoolBinop notLike = isEqual;
        when(notType.or()).thenReturn(or);
        when(notType.and()).thenReturn(and);
        when(notType.isEqual()).thenReturn(isEqual);
        when(notType.notEqual()).thenReturn(notEqual);
        when(notType.less()).thenReturn(less);
        when(notType.lessOrEqual()).thenReturn(lessOrEqual);
        when(notType.greater()).thenReturn(greater);
        when(notType.greaterOrEqual()).thenReturn(greaterOrEqual);
        when(notType.like()).thenReturn(like);
        when(notType.notLike()).thenReturn(notLike);
        return notType;
    }

    private AndOrType createAndOrType() {
        AndOrType andOrType = mock(AndOrType.class);
        List l = List.of(AndOrTypeEnum.And);
        when(andOrType.notOrOrOrAnd()).thenReturn(l);
        return andOrType;
    }

    private ExecuteParameter createExecuteParameter() {
        ExecuteParameter executeParameter = mock(ExecuteParameter.class);
        when(executeParameter.value()).thenReturn(VALUE2);
        when(executeParameter.name()).thenReturn(NAME_LC);
        return executeParameter;
    }


    private void checkServer(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Server").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        //TODO
    }

    private void checkRole(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Role").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        //TODO
    }

    private void checkPerspective(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Perspective").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        //TODO
    }

    private void checkPermission(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Permission").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        //TODO
    }

    private void checkPartition(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Partition").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        //TODO
    }

    private void checkMiningStructure(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/MiningStructure").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        //TODO
    }

    private void checkMiningModel(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/MiningModel").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        //TODO
    }

    private void checkMeasureGroup(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/MeasureGroup").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        //TODO
    }

    private void checkMdxScript(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/MdxScript").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        //TODO
    }

    private void checkDimension(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Dimension").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        //TODO
    }

    private void checkDataSourceView(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/DataSourceView").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        checkAbstractItem(xmlAssert, p);
        xmlAssert.valueByXPath(p + "/DataSourceID")
            .isEqualTo("dataSourceID");
    }

    private void checkDataSource(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/DataSource").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        //TODO
    }

    private void checkDatabase(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Database").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        //TODO
    }

    private void checkCube(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Cube").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        //TODO
    }

    private void checkAssembly(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Assembly").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        checkAbstractItem(xmlAssert, p);
        checkImpersonationInfo(xmlAssert, p);
    }

    private void checkImpersonationInfo(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/ImpersonationInfo").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        xmlAssert.valueByXPath(p + "/ImpersonationMode")
            .isEqualTo("impersonationMode");
        xmlAssert.valueByXPath(p + "/Account")
            .isEqualTo("account");
        xmlAssert.valueByXPath(p + "/Password")
            .isEqualTo("password");
        xmlAssert.valueByXPath(p + "/ImpersonationInfoSecurity")
            .isEqualTo("impersonationInfoSecurity");
    }

    private void checkAbstractItem(XmlAssert xmlAssert, String p) {
        xmlAssert.valueByXPath(p + "/ID")
            .isEqualTo("id");
        xmlAssert.valueByXPath(p + "/Name")
            .isEqualTo("name");
        xmlAssert.valueByXPath(p + "/Description")
            .isEqualTo("description");
        checkAnnotationList(xmlAssert, p);
    }

    private void checkAggregationDesign(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/AggregationDesign").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        xmlAssert.valueByXPath(p + "/EstimatedRows")
            .isEqualTo("10");
        xmlAssert.nodesByXPath(p + "/Dimensions")
            .exist();
        checkAggregationDesignDimensionList(xmlAssert, p);
        checkAggregationList(xmlAssert, p);
        xmlAssert.valueByXPath(p + "/EstimatedPerformanceGain")
            .isEqualTo("11");
    }

    private void checkAggregationList(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/Aggregations")
            .exist();
        checkAggregation(xmlAssert, path + "/Aggregations");
    }

    private void checkAggregation(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Aggregation").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        xmlAssert.valueByXPath(p + "/ID")
            .isEqualTo("id");
        xmlAssert.valueByXPath(p + "/Name")
            .isEqualTo("name");
        xmlAssert.valueByXPath(p + "/Description")
            .isEqualTo("description");
        checkAnnotationList(xmlAssert, p);
        checkAggregationDimensionList(xmlAssert, p);
    }

    private void checkAggregationDimensionList(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/Dimensions")
            .exist();
        checkAggregationDimension(xmlAssert, path + "/Dimensions");
    }

    private void checkAggregationDimension(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Dimension").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        xmlAssert.valueByXPath(p + "/CubeDimensionID")
            .isEqualTo("cubeDimensionID");
        xmlAssert.valueByXPath(p + "/CubeDimensionID")
            .isEqualTo("cubeDimensionID");
        checkAggregationAttributeList(xmlAssert, p);
        checkAnnotationList(xmlAssert, p);
    }

    private void checkAggregationAttributeList(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Attributes").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        checkAggregationAttribute(xmlAssert, p);
    }

    private void checkAggregationAttribute(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Attribute").toString();
        xmlAssert.nodesByXPath(p);
        xmlAssert.valueByXPath(p + "/AttributeID")
            .isEqualTo("attributeID");
        checkAnnotationList(xmlAssert, p);
    }

    private void checkAggregationDesignDimensionList(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/Dimensions")
            .exist();
        checkAggregationDesignDimension(xmlAssert, path + "/Dimensions");
    }

    private void checkAggregationDesignDimension(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Dimension").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        xmlAssert.valueByXPath(p + "/CubeDimensionID")
            .isEqualTo("cubeDimensionID");
        checkAggregationDesignAttributeList(xmlAssert, p);
        checkAnnotationList(xmlAssert, p);
    }

    private void checkAggregationDesignAttributeList(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Attributes").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        checkAggregationDesignAttribute(xmlAssert, p);
    }

    private void checkAggregationDesignAttribute(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Attribute").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        xmlAssert.valueByXPath(p + "/AttributeID")
            .isEqualTo("attributeID");
        xmlAssert.valueByXPath(p + "/EstimatedCount")
            .isEqualTo("10");
    }

    private void checkExecuteParameter(XmlAssert xmlAssert, String path) {
        String p = new StringBuilder(path).append("/Parameter").toString();
        xmlAssert.nodesByXPath(p)
            .exist();
        xmlAssert.valueByXPath(p + "/Name")
            .isEqualTo("Name");
        xmlAssert.valueByXPath(p + "/Value")
            .isEqualTo("Value");
    }

    private void checkTrace(XmlAssert xmlAssert, String path) {
        xmlAssert.nodesByXPath(path + "/Trace")
            .exist();
        xmlAssert.valueByXPath(path + "/Trace/NAME")
            .isEqualTo(NAME);
        xmlAssert.valueByXPath(path + "/Trace/ID")
            .isEqualTo(ID);
        xmlAssert.valueByXPath(path + "/Trace/CreatedTimestamp")
            .isEqualTo("2024-01-10T10:45:00Z");
        xmlAssert.valueByXPath(path + "/Trace/LastSchemaUpdate")
            .isEqualTo("2024-01-10T10:45:00Z");
        xmlAssert.valueByXPath(path + "/Trace/Description")
            .isEqualTo(DESCRIPTION);
        checkAnnotationList(xmlAssert, path + "/Trace");
        xmlAssert.valueByXPath(path + "/Trace/LogFileName")
            .isEqualTo("LogFileName");
        xmlAssert.valueByXPath(path + "/Trace/LogFileAppend")
            .isEqualTo("true");
        xmlAssert.valueByXPath(path + "/Trace/LogFileSize")
            .isEqualTo("10");
        xmlAssert.valueByXPath(path + "/Trace/Audit")
            .isEqualTo("true");
        xmlAssert.valueByXPath(path + "/Trace/LogFileRollover")
            .isEqualTo("true");
        xmlAssert.valueByXPath(path + "/Trace/AutoRestart")
            .isEqualTo("true");
        xmlAssert.valueByXPath(path + "/Trace/StopTime")
            .isEqualTo("2024-01-10T10:45:00Z");
        checkTraceFilter(xmlAssert, path + "/Trace");
        checkEventType(xmlAssert, path + "/Trace");
    }

}
