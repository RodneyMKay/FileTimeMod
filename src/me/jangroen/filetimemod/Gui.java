package me.jangroen.filetimemod;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

@SuppressWarnings("FieldCanBeLocal")
public class Gui extends JFrame {
    private JTextField fileSelector;
    private TimeSelector timeSelector;
    private JButton change;

    public Gui() {
        // General
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("File time changer");
        setBounds(0, 0, 370, 170);
        setResizable(false);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // File selector text field
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        this.fileSelector = new JTextField();
        this.fileSelector.addActionListener(e -> changeTime());
        add(this.fileSelector, c);

        // Time selector
        c.gridy++;
        c.fill = GridBagConstraints.NONE;
        this.timeSelector = new TimeSelector();
        add(this.timeSelector, c);

        // Change button
        c.gridy++;
        this.change = new JButton("Change");
        this.change.addActionListener(e -> changeTime());
        add(this.change, c);
    }

    public void changeTime() {
        LocalDateTime time = timeSelector.getTime();
        FileTime fileTime = FileTime.from(time.atZone(ZoneId.systemDefault()).toInstant());
        Path path = Paths.get(fileSelector.getText());

        if (!Files.exists(path)) {
            showErrorScreen("The specified file does not exist!");
            return;
        }

        try {
            Files.setLastModifiedTime(path, fileTime);
        } catch (IOException e) {
            showErrorScreen("Could not modify time!");
            e.printStackTrace(System.err);
            return;
        }

        JOptionPane.showMessageDialog(this, "Last modified date has been set!",
                "Success!", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorScreen(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Unexpected error!", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.setVisible(true);
    }
}
