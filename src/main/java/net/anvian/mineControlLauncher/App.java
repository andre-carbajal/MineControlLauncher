package net.anvian.mineControlLauncher;

import net.anvian.mineControlLauncher.gui.Gui;
import net.anvian.mineControlLauncher.gui.GuiInstance;
import net.anvian.mineControlLauncher.util.RunAppJavaFx;
import net.anvian.mineControlLauncher.util.checher.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    private static final Checker launcherChecker = new LauncherUpdaterChecker();
    private static final Checker folderChecker = new FolderChecker();
    private static final Checker javaFxChecker = new JavaFxChecker();
    private static final Checker applicationChecker = new ApplicationChecker();
    private static final Gui guiInstance = GuiInstance.getInstance();
    private static final RunAppJavaFx runApplicationWithJavaFx = new RunAppJavaFx();

    public static void init() throws IOException {
        Path dirPath = Paths.get(Main.USER_HOME, Main.MAIN_FOLDER);

        launcherChecker.check(dirPath);
        guiInstance.setLogLabel("");
        folderChecker.check(dirPath);
        guiInstance.setLogLabel("");
        javaFxChecker.check(dirPath);
        guiInstance.setLogLabel("");
        applicationChecker.check(dirPath);
        guiInstance.setLogLabel("");
        runApplicationWithJavaFx.init(dirPath);
    }
}