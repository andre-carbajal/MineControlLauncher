package net.anvian;

import net.anvian.util.Log;
import net.anvian.util.download.Downloader;
import net.anvian.util.download.JavaFxDownloader;

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
            createDirectoriesAndDownloadJavaFxIfNecessary(dirPath);
        }
        if (!Files.exists(dirPath.resolve(Main.JAVA_FX_FOLDER)) || !JavaFxDownloader.checkJavaFxInstalledCorrectly()) {
            downloadJavaFxIfNecessary(dirPath);
        }
        runJarWithJavaFx(dirPath + "/MineControlFx.jar", dirPath.resolve(Main.JAVA_FX_FOLDER) + "/lib");
    }

    private static void createDirectoriesAndDownloadJavaFxIfNecessary(Path dirPath) {
        try {
            Files.createDirectories(dirPath);
            downloadJavaFxIfNecessary(dirPath);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while initializing the application", e);
        }
    }

    private static void downloadJavaFxIfNecessary(Path dirPath) {
        try {
            Path folder = dirPath.resolve(Main.JAVA_FX_FOLDER);
            deleteDirectoryRecursively(folder);
            downloader.download();
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while initializing the application", e);
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

    private static void runJarWithJavaFx(String jarPath, String javaFxPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "--module-path", javaFxPath,
                    "--add-modules", "javafx.controls,javafx.fxml",
                    "-jar", jarPath
            );
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            Log.error("An error occurred while running the .jar file", e);
            throw new RuntimeException("An error occurred while running the .jar file", e);
        }
    }
}