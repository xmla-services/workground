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
package org.eclipse.daanse.xmla.model.record.xmla;

import org.eclipse.daanse.xmla.api.xmla.Annotation;
import org.eclipse.daanse.xmla.api.xmla.AttributePermission;
import org.eclipse.daanse.xmla.api.xmla.CubeDimensionPermission;

import java.util.List;

public record CubeDimensionPermissionR(String cubeDimensionID,
                                       String description,
                                       String read,
                                       String write,
                                       CubeDimensionPermissionR.AttributePermissions attributePermissions,
                                       CubeDimensionPermissionR.Annotations annotations) implements CubeDimensionPermission {

    public record Annotations(List<Annotation> annotation) implements CubeDimensionPermission.Annotations {

    }

    public record AttributePermissions(
        List<AttributePermission> attributePermission) implements CubeDimensionPermission.AttributePermissions {

    }

}
