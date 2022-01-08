package Pacman.View;

import Pacman.GameSpace.Entities.States.PacStates.PacState;

public interface SpaceObserver {

    /// --- Méthodes --- ///
    void onGameStarted();
    void onPacStateChanged(PacState state);
    void onLivesChanged(int newLives);
    void onScoreChanged(int newScore);

}
