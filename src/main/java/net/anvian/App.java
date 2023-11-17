package net.anvian;

import net.anvian.util.download.Downloader;
import net.anvian.util.download.JavaFxDownloader;
import net.anvian.util.Log;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class App {

    private static final Downloader downloader = new JavaFxDownloader();

    public static void init() {
        Path dirPath = Paths.get(Main.USER_HOME, Main.MAIN_FOLDER);
        if (!Files.exists(dirPath)) {
            try {
                createDirectory(dirPath);
                downloadJavaFxIfNecessary(dirPath);
            } catch (IOException e) {
                Log.error("An error occurred while initializing the application", e);
                throw new RuntimeException("An error occurred while initializing the application", e);
            }
        }
    }

    private static void downloadJavaFxIfNecessary(Path dirPath) throws IOException {
        if (!JavaFxDownloader.checkJavaFxInstalledCorrectly()) {
            Path folder = dirPath.resolve(Main.JAVA_FX_FOLDER);
            deleteDirectoryRecursively(folder);
            downloader.download();
        }
    }

    private static void createDirectory(Path dirPath) throws IOException {
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
    }

    private static void deleteDirectoryRecursively(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.walk(path)) {
                entries.sorted(Comparator.reverseOrder())
                        .forEach(p -> {
                            try {
                                Files.deleteIfExists(p);
                            } catch (IOException e) {
                                throw new UncheckedIOException(e);
                            }
                        });
            }
        } else {
            Files.deleteIfExists(path);
        }
    }
}