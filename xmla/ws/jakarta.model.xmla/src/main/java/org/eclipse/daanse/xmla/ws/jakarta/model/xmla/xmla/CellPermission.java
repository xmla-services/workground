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
@XmlType(name = "CellPermission", propOrder = {

})
public class CellPermission {

    @XmlElement(name = "Access", required = true)
    protected String access;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Expression")
    protected String expression;
    @XmlElement(name = "Annotations")
    protected CellPermission.Annotations annotations;

    public String getAccess() {
        return access;
    }

    public void setAccess(String value) {
        this.access = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String value) {
        this.expression = value;
    }

    public CellPermission.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(CellPermission.Annotations value) {
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
