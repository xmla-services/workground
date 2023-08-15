/*
 * This software is subject to the terms of the Eclipse Public License v1.0
 * Agreement, available at the following URL:
 * http://www.eclipse.org/legal/epl-v10.html.
 * You must accept the terms of that agreement to use this software.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

package mondrian.calc.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.daanse.calc.api.BooleanCalc;
import org.eclipse.daanse.calc.api.DateTimeCalc;
import org.eclipse.daanse.calc.api.DimensionCalc;
import org.eclipse.daanse.calc.api.DoubleCalc;
import org.eclipse.daanse.calc.api.IntegerCalc;
import org.eclipse.daanse.calc.api.StringCalc;
import org.eclipse.daanse.calc.impl.AbstractProfilingNestedBooleanCalc;
import org.eclipse.daanse.calc.impl.AbstractProfilingNestedDimensionCalc;
import org.eclipse.daanse.calc.impl.AbstractProfilingNestedDoubleCalc;
import org.eclipse.daanse.calc.impl.AbstractProfilingNestedIntegerCalc;
import org.eclipse.daanse.calc.impl.AbstractProfilingNestedStringCalc;
import org.eclipse.daanse.calc.impl.ConstantBooleanProfilingCalc;
import org.eclipse.daanse.calc.impl.ConstantDoubleProfilingCalc;
import org.eclipse.daanse.calc.impl.ConstantIntegerProfilingCalc;
import org.eclipse.daanse.calc.impl.ConstantStringProfilingCalc;
import org.eclipse.daanse.olap.api.model.Dimension;
import org.eclipse.daanse.olap.api.model.Hierarchy;

import mondrian.calc.Calc;
import mondrian.calc.ExpCompiler;
import mondrian.calc.HierarchyCalc;
import mondrian.calc.IterCalc;
import mondrian.calc.LevelCalc;
import mondrian.calc.ListCalc;
import mondrian.calc.MemberCalc;
import mondrian.calc.ParameterSlot;
import mondrian.calc.ResultStyle;
import mondrian.calc.TupleCalc;
import mondrian.calc.TupleList;
import mondrian.mdx.MemberExpr;
import mondrian.mdx.UnresolvedFunCall;
import mondrian.olap.Category;
import mondrian.olap.Evaluator;
import mondrian.olap.Exp;
import mondrian.olap.Literal;
import mondrian.olap.Parameter;
import mondrian.olap.Syntax;
import mondrian.olap.Util;
import mondrian.olap.Validator;
import mondrian.olap.fun.FunUtil;
import mondrian.olap.fun.HierarchyCurrentMemberFunDef;
import mondrian.olap.fun.HierarchyDimensionFunDef;
import mondrian.olap.fun.LevelHierarchyFunDef;
import mondrian.olap.fun.MemberHierarchyFunDef;
import mondrian.olap.fun.MemberLevelFunDef;
import mondrian.olap.type.BooleanType;
import mondrian.olap.type.DecimalType;
import mondrian.olap.type.DimensionType;
import mondrian.olap.type.HierarchyType;
import mondrian.olap.type.LevelType;
import mondrian.olap.type.MemberType;
import mondrian.olap.type.NullType;
import mondrian.olap.type.NumericType;
import mondrian.olap.type.ScalarType;
import mondrian.olap.type.SetType;
import mondrian.olap.type.StringType;
import mondrian.olap.type.TupleType;
import mondrian.olap.type.Type;
import mondrian.olap.type.TypeUtil;
import mondrian.resource.MondrianResource;

/**
 * Abstract implementation of the {@link mondrian.calc.ExpCompiler} interface.
 *
 * @author jhyde
 * @since Sep 29, 2005
 */
public class AbstractExpCompiler implements ExpCompiler {
    private final Evaluator evaluator;
    private final Validator validator;
    private final Map<Parameter, ParameterSlotImpl> parameterSlots =
            new HashMap<>();
    private List<ResultStyle> resultStyles;

