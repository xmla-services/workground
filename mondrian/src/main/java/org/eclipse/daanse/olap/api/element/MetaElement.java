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
*   SmartCity Jena - initial
*/
package org.eclipse.daanse.olap.api.element;

import java.util.Map;

public interface MetaElement {
    /**
     * Returns a Map of metadata.
     *
     * @return Map with additional Metadata for the Element.
     */
    Map<String, Object> getMetadata();
}