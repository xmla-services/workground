/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2003-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara
// All Rights Reserved.
*/

package mondrian.olap.fun;

import java.util.ArrayList;
import java.util.List;

import mondrian.olap.interfaces.DimensionExpr;
import mondrian.olap.interfaces.Id;
import mondrian.olap.interfaces.LevelExpr;
import mondrian.olap.interfaces.Literal;
import org.eclipse.daanse.olap.api.model.Dimension;

import mondrian.mdx.HierarchyExprImpl;
import mondrian.mdx.MemberExpr;
import mondrian.mdx.ParameterExprImpl;
import mondrian.olap.Category;
import mondrian.olap.Exp;
import mondrian.olap.FunCall;
import mondrian.olap.FunDef;
import mondrian.olap.Parameter;
import mondrian.olap.Util;
import mondrian.olap.Validator;
import mondrian.olap.type.MemberType;
import mondrian.olap.type.NumericType;
import mondrian.olap.type.SetType;
import mondrian.olap.type.StringType;
import mondrian.olap.type.Type;

/**
 * A <code>ParameterFunDef</code> is a pseudo-function describing calls to
 * <code>Parameter</code> and <code>ParamRef</code> functions. It exists only
 * fleetingly, and is then converted into a {@link mondrian.olap.Parameter}.
 * For internal use only.
 *
 * @author jhyde
 * @since Feb 14, 2003
 */
public class ParameterFunDef extends FunDefBase {
    public final String parameterName;
    private final Type type;
    public final Exp exp;
    public final String parameterDescription;

    ParameterFunDef(
        FunDef funDef,
        String parameterName,
        Type type,
        int returnCategory,
        Exp exp,
        String description)
    {
        super(
            funDef.getName(),
            funDef.getSignature(),
            funDef.getDescription(),
            funDef.getSyntax(),
            returnCategory,
            funDef.getParameterCategories());
        Util.assertPrecondition(
            getName().equals("Parameter")
            || getName().equals("ParamRef"));
        this.parameterName = parameterName;
        this.type = type;
        this.exp = exp;
        this.parameterDescription = description;
    }

    @Override
	public Exp createCall(Validator validator, Exp[] args) {
        Parameter parameter = validator.createOrLookupParam(
            this.getName().equals("Parameter"),
            parameterName, type, exp, parameterDescription);
        return new ParameterExprImpl(parameter);
    }

    @Override
	public Type getResultType(Validator validator, Exp[] args) {
        return type;
    }

