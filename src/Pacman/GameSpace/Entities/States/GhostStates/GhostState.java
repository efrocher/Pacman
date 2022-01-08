package Pacman.GameSpace.Entities.States.GhostStates;

import Pacman.GameSpace.Entities.Ghost;
import Pacman.GameSpace.Entities.States.EntityState;

// Classe abstraite représentant l'état un fantôme
public abstract class GhostState extends EntityState {

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

}
