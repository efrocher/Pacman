package Pacman.Space.Entities.PacStates;

import Pacman.Space.Entities.Entity;
import Pacman.Space.Entities.Ghost;
import Pacman.Space.Entities.Pacman;

public class SuperState extends PacState{

    // Constantes
    private static final int DURATION = 10000; // ms

    // Attributs

    // GetSet

    // Constructeurs
    public SuperState(Pacman pacman) {
        super(pacman, DURATION);
    }

    // Méthodes
    @Override
    public void onEntityCollision(Entity otherEntity) {
        if(otherEntity instanceof Ghost)
            otherEntity.die();
    }
    @Override
    public void behave() {
        checkDuration();
        pacman.move(1.5f);
    }
    @Override
    protected void onOutOfDuration() {
        pacman.setState(new RegularState(pacman));
    }

}
