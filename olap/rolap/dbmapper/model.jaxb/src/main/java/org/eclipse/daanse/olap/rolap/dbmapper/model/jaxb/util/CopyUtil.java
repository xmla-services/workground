package org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.util;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Annotation;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.AnnotationImpl;
import org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.SchemaImpl;

public class CopyUtil {
    public static SchemaImpl copy(Schema schemaApi) {
        SchemaImpl schemaImpl = new SchemaImpl();
        schemaImpl.setAnnotations(copy(schemaApi.annotations()));
        schemaImpl.setDefaultRole(schemaApi.defaultRole());
        return schemaImpl;

    }

    private static List<AnnotationImpl> copy(List<? extends Annotation> annotations) {

        // Please no null checks in Lits. getList must provide minimal a empty List.
        return annotations.stream()
                .map(CopyUtil::copy)
                .toList();

    }

    private static AnnotationImpl copy(Annotation annotationApi) {
        AnnotationImpl impl = new AnnotationImpl();
        impl.setContent(annotationApi.content());
        impl.setName(annotationApi.name());
        return impl;
    }
}