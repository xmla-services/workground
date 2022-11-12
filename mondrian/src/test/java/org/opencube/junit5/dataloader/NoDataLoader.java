package org.opencube.junit5.dataloader;

import javax.sql.DataSource;

public class NoDataLoader implements DataLoader{

	@Override
	public boolean loadData(DataSource dataSource) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
