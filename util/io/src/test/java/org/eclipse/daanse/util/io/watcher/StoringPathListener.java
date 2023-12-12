package org.eclipse.daanse.util.io.watcher;

import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

import org.eclipse.daanse.util.io.watcher.api.PathListener;

public class StoringPathListener implements PathListener {

	public StoringPathListener() {
		clear();
	}

	private Queue<Path> initialPaths;
	private Queue<Entry<Path, Kind<Path>>> events;

	void clear() {
		initialPaths = new ArrayDeque<>();
		events = new ArrayDeque<>();
	}

	@Override
	public void handleInitialPaths(List<Path> initialPaths) {
	this.initialPaths.addAll(initialPaths);

	}

	@Override
	public void handlePathEvent(Path path, Kind<Path> kind) {
		events.add(new AbstractMap.SimpleEntry<>(path, kind));
	}

	public Queue<Path> getInitialPaths() {
		return initialPaths;

	}

	public Queue<Entry<Path, Kind<Path>>> getEvents() {
		return events;
	}

	@Override
	public void handleBasePath(Path basePath) {
		
	}

}
