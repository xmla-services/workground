/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2005-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
*/

package org.eclipse.daanse.function;

public class FunInfo  {

    private final Syntax syntax;
    private final String name;
    private final String description;
    private final int[] returnTypes;
    private final int[][] parameterTypes;
    private String[] sigs;

    static FunInfo make(Resolver resolver) {
        FunDef funDef = resolver.getRepresentativeFunDef();
        if (funDef != null) {
            return new FunInfo(funDef);
        } else if (resolver instanceof MultiResolver multiResolver) {
            return new FunInfo(multiResolver);
        } else {
            return new FunInfo(resolver);
        }
    }

    FunInfo(FunDef funDef) {
        this.syntax = funDef.getSyntax();
        this.name = funDef.getName();
        assert name != null;
        assert syntax != null;
        this.returnTypes = new int[] { funDef.getReturnCategory() };
        this.parameterTypes = new int[][] { funDef.getParameterCategories() };

        // use explicit signature if it has one, otherwise generate a set
        this.sigs = funDef instanceof FunDefBase funDefBase
            && funDefBase.signature != null
            ? new String[] {funDefBase.signature}
            : FunInfo.makeSigs(syntax, name, returnTypes, parameterTypes);
        this.description = funDef.getDescription();
    }

    private static String[] makeSigs(
        Syntax syntax,
        String name,
        int[] returnTypes,
        int[][] parameterTypes)
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

    FunInfo(MultiResolver multiResolver) {
        this.syntax = multiResolver.getSyntax();
        this.name = multiResolver.getName();
        assert name != null;
        assert syntax != null;
        this.description = multiResolver.getDescription();

        String[] signatures = multiResolver.getSignatures();
        this.returnTypes = new int[signatures.length];
        this.parameterTypes = new int[signatures.length][];
        for (int i = 0; i < signatures.length; i++) {
            returnTypes[i] = FunUtil.decodeReturnCategory(signatures[i]);
            parameterTypes[i] =
                FunUtil.decodeParameterCategories(signatures[i]);
        }
        this.sigs = FunInfo.makeSigs(syntax, name, returnTypes, parameterTypes);
    }

    FunInfo(Resolver resolver) {
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
        this.returnTypes = new int[] {FunUtil.decodeReturnCategory(flags)};
        this.parameterTypes =
            new int[][] {FunUtil.decodeParameterCategories(flags)};
    }

}
