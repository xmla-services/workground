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
*/
package org.eclipse.daanse.olap.udf.impl;

import org.eclipse.daanse.calc.base.AbstractProfilingNestedBooleanCalc;
import org.eclipse.daanse.olap.api.model.Hierarchy;

import mondrian.calc.Calc;
import mondrian.mdx.ResolvedFunCall;
import mondrian.olap.Evaluator;
import mondrian.olap.fun.FunUtil;
import mondrian.rolap.agg.CellRequestQuantumExceededException;
import mondrian.spi.UserDefinedFunction;

public class BooleanScalarUserDefinedFunctionCalcImpl extends AbstractProfilingNestedBooleanCalc {
        private final UserDefinedFunction udf;
        private final UserDefinedFunction.Argument[] args;

        public BooleanScalarUserDefinedFunctionCalcImpl(
                ResolvedFunCall call,
                Calc[] calcs,
                UserDefinedFunction udf,
                UserDefinedFunction.Argument[] args)      {
            super(call.getFunName(),call.getType(),calcs);
            this.udf = udf;
            this.args = args;
        }
		@Override
		public Boolean evaluate(Evaluator evaluator) {
			try {
                return (Boolean)udf.execute(evaluator, args);
            } catch (CellRequestQuantumExceededException e) {
                // Is assumed will be processed in mondrian.rolap.RolapResult
                throw e;
            } catch (Exception e) {
            	throw FunUtil.newEvalException(
                    "Exception while executing function " + udf.getName(),
                    e);
            }
		}
		
	      @Override
			public boolean dependsOn(Hierarchy hierarchy) {
	            // Be pessimistic. This effectively disables expression caching.
	            return true;
	        }
    }