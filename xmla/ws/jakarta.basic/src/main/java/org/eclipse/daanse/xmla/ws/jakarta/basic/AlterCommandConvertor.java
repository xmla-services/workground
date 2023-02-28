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

import org.eclipse.daanse.xmla.api.engine.ImpersonationInfo;
import org.eclipse.daanse.xmla.api.xmla.Aggregation;
import org.eclipse.daanse.xmla.api.xmla.AggregationAttribute;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesign;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesignAttribute;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesignDimension;
import org.eclipse.daanse.xmla.api.xmla.AggregationDimension;
import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Assembly;
import org.eclipse.daanse.xmla.api.xmla.Cube;
import org.eclipse.daanse.xmla.api.xmla.CubeDimension;
import org.eclipse.daanse.xmla.api.xmla.CubePermission;
import org.eclipse.daanse.xmla.api.xmla.MajorObject;
import org.eclipse.daanse.xmla.api.xmla.ObjectExpansion;
import org.eclipse.daanse.xmla.api.xmla.ObjectReference;
import org.eclipse.daanse.xmla.api.xmla.Scope;
import org.eclipse.daanse.xmla.model.record.engine.ImpersonationInfoR;
import org.eclipse.daanse.xmla.model.record.execute.alter.AlterCommandR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationAttributeR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationDesignAttributeR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationDesignDimensionR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationDesignR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationDimensionR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationR;
import org.eclipse.daanse.xmla.model.record.xmla.AnnotationR;
import org.eclipse.daanse.xmla.model.record.xmla.AssemblyR;
import org.eclipse.daanse.xmla.model.record.xmla.CubeDimensionR;
import org.eclipse.daanse.xmla.model.record.xmla.CubePermissionR;
import org.eclipse.daanse.xmla.model.record.xmla.CubeR;
import org.eclipse.daanse.xmla.model.record.xmla.MajorObjectR;
import org.eclipse.daanse.xmla.model.record.xmla.ObjectReferenceR;
import org.eclipse.daanse.xmla.model.record.xmla.PermissionR;
import org.eclipse.daanse.xmla.model.record.xmla.TranslationR;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Alter;
import org.eclipse.daanse.xmla.api.xmla.Translation;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.eclipse.daanse.xmla.ws.jakarta.basic.AnnotationConvertor.convertAnnotationList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertToInstant;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.CubeConvertor.convertCube;

public class AlterCommandConvertor {

    public static AlterCommandR convertAlterCommand(Alter alter) {
        Optional<ObjectReference> object = convertObjectReference(alter.getObject());
        MajorObject objectDefinition = convertMajorObject(alter.getObjectDefinition());
        Scope scope = convertScope(alter.getScope());
        Boolean allowCreate = alter.isAllowCreate();
        ObjectExpansion objectExpansion = convertObjectExpansion(alter.getObjectExpansion());

        AlterCommandR alterCommand = new AlterCommandR(object, objectDefinition, scope, allowCreate, objectExpansion);
        return alterCommand;
    }

    private static ObjectExpansion convertObjectExpansion(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ObjectExpansion objectExpansion) {
        return ObjectExpansion.fromValue(objectExpansion.value());
    }

    private static Scope convertScope(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Scope scope) {
        return Scope.fromValue(scope.value());
    }

    private static MajorObject convertMajorObject(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MajorObject objectDefinition) {
        AggregationDesign aggregationDesign = convertAggregationDesign(objectDefinition.getAggregationDesign());
        Assembly assembly =  convertAssembly(objectDefinition.getAssembly());
        Cube cube = convertCube(objectDefinition.getCube());
        //TODO
        return new MajorObjectR();
    }


    private static Assembly convertAssembly(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Assembly assembly) {
        if (assembly != null) {
            return new AssemblyR(assembly.getID(),
                assembly.getName(),
                convertToInstant(assembly.getCreatedTimestamp()),
                convertToInstant(assembly.getLastSchemaUpdate()),
                assembly.getDescription(),
                convertAssemblyAnnotations(assembly.getAnnotations()),
                converImpersonationInfo(assembly.getImpersonationInfo()));
        }
        return null;
    }

