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

package org.eclipse.daanse.function;

import mondrian.olap.type.Type;

/**
 * An <code>Exp</code> is an MDX expression.
 *
 * @author jhyde, 20 January, 1999
 * @since 1.0
 */
public interface Exp {

    int getCategory();

    Type getType();
}
