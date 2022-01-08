package Pacman.Controller;

import Pacman.GameSpace.Entities.Entity;
import Pacman.GameSpace.GameSpace;
import Pacman.View.GameView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// Classe gérant le lacement, le déroulement et la fin de la partie
// Ne se charge d'appeler que les évènements logiques récurents
public class GameController {

    /// --- Constantes --- ///
    public static final int TARGET_TICK_TIME = 20; // ms
    public static final float DELTA = TARGET_TICK_TIME / 1000f;

    /// --- Attributs --- ///
    private GameSpace space;
    private GameView view;

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public GameController(GameSpace gameSpace, GameView view){
        this.space = gameSpace;
        this.view = view;
    }

    /// --- Méthodes --- ///
    // Boucle déclenchant la logique du jeu
    public void run() throws InterruptedException {

        // Déroulement du jeu
        double timeStamp = System.nanoTime() / 1e6;
        while(space.getGumAmount() > 0 && space.getPacman().getLives() > 0){

            // Refresh frame
            view.repaint();
            Thread.sleep(TARGET_TICK_TIME / 5); // Eco CPU

            // Logique
            while(System.nanoTime() / 1e6 > timeStamp + TARGET_TICK_TIME){

                // Récupération des entitées
                List<Entity> entities = new ArrayList<Entity>();
                entities.add((space.getPacman()));
                entities.addAll(space.getGhosts());

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

        // Fin
        view.repaint();
        String endMessage;
        if(space.getPacman().getLives() <= 0)
            endMessage = "Défaite...";
        else
            endMessage = "Victoire !";

        JOptionPane.showMessageDialog(null, endMessage);
        System.exit(0);

    }

}
