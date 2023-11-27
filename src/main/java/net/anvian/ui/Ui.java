package net.anvian.ui;

import net.anvian.Main;

import javax.swing.*;

public class Ui extends JFrame {
    private static Ui instance;
    private JPanel contend;
    private JLabel titleLabel;
    private JProgressBar progressBar1;
    private JLabel logLabel;

    public Ui() {
        setVisible(true);
        setContentPane(contend);
        pack();

        setSize(300, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
