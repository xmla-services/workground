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
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine300_300.XEvent;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "events", "xEvent" })
@XmlRootElement(name = "EventType")
public class EventType {

  @XmlElement(name = "Events")
  protected EventType.Events events;
  @XmlElement(name = "XEvent", namespace = "http://schemas.microsoft.com/analysisservices/2011/engine/300/300")
  protected XEvent xEvent;

  public EventType.Events getEvents() {
    return events;
  }

  public void setEvents(EventType.Events value) {
    this.events = value;
  }

  public XEvent getXEvent() {
    return xEvent;
  }

  public void setXEvent(XEvent value) {
    this.xEvent = value;
  }

  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = { "event" })
  public static class Events {

    @XmlElement(name = "Event")
    protected List<Event> event;

    public List<Event> getEvent() {
      return this.event;
    }

      public void setEvent(List<Event> event) {
          this.event = event;
      }
  }
}
