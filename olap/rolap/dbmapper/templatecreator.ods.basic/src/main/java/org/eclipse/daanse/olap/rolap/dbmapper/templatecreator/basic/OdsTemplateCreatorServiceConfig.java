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
package org.eclipse.daanse.olap.rolap.dbmapper.templatecreator.basic;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.nio.charset.StandardCharsets;

@ObjectClassDefinition
public interface OdsTemplateCreatorServiceConfig {

    /**
     * @return ODS Folder Files Path
     */
    @AttributeDefinition(description = "odsFolderPath")
    default String odsFolderPath() {
        return "/";
    }

    /**
     * @return Encoding default UTF-8
     */
    @AttributeDefinition(description = "encoding")
    default String encoding() {
        return StandardCharsets.UTF_8.name();
    }


    /**
     * @return ODS FileName default test
     */
    @AttributeDefinition(description = "odsFileName")
    default String odsFileName() {
            return "test";
    }

    /**
     * @return ODS File Suffix
     */
    @AttributeDefinition(description = "odsFileSuffix")
    default String odsFileSuffix() {
        return ".ods";
    }

}
