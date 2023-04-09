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
package org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb;

import static org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb.ReadTest.extracted;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.Schema;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBException;

class WrongSchemaTest {

    @Test
    void testRrequiredAatrebutesInWrongSchema() throws Exception {
        try {
            Schema schema = extracted( new File("./src/test/resources/WrongFoodMart.xml"));
            fail("JAXBException expected");
        } catch (JAXBException e) {

        }

    }
}
