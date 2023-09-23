/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2005-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara
// Copyright (C) 2021-2022 Sergei Semenkov
// All Rights Reserved.
*/
package mondrian.olap.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.daanse.olap.api.Category;
import org.eclipse.daanse.olap.api.Syntax;
import org.eclipse.daanse.olap.api.Validator;
import org.eclipse.daanse.olap.api.element.Hierarchy;
import org.eclipse.daanse.olap.api.function.FunctionResolver;
import org.eclipse.daanse.olap.api.query.component.Expression;
import org.eclipse.daanse.olap.api.type.Type;

import mondrian.mdx.UnresolvedFunCallImpl;
import mondrian.olap.Util;

/**
 * Utility methods relating to types.
 *
 * @author jhyde
 * @since Feb 17, 2005
 */
public class TypeUtil {

    private TypeUtil() {
        //constructor
    }

    /**
     * Given a set type, returns the element type. Or its element type, if it
     * is a set type. And so on.
     *
     * @param type Type
     * @return underlying element type which is not a set type
     */
    public static Type stripSetType(Type type) {
        while (type instanceof SetType setType) {
            type = setType.getElementType();
        }
        return type;
    }

    /**
     * Converts a type to a member or tuple type.
     * If it cannot, returns null.
     *
     * @param type Type
     * @return member or tuple type
     */
    public static Type toMemberOrTupleType(Type type) {
        type = stripSetType(type);
        if (type instanceof TupleType) {
            return type;
        } else {
            return toMemberType(type);
        }
    }

    /**
     * Converts a type to a member type.
     * If it is a set, strips the set.
     * If it is a member type, returns the type unchanged.
     * If it is a dimension, hierarchy or level type, converts it to
     * a member type.
     * If it is a tuple, number, string, or boolean, returns null.
     *
     * @param type Type
     * @return type as a member type
     */
    public static MemberType toMemberType(Type type) {
        type = stripSetType(type);
        if (type instanceof MemberType memberType) {
            return memberType;
        } else if (type instanceof DimensionType
            || type instanceof HierarchyType
            || type instanceof LevelType)
        {
            return MemberType.forType(type);
        } else if(type instanceof TupleType tupleType && tupleType.getArity() == 1) {
            return MemberType.forHierarchy(((TupleType)type).getHierarchies().get(0));
        }
        else {
            return null;
        }
    }

