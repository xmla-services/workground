package org.eclipse.daanse.util.io.watcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.osgi.test.common.dictionary.Dictionaries.asDictionary;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.util.Map;

import org.eclipse.daanse.util.io.watcher.api.PathListener;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.annotations.RequireConfigurationAdmin;
import org.osgi.service.component.annotations.RequireServiceComponentRuntime;
import org.osgi.test.common.annotation.InjectBundleContext;
import org.osgi.test.junit5.context.BundleContextExtension;

@RequireServiceComponentRuntime
@RequireConfigurationAdmin

@ExtendWith(BundleContextExtension.class)
@ExtendWith(MockitoExtension.class)
class OSGiServiceTest {

	@InjectBundleContext
	BundleContext bc;

	@TempDir
	Path path;

	@org.junit.jupiter.api.Test
	void testDbMappingSchemaProvider() throws Exception {

		StoringPathListener pathListener = new StoringPathListener();
		System.out.println(path);

		Path f = Files.createTempFile(path, "pre_exist1", ".txt");
		Files.writeString(f, "1");

		Map<String, Object> map = Map.of("pathListener.path", path.toAbsolutePath().toString(),"pathListener.recursive",true );

		bc.registerService(PathListener.class, pathListener, asDictionary(map));

		Thread.sleep(1000);
		assertThat(pathListener.getInitialPaths()).hasSize(1);
		assertThat(pathListener.getInitialPaths().poll()).isEqualTo(f);

		Path f2 = Files.createTempFile(path, "created1", ".txt");// create
		Files.writeString(f2, "2");// modify
		Files.delete(f);// delete
		Files.delete(f2);// delete

		Thread.sleep(100);

		assertThat(pathListener.getEvents()).hasSize(4);
		assertThat(pathListener.getEvents().peek().getKey()).isEqualTo(f2);
		assertThat(pathListener.getEvents().poll().getValue()).isEqualTo(StandardWatchEventKinds.ENTRY_CREATE);

		assertThat(pathListener.getEvents().peek().getKey()).isEqualTo(f2);
		assertThat(pathListener.getEvents().poll().getValue()).isEqualTo(StandardWatchEventKinds.ENTRY_MODIFY);

		assertThat(pathListener.getEvents().peek().getKey()).isEqualTo(f);
		assertThat(pathListener.getEvents().poll().getValue()).isEqualTo(StandardWatchEventKinds.ENTRY_DELETE);

		assertThat(pathListener.getEvents().peek().getKey()).isEqualTo(f2);
		assertThat(pathListener.getEvents().poll().getValue()).isEqualTo(StandardWatchEventKinds.ENTRY_DELETE);

		
		Path dir1 = Files.createDirectory(path.resolve("dir1"));// create dir
		Thread.sleep(1000);
		Path f1InDir1 = Files.createTempFile(dir1, "f1", ".txt");// create
		System.out.println(f1InDir1);
		Thread.sleep(100);

		assertThat(pathListener.getEvents()).hasSize(2);
		assertThat(pathListener.getEvents().peek().getKey()).isEqualTo(dir1);		
		assertThat(pathListener.getEvents().poll().getValue()).isEqualTo(StandardWatchEventKinds.ENTRY_CREATE);
		
		assertThat(pathListener.getEvents().peek().getKey()).isEqualTo(f1InDir1);	
		assertThat(pathListener.getEvents().poll().getValue()).isEqualTo(StandardWatchEventKinds.ENTRY_CREATE);

		Thread.sleep(1000);

	}

}
