package net.andrecarbajal.mineControlLauncher.gui;

import net.andrecarbajal.mineControlLauncher.Constants;

public class GuiInstance {
    private static Gui instance;

    public static Gui getInstance() {
        if (instance == null) {
            instance = new Gui(Constants.LAUNCHER_VERSION);
        }
        return instance;
    }
}