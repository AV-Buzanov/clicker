package org.example;

import com.sun.jna.platform.win32.WinUser;

import java.awt.event.KeyEvent;

public class JnaKeyListener implements Runnable {
    public static void register() {
        com.sun.jna.platform.win32.User32.INSTANCE.RegisterHotKey(null, 1, 0x000, KeyEvent.VK_F);
    }

    @Override
    public void run() {
        com.sun.jna.platform.win32.User32 user32 = com.sun.jna.platform.win32.User32.INSTANCE;
        WinUser.MSG msg = new WinUser.MSG();
        while (true) {
            register();
            while (user32.PeekMessage(msg, null, 0, 0, 0x0001)) {
                if (msg != null) {
                    System.out.println("Hotkey pressed with id: " + msg.wParam);
                }
            }

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
