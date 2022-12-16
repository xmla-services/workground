package org.eclipse.daanse.olap.rolap.dbmapper.api;

import java.util.List;

public interface Action {

    String name();
    String caption();
    String description();
    List<? extends Annotation> annotations();

}