    private static ImpersonationInfo converImpersonationInfo(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.engine.ImpersonationInfo impersonationInfo) {
        if (impersonationInfo != null) {
            return new ImpersonationInfoR(impersonationInfo.getImpersonationMode(),
                impersonationInfo.getAccount(),
                impersonationInfo.getPassword(),
                impersonationInfo.getImpersonationInfoSecurity());
        }
        return null;
    }

    private static AssemblyR.Annotations convertAssemblyAnnotations(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Assembly.Annotations annotations) {
        if (annotations != null) {
            return new AssemblyR.Annotations(convertAnnotationList(annotations.getAnnotation()));
        }
        return null;
    }

    private static AggregationDesign convertAggregationDesign(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDesign aggregationDesign) {
        if (aggregationDesign != null) {
            AggregationDesign res = new AggregationDesignR(aggregationDesign.getName(),
                aggregationDesign.getID(),
                convertToInstant(aggregationDesign.getCreatedTimestamp()),
                convertToInstant(aggregationDesign.getLastSchemaUpdate()),
                aggregationDesign.getDescription(),
                convertAggregationDesignAnnotations(aggregationDesign.getAnnotations()),
                aggregationDesign.getEstimatedRows(),
                convertAggregationDesignDimensions(aggregationDesign.getDimensions()),
                convertAggregationDesignAggregations(aggregationDesign.getAggregations()),
                aggregationDesign.getEstimatedPerformanceGain());
            return res;
        }
        return null;
    }

    private static AggregationDesign.Dimensions convertAggregationDesignDimensions(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDesign.Dimensions dimensions) {
        if (dimensions != null) {
            return new AggregationDesignR.Dimensions(convertAggregationDesignDimensionList(dimensions.getDimension()));
        }
        return null;
    }

