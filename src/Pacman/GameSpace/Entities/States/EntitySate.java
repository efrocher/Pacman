package Pacman.GameSpace.Entities.States;

import Pacman.GameSpace.Entities.Entity;

public abstract class EntitySate {

    /// --- Attributs --- ///
    private final int duration; // ms, -1 pour infini
    private final double startTimeStamp; // System.nanoTime()

    /// --- GetSet --- ///

    //// --- Constructeurs --- /// ///
    public EntitySate(int duration) {
        this.duration = duration;
        this.startTimeStamp = System.nanoTime();
    }

    /// --- Méthodes --- ///
    public void behave(){
        if(duration > -1 && (System.nanoTime() - startTimeStamp) / 1e6 > duration)
            onOutOfDuration();
    }
    // Méthode appelée à l'épuisement de la durée d'un état
    protected abstract void onOutOfDuration();
    // Méthode appelée lors de la collision de l'entitée avec un autre
    public abstract void onEntityCollision(Entity otherEntity);
    // Méthode appelée après le changement d'état
    public abstract void onStart();
    // Méthode appelée après le changement d'état
    public abstract void onEnd();
    // Retourne l'id de l'état
    public abstract int stateId();

}
