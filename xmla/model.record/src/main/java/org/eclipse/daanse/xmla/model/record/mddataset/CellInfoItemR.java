package org.eclipse.daanse.xmla.model.record.mddataset;

import org.eclipse.daanse.xmla.api.mddataset.CellInfoItem;

import java.util.Optional;

public record CellInfoItemR(String tagName,
                            String name,
                            Optional<String> type) implements CellInfoItem {

}
