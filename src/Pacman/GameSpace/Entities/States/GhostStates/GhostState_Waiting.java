package Pacman.GameSpace.Entities.States.GhostStates;

import Pacman.GameSpace.Entities.Entity;
import Pacman.GameSpace.Entities.Ghost;

public class GhostState_Waiting extends GhostState{

    /// --- Constantes --- ///
    private static final int DURATION = -1; // ms

    /// --- Attributs --- ///

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public GhostState_Waiting(Ghost ghost) {
        super(ghost, DURATION);
    }

    /// --- Méthodes --- ///
    @Override
    public void behave(){
        super.behave();
    }
    @Override
    protected void onOutOfDuration() {

    }
    @Override
    public void onEntityCollision(Entity otherEntity) {
        // Immune
    }
    @Override
    public void onStart() {

    }
    @Override
    public void onEnd() {

    }
    @Override
    public int stateId() {
        return GhostState.WAITING;
    }

}
