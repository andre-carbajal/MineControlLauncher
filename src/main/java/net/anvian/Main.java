package net.anvian;

import net.anvian.ui.Ui;
import net.anvian.util.JavaVersion;
import net.anvian.util.Log;

import javax.swing.*;

public class Main {

    public static final String USER_HOME = System.getProperty("user.home");
    public static final String MAIN_FOLDER = ".MineControl";
    public static final String JAVA_FX_FOLDER = "javafx-sdk-17.0.9";
    public static final String LAUNCHER_VERSION = "0.1";

    private static final String ERROR_MESSAGE = "<style>p {font-family: Arial; font-size:14;} </style>" +
            "<p>Your Java version (%s) <b>is not supported</b> with this version of launcher!</p>" +
            "<p>Please make sure that you meet one of the following requirements:<br> 1. Update to Java 17 <i>(MineControl Launcher will automatically download JavaFX)</i></p>";

    public static void main(String[] args) {
        if (JavaVersion.get() >= 17) {
            new Ui();
            App.init();
            return;
        }
        displayInitErrorAndExit();
    }

    private static void displayInitErrorAndExit() {
        String err = String.format(ERROR_MESSAGE, JavaVersion.get());
        Log.error(err);
        JEditorPane pane = new JEditorPane("text/html", err);
        pane.setEditable(false);
        pane.setOpaque(false);
        JOptionPane.showMessageDialog(null, pane, "Mine Control Launcher Fatal Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}