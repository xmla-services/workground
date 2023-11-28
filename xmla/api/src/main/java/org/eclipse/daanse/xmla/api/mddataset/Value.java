package org.eclipse.daanse.xmla.api.mddataset;

import org.eclipse.daanse.xmla.api.common.enums.ItemTypeEnum;

import java.util.List;

public interface Value {

    List<CellTypeError> error();

    String value();

    ItemTypeEnum type();
}
