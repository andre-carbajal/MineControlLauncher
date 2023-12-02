package net.anvian.mineControlLauncher.util;

import jakarta.validation.constraints.NotNull;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

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
        printer("[Mine Control Launcher] [ERROR] " + getStackTrace(ex));
    }

    private static void printer(String line) {
        consoleOutput.println(line);
    }

    @NotNull
    private static String getStackTrace(@NotNull Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
