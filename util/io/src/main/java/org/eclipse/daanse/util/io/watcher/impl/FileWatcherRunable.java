package org.eclipse.daanse.util.io.watcher.impl;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.daanse.util.io.watcher.api.EventKind;
import org.eclipse.daanse.util.io.watcher.api.PathListener;
import org.eclipse.daanse.util.io.watcher.api.PathListenerConfig;

public class FileWatcherRunable implements Runnable {

	private WatchService watcher;
	private final Map<WatchKey, Path> watchKeys = new HashMap<>();
	private PathListener listener;
	private boolean stop;

	private PathListenerConfig config;

	private Path observesPath;
	private Kind<?>[] kinds;

	private Optional<Pattern> oPattern;
	private boolean recursive;

	public FileWatcherRunable(PathListener listener, PathListenerConfig config) throws IOException {
		this.listener = listener;
		this.config = config;
		this.recursive = config.pathListener_recursive();
		this.watcher = FileSystems.getDefault().newWatchService();

		FileSystem fs = FileSystems.getDefault();

		String sPattern = config.pathListener_pattern();
		boolean emptyPattern = sPattern == null || sPattern.isBlank();
		oPattern = emptyPattern ? Optional.empty() : Optional.of(Pattern.compile(sPattern));

		EventKind[] eventKinds = config.pathListener_kinds();
		kinds = new WatchEvent.Kind<?>[eventKinds.length];
		
		for (int i = 0; i < eventKinds.length; i++) {
			kinds[i] = eventKinds[i].getKind();
		}

		observesPath = fs.getPath(this.config.pathListener_path());

		if (recursive) {
			registerPathWithSubDirs(observesPath);
		} else {
			registerPath(observesPath);
		}


	}

	private void registerPath(Path path) throws IOException {

		if (config.pathListener_initialFiles()) {
			List<Path> currentPaths = Files.list(path).toList();
			listener.handleInitialPaths(currentPaths);
		}

		WatchKey key = path.register(watcher, kinds);
		synchronized (watchKeys) {
			System.out.println("www "+path);
			watchKeys.put(key, path);
		}

	}

	private void clear() {

		listener = null;
		config = null;
		observesPath = null;
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
			watcher = null;
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
				Path resolvedFile = observesPath.resolve(filename);
				
				
				
				if (recursive && (kind == StandardWatchEventKinds.ENTRY_CREATE)) {
					try {
						if (Files.isDirectory(resolvedFile,LinkOption.NOFOLLOW_LINKS)) {
							
							registerPathWithSubDirs(resolvedFile);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}


				AtomicBoolean matchesPattern = new AtomicBoolean(true);

				oPattern.ifPresent(pattern -> {

					Matcher matcher = pattern.matcher(resolvedFile.toString());
					matchesPattern.set(matcher.matches());

				});

				if (!matchesPattern.get()) {
					continue;
				}

				listener.handlePathEvent(resolvedFile, watchEvent.kind());

			}

			boolean resetValid = key.reset();
			if (!resetValid) {
				break;
			}
		}
		clear();

	}

	private void registerPathWithSubDirs(final Path baseDirectory) throws IOException {
		Files.walkFileTree(baseDirectory, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path currentDirectory, BasicFileAttributes attrs) throws IOException {
				registerPath(currentDirectory);
				return FileVisitResult.CONTINUE;
			}
		});

	}
}