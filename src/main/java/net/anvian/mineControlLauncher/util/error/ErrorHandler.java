package net.anvian.mineControlLauncher.util.error;

import net.anvian.mineControlLauncher.util.Log;
import net.anvian.mineControlLauncher.util.os.JavaVersion;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

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

    public void displayUpdateErrorAndExit(String current_version,String url) {
        String err = String.format(UPDATE_ERROR_MESSAGE, current_version, url);
        Log.error(err);
        JEditorPane pane = new JEditorPane("text/html", err);
        pane.setEditable(false);
        pane.setOpaque(false);
        pane.addHyperlinkListener(e -> {
            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException | URISyntaxException ex) {
                        Log.error("Error opening link", ex);
                    }
                }
            }
        });
        JOptionPane.showMessageDialog(null, pane, "Mine Control Launcher Fatal Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    private static final String UPDATE_ERROR_MESSAGE = "<style>p {font-family: Arial; font-size:14;} </style>" +
            "<p>Your Launcher version (%s) <b>is outdated</b></p>" +
            "<p>Download the new version here:</p>" +
            "<a href=\"%s\">Launcher last version</a>";

    private static final String ERROR_MESSAGE  = "<style>p {font-family: Arial; font-size:14;} </style>" +
            "<p>Your Java version (%s) <b>is not supported</b> with this version of launcher!</p>" +
            "<p>Please make sure that you meet one of the following requirements:<br> 1. Update to Java 17 <i>(MineControl Launcher will automatically download JavaFX)</i></p>";
}