    /**
     * Creates an AbstractExpCompiler
     *
     * @param evaluator Evaluator
     * @param validator Validator
     */
    public AbstractExpCompiler(Evaluator evaluator, Validator validator) {
        this(evaluator, validator, ResultStyle.ANY_LIST);
    }

    /**
     * Creates an AbstractExpCompiler which is constrained to produce one of
     * a set of result styles.
     *
     * @param evaluator Evaluator
     * @param validator Validator
     * @param resultStyles List of result styles, preferred first, must not be
     */
    public AbstractExpCompiler(
            Evaluator evaluator,
            Validator validator,
            List<ResultStyle> resultStyles)
    {
        this.evaluator = evaluator;
        this.validator = validator;
        this.resultStyles = resultStyles == null
                ? ResultStyle.ANY_LIST : resultStyles;
    }

    @Override
    public Evaluator getEvaluator() {
        return evaluator;
    }

    @Override
    public Validator getValidator() {
        return validator;
    }

    /**
     * {@inheritDoc}
     *
     * Uses the current ResultStyle to compile the expression.
     */
    @Override
    public Calc compile(Exp exp) {
        return exp.accept(this);
    }

    /**
     * {@inheritDoc}
     *
     * Uses a new ResultStyle to compile the expression.
     */
    @Override
    public Calc compileAs(
            Exp exp,
            Type resultType,
            List<ResultStyle> preferredResultTypes)
    {
        if (preferredResultTypes == null) {
            throw new IllegalArgumentException("preferredResultTypes should not be null");
        }
        int substitutions = 0;
        if (Util.RETROWOVEN) {
            // Copy and replace ITERABLE
            // A number of functions declare that they can accept
            // ITERABLEs so here is where that those are converted to innocent
            // LISTs for jdk1.4 and other retrowoven code.
            final List<ResultStyle> tmp =
                    new ArrayList<>(preferredResultTypes.size());
            for (ResultStyle preferredResultType : preferredResultTypes) {
                if (preferredResultType == ResultStyle.ITERABLE) {
                    preferredResultType = ResultStyle.LIST;
                    ++substitutions;
                }
                tmp.add(preferredResultType);
            }
            preferredResultTypes = tmp;
        }
        final List<ResultStyle> save = resultStyles;
        try {
            resultStyles = preferredResultTypes;
            if (resultType != null && resultType != exp.getType()) {
                if (resultType instanceof MemberType) {
                    return compileMember(exp);
                }
                if (resultType instanceof LevelType) {
                    return compileLevel(exp);
                } else if (resultType instanceof HierarchyType) {
                    return compileHierarchy(exp);
                } else if (resultType instanceof DimensionType) {
                    return compileDimension(exp);
                } else if (resultType instanceof ScalarType) {
                    return compileScalar(exp, false);
                }
            }
            final Calc calc = compile(exp);
            if (substitutions > 0) {
                final IterCalc iterCalc = (IterCalc) calc;
                if (iterCalc == null) {
                    resultStyles =
                            Collections.singletonList(ResultStyle.ITERABLE);
                    return compile(exp);
                }
                return iterCalc;
            }
            return calc;
        } finally {
            resultStyles = save;
        }
    }

    @Override
    public MemberCalc compileMember(Exp exp) {
        final Type type = exp.getType();
        if (type instanceof HierarchyType) {
            final HierarchyCalc hierarchyCalc = compileHierarchy(exp);
            return hierarchyToMember(hierarchyCalc);
        }
        if (type instanceof NullType) {
            throw MondrianResource.instance().NullNotSupported.ex();
        } else if (type instanceof DimensionType) {
            final HierarchyCalc hierarchyCalc = compileHierarchy(exp);
            return hierarchyToMember(hierarchyCalc);
        }
        assert type instanceof MemberType : type;
        return (MemberCalc) compile(exp);
    }

