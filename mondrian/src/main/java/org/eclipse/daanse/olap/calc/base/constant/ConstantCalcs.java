package org.eclipse.daanse.olap.calc.base.constant;

import org.eclipse.daanse.olap.calc.api.ConstantCalc;

import mondrian.olap.type.BooleanType;
import mondrian.olap.type.DecimalType;
import mondrian.olap.type.NumericType;
import mondrian.olap.type.StringType;
import mondrian.olap.type.Type;

public class ConstantCalcs {

	public static ConstantCalc<?> nullCalcOf(Type type) {

		if (type instanceof StringType st) {
			return new ConstantStringCalc(st, null);

		} else if (type instanceof DecimalType dt) {
			return new ConstantDoubleCalc(dt, null);

		} else if (type instanceof NumericType nt) {
			return new ConstantIntegerCalc(nt, null);

		}
		else if (type instanceof BooleanType bt) {
			return new ConstantBooleanCalc( null);

		}else {
			throw new RuntimeException(type.toString()+" --- "+type.getClass());
		}
			

	}
}