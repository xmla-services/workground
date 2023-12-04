package org.eclipse.daanse.util.io.watcher.api;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

public enum EventKind {
	/**
	 * @see StandardWatchEventKinds.ENTRY_CREATE
	 */
	ENTRY_CREATE(StandardWatchEventKinds.ENTRY_CREATE),
	/**
	 * @see StandardWatchEventKinds.ENTRY_MODIFY
	 */
	ENTRY_MODIFY(StandardWatchEventKinds.ENTRY_MODIFY),
	/**
	 * @see StandardWatchEventKinds.ENTRY_DELETE
	 */
	ENTRY_DELETE(StandardWatchEventKinds.ENTRY_DELETE);

	private final WatchEvent.Kind<Path> kind;

	EventKind(WatchEvent.Kind<Path> kind) {
		this.kind = kind;

	}

	public WatchEvent.Kind<Path> getKind() {
		return kind;
	}

}