    private MemberCalc hierarchyToMember(
            HierarchyCalc hierarchyCalc)
    {
        final Hierarchy hierarchy = hierarchyCalc.getType().getHierarchy();
        if (hierarchy != null) {
            return new HierarchyCurrentMemberFunDef.FixedCalcImpl(
                    TypeUtil.toMemberType(hierarchyCalc.getType()),
                    hierarchy);
        }
        return new HierarchyCurrentMemberFunDef.CalcImpl(
                TypeUtil.toMemberType(hierarchyCalc.getType()),
                hierarchyCalc);
    }

    @Override
    public LevelCalc compileLevel(Exp exp) {
        final Type type = exp.getType();
        if (type instanceof MemberType) {
            // <Member> --> <Member>.Level
            final MemberCalc memberCalc = compileMember(exp);
            return new MemberLevelFunDef.CalcImpl(
                    LevelType.forType(type),
                    memberCalc);
        }
        assert type instanceof LevelType;
        return (LevelCalc) compile(exp);
    }

    @Override
    public DimensionCalc compileDimension(Exp exp) {
        final Type type = exp.getType();
        if (type instanceof HierarchyType) {
            final HierarchyCalc hierarchyCalc = compileHierarchy(exp);
            return new HierarchyDimensionFunDef.CalcImpl(
                    new DimensionType(type.getDimension()),
                    hierarchyCalc);
        }
        assert type instanceof DimensionType : type;
        Calc<?> calc=	compile(exp);
        
        if(calc instanceof DimensionCalc dimCalc) {
        	return dimCalc;
        }
		return new AbstractProfilingNestedDimensionCalc("AbstractProfilingNestedDimensionCalc", type, new Calc[] { calc }) {

			@Override
			public Dimension evaluate(Evaluator evaluator) {
				Object o = calc.evaluate(evaluator);
				if (o instanceof Dimension d) {
					return d;
				}
				throw evaluator.newEvalException(null, "expected Dimension, was: " + o);
			}
		};
    }

    @Override
    public HierarchyCalc compileHierarchy(Exp exp) {
        final Type type = exp.getType();
        if (type instanceof DimensionType) {
            // <Dimension> --> unique Hierarchy else error
            // Resolve at compile time if constant
            final Dimension dimension = type.getDimension();
            if (dimension != null) {
                final Hierarchy hierarchy =
                        FunUtil.getDimensionDefaultHierarchy(dimension);
                if (hierarchy != null) {
                    return (HierarchyCalc) ConstantCalc.constantHierarchy(
                            hierarchy);
                }
                // SSAS gives error at run time (often as an error in a
                // cell) but we prefer to give an error at validate time.
                throw MondrianResource.instance()
                .CannotImplicitlyConvertDimensionToHierarchy.ex(
                        dimension.getName());
            }
            final DimensionCalc dimensionCalc = compileDimension(exp);
            return new DimensionHierarchyCalc(
            		HierarchyType.forType(type),
                    dimensionCalc);
        }
        if (type instanceof MemberType) {
            // <Member> --> <Member>.Hierarchy
            final MemberCalc memberCalc = compileMember(exp);
            return new MemberHierarchyFunDef.CalcImpl(
            		HierarchyType.forType(type),
                    memberCalc);
        }
        if (type instanceof LevelType) {
            // <Level> --> <Level>.Hierarchy
            final LevelCalc levelCalc = compileLevel(exp);
            return new LevelHierarchyFunDef.CalcImpl(
            		HierarchyType.forType(type),
                    levelCalc);
        }
        assert type instanceof HierarchyType;
        return (HierarchyCalc) compile(exp);
    }

