/*
* Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.olap.xmla.bridge.execute;

import org.eclipse.daanse.olap.xmla.bridge.ContextListSupplyer;
import org.eclipse.daanse.xmla.api.execute.ExecuteService;
import org.eclipse.daanse.xmla.api.execute.alter.AlterRequest;
import org.eclipse.daanse.xmla.api.execute.alter.AlterResponse;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelRequest;
import org.eclipse.daanse.xmla.api.execute.cancel.CancelResponse;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheRequest;
import org.eclipse.daanse.xmla.api.execute.clearcache.ClearCacheResponse;
import org.eclipse.daanse.xmla.api.execute.statement.StatementRequest;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;

public class OlapExecuteService implements ExecuteService {

	private ContextListSupplyer contextsListSupplyer;

	public OlapExecuteService(ContextListSupplyer contextsListSupplyer) {
		this.contextsListSupplyer=contextsListSupplyer;
	}

	@Override
	public AlterResponse alter(AlterRequest statementRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CancelResponse cancel(CancelRequest capture) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClearCacheResponse clearCache(ClearCacheRequest clearCacheRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatementResponse statement(StatementRequest statementRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
