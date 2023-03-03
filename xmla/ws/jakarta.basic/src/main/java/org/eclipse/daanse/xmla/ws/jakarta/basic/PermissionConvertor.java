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

import org.eclipse.daanse.xmla.api.xmla.Permission;
import org.eclipse.daanse.xmla.model.record.xmla.PermissionR;

import static org.eclipse.daanse.xmla.ws.jakarta.basic.AnnotationConvertor.convertAnnotationList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertToInstant;

public class PermissionConvertor {
    public static Permission convertPermission(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Permission permission) {
        if (permission != null) {
            return new PermissionR(permission.getName(),
                permission.getID(),
                convertToInstant(permission.getCreatedTimestamp()),
                convertToInstant(permission.getLastSchemaUpdate()),
                permission.getDescription(),
                convertAnnotationList(permission.getAnnotations() == null ? null : permission.getAnnotations().getAnnotation()),
                permission.getRoleID(),
                permission.isProcess(),
                permission.getReadDefinition(),
                permission.getRead());
        }
        return null;
    }
}
