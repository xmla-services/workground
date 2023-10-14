/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.PropertyTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.adapter.PropertyTypeAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Property", propOrder = { "propertyFormatter" })
public class PropertyImpl extends AbstractMainElement implements MappingProperty {

	@XmlAttribute(name = "column", required = true)
	protected String column;
	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(PropertyTypeAdaptor.class)
	protected PropertyTypeEnum type;
	@XmlAttribute(name = "formatter")
	protected String formatter;

	@XmlAttribute(name = "dependsOnLevelValue")
	protected Boolean dependsOnLevelValue;
	@XmlElement(name = "PropertyFormatter", type = ElementFormatterImpl.class)
	protected MappingElementFormatter propertyFormatter;

	@Override
	public String column() {
		return column;
	}

	public void setColumn(String value) {
		this.column = value;
	}

	@Override
	public PropertyTypeEnum type() {
		if (type == null) {
			return PropertyTypeEnum.STRING;
		} else {
			return type;
		}
	}

	public void setType(PropertyTypeEnum type) {
		this.type = type;
	}

	@Override
	public String formatter() {
		return formatter;
	}

	public void setFormatter(String value) {
		this.formatter = value;
	}

	@Override
	public Boolean dependsOnLevelValue() {
		if (dependsOnLevelValue == null) {
			return false;
		} else {
			return dependsOnLevelValue;
		}
	}

	@Override
	public MappingElementFormatter propertyFormatter() {
		return propertyFormatter;
	}

	public void setDependsOnLevelValue(Boolean value) {
		this.dependsOnLevelValue = value;
	}

	public void setPropertyFormatter(MappingElementFormatter propertyFormatter) {
		this.propertyFormatter = propertyFormatter;
	}
}
