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
@XmlType(name = "ProactiveCachingQueryBinding", propOrder = {"refreshInterval", "queryNotifications"})
public class ProactiveCachingQueryBinding extends ProactiveCachingBinding {

    @XmlElement(name = "RefreshInterval", required = true)
    protected Duration refreshInterval;
    @XmlElement(name = "QueryNotifications", required = true)
    protected ProactiveCachingQueryBinding.QueryNotifications queryNotifications;

    public Duration getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(Duration value) {
        this.refreshInterval = value;
    }

    public ProactiveCachingQueryBinding.QueryNotifications getQueryNotifications() {
        return queryNotifications;
    }

    public void setQueryNotifications(ProactiveCachingQueryBinding.QueryNotifications value) {
        this.queryNotifications = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"queryNotification"})
    public static class QueryNotifications {

        @XmlElement(name = "QueryNotification")
        protected List<QueryNotification> queryNotification;

        public List<QueryNotification> getQueryNotification() {
            return this.queryNotification;
        }

        public void setQueryNotification(List<QueryNotification> queryNotification) {
            this.queryNotification = queryNotification;
        }
    }

}
