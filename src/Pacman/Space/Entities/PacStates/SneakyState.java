package Pacman.Space.Entities.PacStates;

import Pacman.Space.Entities.Entity;
import Pacman.Space.Entities.Pacman;

public class SneakyState extends PacState{

    // Constantes
    private static final int DURATION = 10000; // ms

    // Attributs

    // GetSet

    // Constructeurs
    public SneakyState(Pacman pacman) {
        super(pacman, DURATION);
    }

    // Méthodes
    @Override
    public void onEntityCollision(Entity otherEntity) {

    }
    @Override
    public void behave() {
        checkDuration();
        pacman.move(1);
    }
    @Override
    protected void onOutOfDuration() {
        pacman.setState(new RegularState(pacman));
    }

}
