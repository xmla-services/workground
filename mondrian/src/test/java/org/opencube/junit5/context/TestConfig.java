package org.opencube.junit5.context;

import org.eclipse.daanse.olap.core.BasicContextConfig;

public class TestConfig implements BasicContextConfig {
    private Integer cellBatchSize = -1;
    private Integer rolapConnectionShepherdNbThreads = 20;
    private String rolapConnectionShepherdThreadPollingInterval = "1000ms";
    private Integer segmentCacheManagerNumberSqlThreads = 100;
    private String solveOrderMode = "ABSOLUTE";

    public Integer cellBatchSize() {
        return cellBatchSize;
    }

    public void setCellBatchSize(Integer cellBatchSize) {
        this.cellBatchSize = cellBatchSize;
    }

    public Integer rolapConnectionShepherdNbThreads() {
        return rolapConnectionShepherdNbThreads;
    }

    public void setRolapConnectionShepherdNbThreads(Integer rolapConnectionShepherdNbThreads) {
        this.rolapConnectionShepherdNbThreads = rolapConnectionShepherdNbThreads;
    }

    public String rolapConnectionShepherdThreadPollingInterval() {
        return rolapConnectionShepherdThreadPollingInterval;
    }

    public void setRolapConnectionShepherdThreadPollingInterval(String rolapConnectionShepherdThreadPollingInterval) {
        this.rolapConnectionShepherdThreadPollingInterval = rolapConnectionShepherdThreadPollingInterval;
    }

    public Integer segmentCacheManagerNumberSqlThreads() {
        return segmentCacheManagerNumberSqlThreads;
    }

    public void setSegmentCacheManagerNumberSqlThreads(Integer segmentCacheManagerNumberSqlThreads) {
        this.segmentCacheManagerNumberSqlThreads = segmentCacheManagerNumberSqlThreads;
    }

    public void setSolveOrderMode(String solveOrderMode) {
        this.solveOrderMode = solveOrderMode;
    }

    public String solveOrderMode() {
        return solveOrderMode;
    }
}
