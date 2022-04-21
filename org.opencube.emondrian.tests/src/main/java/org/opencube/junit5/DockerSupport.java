package org.opencube.junit5;

import java.time.Duration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;

public class DockerSupport {

	public static void available() throws RuntimeException{
		DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
		DockerHttpClient client = new ZerodepDockerHttpClient.Builder().dockerHost(config.getDockerHost())
				.sslConfig(config.getSSLConfig()).maxConnections(100).connectionTimeout(Duration.ofSeconds(30))
				.responseTimeout(Duration.ofSeconds(45)).build();
		DockerClient dc = DockerClientImpl.getInstance(config, client);
		dc.pingCmd().exec();

	}
}
