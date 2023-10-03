package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCubeDimension;

public record CubeDimensionR(
                             String name,
                             String description,
                             List<MappingAnnotation> annotations,
                             String caption,
                             Boolean visible,
                             String foreignKey,
                             Boolean highCardinality
                            ) implements MappingCubeDimension {



	public  CubeDimensionR(
            String name,
            String description,
            List<MappingAnnotation> annotations,
            String caption,
            Boolean visible,
            String foreignKey,
            Boolean highCardinality
           )
{
	this.name = name;
	this.description = description;
	this.annotations = annotations == null ? List.of() : annotations;
	this.caption = caption;
	this.visible = visible == null ? Boolean.TRUE : visible;
	this.foreignKey = foreignKey;
	this.highCardinality = highCardinality == null ? Boolean.FALSE : highCardinality;

}

}
