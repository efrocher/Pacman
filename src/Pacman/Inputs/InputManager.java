package Pacman.Inputs;

import Pacman.GameSpace.Entities.Entity;
import Pacman.View.InputObserver;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputManager implements KeyListener {


    /// --- Attributs --- ///
    private static Entity.Direction lastInput;
    private static double lastInputTimestamp;
    private static List<InputObserver> inputObservers = new ArrayList<InputObserver>();

    /// --- GetSet --- ///
    public static Entity.Direction getLastInput() {
        return lastInput;
    }
    public static double getLastInputTimestamp() {
        return lastInputTimestamp;
    }
    private static void setLastInput(Entity.Direction input){
        lastInput = input;
        lastInputTimestamp = System.nanoTime();
        for(InputObserver o : inputObservers)
            o.onNewDirectionInput(lastInput);
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
            case KeyEvent.VK_UP, KeyEvent.VK_Z      -> setLastInput(Entity.Direction.UP);
            case KeyEvent.VK_DOWN, KeyEvent.VK_S    -> setLastInput(Entity.Direction.DOWN);
            case KeyEvent.VK_LEFT, KeyEvent.VK_Q    -> setLastInput(Entity.Direction.LEFT);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D   -> setLastInput(Entity.Direction.RIGHT);
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    public static void clearLastInput(){
        lastInput = null;
    }
    public static void subscribe(InputObserver observer){
        inputObservers.add(observer);
    }

}
