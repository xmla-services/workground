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
@XmlType(name = "Aggregation", propOrder = {

})
public class Aggregation extends AbstractAggregation {

    @XmlElement(name = "Dimensions")
    protected Aggregation.Dimensions dimensions;

    public Aggregation.Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Aggregation.Dimensions value) {
        this.dimensions = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"dimension"})
    public static class Dimensions {

        @XmlElement(name = "Dimension")
        protected List<AggregationDimension> dimension;

        public List<AggregationDimension> getDimension() {
            return this.dimension;
        }

        public void setDimension(List<AggregationDimension> dimension) {
            this.dimension = dimension;
        }
    }

}
