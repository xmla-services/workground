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
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.db.datasource.sqlite;

import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

public class Util {

    private Util() {
        // constructor
    }

    public static SQLiteConfig transformConfig(SqliteConfig config) throws SQLException {
        SQLiteConfig c = new SQLiteConfig();

        if (config.applicationId() != null) {
            c.setApplicationId(config.applicationId());
        }
        if (config.busyTimeout() != null) {
            c.setBusyTimeout(config.busyTimeout());
        }
        if (config.dateClass() != null) {
            c.setDateClass(config.dateClass().getValue());
        }
        if (config.dateStringFormat() != null) {
            c.setDateStringFormat(config.dateStringFormat());
        }
        if (config.deferForeignKeys() != null) {
            c.deferForeignKeys(config.deferForeignKeys());
        }
        if (config.defaultCacheSize() != null) {
            c.setDefaultCacheSize(config.defaultCacheSize());
        }
        if (config.deferForeignKeys() != null) {
            c.deferForeignKeys(config.deferForeignKeys());
        }
        if (config.encoding() != null) {
            c.setEncoding(config.encoding());
        }
        if (config.caseSensitiveLike() != null) {
            c.enableCaseSensitiveLike(config.caseSensitiveLike());
        }
        if (config.fullSync() != null) {
            c.enableFullSync(config.fullSync());
        }
        if (config.enforceForeignKeys() != null) {
            c.enforceForeignKeys(config.enforceForeignKeys());
        }
        if (config.hexKeyMode() != null) {
            c.setHexKeyMode(config.hexKeyMode());
        }
        if (config.incrementalVacuum() != null) {
            c.incrementalVacuum(config.incrementalVacuum());
        }
        if (config.cacheSize() != null) {
            c.setCacheSize(config.cacheSize());
        }
        if (config.journalMode() != null) {
            c.setJournalMode(config.journalMode());
        }
        if (config.jounalSizeLimit() != null) {
            c.setJounalSizeLimit(config.jounalSizeLimit());
        }
        if (config.legacyFileFormat() != null) {
            c.useLegacyFileFormat(config.legacyFileFormat());
        }
        if (config.lockingMode() != null) {
            c.setLockingMode(config.lockingMode());
        }
        if (config.loadExtensionEnabled() != null) {
            c.enableLoadExtension(config.loadExtensionEnabled());
        }
        if (config.maxPageCount() != null) {
            c.setMaxPageCount(config.maxPageCount());
        }
        if (config.pageSize() != null) {
            c.setPageSize(config.pageSize());
        }
        if (config.readOnly() != null) {
            c.setReadOnly(config.readOnly());
        }
        if (config.readUncommited() != null) {
            c.setReadUncommited(config.readUncommited());
        }
        if (config.recursiveTriggers() != null) {
            c.enableRecursiveTriggers(config.recursiveTriggers());
        }
        if (config.reverseUnorderedSelects() != null) {
            c.enableReverseUnorderedSelects(config.reverseUnorderedSelects());
        }
        if (config.sharedCache() != null) {
            c.setSharedCache(config.sharedCache());
        }
        if (config.shortColumnNames() != null) {
            c.enableShortColumnNames(config.shortColumnNames());
        }
        if (config.synchronous() != null) {
            c.setSynchronous(config.synchronous());
        }
        if (config.tempStore() != null) {
            c.setTempStore(config.tempStore());
        }
        if (config.tempStoreDirectory() != null) {
            c.setTempStoreDirectory(config.tempStoreDirectory());
        }
        if (config.transactionMode() != null) {
            c.setTransactionMode(config.transactionMode());
        }
        if (config.userVersion() != null) {
            c.setUserVersion(config.userVersion());
        }

        if (config.datePrecision() == null) {
            c.setDatePrecision(config.datePrecision().toString());
        }

        return c;
    }
}
