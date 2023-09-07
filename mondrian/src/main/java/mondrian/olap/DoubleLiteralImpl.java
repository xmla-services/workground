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
import mondrian.olap.api.DoubleLiteral;
import org.olap4j.impl.UnmodifiableArrayMap;

import java.math.BigDecimal;
import java.util.Map;

public class DoubleLiteralImpl extends AbstractLiteralImpl<BigDecimal> implements DoubleLiteral {

    private static final Map<BigDecimal, AbstractLiteralImpl> MAP =
        UnmodifiableArrayMap.of(
            BigDecimal.ZERO, ZeroLiteralImpl.zero,
            BigDecimal.ONE, OneLiteralImpl.one,
            BigDecimal.ONE.negate(), NegativeLiteralImpl.negativeOne);

    private DoubleLiteralImpl(int type, BigDecimal o) {
        super(type, o);
    }

    /**
     * Creates a numeric literal.
     *
     * <p>Using a {@link BigDecimal} allows us to store the precise value that
     * the user typed. We will have to fit the value into a native
     * {@code double} or {@code int} later on, but parse time is not the time to
     * be throwing away information.
     */
    public static AbstractLiteralImpl create(BigDecimal d) {
        final AbstractLiteralImpl literal = MAP.get(d);
        if (literal != null) {
            return literal;
        }
        return new DoubleLiteralImpl(Category.NUMERIC, d);
    }

    @Override
    public Object accept(MdxVisitor visitor) {
        return visitor.visit(this);
    }

}
