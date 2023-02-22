package org.eclipse.daanse.xmla.model.record.execute.statement;

import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;
import org.eclipse.daanse.xmla.model.record.mddataset.MddatasetR;

public record StatementResponseR(MddatasetR mdDataSet) implements StatementResponse {
}
