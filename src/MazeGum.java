public class MazeGum extends Gum{

    // Constantes
    private final static int SCORE_BONUS = 1000;

    // Attributs

    // GetSet

    // Constructeurs
    public MazeGum(int[] tileCoord, GameSpace space) {
        super(tileCoord, space, SCORE_BONUS);
    }

    // Méthodes
    @Override
    protected void releaseBonus() {
        super.releaseBonus();
        space.swapGates();
    }

}
