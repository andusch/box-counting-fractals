package com.fractals;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoxCountingGUI extends JFrame {

    private JLabel appTitle;
    private JLabel resultLabel;
    private JLabel imageLabel;

    public BoxCountingGUI() {
        setTitle("Fractal Dimension Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        ImageIcon imageIcon = new ImageIcon("/Users/AnduScheusan/Documents/Coding/Java/Box-Counting/src/com/fractals/media/icon.png");

        setIconImage(imageIcon.getImage());

        appTitle = new JLabel("Fractal Dimension Calculator");

        JButton selectImageButton = new JButton("Choose Image");
        selectImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(BoxCountingGUI.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                    try {
                        BufferedImage image = ImageIO.read(new File(imagePath));
                        ImageIcon imageIcon = new ImageIcon(image);
                        imageLabel.setIcon(imageIcon);

                        boolean[][] matrix = BoxCounting.loadImage(imagePath);
                        double threshold = 0.9;
                        double dimension = BoxCounting.fractalDimension(matrix, threshold);
                        resultLabel.setText("Fractal dimension: " + dimension);
                    } catch (IOException ex) {
                        resultLabel.setText("Error: " + ex.getMessage());
                    }
                }
            }
        });

        appTitle.setPreferredSize(new Dimension(800, 60));
        appTitle.setFont(new Font("Verdana", Font.BOLD, 30));
        appTitle.setHorizontalAlignment(SwingConstants.CENTER);
        appTitle.setVerticalAlignment(SwingConstants.TOP);

        resultLabel = new JLabel();
        resultLabel.setPreferredSize(new Dimension(300, 60));
        resultLabel.setFont(new Font("Verdana", Font.PLAIN, 16));

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(350, 350));

        add(appTitle);
        add(selectImageButton);
        add(imageLabel);
        add(resultLabel);

        pack();
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BoxCountingGUI();
            }
        });
    }
}
