package Pacman.View;

import Pacman.Space.Entities.Entity;

public interface InputObserver {

    // Méthodes
    void onNewInput(Entity.Direction input);

}
