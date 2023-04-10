package org.eclipse.daanse.xmla.model.record.mddataset;

import java.util.List;

import org.eclipse.daanse.xmla.api.mddataset.CellTypeError;
import org.eclipse.daanse.xmla.api.mddataset.Value;

public record ValueR(
    List<CellTypeError> error) implements Value {

}
