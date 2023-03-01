package org.eclipse.daanse.xmla.model.record.xmla;

import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.Annotations;

import java.util.List;

public record AnnotationsR(List<Annotation> annotation) implements Annotations {

}
