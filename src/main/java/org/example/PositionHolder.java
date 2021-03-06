package org.example;

import java.awt.*;

public class PositionHolder {

    private static int startX;

    private static int startY;

    private static int endX;

    private static int endY;

    static {
        int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
        int width = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
        startX = (width/2) - 20;
        startY = (height/2) - 40;
        endX = (width/2) + 20;
        endY = startY;
    }

    public static int getStartX() {
        return startX;
    }

    public static void setStartX(int startX) {
        System.out.println("startx: " + startX);
        PositionHolder.startX = startX;
    }

    public static int getStartY() {
        return startY;
    }

    public static void setStartY(int startY) {
        System.out.println("starty: " + startY);

        PositionHolder.startY = startY;
    }

    public static int getEndX() {
        return endX;
    }

    public static void setEndX(int endX) {
        System.out.println("endX: " + endX);

        PositionHolder.endX = endX;
    }

    public static int getEndY() {
        return endY;
    }

    public static void setEndY(int endY) {
        System.out.println("endY: " + endY);
        PositionHolder.endY = endY;
    }
}
