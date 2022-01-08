package Pacman.Inputs;

import Pacman.GameSpace.Entities.Entity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

// Classe gérant les inputs du joueur
public class InputManager implements KeyListener {


    /// --- Attributs --- ///
    private static Entity.Direction lastDirectionInput;
    private static double lastDirectionInputTimestamp;
    private static List<InputObserver> inputObservers = new ArrayList<InputObserver>();

    /// --- GetSet --- ///
    public static Entity.Direction getLastDirectionInput() {
        return lastDirectionInput;
    }
    public static double getLastDirectionInputTimestamp() {
        return lastDirectionInputTimestamp;
    }
    private static void setLastDirectionInput(Entity.Direction input){
        lastDirectionInput = input;
        lastDirectionInputTimestamp = System.nanoTime();
        for(InputObserver o : inputObservers)
            o.onNewDirectionInput(lastDirectionInput);
    }

    /// --- Constructeurs --- ///
    public InputManager() { }

    /// --- Méthodes --- ///
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()){
            case KeyEvent.VK_UP, KeyEvent.VK_Z      -> setLastDirectionInput(Entity.Direction.UP);
            case KeyEvent.VK_DOWN, KeyEvent.VK_S    -> setLastDirectionInput(Entity.Direction.DOWN);
            case KeyEvent.VK_LEFT, KeyEvent.VK_Q    -> setLastDirectionInput(Entity.Direction.LEFT);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D   -> setLastDirectionInput(Entity.Direction.RIGHT);
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    public static void clearLastDirectionInput(){
        lastDirectionInput = null;
    }
    public static void subscribe(InputObserver observer){
        inputObservers.add(observer);
    }

}
