package org.example;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KeyListener implements HotkeyListener {
    Clicker clicker = new Clicker();
    ExecutorService executor = Executors.newSingleThreadExecutor();
    JIntellitype instance = JIntellitype.getInstance();

    public void init() {
        instance.registerHotKey(1, JIntellitype.MOD_CONTROL, KeyEvent.VK_1);
        instance.registerHotKey(2, JIntellitype.MOD_CONTROL, KeyEvent.VK_2);
        instance.registerHotKey(3, JIntellitype.MOD_CONTROL, KeyEvent.VK_3);
        instance.registerHotKey(4, JIntellitype.MOD_CONTROL, KeyEvent.VK_4);

        instance.addHotKeyListener(this);
    }

    @Override
    public void onHotKey(int i) {
        if (i == 1) {
            PositionHolder.setStartX((int) MouseInfo.getPointerInfo().getLocation().getX());
            PositionHolder.setStartY(     (int) MouseInfo.getPointerInfo().getLocation().getY());
            System.out.println("Pressed: " + i);
            try {
                System.out.println(new Robot().getPixelColor(PositionHolder.getStartX(), PositionHolder.getStartY()));
            } catch (AWTException e) {
                e.printStackTrace();
            }

        } else if (i == 2) {

             System.out.println("Pressed: " + i);
            try {
                System.out.println(new Robot().getPixelColor(PositionHolder.getEndX(), PositionHolder.getEndY()  ));
            } catch (AWTException e) {
                e.printStackTrace();
            }
        } else if (i == 3) {
            if (Clicker.STOP) {
                Clicker.STOP = false;
                PositionHolder.resetPosition();
                PositionHolder.setStartX(862);
                PositionHolder.setStartY(317);
                PositionHolder.setEndX(689);
                PositionHolder.setEndY(264);
                executor = Executors.newSingleThreadExecutor();
                executor.submit(clicker);
            } else {
                Clicker.STOP = true;
                executor.shutdown();
            }
        } else if (i == 4) {
            instance.cleanUp();
            InputController.releaseKey(ScanCode.DIK_DOWN);
            System.exit(0);
        }
    }
}
