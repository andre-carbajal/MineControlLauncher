package net.andrecarbajal.mineControlLauncher.util.checher;

import net.andrecarbajal.mineControlLauncher.gui.Gui;
import net.andrecarbajal.mineControlLauncher.gui.GuiInstance;
import net.andrecarbajal.mineControlLauncher.util.GithubApi;
import net.andrecarbajal.mineControlLauncher.util.Log;
import net.andrecarbajal.mineControlLauncher.util.download.ApplicationDownloader;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationChecker implements Checker {
    private static final GithubApi githubApi = new GithubApi("MineControlFx");
    private static final Pattern versionPattern = Pattern.compile("MineControlFx-(.*)\\.jar");
    public static final ApplicationDownloader downloader = new ApplicationDownloader();
    private static final Gui guiInstance = GuiInstance.getInstance();

    @Override
    public void check(Path dirPath) throws IOException {
        if (!applicationExists(dirPath)) {
            downloader.download();
        }

        String fileVersion = getFileVersion(dirPath);
        String latestTag = githubApi.getTag();

        if (fileVersion.equals(latestTag)) {
            Log.println("Application is up to date");
        } else if (Float.parseFloat(latestTag) > Float.parseFloat(fileVersion)) {
            Log.println("Application is outdated");
            deleteExistingVersion(dirPath);
            downloader.download();
        }

        Log.println("Application was checked");
    }

    private boolean applicationExists(Path dirPath) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "MineControlFx*")) {
            return stream.iterator().hasNext();
        } catch (IOException e) {
            Log.error("Error while checking for existing application", e);
            throw e;
        }
    }

    private String getFileVersion(Path dirPath) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "MineControlFx*")) {
            for (Path entry : stream) {
                Matcher matcher = versionPattern.matcher(entry.getFileName().toString());
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }
        } catch (IOException e) {
            Log.error("Error while getting file version", e);
            throw e;
        }
        return null;
    }

    private void deleteExistingVersion(Path dirPath) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "MineControlFx*")) {
            for (Path entry : stream) {
                Files.delete(entry);
                guiInstance.setLogLabel("Deleted outdated application: " + entry.getFileName());
                Log.println("Deleted outdated application: " + entry.getFileName());
            }
        } catch (IOException e) {
            Log.error("Error while deleting existing application", e);
            throw e;
        }
    }
}