    @Override
    public IntegerCalc compileInteger(Exp exp) {
        final Calc calc = compileScalar(exp, false);
        final Type type = calc.getType();
        if (type instanceof DecimalType decimalType
                && decimalType.getScale() == 0)
        {
            return (IntegerCalc) calc;
        }
		if (type instanceof NullType) {
			if (calc instanceof org.eclipse.daanse.calc.api.ConstantCalc<?> constantCalc) {
				//no evaluate on constantCalc  result is null and constant - nothing expected while evaluate
				return new ConstantIntegerProfilingCalc(new DecimalType(Integer.MAX_VALUE, 0), null);
			}		

		}
		if (type instanceof NumericType) {
			if (calc instanceof org.eclipse.daanse.calc.api.ConstantCalc<?> constantCalc) {

				Object o = constantCalc.evaluate(evaluator);
				Integer i = null;
				if (o != null) {
					Number n = (Number) o;
					i = n.intValue();
				}
				return new ConstantIntegerProfilingCalc(new DecimalType(Integer.MAX_VALUE, 0), i);
			} else if (calc instanceof DoubleCalc doubleCalc) {
				return new AbstractProfilingNestedIntegerCalc("AbstractIntegerCalc", exp.getType(),
						new Calc[] { doubleCalc }) {
					@Override
					public Integer evaluate(Evaluator evaluator) {
						Double d = doubleCalc.evaluate(evaluator);
						if (d == null) {
							return null;
						}
						return d.intValue();
					}
				};
			}

		} else {
			return new AbstractProfilingNestedIntegerCalc("AbstractIntegerCalc", new DecimalType(Integer.MAX_VALUE, 0),
					new Calc[] { calc }) {
				@Override
				public Integer evaluate(Evaluator evaluator) {
					Object o = calc.evaluate(evaluator);
					if (o == null) {
						return null;
					} else if (o instanceof Number n) {
						return n.intValue();
					}

					throw evaluator.newEvalException(null, "expected NUMBER was " + o);
				}
			};
		}
        
        return (IntegerCalc) calc;
    }

	@Override
	public StringCalc compileString(Exp exp) {
		Calc<?> c = compileScalar(exp, false);

		if (c instanceof StringCalc sc) {
			return sc;
		}else if (c instanceof org.eclipse.daanse.calc.api.ConstantCalc cc) {
			Object o = cc.evaluate(null);
			String s = null;
			if (o != null) {
				s = o.toString();
			}
			return new ConstantStringProfilingCalc(new StringType(), s);
		}else {
			return new AbstractProfilingNestedStringCalc("AbstractProfilingNestedStringCalc", new StringType(), new Calc[] {c}) {

				@Override
				public String evaluate(Evaluator evaluator) {
					
					Object o = c.evaluate(evaluator);
					
					String s = null;
					if (o != null) {
						s = o.toString();
					}
					return s;
				}
		
			
			};
		}
	}

    @Override
    public DateTimeCalc compileDateTime(Exp exp) {
        return (DateTimeCalc) compileScalar(exp, false);
    }

    @Override
    public ListCalc compileList(Exp exp) {
        return compileList(exp, false);
    }

    @Override
    public ListCalc compileList(Exp exp, boolean mutable) {
        if (!(exp.getType() instanceof SetType)) {
            throw new IllegalArgumentException("must be a set: " + exp);
        }
        final List<ResultStyle> resultStyleList;
        if (mutable) {
            resultStyleList = ResultStyle.MUTABLELIST_ONLY;
        } else {
            resultStyleList = ResultStyle.LIST_ONLY;
        }
        Calc calc = compileAs(exp, null, resultStyleList);
        if (calc instanceof ListCalc listCalc) {
            return listCalc;
        }
        if (calc == null) {
            calc = compileAs(exp, null, ResultStyle.ITERABLE_ANY);
            assert calc != null;
        }
        if (calc instanceof ListCalc listCalc) {
        	return listCalc;
        }
        // If expression is an iterator, convert it to a list. Don't check
        // 'calc instanceof IterCalc' because some generic calcs implement both
        // ListCalc and IterCalc.
        if (!(calc instanceof ListCalc)) {
            return toList((IterCalc) calc);
        }
        // A set can only be implemented as a list or an iterable.
        throw Util.newInternal("Cannot convert calc to list: " + calc);
    }

