package org.eclipse.daanse.xmla.ws.jakarta.basic;

import org.eclipse.daanse.xmla.api.engine.ImpersonationInfo;
import org.eclipse.daanse.xmla.api.xmla.Aggregation;
import org.eclipse.daanse.xmla.api.xmla.AggregationAttribute;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesign;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesignAttribute;
import org.eclipse.daanse.xmla.api.xmla.AggregationDesignDimension;
import org.eclipse.daanse.xmla.api.xmla.AggregationDimension;
import org.eclipse.daanse.xmla.api.xmla.Assembly;
import org.eclipse.daanse.xmla.api.xmla.Cube;
import org.eclipse.daanse.xmla.api.xmla.MajorObject;
import org.eclipse.daanse.xmla.model.record.engine.ImpersonationInfoR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationAttributeR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationDesignAttributeR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationDesignDimensionR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationDesignR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationDimensionR;
import org.eclipse.daanse.xmla.model.record.xmla.AggregationR;
import org.eclipse.daanse.xmla.model.record.xmla.AssemblyR;
import org.eclipse.daanse.xmla.model.record.xmla.MajorObjectR;

import java.util.List;
import java.util.stream.Collectors;

import static org.eclipse.daanse.xmla.ws.jakarta.basic.AnnotationConvertor.convertAnnotationList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.AnnotationConvertor.convertAnnotations;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertToInstant;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.CubeConvertor.convertCube;

public class MajorObjectConvertor {
    public static MajorObject convertMajorObject(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.MajorObject objectDefinition) {
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
                convertAnnotations(assembly.getAnnotations() == null ? null : assembly.getAnnotations().getAnnotation()),
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

    private static AggregationDesign convertAggregationDesign(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDesign aggregationDesign) {
        if (aggregationDesign != null) {
            AggregationDesign res = new AggregationDesignR(aggregationDesign.getName(),
                aggregationDesign.getID(),
                convertToInstant(aggregationDesign.getCreatedTimestamp()),
                convertToInstant(aggregationDesign.getLastSchemaUpdate()),
                aggregationDesign.getDescription(),
                convertAnnotations(aggregationDesign.getAnnotations() == null ? null : aggregationDesign.getAnnotations().getAnnotation()),
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
            return dimensionList.stream().map(MajorObjectConvertor::convertAggregationDesignDimension).collect(Collectors.toList());
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
            return attributeList.stream().map(MajorObjectConvertor::convertAggregationDesignAttribute).collect(Collectors.toList());
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
            return aggregationList.stream().map(MajorObjectConvertor::convertAggregation).collect(Collectors.toList());
        }
        return null;
    }

    private static Aggregation convertAggregation(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Aggregation aggregation) {
        if (aggregation != null) {
            return new AggregationR(aggregation.getID(),
                aggregation.getName(),
                convertAggregationDimensions(aggregation.getDimensions()),
                convertAnnotations(aggregation.getAnnotations() == null ? null : aggregation.getAnnotations().getAnnotation()),
                aggregation.getDescription());
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
            return dimensionList.stream().map(MajorObjectConvertor::convertAggregationDimension).collect(Collectors.toList());
        }
        return null;
    }

    private static AggregationDimension convertAggregationDimension(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationDimension aggregationDimension) {
        if (aggregationDimension != null) {
            return new AggregationDimensionR(aggregationDimension.getCubeDimensionID(),
                convertAggregationDimensionAttributes(aggregationDimension.getAttributes()),
                convertAnnotations(aggregationDimension.getAnnotations() == null ? null : aggregationDimension.getAnnotations().getAnnotation()));
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
            return attributeList.stream().map(MajorObjectConvertor::convertAggregationAttribute).collect(Collectors.toList());
        }
        return null;
    }

    private static AggregationAttribute convertAggregationAttribute(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.AggregationAttribute aggregationAttribute) {
        if (aggregationAttribute != null) {
            return new AggregationAttributeR(
                aggregationAttribute.getAttributeID(),
                convertAnnotations(aggregationAttribute.getAnnotations() == null ? null : aggregationAttribute.getAnnotations().getAnnotation()));
        }
        return null;
    }
}
