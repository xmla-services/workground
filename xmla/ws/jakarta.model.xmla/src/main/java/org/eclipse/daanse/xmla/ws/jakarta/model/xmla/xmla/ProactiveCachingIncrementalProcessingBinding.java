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

import javax.xml.datatype.Duration;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProactiveCachingIncrementalProcessingBinding", propOrder = {"refreshInterval",
    "incrementalProcessingNotifications"})
public class ProactiveCachingIncrementalProcessingBinding extends ProactiveCachingBinding {

    @XmlElement(name = "RefreshInterval")
    protected Duration refreshInterval;
    @XmlElement(name = "IncrementalProcessingNotifications", required = true)
    protected ProactiveCachingIncrementalProcessingBinding.IncrementalProcessingNotifications incrementalProcessingNotifications;

    public Duration getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(Duration value) {
        this.refreshInterval = value;
    }

    public ProactiveCachingIncrementalProcessingBinding.IncrementalProcessingNotifications getIncrementalProcessingNotifications() {
        return incrementalProcessingNotifications;
    }

    public void setIncrementalProcessingNotifications(
        ProactiveCachingIncrementalProcessingBinding.IncrementalProcessingNotifications value
    ) {
        this.incrementalProcessingNotifications = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"incrementalProcessingNotification"})
    public static class IncrementalProcessingNotifications {

        @XmlElement(name = "IncrementalProcessingNotification")
        protected List<IncrementalProcessingNotification> incrementalProcessingNotification;

        public List<IncrementalProcessingNotification> getIncrementalProcessingNotification() {
            return this.incrementalProcessingNotification;
        }

        public void setIncrementalProcessingNotification(List<IncrementalProcessingNotification> incrementalProcessingNotification) {
            this.incrementalProcessingNotification = incrementalProcessingNotification;
        }
    }

}
