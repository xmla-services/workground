package org.opencube.junit5.context;

import org.eclipse.daanse.olap.core.BasicContextConfig;

public class TestConfig implements BasicContextConfig {
    private Integer cellBatchSize = -1;
    private Integer rolapConnectionShepherdNbThreads = 20;
    private String rolapConnectionShepherdThreadPollingInterval = "1000ms";
    private Integer segmentCacheManagerNumberSqlThreads = 100;
    private String solveOrderMode = "ABSOLUTE";
    private boolean chooseAggregateByVolume = false;
    private boolean disableCaching = false;
    private boolean disableLocalSegmentCache = false;
    private boolean enableGroupingSets = false;
    private boolean enableSessionCaching = false;
    private int compoundSlicerMemberSolveOrder = -99999;
    private boolean enableDrillThrough = true;
    private boolean enableNativeFilter = true;
    private boolean enableNativeCrossJoin = true;
    private boolean enableNativeNonEmpty = true;
    private boolean enableNativeTopCount = true;
    private boolean enableInMemoryRollup = true;
    private boolean expandNonNative = false;
    private boolean generateAggregateSql = false;
    private boolean ignoreInvalidMembersDuringQuery = false;
    private boolean ignoreMeasureForNonJoiningDimension = false;
    private int iterationLimit = 0;
    private int levelPreCacheThreshold = 300;
    private int maxConstraints = 1000;
    private int testExpDependencies = 0;
    private boolean readAggregates = false;
    private String aggregateRuleTag = "default";
    private String aggregateRules = "/DefaultRules.xml";
    private String alertNativeEvaluationUnsupported = "OFF";
    private int crossJoinOptimizerSize = 0;
    private String currentMemberWithCompoundSlicerAlert = "ERROR";
    private boolean ignoreInvalidMembers = false;
    private int maxEvalDepth = 10;
    private int checkCancelOrTimeoutInterval = 1000;
    private boolean memoryMonitor = false;
    private String warnIfNoPatternForDialect = "NONE";
    private boolean useAggregates = false;
    private int queryTimeout = 0;
    private boolean optimizePredicates = true;
    private boolean nullDenominatorProducesNull = false;
    private boolean needDimensionPrefix = false;
    private int nativizeMinThreshold = 100000;
    private int nativizeMaxResults = 150000;
    private int sparseSegmentCountThreshold = 1000;
    private double sparseSegmentDensityThreshold = 0.5;
    private int memoryMonitorThreshold = 90;
    private boolean generateFormattedSql = false;

    @Override
    public Integer cellBatchSize() {
        return cellBatchSize;
    }

    public void setCellBatchSize(Integer cellBatchSize) {
        this.cellBatchSize = cellBatchSize;
    }

    @Override
    public Integer rolapConnectionShepherdNbThreads() {
        return rolapConnectionShepherdNbThreads;
    }

    public void setRolapConnectionShepherdNbThreads(Integer rolapConnectionShepherdNbThreads) {
        this.rolapConnectionShepherdNbThreads = rolapConnectionShepherdNbThreads;
    }

    @Override
    public String rolapConnectionShepherdThreadPollingInterval() {
        return rolapConnectionShepherdThreadPollingInterval;
    }

    public void setRolapConnectionShepherdThreadPollingInterval(String rolapConnectionShepherdThreadPollingInterval) {
        this.rolapConnectionShepherdThreadPollingInterval = rolapConnectionShepherdThreadPollingInterval;
    }

    @Override
    public Integer segmentCacheManagerNumberSqlThreads() {
        return segmentCacheManagerNumberSqlThreads;
    }

    public void setSegmentCacheManagerNumberSqlThreads(Integer segmentCacheManagerNumberSqlThreads) {
        this.segmentCacheManagerNumberSqlThreads = segmentCacheManagerNumberSqlThreads;
    }

    public void setSolveOrderMode(String solveOrderMode) {
        this.solveOrderMode = solveOrderMode;
    }

    @Override
    public String solveOrderMode() {
        return solveOrderMode;
    }

    @Override
    public Boolean chooseAggregateByVolume() {
        return chooseAggregateByVolume;
    }

    public void setChooseAggregateByVolume(boolean chooseAggregateByVolume) {
        this.chooseAggregateByVolume = chooseAggregateByVolume;
    }

    public void setDisableCaching(boolean disableCaching) {
        this.disableCaching = disableCaching;
    }

    @Override
    public Boolean disableCaching() {
        return disableCaching;
    }

    @Override
    public Boolean disableLocalSegmentCache() {
        return disableLocalSegmentCache;
    }

    public void setDisableLocalSegmentCache(boolean disableLocalSegmentCache) {
        this.disableLocalSegmentCache = disableLocalSegmentCache;
    }

    public void setEnableGroupingSets(boolean enableGroupingSets) {
        this.enableGroupingSets = enableGroupingSets;
    }

