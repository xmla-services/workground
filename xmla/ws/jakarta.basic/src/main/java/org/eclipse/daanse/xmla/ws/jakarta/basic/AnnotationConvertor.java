package org.eclipse.daanse.xmla.ws.jakarta.basic;

import java.util.List;
import java.util.Optional;

import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.model.record.xmla.AnnotationR;

public class AnnotationConvertor {
	private AnnotationConvertor() {
	}
    public static List<Annotation> convertAnnotationList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Annotation> annotationList) {
        if (annotationList == null) {
        	return List.of();
        }
        return annotationList.stream().map(AnnotationConvertor::convertAnnotation).toList();
    }

    public static Annotation convertAnnotation(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Annotation annotation) {
        return new AnnotationR(annotation.getName(),
            Optional.ofNullable(annotation.getVisibility()),
            Optional.ofNullable(annotation.getValue()));
    }

}
