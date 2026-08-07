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

                // Triple-quoted text block: this must be a straight JS/Java string literal, no Java escaping.
                String codeToType = """
app.background = gradient('darkSlateGray', 'black', start='top')
CORRECT_PASSWORD = "OPEN123"
app.isLocked = True

# Simplified UI
Label('VAULT HUB', 200, 40, size=16, font='monospace', fill='lightGray', bold=True)
statusBox = Rect(100, 75, 200, 35, fill=None, border='red', borderWidth=2)
statusText = Label('LOCKED', 200, 92, size=14, font='monospace', fill='red', bold=True)

# Compact Vault Door Shapes
door = Circle(200, 220, 60, fill='gray', border='black', borderWidth=3)
boltL = Rect(100, 212, 40, 16, fill='silver', border='black')
boltR = Rect(260, 212, 40, 16, fill='silver', border='black')
wheel = Circle(200, 220, 25, fill='lightGray', border='black')
spokeV = Line(200, 195, 200, 245, fill='black', lineWidth=6)
spokeH = Line(175, 220, 225, 220, fill='black', lineWidth=6)

# Consolidated Reset Button
btn = Rect(140, 340, 120, 30, fill='darkRed', border='black', roundness=5)
Label('RELOCK', 200, 355, size=12, font='monospace', fill='white', bold=True)

def updateUI(locked, text, color, boxColor, boltLX, boltRX, angle):
    app.isLocked = locked
    statusText.value = text
    statusText.fill = color
    statusBox.border = boxColor
    boltL.x = boltLX
    boltR.x = boltRX
    spokeV.rotateAngle = angle
    spokeH.rotateAngle = angle

def onMousePress(mx, my):
    if door.hits(mx, my) and app.isLocked:
        attempt = app.getTextInput("ENTER PASSWORD:")
        if attempt == CORRECT_PASSWORD:
            # Unlock state: shift bolts inward, rotate spokes 45 deg, update colors
            updateUI(False, 'UNLOCKED', 'lime', 'lime', 125, 235, 45)
        elif attempt is not None:
            statusText.value = 'DENIED'
            statusText.fill = 'orange'
            statusBox.border = 'orange'
            
    elif btn.hits(mx, my) and not app.isLocked:
        # Lock state: reset bolts outward, reset rotation, restore red state
        updateUI(True, 'LOCKED', 'red', 'red', 100, 260, 0)
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