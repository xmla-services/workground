package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeDimension;

public record CubeDimensionR(
                             String name,
                             String description,
                             List<MappingAnnotation> annotations,
                             String caption,
                             boolean visible,
                             String foreignKey,
                             boolean highCardinality
                            ) implements MappingCubeDimension {

}
