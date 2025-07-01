package frags;

import java.awt.AWTException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
public class Quick implements NativeKeyListener{
    public static AtomicBoolean running = new AtomicBoolean(true);
   public static Robot robot;
   
   
    public static void main (String [] args) throws AWTException{
        robot = new Robot();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        
        }
        GlobalScreen.addNativeKeyListener(new BGSAUTOHATCH());
        
        try {
           GlobalScreen.registerNativeHook();
                while (running.get()) {
                    robot.keyPress(KeyEvent.VK_E);
                    robot.keyRelease(KeyEvent.VK_E);
                    
                    Thread.sleep(100);  
                }//main while end
    }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanUp();
        }
    }

     
    public void nativeKeyPressed(NativeKeyEvent e){
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            running.set(false);
            cleanUp(); // Clean resources
        System.exit(0);
            System.out.println("Exiting...");
            
        }
    } //nativekeypressed end
   
   
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}
    
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}
    
    private static void cleanUp() {
         
        try {
             GlobalScreen.removeNativeKeyListener(new BGSAUTOHATCH());
            GlobalScreen.unregisterNativeHook();
            running.set(false);
            
        }
    catch(NativeHookException ignored){
        
    }
    }
}
