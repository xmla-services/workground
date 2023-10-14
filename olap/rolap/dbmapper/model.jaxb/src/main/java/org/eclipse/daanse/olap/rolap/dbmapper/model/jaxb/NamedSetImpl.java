
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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingFormula;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingNamedSet;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NamedSet", propOrder = { "formulaElement" })
public class NamedSetImpl extends AbstractMainElement implements MappingNamedSet {

	@XmlAttribute(name = "displayFolder")
	private String displayFolder;
	@XmlAttribute(name = "formula")
	protected String formula;
	@XmlElement(name = "Formula", type = FormulaImpl.class)
	protected MappingFormula formulaElement;

	@Override
	public String formula() {
		return formula;
	}

	public void setFormula(String value) {
		this.formula = value;
	}

	@Override
	public String displayFolder() {
		return displayFolder;
	}

	@Override
	public MappingFormula formulaElement() {
		return formulaElement;
	}

	public void setDisplayFolder(String displayFolder) {
		this.displayFolder = displayFolder;
	}

	public void setFormulaElement(MappingFormula formulaElement) {
		this.formulaElement = formulaElement;
	}

}
