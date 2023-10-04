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
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingClosure;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingElementFormatter;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingExpressionView;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingLevel;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingProperty;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.HideMemberIfEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.InternalTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.LevelTypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.enums.TypeEnum;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.adapter.HideMemberIfAdaptor;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.adapter.InternalTypeAdaptor;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.adapter.LevelTypeAdaptor;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.adapter.TypeAdaptor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "annotations", "keyExpression", "nameExpression", "captionExpression",
        "ordinalExpression", "parentExpression", "closure", "properties", "memberFormatter" })
public class LevelImpl implements MappingLevel {

    @XmlElement(name = "Annotation", type = AnnotationImpl.class)
    @XmlElementWrapper(name = "Annotations")
    protected List<MappingAnnotation> annotations;
    @XmlElement(name = "KeyExpression", type = ExpressionViewImpl.class)
    protected MappingExpressionView keyExpression;
    @XmlElement(name = "NameExpression", type = ExpressionViewImpl.class)
    protected MappingExpressionView nameExpression;
    @XmlElement(name = "CaptionExpression", type = ExpressionViewImpl.class)
    protected MappingExpressionView captionExpression;
    @XmlElement(name = "OrdinalExpression", type = ExpressionViewImpl.class)
    protected MappingExpressionView ordinalExpression;
    @XmlElement(name = "ParentExpression", type = ExpressionViewImpl.class)
    protected MappingExpressionView parentExpression;
    @XmlElement(name = "Closure", type = ClosureImpl.class)
    protected MappingClosure closure;
    @XmlElement(name = "Property", type = PropertyImpl.class)
    protected List<MappingProperty> properties;
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
    @XmlJavaTypeAdapter(TypeAdaptor.class)
    protected TypeEnum type;
    @XmlAttribute(name = "uniqueMembers")
    protected Boolean uniqueMembers;
    @XmlAttribute(name = "levelType")
    @XmlJavaTypeAdapter(LevelTypeAdaptor.class)
    protected LevelTypeEnum levelType;
    @XmlAttribute(name = "hideMemberIf")
    @XmlJavaTypeAdapter(HideMemberIfAdaptor.class)
    protected HideMemberIfEnum hideMemberIf;
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
    @XmlAttribute(name = "internalType")
    @XmlJavaTypeAdapter(InternalTypeAdaptor.class)
    protected InternalTypeEnum internalType;
    @XmlElement(name = "MemberFormatter", type = ElementFormatterImpl.class)
    MappingElementFormatter memberFormatter;

    @Override
    public List<MappingAnnotation> annotations() {
        return annotations;
    }

    public void setAnnotations(List<MappingAnnotation> value) {
        this.annotations = value;
    }

    @Override
    public MappingExpressionView keyExpression() {
        return keyExpression;
    }

    public void setKeyExpression(MappingExpressionView value) {
        this.keyExpression = value;
    }

    @Override
    public MappingExpressionView nameExpression() {
        return nameExpression;
    }

    public void setNameExpression(MappingExpressionView value) {
        this.nameExpression = value;
    }

    @Override
    public MappingExpressionView captionExpression() {
        return captionExpression;
    }

    public void setCaptionExpression(MappingExpressionView value) {
        this.captionExpression = value;
    }

    @Override
    public MappingExpressionView ordinalExpression() {
        return ordinalExpression;
    }

    public void setOrdinalExpression(MappingExpressionView value) {
        this.ordinalExpression = value;
    }

    @Override
    public MappingExpressionView parentExpression() {
        return  parentExpression;
    }

    public void setParentExpression(MappingExpressionView value) {
        this.parentExpression = value;
    }

    @Override
    public MappingClosure closure() {
        return closure;
    }

    public void setClosure(MappingClosure value) {
        this.closure = value;
    }

    @Override
    public List<MappingProperty> properties() {
        if (properties == null) {
            properties = new ArrayList<>();
        }
        return this.properties;
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
    public TypeEnum type() {
        return type != null ? type : TypeEnum.STRING;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    @Override
    public Boolean uniqueMembers() {
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
    public LevelTypeEnum levelType() {
        if (levelType == null) {
            return LevelTypeEnum.REGULAR;
        } else {
            return levelType;
        }
    }

    public void setLevelType(LevelTypeEnum value) {
        this.levelType = value;
    }

    @Override
    public HideMemberIfEnum hideMemberIf() {
        if (hideMemberIf == null) {
            return HideMemberIfEnum.NEVER;
        } else {
            return hideMemberIf;
        }
    }

    public void setHideMemberIf(HideMemberIfEnum value) {
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
    public Boolean visible() {
        return visible;
    }

    @Override
    public InternalTypeEnum internalType() {
        return internalType;
    }

    @Override
    public MappingElementFormatter memberFormatter() {
        return memberFormatter;
    }

    public void setCaptionColumn(String value) {
        this.captionColumn = value;
    }

    public void setProperties(List<MappingProperty> properties) {
        this.properties = properties;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public void setInternalType(InternalTypeEnum internalType) {
        this.internalType = internalType;
    }

    public void setMemberFormatter(MappingElementFormatter memberFormatter) {
        this.memberFormatter = memberFormatter;
    }
}
