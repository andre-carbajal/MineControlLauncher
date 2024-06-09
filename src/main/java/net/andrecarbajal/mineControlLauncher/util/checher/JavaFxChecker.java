package net.andrecarbajal.mineControlLauncher.util.checher;

import net.andrecarbajal.mineControlLauncher.Constants;
import net.andrecarbajal.mineControlLauncher.util.Log;
import net.andrecarbajal.mineControlLauncher.util.download.Downloader;
import net.andrecarbajal.mineControlLauncher.util.download.JavaFxDownloader;
import net.andrecarbajal.mineControlLauncher.util.os.OsChecker;

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
            deleteDirectoryRecursively(dirPath.resolve(Constants.JAVA_FX_FOLDER));

        if (!Files.exists(dirPath.resolve(Constants.JAVA_FX_FOLDER)))
            fXdownloader.download();

        Log.println("JavaFxChecker: JavaFx is installed correctly");
    }

    private static boolean checkJavaFxInstalledCorrectly(Path dirPath) {
        Path javaFxPath = Paths.get(String.valueOf(dirPath.resolve(Constants.JAVA_FX_FOLDER)));
        if (OsChecker.getOperatingSystemType() == OsChecker.OSType.Windows) {
            return Files.exists(javaFxPath.resolve("bin")) &&
                    Files.exists(javaFxPath.resolve("legal")) &&
                    Files.exists(javaFxPath.resolve("lib"));
        } else if (OsChecker.getOperatingSystemType() == OsChecker.OSType.MacOS || OsChecker.getOperatingSystemType() == OsChecker.OSType.Linux) {
            return Files.exists(javaFxPath.resolve("legal")) &&
                    Files.exists(javaFxPath.resolve("lib"));
        } else {
            return false;
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
}
