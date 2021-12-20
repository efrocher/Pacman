package Pacman.Space.Entities.PacStates;

import Pacman.Space.Entities.Entity;
import Pacman.Space.Entities.Ghost;
import Pacman.Space.Entities.Pacman;

public class RegularState extends PacState {

    // Constantes
    private static final int DURATION = -1;

    // Attributs

    // GetSet

    // Constructeurs
    public RegularState(Pacman pacman) {
        super(pacman, DURATION);
    }

    // Méthodes
    @Override
    public void onEntityCollision(Entity otherEntity) {
        if(otherEntity instanceof Ghost)
            pacman.die();
    }
    @Override
    public void behave() {
        pacman.move(1);
    }
    @Override
    protected void onOutOfDuration() {

    }

}
