package net.anvian.mineControlLauncher.util.download;

import net.anvian.mineControlLauncher.Main;
import net.anvian.mineControlLauncher.util.GithubApi;
import net.anvian.mineControlLauncher.util.Log;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApplicationDownloader implements Downloader{
    private static final GithubApi githubApi = new GithubApi("andre-carbajal", "McPackGenerator");

    @Override
    public void download() throws IOException {
        Path dirPath = Paths.get(Main.USER_HOME, Main.MAIN_FOLDER);
        downloadFile(githubApi.getDownloadUrl(), dirPath.resolve(githubApi.getFileName()), "Application");
        Log.println("Application downloaded successfully");
    }
}
