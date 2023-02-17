package org.eclipse.daanse.xmla.model.record.discover;

import org.eclipse.daanse.xmla.api.discover.mdschema.functions.ParameterInfo;

public record ParameterInfoR(String name,
                             String description,
                             Boolean optional,
                             Boolean repeatable,
                             Integer repeatGroup) implements ParameterInfo {
}
