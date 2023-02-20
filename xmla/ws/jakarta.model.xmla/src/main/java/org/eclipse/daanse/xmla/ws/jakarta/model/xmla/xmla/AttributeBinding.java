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

import java.math.BigInteger;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributeBinding", propOrder = { "attributeID", "type", "ordinal" })
public class AttributeBinding extends Binding {

  @XmlElement(name = "AttributeID", required = true)
  protected String attributeID;
  @XmlElement(name = "Type", required = true)
  protected String type;
  @XmlElement(name = "Ordinal")
  protected BigInteger ordinal;

  public String getAttributeID() {
    return attributeID;
  }

  public void setAttributeID(String value) {
    this.attributeID = value;
  }

  public String getType() {
    return type;
  }

  public void setType(String value) {
    this.type = value;
  }

  public BigInteger getOrdinal() {
    return ordinal;
  }

  public void setOrdinal(BigInteger value) {
    this.ordinal = value;
  }

}
