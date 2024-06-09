package net.andrecarbajal.mineControlLauncher.util.download;

import net.andrecarbajal.mineControlLauncher.Constants;
import net.andrecarbajal.mineControlLauncher.util.GithubApi;
import net.andrecarbajal.mineControlLauncher.util.Log;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApplicationDownloader implements Downloader {
    private static final GithubApi githubApi = new GithubApi("MineControlFx");

    @Override
    public void download() throws IOException {
        Path dirPath = Paths.get(Constants.USER_HOME, Constants.MAIN_FOLDER);
        downloadFile(githubApi.getDownloadUrl(), dirPath.resolve(githubApi.getFileName()), "Application");
        Log.println("Application downloaded successfully");
    }
}
