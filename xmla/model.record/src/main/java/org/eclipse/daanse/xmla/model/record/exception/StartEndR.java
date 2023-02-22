package org.eclipse.daanse.xmla.model.record.exception;

import org.eclipse.daanse.xmla.api.exception.StartEnd;

public record StartEndR(int line,
                        int column) implements StartEnd {

}
