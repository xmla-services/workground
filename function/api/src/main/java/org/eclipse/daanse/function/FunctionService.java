package org.eclipse.daanse.function;

import java.util.List;

import org.eclipse.daanse.olap.api.Syntax;
import org.eclipse.daanse.olap.api.function.FunctionResolver;

public interface FunctionService {

    void addResolver(FunctionResolver resolver);

    void removeResolver(FunctionResolver resolver);

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
     * Returns a list of {@link FunctionResolver} objects.
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
     * Returns a list of {@link FunInfo} objects.
     */
    List<FunInfo> getFunInfoList();

}
