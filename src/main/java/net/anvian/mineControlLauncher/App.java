package net.anvian.mineControlLauncher;

import net.anvian.mineControlLauncher.gui.Gui;
import net.anvian.mineControlLauncher.gui.GuiInstance;
import net.anvian.mineControlLauncher.util.RunAppWithJavaFx;
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
    private static final RunAppWithJavaFx runAppWithJavaFx = new RunAppWithJavaFx();

    public static void init() throws IOException {
        Path dirPath = Paths.get(Constants.USER_HOME, Constants.MAIN_FOLDER);

        checkAndClearLog(launcherChecker, dirPath);
        checkAndClearLog(folderChecker, dirPath);
        checkAndClearLog(javaFxChecker, dirPath);
        checkAndClearLog(applicationChecker, dirPath);
        runAppWithJavaFx.init(dirPath);
    }

    private static void checkAndClearLog(Checker checker, Path dirPath) throws IOException {
        checker.check(dirPath);
        guiInstance.setLogLabel("");
    }
}