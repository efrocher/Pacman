package Pacman.Inputs;

import Pacman.GameSpace.Entities.Entity;

// Interface implémentée par les objets souhaitant être notifiés des inputs
public interface InputObserver {

    /// --- Méthodes --- ///
    void onNewDirectionInput(Entity.Direction input);

}
