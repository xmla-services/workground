/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2002-2005 Julian Hyde
// Copyright (C) 2005-2017 Hitachi Vantara and others
// All Rights Reserved.
*/

package org.eclipse.daanse.function;

/**
 * <code>FunDefBase</code> is the default implementation of {@link FunDef}.
 *
 * <h3>Signatures</h3>
 *
 * <p>A function is defined by the following:</p>
 *
 * <table border="1">
 * <tr><th>Parameter</th><th>Meaning</th><th>Example</th></tr>
 * <tr>
 * <td>name</td><td>Name of the function</td><td>"Members"</td>
 * </tr>
 * <tr>
 * <td>signature</td>
 * <td>Signature of the function</td>
 * <td>"&lt;Dimension&gt;.Members"</td>
 * </tr>
 * <tr>
 * <td>description</td>
 * <td>Description of the function</td>
 * <td>"Returns the set of all members in a dimension."</td>
 * </tr>
 * <tr>
 * <td>flags</td>
 * <td>Encoding of the syntactic type, return type, and parameter
 * types of this operator. The encoding is described below.</td>
 * <td>"pxd"</tr>
 * </table>
 *
 * The <code>flags</code> field is an string which encodes
 * the syntactic type, return type, and parameter types of this operator.
 * <ul>
 * <li>The first character determines the syntactic type, as described by
 * {@link FunUtil#decodeSyntacticType(String)}.
 * <li>The second character determines the return type, as described by
 * {@link FunUtil#decodeReturnCategory(String)}.
 * <li>The third and subsequence characters determine the types of the
 * arguments arguments, as described by
 * {@link FunUtil#decodeParameterCategories(String)}.
 * </ul><p/>
 *
 * For example,  <code>"pxd"</code> means "an operator with
 * {@link Syntax#Property property} syntax (p) which returns a set
 * (x) and takes a dimension (d) as its argument".<p/>
 *
 * The arguments are always read from left to right, regardless of the
 * syntactic type of the operator. For example, the
 * <code>"&lt;Set&gt;.Item(&lt;Index&gt;)"</code> operator
 * (signature <code>"mmxn"</code>) has the
 * syntax of a method-call, and takes two parameters:
 * a set (x) and a numeric (n).<p/>
 *
 * @author jhyde
 * @since 26 February, 2002
 */
public  class FunDefBase implements FunDef {

    protected final int flags;
    private final String name;
    private Syntax syntax;
    final String signature;
    private final String description;
    protected final int returnCategory;
    protected final int[] parameterCategories;


    /**
     * Convenience constructor when we are created by a {@link FunctionResolver}.
     *
     * @param resolver Resolver
     * @param returnType Return type
     * @param parameterTypes Parameter types
     */
    FunDefBase(FunctionResolver resolver, int returnType, int[] parameterTypes) {
        this(
            resolver.getName(),
            null,
            null,
            resolver.getSyntax(),
            returnType,
            parameterTypes);
    }

    /**
     * Creates an operator.
     *
     * @param name           Name of the function, for example "Members".
     * @param signature      Signature of the function, for example
     *                       "&lt;Dimension&gt;.Members".
     * @param description    Description of the function, for example
     *                       "Returns the set of all members in a dimension."
     * @param syntax         Syntactic type of the operator (for
     *                       example, function, method, infix operator)
     * @param returnCategory The {@link Category} of the value returned by this
     *                       operator.
     * @param parameterCategories An array of {@link Category} codes, one for
     *                       each parameter.
     */
    FunDefBase(
        String name,
        String signature,
        String description,
        Syntax syntax,
        int returnCategory,
        int[] parameterCategories)
    {
        assert name != null;
        assert syntax != null;
        this.name = name;
        this.signature = signature;
        this.description = description;
        this.flags = syntax.ordinal();
        this.returnCategory = returnCategory;
        this.parameterCategories = parameterCategories;
    }

    @Override
    public Syntax getSyntax() {
        return syntax;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getReturnCategory() {
        return this.returnCategory;
    }

    @Override
    public int[] getParameterCategories() {
        return this.parameterCategories;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
