package org.eclipse.daanse.olap.api.result;

public interface Property {

    String getName();

    Datatype getDatatype();

    enum StandardMemberProperty implements Property {

        /**
         * Definition of the property which
         * holds the name of the current catalog.
         */
        CATALOG_NAME,

        /**
         * Definition of the property which
         * holds the name of the current schema.
         */
        SCHEMA_NAME,

        /**
         * Definition of the property which
         * holds the name of the current cube.
         */
        CUBE_NAME,

        /**
         * Definition of the property which
         * holds the unique name of the current dimension.
         */
        DIMENSION_UNIQUE_NAME,

        /**
         * Definition of the property which
         * holds the unique name of the current hierarchy.
         */
        HIERARCHY_UNIQUE_NAME,

        /**
         * Definition of the property which
         * holds the unique name of the current level.
         */
        LEVEL_UNIQUE_NAME,

        /**
         * Definition of the property which
         * holds the ordinal of the current level.
         */
        LEVEL_NUMBER,

        /**
         * Definition of the property which
         * holds the ordinal of the current member.
         */
        MEMBER_ORDINAL,

        /**
         * Definition of the property which
         * holds the name of the current member.
         */
        MEMBER_NAME,

        /**
         * Definition of the property which
         * holds the unique name of the current member.
         */
        MEMBER_UNIQUE_NAME,

        /**
         * Definition of the property which
         * holds the type of the member.
         */
        MEMBER_TYPE,

        /**
         * Definition of the property which
         * holds the GUID of the member
         */
        MEMBER_GUID,

        /**
         * Definition of the property which
         * holds the label or caption associated with the member, or the
         * member's name if no caption is defined.
         */
        MEMBER_CAPTION,

        /**
         * Definition of the property which holds the
         * number of children this member has.
         */
        CHILDREN_CARDINALITY,

        /**
         * Definition of the property which holds the
         * distance from the root of the hierarchy of this member's parent.
         */
        PARENT_LEVEL,

        /**
         * Definition of the property which holds the
         * Name of the current catalog.
         */
        PARENT_UNIQUE_NAME,

        /**
         * Definition of the property which holds the
         * number of parents that this member has. Generally 1, or 0
         * for root members.
         */
        PARENT_COUNT,

        /**
         * Definition of the property which holds the
         * description of this member.
         */
        DESCRIPTION,

        /**
         * Definition of the internal property which holds the
         * name of the system property which determines whether to show a member
         * (especially a measure or calculated member) in a user interface such
         * as JPivot.
         */
        $visible,

        /**
         * Definition of the internal property which holds the
         * value of the member key in the original data type. MEMBER_KEY is for
         * backward-compatibility.  MEMBER_KEY has the same value as KEY0 for
         * non-composite keys, and MEMBER_KEY property is null for composite
         * keys.
         */
        MEMBER_KEY,

        /**
         * Definition of the boolean property that indicates whether
         * a member is a placeholder member for an empty position in a
         * dimension hierarchy.
         */
        IS_PLACEHOLDERMEMBER,

        /**
         * Definition of the property that indicates whether the member is a
         * data member.
         */
        IS_DATAMEMBER,

        /**
         * Definition of the property which
         * holds the level depth of a member.
         *
         * <p>Caution: Level depth of members in parent-child hierarchy isn't
         * from their levels.  It's calculated from the underlying data
         * dynamically.
         */
        DEPTH,

        /**
         * Definition of the property which
         * holds the DISPLAY_INFO required by XML/A.
         *
         * <p>Caution: This property's value is calculated based on a specified
         * MDX query, so its value is dynamic at runtime.
         */
        DISPLAY_INFO,

        /**
         * Definition of the property which
         * holds the value of a cell. Is usually numeric (since most measures
         * are numeric) but is occasionally another type.
         */
        VALUE;

        public String getName() {
            return name();
        }

        @Override
        public Datatype getDatatype() {
            return null;
        }
    }

    enum StandardCellProperty implements Property {
        BACK_COLOR,

        CELL_EVALUATION_LIST,

        CELL_ORDINAL,

        FORE_COLOR,

        FONT_NAME,

        FONT_SIZE,

        FONT_FLAGS,

        /**
         * Definition of the property which
         * holds the formatted value of a cell.
         */
        FORMATTED_VALUE,

        /**
         * Definition of the property which
         * holds the format string used to format cell values.
         */
        FORMAT_STRING,

        NON_EMPTY_BEHAVIOR,

        /**
         * Definition of the property which
         * determines the solve order of a calculated member with respect to
         * other calculated members.
         */
        SOLVE_ORDER,

        /**
         * Definition of the property which
         * holds the value of a cell. Is usually numeric (since most measures
         * are numeric) but is occasionally another type.
         */
        VALUE,

        /**
         * Definition of the property which
         * holds the datatype of a cell. Valid values are "String",
         * "Numeric", "Integer". The property's value derives from the
         * "datatype" attribute of the "Measure" element; if the
         * datatype attribute is not specified, the datatype is
         * "Numeric" by default, except measures whose aggregator is
         * "Count", whose datatype is "Integer".
         */
        DATATYPE,

        LANGUAGE,

        ACTION_TYPE,

        UPDATEABLE;


        public String getName() {
            return name();
        }

        @Override
        public Datatype getDatatype() {
            return null;
            //TODO
        }
    }

}
