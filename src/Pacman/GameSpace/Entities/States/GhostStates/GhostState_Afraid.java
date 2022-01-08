package Pacman.GameSpace.Entities.States.GhostStates;

import Pacman.GameSpace.Entities.Entity;
import Pacman.GameSpace.Entities.Ghost;
import Pacman.GameSpace.Entities.Pacman;

public class GhostState_Afraid extends GhostState{

    /// --- Constantes --- ///
    private static final int DURATION = -1; // ms

    /// --- Attributs --- ///

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public GhostState_Afraid(Ghost ghost) {
        super(ghost, DURATION);
    }

    /// --- Méthodes --- ///
    @Override
    public void behave(){
        super.behave();
        ghost.move(0.5f);
    }
    @Override
    protected void onOutOfDuration() {
    }
    @Override
    public void onEntityCollision(Entity otherEntity) {
        if(otherEntity instanceof Pacman)
            ghost.die();
    }
    @Override
    public void onStart() {

    }
    @Override
    public void onEnd() {

    }
    @Override
    public int stateId() {
        return GhostState.AFRAID;
    }

}
