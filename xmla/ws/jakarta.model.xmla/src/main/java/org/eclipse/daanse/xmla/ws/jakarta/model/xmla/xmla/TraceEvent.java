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

import java.math.BigInteger;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TraceEvent", propOrder = {

})
public class TraceEvent {

    @XmlElement(name = "ID", required = true)
    protected BigInteger id;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "EventColumnList", required = true)
    protected TraceEvent.EventColumnList eventColumnList;

    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public TraceEvent.EventColumnList getEventColumnList() {
        return eventColumnList;
    }

    public void setEventColumnList(TraceEvent.EventColumnList value) {
        this.eventColumnList = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"eventColumn"})
    public static class EventColumnList {

        @XmlElement(name = "EventColumn")
        protected List<EventColumn> eventColumn;

        public List<EventColumn> getEventColumn() {
            return this.eventColumn;
        }

        public void setEventColumn(List<EventColumn> eventColumn) {
            this.eventColumn = eventColumn;
        }
    }

}
