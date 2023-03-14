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

import org.eclipse.daanse.xmla.api.xmla.Member;
import org.eclipse.daanse.xmla.api.xmla.Role;
import org.eclipse.daanse.xmla.model.record.xmla.MemberR;
import org.eclipse.daanse.xmla.model.record.xmla.RoleR;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.eclipse.daanse.xmla.ws.jakarta.basic.AnnotationConvertor.convertAnnotationList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertToInstant;

public class RoleConvertor {

    public static Role convertRole(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Role role) {
        if (role != null) {
            return new RoleR(role.getName(),
                Optional.ofNullable(role.getID()),
                Optional.ofNullable(convertToInstant(role.getCreatedTimestamp())),
                Optional.ofNullable(convertToInstant(role.getLastSchemaUpdate())),
                Optional.ofNullable(role.getDescription()),
                Optional.ofNullable(convertAnnotationList(role.getAnnotations() == null ? null : role.getAnnotations().getAnnotation())),
                Optional.ofNullable(convertMemberList(role.getMembers() == null ? null : role.getMembers().getMember())));
        }
        return null;
    }

    private static List<Member> convertMemberList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Member> list) {
        if (list != null) {
            return list.stream().map(RoleConvertor::convertMember).collect(Collectors.toList());
        }
        return null;
    }

    private static Member convertMember(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Member member) {
        if (member != null) {
            return new MemberR(Optional.ofNullable(member.getName()),
                Optional.ofNullable(member.getSid()));
        }
        return null;
    }

    public static List<Role> convertRoleList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Role> list) {
        if (list != null) {
            return list.stream().map(RoleConvertor::convertRole).collect(Collectors.toList());
        }
        return null;
    }
}
