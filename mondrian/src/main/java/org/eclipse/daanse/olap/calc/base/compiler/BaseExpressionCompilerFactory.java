/*
* Copyright (c) 2022 Contributors to the Eclipse Foundation.
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

package org.eclipse.daanse.olap.calc.base.compiler;

import java.util.List;
import java.util.Map;

import org.eclipse.daanse.olap.api.Evaluator;
import org.eclipse.daanse.olap.api.Validator;
import org.eclipse.daanse.olap.calc.api.ResultStyle;
import org.eclipse.daanse.olap.calc.api.compiler.ExpressionCompiler;
import org.eclipse.daanse.olap.calc.api.compiler.ExpressionCompilerFactory;
import org.osgi.service.component.annotations.Activate;

import aQute.bnd.component.annotations.Component;
import aQute.bnd.component.annotations.ServiceScope;
import mondrian.calc.impl.BetterExpCompiler;
import mondrian.util.CreationException;

@Component(scope = ServiceScope.SINGLETON, service = ExpressionCompilerFactory.class, configurationPid =  BaseExpressionCompilerFactory.PID)
public class BaseExpressionCompilerFactory implements ExpressionCompilerFactory {

	public BaseExpressionCompilerFactory() {
		this(Map.of());
	}
	@Activate
	public BaseExpressionCompilerFactory(Map<String,Object> map) {
	}
	public static final String PID = "org.eclipse.daanse.olap.calc.base.compiler.BaseExpressionCompilerFactory";

	@Override
	public ExpressionCompiler createExpressionCompiler(Evaluator evaluator, Validator validator,
			List<ResultStyle> resultStyles) throws CreationException {
		return new BetterExpCompiler(evaluator, validator, resultStyles);
	}

}