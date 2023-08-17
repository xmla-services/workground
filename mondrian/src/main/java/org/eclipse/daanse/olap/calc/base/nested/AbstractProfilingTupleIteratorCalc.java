
package org.eclipse.daanse.olap.calc.base.nested;

import org.eclipse.daanse.olap.calc.api.Calc;
import org.eclipse.daanse.olap.calc.api.TupleIteratorCalc;
import org.eclipse.daanse.olap.calc.base.AbstractProfilingNestedCalc;

import mondrian.calc.ResultStyle;
import mondrian.calc.TupleIterable;
import mondrian.olap.type.SetType;
import mondrian.olap.type.Type;

public abstract class AbstractProfilingTupleIteratorCalc<C extends Calc<?>>
		extends AbstractProfilingNestedCalc<TupleIterable, C> implements TupleIteratorCalc {

	protected AbstractProfilingTupleIteratorCalc(Type type, C[] calcs) {
		super(type, calcs);
	}

	@Override
	public SetType getType() {
		return (SetType) super.getType();
	}

	@Override
	public ResultStyle getResultStyle() {
		return ResultStyle.ITERABLE;
	}

}
