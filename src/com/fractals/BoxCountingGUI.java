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
        
        // Setting the application name
        setTitle("Fractal Dimension Calculator");

        // Setting the mode of work for the application
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Setting the layout type
        setLayout(new FlowLayout());

        // Setting the icon
        ImageIcon logo = new ImageIcon("./media/icon.jpg");
        setIconImage(logo.getImage());

        // Setting the app title
        appTitle = new JLabel("Fractal Dimension Calculator");

        // Creating the image selector button
        JButton selectImageButton = new JButton("Choose Image");

        // Choosing the image and then returning the filepath
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

        // Aligning the file choose button in page
        selectImageButton.setAlignmentX(40);
        selectImageButton.setAlignmentY(300);

        // Customizations
        appTitle.setFont(new Font("Roboto", Font.BOLD, 30));
        appTitle.setPreferredSize(new Dimension(800, 60));
        appTitle.setHorizontalAlignment(SwingConstants.CENTER);
        appTitle.setVerticalAlignment(SwingConstants.TOP);

        resultLabel = new JLabel();
        resultLabel.setPreferredSize(new Dimension(300, 60));
        resultLabel.setFont(new Font("Roboto", Font.PLAIN, 16));

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(350, 350));

        add(appTitle);
        add(selectImageButton);
        add(imageLabel);
        add(resultLabel);


        // Final Touches
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
