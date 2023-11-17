package net.anvian;

import net.anvian.ui.Ui;
import net.anvian.util.JavaVersion;
import net.anvian.util.Log;

import javax.swing.*;

public class Main {
   public static double LAUNCHER_VERSION = 0.1;

    public static void main(String[] args) {
        if (JavaVersion.get() >= 17){
            new Ui();
            App.init();
            return;
        }
        initError();
    }

    private static void initError() {
        String style = "<style>p {font-family: Arial; font-size:14;} </style>";
        String err = String.format("<p>Your Java version (%s) <b>is not supported</b> with this version of launcher!</p>", JavaVersion.get()) + "<p>Please make sure that you meet one of the following requirements:<br> 1. Update to Java 17 <i>(MineControl Launcher will automatically download JavaFX)</i></p>";
        Log.error(err);
        JEditorPane pane = new JEditorPane("text/html", style + err);
        pane.setEditable(false);
        pane.setOpaque(false);
        JOptionPane.showMessageDialog(null, pane, "Mine Control Launcher Fatal Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}