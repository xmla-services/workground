package org.eclipse.daanse.olap.api;


//todo: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/ScopedValue.html
public interface Locus {

	Execution getExecution();

	Context getContext();

}