package com.fractals;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BoxCounting {
    public static double fractalDimension(boolean[][] matrix, double threshold) {
        int width = matrix.length;
        int height = matrix[0].length;

        // Count boxes
        int[] boxes = new int[10];
        for (int i = 1; i < 10; i++) {
            int boxSize = width / i;
            for (int x = 0; x < i; x++) {
                for (int y = 0; y < i; y++) {
                    boolean isBoxEmpty = true;
                    for (int j = x * boxSize; j < (x + 1) * boxSize; j++) {
                        for (int k = y * boxSize; k < (y + 1) * boxSize; k++) {
                            if (matrix[j][k]) {
                                isBoxEmpty = false;
                                break;
                            }
                        }
                        if (!isBoxEmpty) {
                            break;
                        }
                    }
                    if (!isBoxEmpty) {
                        boxes[i]++;
                    }
                }
            }
        }

        // Linear regression (logarithmic)
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXX = 0;
        for (int i = 1; i < 10; i++) {
            double logI = Math.log(1.0 / i);
            double logBoxes = Math.log(boxes[i]);
            sumX += logI;
            sumY += logBoxes;
            sumXY += logI * logBoxes;
            sumXX += logI * logI;
        }

        double n = 9.0;
        double slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        return -slope;
    }

    public static boolean[][] loadImage(String filePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(filePath));
        int width = image.getWidth();
        int height = image.getHeight();
        boolean[][] matrix = new boolean[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb, true);
                matrix[x][y] = color.getAlpha() > 0 && (color.getRed() + color.getGreen() + color.getBlue()) / 3 < 128;
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        try {
            // Load image
            String filePath = "/Users/AnduScheusan/Documents/Coding/Java/Box-Counting/src/com/fractals/pictures/fractal.png";
            boolean[][] matrix = loadImage(filePath);

            // Calculate fractal dimension
            double threshold = 0.9;
            double dimension = fractalDimension(matrix, threshold);

            // Print the fractal dimension
            System.out.println("Fractal dimension: " + dimension);
        }catch (FileNotFoundException e){
            System.out.println("File not found!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
