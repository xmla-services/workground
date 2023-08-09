package org.eclipse.daanse.xmla.api.mddataset;

import org.eclipse.daanse.xmla.api.common.enums.CellTypeEnum;

import java.util.List;

public interface Value {

    List<CellTypeError> error();

    String value();

    CellTypeEnum type();
}
