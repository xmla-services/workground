/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.olap.fun;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.api.model.Dimension;
import org.eclipse.daanse.olap.api.model.Hierarchy;
import org.eclipse.daanse.olap.calc.api.StringCalc;

import mondrian.calc.Calc;
import mondrian.calc.ExpCompiler;
import mondrian.calc.TupleList;
import mondrian.calc.impl.AbstractListCalc;
import mondrian.calc.impl.UnaryTupleList;
import mondrian.mdx.DimensionExpr;
import mondrian.mdx.HierarchyExpr;
import mondrian.mdx.ResolvedFunCall;
import mondrian.olap.Category;
import mondrian.olap.Evaluator;
import mondrian.olap.Exp;
import mondrian.olap.FunDef;
import mondrian.olap.Syntax;
import mondrian.olap.Validator;
import mondrian.olap.type.MemberType;
import mondrian.olap.type.NullType;
import mondrian.olap.type.SetType;
import mondrian.olap.type.StringType;
import mondrian.olap.type.TupleType;
import mondrian.olap.type.Type;
import mondrian.olap.type.TypeUtil;
import mondrian.resource.MondrianResource;

/**
 * Definition of the <code>StrToSet</code> MDX builtin function.
 *
 * @author jhyde
 * @since Mar 23, 2006
 */
class StrToSetFunDef extends FunDefBase {
    static final ResolverImpl Resolver = new ResolverImpl();

    private StrToSetFunDef(int[] parameterTypes) {
        super(
            "StrToSet",
            "<Set> StrToSet(<String>[, <Hierarchy>...])",
            "Constructs a set from a string expression.",
            Syntax.Function, Category.SET, parameterTypes);
    }

    @Override
	public Calc compileCall(ResolvedFunCall call, ExpCompiler compiler) {
        final StringCalc stringCalc = compiler.compileString(call.getArg(0));
        SetType type = (SetType) call.getType();
        Type elementType = type.getElementType();
        if (elementType instanceof MemberType) {
            final Hierarchy hierarchy = elementType.getHierarchy();
            return new AbstractListCalc(call.getType(), new Calc[] {stringCalc}) {
                @Override
				public TupleList evaluateList(Evaluator evaluator) {
                    String string = stringCalc.evaluate(evaluator);
                    if (string == null) {
                        throw FunUtil.newEvalException(
                            MondrianResource.instance().NullValue.ex());
                    }
                    return new UnaryTupleList(
                        FunUtil.parseMemberList(evaluator, string, hierarchy));
                }
            };
        } else {
            TupleType tupleType = (TupleType) elementType;
            final List<Hierarchy> hierarchyList = tupleType.getHierarchies();
            return new AbstractListCalc(call.getType(), new Calc[] {stringCalc}) {
                @Override
				public TupleList evaluateList(Evaluator evaluator) {
                    String string = stringCalc.evaluate(evaluator);
                    if (string == null) {
                        throw FunUtil.newEvalException(
                            MondrianResource.instance().NullValue.ex());
                    }
                    return FunUtil.parseTupleList(evaluator, string, hierarchyList);
                }
            };
        }
    }

    @Override
	public Exp createCall(Validator validator, Exp[] args) {
        final int argCount = args.length;
        if (argCount <= 1) {
            throw MondrianResource.instance().MdxFuncArgumentsNum.ex(getName());
        }
        for (int i = 1; i < argCount; i++) {
            final Exp arg = args[i];
            if (arg instanceof DimensionExpr dimensionExpr) {
                Dimension dimension = dimensionExpr.getDimension();
                args[i] = new HierarchyExpr(dimension.getHierarchy());
            } else if (arg instanceof HierarchyExpr) {
                // nothing
            } else {
                throw MondrianResource.instance().MdxFuncNotHier.ex(
                    i + 1, getName());
            }
        }
        return super.createCall(validator, args);
    }

    @Override
	public Type getResultType(Validator validator, Exp[] args) {
        switch (args.length) {
        case 1:
            // This is a call to the standard version of StrToSet,
            // which doesn't give us any hints about type.
            return new SetType(null);

        case 2:
        {
            final Type argType = args[1].getType();
            return new SetType(
                new MemberType(
                    argType.getDimension(),
                    argType.getHierarchy(),
                    argType.getLevel(),
                    null));
        }

        default:
        {
            // This is a call to Mondrian's extended version of
            // StrToSet, of the form
            //   StrToSet(s, <Hier1>, ... , <HierN>)
            //
            // The result is a set of tuples
            //  (<Hier1>, ... ,  <HierN>)
            final List<MemberType> list = new ArrayList<>();
            for (int i = 1; i < args.length; i++) {
                Exp arg = args[i];
                final Type argType = arg.getType();
                list.add(TypeUtil.toMemberType(argType));
            }
            final MemberType[] types =
                list.toArray(new MemberType[list.size()]);
            TupleType.checkHierarchies(types);
            return new SetType(new TupleType(types));
        }
        }
    }

    private static class ResolverImpl extends ResolverBase {
        ResolverImpl() {
            super(
                "StrToSet",
                "StrToSet(<String Expression>)",
                "Constructs a set from a string expression.",
                Syntax.Function);
        }

        @Override
		public FunDef resolve(
            Exp[] args,
            Validator validator,
            List<Conversion> conversions)
        {
            if (args.length < 1) {
                return null;
            }
            Type type = args[0].getType();
            if (!(type instanceof StringType)
                && !(type instanceof NullType))
            {
                return null;
            }
            for (int i = 1; i < args.length; i++) {
                Exp exp = args[i];
                if (!(exp instanceof DimensionExpr
                      || exp instanceof HierarchyExpr))
                {
                    return null;
                }
            }
            int[] argTypes = new int[args.length];
            argTypes[0] = Category.STRING;
            for (int i = 1; i < argTypes.length; i++) {
                argTypes[i] = Category.HIERARCHY;
            }
            return new StrToSetFunDef(argTypes);
        }

        @Override
		public FunDef getRepresentativeFunDef() {
            return new StrToSetFunDef(new int[] {Category.STRING});
        }
    }
}
