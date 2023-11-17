package net.anvian;

import net.anvian.ui.Ui;
import net.anvian.util.error.ErrorHandler;
import net.anvian.util.os.JavaVersion;

public class Main {

    public static final String USER_HOME = System.getProperty("user.home");
    public static final String MAIN_FOLDER = ".MineControl";
    public static final String JAVA_FX_FOLDER = "javafx-sdk-17.0.9";
    public static final String LAUNCHER_VERSION = "0.1";

    private static final ErrorHandler errorHandler = new ErrorHandler();

    public static void main(String[] args) {
        if (JavaVersion.get() >= 17) {
            new Ui();
            App.init();
            return;
        }
        errorHandler.displayInitErrorAndExit();
    }
}