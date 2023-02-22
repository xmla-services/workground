package org.eclipse.daanse.xmla.api.mddataset;

import java.util.Optional;

public interface CellInfoItem {

    String tagName();

    String name();

    Optional<String> type();
}
