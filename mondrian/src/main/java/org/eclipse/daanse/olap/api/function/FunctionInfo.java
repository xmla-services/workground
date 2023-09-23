/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.eclipse.daanse.olap.api.function;

import org.eclipse.daanse.olap.api.Syntax;

public interface FunctionInfo  extends Comparable<FunctionInfo>{

	String[] getSignatures();

	/**
	 * Returns the syntactic type of the function.
	 */
	Syntax getSyntax();

	/**
	 * Returns the name of this function.
	 */
	String getName();

	/**
	 * Returns the description of this function.
	 */
	String getDescription();

	/**
	 * Returns the type of value returned by this function. Values are the same
	 * as those returned by {@link org.eclipse.daanse.olap.api.query.component.Expression#getCategory()}.
	 */
	int[] getReturnCategories();

	/**
	 * Returns the types of the arguments of this function. Values are the same
	 * as those returned by {@link org.eclipse.daanse.olap.api.query.component.Expression#getCategory()}. The
	 * 0<sup>th</sup> argument of methods and properties are the object they
	 * are applied to. Infix operators have two arguments, and prefix operators
	 * have one argument.
	 */
	int[][] getParameterCategories();

	int compareTo(FunctionInfo fi);

}