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
package mondrian.olap;

import mondrian.mdx.MdxVisitor;
import mondrian.olap.api.NegativeLiteral;

import java.math.BigDecimal;

public class NegativeLiteralImpl extends AbstractLiteralImpl implements NegativeLiteral {

    public static final NegativeLiteralImpl negativeOne =
        new NegativeLiteralImpl(Category.NUMERIC, BigDecimal.ONE.negate());

    /**
     * Private constructor.
     *
     * <p>Use the creation methods create(R) etc.
     *
     * @param type
     * @param o
     */
    protected NegativeLiteralImpl(int type, Object o) {
        super(type, o);
    }

    @Override
    public Object accept(MdxVisitor visitor) {
        return visitor.visit(this);
    }
}
