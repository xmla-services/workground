package org.eclipse.daanse.xmla.model.record.execute;

import org.eclipse.daanse.xmla.api.execute.ExecuteParameter;

public record ExecuteParameterR(String name,
                                Object value) implements ExecuteParameter {

}
