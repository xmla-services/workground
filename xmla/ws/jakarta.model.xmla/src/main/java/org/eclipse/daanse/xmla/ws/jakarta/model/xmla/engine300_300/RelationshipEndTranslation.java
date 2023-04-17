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
package org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine300_300;

import java.io.Serializable;
import java.util.List;

import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Annotation;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelationshipEndTranslation", propOrder = {

})
public class RelationshipEndTranslation implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement(name = "Language")
    @XmlSchemaType(name = "unsignedInt")
    protected long language;
    @XmlElement(name = "Caption")
    protected String caption;
    @XmlElement(name = "CollectionCaption", required = true)
    protected String collectionCaption;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "DisplayFolder")
    protected String displayFolder;
    @XmlElement(name = "Annotations")
    protected RelationshipEndTranslation.Annotations annotations;

    public long getLanguage() {
        return language;
    }

    public void setLanguage(long value) {
        this.language = value;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String value) {
        this.caption = value;
    }

    public String getCollectionCaption() {
        return collectionCaption;
    }

    public void setCollectionCaption(String value) {
        this.collectionCaption = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getDisplayFolder() {
        return displayFolder;
    }

    public void setDisplayFolder(String value) {
        this.displayFolder = value;
    }

    public RelationshipEndTranslation.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(RelationshipEndTranslation.Annotations value) {
        this.annotations = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"annotation"})
    public static class Annotations implements Serializable {

        private static final long serialVersionUID = 1L;
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
