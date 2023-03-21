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
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.creator.impl;

import org.eclipse.daanse.olap.rolap.dbmapper.creator.api.DbCreatorService;
import org.eclipse.daanse.olap.rolap.dbmapper.creator.api.DbCreatorServiceFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component(service = DbCreatorServiceFactory.class, scope = ServiceScope.SINGLETON)

public class DbCreatorServiceFactoryImpl implements DbCreatorServiceFactory {

    @Override
    public DbCreatorService create(DataSource dataSource) throws SQLException {
        return new DbCreatorServiceImpl(dataSource);
    }

}
