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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.PropertyTypeEnum;

public record PropertyR(String name,
		String description,
		String caption,
        String column,
        PropertyTypeEnum type,
        String formatter,
        Boolean dependsOnLevelValue,
        MappingElementFormatter propertyFormatter)
        implements MappingProperty {
	

	public  PropertyR(String name,
			String description,
			String caption,
	        String column,
	        PropertyTypeEnum type,
	        String formatter,
	        Boolean dependsOnLevelValue,
	        MappingElementFormatter propertyFormatter)
	          {
				this.name = name;
				this.description = description;
				this.caption = caption;
				this.column = column;
				this.type = type;
				this.formatter = formatter;
				this.dependsOnLevelValue = dependsOnLevelValue;
				this.propertyFormatter = propertyFormatter;
		
	}

}