    @Override
    public Boolean enableGroupingSets() {
        return enableGroupingSets;
    }

    @Override
    public Boolean enableSessionCaching() {
        return enableSessionCaching;
    }

    public void setEnableSessionCaching(boolean enableSessionCaching) {
        this.enableSessionCaching = enableSessionCaching;
    }

    public void setCompoundSlicerMemberSolveOrder(int compoundSlicerMemberSolveOrder) {
        this.compoundSlicerMemberSolveOrder = compoundSlicerMemberSolveOrder;
    }

    @Override
    public Integer compoundSlicerMemberSolveOrder() {
        return compoundSlicerMemberSolveOrder;
    }

    public void setEnableDrillThrough(boolean enableDrillThrough) {
        this.enableDrillThrough = enableDrillThrough;
    }

    @Override
    public Boolean enableDrillThrough() {
        return enableDrillThrough;
    }

    public void setEnableNativeFilter(boolean enableNativeFilter) {
        this.enableNativeFilter = enableNativeFilter;
    }

    @Override
    public Boolean enableNativeFilter() {
        return enableNativeFilter;
    }

    public void setEnableNativeCrossJoin(boolean enableNativeCrossJoin) {
        this.enableNativeCrossJoin = enableNativeCrossJoin;
    }

    @Override
    public Boolean enableNativeCrossJoin() {
        return enableNativeCrossJoin;
    }

    public void setEnableNativeNonEmpty(boolean enableNativeNonEmpty) {
        this.enableNativeNonEmpty = enableNativeNonEmpty;
    }

    @Override
    public Boolean enableNativeNonEmpty() {
        return enableNativeNonEmpty;
    }

    public void setEnableNativeTopCount(boolean enableNativeTopCount) {
        this.enableNativeTopCount = enableNativeTopCount;
    }

    @Override
    public Boolean enableNativeTopCount() {
        return enableNativeTopCount;
    }

    public void setEnableInMemoryRollup(boolean enableInMemoryRollup) {
        this.enableInMemoryRollup = enableInMemoryRollup;
    }

    @Override
    public Boolean enableInMemoryRollup() {
        return enableInMemoryRollup;
    }

    public void setExpandNonNative(boolean expandNonNative) {
        this.expandNonNative = expandNonNative;
    }

    @Override
    public Boolean expandNonNative() {
        return expandNonNative;
    }

    public void setGenerateAggregateSql(boolean generateAggregateSql) {
        this.generateAggregateSql = generateAggregateSql;
    }

    @Override
    public Boolean generateAggregateSql() {
        return generateAggregateSql;
    }

    public void setIgnoreInvalidMembersDuringQuery(boolean ignoreInvalidMembersDuringQuery) {
        this.ignoreInvalidMembersDuringQuery = ignoreInvalidMembersDuringQuery;
    }

    @Override
    public Boolean ignoreInvalidMembersDuringQuery() {
        return ignoreInvalidMembersDuringQuery;
    }

    public void setIgnoreMeasureForNonJoiningDimension(boolean ignoreMeasureForNonJoiningDimension) {
        this.ignoreMeasureForNonJoiningDimension = ignoreMeasureForNonJoiningDimension;
    }

    public Boolean ignoreMeasureForNonJoiningDimension() {
        return ignoreMeasureForNonJoiningDimension;
    }

    public void setIterationLimit(int iterationLimit) {
        this.iterationLimit = iterationLimit;
    }

    @Override
    public Integer iterationLimit() {
        return iterationLimit;
    }

    public void setLevelPreCacheThreshold(int levelPreCacheThreshold) {
        this.levelPreCacheThreshold = levelPreCacheThreshold;
    }

    @Override
    public Integer levelPreCacheThreshold() {
        return levelPreCacheThreshold;
    }

    public void setMaxConstraints(int maxConstraints) {
        this.maxConstraints = maxConstraints;
    }


    @Override
    public Integer maxConstraints() {
        return maxConstraints;
    }

    @Override
    public Integer testExpDependencies() {
        return testExpDependencies;
    }

    public void setTestExpDependencies(int testExpDependencies) {
        this.testExpDependencies = testExpDependencies;
    }

    public void setReadAggregates(boolean readAggregates) {
        this.readAggregates = readAggregates;
    }

    @Override
    public Boolean readAggregates() {
        return readAggregates;
    }

    @Override
    public String aggregateRuleTag() {
        return aggregateRuleTag;
    }

    public void setAggregateRuleTag(String aggregateRuleTag) {
        this.aggregateRuleTag = aggregateRuleTag;
    }

    @Override
    public String aggregateRules() {
        return aggregateRules;
    }

    public void setAggregateRules(String aggregateRules) {
        this.aggregateRules = aggregateRules;
    }

