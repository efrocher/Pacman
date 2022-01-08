package Pacman.View;

import Pacman.GameSpace.Entities.Entity;

public interface InputObserver {

    /// --- Méthodes --- ///
    void onNewDirectionInput(Entity.Direction input);

}
