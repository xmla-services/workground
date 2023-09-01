package mondrian.olap.interfaces;

import org.eclipse.daanse.olap.api.model.OlapElement;

import java.util.List;

public interface DrillThrough extends QueryPart {

    Query getQuery();

    int getMaxRowCount();

    int getFirstRowOrdinal();

    List<OlapElement> getReturnList();
}
