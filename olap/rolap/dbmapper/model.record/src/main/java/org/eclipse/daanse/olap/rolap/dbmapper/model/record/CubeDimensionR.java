package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Annotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.CubeDimension;

public record CubeDimensionR(List<Annotation> annotations,
                             String name,
                             String foreignKey,
                             boolean highCardinality,
                             String caption,
                             boolean visible,
                             String description) implements CubeDimension {

}
