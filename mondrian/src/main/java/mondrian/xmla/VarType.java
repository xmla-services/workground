package mondrian.xmla;

import org.eclipse.daanse.olap.api.DataType;

public enum VarType {
            Empty("Uninitialized (default)"),
            Null("Contains no valid data"),
            Integer("Integer subtype"),
            Long("Long subtype"),
            Single("Single subtype"),
            Double("Double subtype"),
            Currency("Currency subtype"),
            Date("Date subtype"),
            String("String subtype"),
            Object("Object subtype"),
            Error("Error subtype"),
            Boolean("Boolean subtype"),
            Variant("Variant subtype"),
            DataObject("DataObject subtype"),
            Decimal("Decimal subtype"),
            Byte("Byte subtype"),
            Array("Array subtype");

            public static VarType forCategory(DataType category) {
                switch (category) {
                case UNKNOWN:
                    // expression == unknown ???
                    // case Category.Expression:
                    return Empty;
                case ARRAY:
                    return Array;
                case DIMENSION,
                HIERARCHY,
                LEVEL,
                MEMBER,
                SET,
                TUPLE,
                CUBE,
                VALUE:
                    return Variant;
                case LOGICAL:
                    return Boolean;
                case NUMERIC:
                    return Double;
                case STRING, SYMBOL:
                    return String;
                case DATE_TIME:
                    return Date;
                case INTEGER:
                    return Integer;
                default:
                    break;
                }
                // NOTE: this should never happen
                return Empty;
            }

            VarType(String description) {
                
            }
        }