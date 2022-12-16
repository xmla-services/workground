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
package org.eclipse.daanse.olap.rolap.dbmapper.mondrian;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.api.*;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "annotations", "keyExpression", "nameExpression", "captionExpression",
        "ordinalExpression", "parentExpression", "closure", "property", "memberFormatter" })
public class LevelImpl implements Level {

    @XmlElement(name = "Annotation")
    @XmlElementWrapper(name = "Annotations")
    protected List<AnnotationImpl> annotations;
    @XmlElement(name = "KeyExpression")
    protected ExpressionViewImpl keyExpression;
    @XmlElement(name = "NameExpression")
    protected ExpressionViewImpl nameExpression;
    @XmlElement(name = "CaptionExpression")
    protected ExpressionViewImpl captionExpression;
    @XmlElement(name = "OrdinalExpression")
    protected ExpressionViewImpl ordinalExpression;
    @XmlElement(name = "ParentExpression")
    protected ExpressionViewImpl parentExpression;
    @XmlElement(name = "Closure")
    protected ClosureImpl closure;
    @XmlElement(name = "Property")
    protected List<PropertyImpl> property;
    @XmlAttribute(name = "approxRowCount")
    protected String approxRowCount;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "table")
    protected String table;
    @XmlAttribute(name = "column")
    protected String column;
    @XmlAttribute(name = "nameColumn")
    protected String nameColumn;
    @XmlAttribute(name = "ordinalColumn")
    protected String ordinalColumn;
    @XmlAttribute(name = "parentColumn")
    protected String parentColumn;
    @XmlAttribute(name = "nullParentValue")
    protected String nullParentValue;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "uniqueMembers")
    protected Boolean uniqueMembers;
    @XmlAttribute(name = "levelType")
    protected String levelType;
    @XmlAttribute(name = "hideMemberIf")
    protected String hideMemberIf;
    @XmlAttribute(name = "formatter")
    protected String formatter;
    @XmlAttribute(name = "caption")
    protected String caption;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "captionColumn")
    protected String captionColumn;
    @XmlAttribute(name = "visible")
    protected Boolean visible = true;
    @XmlAttribute(name = "internalType") //{"int", "long", "Object", "String"}
    protected String internalType;
    @XmlElement(name = "MemberFormatter")
    ElementFormatterImpl memberFormatter;

    @Override
    public List<AnnotationImpl> annotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationImpl> value) {
        this.annotations = value;
    }

    @Override
    public ExpressionView keyExpression() {
        return keyExpression;
    }

    public void setKeyExpression(ExpressionViewImpl value) {
        this.keyExpression = value;
    }

    @Override
    public ExpressionViewImpl nameExpression() {
        return nameExpression;
    }

    public void setNameExpression(ExpressionViewImpl value) {
        this.nameExpression = value;
    }

    @Override
    public ExpressionView captionExpression() {
        return captionExpression;
    }

    public void setCaptionExpression(ExpressionViewImpl value) {
        this.captionExpression = value;
    }

    @Override
    public ExpressionView ordinalExpression() {
        return ordinalExpression;
    }

    public void setOrdinalExpression(ExpressionViewImpl value) {
        this.ordinalExpression = value;
    }

    @Override
    public ExpressionViewImpl parentExpression() {
        return parentExpression;
    }

    public void setParentExpression(ExpressionViewImpl value) {
        this.parentExpression = value;
    }

    @Override
    public Closure closure() {
        return closure;
    }

    public void setClosure(ClosureImpl value) {
        this.closure = value;
    }

    @Override
    public List<PropertyImpl> property() {
        if (property == null) {
            property = new ArrayList<PropertyImpl>();
        }
        return this.property;
    }

    @Override
    public String approxRowCount() {
        return approxRowCount;
    }

    public void setApproxRowCount(String value) {
        this.approxRowCount = value;
    }

    @Override
    public String name() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public String table() {
        return table;
    }

    public void setTable(String value) {
        this.table = value;
    }

    @Override
    public String column() {
        return column;
    }

    public void setColumn(String value) {
        this.column = value;
    }

    @Override
    public String nameColumn() {
        return nameColumn;
    }

    public void setNameColumn(String value) {
        this.nameColumn = value;
    }

    @Override
    public String ordinalColumn() {
        return ordinalColumn;
    }

    public void setOrdinalColumn(String value) {
        this.ordinalColumn = value;
    }

    @Override
    public String parentColumn() {
        return parentColumn;
    }

    public void setParentColumn(String value) {
        this.parentColumn = value;
    }

    @Override
    public String nullParentValue() {
        return nullParentValue;
    }

    public void setNullParentValue(String value) {
        this.nullParentValue = value;
    }

    @Override
    public String type() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    @Override
    public boolean uniqueMembers() {
        if (uniqueMembers == null) {
            return false;
        } else {
            return uniqueMembers;
        }
    }

    public void setUniqueMembers(Boolean value) {
        this.uniqueMembers = value;
    }

    @Override
    public String levelType() {
        if (levelType == null) {
            return "Regular";
        } else {
            return levelType;
        }
    }

    public void setLevelType(String value) {
        this.levelType = value;
    }

    @Override
    public String hideMemberIf() {
        if (hideMemberIf == null) {
            return "Never";
        } else {
            return hideMemberIf;
        }
    }

    public void setHideMemberIf(String value) {
        this.hideMemberIf = value;
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
    public String captionColumn() {
        return captionColumn;
    }

    @Override
    public boolean visible() {
        return visible;
    }

    @Override
    public String internalType() {
        return internalType;
    }

    @Override
    public ElementFormatter memberFormatter() {
        return memberFormatter;
    }

    @Override
    public Expression getPropertyExp(int i) {
        return new ColumnImpl(table, property.get(i).column());
    }

    public void setCaptionColumn(String value) {
        this.captionColumn = value;
    }

    public void setProperty(List<PropertyImpl> property) {
        this.property = property;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
