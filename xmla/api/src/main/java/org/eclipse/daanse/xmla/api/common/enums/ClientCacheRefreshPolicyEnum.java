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
package org.eclipse.daanse.xmla.api.common.enums;

/**
 * A hint to the client applications about when
 * their data caches, if any, SHOULD<180> be
 * refreshed after a Refresh command changes
 * the data on the server.
 */
public enum ClientCacheRefreshPolicyEnum {

    /**
     * Client applications are notified to
     * refresh their caches only if a user
     * query/interaction needs newer data.
     */
    refresh_newer_data(0),

    /**
     * (default) – Client applications are
     * notified to allow all background cache
     * refreshes.
     */
    all_cache_refresh(1);

    private final int value;

    ClientCacheRefreshPolicyEnum(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public static ClientCacheRefreshPolicyEnum fromValue(String v) {
        if (v == null) {
            return all_cache_refresh;
        }
        int vi = Integer.valueOf(v);
        for (ClientCacheRefreshPolicyEnum c : ClientCacheRefreshPolicyEnum.values()) {
            if (c.value == vi) {
                return c;
            }
        }
        throw new IllegalArgumentException(new StringBuilder("InstanceSelectionEnum Illegal argument ")
            .append(v).toString());
    }
}
