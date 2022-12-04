
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Hierarchy;
import org.eclipse.daanse.olap.rolap.dbmapper.api.InlineTable;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Join;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Table;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Hierarchy", propOrder = { "annotations", "table", "view", "join", "inlineTable", "level",
        "memberReaderParameter" })
public class HierarchyImpl implements Hierarchy {

    @XmlElement(name = "Annotation")
    @XmlElementWrapper(name = "Annotations")
    protected List<AnnotationImpl> annotations;
    @XmlElement(name = "Table")
    protected TableImpl table;
    @XmlElement(name = "View")
    protected ViewImpl view;
    @XmlElement(name = "Join")
    protected JoinImpl join;
    @XmlElement(name = "InlineTable")
    protected InlineTableImpl inlineTable;
    @XmlElement(name = "Level", required = true)
    protected List<LevelImpl> level;
    @XmlElement(name = "MemberReaderParameter")
    protected List<MemberReaderParameterImpl> memberReaderParameter;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "hasAll", required = true)
    protected boolean hasAll;
    @XmlAttribute(name = "allMemberName")
    protected String allMemberName;
    @XmlAttribute(name = "allMemberCaption")
    protected String allMemberCaption;
    @XmlAttribute(name = "allLevelName")
    protected String allLevelName;
    @XmlAttribute(name = "primaryKey")
    protected String primaryKey;
    @XmlAttribute(name = "primaryKeyTable")
    protected String primaryKeyTable;
    @XmlAttribute(name = "defaultMember")
    protected String defaultMember;
    @XmlAttribute(name = "memberReaderClass")
    protected String memberReaderClass;
    @XmlAttribute(name = "caption")
    protected String caption;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "uniqueKeyLevelName")
    protected String uniqueKeyLevelName;

    @Override
    public List<AnnotationImpl> annotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationImpl> value) {
        this.annotations = value;
    }

    @Override
    public Table table() {
        return table;
    }

    public void setTable(TableImpl value) {
        this.table = value;
    }

    @Override
    public ViewImpl view() {
        return view;
    }

    public void setView(ViewImpl value) {
        this.view = value;
    }

    @Override
    public Join join() {
        return join;
    }

    public void setJoin(JoinImpl value) {
        this.join = value;
    }

    @Override
    public InlineTable inlineTable() {
        return inlineTable;
    }

    public void setInlineTable(InlineTableImpl value) {
        this.inlineTable = value;
    }

    @Override
    public List<LevelImpl> level() {
        if (level == null) {
            level = new ArrayList<LevelImpl>();
        }
        return this.level;
    }

    @Override
    public List<MemberReaderParameterImpl> memberReaderParameter() {
        if (memberReaderParameter == null) {
            memberReaderParameter = new ArrayList<MemberReaderParameterImpl>();
        }
        return this.memberReaderParameter;
    }

    @Override
    public String name() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Override
    public boolean hasAll() {
        return hasAll;
    }

    public void setHasAll(boolean value) {
        this.hasAll = value;
    }

    @Override
    public String allMemberName() {
        return allMemberName;
    }

    public void setAllMemberName(String value) {
        this.allMemberName = value;
    }

    @Override
    public String allMemberCaption() {
        return allMemberCaption;
    }

    public void setAllMemberCaption(String value) {
        this.allMemberCaption = value;
    }

    @Override
    public String allLevelName() {
        return allLevelName;
    }

    public void setAllLevelName(String value) {
        this.allLevelName = value;
    }

    @Override
    public String primaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String value) {
        this.primaryKey = value;
    }

    @Override
    public String primaryKeyTable() {
        return primaryKeyTable;
    }

    public void setPrimaryKeyTable(String value) {
        this.primaryKeyTable = value;
    }

    @Override
    public String defaultMember() {
        return defaultMember;
    }

    public void setDefaultMember(String value) {
        this.defaultMember = value;
    }

    @Override
    public String memberReaderClass() {
        return memberReaderClass;
    }

    public void setMemberReaderClass(String value) {
        this.memberReaderClass = value;
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
    public String uniqueKeyLevelName() {
        return uniqueKeyLevelName;
    }

    public void setUniqueKeyLevelName(String value) {
        this.uniqueKeyLevelName = value;
    }

}
