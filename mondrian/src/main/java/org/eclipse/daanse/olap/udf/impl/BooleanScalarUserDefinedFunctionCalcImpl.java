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

import org.eclipse.daanse.olap.api.Evaluator;
import org.eclipse.daanse.olap.api.element.Hierarchy;
import org.eclipse.daanse.olap.api.query.component.ResolvedFunCall;
import org.eclipse.daanse.olap.calc.api.Calc;
import org.eclipse.daanse.olap.calc.base.nested.AbstractProfilingNestedBooleanCalc;

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
            super(call.getType(),calcs);
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