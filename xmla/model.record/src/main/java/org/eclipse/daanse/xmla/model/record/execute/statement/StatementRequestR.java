package org.eclipse.daanse.xmla.model.record.execute.statement;

import org.eclipse.daanse.xmla.api.execute.ExecuteParameter;
import org.eclipse.daanse.xmla.api.execute.statement.StatementRequest;
import org.eclipse.daanse.xmla.model.record.discover.PropertiesR;

import java.util.List;

public record StatementRequestR(PropertiesR properties,
                                List<ExecuteParameter> parameters,
                                StatementCommandR command) implements StatementRequest {
}
