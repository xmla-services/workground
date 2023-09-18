/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2002-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
*/

package mondrian.calc.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import mondrian.olap.fun.FunUtil;
import mondrian.olap.type.NullType;

/**
 * Test for <code>ConstantCalc</code>
 * @author Matt
 * @author stbischof
 */
class ConstantCalcTest{

	@Test
    void testNullEvaluatesToConstantDoubleNull() {
//        ConstantCalc constantCalc = new ConstantCalc(new NullType(), null);
//        assertEquals(FunUtil.DOUBLE_NULL, constantCalc.evaluateDouble(null));
    }

	@Test
    void testNullEvaluatesToConstantIntegerNull() {
//        ConstantCalc constantCalc = new ConstantCalc(new NullType(), null);
//        assertEquals(FunUtil.INTEGER_NULL, constantCalc.evaluateInteger(null));
    }
}
