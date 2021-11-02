package Pacman.Space.GridElements;

import Pacman.Space.Entities.Entity;

public abstract class GridElement {

    // Attributs

    // GetSet

    // Constructeurs

    // Méthodes
    public abstract void onCrossed(Entity crossingEntity);
    public abstract boolean isCrosseable();

}
