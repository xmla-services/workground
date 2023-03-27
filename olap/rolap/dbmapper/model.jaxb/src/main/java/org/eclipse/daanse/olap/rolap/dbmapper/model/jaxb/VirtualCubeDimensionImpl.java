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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Annotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.VirtualCubeDimension;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "annotations" })
public class VirtualCubeDimensionImpl implements VirtualCubeDimension {

    @XmlAttribute(name = "cubeName")
    protected String cubeName;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlElement(name = "Annotation")
    protected List<? extends AnnotationImpl> annotations;
    @XmlAttribute(name = "foreignKey")
    protected String foreignKey;
    @XmlAttribute(name = "highCardinality")
    protected Boolean highCardinality = false;
    @XmlAttribute(name = "caption")
    protected String caption;
    @XmlAttribute(name = "visible")
    protected Boolean visible = true;
    @XmlAttribute(name = "description")
    protected String description;

    @Override
    public String cubeName() {
        return cubeName;
    }

    public void setCubeName(String value) {
        this.cubeName = value;
    }

    @Override
    public List<? extends Annotation> annotations() {
        return annotations;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String foreignKey() {
        return foreignKey;
    }

    @Override
    public boolean highCardinality() {
        return highCardinality;
    }

    @Override
    public String caption() {
        return caption;
    }

    @Override
    public boolean visible() {
        return visible;
    }

    @Override
    public String description() {
        return description;
    }

    public void setName(String value) {
        this.name = value;
    }

}
