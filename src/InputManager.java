import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputManager implements KeyListener {


    // Attributs
    private static BehavingEntity.Direction lastInput;
    private static double lastInputTimestamp;
    private static List<BehavingEntity> listeningEntities = new ArrayList<BehavingEntity>();

    // GetSet
    public static BehavingEntity.Direction getLastInput() {
        return lastInput;
    }
    public static double getLastInputTimestamp() {
        return lastInputTimestamp;
    }
    private static void setLastInput(BehavingEntity.Direction input){
        lastInput = input;
        lastInputTimestamp = System.nanoTime();
        for(BehavingEntity e : listeningEntities)
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
            setLastInput(BehavingEntity.Direction.UP);
        else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
            setLastInput(BehavingEntity.Direction.DOWN);
        else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_Q)
            setLastInput(BehavingEntity.Direction.LEFT);
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
            setLastInput(BehavingEntity.Direction.RIGHT);
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    public static void clearLastInput(){
        lastInput = null;
    }
    public static void subscribe(BehavingEntity entity){
        listeningEntities.add(entity);
    }

}
