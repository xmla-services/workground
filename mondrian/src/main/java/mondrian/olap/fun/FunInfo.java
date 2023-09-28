/*
 * This software is subject to the terms of the Eclipse Public License v1.0
 * Agreement, available at the following URL:
 * http://www.eclipse.org/legal/epl-v10.html.
 * You must accept the terms of that agreement to use this software.
 *
 * Copyright (C) 2005-2005 Julian Hyde
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

package mondrian.olap.fun;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.daanse.olap.api.DataType;
import org.eclipse.daanse.olap.api.Syntax;
import org.eclipse.daanse.olap.api.function.FunctionDefinition;
import org.eclipse.daanse.olap.api.function.FunctionInfo;
import org.eclipse.daanse.olap.api.function.FunctionResolver;
import org.eclipse.daanse.olap.function.AbstractFunctionDefinition;

import mondrian.olap.Util;

/**
 * Support class for the {@link mondrian.tui.CmdRunner} allowing one to view
 * available functions and their syntax.
 *
 * @author Richard M. Emberson
 */
public class FunInfo implements FunctionInfo {
    private final Syntax syntax;
    private final String name;
    private final String description;
    private final DataType[] returnTypes;
    private final DataType[][] parameterTypes;
    private String[] sigs;

    static FunInfo make(FunctionResolver resolver) {
        Optional<FunctionDefinition> optionalRepresentativeFunDef = resolver.getRepresentativeFunDef();
        if (optionalRepresentativeFunDef.isPresent()) {
            return new FunInfo(optionalRepresentativeFunDef.get());
        } else if (resolver instanceof MultiResolver multiResolver) {
            return new FunInfo(multiResolver);
        } else {
            return new FunInfo(resolver);
        }
    }

    FunInfo(FunctionDefinition funDef) {
        this.syntax = funDef.getFunctionMetaData().syntax();
        this.name = funDef.getFunctionMetaData().name();
        assert name != null;
        assert syntax != null;
        this.returnTypes = new DataType[] { funDef.getFunctionMetaData().returnCategory() };
        this.parameterTypes = new DataType[][] { funDef.getFunctionMetaData().parameterCategories() };

        // use explicit signature if it has one, otherwise generate a set
        this.sigs = funDef instanceof AbstractFunctionDefinition funDefBase
            && funDefBase.getFunctionMetaData().signature() != null
            ? new String[] {funDefBase.getFunctionMetaData().signature()}
            : FunInfo.makeSigs(syntax, name, returnTypes, parameterTypes);
        this.description = funDef.getFunctionMetaData().description();
    }

    FunInfo(MultiResolver multiResolver) {
        this.syntax = multiResolver.getSyntax();
        this.name = multiResolver.getName();
        assert name != null;
        assert syntax != null;
        this.description = multiResolver.getDescription();

        String[] signatures = multiResolver.getSignatures();
        this.returnTypes = new DataType[signatures.length];
        this.parameterTypes = new DataType[signatures.length][];
        for (int i = 0; i < signatures.length; i++) {
            returnTypes[i] = FunUtil.decodeReturnCategory(signatures[i]);
            parameterTypes[i] =
                FunUtil.decodeParameterCategories(signatures[i]);
        }
        this.sigs = FunInfo.makeSigs(syntax, name, returnTypes, parameterTypes);
    }

    FunInfo(FunctionResolver resolver) {
        this.syntax = resolver.getSyntax();
        this.name = resolver.getName();
        assert name != null;
        assert syntax != null;
        this.description = resolver.getDescription();
        this.returnTypes = null;
        this.parameterTypes = null;
        final String signature = resolver.getSignature();
        this.sigs =
            signature == null
            ? new String[0]
            : new String[] {signature};
    }

    FunInfo(
        String name,
        String description,
        String flags)
    {
        this.name = name;
        this.description = description;
        this.syntax = FunUtil.decodeSyntacticType(flags);
        this.returnTypes = new DataType[] {FunUtil.decodeReturnCategory(flags)};
        this.parameterTypes =
            new DataType[][] {FunUtil.decodeParameterCategories(flags)};
    }

    @Override
	public String[] getSignatures() {
        return sigs;
    }

    private static String[] makeSigs(
        Syntax syntax,
        String name,
        DataType[] returnTypes,
        DataType[][] parameterTypes)
    {
        if (parameterTypes == null) {
            return null;
        }

        String[] sigs = new String[parameterTypes.length];
        for (int i = 0; i < sigs.length; i++) {
            sigs[i] = syntax.getSignature(
                name, returnTypes[i], parameterTypes[i]);
        }
        return sigs;
    }

    /**
     * Returns the syntactic type of the function.
     */
    @Override
	public Syntax getSyntax() {
        return this.syntax;
    }

    /**
     * Returns the name of this function.
     */
    @Override
	public String getName() {
        return this.name;
    }

    /**
     * Returns the description of this function.
     */
    @Override
	public String getDescription() {
        return this.description;
    }

    /**
     * Returns the type of value returned by this function. Values are the same
     * as those returned by {@link org.eclipse.daanse.olap.api.query.component.Expression#getCategory()}.
     */
    @Override
	public DataType[] getReturnCategories() {
        return this.returnTypes;
    }

    /**
     * Returns the types of the arguments of this function. Values are the same
     * as those returned by {@link org.eclipse.daanse.olap.api.query.component.Expression#getCategory()}. The
     * 0<sup>th</sup> argument of methods and properties are the object they
     * are applied to. Infix operators have two arguments, and prefix operators
     * have one argument.
     */
    @Override
	public DataType[][] getParameterCategories() {
        return this.parameterTypes;
    }

    @Override
	public int compareTo(FunctionInfo fi) {
        int c = this.name.compareTo(fi.getName());
        if (c != 0) {
            return c;
        }
        final List<Object> pcList = FunInfo.toList(this.getParameterCategories());
        final String pc = pcList.toString();
        final List otherPcList = FunInfo.toList(fi.getParameterCategories());
        final String otherPc = otherPcList.toString();
        return pc.compareTo(otherPc);
    }

    @Override
	public boolean equals(Object obj) {
        if (obj instanceof FunInfo that) {
            if (!name.equals(that.name)) {
                return false;
            }
            final List<Object> pcList = FunInfo.toList(this.getParameterCategories());
            final List<Object> pcList2 = FunInfo.toList(that.getParameterCategories());
            return pcList.equals(pcList2);
        } else {
            return false;
        }
    }

    @Override
	public int hashCode() {
        int h = name.hashCode();
        final List<Object> pcList = FunInfo.toList(this.getParameterCategories());
        return Util.hash(h, pcList);
    }

    private static List<Object> toList(Object a) {
        final List<Object> list = new ArrayList<>();
        if (a == null) {
            return list;
        }
        final int length = Array.getLength(a);
        for (int i = 0; i < length; i++) {
            final Object o = Array.get(a, i);
            if (o.getClass().isArray()) {
                list.add(FunInfo.toList(o));
            } else {
                list.add(o);
            }
        }
        return list;
    }


}
