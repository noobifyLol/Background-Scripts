package frags;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;


public class move{
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
                    
                }//main while end
    }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanUp();
        }
    }

     
    public static void nativeKeyPressed(NativeKeyEvent e){
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            running.set(false);
            cleanUp(); // Clean resources
        System.exit(0);
            System.out.println("Exiting...");
            
        }
    } //nativekeypressed end
   
   
    
    
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

