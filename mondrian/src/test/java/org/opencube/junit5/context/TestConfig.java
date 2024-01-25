package org.opencube.junit5.context;

import org.eclipse.daanse.olap.core.BasicContextConfig;

public class TestConfig implements BasicContextConfig {
    private Integer cellBatchSize = -1;

    public Integer cellBatchSize() {
        return cellBatchSize;
    }

    public void setCellBatchSize(Integer cellBatchSize) {
        this.cellBatchSize = cellBatchSize;
    }
}
