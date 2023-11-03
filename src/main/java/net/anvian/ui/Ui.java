package net.anvian.ui;

import net.anvian.Main;

import javax.swing.*;

public class Ui extends JDialog {
    private JPanel contend;
    private JLabel titleLabel;
    private JProgressBar progressBar1;
    public JLabel logLabel;

    public Ui(String[] args) {
        setUndecorated(true);
        setVisible(true);
        setContentPane(contend);
        pack();
        setSize(300, 350);
        setLocationRelativeTo(null);

        titleLabel.setText("  MineControl Launcher v." + Main.LAUNCHER_VERSION);

        progressBar1.setIndeterminate(true);
    }
}
