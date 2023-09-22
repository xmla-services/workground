package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeDimension;

public record CubeDimensionR(List<MappingAnnotation> annotations,
                             String name,
                             String foreignKey,
                             boolean highCardinality,
                             String caption,
                             boolean visible,
                             String description) implements MappingCubeDimension {

}