    /**
     * Converts an iterable over tuples to a list of tuples.
     *
     * @param calc Calc
     * @return List calculation.
     */
    public ListCalc toList(IterCalc calc) {
        return new IterableListCalc(calc);
    }

    @Override
    public IterCalc compileIter(Exp exp) {
        IterCalc calc =
                (IterCalc) compileAs(exp, null, ResultStyle.ITERABLE_ONLY);
        if (calc == null) {
            calc = (IterCalc) compileAs(exp, null, ResultStyle.ANY_ONLY);
            assert calc != null;
        }
        return calc;
    }

    @Override
    public BooleanCalc compileBoolean(Exp exp) {
        final Calc calc = compileScalar(exp, false);
        if (calc instanceof BooleanCalc bc) {
            return bc;
        }
        //
        if (calc instanceof ConstantCalc constantCalc)
        {
        	Object o=constantCalc.evaluate(null);
        	
        	Boolean b = null;
        	if( o ==null) {
        		b=FunUtil.BOOLEAN_NULL;
        	}else if (o instanceof Boolean bt) {
				b=bt;
			}else if (o instanceof Number n) {
				b=n.intValue()>0;
			}else {
				throw new RuntimeException("wring type. was: "+o);
			}
            return 	new ConstantBooleanProfilingCalc(b);
        }
        //
        
        
        if (calc instanceof DoubleCalc doubleCalc) {
            return new AbstractProfilingNestedBooleanCalc("AbstractProfilingNestedBooleanCalc",exp.getType(), new Calc[] {doubleCalc}) {
                @Override
				public Boolean evaluate(Evaluator evaluator) {
					Double v0 = doubleCalc.evaluate(evaluator);

					if (Double.isNaN(v0) || v0 == FunUtil.DOUBLE_NULL) {
						return FunUtil.BOOLEAN_NULL;
					}

					return v0 != 0;
				}
            };
        } else if (calc instanceof IntegerCalc integerCalc) {
            return new AbstractProfilingNestedBooleanCalc("AbstractProfilingNestedBooleanCalc",exp.getType(), new Calc[] {integerCalc}) {
                @Override
				public Boolean evaluate(Evaluator evaluator) {
					Integer v0 = integerCalc.evaluate(evaluator);
					if (v0 == null) {
						return FunUtil.BOOLEAN_NULL;
					}
					return v0 != 0;
				}
			};
        } else {
            return new AbstractProfilingNestedBooleanCalc("AbstractProfilingNestedBooleanCalc",exp.getType(), new Calc[] {calc}) {
                @Override
				public Boolean evaluate(Evaluator evaluator) {
					Object v0 = calc.evaluate(evaluator);
					if (v0 == null) {
						return FunUtil.BOOLEAN_NULL;
					}
					if (v0 instanceof Boolean b) {
						return b;
					}if (v0 instanceof Number n) {
						return n.intValue() != 0;
					}
					throw evaluator.newEvalException(null, "wrong type, was:"+v0);
					
				}
			};
        }
    }

