package net.anvian;

import net.anvian.util.JavaFxDownloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void init() {
        String userHome = System.getProperty("user.home");
        Path dirPath = Paths.get(userHome, ".MineControl");
        if(!Files.exists(dirPath)){
            try {
                JavaFxDownloader.downloadJavaFx();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}