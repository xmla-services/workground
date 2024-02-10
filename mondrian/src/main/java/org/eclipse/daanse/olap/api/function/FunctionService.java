package org.eclipse.daanse.olap.api.function;

public interface FunctionService extends FunctionTable{

    void addResolver(FunctionResolver resolver);

    void removeResolver(FunctionResolver resolver);

  

}
