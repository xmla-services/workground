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

import org.eclipse.daanse.xmla.api.xmla.Attach;
import org.eclipse.daanse.xmla.api.xmla.Cancel;
import org.eclipse.daanse.xmla.api.xmla.Command;

public record CommandR(
    String statement,
    CreateR create,
    AlterR alter,
    DeleteR delete,
    Process process,
    MergePartitionsR mergePartitions,
    DesignAggregationsR designAggregations,
    ClearCacheR clearCache,
    SubscribeR subscribe,
    UnsubscribeR unsubscribe,
    Cancel cancel,
    BeginTransactionR beginTransaction,
    CommitTransactionR commitTransaction,
    RollbackTransactionR rollbackTransaction,
    LockR lock,
    UnlockR unlock,
    BackupR backup,
    RestoreR restore,
    SynchronizeR synchronize,
    Attach attach,
    DetachR detach,
    InsertR insert,
    UpdateR update,
    DropR drop,
    UpdateCellsR updateCells,
    NotifyTableChangeR notifyTableChange,
    BatchR batch,
    ImageLoadR imageLoad,
    ImageSaveR imageSave,
    CloneDatabaseR cloneDatabase,
    SetAuthContextR setAuthContext,
    DBCCR dbcc) implements Command {

}
