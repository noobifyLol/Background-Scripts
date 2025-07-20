package frags;

import java.awt.AWTException;
import java.awt.Robot;
import static java.awt.event.InputEvent.BUTTON3_DOWN_MASK;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class move implements NativeKeyListener {
    public static AtomicBoolean running = new AtomicBoolean(true);
    public static Robot robot;
    public static ScheduledExecutorService executor;
    public static int i = 960; 

    public static void main(String[] args) throws AWTException, NativeHookException {
        robot = new Robot();
        executor = Executors.newScheduledThreadPool(2);

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new move());

            Thread.sleep(5000); // Initial delay for setup

            
            executor.execute(() -> {
                robot.keyPress(KeyEvent.VK_W);
                while (running.get()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                robot.keyRelease(KeyEvent.VK_W);
            });

            
            executor.execute(() -> {
                robot.mousePress(BUTTON3_DOWN_MASK);
                executor.scheduleAtFixedRate(() -> {
                    if (running.get()) {
                        i += 8; 
                        robot.mouseMove(i, 540);
                    } else {
                        robot.mouseRelease(BUTTON3_DOWN_MASK);
                    }
                }, 0, 28, TimeUnit.MILLISECONDS);
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
    public void nativeKeyPressed(NativeKeyEvent e) {
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