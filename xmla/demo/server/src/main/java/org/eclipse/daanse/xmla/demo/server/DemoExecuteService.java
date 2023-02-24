package org.eclipse.daanse.xmla.demo.server;

import org.eclipse.daanse.xmla.api.execute.ExecuteService;
import org.eclipse.daanse.xmla.api.execute.alter.AlterRequest;
import org.eclipse.daanse.xmla.api.execute.alter.AlterResponse;
import org.eclipse.daanse.xmla.api.execute.statement.StatementRequest;
import org.eclipse.daanse.xmla.api.execute.statement.StatementResponse;

public class DemoExecuteService implements ExecuteService {

    @Override
    public StatementResponse statement(StatementRequest statementRequest) {
        //TODO
        return null;
    }

    @Override
    public AlterResponse alter(AlterRequest statementRequest) {
        //TODO
        return null;
    }
}
