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
@XmlType(name = "Trace", propOrder = {

})
public class Trace {

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
    protected Trace.Annotations annotations;
    @XmlElement(name = "LogFileName")
    protected String logFileName;
    @XmlElement(name = "LogFileAppend")
    protected Boolean logFileAppend;
    @XmlElement(name = "LogFileSize")
    protected Long logFileSize;
    @XmlElement(name = "Audit")
    protected Boolean audit;
    @XmlElement(name = "LogFileRollover")
    protected Boolean logFileRollover;
    @XmlElement(name = "AutoRestart")
    protected Boolean autoRestart;
    @XmlElement(name = "StopTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar stopTime;
    @XmlElement(name = "Filter")
    protected TraceFilter filter;
    @XmlElement(name = "EventType", namespace = "urn:schemas-microsoft-com:xml-analysis", required = true)
    protected EventType eventType;

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

    public Trace.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Trace.Annotations value) {
        this.annotations = value;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String value) {
        this.logFileName = value;
    }

    public Boolean isLogFileAppend() {
        return logFileAppend;
    }

    public void setLogFileAppend(Boolean value) {
        this.logFileAppend = value;
    }

    public Long getLogFileSize() {
        return logFileSize;
    }

    public void setLogFileSize(Long value) {
        this.logFileSize = value;
    }

    public Boolean isAudit() {
        return audit;
    }

    public void setAudit(Boolean value) {
        this.audit = value;
    }

    public Boolean isLogFileRollover() {
        return logFileRollover;
    }

    public void setLogFileRollover(Boolean value) {
        this.logFileRollover = value;
    }

    public Boolean isAutoRestart() {
        return autoRestart;
    }

    public void setAutoRestart(Boolean value) {
        this.autoRestart = value;
    }

    public XMLGregorianCalendar getStopTime() {
        return stopTime;
    }

    public void setStopTime(XMLGregorianCalendar value) {
        this.stopTime = value;
    }

    public TraceFilter getFilter() {
        return filter;
    }

    public void setFilter(TraceFilter value) {
        this.filter = value;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType value) {
        this.eventType = value;
    }

    public boolean isSetEventType() {
        return (this.eventType != null);
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

}
