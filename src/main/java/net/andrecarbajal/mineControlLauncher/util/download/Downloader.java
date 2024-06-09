package net.andrecarbajal.mineControlLauncher.util.download;

import net.andrecarbajal.mineControlLauncher.gui.GuiInstance;
import net.andrecarbajal.mineControlLauncher.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;

public interface Downloader {
    default void downloadFile(String url, Path filePath, String txt) throws IOException {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        int fileSize = connection.getContentLength();

        try (ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
             FileOutputStream fos = new FileOutputStream(filePath.toFile())) {

            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            int bytesRead;
            int totalBytesRead = 0;
            while ((bytesRead = rbc.read(buffer)) != -1) {
                buffer.flip();
                fos.getChannel().write(buffer);
                buffer.clear();

                totalBytesRead += bytesRead;
                double progress = (double) totalBytesRead / fileSize * 100;
                Log.println(String.format("%s download progress: %.2f %%",txt, progress));
                GuiInstance.getInstance().setLogLabel(String.format("Download progress: %.2f %%", progress));
            }
        } catch (IOException e) {
            Log.error("Error downloading file: " + e);
        }
    }

    void download() throws IOException;
}
