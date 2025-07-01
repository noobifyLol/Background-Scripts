package frags;

import java.awt.AWTException;
import java.awt.Robot;
import static java.awt.event.InputEvent.BUTTON3_DOWN_MASK;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.sun.jna.platform.win32.Tlhelp32;



public class move{
public static AtomicBoolean running = new AtomicBoolean(true);
   
public static Robot robot;
   
   public static void movinmovstatic (Robot robot) {
        robot.keyPress(KeyEvent.VK_W);
        
   }

   public static void click (Robot robot){
    
    try{
    robot.mousePress(BUTTON3_DOWN_MASK);
    Thread.sleep(250);
    }
    catch (Exception e){

    }
   }
    public static void main (String [] args) throws AWTException{
        robot = new Robot();
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        
        }
        
        try {
           GlobalScreen.addNativeKeyListener(new BGSAUTOHATCH());
            GlobalScreen.registerNativeHook();
                Thread movement = new Thread(() ->{
           while (running.get()) {
                  
            movinmovstatic(robot);

                }
            });
           while (running.get()){
            Thread clicking = new Thread(() -> {
                click(robot);

            });
           }
           movement.start();
           clicking.start();

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
