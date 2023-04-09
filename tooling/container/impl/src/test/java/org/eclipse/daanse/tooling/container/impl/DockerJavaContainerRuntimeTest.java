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

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.eclipse.daanse.tooling.container.api.ContainerRuntime;
import org.junit.jupiter.api.Test;
import org.osgi.test.common.annotation.InjectService;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;

class DockerJavaContainerRuntimeTest {

    @Test
    void testName(@InjectService(service = ContainerRuntime.class) ContainerRuntime containerRuntime) throws Exception {

        Assertions.assertThat(containerRuntime)
                .isNotNull()
                .isInstanceOf(DockerJavaContainerRuntime.class);
    }

    @Test
    void testRun() throws Exception {

        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .build();

        DockerHttpClient client = new ZerodepDockerHttpClient.Builder().dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(java.time.Duration.ofSeconds(30))
                .responseTimeout(java.time.Duration.ofSeconds(45))
                .build();
        DockerClient dc = DockerClientImpl.getInstance(config, client);

        System.out.println(12);

        try {
            dc.pullImageCmd("mysql:latest")
                    .exec(new PullImageResultCallback())
                    .awaitCompletion(5 * 60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        HostConfig hostConfig = HostConfig.newHostConfig()//
                .withShmSize(1024l * 1024l * 1024l * 4l)
                .withNanoCPUs(16l)
                .withAutoRemove(true)
                .withPortBindings(portBinding());

        CreateContainerResponse containerResponse = dc.createContainerCmd("mysql-t")
                .withImage("mysql:latest")//
                .withEnv(env())//
                .withHostConfig(hostConfig)
                .exec();

        String containerId = containerResponse.getId();

        dc.startContainerCmd(containerId)
        .exec();

        dc.stopContainerCmd(containerId)
        .exec();

    }

    public static String MYSQL_ROOT_PASSWORD = "the.root.pw";
    public static String MYSQL_DATABASE = "the.db";
    public static String MYSQL_USER = "the.user";
    public static String MYSQL_PASSWORD = "the.pw";
    public static int PORT_MYSQL = 3306;

    public static String serverName = "0.0.0.0";

    private PortBinding portBinding() throws IllegalArgumentException, IOException {
        return PortBinding.parse(freePort() + ":" + PORT_MYSQL);
    }

    private List<String> env() {
        ArrayList<String> envs = new ArrayList<>();
        envs.add("MYSQL_ROOT_PASSWORD=" + MYSQL_ROOT_PASSWORD);
        envs.add("MYSQL_USER=" + MYSQL_USER);
        envs.add("MYSQL_PASSWORD=" + MYSQL_PASSWORD);
        envs.add("MYSQL_DATABASE=" + MYSQL_DATABASE);

        return envs;
    }

    private int freePort() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {

            return serverSocket.getLocalPort();
        } catch (IOException e) {
            throw e;
        }
    }
}
