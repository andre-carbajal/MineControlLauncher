package net.anvian.mineControlLauncher.util;

import net.anvian.mineControlLauncher.Constants;
import net.anvian.mineControlLauncher.gui.GuiInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class RunAppWithJavaFx {
    public void init(Path dirPath) throws IOException {
        Optional<Path> mcPackJarFile = findMcPackJarFile(dirPath);
        if (mcPackJarFile.isPresent()) {
            GuiInstance.getInstance().setLogLabel("The application is about to run");
            runJarWithJavaFx(dirPath, mcPackJarFile.get());
        } else {
            Log.error("No .jar file starting with 'MineControlFx' found");
        }
    }

    private Optional<Path> findMcPackJarFile(Path dirPath) throws IOException {
        try (Stream<Path> files = Files.walk(dirPath)) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().startsWith("MineControlFx") && file.getFileName().toString().endsWith(".jar"))
                    .findFirst();
        }
    }

    private void runJarWithJavaFx(Path dirPath, Path jarFile) throws IOException {
        String javaPath = System.getProperty("java.home") + "/bin/java";
        String javaFxPath = dirPath.resolve(Constants.JAVA_FX_FOLDER).resolve("lib").toString();
        String jarPath = jarFile.toString();

        ProcessBuilder pb = new ProcessBuilder(
                javaPath,
                "--module-path", javaFxPath,
                "--add-modules", "ALL-MODULE-PATH",
                "-jar", jarPath
        );

        Process process = pb.start();

        System.exit(0);
    }
}
