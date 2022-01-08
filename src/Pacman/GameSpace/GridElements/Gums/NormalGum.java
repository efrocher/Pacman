package Pacman.GameSpace.GridElements.Gums;

import Pacman.GameSpace.GameSpace;

public class NormalGum extends Gum{

    /// --- Constantes --- ///
    private final static int SCORE_BONUS = 100;

    /// --- Attributs --- ///

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public NormalGum(int[] tileCoord, GameSpace space) {
        super(tileCoord, space, SCORE_BONUS);
    }

    /// --- Méthodes --- ///
    @Override
    protected void releaseBonus() {
        super.releaseBonus();
    }

}
