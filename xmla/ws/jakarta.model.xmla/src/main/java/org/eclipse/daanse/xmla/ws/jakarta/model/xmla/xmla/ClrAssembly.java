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
@XmlType(name = "ClrAssembly", propOrder = {"files", "permissionSet"})
public class ClrAssembly extends Assembly {

    @XmlElement(name = "Files", required = true)
    protected ClrAssembly.Files files;
    @XmlElement(name = "PermissionSet")
    protected String permissionSet;

    public ClrAssembly.Files getFiles() {
        return files;
    }

    public void setFiles(ClrAssembly.Files value) {
        this.files = value;
    }

    public String getPermissionSet() {
        return permissionSet;
    }

    public void setPermissionSet(String value) {
        this.permissionSet = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"file"})
    public static class Files {

        @XmlElement(name = "File", required = true)
        protected List<ClrAssemblyFile> file;

        public List<ClrAssemblyFile> getFile() {
            return this.file;
        }

        public void setFile(List<ClrAssemblyFile> file) {
            this.file = file;
        }
    }

}
