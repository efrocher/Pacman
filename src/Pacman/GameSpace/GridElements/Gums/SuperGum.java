package Pacman.GameSpace.GridElements.Gums;


import Pacman.GameSpace.Entities.States.PacStates.PacState;
import Pacman.GameSpace.Entities.States.PacStates.PacState_Super;
import Pacman.GameSpace.GameSpace;

public class SuperGum extends Gum{

    /// --- Constantes --- ///
    private final static int SCORE_BONUS = 300;

    /// --- Attributs --- ///

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public SuperGum(int[] tileCoord, GameSpace space) {
        super(tileCoord, space, SCORE_BONUS);
    }

    /// --- Méthodes --- ///
    @Override
    protected void releaseBonus() {

        super.releaseBonus();

        space.getPacman().setState(PacState.SUPER);

    }

}
