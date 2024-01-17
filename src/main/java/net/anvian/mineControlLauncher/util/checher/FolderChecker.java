package net.anvian.mineControlLauncher.util.checher;

import net.anvian.mineControlLauncher.util.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FolderChecker implements Checker {
    @Override
    public void check(Path dirPath) throws IOException {
        if (!Files.exists(dirPath))
            Files.createDirectories(dirPath);
        Log.println("FolderChecker: " + dirPath + " is exist");
    }
}
