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
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for DatabasePermission complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="DatabasePermission"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{urn:schemas-microsoft-com:xml-analysis}Permission"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Administer" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="Write" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="None"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatabasePermission", propOrder = { "administer", "write" })
public class DatabasePermission extends Permission {

  @XmlElement(name = "Administer")
  protected Boolean administer;
  @XmlElement(name = "Write")
  protected String write;

  /**
   * Gets the value of the administer property.
   * 
   * @return possible object is {@link Boolean }
   * 
   */
  public Boolean isAdminister() {
    return administer;
  }

  /**
   * Sets the value of the administer property.
   * 
   * @param value allowed object is {@link Boolean }
   * 
   */
  public void setAdminister(Boolean value) {
    this.administer = value;
  }

  public boolean isSetAdminister() {
    return (this.administer != null);
  }

  /**
   * Gets the value of the write property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getWrite() {
    return write;
  }

  /**
   * Sets the value of the write property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setWrite(String value) {
    this.write = value;
  }

  public boolean isSetWrite() {
    return (this.write != null);
  }

}