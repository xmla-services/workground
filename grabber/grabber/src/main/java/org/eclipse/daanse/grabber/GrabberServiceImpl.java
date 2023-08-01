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
import org.eclipse.daanse.grabber.api.GrabberInitData;
import org.eclipse.daanse.grabber.api.GrabberService;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.client.soapmessage.XmlaServiceClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class GrabberServiceImpl implements GrabberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrabberServiceImpl.class);

    private final JdbcMetaDataServiceFactory jmdsf;
    private final DataSource dataSource;

    public GrabberServiceImpl(DataSource dataSource, JdbcMetaDataServiceFactory jmdsf) {
        this.dataSource = dataSource;
        this.jmdsf = jmdsf;
    }

    @Override
    public Schema grab(GrabberInitData gid) {
        LOGGER.debug("start grabbing");
        if (gid.getSourcesEndPoints() != null) {
            gid.getSourcesEndPoints().forEach(
                this::grabData
            );
        }
        LOGGER.debug("end grabbing");
        return null;
    }

    private void grabData(String endPointUrl) {
        XmlaService client  = new XmlaServiceClientImpl(endPointUrl);
        //get data
        //client.execute()
    }

}
