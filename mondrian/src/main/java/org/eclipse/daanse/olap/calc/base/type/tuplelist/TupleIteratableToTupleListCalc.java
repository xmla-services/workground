
package org.eclipse.daanse.olap.calc.base.type.tuplelist;

import org.eclipse.daanse.olap.calc.api.TupleIterableCalc;
import org.eclipse.daanse.olap.calc.base.nested.AbstractProfilingNestedTupleListCalc;

import mondrian.calc.TupleCollections;
import mondrian.calc.TupleCursor;
import mondrian.calc.TupleIterable;
import mondrian.calc.TupleList;
import mondrian.olap.Evaluator;


public class TupleIteratableToTupleListCalc extends AbstractProfilingNestedTupleListCalc<TupleIterableCalc> {

	public TupleIteratableToTupleListCalc(TupleIterableCalc tupleIterableCalc) {
		super(tupleIterableCalc.getType(), new TupleIterableCalc[] { tupleIterableCalc });
	}

	@Override
	public TupleList evaluate(Evaluator evaluator) {
		final TupleIterable iterable = getFirstChildCalc().evaluate(evaluator);

		// If TupleIteratable is TupleList do not copy.
		if (iterable instanceof TupleList tupleList) {
			return tupleList;
		}

		final TupleList list = TupleCollections.createList(iterable.getArity());
		final TupleCursor tupleCursor = iterable.tupleCursor();
		while (tupleCursor.forward()) {
			list.addCurrent(tupleCursor);
		}
		return list;
	}
}
