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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportAction", propOrder = {"name", "id", "caption", "captionIsMdx", "translations", "targetType",
    "target", "condition", "type", "invocation", "application", "description", "annotations", "reportServer", "path",
    "reportParameters", "reportFormatParameters"})
public class ReportAction extends Action {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "Caption")
    protected String caption;
    @XmlElement(name = "CaptionIsMdx")
    protected Boolean captionIsMdx;
    @XmlElement(name = "Translations")
    protected ReportAction.Translations translations;
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
    protected ReportAction.Annotations annotations;
    @XmlElement(name = "ReportServer", required = true)
    protected String reportServer;
    @XmlElement(name = "Path")
    protected String path;
    @XmlElement(name = "ReportParameters")
    protected ReportAction.ReportParameters reportParameters;
    @XmlElement(name = "ReportFormatParameters")
    protected ReportAction.ReportFormatParameters reportFormatParameters;

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

    public ReportAction.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(ReportAction.Translations value) {
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

    public boolean isSetTarget() {
        return (this.target != null);
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

    public ReportAction.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(ReportAction.Annotations value) {
        this.annotations = value;
    }

    public String getReportServer() {
        return reportServer;
    }

    public void setReportServer(String value) {
        this.reportServer = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String value) {
        this.path = value;
    }

    public ReportAction.ReportParameters getReportParameters() {
        return reportParameters;
    }

    public void setReportParameters(ReportAction.ReportParameters value) {
        this.reportParameters = value;
    }

    public ReportAction.ReportFormatParameters getReportFormatParameters() {
        return reportFormatParameters;
    }

    public void setReportFormatParameters(ReportAction.ReportFormatParameters value) {
        this.reportFormatParameters = value;
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
    @XmlType(name = "", propOrder = {"reportFormatParameter"})
    public static class ReportFormatParameters {

        @XmlElement(name = "ReportFormatParameter")
        protected List<ReportFormatParameter> reportFormatParameter;

        public List<ReportFormatParameter> getReportFormatParameter() {
            return this.reportFormatParameter;
        }

        public void setReportFormatParameter(List<ReportFormatParameter> reportFormatParameter) {
            this.reportFormatParameter = reportFormatParameter;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"reportParameter"})
    public static class ReportParameters {

        @XmlElement(name = "ReportParameter")
        protected List<ReportParameter> reportParameter;

        public List<ReportParameter> getReportParameter() {
            return this.reportParameter;
        }

        public void setReportParameter(List<ReportParameter> reportParameter) {
            this.reportParameter = reportParameter;
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
