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
@XmlType(name = "CubePermission", propOrder = {"readSourceData", "dimensionPermissions", "cellPermissions"})
public class CubePermission extends Permission {

    @XmlElement(name = "ReadSourceData")
    protected String readSourceData;
    @XmlElement(name = "DimensionPermissions")
    protected CubePermission.DimensionPermissions dimensionPermissions;
    @XmlElement(name = "CellPermissions")
    protected CubePermission.CellPermissions cellPermissions;

    public String getReadSourceData() {
        return readSourceData;
    }

    public void setReadSourceData(String value) {
        this.readSourceData = value;
    }

    public CubePermission.DimensionPermissions getDimensionPermissions() {
        return dimensionPermissions;
    }

    public void setDimensionPermissions(CubePermission.DimensionPermissions value) {
        this.dimensionPermissions = value;
    }

    public CubePermission.CellPermissions getCellPermissions() {
        return cellPermissions;
    }

    public void setCellPermissions(CubePermission.CellPermissions value) {
        this.cellPermissions = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"cellPermission"})
    public static class CellPermissions {

        @XmlElement(name = "CellPermission")
        protected List<CellPermission> cellPermission;

        public List<CellPermission> getCellPermission() {
            return this.cellPermission;
        }

        public void setCellPermission(List<CellPermission> cellPermission) {
            this.cellPermission = cellPermission;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"dimensionPermission"})
    public static class DimensionPermissions {

        @XmlElement(name = "DimensionPermission")
        protected List<CubeDimensionPermission> dimensionPermission;

        public List<CubeDimensionPermission> getDimensionPermission() {
            return this.dimensionPermission;
        }

        public void setDimensionPermission(List<CubeDimensionPermission> dimensionPermission) {
            this.dimensionPermission = dimensionPermission;
        }
    }

}
