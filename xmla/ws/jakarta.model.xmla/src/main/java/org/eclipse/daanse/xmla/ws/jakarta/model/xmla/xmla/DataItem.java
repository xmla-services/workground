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

import java.math.BigInteger;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataItem", propOrder = {

})
public class DataItem {

    @XmlElement(name = "DataType", required = true)
    protected String dataType;
    @XmlElement(name = "DataSize")
    protected BigInteger dataSize;
    @XmlElement(name = "MimeType")
    protected String mimeType;
    @XmlElement(name = "NullProcessing")
    protected String nullProcessing;
    @XmlElement(name = "Trimming")
    protected String trimming;
    @XmlElement(name = "InvalidXmlCharacters")
    protected String invalidXmlCharacters;
    @XmlElement(name = "Collation")
    protected String collation;
    @XmlElement(name = "Format")
    protected String format;
    @XmlElement(name = "Source")
    protected Binding source;
    @XmlElement(name = "Annotations")
    protected DataItem.Annotations annotations;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String value) {
        this.dataType = value;
    }

    public BigInteger getDataSize() {
        return dataSize;
    }

    public void setDataSize(BigInteger value) {
        this.dataSize = value;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String value) {
        this.mimeType = value;
    }

    public String getNullProcessing() {
        return nullProcessing;
    }

    public void setNullProcessing(String value) {
        this.nullProcessing = value;
    }

    public String getTrimming() {
        return trimming;
    }

    public void setTrimming(String value) {
        this.trimming = value;
    }

    public String getInvalidXmlCharacters() {
        return invalidXmlCharacters;
    }

    public void setInvalidXmlCharacters(String value) {
        this.invalidXmlCharacters = value;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String value) {
        this.collation = value;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String value) {
        this.format = value;
    }

    public Binding getSource() {
        return source;
    }

    public void setSource(Binding value) {
        this.source = value;
    }

    public DataItem.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(DataItem.Annotations value) {
        this.annotations = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"annotation"})
    public static class Annotations {

        @XmlElement(name = "Annotation")
        protected List<Annotation> annotation;

        public List<Annotation> getAnnotation() {
            return this.annotation;
        }

        public void setAnnotation(List<Annotation> annotation) {
            this.annotation = annotation;
        }
    }

}
