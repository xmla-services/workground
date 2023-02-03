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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "schema", "returnValue" })
@XmlRootElement(name = "DiscoverResponse")
public class DiscoverResponse {
	
	@XmlElement(name = "schema", required = false, namespace = "http://www.w3.org/2001/XMLSchema")
	protected org.w3._2001.xmlschema.Schema schema;

    @XmlElement(name = "return", required = true, namespace = "")
    protected Return returnValue;

    public Return getReturn() {
        return returnValue;
    }

    public void setReturn(Return value) {
        this.returnValue = value;
    }
    
    public void setSchema(org.w3._2001.xmlschema.Schema schema) {
    	this.schema = schema;
	}
    

}
