package org.eclipse.daanse.util.io.watcher.api;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.List;

public interface PathListener {

	void handleInitialPaths(List<Path> path);

	void handlePathEvent(Path path, WatchEvent.Kind<Path> kind);
}
