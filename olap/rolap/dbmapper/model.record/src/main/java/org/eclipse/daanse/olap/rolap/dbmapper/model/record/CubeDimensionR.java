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
	


	public  CubeDimensionR(
            String name,
            String description,
            List<MappingAnnotation> annotations,
            String caption,
            boolean visible,
            String foreignKey,
            boolean highCardinality
           ) 
{
	this.name = name;
	this.description = description;
	this.annotations = annotations == null ? List.of() : annotations;
	this.caption = caption;
	this.visible = visible;
	this.foreignKey = foreignKey;
	this.highCardinality = highCardinality;
		
}

}
