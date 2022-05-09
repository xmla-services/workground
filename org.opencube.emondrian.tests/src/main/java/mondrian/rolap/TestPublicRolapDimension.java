package mondrian.rolap;

import mondrian.olap.MondrianDef.CubeDimension;
import mondrian.olap.MondrianDef.Dimension;

public class TestPublicRolapDimension extends RolapDimension{

	TestPublicRolapDimension(RolapSchema schema, RolapCube cube, Dimension xmlDimension,
			CubeDimension xmlCubeDimension) {
		super(schema, cube, xmlDimension, xmlCubeDimension);
	}

}
