/*
* Copyright (c) 2024 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.olap.operation.api;

public sealed interface OperationAtom permits AmpersandQuotedPropertyOperationAtom, //
		BracesOperationAtom, //
		CaseOperationAtom, //
		CastOperationAtom, //
		EmptyOperationAtom, //
		FunctionOperationAtom, //
		InfixOperationAtom, //
		InternalOperationAtom, //
		MethodOperationAtom, //
		ParenthesesOperationAtom, //
		PlainPropertyOperationAtom, //
		PostfixOperationAtom, //
		PrefixOperationAtom, //
		QuotedPropertyOperationAtom {

	String name();

}