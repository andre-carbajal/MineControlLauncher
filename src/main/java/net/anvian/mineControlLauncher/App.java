package net.anvian.mineControlLauncher;

import net.anvian.mineControlLauncher.gui.GuiInstance;
import net.anvian.mineControlLauncher.util.Log;
import net.anvian.mineControlLauncher.util.download.ApplicationDownloader;
import net.anvian.mineControlLauncher.util.download.Downloader;
import net.anvian.mineControlLauncher.util.download.JavaFxDownloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class App {
    private static final Downloader fXdownloader = new JavaFxDownloader();
    private static final Downloader aplicationDownloader = new ApplicationDownloader();
    private static final String JAR_FILE = "McPackGenerator-2.2.jar";

    public static void init() {
        Path dirPath = Paths.get(Main.USER_HOME, Main.MAIN_FOLDER);
        if (!Files.exists(dirPath)) {
            createDirectoriesAndDownloadJavaFxIfNecessary(dirPath);
        }
        if (!Files.exists(dirPath.resolve(Main.JAVA_FX_FOLDER)) || !JavaFxDownloader.checkJavaFxInstalledCorrectly()) {
            downloadJavaFxIfNecessary(dirPath);
        }
        try {
            GuiInstance.getInstance().setLogLabel("Init Aplication download");
            aplicationDownloader.download();
        } catch (IOException e) {
            Log.error("Error", e);
            throw new RuntimeException(e);
        }
        GuiInstance.getInstance().setLogLabel("The application is about to run");
        runJarWithJavaFx(dirPath);
    }

    private static void createDirectoriesAndDownloadJavaFxIfNecessary(Path dirPath) {
        try {
            Files.createDirectories(dirPath);
            downloadJavaFxIfNecessary(dirPath);
        } catch (IOException e) {
            Log.error("An error occurred while initializing the application", e);
        }
    }

    private static void downloadJavaFxIfNecessary(Path dirPath) {
        try {
            Path folder = dirPath.resolve(Main.JAVA_FX_FOLDER);
            deleteDirectoryRecursively(folder);
            fXdownloader.download();
        } catch (IOException e) {
            Log.error("An error occurred while downloading JavaFX", e);
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
                                Log.error("An error occurred while deleting the directory", e);
                            }
                        });
            }
        } else {
            Files.deleteIfExists(path);
        }
    }

    private static void runJarWithJavaFx(Path dirPath) {
        try {
            String jarPath = dirPath.resolve(JAR_FILE).toString();
            String javaFxPath = dirPath.resolve(Main.JAVA_FX_FOLDER).resolve("/lib").toString();

            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "--module-path", javaFxPath,
                    "--add-modules", "javafx.controls,javafx.fxml",
                    "-jar", jarPath
            );

            Process process = pb.start();

            System.exit(0);
        } catch (IOException e) {
            Log.error("An error occurred while running the .jar file", e);
        }
    }
}