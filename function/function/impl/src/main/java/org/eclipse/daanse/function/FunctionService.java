package org.eclipse.daanse.function;

import java.util.List;

public interface FunctionService {

    void addResolver(Resolver resolver);

    void removeResolver(Resolver resolver);

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
     * Returns a list of {@link Resolver} objects.
     */
    List<Resolver> getResolvers();

    /**
     * Returns a list of resolvers for an operator with a given name and syntax.
     * Never returns null; if there are no resolvers, returns the empty list.
     *
     * @param name Operator name
     * @param syntax Operator syntax
     * @return List of resolvers for the operator
     */
    List<Resolver> getResolvers(
        String name,
        Syntax syntax);

    /**
     * Returns a list of {@link FunInfo} objects.
     */
    List<FunInfo> getFunInfoList();

}
