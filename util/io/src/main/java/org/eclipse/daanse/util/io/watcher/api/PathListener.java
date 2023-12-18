package org.eclipse.daanse.util.io.watcher.api;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.List;

public interface PathListener {

	void handleBasePath(Path basePath);
	void handleInitialPaths(List<Path> paths);

	void handlePathEvent(Path path, WatchEvent.Kind<Path> kind);

}
