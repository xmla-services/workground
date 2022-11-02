package org.opencube.junit5.dbprovider;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

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

import mondrian.olap.Util.PropertyList;

public abstract class AbstractDockerBasesDatabaseProvider implements DatabaseProvider{

	private static final String THIS_IS_MONDRIAN_INSTANCE = "thisIsMondrianInstance";
	private DockerClient dc;
	
	
	 protected Optional<String> findContainerForReuse(String param) {
	        Map<String,String>lblFilter=new HashMap<>();
	        lblFilter.put(THIS_IS_MONDRIAN_INSTANCE,param );
	        Optional<String>  id=  dc.listContainersCmd()
	            .withLabelFilter(lblFilter)
	            .withLimit(1)
	            .withStatusFilter(Arrays.asList("running"))
	            .exec()
	            .stream()
	            .filter(Objects::nonNull)
	            .map(it -> it.getId())
	            .findAny();
	         return id;
	    }

	  public Entry<PropertyList, DataSource> activate() {


			DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();

			DockerHttpClient client = new ZerodepDockerHttpClient.Builder().dockerHost(config.getDockerHost())
					.sslConfig(config.getSSLConfig()).maxConnections(100)
					.connectionTimeout(java.time.Duration.ofSeconds(30)).responseTimeout(java.time.Duration.ofSeconds(45))
					.build();
			dc = DockerClientImpl.getInstance(config, client);

		findContainerForReuse("1").ifPresent(id->dc.stopContainerCmd(id).exec());

		if(!findContainerForReuse("1").isPresent()) {

			
	//		findContainerForReuse("1").ifPresent(id->dc.stopContainerCmd(id).exec());

			System.out.println(12);

			try {
				dc.pullImageCmd(image()).exec(new PullImageResultCallback()).awaitCompletion(60, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		


			HostConfig hostConfig = HostConfig.newHostConfig()//
//					.withAutoRemove(true)
					.withShmSize(1024l*1024l*1024l*4l)
					.withNanoCPUs(16l)	
					.withPortBindings(portBinding());

			HashMap<String, String> lbl=new HashMap<>();
			lbl.put(THIS_IS_MONDRIAN_INSTANCE, "1");

			CreateContainerResponse containerResponse = dc.createContainerCmd("mysql-t").withImage(image())//
					.withEnv(env())//
					.withHostConfig(hostConfig)
					.withLabels(lbl)
					.exec();

			String	containerId = containerResponse.getId();


			dc.startContainerCmd(containerId).exec();
		}

			
			return createConnection();
		
		}

	protected abstract SimpleEntry<PropertyList, DataSource> createConnection();

	protected abstract List<String> env();

	protected abstract String image();

	protected abstract PortBinding portBinding();
	  
		@Override
		public void close() {
			try {
		//		findContainerForReuse("1").ifPresent(id->dc.stopContainerCmd(id).exec());

		//		findContainerForReuse("1").ifPresent(id->dc.removeContainerCmd(id).withRemoveVolumes(true).exec());

				dc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	  
}
