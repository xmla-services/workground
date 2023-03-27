
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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.DimensionUsage;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DimensionUsage", propOrder = { "annotations" })
@XmlRootElement(name = "DimensionUsage")
public class DimensionUsageImpl implements DimensionUsage {

    @XmlElement(name = "Annotation")
    @XmlElementWrapper(name = "Annotations")
    protected List<AnnotationImpl> annotations;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "source", required = true)
    protected String source;
    @XmlAttribute(name = "level")
    protected String level;
    @XmlAttribute(name = "usagePrefix")
    protected String usagePrefix;
    @XmlAttribute(name = "foreignKey")
    protected String foreignKey;
    @XmlAttribute(name = "highCardinality")
    protected Boolean highCardinality;
    @XmlAttribute(name = "caption")
    protected String caption;
    @XmlAttribute(name = "visible")
    protected Boolean visible = true;
    @XmlAttribute(name = "description")
    protected  String description;

    @Override
    public List<AnnotationImpl> annotations() {
        if (annotations == null) {
            annotations = new ArrayList<AnnotationImpl>();
        }
        return this.annotations;
    }

    @Override
    public String name() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

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
    public boolean highCardinality() {
        if (highCardinality == null) {
            return false;
        } else {
            return highCardinality;
        }
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

    public void setHighCardinality(Boolean value) {
        this.highCardinality = value;
    }

}
