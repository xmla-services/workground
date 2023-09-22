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

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCalculatedMemberProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingExpressionView;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingMeasure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.MeasureDataTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.adapter.MeasureDataTypeAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "annotations", "measureExpression", "calculatedMemberProperties", "cellFormatter" })
public class MeasureImpl implements MappingMeasure {

    @XmlElement(name = "Annotation", type = AnnotationImpl.class)
    @XmlElementWrapper(name = "Annotations")
    protected List<MappingAnnotation> annotations;
    @XmlElement(name = "MeasureExpression", type = ExpressionViewImpl.class)
    protected MappingExpressionView measureExpression;
    @XmlElement(name = "CalculatedMemberProperty", type = CalculatedMemberPropertyImpl.class)
    protected List<MappingCalculatedMemberProperty> calculatedMemberProperties;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "column")
    protected String column;
    @XmlAttribute(name = "datatype")
    @XmlJavaTypeAdapter(MeasureDataTypeAdaptor.class)
    protected MeasureDataTypeEnum datatype;
    @XmlAttribute(name = "formatString")
    protected String formatString;
    @XmlAttribute(name = "aggregator", required = true)
    protected String aggregator;
    @XmlAttribute(name = "formatter")
    protected String formatter;
    @XmlAttribute(name = "caption")
    protected String caption;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "visible")
    protected Boolean visible;
    @XmlAttribute(name = "displayFolder")
    protected String displayFolder;
    @XmlElement(name = "CellFormatter")
    CellFormatterImpl cellFormatter;
    @XmlAttribute(name = "backColor")
    protected String backColor;

    @Override
    public List<MappingAnnotation> annotations() {
        return annotations;
    }

    public void setAnnotations(List<MappingAnnotation> value) {
        this.annotations = value;
    }

    @Override
    public MappingExpressionView measureExpression() {
        return measureExpression;
    }

    public void setMeasureExpression(MappingExpressionView value) {
        this.measureExpression = value;
    }

    @Override
    public List<MappingCalculatedMemberProperty> calculatedMemberProperties() {
        if (calculatedMemberProperties == null) {
            calculatedMemberProperties = new ArrayList<>();
        }
        return this.calculatedMemberProperties;
    }

    @Override
    public String name() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public String column() {
        return column;
    }

    public void setColumn(String value) {
        this.column = value;
    }

    @Override
    public MeasureDataTypeEnum datatype() {
        return datatype;
    }

    public void setDatatype(MeasureDataTypeEnum value) {
        this.datatype = value;
    }

    @Override
    public String formatString() {
        return formatString;
    }

    public void setFormatString(String value) {
        this.formatString = value;
    }

    @Override
    public String aggregator() {
        return aggregator;
    }

    public void setAggregator(String value) {
        this.aggregator = value;
    }

    @Override
    public String formatter() {
        return formatter;
    }

    public void setFormatter(String value) {
        this.formatter = value;
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

    public void setDescription(String value) {
        this.description = value;
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

    @Override
    public String displayFolder() {
        return displayFolder;
    }

    @Override
    public MappingElementFormatter cellFormatter() {
        return cellFormatter;
    }

    @Override
    public String backColor() {
        return backColor;
    }

    public void setDisplayFolder(String value) {
        this.displayFolder = value;
    }

    public void setCalculatedMemberProperties(List<MappingCalculatedMemberProperty> calculatedMemberProperties) {
        this.calculatedMemberProperties = calculatedMemberProperties;
    }

    public void setCellFormatter(CellFormatterImpl cellFormatter) {
        this.cellFormatter = cellFormatter;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }
}
