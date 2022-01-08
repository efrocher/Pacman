package Pacman.GameSpace.Entities.States.PacStates;

import Pacman.GameSpace.Entities.Entity;
import Pacman.GameSpace.Entities.States.EntityState;
import Pacman.GameSpace.Entities.Pacman;

// Classe abstraite représentant l'état de pacman
public abstract class PacState extends EntityState {

    /// --- Constantes --- ///
    public static final int REGULAR = 0;
    public static final int WAITING = 1;
    public static final int SNEAKY = 2;
    public static final int SUPER = 3;

    /// --- Attributs --- ///
    protected final Pacman pacman;

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public PacState(Pacman pacman, int duration){
        super(duration);
        this.pacman = pacman;
    }

    /// --- Méthodes --- ///
    @Override
    public abstract int stateId();
    // Méthode appelée quand une direction est préssée
    public void onNewDirectionInput(Entity.Direction newDirection){
        pacman.tryChangeDirection(newDirection);
    }

}
