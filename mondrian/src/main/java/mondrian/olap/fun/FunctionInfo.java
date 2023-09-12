package mondrian.olap.fun;

import mondrian.olap.Syntax;

public interface FunctionInfo  extends Comparable<FunctionInfo>{

	String[] getSignatures();

	/**
	 * Returns the syntactic type of the function.
	 */
	Syntax getSyntax();

	/**
	 * Returns the name of this function.
	 */
	String getName();

	/**
	 * Returns the description of this function.
	 */
	String getDescription();

	/**
	 * Returns the type of value returned by this function. Values are the same
	 * as those returned by {@link mondrian.olap.Exp#getCategory()}.
	 */
	int[] getReturnCategories();

	/**
	 * Returns the types of the arguments of this function. Values are the same
	 * as those returned by {@link mondrian.olap.Exp#getCategory()}. The
	 * 0<sup>th</sup> argument of methods and properties are the object they
	 * are applied to. Infix operators have two arguments, and prefix operators
	 * have one argument.
	 */
	int[][] getParameterCategories();

	int compareTo(FunctionInfo fi);

}