    @Override
    public DoubleCalc compileDouble(Exp exp) {
        final Calc calc = compileScalar(exp, false);
        if (calc instanceof ConstantCalc constantCalc
                && !(calc.evaluate(null) instanceof Double))
        {
        	Object o=constantCalc.evaluate(null);
        	
        	Double d = null;
        	if( o ==null) {
        		d=FunUtil.DOUBLE_NULL;
        	}else if (o instanceof Double dt) {
				d=dt;
			}else if (o instanceof Number n) {
				d=n.doubleValue();
			}else {
				throw new RuntimeException("wring type. was: "+o);
			}
        
            return 	new ConstantDoubleProfilingCalc(new NumericType(),d);

        }
        if (calc instanceof DoubleCalc doubleCalc) {
            return doubleCalc;
        }
        if (calc instanceof IntegerCalc integerCalc) {
            return new AbstractProfilingNestedDoubleCalc("AbstractProfilingNestedDoubleCalc",exp.getType(), new Calc[] {integerCalc}) {
                @Override
                public Double evaluate(Evaluator evaluator) {
                	
                	Integer i=integerCalc.evaluate(evaluator);
                	if(i==null) {
                		return FunUtil.DOUBLE_NULL;
                				//null;
                				// TODO: !!! JUST REFACTORING 0 must be null
                	}
                    return i.doubleValue();
                }
            };
        }

		return new AbstractProfilingNestedDoubleCalc("AbstractProfilingNestedDoubleCalc", new NumericType(), new Calc[] { calc }) {
			@Override
			public Double evaluate(Evaluator evaluator) {

				Object o = calc.evaluate(evaluator);
				if (o == null) {
					return FunUtil.DOUBLE_NULL;
					// null;
					// TODO: !!! JUST REFACTORING 0 must be null
				} else  if(Objects.equals(o, FunUtil.DOUBLE_NULL)){
					return FunUtil.DOUBLE_NULL;
				}else if (o instanceof Double d) {
					return d;
				} else if (o instanceof Number n) {
					return n.doubleValue();
				}
				throw evaluator.newEvalException(null, "wrtong typed, was: " + o);
			}
		};

     //   throw Util.newInternal("cannot cast " + exp);
    }

    @Override
    public TupleCalc compileTuple(Exp exp) {
        return (TupleCalc) compile(exp);
    }

    @Override
    public Calc compileScalar(Exp exp, boolean specific) {
        final Type type = exp.getType();
        if (type instanceof MemberType) {
            final MemberCalc calc = compileMember(exp);
            return memberToScalar(calc);
        }
        if ((type instanceof DimensionType) || (type instanceof HierarchyType)) {
            final HierarchyCalc hierarchyCalc = compileHierarchy(exp);
            return hierarchyToScalar(hierarchyCalc);
        } else if (type instanceof TupleType tupleType) {
            final TupleCalc tupleCalc = compileTuple(exp);
            final TupleValueCalc scalarCalc =
                    new TupleValueCalc(
                            tupleType.getValueType(),
                            tupleCalc,
                            getEvaluator().mightReturnNullForUnrelatedDimension());
            return scalarCalc.optimize();
        } else if (type instanceof ScalarType) {
            if (specific) {
                if (type instanceof BooleanType) {
                    return compileBoolean(exp);
                } else if (type instanceof NumericType) {
                    return compileDouble(exp);
                } else if (type instanceof StringType) {
                    return compileString(exp);
                } else {
                    return compile(exp);
                }
            } else {
                return compile(exp);
            }
        } else {
            return compile(exp);
        }
    }

    private Calc hierarchyToScalar(HierarchyCalc hierarchyCalc) {
        final MemberCalc memberCalc = hierarchyToMember(hierarchyCalc);
        return memberToScalar(memberCalc);
    }

    private Calc memberToScalar(MemberCalc memberCalc) {
        final MemberType memberType = (MemberType) memberCalc.getType();
        return MemberValueCalc.create(
                memberType.getValueType(),
                new MemberCalc[] {memberCalc},
                getEvaluator().mightReturnNullForUnrelatedDimension());
    }

