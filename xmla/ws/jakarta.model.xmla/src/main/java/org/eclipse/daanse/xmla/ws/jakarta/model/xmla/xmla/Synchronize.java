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
@XmlType(name = "Synchronize", propOrder = {

})
public class Synchronize {

    @XmlElement(name = "Source", required = true)
    protected Source source;
    @XmlElement(name = "SynchronizeSecurity")
    protected String synchronizeSecurity;
    @XmlElement(name = "ApplyCompression")
    protected Boolean applyCompression;
    @XmlElement(name = "DbStorageLocation", namespace = "http://schemas.microsoft" +
        ".com/analysisservices/2008/engine/100/100")
    protected String dbStorageLocation;
    @XmlElement(name = "Locations")
    protected Synchronize.Locations locations;

    public Source getSource() {
        return source;
    }

    public void setSource(Source value) {
        this.source = value;
    }

    public String getSynchronizeSecurity() {
        return synchronizeSecurity;
    }

    public void setSynchronizeSecurity(String value) {
        this.synchronizeSecurity = value;
    }

    public Boolean isApplyCompression() {
        return applyCompression;
    }

    public void setApplyCompression(Boolean value) {
        this.applyCompression = value;
    }

    public String getDbStorageLocation() {
        return dbStorageLocation;
    }

    public void setDbStorageLocation(String value) {
        this.dbStorageLocation = value;
    }

    public Synchronize.Locations getLocations() {
        return locations;
    }

    public void setLocations(Synchronize.Locations value) {
        this.locations = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"location"})
    public static class Locations {

        @XmlElement(name = "Location")
        protected List<Location> location;

        public List<Location> getLocation() {
            return this.location;
        }

        public void setLocation(List<Location> location) {
            this.location = location;
        }
    }

}
