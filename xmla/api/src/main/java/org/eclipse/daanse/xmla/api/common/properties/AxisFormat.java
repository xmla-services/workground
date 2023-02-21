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
package org.eclipse.daanse.xmla.api.common.properties;

public enum AxisFormat {

    /**
     * The MDDataSet axis is made up of one or more CrossProduct
     * elements.
     */
    TupleFormat,

    /**
     * Analysis Services uses the TupleFormat format for this setting.
     */
    ClusterFormat,

    /**
     * The MDDataSet axis contains one or more Tuple elements.
     */
    CustomFormat;

}
