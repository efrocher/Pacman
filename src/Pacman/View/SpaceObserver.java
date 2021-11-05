package Pacman.View;

import Pacman.Space.Entities.PacStates.PacState;

public interface SpaceObserver {

    // Méthodes
    void onPacStateChanged(PacState state);
    void onLivesChanged(int newLives);
    void onScoreChanged(int newScore);

}
