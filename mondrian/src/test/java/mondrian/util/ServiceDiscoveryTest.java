/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * History:
 *  This files came from the mondrian project. Some of the Flies
 *  (mostly the Tests) did not have License Header.
 *  But the Project is EPL Header. 2002-2022 Hitachi Vantara.
 *
 * Contributors:
 *   Hitachi Vantara.
 *   SmartCity Jena - initial  Java 8, Junit5
 */
package mondrian.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mondrian.spi.UserDefinedFunction;

/**
 * Created by nbaker on 8/2/16.
 */
class ServiceDiscoveryTest{

  @org.junit.jupiter.api.Test
  void testGetImplementor() throws Exception {
    ServiceDiscovery<UserDefinedFunction> userDefinedFunctionServiceDiscovery =
        ServiceDiscovery.forClass( UserDefinedFunction.class );
    assertNotNull( userDefinedFunctionServiceDiscovery );
    assertTrue( userDefinedFunctionServiceDiscovery.getImplementor().size() > 0 );
  }

}
