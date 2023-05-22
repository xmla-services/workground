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
@XmlType(name = "MdxScript", propOrder = {

})
public class MdxScript extends AbstractItem {

    @XmlElement(name = "Commands")
    protected MdxScript.Commands commands;
    @XmlElement(name = "DefaultScript")
    protected Boolean defaultScript;
    @XmlElement(name = "CalculationProperties")
    protected MdxScript.CalculationProperties calculationProperties;

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
