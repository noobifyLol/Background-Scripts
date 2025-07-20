package frags;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.awt.event.InputEvent;
public class 99dayseatingstew implements NativeKeyListener{
    public static AtomicBoolean running = new AtomicBoolean(true);
   public static Robot robot;
   
   
    public static void main (String [] args) throws AWTException , Exception{
        robot = new Robot();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        
        }
        GlobalScreen.addNativeKeyListener(new 99dayseatingstew());
        
        try {
           GlobalScreen.registerNativeHook();
                while (running.get()) {
                    Thread.sleep(200000);
                    robot.keyPress(KeyEvent.VK_1);
                    robot.keyRelease(KeyEvent.VK_1);
                    moveclick(robot);
                    Thread.sleep(200000);
                    robot.keyPress(KeyEvent.VK_1);
                    robot.keyRelease(KeyEvent.VK_1);
                    moveclick(robot);
                    Thread.sleep(200000);
                    robot.keyPress(KeyEvent.VK_2);
                    robot.keyRelease(KeyEvent.VK_2);
                    moveclick(robot);
                    Thread.sleep(200000);
                    robot.keyPress(KeyEvent.VK_3);
                    robot.keyRelease(KeyEvent.VK_3);
                    moveclick(robot);
                    Thread.sleep(200000);
                    robot.keyPress(KeyEvent.VK_4);
                    robot.keyRelease(KeyEvent.VK_4);
                    moveclick(robot);
                    Thread.sleep(200000);
                    robot.keyPress(KeyEvent.VK_5);
                    robot.keyRelease(KeyEvent.VK_5);
                    moveclick(robot);
                    Thread.sleep(200000);
                    robot.keyPress(KeyEvent.VK_6);
                    robot.keyRelease(KeyEvent.VK_6);
                    moveclick(robot);
                    Thread.sleep(200000);
                    robot.keyPress(KeyEvent.VK_7);
                    robot.keyRelease(KeyEvent.VK_7);
                    moveclick(robot);
                    Thread.sleep(200000);
                    robot.keyPress(KeyEvent.VK_8);
                    robot.keyRelease(KeyEvent.VK_8);
                    moveclick(robot);
                    Thread.sleep(200000);
                    robot.keyPress(KeyEvent.VK_9);
                    robot.keyRelease(KeyEvent.VK_9);
                    moveclick(robot);
                    
                     
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
             GlobalScreen.removeNativeKeyListener(new 99dayseatingstew());
            GlobalScreen.unregisterNativeHook();
            running.set(false);
            
        }
    catch(NativeHookException ignored){
        

    }
    }
    private static void moveclick(Robot robot){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

    }
}