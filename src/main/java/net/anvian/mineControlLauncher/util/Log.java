package net.anvian.mineControlLauncher.util;

import java.io.PrintStream;

public class Log {
    private static final PrintStream consoleOutput = System.out;

    public static void println(String string) {
        printer("[Mine Control Launcher] " + string);
    }

    public static void warn(String string) {
        printer("[Mine Control Launcher] [WARN] " + string);
    }

    public static void error(String string) {
        printer("[Mine Control Launcher] [ERROR] " + string);
    }

    public static void error(String string, Throwable ex) {
        printer("[Mine Control Launcher] [ERROR] " + string);
        printer("[Mine Control Launcher] [ERROR] " + StringUtils.getStackTrace(ex));
    }

    private static void printer(String line) {
        consoleOutput.println(line);
    }

}
