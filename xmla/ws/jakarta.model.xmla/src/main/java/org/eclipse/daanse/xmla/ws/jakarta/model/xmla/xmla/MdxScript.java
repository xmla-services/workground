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
@XmlType(name = "MdxScript", propOrder = {

})
public class MdxScript {

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
    protected MdxScript.Annotations annotations;
    @XmlElement(name = "Commands")
    protected MdxScript.Commands commands;
    @XmlElement(name = "DefaultScript")
    protected Boolean defaultScript;
    @XmlElement(name = "CalculationProperties")
    protected MdxScript.CalculationProperties calculationProperties;

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

    public MdxScript.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(MdxScript.Annotations value) {
        this.annotations = value;
    }

    public MdxScript.Commands getCommands() {
        return commands;
    }

    public void setCommands(MdxScript.Commands value) {
        this.commands = value;
    }

    public Boolean isDefaultScript() {
        return defaultScript;
    }

    public void setDefaultScript(Boolean value) {
        this.defaultScript = value;
    }

    public MdxScript.CalculationProperties getCalculationProperties() {
        return calculationProperties;
    }

    public void setCalculationProperties(MdxScript.CalculationProperties value) {
        this.calculationProperties = value;
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
    @XmlType(name = "", propOrder = {"calculationProperty"})
    public static class CalculationProperties {

        @XmlElement(name = "CalculationProperty")
        protected List<CalculationProperty> calculationProperty;

        public List<CalculationProperty> getCalculationProperty() {
            return this.calculationProperty;
        }

        public void setCalculationProperty(List<CalculationProperty> calculationProperty) {
            this.calculationProperty = calculationProperty;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"command"})
    public static class Commands {

        @XmlElement(name = "Command")
        protected List<Command> command;

        public List<Command> getCommand() {
            return this.command;
        }

        public void setCommand(List<Command> command) {
            this.command = command;
        }
    }

}
