package Pacman.GameSpace.Entities.States.PacStates;

import Pacman.GameSpace.Entities.Entity;
import Pacman.GameSpace.Entities.Ghost;
import Pacman.GameSpace.Entities.Pacman;

public class PacState_Regular extends PacState {

    /// --- Constantes --- ///
    private static final int DURATION = -1; // infini

    /// --- Attributs --- ///

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public PacState_Regular(Pacman pacman) {
        super(pacman, DURATION);
    }

    /// --- Méthodes --- ///
    @Override
    public int stateId() {
        return PacState.REGULAR;
    }
    @Override
    public void onEntityCollision(Entity otherEntity) {
        if(otherEntity instanceof Ghost)
            pacman.die();
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

    }

}
