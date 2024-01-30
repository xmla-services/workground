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
}
