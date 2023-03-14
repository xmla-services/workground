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
import org.eclipse.daanse.xmla.api.xmla.ReadDefinitionEnum;
import org.eclipse.daanse.xmla.api.xmla.ReadWritePermissionEnum;
import org.eclipse.daanse.xmla.model.record.xmla.PermissionR;

import java.util.Optional;

import static org.eclipse.daanse.xmla.ws.jakarta.basic.AnnotationConvertor.convertAnnotationList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertToInstant;

public class PermissionConvertor {
    public static Permission convertPermission(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Permission permission) {
        if (permission != null) {
            return new PermissionR(permission.getName(),
                Optional.ofNullable(permission.getID()),
                Optional.ofNullable(convertToInstant(permission.getCreatedTimestamp())),
                Optional.ofNullable(convertToInstant(permission.getLastSchemaUpdate())),
                Optional.ofNullable(permission.getDescription()),
                Optional.ofNullable(convertAnnotationList(permission.getAnnotations() == null ? null : permission.getAnnotations().getAnnotation())),
                permission.getRoleID(),
                Optional.ofNullable(permission.isProcess()),
                Optional.ofNullable(ReadDefinitionEnum.fromValue(permission.getReadDefinition())),
                Optional.ofNullable(ReadWritePermissionEnum.fromValue(permission.getRead())),
                Optional.ofNullable(ReadWritePermissionEnum.fromValue(permission.getWrite())));
        }
        return null;
    }
}
