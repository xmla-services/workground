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
package org.eclipse.daanse.xmla.model.record.xmla;

import org.eclipse.daanse.xmla.api.xmla.Alter;
import org.eclipse.daanse.xmla.api.xmla.Attach;
import org.eclipse.daanse.xmla.api.xmla.Backup;
import org.eclipse.daanse.xmla.api.xmla.Batch;
import org.eclipse.daanse.xmla.api.xmla.Process;
import org.eclipse.daanse.xmla.api.xmla.BeginTransaction;
import org.eclipse.daanse.xmla.api.xmla.Cancel;
import org.eclipse.daanse.xmla.api.xmla.ClearCache;
import org.eclipse.daanse.xmla.api.xmla.CloneDatabase;
import org.eclipse.daanse.xmla.api.xmla.Command;
import org.eclipse.daanse.xmla.api.xmla.CommitTransaction;
import org.eclipse.daanse.xmla.api.xmla.Create;
import org.eclipse.daanse.xmla.api.xmla.DBCC;
import org.eclipse.daanse.xmla.api.xmla.Delete;
import org.eclipse.daanse.xmla.api.xmla.DesignAggregations;
import org.eclipse.daanse.xmla.api.xmla.Detach;
import org.eclipse.daanse.xmla.api.xmla.Drop;
import org.eclipse.daanse.xmla.api.xmla.ImageLoad;
import org.eclipse.daanse.xmla.api.xmla.ImageSave;
import org.eclipse.daanse.xmla.api.xmla.Insert;
import org.eclipse.daanse.xmla.api.xmla.Lock;
import org.eclipse.daanse.xmla.api.xmla.MergePartitions;
import org.eclipse.daanse.xmla.api.xmla.NotifyTableChange;
import org.eclipse.daanse.xmla.api.xmla.Restore;
import org.eclipse.daanse.xmla.api.xmla.RollbackTransaction;
import org.eclipse.daanse.xmla.api.xmla.SetAuthContext;
import org.eclipse.daanse.xmla.api.xmla.Subscribe;
import org.eclipse.daanse.xmla.api.xmla.Synchronize;
import org.eclipse.daanse.xmla.api.xmla.Unlock;
import org.eclipse.daanse.xmla.api.xmla.Unsubscribe;
import org.eclipse.daanse.xmla.api.xmla.Update;
import org.eclipse.daanse.xmla.api.xmla.UpdateCells;

public record CommandR(
    String statement,
    Create create,
    Alter alter,
    Delete delete,
    Process process,
    MergePartitions mergePartitions,
    DesignAggregations designAggregations,
    ClearCache clearCache,
    Subscribe subscribe,
    Unsubscribe unsubscribe,
    Cancel cancel,
    BeginTransaction beginTransaction,
    CommitTransaction commitTransaction,
    RollbackTransaction rollbackTransaction,
    Lock lock,
    Unlock unlock,
    Backup backup,
    Restore restore,
    Synchronize synchronize,
    Attach attach,
    Detach detach,
    Insert insert,
    Update update,
    Drop drop,
    UpdateCells updateCells,
    NotifyTableChange notifyTableChange,
    Batch batch,
    ImageLoad imageLoad,
    ImageSave imageSave,
    CloneDatabase cloneDatabase,
    SetAuthContext setAuthContext,
    DBCC dbcc) implements Command {

}
