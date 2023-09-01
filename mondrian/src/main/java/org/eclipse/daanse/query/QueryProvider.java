package org.eclipse.daanse.query;

import mondrian.olap.interfaces.QueryPart;
import org.eclipse.daanse.mdx.model.api.MdxStatement;

public interface QueryProvider {

    QueryPart createQuery(MdxStatement selectStatement);

}
