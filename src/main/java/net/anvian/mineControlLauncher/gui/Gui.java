package net.anvian.mineControlLauncher.gui;

import net.anvian.mineControlLauncher.Main;

import javax.swing.*;
import java.util.Objects;

public class Gui extends JFrame {

    private static Gui instance;
    private JProgressBar progressBar;
    private JLabel logLabel;
    private JLabel titleLabel;

    public Gui() {
        initComponents();

        setVisible(true);
        setSize(300, 350);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel.setText("MineControl Launcher v." + Main.LAUNCHER_VERSION);
        progressBar.setIndeterminate(true);
    }

    private void initComponents() {

        JPanel contend = new JPanel();
        titleLabel = new JLabel();
        JLabel icon = new JLabel();
        logLabel = new JLabel();
        progressBar = new JProgressBar();

        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setIcon(new ImageIcon(Objects.requireNonNull(Gui.class.getResource("/icon.png"))));

        logLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GroupLayout contendLayout = new GroupLayout(contend);
        contend.setLayout(contendLayout);
        contendLayout.setHorizontalGroup(
                contendLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(contendLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contendLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(icon, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(logLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
                        .addGroup(contendLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(titleLabel)
                                .addGap(344, 344, 344))
        );
        contendLayout.setVerticalGroup(
                contendLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(contendLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titleLabel)
                                .addGap(40, 40, 40)
                                .addComponent(icon)
                                .addGap(28, 28, 28)
                                .addComponent(logLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(106, Short.MAX_VALUE))
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

    public static Gui getInstance() {
        if (instance == null) {
            instance = new Gui();
        }
        return instance;
    }

    public void setLogLabel(String text) {
        SwingUtilities.invokeLater(() -> logLabel.setText(text));
    }
}
