package net.andrecarbajal.mineControlLauncher.util.download;

import net.andrecarbajal.mineControlLauncher.Constants;
import net.andrecarbajal.mineControlLauncher.gui.GuiInstance;
import net.andrecarbajal.mineControlLauncher.util.Log;
import net.andrecarbajal.mineControlLauncher.util.os.OsChecker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class JavaFxDownloader implements Downloader {
    @Override
    public void download() throws IOException {
        String url = selectUrl();
        Path dirPath = Paths.get(Constants.USER_HOME, Constants.MAIN_FOLDER);

        Path filePath = dirPath.resolve(Constants.JAVA_FX_FOLDER + ".zip");
        downloadFile(url, filePath, "JavaFx");
        unzipFile(filePath, dirPath);
        Files.delete(filePath);
    }

    private static String selectUrl() {
        String url;
        switch (OsChecker.getOperatingSystemType()) {
            case Windows:
                url = "https://download2.gluonhq.com/openjfx/17.0.9/openjfx-17.0.9_windows-x64_bin-sdk.zip";
                GuiInstance.getInstance().setLogLabel("Downloading JavaFX for Windows");
                break;
            case MacOS:
                if (OsChecker.getArchitectureType() == OsChecker.ArchType.x86_64) {
                    url = "https://download2.gluonhq.com/openjfx/17.0.9/openjfx-17.0.9_osx-x64_bin-sdk.zip";
                    GuiInstance.getInstance().setLogLabel("Downloading JavaFX for MacOS x86_64");
                } else if (OsChecker.getArchitectureType() == OsChecker.ArchType.aarch64) {
                    url = "https://download2.gluonhq.com/openjfx/17.0.9/openjfx-17.0.9_osx-aarch64_bin-sdk.zip";
                    GuiInstance.getInstance().setLogLabel("Downloading JavaFX for MacOS aarch64");
                } else {
                    Log.error("Unsupported architecture");
                    GuiInstance.getInstance().setLogLabel("Your architecture is not supported");
                    throw new UnsupportedOperationException("Unsupported architecture");
                }
                break;
            case Linux:
                url = "https://download2.gluonhq.com/openjfx/17.0.9/openjfx-17.0.9_linux-x64_bin-sdk.zip";
                GuiInstance.getInstance().setLogLabel("Downloading JavaFX for Linux");
                break;
            default:
                Log.error("Unsupported operating system");
                GuiInstance.getInstance().setLogLabel("Your operating system is not supported");
                throw new UnsupportedOperationException("Unsupported operating system");
        }
        return url;
    }

    private static void unzipFile(Path filePath, Path dirPath) throws IOException {
        Log.println("Unzipping JavaFX");
        GuiInstance.getInstance().setLogLabel("Unzipping JavaFX");
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(filePath.toFile()))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = new File(dirPath.toFile(), zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        Log.error("Failed to create directory " + newFile);
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        Log.error("Failed to create directory " + parent);
                        throw new IOException("Failed to create directory " + parent);
                    }
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        byte[] buffer = new byte[1024];
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
    }
}