    /**
     * Returns whether this type is union-compatible with another.
     * In general, to be union-compatible, types must have the same
     * dimensionality.
     *
     * @param type1 First type
     * @param type2 Second type
     * @return whether types are union-compatible
     */
    public static boolean isUnionCompatible(Type type1, Type type2) {
        final MemberType memberType1 = toMemberType(type1);
        final MemberType memberType2 = toMemberType(type2);
        if(memberType1 != null && memberType2 != null ) {
            final Hierarchy hierarchy1 = memberType1.getHierarchy();
            final Hierarchy hierarchy2 = memberType2.getHierarchy();
            return equalHierarchy(hierarchy1, hierarchy2);
        }
        if (type1 instanceof TupleType tupleType1) {
            if (type2 instanceof TupleType tupleType2 && tupleType1.elementTypes.length
                == tupleType2.elementTypes.length) {
                for (int i = 0; i < tupleType1.elementTypes.length; i++) {
                    if (!isUnionCompatible(
                        tupleType1.elementTypes[i],
                        tupleType2.elementTypes[i])) {
                            return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Returns whether two hierarchies are equal.
     *
     * @param hierarchy1 First hierarchy
     * @param hierarchy2 Second hierarchy
     * @return Whether hierarchies are equal
     */
    private static boolean equalHierarchy(
        final Hierarchy hierarchy1,
        final Hierarchy hierarchy2)
    {
        return hierarchy1 == null
            || hierarchy2 == null
            || hierarchy2.getUniqueName().equals(
                hierarchy1.getUniqueName());
    }

    /**
     * Returns whether a value of a given type can be evaluated to a scalar
     * value.
     *
     * <p>The rules are as follows:<ul>
     * <li>Clearly boolean, numeric and string expressions can be evaluated.
     * <li>Member and tuple expressions can be interpreted as a scalar value.
     *     The expression is evaluated to establish the context where a measure
     *     can be evaluated.
     * <li>Hierarchy and dimension expressions are implicitly
     *     converted into the current member, and evaluated as above.
     * <li>Level expressions cannot be evaluated
     * <li>Cube and Set (even sets with a single member) cannot be evaluated.
     * </ul>
     *
     * @param type Type
     * @return Whether an expression of this type can be evaluated to yield a
     *   scalar value.
     */
    public static boolean canEvaluate(Type type) {
        return ! (type instanceof SetType
                  || type instanceof CubeType
                  || type instanceof LevelType);
    }

    /**
     * Returns whether a type is a set type.
     *
     * @param type Type
     * @return Whether a value of this type can be evaluated to yield a set.
     */
    public static boolean isSet(Type type) {
        return type instanceof SetType;
    }

    public static boolean couldBeMember(Type type) {
        return type instanceof MemberType
            || type instanceof HierarchyType
            || type instanceof DimensionType;
    }

    /**
     * Converts a {@link Type} value to a {@link Category} ordinal.
     *
     * @param type Type
     * @return category ordinal
     */
    public static int typeToCategory(Type type) {
        if (type instanceof NullType) {
            return Category.NULL;
        } else if (type instanceof EmptyType) {
            return Category.EMPTY;
        } else if (type instanceof DateTimeType) {
            return Category.DATE_TIME;
        } else if (type instanceof DecimalType decimalType
            && decimalType.getScale() == 0)
        {
            return Category.INTEGER;
        } else if (type instanceof NumericType) {
            return Category.NUMERIC;
        } else if (type instanceof BooleanType) {
            return Category.LOGICAL;
        } else if (type instanceof DimensionType) {
            return Category.DIMENSION;
        } else if (type instanceof HierarchyType) {
            return Category.HIERARCHY;
        } else if (type instanceof MemberType) {
            return Category.MEMBER;
        } else if (type instanceof LevelType) {
            return Category.LEVEL;
        } else if (type instanceof SymbolType) {
            return Category.SYMBOL;
        } else if (type instanceof StringType) {
            return Category.STRING;
        } else if (type instanceof ScalarType) {
            return Category.VALUE;
        } else if (type instanceof SetType) {
            return Category.SET;
        } else if (type instanceof TupleType) {
            return Category.TUPLE;
        } else {
            throw Util.newInternal("Unknown type " + type);
        }
    }

    /**
     * Returns a type sufficiently broad to hold any value of several types,
     * but as narrow as possible. If there is no such type, returns null.
     *
     * <p>The result is equivalent to calling
     * {@link Type#computeCommonType(Type, int[])} pairwise.
     *
     * @param allowConversions Whether to allow implicit conversions
     * @param types Array of types
     * @return Most general type which encompases all types
     */
    public static Type computeCommonType(
        boolean allowConversions,
        Type... types)
    {
        if (types.length == 0) {
            return null;
        }
        Type type = types[0];
        int[] conversionCount = allowConversions ? new int[] {0} : null;
        for (int i = 1; i < types.length; ++i) {
            if (type == null) {
                return null;
            }
            type = type.computeCommonType(types[i], conversionCount);
        }
        return type;
    }

    /**
     * Returns whether we can convert an argument of a given category to a
     * given parameter category.
     *
     * @param ordinal argument ordinal
     * @param fromType actual argument type
     * @param to   formal parameter category
     * @param conversions list of implicit conversions required (out)
     * @return whether can convert from 'from' to 'to'
     */
    public static boolean canConvert(
        int ordinal,
        Type fromType,
        int to,
        List<FunctionResolver.Conversion> conversions)
    {
        final int from = typeToCategory(fromType);
        if (from == to) {
            return true;
        }
        RuntimeException e = null;
        switch (from) {
        case Category.ARRAY:
            return false;
        case Category.DIMENSION:
            // We can go from Dimension to Hierarchy if the dimension has a
            // default hierarchy. From there, we can go to Member or Tuple.
            // Even if the dimension does not have a default hierarchy, we claim
            // now that we can do the conversion, to prevent other overloads
            // from being chosen; we will hit an error either at compile time or
            // at run time.
            switch (to) {
            case Category.MEMBER, Category.TUPLE, Category.HIERARCHY:
                // It is more difficult to convert dimension->hierarchy than
                // hierarchy->dimension
                conversions.add(new ConversionImpl(from, to, ordinal, 2, e));
                return true;
            case Category.LEVEL:
                // It is more difficult to convert dimension->level than
                // dimension->member or dimension->hierarchy->member.
                conversions.add(new ConversionImpl(from, to, ordinal, 3, null));
                return true;
            default:
                return false;
            }
        case Category.HIERARCHY:
            // Seems funny that you can 'downcast' from a hierarchy, doesn't
            // it? But we add an implicit 'CurrentMember', for example,
            // '[Product].PrevMember' actually means
            // '[Product].CurrentMember.PrevMember'.
            if (to == Category.DIMENSION || to == Category.MEMBER || to == Category.TUPLE) {
                conversions.add(new ConversionImpl(from, to, ordinal, 1, null));
                return true;
            } else {
                return false;
            }
        case Category.LEVEL:
            switch (to) {
            case Category.DIMENSION:
                // It's more difficult to convert to a dimension than a
                // hierarchy. For example, we want '[Store City].CurrentMember'
                // to resolve to <Hierarchy>.CurrentMember rather than
                // <Dimension>.CurrentMember.
                conversions.add(new ConversionImpl(from, to, ordinal, 2, null));
                return true;
            case Category.HIERARCHY, Category.SET:
                conversions.add(new ConversionImpl(from, to, ordinal, 1, null));
                return true;
            default:
                return false;
            }
        case Category.LOGICAL:
            return Category.VALUE == to;
        case Category.MEMBER:
            switch (to) {
            case Category.DIMENSION, Category.HIERARCHY, Category.LEVEL, Category.TUPLE:
                conversions.add(new ConversionImpl(from, to, ordinal, 1, null));
                return true;
            case Category.SET:
                conversions.add(new ConversionImpl(from, to, ordinal, 2, null));
                return true;
            case Category.NUMERIC:
                conversions.add(new ConversionImpl(from, to, ordinal, 3, null));
                return true;
            case Category.VALUE, Category.STRING:
                // We assume that measures are numeric, so a cast to a string or
                // general value expression is more expensive (cost=4) than a
                // conversion to a numeric expression (cost=3).
                conversions.add(new ConversionImpl(from, to, ordinal, 4, null));
                return true;
            default:
                return false;
            }
        case Category.NUMERIC | Category.CONSTANT:
            return (to == Category.VALUE || to == Category.NUMERIC);
        case Category.NUMERIC:
            switch (to) {
            case Category.LOGICAL:
                conversions.add(new ConversionImpl(from, to, ordinal, 2, null));
                return true;
            case Category.VALUE, Category.INTEGER:
            case (Category.INTEGER | Category.CONSTANT):
            case (Category.NUMERIC | Category.CONSTANT):
                return true;
            default:
                return false;
            }
        case Category.INTEGER:
            switch (to) {
            case Category.VALUE, Category.NUMERIC:
            case (Category.INTEGER | Category.CONSTANT):
            case (Category.NUMERIC | Category.CONSTANT):
                return true;
            default:
                return false;
            }
        case Category.SET:
            return false;
        case Category.STRING | Category.CONSTANT:
            return (to == Category.VALUE || to == Category.STRING);
        case Category.STRING:
            switch (to) {
            case Category.VALUE:
            case (Category.STRING | Category.CONSTANT):
                return true;
            default:
                return false;
            }
        case Category.DATE_TIME | Category.CONSTANT:
            return to == Category.VALUE || to == Category.DATE_TIME;
        case Category.DATE_TIME:
            switch (to) {
            case Category.VALUE:
            case (Category.DATE_TIME | Category.CONSTANT):
                return true;
            default:
                return false;
            }
        case Category.TUPLE:
            switch (to) {
            case Category.NUMERIC:
                conversions.add(new ConversionImpl(from, to, ordinal, 3, null));
                return true;
            case Category.SET:
                conversions.add(new ConversionImpl(from, to, ordinal, 2, null));
                return true;
            case Category.STRING, Category.VALUE:
                // We assume that measures are numeric, so a cast to a string or
                // general value expression is more expensive (cost=4) than a
                // conversion to a numeric expression (cost=3).
                conversions.add(new ConversionImpl(from, to, ordinal, 4, null));
                return true;
            default:
                return false;
            }
        case Category.VALUE:
            // We can implicitly cast from value to a more specific scalar type,
            // but the cost is significant.
            if (to == Category.STRING || to == Category.NUMERIC || to == Category.LOGICAL) {
                conversions.add(new ConversionImpl(from, to, ordinal, 2, null));
                return true;
            } else {
                return false;
            }
        case Category.SYMBOL:
            return false;
        case Category.NULL:
            // now null supports members as well as scalars; but scalar is
            // preferred
            if (Category.isScalar(to)) {
                return true;
            } else if (to == Category.MEMBER) {
                conversions.add(new ConversionImpl(from, to, ordinal, 2, null));
                return true;
            } else {
                return false;
            }
        case Category.EMPTY:
            return false;
        default:
            throw Util.newInternal(
                new StringBuilder("unknown category ").append(from).append(" for type ").append(fromType).toString());
        }
    }

    static <T> T neq(T t1, T t2) {
        if (t1 == null) {
            return t2;
        } else {
            if (t2 == null) {
                return t1;
            } else {
                return t1.equals(t2) ? t1 : null;
            }
        }
    }

    /**
     * Returns the hierarchies in a set, member, or tuple type.
     *
     * @param type Type
     * @return List of hierarchies
     */
    public static List<Hierarchy> getHierarchies(Type type) {
        if (type instanceof SetType setType) {
            type = setType.getElementType();
        }
        if (type instanceof TupleType tupleType) {
            List<Hierarchy> hierarchyList = new ArrayList<>();
            for (Type elementType : tupleType.elementTypes) {
                hierarchyList.add(elementType.getHierarchy());
            }
            return hierarchyList;
        } else {
            Hierarchy hierarchy = type.getHierarchy();
            return hierarchy == null
                ? Collections.<Hierarchy>emptyList()
                : Collections.singletonList(hierarchy);
        }
    }

    /**
     * Implementation of {@link org.eclipse.daanse.olap.api.function.FunctionResolver.Conversion}.
     */
    private static class ConversionImpl implements FunctionResolver.Conversion {
        final int from;
        final int to;
        /**
         * Which argument. Arguments are 0-based, and in particular the 'this'
         * of a call of member or method call syntax is argument 0. Argument -1
         * is the return.
         */
        final int ordinal;

        /**
         * Score of the conversion. A higher value is more onerous and therefore
         * a call using such a conversion is less likly to be chosen.
         */
        final int cost;

        final RuntimeException e;

        /**
         * Creates a conversion.
         *
         * @param from From type
         * @param to To type
         * @param ordinal Ordinal of argument
         * @param cost Cost of conversion
         * @param e Exception
         */
        public ConversionImpl(
            int from,
            int to,
            int ordinal,
            int cost,
            RuntimeException e)
        {
            this.from = from;
            this.to = to;
            this.ordinal = ordinal;
            this.cost = cost;
            this.e = e;
        }

        @Override
		public int getCost() {
            return cost;
        }

        @Override
		public void checkValid() {
            if (e != null) {
                throw e;
            }
        }

        @Override
		public void apply(Validator validator, List<Expression> args) {
            final Expression arg = args.get(ordinal);
            if ((from == Category.MEMBER || from == Category.TUPLE) && to == Category.SET) {
                final Expression newArg =
                    validator.validate(
                        new UnresolvedFunCallImpl(
                            "{}", Syntax.Braces, new Expression[]{arg}), false);
                args.set(ordinal, newArg);
            }
        }

        // for debug
        @Override
		public String toString() {
            return new StringBuilder("Conversion(from=").append(Category.instance().getName(from))
                .append(", to=").append(Category.instance().getName(to))
                .append(", ordinal=")
                .append(ordinal).append(", cost=")
                .append(cost).append(", e=").append(e).append(")").toString();
        }
    }
}
