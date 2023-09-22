/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.olap.api.query.component;

import org.eclipse.daanse.olap.api.element.Member;
import org.eclipse.daanse.olap.api.element.NamedSet;
import org.eclipse.daanse.olap.api.element.OlapElement;

import mondrian.mdx.MdxVisitor;
import mondrian.olap.Expression;
import mondrian.olap.Validator;

public non-sealed interface Formula extends QueryPart {

    boolean isMember();

    NamedSet getNamedSet();

    Id getIdentifier();

    String getName();

    String getCaption();

    Expression getExpression();

    Expression setExpression(Expression exp);

    Member getMdxMember();

    Number getSolveOrder();

    void compile();

    Id getId();

    Expression getExp();

    NamedSet getMdxSet();

    MemberProperty[] getPemberProperties();

    void accept(Validator validator);

    Object accept(MdxVisitor visitor);

    void createElement(Query q);

    OlapElement getElement();

    String getUniqueName();

    void rename(String newName);
}
