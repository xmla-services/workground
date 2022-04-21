package org.osgi.impl.service.jdbc.oracle11.datasource;

import java.sql.SQLException;

import javax.sql.XAConnectionBuilder;
import javax.sql.XADataSource;

import org.osgi.impl.service.jdbc.common.datasource.AbstractDelegateXADataSource;
import org.osgi.impl.service.jdbc.oracle11.util.Util;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;

import oracle.jdbc.xa.client.OracleXADataSource;

@Designate(ocd = Oracle11Config.class, factory = true)
@Component(service = XADataSource.class, scope = ServiceScope.PROTOTYPE)
public class XADataSourceService extends AbstractDelegateXADataSource<OracleXADataSource> {

	private Oracle11Config config;
	private OracleXADataSource ds;

	@Activate
	public XADataSourceService(Oracle11Config config) throws SQLException {
		this.ds = new OracleXADataSource();
		ds.setConnectionProperties(Util.transformConfig(config));
		this.config = config;
	}
	// no @Modified to force consumed Services get new configured connections.

	@Deactivate
	public void deactivate() {

		config = null;
	}

	@Override
	public XAConnectionBuilder createXAConnectionBuilder() throws SQLException {
		return super.createXAConnectionBuilder().user(config._password()).password(config._password());
	}

	@Override
	protected OracleXADataSource delegate() {
		return ds;
	}

}
