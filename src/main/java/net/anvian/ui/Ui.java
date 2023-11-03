package net.anvian.ui;

import javax.swing.*;

public class Ui extends JDialog {
    private JPanel contend;
    private JProgressBar progressBar;
    private JLabel titleName;

    public Ui(String[] args) {
        setUndecorated(true);
        setVisible(true);
        setContentPane(contend);
        pack();
        setSize(300, 350);
        setLocationRelativeTo(null);
        titleName.setText("MineControl Launcher v.0.1");
    }
}
