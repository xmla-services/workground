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
package org.eclipse.daanse.tooling.container.impl;

import java.time.Duration;

import org.eclipse.daanse.tooling.container.api.ContainerRuntime;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;

@Component(service = ContainerRuntime.class, scope = ServiceScope.SINGLETON)
public class DockerJavaContainerRuntime implements ContainerRuntime {

    Logger LOGGER = LoggerFactory.getLogger(DockerJavaContainerRuntime.class);

    @Activate
    public void activate() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .build();

        DockerHttpClient client = new ZerodepDockerHttpClient.Builder().dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(5))
                .responseTimeout(Duration.ofSeconds(10))
                .build();
        DockerClient dc = DockerClientImpl.getInstance(config, client);

        LOGGER.debug("try start ping command");
        dc.pingCmd()
                .exec();
        LOGGER.debug("end ping command");
    }
}
