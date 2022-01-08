package Pacman.GameSpace.GridElements;

import Pacman.GameSpace.Entities.Entity;

public abstract class GridElement {

    /// --- Attributs --- ///

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///

    /// --- Méthodes --- ///
    public abstract void onCrossed(Entity crossingEntity);
    public abstract boolean isCrosseable();

}
