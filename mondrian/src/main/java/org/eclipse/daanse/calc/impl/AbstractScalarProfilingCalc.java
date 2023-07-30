package org.eclipse.daanse.calc.impl;

import mondrian.calc.ResultStyle;
import mondrian.olap.type.Type;

public abstract class AbstractScalarProfilingCalc<T> extends AbstractProfilingCalc<T> {

	public AbstractScalarProfilingCalc(Type type, String name) {
		super(type, name);
	}

	@Override
	public ResultStyle getResultStyle() {
		return ResultStyle.VALUE;
	}

}
