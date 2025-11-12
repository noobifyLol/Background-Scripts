package frags;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
/* This script is very situational if you want to get a high score in 99 Days which this program  allows when you reach the 
 *max mutiplier and then you don't want to starve but you can optimize this code it pretty inefficent right now
 */
public class day99seatingstew implements NativeKeyListener{


    public static AtomicBoolean running = new AtomicBoolean(true);
   public static Robot robot;
    public static void main (String [] args) throws AWTException , Exception{
        robot = new Robot();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        
        }
        GlobalScreen.addNativeKeyListener(new day99seatingstew());
        
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
             GlobalScreen.removeNativeKeyListener(new day99seatingstew());
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