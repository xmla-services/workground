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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatabasePermission", propOrder = { "administer", "write" })
public class DatabasePermission extends Permission {

  @XmlElement(name = "Administer")
  protected Boolean administer;
  @XmlElement(name = "Write")
  protected String write;

  public Boolean isAdminister() {
    return administer;
  }

  public void setAdminister(Boolean value) {
    this.administer = value;
  }

  public String getWrite() {
    return write;
  }

  public void setWrite(String value) {
    this.write = value;
  }

}
