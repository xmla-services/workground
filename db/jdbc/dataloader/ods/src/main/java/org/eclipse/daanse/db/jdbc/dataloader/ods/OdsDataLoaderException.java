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

public class OdsDataLoaderException extends RuntimeException{

    public OdsDataLoaderException(String msg, Exception e) {
        super(msg, e);
    }

    public OdsDataLoaderException(String msg) {
        super(msg);
    }
}
