package org.eclipse.daanse.calc.impl;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;

import mondrian.olap.type.Type;

public abstract class AbstractIteratorProfilingCalc<T> extends AbstractProfilingCalc<T> {

	public AbstractIteratorProfilingCalc(Type type, String name) {
		super(type, name);
	}

	private long elementCount;
	private long elementSquaredCount;

	@Override
	protected void profileEvaluation(Instant evaluationStart, Instant evaluationEnd, T evaluationResult) {
		super.profileEvaluation(evaluationStart, evaluationEnd, evaluationResult);
		if (evaluationResult instanceof Collection c) {
			long size = c.size();
			elementCount += size;
			elementSquaredCount += size * size;
		}
	}

	@Override
	protected Map<String, Object> profilingProperties(Map<String, Object> properties) {
		properties.put("elementCount", elementCount);
		properties.put("elementSquaredCount", elementSquaredCount);
		return properties;
	}

}
