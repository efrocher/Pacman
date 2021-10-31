import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputManager implements KeyListener {


    // Attributs
    private static Entity.Direction lastInput;
    private static double lastInputTimestamp;
    private static List<Entity> listeningEntities = new ArrayList<Entity>();

    // GetSet
    public static Entity.Direction getLastInput() {
        return lastInput;
    }
    public static double getLastInputTimestamp() {
        return lastInputTimestamp;
    }
    private static void setLastInput(Entity.Direction input){
        lastInput = input;
        lastInputTimestamp = System.nanoTime();
        for(Entity e : listeningEntities)
            e.notifyNewInput(lastInput);
    }

    // Constructeurs
    public InputManager() { }

    // Méthodes
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_Z)
            setLastInput(Entity.Direction.UP);
        else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
            setLastInput(Entity.Direction.DOWN);
        else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_Q)
            setLastInput(Entity.Direction.LEFT);
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
            setLastInput(Entity.Direction.RIGHT);
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    public static void clearLastInput(){
        lastInput = null;
    }
    public static void subscribe(Entity entity){
        listeningEntities.add(entity);
    }

}
