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
import java.util.function.Consumer;

import org.sqlite.SQLiteConfig;

public class Util {
    private Util() {
        // constructor
    }

    public static SQLiteConfig transformConfig(SqliteConfig config) throws SQLException {
        SQLiteConfig c = new SQLiteConfig();
        setValueIfNotNull(c::setApplicationId, config.applicationId());
        setValueIfNotNull(c::setBusyTimeout, config.busyTimeout());
        if (config.dateClass() != null) {
            c.setDateClass(config.dateClass().getValue());
        }
        setValueIfNotNull(c::setDateStringFormat, config.dateStringFormat());
        setValueIfNotNull(c::deferForeignKeys, config.deferForeignKeys());
        setValueIfNotNull(c::setDefaultCacheSize, config.defaultCacheSize());
        setValueIfNotNull(c::deferForeignKeys, config.deferForeignKeys());
        setValueIfNotNull(c::setEncoding, config.encoding());
        setValueIfNotNull(c::enableCaseSensitiveLike, config.caseSensitiveLike());
        setValueIfNotNull(c::enableFullSync, config.fullSync());
        setValueIfNotNull(c::enforceForeignKeys, config.enforceForeignKeys());
        setValueIfNotNull(c::setHexKeyMode, config.hexKeyMode());
        setValueIfNotNull(c::incrementalVacuum, config.incrementalVacuum());
        setValueIfNotNull(c::setCacheSize, config.cacheSize());
        setValueIfNotNull(c::setJournalMode, config.journalMode());
        setValueIfNotNull(c::setJournalMode, config.journalMode());
        setValueIfNotNull(c::setJounalSizeLimit, config.jounalSizeLimit());
        setValueIfNotNull(c::useLegacyFileFormat, config.legacyFileFormat());
        setValueIfNotNull(c::setLockingMode, config.lockingMode());
        setValueIfNotNull(c::enableLoadExtension, config.loadExtensionEnabled());
        setValueIfNotNull(c::setMaxPageCount, config.maxPageCount());
        setValueIfNotNull(c::setPageSize, config.pageSize());
        setValueIfNotNull(c::setReadOnly, config.readOnly());
        setValueIfNotNull(c::setReadUncommited, config.readUncommited());
        setValueIfNotNull(c::enableRecursiveTriggers, config.recursiveTriggers());
        setValueIfNotNull(c::enableReverseUnorderedSelects, config.reverseUnorderedSelects());
        setValueIfNotNull(c::setSharedCache, config.sharedCache());
        setValueIfNotNull(c::enableShortColumnNames, config.shortColumnNames());
        setValueIfNotNull(c::setSynchronous, config.synchronous());
        setValueIfNotNull(c::setTempStore, config.tempStore());
        setValueIfNotNull(c::setTempStoreDirectory, config.tempStoreDirectory());
        setValueIfNotNull(c::setTransactionMode, config.transactionMode());
        setValueIfNotNull(c::setUserVersion, config.userVersion());
        if (config.datePrecision() != null) {
            c.setDatePrecision(config.datePrecision().toString());
        }
        return c;
    }

    private static <T> void setValueIfNotNull(Consumer<T> setterMethod, T value){
        if (value != null){
            setterMethod.accept(value);
        }
    }
}
