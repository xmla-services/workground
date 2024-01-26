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
}
