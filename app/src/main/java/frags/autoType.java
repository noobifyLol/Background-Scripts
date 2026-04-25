package frags;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class autoType implements NativeKeyListener {
    static Robot robot;
    static volatile boolean stopTyping = false;
    static volatile boolean isTyping = false;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new autoType());
            System.out.println("Robot active! Press [END] to type, [ESC] to quit.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void typeString(String str) {
        stopTyping = false;

        for (int i = 1; i > 0; i--) {
            if (stopTyping) return;
            System.out.println("Starting in " + i + "...");
            robot.delay(1000);
        }

        String[] lines = str.split("\n", -1);

        for (int i = 0; i < lines.length; i++) {
            if (stopTyping) return;

            String line = lines[i];

            // Count leading whitespace on this line
            int leadingWS = 0;
            for (char c : line.toCharArray()) {
                if (c == ' ' || c == '\t') leadingWS++;
                else break;
            }


            int startAt = (i == 0) ? 0 : leadingWS;

            for (int ci = startAt; ci < line.length(); ci++) {
                if (stopTyping) return;
                typeCharacter(line.charAt(ci));
                robot.delay(1);
            }

            if (i < lines.length - 1) {


                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                robot.delay(10); 

                robot.keyPress(KeyEvent.VK_HOME);
                robot.keyRelease(KeyEvent.VK_HOME);
                robot.delay(10);


                String nextLine = lines[i + 1];
                for (char c : nextLine.toCharArray()) {
                    if (stopTyping) return;
                    if (c == ' ' || c == '\t') {
                        typeCharacter(c);
                        robot.delay(1);
                    } else {
                        break;
                    }
                }
            }
        }

    }

    private static void typeCharacter(char c) {
        boolean shift = false;
        int keyCode;

        if (Character.isUpperCase(c)) {
            shift = true;
            keyCode = c;
        } else if (Character.isLowerCase(c)) {
            keyCode = Character.toUpperCase(c);
        } else {
            switch (c) {
                case '!': shift = true; keyCode = KeyEvent.VK_1; break;
                case '@': shift = true; keyCode = KeyEvent.VK_2; break;
                case '#': shift = true; keyCode = KeyEvent.VK_3; break;
                case '$': shift = true; keyCode = KeyEvent.VK_4; break;
                case '%': shift = true; keyCode = KeyEvent.VK_5; break;
                case '^': shift = true; keyCode = KeyEvent.VK_6; break;
                case '&': shift = true; keyCode = KeyEvent.VK_7; break;
                case '*': shift = true; keyCode = KeyEvent.VK_8; break;
                case '(': shift = true; keyCode = KeyEvent.VK_9; break;
                case ')': shift = true; keyCode = KeyEvent.VK_0; break;
                case '_': shift = true; keyCode = KeyEvent.VK_MINUS; break;
                case '+': shift = true; keyCode = KeyEvent.VK_EQUALS; break;
                case '{': shift = true; keyCode = KeyEvent.VK_OPEN_BRACKET; break;
                case '}': shift = true; keyCode = KeyEvent.VK_CLOSE_BRACKET; break;
                case '|': shift = true; keyCode = KeyEvent.VK_BACK_SLASH; break;
                case ':': shift = true; keyCode = KeyEvent.VK_SEMICOLON; break;
                case '"': shift = true; keyCode = KeyEvent.VK_QUOTE; break;
                case '<': shift = true; keyCode = KeyEvent.VK_COMMA; break;
                case '>': shift = true; keyCode = KeyEvent.VK_PERIOD; break;
                case '?': shift = true; keyCode = KeyEvent.VK_SLASH; break;
                case '~': shift = true; keyCode = KeyEvent.VK_BACK_QUOTE; break;
                case '\n': keyCode = KeyEvent.VK_ENTER; break;
                case '\t': keyCode = KeyEvent.VK_TAB; break;
                case ' ':  keyCode = KeyEvent.VK_SPACE; break;
                case '=':  keyCode = KeyEvent.VK_EQUALS; break;
                case '\'': keyCode = KeyEvent.VK_QUOTE; break;
                case '.':  keyCode = KeyEvent.VK_PERIOD; break;
                case ',':  keyCode = KeyEvent.VK_COMMA; break;
                case '-':  keyCode = KeyEvent.VK_MINUS; break;
                case '/':  keyCode = KeyEvent.VK_SLASH; break;
                case '\\': keyCode = KeyEvent.VK_BACK_SLASH; break;
                case '[':  keyCode = KeyEvent.VK_OPEN_BRACKET; break;
                case ']':  keyCode = KeyEvent.VK_CLOSE_BRACKET; break;
                case ';':  keyCode = KeyEvent.VK_SEMICOLON; break;
                case '`':  keyCode = KeyEvent.VK_BACK_QUOTE; break;
                case '0':  keyCode = KeyEvent.VK_0; break;
                case '1':  keyCode = KeyEvent.VK_1; break;
                case '2':  keyCode = KeyEvent.VK_2; break;
                case '3':  keyCode = KeyEvent.VK_3; break;
                case '4':  keyCode = KeyEvent.VK_4; break;
                case '5':  keyCode = KeyEvent.VK_5; break;
                case '6':  keyCode = KeyEvent.VK_6; break;
                case '7':  keyCode = KeyEvent.VK_7; break;
                case '8':  keyCode = KeyEvent.VK_8; break;
                case '9':  keyCode = KeyEvent.VK_9; break;
                default:   keyCode = Character.toUpperCase(c); break;
            }
        }

        if (shift) robot.keyPress(KeyEvent.VK_SHIFT);
        try {
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        } catch (Exception e) { /* skip unmappable chars */ }
        if (shift) robot.keyRelease(KeyEvent.VK_SHIFT);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_END) {
            if (!isTyping) {
                isTyping = true;

                String codeToType = """
# --- 3. THE "LID" (Move this to the bottom of the drawing section) ---
fx, fy = iso(0, 0, 0)

# We use 2.7 to fill the front as you found, but we match the 
# height to your isoY math (0.5) to keep it from looking distorted.
# Increasing borderWidth hides the "bristly" edges of the walls.
Oval(fx, fy, radius * 2.7, radius * 2.7 * 0.5, fill='white', border='black', borderWidth=5)

# Center pin
Oval(fx, fy, 8, 8 * 0.5, fill='black')
""";

                new Thread(() -> {
                    typeString(codeToType);
                    isTyping = false;
                }).start();
            }
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            System.out.println("EMERGENCY STOP!");
            stopTyping = true;
            isTyping = false;
            System.exit(0);
        }
    }

    @Override public void nativeKeyReleased(NativeKeyEvent e) {}
    @Override public void nativeKeyTyped(NativeKeyEvent e) {}
}