package org.eclipse.daanse.xmla.model.record.mddataset;

import org.eclipse.daanse.xmla.api.mddataset.CellTypeError;
import org.eclipse.daanse.xmla.api.mddataset.Value;

import java.util.List;

public record ValueR(
    List<CellTypeError> error) implements Value {

}
