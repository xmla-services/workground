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
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla;

import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_rowset.Rowset;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "_return" })
@XmlRootElement(name = "DiscoverResponse")
public class DiscoverResponse {

    @XmlElement(name = "return")
    protected DiscoverResponse.Return _return;

    public DiscoverResponse.Return getReturn() {
        return _return;
    }

    public void setReturn(DiscoverResponse.Return value) {
        this._return = value;
    }

    public boolean isSetReturn() {
        return (this._return != null);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "root" })
    public static class Return {

        @XmlElement(namespace = "urn:schemas-microsoft-com:xml-analysis:rowset")
        protected Rowset root;

        public Rowset getRoot() {
            return root;
        }

        public void setRoot(Rowset value) {
            this.root = value;
        }

        public boolean isSetRoot() {
            return (this.root != null);
        }

    }

}
