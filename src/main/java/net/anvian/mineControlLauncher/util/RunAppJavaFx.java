package net.anvian.mineControlLauncher.util;

import net.anvian.mineControlLauncher.Main;
import net.anvian.mineControlLauncher.gui.GuiInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class RunAppJavaFx {
    public void init(Path dirPath) {
        Optional<Path> mcPackJarFile = findMcPackJarFile(dirPath);
        if (mcPackJarFile.isPresent()) {
            GuiInstance.getInstance().setLogLabel("The application is about to run");
            runJarWithJavaFx(dirPath, mcPackJarFile.get());
        } else {
            Log.error("No .jar file starting with 'McPack' found");
        }
    }

    private static Optional<Path> findMcPackJarFile(Path dirPath) {
        try (Stream<Path> files = Files.walk(dirPath)) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().startsWith("MineControlFx") && file.getFileName().toString().endsWith(".jar"))
                    .findFirst();
        } catch (IOException e) {
            Log.error("An error occurred while searching for the .jar file", e);
            return Optional.empty();
        }
    }

    private static void runJarWithJavaFx(Path dirPath, Path jarFile){
        try {
            String jarPath = jarFile.toString();
            String javaFxPath = dirPath.resolve(Main.JAVA_FX_FOLDER).resolve("lib").toString();

            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "--module-path", javaFxPath,
                    "--add-modules", "ALL-MODULE-PATH",
                    "-jar", jarPath
            );

            Process process = pb.start();

            System.exit(0);
        } catch (IOException e) {
            Log.error("An error occurred while running the application file", e);
        }
    }
}
