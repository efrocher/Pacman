import java.util.ArrayList;
import java.util.List;

public class GameController {

    // Constantes
    public static final int TARGET_TICK_TIME = 20; // ms
    public static final float DELTA = TARGET_TICK_TIME / 1000f;

    // Attributs
    private GameSpace space;
    private GameView view;
    private double timeStamp;

    // GetSet

    // Constructeurs
    public GameController(GameSpace gameSpace, GameView view){
        this.space = gameSpace;
        this.view = view;
    }

    // Méthodes
    public void run() throws InterruptedException {

        timeStamp = System.nanoTime() / 1e6;
        while(space.getGumAmount() > 0 && space.getPacman().getLives() > 0){

            // Refresh frame
            view.repaint();

            // Logique
            while(System.nanoTime() / 1e6 > timeStamp + TARGET_TICK_TIME){

                // Récupération des entitées
                List<Entity> entities = new ArrayList<Entity>();
                entities.add((space.getPacman()));
                for(Entity e : space.getGhosts())
                    entities.add(e);

                // Comportement régulier des entités
                for(Entity e : entities)
                    e.behave();

                // Collision des entités
                int nbEntities = entities.size();
                for(int i = 0; i < nbEntities; i++)
                    for(int j = i + 1; j < nbEntities; j++)
                        Entity.checkCollision(entities.get(i), entities.get(j));

                // Avancement du timeStamp
                timeStamp += TARGET_TICK_TIME;

            }
        }
    }

}
