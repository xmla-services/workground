package org.eclipse.daanse.olap.api;

import org.eclipse.daanse.olap.api.query.component.Expression;
import org.eclipse.daanse.olap.calc.api.Calc;

public interface ExpCacheDescriptor {

	Calc getCalc();

	Object evaluate(Evaluator evaluator);

	Expression getExp();

	/**
	 * Returns the ordinals of the hierarchies which this expression is dependent
	 * upon. When the cache descriptor is used to generate a cache key, the key will
	 * consist of a member from each of these hierarchies.
	 */
	int[] getDependentHierarchyOrdinals();

}