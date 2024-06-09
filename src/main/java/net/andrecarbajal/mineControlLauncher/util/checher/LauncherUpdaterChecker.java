package net.andrecarbajal.mineControlLauncher.util.checher;

import net.andrecarbajal.mineControlLauncher.Constants;
import net.andrecarbajal.mineControlLauncher.gui.GuiInstance;
import net.andrecarbajal.mineControlLauncher.util.GithubApi;
import net.andrecarbajal.mineControlLauncher.util.Log;
import net.andrecarbajal.mineControlLauncher.util.error.ErrorHandler;

import java.io.IOException;
import java.nio.file.Path;

public class LauncherUpdaterChecker implements Checker {
    private static final GithubApi githubApi = new GithubApi("MineControlLauncher");
    private static final ErrorHandler errorHandler = new ErrorHandler();

    @Override
    public void check(Path dirPath) throws IOException {
        String latestTag = githubApi.getTag();
        String currentTag = Constants.LAUNCHER_VERSION;

        if (latestTag.equals(currentTag)) {
            Log.println("Launcher is up to date");
            GuiInstance.getInstance().setLogLabel("Launcher is up to date");
        } else if (Float.parseFloat(latestTag) > Float.parseFloat(currentTag)) {
            Log.println("Launcher is outdated");
            GuiInstance.getInstance().setLogLabel("Launcher is outdated");

            errorHandler.displayUpdateErrorAndExit(currentTag, githubApi.getDownloadUrl());
        }
    }
}