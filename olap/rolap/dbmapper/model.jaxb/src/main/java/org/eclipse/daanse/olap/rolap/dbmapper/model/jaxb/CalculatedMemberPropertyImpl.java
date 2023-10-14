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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMemberProperty;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CalculatedMemberProperty")
public class CalculatedMemberPropertyImpl extends AbstractMainElement implements MappingCalculatedMemberProperty {

	@XmlAttribute(name = "expression")
	protected String expression;
	@XmlAttribute(name = "value")
	protected String value;

	@Override
	public String expression() {
		return expression;
	}

	public void setExpression(String value) {
		this.expression = value;
	}

	@Override
	public String value() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
