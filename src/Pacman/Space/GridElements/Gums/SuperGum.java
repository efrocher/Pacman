package Pacman.Space.GridElements.Gums;


import Pacman.Space.Entities.PacStates.SuperState;
import Pacman.Space.GameSpace;

public class SuperGum extends Gum{

    // Constantes
    private final static int SCORE_BONUS = 300;

    // Attributs

    // GetSet

    // Constructeurs
    public SuperGum(int[] tileCoord, GameSpace space) {
        super(tileCoord, space, SCORE_BONUS);
    }

    // Méthodes
    @Override
    protected void releaseBonus() {

        super.releaseBonus();

        space.getPacman().setState(new SuperState(space.getPacman()));

    }

}
