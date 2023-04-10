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
package mondrian.rolap.util;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Cube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.CubeDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.DimensionUsage;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.PrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.VirtualCubeDimension;

import mondrian.olap.Util;

public class DimensionUtil {

    public static PrivateDimension getDimension(Schema schema, CubeDimension dimension) {
        if (dimension instanceof DimensionUsage) {
            Util.assertPrecondition(schema != null, "schema != null");
            for (int i = 0; i < schema.dimension().size(); i++) {
                if (schema.dimension().get(i).name().equals(((DimensionUsage) dimension).source())) {
                    return schema.dimension().get(i);
                }
            }
            throw Util.newInternal(
                new StringBuilder("Cannot find shared dimension '")
                    .append(((DimensionUsage) dimension).source()).append("'").toString());
        }
        if (dimension instanceof PrivateDimension) {
            Util.assertPrecondition(schema != null, "schema != null");
            return (PrivateDimension)dimension;
        }
        if (dimension instanceof VirtualCubeDimension) {
            Util.assertPrecondition(schema != null, "schema != null");
            if (((VirtualCubeDimension)dimension).cubeName() == null) {
                return getPublicDimension(schema, dimension.name());
            } else {
                Cube cube = getCube(schema, ((VirtualCubeDimension)dimension).cubeName());
                return getDimension(cube, schema, dimension.name());
            }
        }
        throw new RuntimeException("getDimension error");
    }

    private static PrivateDimension  getPublicDimension(Schema schema, String dimensionName) {
        for (int i = 0; i < schema.dimension().size(); i++) {
            if (schema.dimension().get(i).name().equals(dimensionName)) {
                return schema.dimension().get(i);
            }
        }
        throw Util.newInternal(
            new StringBuilder("Cannot find public dimension '").append(dimensionName).append("'").toString());
    }


    private static Cube getCube(Schema schema, String cubeName) {
        for (int i = 0; i < schema.cube().size(); i++) {
            if (schema.cube().get(i).name().equals(cubeName)) {
                return schema.cube().get(i);
            }
        }
        throw Util.newInternal(new StringBuilder("Cannot find cube '").append(cubeName).append("'").toString());
    }

    private static PrivateDimension getDimension(Cube cube, Schema schema, String dimensionName) {
        for (int i = 0; i < cube.dimensionUsageOrDimension().size(); i++) {
            if (cube.dimensionUsageOrDimension().get(i).name().equals(dimensionName)) {
                return getDimension(schema, cube.dimensionUsageOrDimension().get(i));
            }
        }
        throw Util.newInternal(
            new StringBuilder("Cannot find dimension '").append(dimensionName).append("' in cube '")
                .append(cube.name()).append("'").toString());

    }
}
