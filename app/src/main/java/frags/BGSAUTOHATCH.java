package frags;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Union;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

import frags.BGSAUTOHATCH.SendInputHelper.User32Ext;

public class BGSAUTOHATCH implements NativeKeyListener {
    public static AtomicBoolean running = new AtomicBoolean(true);
    private static final int EXIT_KEY = NativeKeyEvent.VC_ESCAPE;
    private static final int STOP_KEY = NativeKeyEvent.VC_F1;
    private static final int STARTAGAIN_KEY = NativeKeyEvent.VC_F2;
    public static Boolean running2 = false;
    SendInputHelper inputHelper = new SendInputHelper();
    private static final int key = 0x45;

    private static final String GAME_WINDOW_TITLE = "Roblox";
    private static volatile boolean paused = false;
    private static final Object pauseLock = new Object();

    public static class SendInputHelper {
        public interface User32Ext extends StdCallLibrary {
            User32Ext INSTANCE = Native.load("user32", User32Ext.class, W32APIOptions.DEFAULT_OPTIONS);
            int SendInput(int nInputs, INPUT[] pInputs, int cbSize);
            HWND FindWindowA(String lpClassName, String lpWindowName);
            boolean SetForegroundWindow(HWND hWnd);
            boolean ShowWindow(HWND hWnd, int nCmdShow);
            HWND GetForegroundWindow();

           
        }

        public static class INPUT extends Structure {
            public static class ByReference extends INPUT implements Structure.ByReference {}
            public int type;
            public INPUT_UNION input;

            public INPUT() {
                super();
            }

            @Override
            protected List<String> getFieldOrder() {
                return Arrays.asList("type", "input");
            }
        }

        public static class INPUT_UNION extends Union {
            public KEYBDINPUT ki;

            public INPUT_UNION() {
                super();
            }

            @Override
            protected List<String> getFieldOrder() {
                return Arrays.asList("ki");
            }
        }

        public static class KEYBDINPUT extends Structure {
            public short wVk;
            public short wScan;
            public int dwFlags;
            public int time;
            public Pointer dwExtraInfo;

            @Override
            protected List<String> getFieldOrder() {
                return Arrays.asList("wVk", "wScan", "dwFlags", "time", "dwExtraInfo");
            }
        }

        public static final int INPUT_KEYBOARD = 1;
        public static final int KEYEVENTF_KEYUP = 0x0002;

        
        public static final int SW_RESTORE = 9;
        private static final int SW_MINIMIZE = 6;
        public static int values(){
            return SW_MINIMIZE;
        }
        public static void pressKey(short keyCode) {
            INPUT[] inputs = (INPUT[]) new INPUT().toArray(2);

            // Press key
            inputs[0].type = INPUT_KEYBOARD;
            inputs[0].input.setType(KEYBDINPUT.class);
            inputs[0].input.ki = new KEYBDINPUT();
            inputs[0].input.ki.wVk = keyCode;
            inputs[0].input.ki.wScan = 0;
            inputs[0].input.ki.dwFlags = 0;
            inputs[0].input.ki.time = 0;
            inputs[0].input.ki.dwExtraInfo = null;
            inputs[0].write(); // write to native memory
            
            // Release key
            inputs[1].type = INPUT_KEYBOARD;
            inputs[1].input.setType(KEYBDINPUT.class);
            inputs[1].input.ki = new KEYBDINPUT();
            inputs[1].input.ki.wVk = keyCode;
            inputs[1].input.ki.wScan = 0;
            inputs[1].input.ki.dwFlags = KEYEVENTF_KEYUP;
            inputs[1].input.ki.time = 0;
            inputs[1].input.ki.dwExtraInfo = null;
            inputs[1].write(); 

            // Call native function
            User32Ext.INSTANCE.SendInput(inputs.length, inputs, inputs[0].size());
        }


        public static void activateWindow(HWND hWnd) {
            User32Ext.INSTANCE.ShowWindow(hWnd, SW_RESTORE);   
            User32Ext.INSTANCE.SetForegroundWindow(hWnd);      
        }

        
        public static boolean inForeGround(HWND roblox){
            
            HWND foreground = User32Ext.INSTANCE.GetForegroundWindow();
            return roblox != null && roblox.equals(foreground);
        }
    }

    public static void main(String[] args) throws AWTException, IOException {
       SendInputHelper inputHelper = new SendInputHelper();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {}

        GlobalScreen.addNativeKeyListener(new BGSAUTOHATCH());

        try {
            GlobalScreen.registerNativeHook();
            HWND robloxWnd = User32.INSTANCE.FindWindow(null, "Roblox");
            SendInputHelper.activateWindow(robloxWnd);
            
            while (running.get()) {
                synchronized (pauseLock) {
                    while (paused) {
                        pauseLock.wait();
                    }
                }
                if (!SendInputHelper.inForeGround(robloxWnd)){
                    User32.INSTANCE.SetForegroundWindow(robloxWnd);
                    User32Ext.INSTANCE.ShowWindow(robloxWnd, SendInputHelper.values());
                    SendInputHelper.activateWindow(robloxWnd);
                    Thread.sleep(1325);
                }
                
                
                
                if (robloxWnd != null && SendInputHelper.inForeGround(robloxWnd)) {
                    
                    
                    Thread.sleep(100); // Allow window to activate
                    SendInputHelper.pressKey((short) 0x12);  // 'E' key
                    Thread.sleep(100); // Allow key press processing
                    
                } else {
                    System.out.println("Roblox window not found!");
                }
                

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanUp();
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == EXIT_KEY) {
            running.set(false);
            synchronized (pauseLock) {
                pauseLock.notifyAll();
            }
            System.exit(0);
        }

        if (e.getKeyCode() == STOP_KEY) {
            paused = true;
            System.out.println("Paused");
        }

        if (e.getKeyCode() == STARTAGAIN_KEY) {
            synchronized (pauseLock) {
                paused = false;
                pauseLock.notifyAll();
                System.out.println("Resumed");
            }
    }
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}

    private static void cleanUp() {
        try {
            GlobalScreen.unregisterNativeHook();
            running.set(false);
        } catch (NativeHookException ignored) {}
    }
}