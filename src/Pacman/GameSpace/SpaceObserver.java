package Pacman.GameSpace;

import Pacman.GameSpace.Entities.States.PacStates.PacState;

// Interface implémentée par les objets souhaitant être notifiés des évolution de l'espace de jeu
public interface SpaceObserver {

    /// --- Méthodes --- ///
    void onGameStarted();
    void onPacStateChanged(PacState state);
    void onLivesChanged(int newLives);
    void onScoreChanged(int newScore);

}
