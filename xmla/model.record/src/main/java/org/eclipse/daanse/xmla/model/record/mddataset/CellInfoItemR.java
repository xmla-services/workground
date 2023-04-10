package org.eclipse.daanse.xmla.model.record.mddataset;

import java.util.Optional;

import org.eclipse.daanse.xmla.api.mddataset.CellInfoItem;

public record CellInfoItemR(String tagName,
                            String name,
                            Optional<String> type) implements CellInfoItem {

}
