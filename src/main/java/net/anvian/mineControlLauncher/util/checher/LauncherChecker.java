package net.anvian.mineControlLauncher.util;

import net.anvian.mineControlLauncher.Main;
import net.anvian.mineControlLauncher.gui.GuiInstance;
import net.anvian.mineControlLauncher.util.error.ErrorHandler;

import java.io.IOException;

public class LauncherChecker implements Checker{
    private static final GithubApi githubApi = new GithubApi("MineControlLauncher");
    private static final ErrorHandler errorHandler = new ErrorHandler();

    public void checkUpdate() throws IOException {
        String latestTag = githubApi.getTag();
        String currentTag = Main.LAUNCHER_VERSION;

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