/*
* Copyright (c) 2023 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/

package org.eclipse.daanse.olap.calc.base.type.tuple;

import org.eclipse.daanse.olap.api.element.Member;
import org.eclipse.daanse.olap.calc.api.MemberCalc;
import org.eclipse.daanse.olap.calc.base.nested.AbstractProfilingNestedTupleCalc;

import mondrian.olap.Evaluator;
import mondrian.olap.type.Type;

public class MemberCalcToTupleCalc extends AbstractProfilingNestedTupleCalc<MemberCalc> {
	private final MemberCalc memberCalc;

	public MemberCalcToTupleCalc(Type type, MemberCalc memberCalc) {
		super(type, new MemberCalc[] { memberCalc });
		this.memberCalc = memberCalc;
	}

	@Override
	public Member[] evaluate(Evaluator evaluator) {
		final Member member = memberCalc.evaluate(evaluator);
		if (member == null) {
			return null;
		} else {
			return new Member[] { memberCalc.evaluate(evaluator) };
		}
	}
}