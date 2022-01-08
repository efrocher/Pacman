package Pacman.GameSpace.GridElements.Gums;

import Pacman.GameSpace.Entities.States.PacStates.PacState;
import Pacman.GameSpace.Entities.States.PacStates.PacState_Sneaky;
import Pacman.GameSpace.GameSpace;

public class SneakyGum extends Gum{

    /// --- Constantes --- ///
    private final static int SCORE_BONUS = 300;

    /// --- Attributs --- ///

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public SneakyGum(int[] tileCoord, GameSpace space) {
        super(tileCoord, space, SCORE_BONUS);
    }

    /// --- Méthodes --- ///
    @Override
    protected void releaseBonus() {

        super.releaseBonus();

        space.getPacman().setState(PacState.SNEAKY);

    }

}
