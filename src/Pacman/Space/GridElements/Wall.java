package Pacman.Space.GridElements;

import Pacman.Space.Entities.Entity;

public class Wall extends GridElement{

    // Attributs

    // GetSet

    // Constructeurs

    // Méthodes
    @Override
    public void onCrossed(Entity crossingEntity) {

    }

    @Override
    public boolean isCrosseable() {
        return false;
    }
}
