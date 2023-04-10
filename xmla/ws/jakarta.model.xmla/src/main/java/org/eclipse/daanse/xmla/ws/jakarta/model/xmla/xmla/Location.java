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
@XmlType(name = "Location", propOrder = {"dataSourceType", "connectionString", "folders"})
public class Location extends LocationBackup {

    @XmlElement(name = "DataSourceType")
    protected String dataSourceType;
    @XmlElement(name = "ConnectionString")
    protected String connectionString;
    @XmlElement(name = "Folders")
    protected Location.Folders folders;

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String value) {
        this.dataSourceType = value;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String value) {
        this.connectionString = value;
    }

    public Location.Folders getFolders() {
        return folders;
    }

    public void setFolders(Location.Folders value) {
        this.folders = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"folder"})
    public static class Folders {

        @XmlElement(name = "Folder")
        protected List<Folder> folder;

        public List<Folder> getFolder() {
            return this.folder;
        }

        public void setFolder(List<Folder> folder) {
            this.folder = folder;
        }
    }

}
