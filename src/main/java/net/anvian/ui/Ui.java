package net.anvian.ui;

import javax.swing.*;

public class Ui extends JDialog {
    private JPanel contend;
    private JLabel titleName;
    public JProgressBar progressBar;

    public Ui(String[] args) {
        setUndecorated(true);
        setVisible(true);
        setContentPane(contend);
        pack();
        setSize(300, 350);
        setLocationRelativeTo(null);

        titleName.setText("MineControl Launcher v.0.1");

        progressBar.setIndeterminate(true);
    }
}
