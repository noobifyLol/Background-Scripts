package frags;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.util.concurrent.atomic.AtomicInteger;
public class Halloween implements NativeKeyListener {
     public static AtomicBoolean running = new AtomicBoolean(true);
     private static final int EXIT_KEY = NativeKeyEvent.VC_ESCAPE;
     public static Boolean running2 = true;
     public static AtomicInteger m = new AtomicInteger(0);
     private static final String WINDOW_TITLE = "Roblox";
    private static final int WM_KEYDOWN = 0x0100;
    private static final int WM_KEYUP = 0x0101;
    private static final int VK_W = 0x57; 
    private static final int FOCUS_DELAY_MS = 2000;
    public static void main(String [] args) throws AWTException, InterruptedException{
        int x = 1900;
        int y = 540;
        int n = 0;
        
        Robot robot = new Robot();
        try {
            Thread.sleep(5000);
            
            Thread Thread1 = new Thread(() -> {
                try {
                    GlobalScreen.registerNativeHook();
                    GlobalScreen.addNativeKeyListener(new Halloween());
                    
                    while (running.get() && running2) {
                       
                       
                         robot.keyPress(KeyEvent.VK_W);
                        Thread.sleep(1259);
                        robot.keyRelease(KeyEvent.VK_W);
                        Thread.sleep(3000);
                        
                        // Backward movement
                        robot.keyPress(KeyEvent.VK_S);
                        Thread.sleep(1250);
                        robot.keyRelease(KeyEvent.VK_S);
                        Thread.sleep(3000); 
                        m.incrementAndGet();
                        if (m.get() %3 == 0){
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            robot.mouseMove(962,540);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cleanUp(robot);
                }
            });
            Thread1.start();
            
            Thread Thread2 = new Thread(() -> {
                mouseWhileMoving(x, y);
            });
            Thread2.start();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

            }//main end

        
                 
              @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == EXIT_KEY) {
            running.set(false);
            running2 = false; // Trigger exit
        }
    } //nativekeypressed end
    
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}

    private static void cleanUp(Robot robot) {
         int keycode = KeyEvent.VK_W;
        try {
             GlobalScreen.removeNativeKeyListener(new Halloween());
            GlobalScreen.unregisterNativeHook();
            running.set(false);
            running2 = false; // Ensure the second thread stops
            releaseKeyWithSafety(keycode, robot);
        } catch (Exception e) {
            e.printStackTrace();
    
        }} //clean up end
    private static void mouseWhileMoving(int startX, int y){
    
        try {
           while (running.get()){
            int x = startX;
            /*if (x >= 500) {
                for (x = 1900; x > 500 && running.get() && running2; x -= 10) {
                    robot.mouseMove(x, y);
                    Thread.sleep(10);
                }
            } else {
                for (x = 500; x < 1900 && running.get()&& running2; x += 10) {
                    robot.mouseMove(x, y);
                    Thread.sleep(10);
                }
            }*/
        }
        } catch (Exception e) {
            running.set(false);
        }
    }//moving mouse method end
      private static void releaseKeyWithSafety(int keyCode, Robot robot) {
        try {
            robot.keyRelease(keyCode);
        } catch (Exception e) {
            try {
                robot.keyRelease(keyCode);
            } catch (Exception ignored) {}
        } 
    }  // Safety end
    
    
    
    private static void focusGameWindow(Robot robot) {
        try {
            // Simulate Alt+Tab to focus the game window
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_TAB);
            Thread.sleep(FOCUS_DELAY_MS);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_ALT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }//window focus method
    
} //class end
   
