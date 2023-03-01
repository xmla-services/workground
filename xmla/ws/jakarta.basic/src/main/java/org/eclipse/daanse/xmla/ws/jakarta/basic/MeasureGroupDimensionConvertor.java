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
package org.eclipse.daanse.xmla.ws.jakarta.basic;

import org.eclipse.daanse.xmla.api.xmla.MeasureGroupAttribute;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroupDimension;
import org.eclipse.daanse.xmla.api.xmla.MeasureGroupDimensionBinding;
import org.eclipse.daanse.xmla.model.record.xmla.DataMiningMeasureGroupDimensionR;
import org.eclipse.daanse.xmla.model.record.xmla.DegenerateMeasureGroupDimensionR;
import org.eclipse.daanse.xmla.model.record.xmla.ManyToManyMeasureGroupDimensionR;
import org.eclipse.daanse.xmla.model.record.xmla.MeasureGroupAttributeR;
import org.eclipse.daanse.xmla.model.record.xmla.MeasureGroupDimensionBindingR;
import org.eclipse.daanse.xmla.model.record.xmla.ReferenceMeasureGroupDimensionR;
import org.eclipse.daanse.xmla.model.record.xmla.RegularMeasureGroupDimensionR;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DataMiningMeasureGroupDimension;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.DegenerateMeasureGroupDimension;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ManyToManyMeasureGroupDimension;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.RegularMeasureGroupDimension;

import java.util.List;
import java.util.stream.Collectors;

import static org.eclipse.daanse.xmla.ws.jakarta.basic.AnnotationConvertor.convertAnnotations;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.DataItemConvertor.convertDataItemList;

public class MeasureGroupDimensionConvertor {

    public static MeasureGroupDimension convertMeasureGroupDimension(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MeasureGroupDimension measureGroupDimension) {
        if (measureGroupDimension != null) {
            if (measureGroupDimension instanceof ManyToManyMeasureGroupDimension) {
                ManyToManyMeasureGroupDimension manyToManyMeasureGroupDimension =
                    (ManyToManyMeasureGroupDimension) measureGroupDimension;
                return new ManyToManyMeasureGroupDimensionR(manyToManyMeasureGroupDimension.getCubeDimensionID(),
                    convertAnnotations(manyToManyMeasureGroupDimension.getAnnotations() == null ? null :
                        manyToManyMeasureGroupDimension.getAnnotations().getAnnotation()),
                    convertMeasureGroupDimensionBinding(manyToManyMeasureGroupDimension.getSource()),
                    manyToManyMeasureGroupDimension.getMeasureGroupID(),
                    manyToManyMeasureGroupDimension.getDirectSlice());
            }
            if (measureGroupDimension instanceof RegularMeasureGroupDimension) {
                RegularMeasureGroupDimension regularMeasureGroupDimension =
                    (RegularMeasureGroupDimension) measureGroupDimension;
                return new RegularMeasureGroupDimensionR(regularMeasureGroupDimension.getCubeDimensionID(),
                    convertAnnotations(regularMeasureGroupDimension.getAnnotations().getAnnotation()),
                    convertMeasureGroupDimensionBinding(regularMeasureGroupDimension.getSource()),
                    regularMeasureGroupDimension.getCardinality(),
                    convertRegularMeasureGroupDimensionAttributes(regularMeasureGroupDimension.getAttributes()));
            }
            if (measureGroupDimension instanceof org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ReferenceMeasureGroupDimension) {
                org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ReferenceMeasureGroupDimension referenceMeasureGroupDimension =
                    (org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ReferenceMeasureGroupDimension) measureGroupDimension;
                return new ReferenceMeasureGroupDimensionR(referenceMeasureGroupDimension.getCubeDimensionID(),
                    convertAnnotations(referenceMeasureGroupDimension.getAnnotations() == null ? null :
                        referenceMeasureGroupDimension.getAnnotations().getAnnotation()),
                    convertMeasureGroupDimensionBinding(referenceMeasureGroupDimension.getSource()),
                    referenceMeasureGroupDimension.getIntermediateCubeDimensionID(),
                    referenceMeasureGroupDimension.getIntermediateGranularityAttributeID(),
                    referenceMeasureGroupDimension.getMaterialization(),
                    referenceMeasureGroupDimension.getProcessingState());
            }
            if (measureGroupDimension instanceof DegenerateMeasureGroupDimension) {
                DegenerateMeasureGroupDimension degenerateMeasureGroupDimension =
                    (DegenerateMeasureGroupDimension) measureGroupDimension;
                return new DegenerateMeasureGroupDimensionR(degenerateMeasureGroupDimension.getCubeDimensionID(),
                    convertAnnotations(degenerateMeasureGroupDimension.getAnnotations() == null
                        ? null : degenerateMeasureGroupDimension.getAnnotations().getAnnotation()),
                    convertMeasureGroupDimensionBinding(degenerateMeasureGroupDimension.getSource()),
                    degenerateMeasureGroupDimension.getShareDimensionStorage());
            }
            if (measureGroupDimension instanceof DataMiningMeasureGroupDimension) {
                DataMiningMeasureGroupDimension dataMiningMeasureGroupDimension =
                    (DataMiningMeasureGroupDimension) measureGroupDimension;
                return new DataMiningMeasureGroupDimensionR(dataMiningMeasureGroupDimension.getCubeDimensionID(),
                    convertAnnotations(dataMiningMeasureGroupDimension.getAnnotations() == null ? null :
                        dataMiningMeasureGroupDimension.getAnnotations().getAnnotation()),
                    convertMeasureGroupDimensionBinding(dataMiningMeasureGroupDimension.getSource()),
                    dataMiningMeasureGroupDimension.getCaseCubeDimensionID());
            }
        }
        return null;
    }

