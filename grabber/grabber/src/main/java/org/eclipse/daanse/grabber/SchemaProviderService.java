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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Annotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.NamedSet;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Parameter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Role;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.UserDefinedFunction;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.VirtualCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.PrivateDimensionR;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.SchemaR;
import org.eclipse.daanse.xmla.api.XmlaService;
import org.eclipse.daanse.xmla.api.common.enums.CubeSourceEnum;
import org.eclipse.daanse.xmla.api.common.enums.VisibilityEnum;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsRequest;
import org.eclipse.daanse.xmla.api.discover.mdschema.demensions.MdSchemaDimensionsResponseRow;
import org.eclipse.daanse.xmla.client.soapmessage.XmlaServiceClientImpl;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsRequestR;
import org.eclipse.daanse.xmla.model.record.discover.mdschema.demensions.MdSchemaDimensionsRestrictionsR;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SchemaProviderService {

    public Schema getSchema(String endPointUrl) {
        String name = null;
        String description = null;
        String measuresCaption = null;
        String defaultRole = null;

        List<PrivateDimension> dimensions = new ArrayList<>();
        List<Annotation> annotations = new ArrayList<>();
        List<Parameter> parameters = new ArrayList<>();
        List<VirtualCube> virtualCubes = new ArrayList<>();
        List<Cube> cubes = new ArrayList<>();
        List<NamedSet> namedSets = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        List<UserDefinedFunction> userDefinedFunctions = new ArrayList<>();

        dimensions.addAll(getDimensions(endPointUrl));

        return new SchemaR(
            name,
            description,
            measuresCaption,
            defaultRole,
            annotations,
            parameters,
            dimensions,
            cubes,
            virtualCubes,
            namedSets,
            roles,
            userDefinedFunctions
        );

    }

    private List<PrivateDimension> getDimensions(String endPointUrl) {
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
        List<MdSchemaDimensionsResponseRow> res = client.discover().mdSchemaDimensions(request);
        if (res != null) {
            res.stream().map(it -> {
                if (it.cubeName().isPresent()) {
                    return new PrivateDimensionR(
                        it.dimensionName().orElse(null),
                        DimensionTypeEnum.STANDARD_DIMENSION,
                        it.dimensionCaption().orElse(null),
                        it.description().orElse(null),
                        null,
                        true,
                        List.of(),
                        List.of(),
                        it.dimensionIsVisible().orElse(null),
                        List.of(),
                        null
                    );
                }
                return List.of();
            }).toList();
        }

        return List.of();
    }
}
