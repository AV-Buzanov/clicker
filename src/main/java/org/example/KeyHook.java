package org.example;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KeyHook {
    private static volatile boolean quit;
    private static HHOOK hhk;
    private static LowLevelKeyboardProc keyboardHook;
    private static Clicker clicker = new Clicker();
    private static ExecutorService executor = Executors.newSingleThreadExecutor();
    private static long timer;

    public static void main(String[] args) {
        final User32 lib = User32.INSTANCE;
        HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        keyboardHook = new LowLevelKeyboardProc() {
            @Override
            public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT info) {
                if (nCode >= 0) {
                    switch(wParam.intValue()) {
                        case WinUser.WM_KEYUP:
//                            System.out.println("up "+ info.vkCode);
//                            System.out.println(System.currentTimeMillis()-timer);
                            if (info.vkCode == 119) {
                                quit = true;
                            }
                            break;
                        case WinUser.WM_KEYDOWN:
//                            timer=System.currentTimeMillis();
                            switch (info.vkCode){
                                case 116:
                                    PositionHolder.setStartX((int) MouseInfo.getPointerInfo().getLocation().getX());
                                    PositionHolder.setStartY((int) MouseInfo.getPointerInfo().getLocation().getY());
                                    System.out.println("Pressed: " + info.vkCode);
                                    break;
                                case 117:
                                    PositionHolder.setEndX((int) MouseInfo.getPointerInfo().getLocation().getX());
                                    PositionHolder.setEndY((int) MouseInfo.getPointerInfo().getLocation().getY());
                                    System.out.println("Pressed: " + info.vkCode);
                                    break;
                                case 118:
                                    if (Clicker.STOP) {
                                        Clicker.STOP = false;
                                        PositionHolder.resetPosition();
                                        executor = Executors.newSingleThreadExecutor();
                                        executor.submit(clicker);
                                    } else {
                                        Clicker.STOP = true;
                                        executor.shutdown();
                                    }
                                    break;
                            }

//                            System.out.println("down "+ info.vkCode);
                            break;
                        case WinUser.WM_SYSKEYUP:
                        case WinUser.WM_SYSKEYDOWN:
                            if (info.vkCode == 119) {
                                quit = true;
                            }
                    }
                }

                Pointer ptr = info.getPointer();
                long peer = Pointer.nativeValue(ptr);
                return lib.CallNextHookEx(hhk, nCode, wParam, new WinDef.LPARAM(peer));
            }
        };
        hhk = lib.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyboardHook, hMod, 0);
        System.out.println("Keyboard hook installed, type anywhere, 'q' to quit");
        new Thread() {
            @Override
            public void run() {
                while (!quit) {
                    try { Thread.sleep(10); } catch(Exception e) { }
                }
                System.err.println("unhook and exit");
                lib.UnhookWindowsHookEx(hhk);
                System.exit(0);
            }
        }.start();

        // This bit never returns from GetMessage
        int result;
        MSG msg = new MSG();
        while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
            if (result == -1) {
                System.err.println("error in get message");
                break;
            }
            else {
                System.err.println("got message");
                lib.TranslateMessage(msg);
                lib.DispatchMessage(msg);
            }
        }
        lib.UnhookWindowsHookEx(hhk);
    }
}
