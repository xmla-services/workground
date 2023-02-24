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
package org.eclipse.daanse.xmla.api.xmla;

public interface Command {


  String statement();

  Create create();

  Alter alter();

  Delete delete();

  Process process();

  MergePartitions mergePartitions();

  DesignAggregations designAggregations();

  ClearCache clearCache();

  Subscribe subscribe();

  Unsubscribe unsubscribe();

  Cancel cancel();

  BeginTransaction beginTransaction();

  CommitTransaction commitTransaction();

  RollbackTransaction rollbackTransaction();

  Lock lock();

  Unlock unlock();

  Backup backup();

  Restore restore();

  Synchronize synchronize();

  Attach attach();

  Detach detach();

  Insert insert();

  Update update();

  Drop drop();

  UpdateCells updateCells();

  NotifyTableChange notifyTableChange();

  Batch batch();

  ImageLoad imageLoad();

  ImageSave imageSave();

  CloneDatabase cloneDatabase();

  SetAuthContext setAuthContext();

  DBCC dbcc();

}
