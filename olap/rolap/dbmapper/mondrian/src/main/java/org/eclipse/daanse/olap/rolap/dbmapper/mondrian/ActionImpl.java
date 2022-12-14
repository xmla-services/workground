/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.mondrian;

import jakarta.xml.bind.annotation.*;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Action;
import org.eclipse.daanse.olap.rolap.dbmapper.api.Annotation;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Action", propOrder = { "annotations" })
public class ActionImpl implements Action {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "name")
    protected String caption;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlElement(name = "Annotations")
    protected List<AnnotationImpl> annotations;

    @Override
    public String name() {
        return name;
    }

    @Override
    public String caption() {
        return caption;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public List<? extends Annotation> annotations() {
        if (annotations == null) {
            annotations = new ArrayList<AnnotationImpl>();
        }
        return this.annotations;
    }
}
