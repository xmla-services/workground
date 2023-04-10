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

import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Perspective", propOrder = {

})
public class Perspective {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "CreatedTimestamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdTimestamp;
    @XmlElement(name = "LastSchemaUpdate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastSchemaUpdate;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Annotations")
    protected Perspective.Annotations annotations;
    @XmlElement(name = "Translations")
    protected Perspective.Translations translations;
    @XmlElement(name = "DefaultMeasure")
    protected String defaultMeasure;
    @XmlElement(name = "Dimensions")
    protected Perspective.Dimensions dimensions;
    @XmlElement(name = "MeasureGroups")
    protected Perspective.MeasureGroups measureGroups;
    @XmlElement(name = "Calculations")
    protected Perspective.Calculations calculations;
    @XmlElement(name = "Kpis")
    protected Perspective.Kpis kpis;
    @XmlElement(name = "Actions")
    protected Perspective.Actions actions;

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

    public XMLGregorianCalendar getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(XMLGregorianCalendar value) {
        this.createdTimestamp = value;
    }

    public XMLGregorianCalendar getLastSchemaUpdate() {
        return lastSchemaUpdate;
    }

    public void setLastSchemaUpdate(XMLGregorianCalendar value) {
        this.lastSchemaUpdate = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public Perspective.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Perspective.Annotations value) {
        this.annotations = value;
    }

    public Perspective.Translations getTranslations() {
        return translations;
    }

    public void setTranslations(Perspective.Translations value) {
        this.translations = value;
    }

    public String getDefaultMeasure() {
        return defaultMeasure;
    }

    public void setDefaultMeasure(String value) {
        this.defaultMeasure = value;
    }

    public Perspective.Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Perspective.Dimensions value) {
        this.dimensions = value;
    }

    public Perspective.MeasureGroups getMeasureGroups() {
        return measureGroups;
    }

    public void setMeasureGroups(Perspective.MeasureGroups value) {
        this.measureGroups = value;
    }

    public Perspective.Calculations getCalculations() {
        return calculations;
    }

    public void setCalculations(Perspective.Calculations value) {
        this.calculations = value;
    }

    public Perspective.Kpis getKpis() {
        return kpis;
    }

    public void setKpis(Perspective.Kpis value) {
        this.kpis = value;
    }

    public Perspective.Actions getActions() {
        return actions;
    }

    public void setActions(Perspective.Actions value) {
        this.actions = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"action"})
    public static class Actions {

        @XmlElement(name = "Action")
        protected List<PerspectiveAction> action;

        public List<PerspectiveAction> getAction() {
            return this.action;
        }

        public void setAction(List<PerspectiveAction> action) {
            this.action = action;
        }
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
    @XmlType(name = "", propOrder = {"calculation"})
    public static class Calculations {

        @XmlElement(name = "Calculation")
        protected List<PerspectiveCalculation> calculation;

        public List<PerspectiveCalculation> getCalculation() {
            return this.calculation;
        }

        public void setCalculation(List<PerspectiveCalculation> calculation) {
            this.calculation = calculation;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"dimension"})
    public static class Dimensions {

        @XmlElement(name = "Dimension")
        protected List<PerspectiveDimension> dimension;

        public List<PerspectiveDimension> getDimension() {
            return this.dimension;
        }

        public void setDimension(List<PerspectiveDimension> dimension) {
            this.dimension = dimension;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"kpi"})
    public static class Kpis {

        @XmlElement(name = "Kpi")
        protected List<PerspectiveKpi> kpi;

        public List<PerspectiveKpi> getKpi() {
            return this.kpi;
        }

        public void setKpi(List<PerspectiveKpi> kpi) {
            this.kpi = kpi;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"measureGroup"})
    public static class MeasureGroups {

        @XmlElement(name = "MeasureGroup")
        protected List<PerspectiveMeasureGroup> measureGroup;

        public List<PerspectiveMeasureGroup> getMeasureGroup() {
            return this.measureGroup;
        }

        public void setMeasureGroup(List<PerspectiveMeasureGroup> measureGroup) {
            this.measureGroup = measureGroup;
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
