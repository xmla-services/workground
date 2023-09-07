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
import mondrian.olap.api.StringLiteral;

public class StringLiteralImpl extends AbstractLiteralImpl<String> implements StringLiteral {

    private StringLiteralImpl(int type, String o) {
        super(type, o);
    }

    /**
     * Creates a string literal.
     */
    public static AbstractLiteralImpl create(String s) {
        return (s.equals(""))
            ? EmptyStringLiteralImpl.emptyString
            : new StringLiteralImpl(Category.STRING, s);
    }

    @Override
    public Object accept(MdxVisitor visitor) {
        return visitor.visit(this);
    }
}
