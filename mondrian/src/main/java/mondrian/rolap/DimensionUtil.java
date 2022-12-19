package mondrian.rolap;

import mondrian.olap.MondrianDef;
import mondrian.olap.Util;
import org.eclipse.daanse.olap.rolap.dbmapper.api.*;

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
                "Cannot find shared dimension '" + ((DimensionUsage) dimension).source() + "'");
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
            "Cannot find public dimension '" + dimensionName + "'");
    }


    private static Cube getCube(Schema schema, String cubeName) {
        for (int i = 0; i < schema.cube().size(); i++) {
            if (schema.cube().get(i).name().equals(cubeName)) {
                return schema.cube().get(i);
            }
        }
        throw Util.newInternal("Cannot find cube '" + cubeName + "'");
    }

    private static PrivateDimension getDimension(Cube cube, Schema schema, String dimensionName) {
        for (int i = 0; i < schema.dimension().size(); i++) {
            if (schema.dimension().get(i).name().equals(dimensionName)) {
                return getDimension(schema, schema.dimension().get(i));
            }
        }
        throw Util.newInternal(
            "Cannot find dimension '" + dimensionName + "' in cube '" +
                cube.name() + "'");

    }
}
