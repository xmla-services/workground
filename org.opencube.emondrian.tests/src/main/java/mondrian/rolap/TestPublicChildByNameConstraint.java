package mondrian.rolap;

import java.util.List;

import mondrian.olap.Id.NameSegment;

public class TestPublicChildByNameConstraint extends ChildByNameConstraint {

	public TestPublicChildByNameConstraint(List<NameSegment> childNames) {
		super(childNames);
	}

}
