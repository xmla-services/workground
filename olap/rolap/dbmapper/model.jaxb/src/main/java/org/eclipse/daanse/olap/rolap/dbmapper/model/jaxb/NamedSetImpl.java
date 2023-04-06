
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

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Formula;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.NamedSet;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NamedSet", propOrder = { "annotations", "formulaElement" })
public class NamedSetImpl implements NamedSet {

    @XmlElement(name = "Annotation")
    @XmlElementWrapper(name = "Annotations")
    protected List<AnnotationImpl> annotations;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "caption")
    protected String caption;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "displayFolder")
    private String displayFolder;
    @XmlAttribute(name = "formula")
    protected String formula;
    @XmlElement(name = "Formula")
    protected FormulaImpl formulaElement;

    /**
     * Gets the value of the annotations property.
     *
     * @return possible object is {@link Annotations }
     *
     */
    @Override
    public List<AnnotationImpl> annotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationImpl> value) {
        this.annotations = value;
    }

    @Override
    public String formula() {
        return formula;
    }

    public void setFormula(String value) {
        this.formula = value;
    }

    @Override
    public String name() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public String caption() {
        return caption;
    }

    public void setCaption(String value) {
        this.caption = value;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String displayFolder() {
        return displayFolder;
    }

    @Override
    public Formula formulaElement() {
        return formulaElement;
    }

    public void setDescription(String value) {
        this.description = value;
    }

	public void setDisplayFolder(String displayFolder) {
		this.displayFolder = displayFolder;
	}

	public void setFormulaElement(FormulaImpl formulaElement) {
		this.formulaElement = formulaElement;
	}

}
