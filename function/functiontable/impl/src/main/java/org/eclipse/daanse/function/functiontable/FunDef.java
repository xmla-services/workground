/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 1999-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
*/

package org.eclipse.daanse.function.functiontable;



/**
 * Definition of an MDX function..
 *
 * @author jhyde, 21 April, 1999
 */
public interface FunDef {

    Syntax getSyntax();

    String getName();

    int getReturnCategory();

    int[] getParameterCategories();
}
