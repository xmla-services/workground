package org.eclipse.daanse.xmla.model.record.mddataset;

import java.util.List;

import org.eclipse.daanse.xmla.api.common.enums.ItemTypeEnum;
import org.eclipse.daanse.xmla.api.mddataset.CellTypeError;
import org.eclipse.daanse.xmla.api.mddataset.Value;

public record ValueR(String value, ItemTypeEnum type,
    List<CellTypeError> error) implements Value {

}
