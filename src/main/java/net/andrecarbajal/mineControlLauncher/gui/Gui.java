package net.andrecarbajal.mineControlLauncher.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Gui extends JFrame {
    private JProgressBar progressBar;
    private JLabel logLabel;

    protected Gui(String version) {
        initComponents(version);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        progressBar.setIndeterminate(true);
    }

    private void initComponents(String version) {
        JPanel contend = new JPanel();
        JLabel icon = initIcon();
        logLabel = initLogLabel();
        progressBar = initProgressBar();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MineControl " + version);
        setPreferredSize(new Dimension(300, 350));
        setResizable(false);
        setSize(new Dimension(300, 350));

        GroupLayout contendLayout = new GroupLayout(contend);
        contend.setLayout(contendLayout);
        contendLayout.setHorizontalGroup(
                contendLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(contendLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contendLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(logLabel)
                                        .addComponent(icon, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                        .addGroup(contendLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGap(6, 6, 6))
        );
        contendLayout.setVerticalGroup(
                contendLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(contendLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGap(51, 51, 51)
                                .addComponent(icon)
                                .addGap(38, 38, 38)
                                .addComponent(logLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(85, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(contend, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(contend, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private JLabel initIcon() {
        JLabel icon = new JLabel();
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setIcon(new ImageIcon(Objects.requireNonNull(Gui.class.getResource("/icon.png"))));
        return icon;
    }

    private JLabel initLogLabel() {
        return new JLabel();
    }

    private JProgressBar initProgressBar() {
        return new JProgressBar();
    }

    public void setLogLabel(String text) {
        SwingUtilities.invokeLater(() -> logLabel.setText(text));
    }
}