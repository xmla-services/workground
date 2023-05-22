/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena - initial
 *   Stefan Bischof (bipolis.org) - initial
 */
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MiningModelColumn", propOrder = {

})
public class MiningModelColumn {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "SourceColumnID", required = true)
    protected String sourceColumnID;
    @XmlElement(name = "Usage")
    protected String usage;
    @XmlElement(name = "Filter")
    protected String filter;
    @XmlElement(name = "Translations")
    protected MiningModelColumn.Translations translations;
    @XmlElement(name = "Columns")
    protected MiningModelColumn.Columns columns;
    @XmlElement(name = "ModelingFlags")
    protected MiningModelColumn.ModelingFlags modelingFlags;
    @XmlElement(name = "Annotations")
    protected Annotations annotations;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getID() {
        return id;
    }

    public void setID(String value) {
        this.id = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getSourceColumnID() {
        return sourceColumnID;
    }

    public void setSourceColumnID(String value) {
        this.sourceColumnID = value;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String value) {
        this.usage = value;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String value) {
        this.filter = value;
    }

    public MiningModelColumn.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(MiningModelColumn.Translations value) {
        this.translations = value;
    }

    public MiningModelColumn.Columns getColumns() {
        return columns;
    }

    public void setColumns(MiningModelColumn.Columns value) {
        this.columns = value;
    }

    public MiningModelColumn.ModelingFlags getModelingFlags() {
        return modelingFlags;
    }

    public void setModelingFlags(MiningModelColumn.ModelingFlags value) {
        this.modelingFlags = value;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotations value) {
        this.annotations = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"column"})
    public static class Columns {

        @XmlElement(name = "Column")
        protected List<MiningModelColumn> column;

        public List<MiningModelColumn> getColumn() {
            return this.column;
        }

        public void setColumn(List<MiningModelColumn> column) {
            this.column = column;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"modelingFlag"})
    public static class ModelingFlags {

        @XmlElement(name = "ModelingFlag")
        protected List<MiningModelingFlag> modelingFlag;

        public List<MiningModelingFlag> getModelingFlag() {
            return this.modelingFlag;
        }

        public void setModelingFlag(List<MiningModelingFlag> modelingFlag) {
            this.modelingFlag = modelingFlag;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"translation"})
    public static class Translations {

        @XmlElement(name = "Translation")
        protected List<Translation> translation;

        public List<Translation> getTranslation() {
            return this.translation;
        }

        public void setTranslation(List<Translation> translation) {
            this.translation = translation;
        }
    }

}
