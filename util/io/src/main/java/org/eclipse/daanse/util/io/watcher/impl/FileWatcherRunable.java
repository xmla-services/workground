package org.eclipse.daanse.util.io.watcher.impl;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.daanse.util.io.watcher.api.EventKind;
import org.eclipse.daanse.util.io.watcher.api.PathListener;
import org.eclipse.daanse.util.io.watcher.api.PathListenerConfig;

public class FileWatcherRunable implements Runnable {

	private WatchService watcher;
	private final Map<WatchKey, Path> watchKeys = new HashMap<>();
	private PathListener listener;
	private boolean stop;

	private PathListenerConfig config;

	private List<Path> paths;
	private Kind<?>[] kinds;

	private Optional<Pattern> oPattern;

	public FileWatcherRunable(PathListener listener, PathListenerConfig config) throws IOException {
		this.listener = listener;
		this.config = config;
		this.watcher = FileSystems.getDefault().newWatchService();

		FileSystem fs = FileSystems.getDefault();

		String sPattern = config.pattern();
		oPattern = sPattern.isBlank() ? Optional.empty() : Optional.of(Pattern.compile(sPattern));

		paths = Stream.of(this.config.paths()).map(sPath -> fs.getPath(sPath)).toList();
		EventKind[] eventKinds = config.kind();
		kinds = new WatchEvent.Kind<?>[eventKinds.length];

		for (int i = 0; i < eventKinds.length; i++) {
			kinds[i] = eventKinds[i].getKind();
		}

		init();
	}

	private void init() throws IOException {

		for (Path path : paths) {

			if (config.listInitialFiles()) {
				List<Path> currentPaths = Files.list(path).toList();
				listener.handleInitialPaths(currentPaths);
			}

			WatchKey key = path.register(watcher, kinds);
			synchronized (watchKeys) {
				watchKeys.put(key, path);
			}

		}
	}

	private void clear() {

		listener = null;
		config = null;
		paths = null;
		kinds = null;
		oPattern = null;
	}

	public void shutdown() {
		stop = true;
		synchronized (watchKeys) {
			for (WatchKey key : watchKeys.keySet()) {
				key.cancel();

			}
			watchKeys.clear();
		}
		try {
			watcher.close();
			watcher=null;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while (!stop) {
			WatchKey key = null;
			try {
				key = watcher.poll();
			} catch (ClosedWatchServiceException e) {
				break;
			}

			if (key == null) {
				continue;
			}
			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();
				if (kind == StandardWatchEventKinds.OVERFLOW) {
					continue;// not registerable
				}

				WatchEvent<Path> watchEvent = (WatchEvent<Path>) event;
				Path filename = watchEvent.context();

				AtomicBoolean matchesPattern = new AtomicBoolean(true);

				oPattern.ifPresent(pattern -> {

					Matcher matcher = pattern.matcher(filename.toString());
					matchesPattern.set(matcher.matches());

				});

				if (!matchesPattern.get()) {
					continue;
				}
				// search for filename
				synchronized (watchKeys) {

					Path observatedPath = watchKeys.get(key);
					Path p = observatedPath.resolve(filename);

					listener.handlePathEvent(p, watchEvent.kind());

				}
			}

			boolean resetValid = key.reset();
			if (!resetValid) {
				break;
			}
		}
		clear();

	}
}