package net.anvian.mineControlLauncher.gui;

import net.anvian.mineControlLauncher.Main;

public class GuiInstance {
    private static Gui instance;

    public static Gui getInstance() {
        if (instance == null) {
            instance = new Gui(Main.LAUNCHER_VERSION);
        }
        return instance;
    }
}