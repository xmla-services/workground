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

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for PerspectiveMeasureGroup complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="PerspectiveMeasureGroup"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="MeasureGroupID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Measures" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Measure" type="{urn:schemas-microsoft-com:xml-analysis}PerspectiveMeasure" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Annotations" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Annotation" type="{urn:schemas-microsoft-com:xml-analysis}Annotation" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PerspectiveMeasureGroup", propOrder = {

})
public class PerspectiveMeasureGroup {

  @XmlElement(name = "MeasureGroupID", required = true)
  protected String measureGroupID;
  @XmlElement(name = "Measures")
  protected PerspectiveMeasureGroup.Measures measures;
  @XmlElement(name = "Annotations")
  protected PerspectiveMeasureGroup.Annotations annotations;

  /**
   * Gets the value of the measureGroupID property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getMeasureGroupID() {
    return measureGroupID;
  }

  /**
   * Sets the value of the measureGroupID property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setMeasureGroupID(String value) {
    this.measureGroupID = value;
  }

  public boolean isSetMeasureGroupID() {
    return (this.measureGroupID != null);
  }

  /**
   * Gets the value of the measures property.
   * 
   * @return possible object is {@link PerspectiveMeasureGroup.Measures }
   * 
   */
  public PerspectiveMeasureGroup.Measures getMeasures() {
    return measures;
  }

  /**
   * Sets the value of the measures property.
   * 
   * @param value allowed object is {@link PerspectiveMeasureGroup.Measures }
   * 
   */
  public void setMeasures(PerspectiveMeasureGroup.Measures value) {
    this.measures = value;
  }

  public boolean isSetMeasures() {
    return (this.measures != null);
  }

  /**
   * Gets the value of the annotations property.
   * 
   * @return possible object is {@link PerspectiveMeasureGroup.Annotations }
   * 
   */
  public PerspectiveMeasureGroup.Annotations getAnnotations() {
    return annotations;
  }

  /**
   * Sets the value of the annotations property.
   * 
   * @param value allowed object is {@link PerspectiveMeasureGroup.Annotations }
   * 
   */
  public void setAnnotations(PerspectiveMeasureGroup.Annotations value) {
    this.annotations = value;
  }

  public boolean isSetAnnotations() {
    return (this.annotations != null);
  }

  /**
   * <p>
   * Java class for anonymous complex type.
   * 
   * <p>
   * The following schema fragment specifies the expected content contained within
   * this class.
   * 
   * <pre>
   * &lt;complexType&gt;
   *   &lt;complexContent&gt;
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
   *       &lt;sequence&gt;
   *         &lt;element name="Annotation" type="{urn:schemas-microsoft-com:xml-analysis}Annotation" maxOccurs="unbounded" minOccurs="0"/&gt;
   *       &lt;/sequence&gt;
   *     &lt;/restriction&gt;
   *   &lt;/complexContent&gt;
   * &lt;/complexType&gt;
   * </pre>
   * 
   * 
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = { "annotation" })
  public static class Annotations {

    @XmlElement(name = "Annotation")
    protected List<Annotation> annotation;

    /**
     * Gets the value of the annotation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot.
     * Therefore any modification you make to the returned list will be present
     * inside the Jakarta XML Binding object. This is why there is not a
     * <CODE>set</CODE> method for the annotation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getAnnotation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Annotation }
     * 
     * 
     */
    public List<Annotation> getAnnotation() {
      if (annotation == null) {
        annotation = new ArrayList<Annotation>();
      }
      return this.annotation;
    }

    public boolean isSetAnnotation() {
      return ((this.annotation != null) && (!this.annotation.isEmpty()));
    }

    public void unsetAnnotation() {
      this.annotation = null;
    }

  }

  /**
   * <p>
   * Java class for anonymous complex type.
   * 
   * <p>
   * The following schema fragment specifies the expected content contained within
   * this class.
   * 
   * <pre>
   * &lt;complexType&gt;
   *   &lt;complexContent&gt;
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
   *       &lt;sequence&gt;
   *         &lt;element name="Measure" type="{urn:schemas-microsoft-com:xml-analysis}PerspectiveMeasure" maxOccurs="unbounded" minOccurs="0"/&gt;
   *       &lt;/sequence&gt;
   *     &lt;/restriction&gt;
   *   &lt;/complexContent&gt;
   * &lt;/complexType&gt;
   * </pre>
   * 
   * 
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = { "measure" })
  public static class Measures {

    @XmlElement(name = "Measure")
    protected List<PerspectiveMeasure> measure;

    /**
     * Gets the value of the measure property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot.
     * Therefore any modification you make to the returned list will be present
     * inside the Jakarta XML Binding object. This is why there is not a
     * <CODE>set</CODE> method for the measure property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getMeasure().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PerspectiveMeasure }
     * 
     * 
     */
    public List<PerspectiveMeasure> getMeasure() {
      if (measure == null) {
        measure = new ArrayList<PerspectiveMeasure>();
      }
      return this.measure;
    }

    public boolean isSetMeasure() {
      return ((this.measure != null) && (!this.measure.isEmpty()));
    }

    public void unsetMeasure() {
      this.measure = null;
    }

  }

}