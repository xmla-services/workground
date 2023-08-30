package org.eclipse.daanse.query;

import mondrian.olap.Query;
import org.eclipse.daanse.mdx.model.api.SelectStatement;

public interface QueryProvider {

    Query createQuery(SelectStatement selectStatement);

}
