package net.anvian.mineControlLauncher.util.checher;

import net.anvian.mineControlLauncher.util.Log;
import net.anvian.mineControlLauncher.util.download.ApplicationDownloader;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ApplicationChecker implements Checker {
    public static final ApplicationDownloader downloader = new ApplicationDownloader();

    @Override
    public void check(Path dirPath) throws IOException {
        if (!applicationExists(dirPath)) {
            downloader.download();
        }

        Log.println("Application was checked");
    }

    private boolean applicationExists(Path dirPath) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "MineControlFx*")) {
            return stream.iterator().hasNext();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
