package net.anvian.util.error;

import net.anvian.util.Log;
import net.anvian.util.os.JavaVersion;

import javax.swing.*;

public class ErrorHandler {

    public void displayInitErrorAndExit() {
        String err = String.format(ERROR_MESSAGE, JavaVersion.get());
        Log.error(err);
        JEditorPane pane = new JEditorPane("text/html", err);
        pane.setEditable(false);
        pane.setOpaque(false);
        JOptionPane.showMessageDialog(null, pane, "Mine Control Launcher Fatal Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    private static final String ERROR_MESSAGE = "<style>p {font-family: Arial; font-size:14;} </style>" +
            "<p>Your Java version (%s) <b>is not supported</b> with this version of launcher!</p>" +
            "<p>Please make sure that you meet one of the following requirements:<br> 1. Update to Java 17 <i>(MineControl Launcher will automatically download JavaFX)</i></p>";
}
