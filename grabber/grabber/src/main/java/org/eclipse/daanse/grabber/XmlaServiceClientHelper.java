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

import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.common.enums.CubeSourceEnum;
import org.eclipse.daanse.xmla.api.common.enums.VisibilityEnum;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.cubes.MdSchemaCubesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.hierarchies.MdSchemaHierarchiesResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.levels.MdSchemaLevelsResponseRow;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.properties.MdSchemaPropertiesResponseRow;
import org.eclipse.daanse.xmla.api.execute.statement.StatementRequest;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.client.soapmessage.XmlaServiceClientImpl;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.cubes.MdSchemaCubesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.cubes.MdSchemaCubesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.hierarchies.MdSchemaHierarchiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.hierarchies.MdSchemaHierarchiesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.levels.MdSchemaLevelsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.levels.MdSchemaLevelsRestrictionsR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.properties.MdSchemaPropertiesRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.properties.MdSchemaPropertiesRestrictionsR;
import org.eclipse.daanse.xmla.model.record.execute.statement.StatementRequestR;
import org.eclipse.daanse.xmla.model.record.xmla.StatementR;

import java.util.List;
import java.util.Optional;

public class XmlaServiceClientHelper {

    private XmlaServiceClientHelper() {
        // constructor
    }

    static List<MdSchemaLevelsResponseRow> getMdSchemaLevels(String endPointUrl) {
        XmlaService client = new XmlaServiceClientImpl(endPointUrl);
        PropertiesR properties = new PropertiesR();
        MdSchemaLevelsRestrictionsR restrictions = new MdSchemaLevelsRestrictionsR(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(CubeSourceEnum.CUBE),
            Optional.of(VisibilityEnum.VISIBLE)
        );
        MdSchemaLevelsRequest request = new MdSchemaLevelsRequestR(properties, restrictions);
        return client.discover().mdSchemaLevels(request);
    }

    static List<MdSchemaDimensionsResponseRow> getMdSchemaDimensions(String endPointUrl) {
        XmlaService client = new XmlaServiceClientImpl(endPointUrl);
        PropertiesR properties = new PropertiesR();
        MdSchemaDimensionsRestrictionsR restrictions = new MdSchemaDimensionsRestrictionsR(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(CubeSourceEnum.CUBE),
            Optional.of(VisibilityEnum.VISIBLE)
        );
        MdSchemaDimensionsRequest request = new MdSchemaDimensionsRequestR(properties, restrictions);
        return client.discover().mdSchemaDimensions(request);
    }

    static List<MdSchemaPropertiesResponseRow> getMdSchemaProperties(String endPointUrl) {
        XmlaService client = new XmlaServiceClientImpl(endPointUrl);
        PropertiesR properties = new PropertiesR();
        MdSchemaPropertiesRestrictionsR restrictions = new MdSchemaPropertiesRestrictionsR(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(CubeSourceEnum.CUBE),
            Optional.of(VisibilityEnum.VISIBLE)
        );
        MdSchemaPropertiesRequest request = new MdSchemaPropertiesRequestR(properties, restrictions);
        return client.discover().mdSchemaProperties(request);
    }

    static List<MdSchemaHierarchiesResponseRow> getMdSchemaHierarchies(String endPointUrl) {
        XmlaService client = new XmlaServiceClientImpl(endPointUrl);
        PropertiesR properties = new PropertiesR();
        MdSchemaHierarchiesRestrictionsR restrictions = new MdSchemaHierarchiesRestrictionsR(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(CubeSourceEnum.CUBE),
            Optional.of(VisibilityEnum.VISIBLE)
        );
        MdSchemaHierarchiesRequest request = new MdSchemaHierarchiesRequestR(properties, restrictions);
        return client.discover().mdSchemaHierarchies(request);

    }

    static List<MdSchemaCubesResponseRow> getMdSchemaCubes(String endPointUrl) {
        XmlaService client = new XmlaServiceClientImpl(endPointUrl);
        PropertiesR properties = new PropertiesR();
        MdSchemaCubesRestrictionsR restrictions = new MdSchemaCubesRestrictionsR(
            null,
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
        );
        MdSchemaCubesRequest request = new MdSchemaCubesRequestR(properties, restrictions);
        return client.discover().mdSchemaCubes(request);
    }

    static StatementResponse executeStatement(String endPointUrl, String mdxQuery) {
        XmlaService client = new XmlaServiceClientImpl(endPointUrl);
        PropertiesR properties = new PropertiesR();
        StatementR command = new StatementR(mdxQuery);
        StatementRequest statementRequest = new StatementRequestR(properties, List.of(), command);
        return client.execute().statement(statementRequest);
    }
}
