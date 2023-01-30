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

public record Axis(int ordinal) {

    public Axis {
        if (ordinal < -2) {
            throw new IllegalArgumentException("must be >= -2");
        }
    }

    public static Axis create(int ordinal) {

        if (ordinal == -2) {
            return NONE;
        } else if (ordinal == -1) {
            return SLICER;
        } else if (ordinal == 0) {
            return COLUMNS;
        } else if (ordinal == 1) {
            return ROWS;
        } else if (ordinal == 2) {
            return PAGES;
        } else if (ordinal == 3) {
            return CHAPTERS;
        } else if (ordinal == 4) {
            return SECTIONS;
        } else {
            return new Axis(ordinal);
        }

    }

    public static final Axis NONE = new Axis(-2);
    public static final Axis SLICER = new Axis(-1);
    public static final Axis COLUMNS = new Axis(0);
    public static final Axis ROWS = new Axis(1);
    public static final Axis PAGES = new Axis(2);
    public static final Axis CHAPTERS = new Axis(3);
    public static final Axis SECTIONS = new Axis(4);

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
            return "Axis(" + ")";
        }
    }
}
