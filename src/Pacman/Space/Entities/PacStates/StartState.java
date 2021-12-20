package Pacman.Space.Entities.PacStates;

import Pacman.Space.Entities.Entity;
import Pacman.Space.Entities.Pacman;

public class StartState extends PacState{

    // Attributs
    private static final int DURATION = -1;

    // GetSet

    // Constructeurs
    public StartState(Pacman pacman) {
        super(pacman, DURATION);
    }

    // Méthodes
    @Override
    public void onEntityCollision(Entity otherEntity) {
        // Undying
    }
    @Override
    public void behave() {

    }
    @Override
    protected void onOutOfDuration() {

    }

}
