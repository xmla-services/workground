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
package org.eclipse.daanse.db.dialect.db.derby;

import aQute.bnd.annotation.spi.ServiceProvider;
import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectFactory;

import org.eclipse.daanse.db.dialect.db.common.AbstractDialectFactory;
import org.osgi.service.component.annotations.Component;

import java.sql.Connection;
import java.util.Optional;
import java.util.function.Function;

@ServiceProvider(value = DialectFactory.class, attribute = { "database.dialect.type:String='DERBY'",
    "database.product:String='DERBY'" })
@Component(service = DialectFactory.class)
public class DerbyDialectFactory extends AbstractDialectFactory {
    private static final String SUPPORTED_PRODUCT_NAME = "DERBY";

    @Override
    public boolean isSupportedProduct(String productName, String productVersion, Connection connection) {
        return SUPPORTED_PRODUCT_NAME.equalsIgnoreCase(productVersion);
    }

    @Override
    public Function<Connection, DerbyDialect> getConstructorFunction() {
        return DerbyDialect::new;
    }

}
