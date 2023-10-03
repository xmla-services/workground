package org.eclipse.daanse.function;

import org.eclipse.daanse.olap.api.function.FunctionResolver;
import org.eclipse.daanse.olap.api.function.FunctionTable;

public interface FunctionService extends FunctionTable{

    void addResolver(FunctionResolver resolver);

    void removeResolver(FunctionResolver resolver);

  

}
