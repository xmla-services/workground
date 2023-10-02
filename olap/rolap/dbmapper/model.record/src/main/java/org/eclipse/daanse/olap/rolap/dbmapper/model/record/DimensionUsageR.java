/*
 * Copyright (c) 0 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License .0
 * which is available at https://www.eclipse.org/legal/epl-.0/
 *
 * SPDX-License-Identifier: EPL-.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.model.record;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDimensionUsage;

public record DimensionUsageR(String name,
						String description,
							List<MappingAnnotation> annotations,
							String caption,
							boolean visible,
                              String source,
                              String level,
                              String usagePrefix,
                              String foreignKey,
                              boolean highCardinality
                              )
        implements MappingDimensionUsage {
	
	


	public  DimensionUsageR(String name,
			String description,
				List<MappingAnnotation> annotations,
				String caption,
				boolean visible,
                  String source,
                  String level,
                  String usagePrefix,
                  String foreignKey,
                  boolean highCardinality
                  )
{
	this.name = name;
	this.description = description;
	this.annotations = annotations == null ? List.of() : annotations;
	this.caption = caption;
	this.visible = visible;
	this.source = source;
	this.level = level;
	this.usagePrefix = usagePrefix;
	this.foreignKey = foreignKey;
	this.highCardinality = highCardinality;
		
	}
}
