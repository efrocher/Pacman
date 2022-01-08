package Pacman.GameSpace.Entities.States.GhostStates;

import Pacman.GameSpace.Entities.Ghost;
import Pacman.GameSpace.Entities.States.EntitySate;

public abstract class GhostState extends EntitySate {

    /// --- Constantes --- ///
    public static final int REGULAR = 0;
    public static final int WAITING = 1;
    public static final int AFRAID = 2;

    /// --- Attributs --- ///
    protected final Ghost ghost;

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public GhostState(Ghost ghost, int duration){
        super(duration);
        this.ghost = ghost;
    }

    /// --- Méthodes --- ///
    @Override
    public void behave(){
        super.behave();
    }

}
