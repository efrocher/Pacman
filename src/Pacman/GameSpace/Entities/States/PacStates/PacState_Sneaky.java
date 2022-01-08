package Pacman.GameSpace.Entities.States.PacStates;

import Pacman.GameSpace.Entities.Entity;
import Pacman.GameSpace.Entities.Pacman;

public class PacState_Sneaky extends PacState{

    /// --- Constantes --- ///
    private static final int DURATION = 10000; // ms

    /// --- Attributs --- ///

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public PacState_Sneaky(Pacman pacman) {
        super(pacman, DURATION);
    }

    /// --- Méthodes --- ///
    @Override
    public int stateId() {
        return PacState.SNEAKY;
    }
    @Override
    public void onEntityCollision(Entity otherEntity) {
        // Invisible
    }
    @Override
    public void onStart() {

    }
    @Override
    public void onEnd() {

    }
    @Override
    public void behave() {
        super.behave();
        pacman.move(1);
    }
    @Override
    protected void onOutOfDuration() {
        pacman.setState(PacState.REGULAR);
    }

}
