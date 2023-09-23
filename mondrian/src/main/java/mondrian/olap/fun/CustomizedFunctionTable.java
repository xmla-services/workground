/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.olap.fun;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.daanse.olap.api.function.FunctionDefinition;
import org.eclipse.daanse.olap.api.function.FunctionResolver;
import org.eclipse.daanse.olap.api.function.FunctionTable;

/**
 * Interface to build a customized function table, selecting functions from the
 * set of supported functions in an instance of {@link BuiltinFunTable}.
 *
 * @author Rushan Chen
 */
public class CustomizedFunctionTable extends FunTableImpl {

    Set<String> supportedBuiltinFunctions;
    Set<FunctionDefinition> specialFunctions;

    public CustomizedFunctionTable(Set<String> builtinFunctions) {
        supportedBuiltinFunctions = builtinFunctions;
        this.specialFunctions = new HashSet<>();
    }

    public CustomizedFunctionTable(
        Set<String> builtinFunctions,
        Set<FunctionDefinition> specialFunctions)
    {
        this.supportedBuiltinFunctions = builtinFunctions;
        this.specialFunctions = specialFunctions;
    }

    @Override
	public void defineFunctions(FunctionTableCollector builder) {
        final FunctionTable builtinFunTable = BuiltinFunTable.instance();

        // Includes all the keywords form builtin function table
        for (String reservedWord : builtinFunTable.getReservedWords()) {
            builder.defineReserved(reservedWord);
        }

        // Add supported builtin functions
        for (FunctionResolver resolver : builtinFunTable.getResolvers()) {
            if (supportedBuiltinFunctions.contains(resolver.getName())) {
                builder.define(resolver);
            }
        }

        // Add special function definitions
        for (FunctionDefinition funDef : specialFunctions) {
            builder.define(funDef);
        }
    }
}
