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
import org.eclipse.daanse.db.jdbc.util.api.DatabaseCreatorService;
import org.eclipse.daanse.db.jdbc.util.impl.DBStructure;
import org.eclipse.daanse.grabber.api.GrabberInitData;
import org.eclipse.daanse.grabber.api.GrabberService;
import org.eclipse.daanse.grabber.api.StructureProviderService;
import org.eclipse.daanse.olap.rolap.dbmapper.dbcreator.api.DbCreatorService;
import org.eclipse.daanse.olap.rolap.dbmapper.dbcreator.api.DbCreatorServiceFactory;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.utils.Utils;
import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataRequest;
import org.eclipse.daanse.xmla.api.discover.discover.xmlmetadata.DiscoverXmlMetaDataResponseRow;
import org.eclipse.daanse.xmla.client.soapmessage.XmlaServiceClientImpl;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.discover.xmlmetadata.DiscoverXmlMetaDataRequestR;
import org.eclipse.daanse.xmla.model.record.discover.discover.xmlmetadata.DiscoverXmlMetaDataRestrictionsR;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Designate(ocd = GrabberServiceConfig.class, factory = true)
@Component(service = GrabberService.class, scope = ServiceScope.SINGLETON)
public class GrabberServiceImpl implements GrabberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrabberServiceImpl.class);
    private final DataSource dataSource;

    public static final Converter CONVERTER = Converters.standardConverter();
    private GrabberServiceConfig config;

    @Reference
    private JdbcMetaDataServiceFactory jmdsf;

    @Reference
    private StructureProviderService structureProviderService;

    @Reference
    private DatabaseCreatorService databaseCreatorService;



    @Activate
    public void activate(Map<String, Object> configMap) {
        LOGGER.debug("activate started");
        this.config = CONVERTER.convert(configMap)
            .to(GrabberServiceConfig.class);
        LOGGER.debug("activate finished");
    }

    @Deactivate
    public void deactivate() {
        config = null;
    }

    public GrabberServiceImpl(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
    }

    @Override
    public Schema grab(GrabberInitData gid)  {
        LOGGER.debug("start grabbing");
        if (gid.getSourcesEndPoints() != null) {
            DBStructure dbStructure = structureProviderService.grabStructure(config.targetSchemaName(), gid.getSourcesEndPoints());
            try {
                databaseCreatorService.createDatabaseSchema(dataSource, dbStructure);
            } catch (SQLException exception) {
                new GrabberException("grabbing error " + exception.getMessage());
            }

            gid.getSourcesEndPoints().forEach(
                this::grabData
            );
        }
        LOGGER.debug("end grabbing");
        return null;
    }

    private void grabData(String endPointUrl) {
        XmlaService client  = new XmlaServiceClientImpl(endPointUrl);


    }

}
