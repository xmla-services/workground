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

import static org.eclipse.daanse.xmla.ws.jakarta.basic.AnnotationConvertor.convertAnnotationList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.ConvertorUtil.convertToInstant;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.DatabaseConvertor.convertAssemblyList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.DatabaseConvertor.convertDatabaseList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.RoleConvertor.convertRoleList;
import static org.eclipse.daanse.xmla.ws.jakarta.basic.TraceConvertor.convertTraceList;

import java.util.List;

import org.eclipse.daanse.xmla.api.xmla.Server;
import org.eclipse.daanse.xmla.api.xmla.ServerProperty;
import org.eclipse.daanse.xmla.model.record.xmla.ServerPropertyR;
import org.eclipse.daanse.xmla.model.record.xmla.ServerR;

public class ServerConvertor {

	private ServerConvertor() {
	}
    public static Server convertServer(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.Server server) {
        if (server != null) {
            return new ServerR(server.getName(),
                server.getID(),
                convertToInstant(server.getCreatedTimestamp()),
                convertToInstant(server.getLastSchemaUpdate()),
                server.getDescription(),
                convertAnnotationList(server.getAnnotations() == null ? null : server.getAnnotations().getAnnotation()),
                server.getProductName(),
                server.getEdition(),
                server.getEditionID(),
                server.getVersion(),
                server.getServerMode(),
                server.getProductLevel(),
                server.getDefaultCompatibilityLevel(),
                server.getSupportedCompatibilityLevels(),
                convertDatabaseList(server.getDatabases() == null ? null : server.getDatabases().getDatabase()),
                convertAssemblyList(server.getAssemblies() == null ? null : server.getAssemblies().getAssembly()),
                convertTraceList(server.getTraces() == null ? null : server.getTraces().getTrace()),
                convertRoleList(server.getRoles() == null ? null : server.getRoles().getRole()),
                convertServerPropertyList(server.getServerProperties() == null ? null : server.getServerProperties().getServerProperty()));
        }
        return null;
    }

    private static List<ServerProperty> convertServerPropertyList(List<org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ServerProperty> list) {
        if (list != null) {
            return list.stream().map(ServerConvertor::convertServerProperty).toList();
        }
        return List.of();

    }

    private static ServerProperty convertServerProperty(org.eclipse.daanse.xmla.ws.jakarta.model.xmla.xmla.ServerProperty serverProperty) {
        if (serverProperty != null) {
            return new ServerPropertyR(serverProperty.getName(),
                serverProperty.getValue(),
                serverProperty.isRequiresRestart(),
                serverProperty.getPendingValue(),
                serverProperty.getDefaultValue(),
                serverProperty.isDisplayFlag(),
                serverProperty.getType());
        }
        return null;
    }

}