    public void setAlertNativeEvaluationUnsupported(String alertNativeEvaluationUnsupported) {
        this.alertNativeEvaluationUnsupported = alertNativeEvaluationUnsupported;
    }

    @Override
    public String alertNativeEvaluationUnsupported() {
        return alertNativeEvaluationUnsupported;
    }

    public void setCrossJoinOptimizerSize(int crossJoinOptimizerSize) {
        this.crossJoinOptimizerSize = crossJoinOptimizerSize;
    }

    @Override
    public Integer crossJoinOptimizerSize() {
        return crossJoinOptimizerSize;
    }

    public void setCurrentMemberWithCompoundSlicerAlert(String currentMemberWithCompoundSlicerAlert) {
        this.currentMemberWithCompoundSlicerAlert = currentMemberWithCompoundSlicerAlert;
    }

    @Override
    public String currentMemberWithCompoundSlicerAlert() {
        return currentMemberWithCompoundSlicerAlert;
    }

    public void setIgnoreInvalidMembers(boolean ignoreInvalidMembers) {
        this.ignoreInvalidMembers = ignoreInvalidMembers;
    }

    @Override
    public Boolean ignoreInvalidMembers() {
        return ignoreInvalidMembers;
    }

    public void setMaxEvalDepth(int maxEvalDepth) {
        this.maxEvalDepth = maxEvalDepth;
    }

    @Override
    public Integer maxEvalDepth() {
        return maxEvalDepth;
    }

    public void setCheckCancelOrTimeoutInterval(int checkCancelOrTimeoutInterval) {
        this.checkCancelOrTimeoutInterval = checkCancelOrTimeoutInterval;
    }

    @Override
    public Integer checkCancelOrTimeoutInterval() {
        return checkCancelOrTimeoutInterval;
    }

    @Override
    public Boolean memoryMonitor() {
        return memoryMonitor;
    }


    public void setMemoryMonitor(boolean memoryMonitor) {
        this.memoryMonitor = memoryMonitor;
    }

    public void setWarnIfNoPatternForDialect(String warnIfNoPatternForDialect) {
        this.warnIfNoPatternForDialect = warnIfNoPatternForDialect;
    }

    @Override
    public String warnIfNoPatternForDialect() {
        return warnIfNoPatternForDialect;
    }

    public void setUseAggregates(boolean useAggregates) {
        this.useAggregates = useAggregates;
    }

    @Override
    public Boolean useAggregates() {
        return useAggregates;
    }

    public void setQueryTimeout(int queryTimeout) {
        this.queryTimeout = queryTimeout;
    }

    @Override
    public Integer queryTimeout() {
        return queryTimeout;
    }

    public void setOptimizePredicates(boolean optimizePredicates) {
        this.optimizePredicates = optimizePredicates;
    }

    @Override
    public Boolean optimizePredicates() {
        return optimizePredicates;
    }

    public void setNullDenominatorProducesNull(boolean nullDenominatorProducesNull) {
        this.nullDenominatorProducesNull = nullDenominatorProducesNull;
    }

    @Override
    public Boolean nullDenominatorProducesNull() {
        return nullDenominatorProducesNull;
    }

    public void setNeedDimensionPrefix(boolean needDimensionPrefix) {
        this.needDimensionPrefix = needDimensionPrefix;
    }

    @Override
    public Boolean needDimensionPrefix() {
        return needDimensionPrefix;
    }

    public void setNativizeMinThreshold(int nativizeMinThreshold) {
        this.nativizeMinThreshold = nativizeMinThreshold;
    }

    public Integer nativizeMinThreshold() {
        return nativizeMinThreshold;
    }

    public void setNativizeMaxResults(int nativizeMaxResults) {
        this.nativizeMaxResults = nativizeMaxResults;
    }

    @Override
    public Integer nativizeMaxResults() {
        return nativizeMaxResults;
    }

    @Override
    public Integer sparseSegmentCountThreshold() {
        return sparseSegmentCountThreshold;
    }

    public void setSparseSegmentCountThreshold(int sparseSegmentCountThreshold) {
        this.sparseSegmentCountThreshold = sparseSegmentCountThreshold;
    }

    @Override
    public Double sparseSegmentDensityThreshold() {
        return sparseSegmentDensityThreshold;
    }

    public void setSparseSegmentDensityThreshold(double sparseSegmentDensityThreshold) {
        this.sparseSegmentDensityThreshold = sparseSegmentDensityThreshold;
    }

    @Override
    public Integer memoryMonitorThreshold() {
        return memoryMonitorThreshold;
    }

    public void setMemoryMonitorThreshold(int memoryMonitorThreshold) {
        this.memoryMonitorThreshold = memoryMonitorThreshold;
    }

    public void setGenerateFormattedSql(boolean generateFormattedSql) {
        this.generateFormattedSql = generateFormattedSql;
    }

    @Override
    public Boolean generateFormattedSql() {
        return generateFormattedSql;
    }
}