    private static boolean isConstant(Exp typeArg) {
        if (typeArg instanceof LevelExpr) {
            // e.g. "[Time].[Quarter]"
            return true;
        }
        if (typeArg instanceof HierarchyExprImpl) {
            // e.g. "[Time].[By Week]"
            return true;
        }
        if (typeArg instanceof DimensionExpr) {
            // e.g. "[Time]"
            return true;
        }
        if (typeArg instanceof FunCall hierarchyCall) {
            if (hierarchyCall.getFunName().equals("Hierarchy")
                && hierarchyCall.getArgCount() > 0
                && hierarchyCall.getArg(0) instanceof FunCall)
            {
                FunCall currentMemberCall = (FunCall) hierarchyCall.getArg(0);
                if (currentMemberCall.getFunName().equals("CurrentMember")
                    && currentMemberCall.getArgCount() > 0
                    && currentMemberCall.getArg(0) instanceof DimensionExpr)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getParameterName(Exp[] args) {
        if (args[0] instanceof Literal
            && args[0].getCategory() == Category.STRING)
        {
            return (String) ((Literal) args[0]).getValue();
        } else {
            throw Util.newInternal("Parameter name must be a string constant");
        }
    }

    /**
     * Returns an approximate type for a parameter, based upon the 1'th
     * argument. Does not use the default value expression, so this method
     * can safely be used before the expression has been validated.
     */
    public static Type getParameterType(Exp[] args) {
        if (args[1] instanceof Id id) {
            String[] names = id.toStringArray();
            if (names.length == 1) {
                final String name = names[0];
                if (name.equals("NUMERIC")) {
                    return new NumericType();
                }
                if (name.equals("STRING")) {
                    return new StringType();
                }
            }
        } else if (args[1] instanceof Literal literal) {
            if (literal.getValue().equals("NUMERIC")) {
                return new NumericType();
            } else if (literal.getValue().equals("STRING")) {
                return new StringType();
            }
        } else if (args[1] instanceof MemberExpr) {
            return new MemberType(null, null, null, null);
        }
        return new StringType();
    }

    /**
     * Resolves calls to the <code>Parameter</code> MDX function.
     */
    public static class ParameterResolver extends MultiResolver {
        private static final String[] SIGNATURES = {
            // Parameter(string const, symbol, string[, string const]): string
            "fS#yS#", "fS#yS",
            // Parameter(string const, symbol, numeric[, string const]): numeric
            "fn#yn#", "fn#yn",
            // Parameter(string const, hierarchy constant, member[, string
            // const[, symbol]]): member
            "fm#hm#", "fm#hm",
            // Parameter(string const, hierarchy constant, set[, string
            // const]): set
            "fx#hx#", "fx#hx",
        };

        public ParameterResolver() {
            super(
                "Parameter",
                "Parameter(<Name>, <Type>, <DefaultValue>, <Description>, <Set>)",
                "Returns default value of parameter.",
                ParameterResolver.SIGNATURES);
        }

        @Override
		public String[] getReservedWords() {
            return new String[]{"NUMERIC", "STRING"};
        }

        @Override
		protected FunDef createFunDef(Exp[] args, FunDef dummyFunDef) {
            String parameterName = ParameterFunDef.getParameterName(args);
            Exp typeArg = args[1];
            int category;
            Type type = typeArg.getType();
            switch (typeArg.getCategory()) {
            case Category.DIMENSION:
            case Category.HIERARCHY:
            case Category.LEVEL:
                Dimension dimension = type.getDimension();
                if (!ParameterFunDef.isConstant(typeArg)) {
                    throw FunUtil.newEvalException(
                        dummyFunDef,
                        new StringBuilder("Invalid parameter '").append(parameterName)
                        .append("'. Type must be a NUMERIC, STRING, or a dimension, ")
                        .append("hierarchy or level").toString());
                }
                if (dimension == null) {
                    throw FunUtil.newEvalException(
                        dummyFunDef,
                        new StringBuilder("Invalid dimension for parameter '")
                        .append(parameterName).append("'").toString());
                }
                type =
                    new MemberType(
                        type.getDimension(),
                        type.getHierarchy(),
                        type.getLevel(),
                        null);
                category = Category.MEMBER;
                break;

            case Category.SYMBOL:
                String s = (String) ((Literal) typeArg).getValue();
                if (s.equalsIgnoreCase("NUMERIC")) {
                    category = Category.NUMERIC;
                    type = new NumericType();
                    break;
                } else if (s.equalsIgnoreCase("STRING")) {
                    category = Category.STRING;
                    type = new StringType();
                    break;
                }
                // fall through and throw error
            default:
                // Error is internal because the function call has already been
                // type-checked.
                throw FunUtil.newEvalException(
                    dummyFunDef,
                    new StringBuilder("Invalid type for parameter '").append(parameterName)
                    .append("'; expecting NUMERIC, STRING or a hierarchy").toString());
            }

            // Default value
            Exp exp = args[2];
            Validator validator =
                Util.createSimpleValidator(BuiltinFunTable.instance());
            final List<Conversion> conversionList = new ArrayList<>();
            String typeName = Category.instance.getName(category).toUpperCase();
            if (!validator.canConvert(2, exp, category, conversionList)) {
                throw FunUtil.newEvalException(
                    dummyFunDef,
                    new StringBuilder("Default value of parameter '").append(parameterName)
                    .append("' is inconsistent with its type, ").append(typeName).toString());
            }
            if (exp.getCategory() == Category.SET
                && category == Category.MEMBER)
            {
                // Default value is a set; take this an indication that
                // the type is 'set of <member type>'.
                type = new SetType(type);
            }
            if (category == Category.MEMBER) {
                Type expType = exp.getType();
                if (expType instanceof SetType) {
                    expType = ((SetType) expType).getElementType();
                }
                if (ParameterResolver.distinctFrom(type.getDimension(), expType.getDimension())
                    || ParameterResolver.distinctFrom(type.getHierarchy(), expType.getHierarchy())
                    || ParameterResolver.distinctFrom(type.getLevel(), expType.getLevel()))
                {
                    throw FunUtil.newEvalException(
                        dummyFunDef,
                        new StringBuilder("Default value of parameter '").append(parameterName)
                        .append("' is not consistent with the parameter type '")
                        .append(type).toString());
                }
            }

            String parameterDescription = null;
            if (args.length > 3) {
                if (args[3] instanceof Literal
                    && args[3].getCategory() == Category.STRING)
                {
                    parameterDescription =
                        (String) ((Literal) args[3]).getValue();
                } else {
                    throw FunUtil.newEvalException(
                        dummyFunDef,
                        new StringBuilder("Description of parameter '").append(parameterName)
                        .append("' must be a string constant").toString());
                }
            }

            return new ParameterFunDef(
                dummyFunDef, parameterName, type, category,
                exp, parameterDescription);
        }

        private static <T> boolean distinctFrom(T t1, T t2) {
            return t1 != null
               && t2 != null
               && !t1.equals(t2);
        }
    }

    /**
     * Resolves calls to the <code>ParamRef</code> MDX function.
     */
    public static class ParamRefResolver extends MultiResolver {
        public ParamRefResolver() {
            super(
                "ParamRef",
                "ParamRef(<Name>)",
                "Returns the current value of this parameter. If it is null, returns the default value.",
                new String[]{"fv#"});
        }

        @Override
		protected FunDef createFunDef(Exp[] args, FunDef dummyFunDef) {
            String parameterName = ParameterFunDef.getParameterName(args);
            return new ParameterFunDef(
                dummyFunDef, parameterName, null, Category.UNKNOWN, null,
                null);
        }
    }
}
