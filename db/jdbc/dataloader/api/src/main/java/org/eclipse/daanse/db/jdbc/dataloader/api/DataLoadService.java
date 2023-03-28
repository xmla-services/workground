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
package org.eclipse.daanse.db.jdbc.dataloader.api;

import org.eclipse.daanse.db.jdbc.util.impl.Table;
import javax.sql.DataSource;
import java.nio.file.Path;
import java.util.List;

public interface DataLoadService {

    void loadData(DataSource dataSource, List<Table> tables);
}
