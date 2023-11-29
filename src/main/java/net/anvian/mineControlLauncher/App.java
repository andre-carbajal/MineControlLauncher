package net.anvian.mineControlLauncher;

import net.anvian.mineControlLauncher.gui.GuiInstance;
import net.anvian.mineControlLauncher.util.Log;
import net.anvian.mineControlLauncher.util.download.Downloader;
import net.anvian.mineControlLauncher.util.download.JavaFxDownloader;

import java.io.IOException;
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
        GuiInstance.getInstance().setLogLabel("The application is about to run");
        runJarWithJavaFx(dirPath + "/MineControlFx.jar", dirPath.resolve(Main.JAVA_FX_FOLDER) + "/lib");
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
            downloader.download();
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

    private static void runJarWithJavaFx(String jarPath, String javaFxPath) {
        try {
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