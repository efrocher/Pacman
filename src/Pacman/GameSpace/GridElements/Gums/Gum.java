package Pacman.GameSpace.GridElements.Gums;

import Pacman.GameSpace.Entities.Entity;
import Pacman.GameSpace.Entities.Pacman;
import Pacman.GameSpace.GameSpace;
import Pacman.GameSpace.GridElements.GridElement;

public abstract class Gum extends GridElement {

    /// --- Attributs --- ///
    protected final GameSpace space;
    private final int[] tileCoord;
    private final int scoreBonus;

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public Gum(int[] tileCoord, GameSpace space) {
        super();
        this.tileCoord = tileCoord;
        this.space = space;
        scoreBonus = 0;
    }
    public Gum(int[] tileCoord, GameSpace space, int scoreBonus) {
        super();
        this.tileCoord = tileCoord;
        this.space = space;
        this.scoreBonus = scoreBonus;
    }

    /// --- Méthodes --- ///
    @Override
    public void onCrossed(Entity crossingEntity) {

        if(crossingEntity instanceof Pacman){
            releaseBonus();
            space.removeGumAtCoordinate(tileCoord);
        }

    }
    @Override
    public boolean isCrosseable() {
        return true;
    }
    protected void releaseBonus(){
        space.addPoints(scoreBonus);
    }

}