    private static List<AggregationDesignDimension> convertAggregationDesignDimensionList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDesignDimension> dimensionList) {
        if (dimensionList != null) {
            return dimensionList.stream().map(AlterCommandConvertor::convertAggregationDesignDimension).collect(Collectors.toList());
        }
        return null;
    }

    private static AggregationDesignDimension convertAggregationDesignDimension(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDesignDimension aggregationDesignDimension) {
        if (aggregationDesignDimension != null) {
            return new AggregationDesignDimensionR(
                aggregationDesignDimension.getCubeDimensionID(),
                convertAggregationDesignDimensionAttributes(aggregationDesignDimension.getAttributes()),
                convertAggregationDesignDimensionAnnotations(aggregationDesignDimension.getAnnotations()));
        }
        return null;
    }

    private static AggregationDesignDimension.Annotations convertAggregationDesignDimensionAnnotations(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDesignDimension.Annotations annotations) {
        if (annotations != null) {
            return new AggregationDesignDimensionR.AnnotationsR(convertAnnotationList(annotations.getAnnotation()));
        }
        return null;
    }

    private static AggregationDesignDimension.Attributes convertAggregationDesignDimensionAttributes(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDesignDimension.Attributes attributes) {
        if (attributes != null) {
            return new AggregationDesignDimensionR.AttributesR(convertAggregationDesignAttributeList(attributes.getAttribute()));
        }
        return null;
    }

    private static List<AggregationDesignAttribute> convertAggregationDesignAttributeList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDesignAttribute> attributeList) {
        if (attributeList != null) {
            return attributeList.stream().map(AlterCommandConvertor::convertAggregationDesignAttribute).collect(Collectors.toList());
        }
        return null;
    }

    private static AggregationDesignAttribute convertAggregationDesignAttribute(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDesignAttribute aggregationDesignAttribute) {
        if (aggregationDesignAttribute != null) {
            return new AggregationDesignAttributeR(aggregationDesignAttribute.getAttributeID(),
                aggregationDesignAttribute.getEstimatedCount());
        }
        return null;

    }

    private static AggregationDesign.Aggregations convertAggregationDesignAggregations(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDesign.Aggregations aggregations) {
        if (aggregations != null) {
            return new AggregationDesignR.Aggregations(convertAggregationList(aggregations.getAggregation()));
        }
        return null;
    }

    private static List<Aggregation> convertAggregationList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Aggregation> aggregationList) {
        if (aggregationList != null) {
            return aggregationList.stream().map(AlterCommandConvertor::convertAggregation).collect(Collectors.toList());
        }
        return null;
    }

    private static Aggregation convertAggregation(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Aggregation aggregation) {
        if (aggregation != null) {
            return new AggregationR(aggregation.getID(),
                aggregation.getName(),
                convertAggregationDimensions(aggregation.getDimensions()),
                convertAggregationAnnotations(aggregation.getAnnotations()),
                aggregation.getDescription());
        }
        return null;
    }

    private static AggregationR.Annotations convertAggregationAnnotations(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Aggregation.Annotations annotations) {
        if (annotations != null) {
            return new AggregationR.Annotations(convertAnnotationList(annotations.getAnnotation()));
        }
        return null;
    }

    private static AggregationR.Dimensions convertAggregationDimensions(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Aggregation.Dimensions dimensions) {
        if (dimensions != null) {
            return new AggregationR.Dimensions(convertAggregationDimensionList(dimensions.getDimension()));
        }
        return null;
    }

    private static List<AggregationDimension> convertAggregationDimensionList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDimension> dimensionList) {
        if (dimensionList != null) {
            return dimensionList.stream().map(AlterCommandConvertor::convertAggregationDimension).collect(Collectors.toList());
        }
        return null;
    }

    private static AggregationDimension convertAggregationDimension(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDimension aggregationDimension) {
        if (aggregationDimension != null) {
            return new AggregationDimensionR(aggregationDimension.getCubeDimensionID(),
                convertAggregationDimensionAttributes(aggregationDimension.getAttributes()),
                convertAggregationDimensionAnnotations(aggregationDimension.getAnnotations()));
        }
        return null;
    }

    private static AggregationDimension.Annotations convertAggregationDimensionAnnotations(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDimension.Annotations annotations) {
        if (annotations != null) {
            new AggregationDimensionR.AnnotationsR(convertAnnotationList(annotations.getAnnotation()));
        }
        return null;

    }

    private static AggregationDimension.Attributes convertAggregationDimensionAttributes(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDimension.Attributes attributes) {
        if (attributes != null) {
            new AggregationDimensionR.AttributesR(convertAggregationAttributeList(attributes.getAttribute()));
        }
        return null;
    }

    private static List<AggregationAttribute> convertAggregationAttributeList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationAttribute> attributeList) {
        if (attributeList != null) {
            return attributeList.stream().map(AlterCommandConvertor::convertAggregationAttribute).collect(Collectors.toList());
        }
        return null;
    }

    private static AggregationAttribute convertAggregationAttribute(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationAttribute aggregationAttribute) {
        if (aggregationAttribute != null) {
            return new AggregationAttributeR(
                aggregationAttribute.getAttributeID(),
                convertAggregationAttributeAnnotations(aggregationAttribute.getAnnotations()));
        }
        return null;
    }

    private static AggregationAttribute.Annotations convertAggregationAttributeAnnotations(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationAttribute.Annotations annotations) {
        if (annotations != null) {
            return new AggregationAttributeR.AnnotationsR(convertAnnotationList(annotations.getAnnotation()));
        }
        return null;
    }

    private static AggregationDesign.Annotations convertAggregationDesignAnnotations(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDesign.Annotations annotations) {
        if (annotations != null) {
            return new AggregationDesignR.Annotations(convertAnnotationList(annotations.getAnnotation()));
        }
        return null;
    }


    private static Optional<ObjectReference> convertObjectReference(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ObjectReference object) {
        return Optional.of(new ObjectReferenceR(
            object.getServerID(),
            object.getDatabaseID(),
            object.getRoleID(),
            object.getTraceID(),
            object.getAssemblyID(),
            object.getDimensionID(),
            object.getDimensionPermissionID(),
            object.getDataSourceID(),
            object.getDataSourcePermissionID(),
            object.getDatabasePermissionID(),
            object.getDataSourceViewID(),
            object.getCubeID(),
            object.getMiningStructureID(),
            object.getMeasureGroupID(),
            object.getPerspectiveID(),
            object.getCubePermissionID(),
            object.getMdxScriptID(),
            object.getPartitionID(),
            object.getAggregationDesignID(),
            object.getMiningModelID(),
            object.getMiningModelPermissionID(),
            object.getMiningStructurePermissionID()
        ));
    }
}
