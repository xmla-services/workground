package org.eclipse.daanse.xmla.ws.jakarta.basic;

import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.model.record.xmla.AnnotationR;

import java.util.List;
import java.util.stream.Collectors;

public class AnnotationConvertor {

    public static List<Annotation> convertAnnotationList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Annotation> annotationList) {
        if (annotationList != null) {
            return annotationList.stream().map(AnnotationConvertor::convertAnnotation).collect(Collectors.toList());
        }
        return null;
    }

    public static Annotation convertAnnotation(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Annotation annotation) {
        return new AnnotationR(annotation.getName(),
            annotation.getVisibility(),
            annotation.getValue());
    }

}
