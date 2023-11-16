package net.anvian;

import net.anvian.util.JavaFxDownloader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class App {
    public static void init() {
        String userHome = System.getProperty("user.home");
        Path dirPath = Paths.get(userHome, ".MineControl");
        if(!Files.exists(dirPath)){
            try {
                createDirectory(dirPath);
                JavaFxDownloader.downloadJavaFx();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!JavaFxDownloader.checkJavaFxInstalledCorrectly()){
            Path folder = Paths.get(userHome, ".MineControl", "javafx-sdk-17.0.9");
            try {
                deleteDirectoryRecursively(folder);
                JavaFxDownloader.downloadJavaFx();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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