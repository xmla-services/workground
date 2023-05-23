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
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StandardAction", propOrder = {"name", "id", "caption", "captionIsMdx", "translations", "targetType",
    "target", "condition", "type", "invocation", "application", "description", "annotations", "expression"})
public class StandardAction extends Action {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "Caption")
    protected String caption;
    @XmlElement(name = "CaptionIsMdx")
    protected Boolean captionIsMdx;
    @XmlElement(name = "Translations")
    protected StandardAction.Translations translations;
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
    @XmlElementWrapper(name = "Annotations")
    @XmlElement(name = "Annotation", type = Annotation.class)
    protected List<Annotation> annotations;
    @XmlElement(name = "Expression", required = true)
    protected String expression;

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

    public StandardAction.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(StandardAction.Translations value) {
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

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> value) {
        this.annotations = value;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String value) {
        this.expression = value;
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
