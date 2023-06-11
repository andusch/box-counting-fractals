package com.fractals;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class BoxCountingGUI extends JFrame {
    private JLabel resultLabel;

    public BoxCountingGUI() {
        setTitle("Box Counting Fractal Dimension");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton selectImageButton = new JButton("Select Image");
        selectImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(BoxCountingGUI.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                    try {
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

        resultLabel = new JLabel();
        resultLabel.setPreferredSize(new Dimension(300, 30));

        add(selectImageButton);
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
