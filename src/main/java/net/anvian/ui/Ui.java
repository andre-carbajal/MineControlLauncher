package net.anvian.ui;

import net.anvian.Main;

import javax.swing.*;

public class Ui extends JDialog {
    private JPanel contend;
    private JLabel titleName;
    private JProgressBar progressBar;
    public JLabel log;

    public Ui(String[] args) {
        setUndecorated(true);
        setVisible(true);
        setContentPane(contend);
        pack();
        setSize(300, 350);
        setLocationRelativeTo(null);

        titleName.setText("MineControl Launcher v." + Main.LAUNCHER_VERSION);

        progressBar.setIndeterminate(true);
    }
}
