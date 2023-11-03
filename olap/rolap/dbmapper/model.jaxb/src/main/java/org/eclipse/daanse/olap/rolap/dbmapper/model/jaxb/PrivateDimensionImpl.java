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

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlRootElement;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingHierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingPrivateDimension;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.DimensionTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.adapter.DimensionTypeAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dimension", propOrder = { "hierarchies" })
@XmlRootElement(name = "Dimension")
public class PrivateDimensionImpl extends AbstractMainElement implements MappingPrivateDimension {

	@XmlElement(name = "Hierarchy", required = true, type = HierarchyImpl.class)
	protected List<MappingHierarchy> hierarchies;

	@XmlAttribute(name = "type")
	@XmlJavaTypeAdapter(DimensionTypeAdaptor.class)
	protected DimensionTypeEnum type;

	@XmlAttribute(name = "foreignKey")
	protected String foreignKey;
	@XmlAttribute(name = "highCardinality")
	protected Boolean highCardinality;

	@XmlAttribute(name = "visible")
	private Boolean visible = true;
	@XmlAttribute(name = "usagePrefix")
	private String usagePrefix;

	@Override
	public List<MappingHierarchy> hierarchies() {
		if (hierarchies == null) {
			hierarchies = new ArrayList<>();
		}
		return this.hierarchies;
	}

	@Override
	public DimensionTypeEnum type() {
		return type;
	}

	public void setType(DimensionTypeEnum value) {
		this.type = value;
	}

	@Override
	public String foreignKey() {
		return foreignKey;
	}

	public void setForeignKey(String value) {
		this.foreignKey = value;
	}

	@Override
	public Boolean highCardinality() {
		if (highCardinality == null) {
			return false;
		} else {
			return highCardinality;
		}
	}

	public void setHighCardinality(Boolean value) {
		this.highCardinality = value;
	}

	@Override
	public Boolean visible() {
		return visible == null ? Boolean.FALSE : visible;
	}

	@Override
	public String usagePrefix() {
		return usagePrefix;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public void setHierarchies(List<MappingHierarchy> hierarchies) {
		this.hierarchies = hierarchies;
	}

	public void setUsagePrefix(String usagePrefix) {
		this.usagePrefix = usagePrefix;
	}
}
