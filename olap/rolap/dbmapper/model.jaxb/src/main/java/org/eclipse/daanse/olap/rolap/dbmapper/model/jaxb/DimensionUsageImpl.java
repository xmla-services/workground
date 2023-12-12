
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

import jakarta.xml.bind.annotation.XmlRootElement;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingDimensionUsage;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DimensionUsage", propOrder = {})
@XmlRootElement(name = "DimensionUsage")
public class DimensionUsageImpl extends AbstractMainElement implements MappingDimensionUsage {

	@XmlAttribute(name = "source", required = true)
	protected String source;
	@XmlAttribute(name = "level")
	protected String level;
	@XmlAttribute(name = "usagePrefix")
	protected String usagePrefix;
	@XmlAttribute(name = "foreignKey")
	protected String foreignKey;

	@XmlAttribute(name = "visible")
	protected Boolean visible = true;

	@Override
	public String source() {
		return source;
	}

	public void setSource(String value) {
		this.source = value;
	}

	@Override
	public String level() {
		return level;
	}

	public void setLevel(String value) {
		this.level = value;
	}

	@Override
	public String usagePrefix() {
		return usagePrefix;
	}

	public void setUsagePrefix(String value) {
		this.usagePrefix = value;
	}

	@Override
	public String foreignKey() {
		return foreignKey;
	}

	public void setForeignKey(String value) {
		this.foreignKey = value;
	}

	@Override
	public Boolean visible() {
		return visible == null ? Boolean.FALSE : visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}
