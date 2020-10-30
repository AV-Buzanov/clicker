package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight());
        System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth());
//        Window window = new Window(new Frame());
//        window.setSize(200,200);
//        window.setAlwaysOnTop(true);
//        window.setBackground(Color.RED);
//        window.setLocation(500,500);
//        window.show();
//        Thread.sleep(5000);
//        JDialog dialog = new JDialog();
//        dialog.setAlwaysOnTop(true);
//        dialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//        dialog.setLocation(500, 500);
//        dialog.setSize(200,200);
//        dialog.setVisible(true);
//        dialog.show();
        KeyListener keyListener = new KeyListener();
        keyListener.init();
    }
}
