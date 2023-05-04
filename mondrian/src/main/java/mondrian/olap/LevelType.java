/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2004-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara
// All Rights Reserved.
*/
package mondrian.olap;

/**
 * Enumerates the types of levels.
 *
 * @deprecated Will be replaced with {@link org.olap4j.metadata.Level.Type}
 * before mondrian-4.0.
 *
 * @author jhyde
 * @since 5 April, 2004
 */
public enum LevelType {

    /** Indicates that the level is not related to time. */
    REGULAR("Regular"),

    /**
     * Indicates that a level refers to years.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TIME_DIMENSION}.
     */
    TIME_YEARS("TimeYears"),

    /**
     * Indicates that a level refers to half years.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TIME_DIMENSION}.
     */
    TIME_HALF_YEARS("TimeHalfYears"),

    /**
     * Indicates that a level refers to quarters.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TIME_DIMENSION}.
     */
    TIME_QUARTERS("TimeQuarters"),

    /**
     * Indicates that a level refers to months.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TIME_DIMENSION}.
     */
    TIME_MONTHS("TimeMonths"),

    /**
     * Indicates that a level refers to weeks.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TIME_DIMENSION}.
     */
    TIME_WEEKS("TimeWeeks"),

    /**
     * Indicates that a level refers to days.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TIME_DIMENSION}.
     */
    TIME_DAYS("TimeDays"),

    /**
     * Indicates that a level refers to hours.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TIME_DIMENSION}.
     */
    TIME_HOURS("TimeHours"),

    /**
     * Indicates that a level refers to minutes.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TIME_DIMENSION}.
     */
    TIME_MINUTES("TimeMinutes"),

    /**
     * Indicates that a level refers to seconds.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TIME_DIMENSION}.
     */
    TIME_SECONDS("TimeSeconds"),

    /**
     * Indicates that a level is an unspecified time period.
     * It must be used in a dimension whose type is
     * {@link DimensionType#TIME_DIMENSION}.
     */
    TIME_UNDEFINED("TimeUndefined"),

    /**
     * Indicates that a level holds the null member.
     */
    NULL("Null");


    private final String value;

    LevelType(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

    public static LevelType fromValue(String v) {
        for (LevelType c : LevelType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    /**
     * Returns whether this is a time level.
     *
     * @return Whether this is a time level.
     */
    public boolean isTime() {
        return ordinal() >= TIME_YEARS.ordinal()
           && ordinal() <= TIME_UNDEFINED.ordinal();
    }
}
