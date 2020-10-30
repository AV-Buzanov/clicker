package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Clicker implements Runnable {
    public static boolean STOP = true;
    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!STOP) {
            if (doStep(true, true)) {
                System.out.println("1");
                if (doStep(false, true)) {
                    System.out.println("2");
                    if (doStep(false, false)) {
                        System.out.println("3");
                        if (doStep(true, false)) {
                            System.out.println("4");
                            if (doStep(true, true)) {
                                System.out.println("5");
                                if (doStep(true, false)) {
                                    System.out.println("6");
                                    if (doStep(false, false)) {
                                        System.out.println("final");
                                        finalStep();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean doStep(boolean first, boolean second) {
        int timer = 500;
        while (timer != 0 && !STOP) {
            Color pixelColor = robot.getPixelColor(PositionHolder.getStartX(), PositionHolder.getStartY());
            if (first && isBlack(pixelColor) || !first && isGreen(pixelColor)) {
                System.out.println("start: "+ pixelColor);
                pixelColor = robot.getPixelColor(PositionHolder.getEndX(), PositionHolder.getEndY());
                if (second && isBlack(pixelColor) || !second && isGreen(pixelColor)) {
                    System.out.println("end: "+ pixelColor);
                    return true;
                }
            }
            timer--;
            makeLatency(1);
        }
        System.out.println("fuck");
        return false;
    }

    public void finalStep() {
        System.out.println("press");
        robot.keyPress(KeyEvent.VK_CAPS_LOCK);
        robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
        makeLatency(500);
    }

    private boolean isBlack(Color color) {
        if (color == null)
            return false;
        else
            return color.getBlue() < 20 && color.getGreen() < 20 && color.getRed() < 20;
    }

    private boolean isGreen(Color color) {
        if (color == null)
            return false;
        else
            return (color.getBlue() > 20 && color.getGreen() > 100 && color.getRed() > 20);
    }

    private void makeLatency(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
