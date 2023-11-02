package net.anvian.window;

import javax.swing.*;

public class Window extends JDialog {
    private JPanel contend;
    private JProgressBar progressBar1;

    public Window() {
        setUndecorated(true);
        setContentPane(contend);
        pack();
        setSize(300, 350);
        setLocationRelativeTo(null);
    }
}
