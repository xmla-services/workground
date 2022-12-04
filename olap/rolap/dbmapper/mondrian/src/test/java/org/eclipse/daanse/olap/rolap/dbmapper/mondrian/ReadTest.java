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
package org.eclipse.daanse.olap.rolap.dbmapper.mondrian;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Schema;
import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class ReadTest {
    private static final File FILE_STEEL_WHEEL = new File("./src/test/resources/SteelWheels.xml");
    private static final File F_FOOD_MART = new File("./src/test/resources/FoodMart.xml");

    @Test
    void test_SteelWheel() throws Exception {

        Schema schema = extracted(FILE_STEEL_WHEEL);
        assertThat(schema).isNotNull();
    }

    @Test
    void test_Foodmart() throws Exception {

        Schema schema = extracted(F_FOOD_MART);
        assertThat(schema).isNotNull();
    }

    private static Schema extracted(File file) throws JAXBException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SchemaImpl.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (SchemaImpl) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
