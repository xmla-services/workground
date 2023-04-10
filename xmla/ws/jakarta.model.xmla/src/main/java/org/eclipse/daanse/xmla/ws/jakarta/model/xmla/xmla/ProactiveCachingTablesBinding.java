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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProactiveCachingTablesBinding", propOrder = {"notificationTechnique", "tableNotifications"})
public class ProactiveCachingTablesBinding extends ProactiveCachingObjectNotificationBinding {

    @XmlElement(name = "NotificationTechnique")
    protected String notificationTechnique;
    @XmlElement(name = "TableNotifications", required = true)
    protected ProactiveCachingTablesBinding.TableNotifications tableNotifications;

    public String getNotificationTechnique() {
        return notificationTechnique;
    }

    public void setNotificationTechnique(String value) {
        this.notificationTechnique = value;
    }

    public ProactiveCachingTablesBinding.TableNotifications getTableNotifications() {
        return tableNotifications;
    }

    public void setTableNotifications(ProactiveCachingTablesBinding.TableNotifications value) {
        this.tableNotifications = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"tableNotification"})
    public static class TableNotifications {

        @XmlElement(name = "TableNotification")
        protected List<TableNotification> tableNotification;

        public List<TableNotification> getTableNotification() {
            return this.tableNotification;
        }

        public void setTableNotification(List<TableNotification> tableNotification) {
            this.tableNotification = tableNotification;
        }
    }

}
