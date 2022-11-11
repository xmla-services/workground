/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (c) 2002-2020 Hitachi Vantara..  All rights reserved.
// Copyright (c) 2021 Sergei Semenkov.  All rights reserved.
*/
package org.eclipse.daanse.sql.dialect.api;

/**
 * Datatype of a column.
 */
public enum Datatype {
    String {
        public void quoteValue(
            StringBuilder buf, Dialect dialect, String value)
        {
            dialect.quoteStringLiteral(buf, value);
        }
    },

    Numeric {
        public void quoteValue(
            StringBuilder buf, Dialect dialect, String value)
        {
            dialect.quoteNumericLiteral(buf, value);
        }

        public boolean isNumeric() {
            return true;
        }
    },

    Integer {
        public void quoteValue(
            StringBuilder buf, Dialect dialect, String value)
        {
            dialect.quoteNumericLiteral(buf, value);
        }

        public boolean isNumeric() {
            return true;
        }
    },

    Boolean {
        public void quoteValue(
            StringBuilder buf, Dialect dialect, String value)
        {
            dialect.quoteBooleanLiteral(buf, value);
        }
    },

    Date {
        public void quoteValue(
            StringBuilder buf, Dialect dialect, String value)
        {
            dialect.quoteDateLiteral(buf, value);
        }
    },

    Time {
        public void quoteValue(
            StringBuilder buf, Dialect dialect, String value)
        {
            dialect.quoteTimeLiteral(buf, value);
        }
    },

    Timestamp {
        public void quoteValue(
            StringBuilder buf, Dialect dialect, String value)
        {
            dialect.quoteTimestampLiteral(buf, value);
        }
    };

    /**
     * Appends to a buffer a value of this type, in the appropriate format
     * for this dialect.
     *
     * @param buf Buffer
     * @param dialect Dialect
     * @param value Value
     */
    public abstract void quoteValue(
        StringBuilder buf,
        Dialect dialect,
        String value);

    /**
     * Returns whether this is a numeric datatype.
     *
     * @return whether this is a numeric datatype.
     */
    public boolean isNumeric() {
        return false;
    }
}
