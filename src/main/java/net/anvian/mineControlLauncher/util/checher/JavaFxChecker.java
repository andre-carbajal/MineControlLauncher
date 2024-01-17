package net.anvian.mineControlLauncher.util.checher;

import net.anvian.mineControlLauncher.Main;
import net.anvian.mineControlLauncher.util.Log;
import net.anvian.mineControlLauncher.util.download.Downloader;
import net.anvian.mineControlLauncher.util.download.JavaFxDownloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class JavaFxChecker implements Checker {
    private static final Downloader fXdownloader = new JavaFxDownloader();

    @Override
    public void check(Path dirPath) throws IOException {
        if (!checkJavaFxInstalledCorrectly(dirPath))
            deleteDirectoryRecursively(dirPath.resolve(Main.JAVA_FX_FOLDER));

        if (!Files.exists(dirPath.resolve(Main.JAVA_FX_FOLDER)))
            fXdownloader.download();

        Log.println("JavaFxChecker: JavaFx is installed correctly");
    }

    private static boolean checkJavaFxInstalledCorrectly(Path dirPath) {
        Path javaFxPath = Paths.get(String.valueOf(dirPath.resolve(Main.JAVA_FX_FOLDER)));
        return Files.exists(javaFxPath.resolve("bin")) &&
                Files.exists(javaFxPath.resolve("legal")) &&
                Files.exists(javaFxPath.resolve("lib"));
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
}
