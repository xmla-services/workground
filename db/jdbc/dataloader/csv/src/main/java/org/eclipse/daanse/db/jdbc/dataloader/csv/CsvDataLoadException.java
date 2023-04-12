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
package org.eclipse.daanse.db.jdbc.dataloader.csv;

import java.sql.SQLException;

public class CsvDataLoadException extends RuntimeException{

    public CsvDataLoadException(String msg, SQLException e) {
        super(msg, e);
    }

    public CsvDataLoadException(String msg) {
        super(msg);
    }
}
