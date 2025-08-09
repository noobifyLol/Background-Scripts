
    package frags;

import java.awt.AWTException;
import java.awt.Robot;
import static java.awt.event.InputEvent.BUTTON3_DOWN_MASK;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
/* This script perfectly algins the Plinko shooting machine in the middle for the slot machine in Samuri Vs Zombies if you remember that game and then it just shoots for you */
public class SVZ implements NativeKeyListener {
    public static AtomicBoolean running = new AtomicBoolean(true);
    public static Robot robot;
    public static ScheduledExecutorService executor;
    public static int i = 760; 

    public static void main(String[] args) throws AWTException, NativeHookException {
        robot = new Robot();
        executor = Executors.newScheduledThreadPool(2);

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new move());

            Thread.sleep(5000); // Initial delay for setup
            executor.execute(() -> {
                executor.scheduleAtFixedRate(() -> {
                    while (running.get()){
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        if (i < 800) {
                        i += 2; 
                        robot.mouseMove(1240, i);
                        
                        
                    }
                        else if (i >= 800){
                            i = 760;
                            try {
                            Thread.sleep(500);
                        } catch (Exception e) {
                        }
                        }
                     else {
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    }
                }
                }, 0, 1500, TimeUnit.MILLISECONDS);
            });

            while (running.get()) {
                Thread.sleep(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanUp();
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) { //failsafe
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            running.set(false);
            System.out.println("Exiting...");
            cleanUp();
            System.exit(0);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}

    private static void cleanUp() {
        try {
            executor.shutdownNow();
            GlobalScreen.unregisterNativeHook();
            robot.mouseRelease(BUTTON3_DOWN_MASK);
            robot.keyRelease(KeyEvent.VK_W);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

