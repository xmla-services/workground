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
@XmlType(name = "Account", propOrder = {

})
public class Account {

    @XmlElement(name = "AccountType", required = true)
    protected String accountType;
    @XmlElement(name = "AggregationFunction")
    protected String aggregationFunction;
    @XmlElement(name = "Aliases")
    protected Account.Aliases aliases;
    @XmlElement(name = "Annotations")
    protected Account.Annotations annotations;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String value) {
        this.accountType = value;
    }

    public String getAggregationFunction() {
        return aggregationFunction;
    }

    public void setAggregationFunction(String value) {
        this.aggregationFunction = value;
    }

    public Account.Aliases getAliases() {
        return aliases;
    }

    public void setAliases(Account.Aliases value) {
        this.aliases = value;
    }

    public Account.Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Account.Annotations value) {
        this.annotations = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"alias"})
    public static class Aliases {

        @XmlElement(name = "Alias")
        protected List<String> alias;

        public List<String> getAlias() {
            return this.alias;
        }

        public void setAlias(List<String> alias) {
            this.alias = alias;
        }
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
