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
package org.eclipse.daanse.db.jdbc.dataloader.ods;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.nio.charset.StandardCharsets;

@ObjectClassDefinition
public interface OdsDataLoadServiceConfig {

    /**
     * @return CSV Folder Files Path
     */
    @AttributeDefinition(description = "odsFolderPath")
    default String odsFolderPath() {
        return "/";
    }

    /**
     * @return CSV File Suffix
     */
    @AttributeDefinition(description = "odsFileSuffix")
    default String odsFileSuffix() {
        return ".ods";
    }

    /**
     * @return CSV File Prefix
     */
    @AttributeDefinition(description = "odsFilePrefix")
    default String odsFilePrefix() {
        return "";
    }

    /**
     * @return Encoding default UTF-8
     */
    @AttributeDefinition(description = "encoding")
    default String encoding() {
        return StandardCharsets.UTF_8.name();
    }

    /**
     * @return Clear Table Before Load Data
     */
    @AttributeDefinition(description = "clearTableBeforeLoad")
    default Boolean clearTableBeforeLoad() {
        return true;
    }

    /**
     * @return Batch Size. Use Batch operation if dialect support it
     */
    @AttributeDefinition(description = "batchSize")
    default int batchSize() {
        return 1000;
    }

    /**
     * @return Encoding default UTF-8
     */
    @AttributeDefinition(description = "encoding")
    default String odsFileName() {
            return "test";
    }

}
