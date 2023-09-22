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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.VirtualCubeMeasure;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "annotations" })
public class VirtualCubeMeasureImpl implements VirtualCubeMeasure {

    @XmlElement(name = "Annotation", type = AnnotationImpl.class)
    @XmlElementWrapper(name = "Annotations")
    protected List<MappingAnnotation> annotations;
    @XmlAttribute(name = "cubeName", required = true)
    protected String cubeName;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "visible")
    protected Boolean visible;

    @Override
    public List<MappingAnnotation> annotations() {
        return annotations;
    }

    public void setAnnotations(List<MappingAnnotation> value) {
        this.annotations = value;
    }

    @Override
    public String cubeName() {
        return cubeName;
    }

    public void setCubeName(String value) {
        this.cubeName = value;
    }

    @Override
    public String name() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public boolean visible() {
        if (visible == null) {
            return true;
        } else {
            return visible;
        }
    }

    public void setVisible(Boolean value) {
        this.visible = value;
    }

}
