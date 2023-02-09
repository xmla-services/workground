/*
* Copyright (c) 2023 Contributors to the Eclipse Foundation.
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
package org.eclipse.daanse.mdx.model;

public record Axis(int ordinal,boolean named) {

    public Axis {
        if (ordinal < -2) {
            throw new IllegalArgumentException("must be >= -2");
        }
    }

    public static Axis createUnnamed(int ordinal) {

        if (ordinal == -2) {
            return NONE;
        } else if (ordinal == -1) {
            return SLICER;
        } else if (ordinal == 0) {
            return COLUMNS_NAMED;
        } else if (ordinal == 1) {
            return ROWS_NAMED;
        } else if (ordinal == 2) {
            return PAGES_NAMED;
        } else if (ordinal == 3) {
            return CHAPTERS_NAMED;
        } else if (ordinal == 4) {
            return SECTIONS_NAMED;
        } else {
            return new Axis(ordinal,false);
        }

    }

    public static final Axis NONE = new Axis(-2,false);
    public static final Axis SLICER = new Axis(-1,false);
    public static final Axis COLUMNS_NAMED = new Axis(0,true);
    public static final Axis ROWS_NAMED = new Axis(1,true);
    public static final Axis PAGES_NAMED = new Axis(2,true);
    public static final Axis CHAPTERS_NAMED = new Axis(3,true);
    public static final Axis SECTIONS_NAMED = new Axis(4,true);

    public boolean isFilter() {
        return isFilter(this);
    }

    public static boolean isFilter(Axis axis) {
        return axis == SLICER;
    }

    public String name() {

        if (this.ordinal == -2) {
            return "None";
        } else if (this.ordinal == -1) {
            return "Slicer";
        } else if (this.ordinal == 0) {
            return "Columns";
        } else if (this.ordinal == 1) {
            return "Rows";
        } else if (this.ordinal == 2) {
            return "Pages";
        } else if (this.ordinal == 3) {
            return "Chapters";
        } else if (this.ordinal == 4) {
            return "Sections";
        } else {
            return "Axis(" +this.ordinal+ ")";
        }
    }
}
