package org.example;

import java.awt.*;

public class Clicker implements Runnable {
    public static boolean STOP = true;
    private static Robot robot;
    private static boolean cactus;

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

            if (doStep(true, false)) {
                long l = System.currentTimeMillis();
                if (doStep(false, true)) {
                    long l1 = System.currentTimeMillis() - l;
                    if (l1 < 100) {
                        makeLatency((int) (100 - l1));
                        dinoStep(true);
                        makeLatency((int) l1);
                        dinoStep(false);
                    }

                }
            }
//            if (doStep(true, true)) {
//                System.out.println("1");
//                if (doStep(false, true)) {
//                    System.out.println("2");
//                    if (doStep(false, false)) {
//                        System.out.println("3");
//                        if (doStep(true, false)) {
//                            System.out.println("4");
//                            if (doStep(true, true)) {
//                                System.out.println("5");
//                                if (doStep(true, false)) {
//                                    System.out.println("6");
//                                    if (doStep(false, false)) {
//                                        System.out.println("final");
//                                        finalStep();
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        }
    }

    public boolean doStep(boolean first, boolean second) {
        while (!STOP) {
//            for (int i = 0; i < 10; i++) {
                Color pixelColor = robot.getPixelColor(PositionHolder.getStartX(), PositionHolder.getStartY() );
                if (first && isBlack(pixelColor) || !first && isGreen(pixelColor)) {
                    Clicker.cactus = true;
                    return true;
                }
//            }

            Color pixelColor1 = robot.getPixelColor(PositionHolder.getEndX(), PositionHolder.getEndY());
            if (first && isBlack(pixelColor1) || !first && isGreen(pixelColor1)) {
                if (first){
                    Clicker.cactus = false;
                }
                return true;
            }

        }
//        System.out.println("fuck");
        return false;
    }

    public void finalStep() {
        System.out.println("press");
        InputController.pressKey(ScanCode.DIK_E, 500);
        makeLatency(1);
    }

    public void dinoStep(boolean push) {
        System.out.println("press " + push);
//        InputController.releaseKey(ScanCode.DIK_DOWN);
        if (push && cactus) {
            InputController.pushKey(ScanCode.DIK_SPACE);
        } else if (!push && cactus) {
            InputController.releaseKey(ScanCode.DIK_SPACE);
        }
        if (!push && !cactus) {
            InputController.releaseKey(ScanCode.DIK_DOWN);
        } else if (push && !cactus) {
            InputController.pushKey(ScanCode.DIK_DOWN);
        }
//        makeLatency((int)l);
//        makeLatency(1);
    }

    private boolean isBlack(Color color) {
        if (color == null)
            return false;
        else
            return color.getBlue() < 150 && color.getGreen() < 150 && color.getRed() < 150;
    }

    private boolean isGreen(Color color) {
        if (color == null)
            return false;
        else
            return (color.getBlue() > 250 && color.getGreen() > 250 && color.getRed() > 250);
    }

    private void makeLatency(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
