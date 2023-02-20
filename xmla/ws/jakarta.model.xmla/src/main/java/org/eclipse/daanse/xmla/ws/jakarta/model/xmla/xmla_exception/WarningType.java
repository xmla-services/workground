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
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla_exception;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WarningType", propOrder = {

})
public class WarningType implements Serializable {

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Location")
    protected MessageLocation location;
    @XmlAttribute(name = "WarningCode")
    protected Integer warningCode;
    @XmlAttribute(name = "Description")
    protected String description;
    @XmlAttribute(name = "Source")
    protected String source;
    @XmlAttribute(name = "HelpFile")
    protected String helpFile;

    public MessageLocation getLocation() {
        return location;
    }

    public void setLocation(MessageLocation value) {
        this.location = value;
    }

    public int getWarningCode() {
        return warningCode;
    }

    public void setWarningCode(Integer value) {
        this.warningCode = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String value) {
        this.source = value;
    }

    public String getHelpFile() {
        return helpFile;
    }

    public void setHelpFile(String value) {
        this.helpFile = value;
    }

}
