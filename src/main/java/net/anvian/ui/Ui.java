package net.anvian.ui;

import net.anvian.Main;

import javax.swing.*;

public class Ui extends JDialog {
    private static Ui instance;
    private JPanel contend;
    private JLabel titleLabel;
    private JProgressBar progressBar1;
    private JLabel logLabel;

    public Ui() {
        setUndecorated(true);
        setVisible(true);
        setContentPane(contend);
        pack();
        setSize(300, 350);
        setLocationRelativeTo(null);

        titleLabel.setText(" MineControl Launcher v." + Main.LAUNCHER_VERSION);

        progressBar1.setIndeterminate(true);
    }

    public static Ui getInstance() {
        if (instance == null) {
            instance = new Ui();
        }
        return instance;
    }

    public void setLogLabel(String text) {
        SwingUtilities.invokeLater(() -> logLabel.setText(text));
    }
}
