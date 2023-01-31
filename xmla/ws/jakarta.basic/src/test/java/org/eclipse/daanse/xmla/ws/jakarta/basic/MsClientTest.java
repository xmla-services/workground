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
package org.eclipse.daanse.xmla.ws.jakarta.basic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.Discover;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.DiscoverResponse;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.DiscoverResponse.Return;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.Properties;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla.Restrictions;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_rowset.DiscoverPropertiesResponseRowXml;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_rowset.Row;
import org.eclipse.daanse.xmla.ws.jakarta.model.xmla_rowset.Rowset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.osgi.framework.BundleContext;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.common.annotation.InjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aQute.bnd.annotation.service.ServiceCapability;

@ServiceCapability(WsAdapter.class)
//@EnabledOnOs(OS.WINDOWS)
@Disabled
public class MsClientTest {
    private Logger logger = LoggerFactory.getLogger(MsClientTest.class);

    @InjectBundleContext
    BundleContext bc;

    private ArgumentCaptor<Discover> dicoverCaptor;

    @BeforeEach
    void beforaEach() {
        WsAdapter xmlaService = mock(WsAdapter.class);
        dicoverCaptor = ArgumentCaptor.forClass(Discover.class);
        bc.registerService(WsAdapter.class, xmlaService, new Hashtable<>());
    }

    @Test
    void testRequest_1(@InjectService WsAdapter xmlaService) throws Exception {

        Thread.sleep(6000);
        // prepare response
        DiscoverResponse discoverResponse = new DiscoverResponse();

        Return r = new Return();
        Rowset rs = new Rowset();
        Row row = new DiscoverPropertiesResponseRowXml();

        rs.getRow()
                .add(row);

        r.setRoot(rs);
        discoverResponse.setReturn(r);

        when(xmlaService.discover(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(discoverResponse);

        // call test

        Process process = callByMsClient("schema", "MDSCHEMA_CUBES");

        byte[] errors = process.getErrorStream()
                .readAllBytes();
        byte[] info = process.getInputStream()
                .readAllBytes();
        process.waitFor(1000, TimeUnit.SECONDS);

        System.out.println(new String(info));

        System.out.println(new String(errors));
        logger.info(new String(info));
        logger.error(new String(errors));

        Assertions.assertThat(errors)
                .isEmpty();
        Assertions.assertThat(process.exitValue())
                .isEqualTo(0);

        // verify request
        verify(xmlaService, (Mockito.atLeastOnce())).discover(dicoverCaptor.capture(), Mockito.any(), Mockito.any(),
                Mockito.any());

        var discoverAssert = assertThat(dicoverCaptor.getAllValues()
                .get(0));

        discoverAssert.extracting(Discover::getRequestType)
                .isNotNull()
                .isEqualTo("DISCOVER_PROPERTIES");

        discoverAssert.extracting(Discover::getRestrictions)
                .isNotNull()
                .extracting(Restrictions::getRestrictionList)
                .isNull();

        discoverAssert.extracting(Discover::getProperties)
                .isNotNull()
                .extracting(Properties::getPropertyList)
                .isNotNull()
                .satisfies(pl -> pl.getLocaleIdentifier()
                        .equals(new BigInteger("1033")));

//		verify client 

    }

    private Process callByMsClient(String... values) throws IOException {
        String dotNet = System.getProperty("user.home") + "/.dotnet/dotnet";

        final var cmds = new ArrayList<String>();
        cmds.add(dotNet);
        cmds.add("run");
        cmds.add("Data source=http://localhost:8081/xmla;UID=Domain\\User;PWD=UserDomainPassword");

        Stream.of(values)
                .forEach(v -> cmds.add(v));

        System.out.println(cmds);

        ProcessBuilder processBuilder = new ProcessBuilder(cmds);
        processBuilder.inheritIO();
        processBuilder.directory(Paths.get("../../../../../../MsAdomdClientTester/")
                .toAbsolutePath()
                .toFile());

        Process p = processBuilder.start();

        return p;

    }

}
