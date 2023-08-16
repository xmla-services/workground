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
package org.eclipse.daanse.olap.calc.base.constant;

import org.eclipse.daanse.olap.api.model.Member;
import org.eclipse.daanse.olap.calc.api.MemberCalc;
import org.eclipse.daanse.olap.calc.base.AbstractProfilingConstantCalc;

import mondrian.olap.type.MemberType;

public class ConstantProfilingMemberCalc extends AbstractProfilingConstantCalc<Member> implements MemberCalc {

	public ConstantProfilingMemberCalc(MemberType type, Member value) {
		super(value, type);
	}

	public static ConstantProfilingMemberCalc of(Member member) {
		return new ConstantProfilingMemberCalc(MemberType.forMember(member), member);
	}

}
