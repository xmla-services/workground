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
package org.eclipse.daanse.grabber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.osgi.test.common.annotation.InjectService;
import org.osgi.test.junit5.context.BundleContextExtension;
import org.osgi.test.junit5.service.ServiceExtension;

import java.sql.SQLException;

@ExtendWith(BundleContextExtension.class)
@ExtendWith(ServiceExtension.class)
class GrabberServiceImplTest {

    public static final String COMPONENT_NAME = "org.eclipse.daanse.grabber.basic.GrabberServiceFactoryImpl";

    @Test
    void test() throws SQLException {

    }

}
