package org.eclipse.daanse.calc.base.value;

import org.eclipse.daanse.calc.api.DoubleCalc;
import org.eclipse.daanse.calc.base.AbstractProfilingValueCalc;

import mondrian.olap.fun.FunUtil;
import mondrian.olap.type.Type;

public class CurrentValueDoubleCalc extends AbstractProfilingValueCalc<Double> implements DoubleCalc{


	public CurrentValueDoubleCalc(Type type) {
		super(type);
	}

	@Override
	protected Double convertCurrentValue(Object evaluatedCurrentValue) {
		if (evaluatedCurrentValue == null) {
			return FunUtil.DOUBLE_NULL;
		} else if (evaluatedCurrentValue instanceof Double d) {
			return d;
		} else if (evaluatedCurrentValue instanceof Number n) {
			return n.doubleValue();
		}
		throw new RuntimeException("wring value");
	}

}
