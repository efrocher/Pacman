package Pacman.GameSpace.GridElements;

import Pacman.GameSpace.Entities.Entity;

public class Wall extends GridElement{

    /// --- Attributs --- ///

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///

    /// --- Méthodes --- ///
    @Override
    public void onCrossed(Entity crossingEntity) {

    }

    @Override
    public boolean isCrosseable() {
        return false;
    }
}
