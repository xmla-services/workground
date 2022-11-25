/*********************************************************************
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/

package  org.eclipse.daanse.core.api.olap;

import java.io.PrintWriter;

/**
 * A <code>Formula</code> is a clause in an MDX query which defines a Set or a
 * Member.
 */
public interface IFormula {

    void accept(Validator validator);

    /**
     * Creates the {@link Member} or {@link NamedSet} object which this formula
     * defines.
     */
    void createElement(IQuery q);

    Object[] getChildren();

    void unparse(PrintWriter pw);

    boolean isMember();

    NamedSet getNamedSet();

    public IId getIdentifier();

    /** Returns this formula's name. */
    String getName();

    /** Returns this formula's caption. */
    String getCaption();

    /**
     * Changes the last part of the name to <code>newName</code>. For example,
     * <code>[Abc].[Def].[Ghi]</code> becomes <code>[Abc].[Def].[Xyz]</code>;
     * and the member or set is renamed from <code>Ghi</code> to
     * <code>Xyz</code>.
     */
    void rename(String newName);

    /** Returns the unique name of the member or set. */
    String getUniqueName();

    OlapElement getElement();

    Exp getExpression();

    Exp setExpression(Exp exp);


    /**
     * Returns the Member. (Not valid if this formula defines a set.)
     *
     * @pre isMember()
     * @post return != null
     */
    Member getMdxMember();

    /**
     * Returns the solve order. (Not valid if this formula defines a set.)
     *
     * @pre isMember()
     * @return Solve order, or null if SOLVE_ORDER property is not specified
     *   or is not a number or is not constant
     */
    Number getSolveOrder();
}

// End Formula.java
