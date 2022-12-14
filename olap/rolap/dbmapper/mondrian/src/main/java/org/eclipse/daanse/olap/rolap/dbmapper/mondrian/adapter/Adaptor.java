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
package org.eclipse.daanse.olap.rolap.dbmapper.mondrian.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class Adaptor<T extends Enum<T>> extends XmlAdapter<String,  Enum<T>> {

    private Class<T> _enumClass;

    public Adaptor() {
    }

    protected Adaptor(Class<T> enumClass) {
        _enumClass = enumClass;
    }

    @Override
    public String marshal(Enum<T> e) throws Exception {
        return e.toString();
    }

    @Override
    public Enum<T> unmarshal(String v) {
        return T.valueOf(_enumClass, v);
    }
}
