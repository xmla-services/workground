package org.eclipse.daanse.query;

import mondrian.olap.Query;
import org.eclipse.daanse.mdx.model.api.MdxStatement;

public interface QueryProvider {

    Query createQuery(MdxStatement selectStatement);

}
