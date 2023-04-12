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
package org.eclipse.daanse.mdx.parser.ccc;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.mdx.model.api.expression.CallExpression;
import org.eclipse.daanse.mdx.model.api.expression.CompoundId;
import org.eclipse.daanse.mdx.model.api.expression.Expression;
import org.eclipse.daanse.mdx.model.api.expression.NameObjectIdentifier;
import org.eclipse.daanse.mdx.model.api.expression.ObjectIdentifier;
import org.eclipse.daanse.mdx.model.record.expression.CallExpressionR;
import org.eclipse.daanse.mdx.model.record.expression.CompoundIdR;

public class MdxParserUtil {
	private MdxParserUtil() {
	}

	public static String stripQuotes(String s, String prefix, String suffix, String quoted) {
		if (!(s.startsWith(prefix) && s.endsWith(suffix))) {
            throw new IllegalArgumentException("Invalid quotes: " + s);
        }
		s = s.substring(prefix.length(), s.length() - suffix.length());
		s = s.replace(quoted, suffix);
		return s;
	}

	public static Expression createCall(Expression left, ObjectIdentifier objectIdentifier,
			List<Expression> expressions) {
		final String name = objectIdentifier instanceof NameObjectIdentifier nameObjectIdentifier
				?  nameObjectIdentifier.name()
				: null;
		if (expressions != null) {
			if (left != null) {
				// Method syntax: "x.foo(arg1, arg2)" or "x.foo()"
				expressions.add(0, left);
				return new CallExpressionR(name, CallExpression.Type.METHOD, expressions);
			} else {
				// Function syntax: "foo(arg1, arg2)" or "foo()"
				return new CallExpressionR(name, CallExpression.Type.FUNCTION, expressions);
			}
		} else {
			// Member syntax: "foo.bar"
			// or property syntax: "foo.RESERVED_WORD"
			CallExpression.Type type;
			boolean call = false;
			switch (objectIdentifier.quoting()) {
			case UNQUOTED:
				type = CallExpression.Type.PROPERTY;
				call = true;
				break;
			case QUOTED:
				type = CallExpression.Type.PROPERTY_QUOTED;
				break;
			default:
				type = CallExpression.Type.PROPERTY_AMPERS_AND_QUOTED;
				break;
			}
			if (left instanceof CompoundId compoundIdLeft && !call) {
				List<ObjectIdentifier> newObjectIdentifiers = new ArrayList<>(
						(compoundIdLeft).objectIdentifiers());
				newObjectIdentifiers.add(objectIdentifier);
				return new CompoundIdR(newObjectIdentifiers);
			} else if (left == null) {
				return new CompoundIdR(List.of(objectIdentifier));
			} else {
				return new CallExpressionR(name, type, List.of(left));
			}
		}
	}
}
