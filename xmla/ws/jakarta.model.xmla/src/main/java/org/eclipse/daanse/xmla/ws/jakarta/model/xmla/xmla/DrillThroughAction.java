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
@XmlType(name = "DrillThroughAction", propOrder = {"name", "id", "caption", "captionIsMdx", "translations",
    "targetType", "target", "condition", "type", "invocation", "application", "description", "annotations", "_default",
    "columns", "maximumRows"})
public class DrillThroughAction extends Action {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "Caption")
    protected String caption;
    @XmlElement(name = "CaptionIsMdx")
    protected Boolean captionIsMdx;
    @XmlElement(name = "Translations")
    protected DrillThroughAction.Translations translations;
    @XmlElement(name = "TargetType", required = true)
    protected String targetType;
    @XmlElement(name = "Target")
    protected String target;
    @XmlElement(name = "Condition")
    protected String condition;
    @XmlElement(name = "Type", required = true)
    protected String type;
    @XmlElement(name = "Invocation")
    protected String invocation;
    @XmlElement(name = "Application")
    protected String application;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Annotations")
    protected DrillThroughAction.Annotations annotations;
    @XmlElement(name = "Default")
    protected Boolean _default;
    @XmlElement(name = "Columns")
    protected DrillThroughAction.Columns columns;
    @XmlElement(name = "MaximumRows")
    protected Integer maximumRows;

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

    public boolean isSetID() {
        return (this.id != null);
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String value) {
        this.caption = value;
    }

    public Boolean isCaptionIsMdx() {
        return captionIsMdx;
    }

    public void setCaptionIsMdx(Boolean value) {
        this.captionIsMdx = value;
    }

    public DrillThroughAction.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(DrillThroughAction.Translations value) {
        this.translations = value;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String value) {
        this.targetType = value;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String value) {
        this.target = value;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String value) {
        this.condition = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getInvocation() {
        return invocation;
    }

    public void setInvocation(String value) {
        this.invocation = value;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String value) {
        this.application = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public DrillThroughAction.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(DrillThroughAction.Annotations value) {
        this.annotations = value;
    }

    public Boolean isDefault() {
        return _default;
    }

    public void setDefault(Boolean value) {
        this._default = value;
    }

    public DrillThroughAction.Columns getColumns() {
        return columns;
    }

    public void setColumns(DrillThroughAction.Columns value) {
        this.columns = value;
    }

    public Integer getMaximumRows() {
        return maximumRows;
    }

    public void setMaximumRows(Integer value) {
        this.maximumRows = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"annotation"})
    public static class Annotations {

        @XmlElement(name = "Annotation")
        protected List<Annotation> annotation;

        public List<Annotation> getAnnotation() {
            return this.annotation;
        }

        public void setAnnotation(List<Annotation> annotation) {
            this.annotation = annotation;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"column"})
    public static class Columns {

        @XmlElement(name = "Column")
        protected List<Binding> column;

        public List<Binding> getColumn() {
            return this.column;
        }

        public void setColumn(List<Binding> column) {
            this.column = column;
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
