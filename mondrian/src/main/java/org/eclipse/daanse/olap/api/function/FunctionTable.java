/*
 * This software is subject to the terms of the Eclipse Public License v1.0
 * Agreement, available at the following URL:
 * http://www.eclipse.org/legal/epl-v10.html.
 * You must accept the terms of that agreement to use this software.
 *
 * Copyright (C) 2002-2005 Julian Hyde
 * Copyright (C) 2005-2017 Hitachi Vantara and others
 * All Rights Reserved.
 * 
 * For more information please visit the Project: Hitachi Vantara - Mondrian
 * 
 * ---- All changes after Fork in 2023 ------------------------
 * 
 * Project: Eclipse daanse
 * 
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors after Fork in 2023:
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */


package org.eclipse.daanse.olap.api.function;

import java.util.List;

import mondrian.olap.Syntax;
import mondrian.olap.fun.FunctionInfo;
import mondrian.olap.fun.FunctionResolver;

/**
 * List of all MDX functions.
 *
 * <p>A function table can resolve a function call, using a particular
 * {@link Syntax} and set of arguments, to a
 * function definition ({@link FunctionDefinition}).</p>
 *
 * @author jhyde, 3 March, 2002
 */
public interface FunctionTable {
    /**
     * Returns whether a string is a reserved word.
     */
    boolean isReserved(String s);

    /**
     * Returns whether a string is a property-style (postfix)
     * operator. This is used during parsing to disambiguate
     * functions from unquoted member names.
     */
    boolean isProperty(String s);

    /**
     * Returns a list of words ({@link String}) which may not be used as
     * identifiers.
     */
    List<String> getReservedWords();

    /**
     * Returns a list of {@link mondrian.olap.fun.FunctionResolver} objects.
     */
    List<FunctionResolver> getResolvers();

    /**
     * Returns a list of resolvers for an operator with a given name and syntax.
     * Never returns null; if there are no resolvers, returns the empty list.
     *
     * @param name Operator name
     * @param syntax Operator syntax
     * @return List of resolvers for the operator
     */
    List<FunctionResolver> getResolvers(
        String name,
        Syntax syntax);

    /**
     * Returns a list of {@link mondrian.olap.fun.FunInfo} objects.
     */
    List<FunctionInfo> getFunctionInfos();

    /**
     * This method is called from the constructor, to define the set of
     * functions and reserved words recognized.
     *
     * <p>The implementing class calls {@link FunctionTableCollector} methods to declare
     * functions and reserved words.
     *
     * <p>Derived class can override this method to add more functions. It must
     * call the base method.
     *
     * @param collector FunctionTableCollector
     */
    void defineFunctions(FunctionTableCollector collector);

    /**
     * Builder that assists with the construction of a function table by
     * providing callbacks to define functions.
     *
     * <p>An implementation of {@link org.eclipse.daanse.olap.api.function.FunctionTable} must register all
     * of its functions and operators by making callbacks during its
     * {@link org.eclipse.daanse.olap.api.function.FunctionTable#defineFunctions(org.eclipse.daanse.olap.api.function.FunctionTable.FunctionTableCollector)}
     * method.
     */
    public interface FunctionTableCollector {
        /**
         * Defines a function.
         *
         * @param funDef Function definition
         */
        void define(FunctionDefinition funDef);

        /**
         * Defines a resolver that will resolve overloaded function calls to
         * function definitions.
         *
         * @param resolver Function call resolver
         */
        void define(FunctionResolver resolver);

        /**
         * Defines a function info that is not matchd by an actual function.
         * The function will be implemented via implicit conversions, but
         * we still want the function info to appear in the metadata.
         *
         * @param funInfo Function info
         */
        void define(FunctionInfo funInfo);

        /**
         * Defines a reserved word.
         *
         * @param keyword Reserved word
         *
         * @see org.eclipse.daanse.olap.api.function.FunctionTable#isReserved
         */
        void defineReserved(String keyword);
    }
}
