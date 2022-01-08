package Pacman.GameSpace.Entities.States.PacStates;

import Pacman.GameSpace.Entities.Entity;
import Pacman.GameSpace.Entities.Ghost;
import Pacman.GameSpace.Entities.Pacman;
import Pacman.GameSpace.Entities.States.GhostStates.GhostState;

public class PacState_Super extends PacState{

    /// --- Constantes --- ///
    private static final int DURATION = 10000; // ms

    /// --- Attributs --- ///

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public PacState_Super(Pacman pacman) {
        super(pacman, DURATION);
    }

    /// --- Méthodes --- ///
    @Override
    public int stateId() {
        return PacState.SUPER;
    }
    @Override
    public void onEntityCollision(Entity otherEntity) {
        // Immune
    }
    @Override
    public void onStart() {

        // Apeurement des fantômes
        for(Ghost g : pacman.getSpace().getGhosts())
            g.setState(GhostState.AFRAID);

    }
    @Override
    public void onEnd() {

        // Retour à l'état normal des fantômes
        for(Ghost g : pacman.getSpace().getGhosts())
            g.setState(GhostState.REGULAR);

    }
    @Override
    public void behave() {
        super.behave();
        pacman.move(1);//1.5f); plus amusant avec un multiplicateur de vitesse mais c'est pas la consigne
    }
    @Override
    protected void onOutOfDuration() {

        // Retour à l'état normal
        pacman.setState(PacState.REGULAR);

    }

}
