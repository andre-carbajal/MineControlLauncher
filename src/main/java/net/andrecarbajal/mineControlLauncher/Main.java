package net.andrecarbajal.mineControlLauncher;

import net.andrecarbajal.mineControlLauncher.gui.GuiInstance;
import net.andrecarbajal.mineControlLauncher.util.error.ErrorHandler;
import net.andrecarbajal.mineControlLauncher.util.os.JavaVersion;

import java.io.IOException;

public class Main {
    private static final ErrorHandler errorHandler = new ErrorHandler();

    public static void main(String[] args) throws IOException {
        if (JavaVersion.get() >= 17) {
            GuiInstance.getInstance();
            App.init();
            return;
        }
        errorHandler.displayInitErrorAndExit();
    }
}