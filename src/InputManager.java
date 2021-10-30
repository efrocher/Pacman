import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class InputManager implements KeyListener {

    // Attributs
    private static volatile boolean upPressed = false;
    private static volatile boolean downPressed = false;
    private static volatile boolean leftPressed = false;
    private static volatile boolean rightPressed = false;

    // GetSet
    public static boolean isUpPressed() {
        return upPressed;
    }
    public static boolean isDownPressed() {
        return downPressed;
    }
    public static boolean isLeftPressed() {
        return leftPressed;
    }
    public static boolean isRightPressed() {
        return rightPressed;
    }

    // Constructeurs
    public InputManager() { }

    // Méthodes
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            upPressed = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            upPressed = false;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
    }

}
