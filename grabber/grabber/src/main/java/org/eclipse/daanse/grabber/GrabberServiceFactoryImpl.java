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
package org.eclipse.daanse.grabber;

import org.eclipse.daanse.db.jdbc.metadata.api.JdbcMetaDataServiceFactory;
import org.eclipse.daanse.grabber.api.GrabberService;
import org.eclipse.daanse.grabber.api.GrabberServiceFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import javax.sql.DataSource;

@Component(service = GrabberServiceFactory.class, scope = ServiceScope.SINGLETON)
public class GrabberServiceFactoryImpl implements GrabberServiceFactory {

    @Reference
    JdbcMetaDataServiceFactory jmdsf;

    @Override
    public GrabberService create(DataSource dataSource) {
        return new GrabberServiceImpl(dataSource, jmdsf);
    }

}