    @Override
    public ParameterSlot registerParameter(Parameter parameter) {
        final ParameterSlot slot = parameterSlots.get(parameter);
        if (slot != null) {
            return slot;
        }
        final int index = parameterSlots.size();
        final ParameterSlotImpl slot2 = new ParameterSlotImpl(parameter, index);
        parameterSlots.put(parameter, slot2);
        slot2.value = parameter.getValue();

        // Compile the expression only AFTER the parameter has been
        // registered with a slot. Otherwise a cycle is possible.
        final Type type = parameter.getType();
        Exp defaultExp = parameter.getDefaultExp();
        Calc calc;
        if (type instanceof ScalarType) {
            if (!defaultExp.getType().equals(type)) {
                defaultExp =
                        new UnresolvedFunCall(
                                "Cast",
                                Syntax.Cast,
                                new Exp[] {
                                        defaultExp,
                                        Literal.createSymbol(
                                                Category.instance.getName(
                                                        TypeUtil.typeToCategory(type)))});
                defaultExp = getValidator().validate(defaultExp, true);
            }
            calc = compileScalar(defaultExp, true);
        } else {
            calc = compileAs(defaultExp, type, resultStyles);
        }
        slot2.setDefaultValueCalc(calc);
        return slot2;
    }

    @Override
    public List<ResultStyle> getAcceptableResultStyles() {
        return resultStyles;
    }

    /**
     * Implementation of {@link ParameterSlot}.
     */
    private static class ParameterSlotImpl implements ParameterSlot {
        private final Parameter parameter;
        private final int index;
        private Calc defaultValueCalc;
        private Object value;
        private boolean assigned;
        private Object cachedDefaultValue;

        /**
         * Creates a ParameterSlotImpl.
         *
         * @param parameter Parameter
         * @param index Unique index of the slot
         */
        public ParameterSlotImpl(
                Parameter parameter, int index)
        {
            this.parameter = parameter;
            this.index = index;
        }

        @Override
        public int getIndex() {
            return index;
        }

        @Override
        public Calc getDefaultValueCalc() {
            return defaultValueCalc;
        }

        @Override
        public Parameter getParameter() {
            return parameter;
        }

        /**
         * Sets a compiled expression to compute the default value of the
         * parameter.
         *
         * @param calc Compiled expression to compute default value of
         * parameter
         *
         * @see #getDefaultValueCalc()
         */
        private void setDefaultValueCalc(Calc calc) {
            defaultValueCalc = calc;
        }

        @Override
        public void setParameterValue(Object value, boolean assigned) {
            this.value = value;
            this.assigned = assigned;

            // make sure caller called convert first
            assert (!(value instanceof List) || (value instanceof TupleList));
            if (value instanceof Literal || value instanceof MemberExpr) {
                throw new IllegalArgumentException("value should not be Literal or MemberExpr");
            }
        }

        @Override
        public Object getParameterValue() {
            return value;
        }

        @Override
        public boolean isParameterSet() {
            return assigned;
        }

        @Override
        public void unsetParameterValue() {
            value = null;
            assigned = false;
        }

        @Override
        public void setCachedDefaultValue(Object value) {
            cachedDefaultValue = value;
        }

        @Override
        public Object getCachedDefaultValue() {
            return cachedDefaultValue;
        }
    }

    /**
     * Computes the hierarchy of a dimension.
     */
    private static class DimensionHierarchyCalc extends AbstractHierarchyCalc {
        private final DimensionCalc dimensionCalc;

        protected DimensionHierarchyCalc(Type type, DimensionCalc dimensionCalc) {
            super("DimensionHierarchyCalc",type, new Calc[] {dimensionCalc});
            this.dimensionCalc = dimensionCalc;
        }

        @Override
        public Hierarchy evaluateHierarchy(Evaluator evaluator) {
            final Dimension dimension =
                    dimensionCalc.evaluate(evaluator);
            final Hierarchy hierarchy =
                    FunUtil.getDimensionDefaultHierarchy(dimension);
            if (hierarchy != null) {
                return hierarchy;
            }
            throw FunUtil.newEvalException(
                    MondrianResource.instance()
                    .CannotImplicitlyConvertDimensionToHierarchy.ex(
                            dimension.getName()));
        }
    }
}
