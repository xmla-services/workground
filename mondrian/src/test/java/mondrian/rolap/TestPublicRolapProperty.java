package mondrian.rolap;

import mondrian.olap.MondrianDef.Expression;
import mondrian.spi.PropertyFormatter;

public class TestPublicRolapProperty extends RolapProperty {

	TestPublicRolapProperty(String name, Datatype type, Expression exp, PropertyFormatter formatter, String caption,
			Boolean dependsOnLevelValue, boolean internal, String description) {
		super(name, type, exp, formatter, caption, dependsOnLevelValue, internal, description);
	}

}
