package org.opencube.junit5.dataloader;

import javax.sql.DataSource;

public interface DataLoader {

    boolean loadData(DataSource dataSource) throws Exception;


}
