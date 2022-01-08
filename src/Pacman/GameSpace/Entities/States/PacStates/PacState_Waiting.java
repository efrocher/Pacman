package Pacman.GameSpace.Entities.States.PacStates;

import Pacman.GameSpace.Entities.Entity;
import Pacman.GameSpace.Entities.Pacman;

public class PacState_Waiting extends PacState{

    /// --- Attributs --- ///
    private static final int DURATION = -1; // ms

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public PacState_Waiting(Pacman pacman) {
        super(pacman, DURATION);
    }

    /// --- Méthodes --- ///
    @Override
    public int stateId() {
        return PacState.WAITING;
    }
    @Override
    public void onEntityCollision(Entity otherEntity) {
        // Undying
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
    }
    @Override
    protected void onOutOfDuration() {

    }
    @Override
    public void onNewDirectionInput(Entity.Direction newDirection) {

        super.onNewDirectionInput(newDirection);

        // Changement d'état (départ)
        pacman.setState(PacState.REGULAR);

        // Annoncement que la partie commence
        pacman.getSpace().startGame();

    }

}