    private static org.eclipse.daanse.xmla.api.xmla.RegularMeasureGroupDimension.Attributes convertRegularMeasureGroupDimensionAttributes(
        org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.RegularMeasureGroupDimension.Attributes attributes
    ) {
        if (attributes != null) {
            return new RegularMeasureGroupDimensionR.AttributesR(convertMeasureGroupAttributeList(attributes.getAttribute()));
        }
        return null;
    }

    private static List<MeasureGroupAttribute> convertMeasureGroupAttributeList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MeasureGroupAttribute> attributeList) {
        if (attributeList != null) {
            return attributeList.stream().map(MeasureGroupDimensionConvertor::convertMeasureGroupAttribute).collect(Collectors.toList());
        }
        return null;
    }

    private static MeasureGroupAttribute convertMeasureGroupAttribute(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MeasureGroupAttribute measureGroupAttribute) {
        if (measureGroupAttribute != null) {
            return new MeasureGroupAttributeR(measureGroupAttribute.getAttributeID(),
                convertMeasureGroupAttributeKeyColumns(measureGroupAttribute.getKeyColumns()),
                measureGroupAttribute.getType(),
                convertAnnotations(measureGroupAttribute.getAnnotations() == null ? null :
                    measureGroupAttribute.getAnnotations().getAnnotation()));
        }
        return null;
    }

    private static MeasureGroupAttribute.KeyColumns convertMeasureGroupAttributeKeyColumns(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MeasureGroupAttribute.KeyColumns keyColumns) {
        if (keyColumns != null) {
            return new MeasureGroupAttributeR.KeyColumns(convertDataItemList(keyColumns.getKeyColumn()));
        }
        return null;
    }

    private static MeasureGroupDimensionBinding convertMeasureGroupDimensionBinding(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MeasureGroupDimensionBinding source) {
        if (source != null) {
            return new MeasureGroupDimensionBindingR(source.getCubeDimensionID());
        }
        return null;
    }

}
