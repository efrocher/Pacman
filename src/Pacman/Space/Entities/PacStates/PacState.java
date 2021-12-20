package Pacman.Space.Entities.PacStates;

import Pacman.Space.Entities.Entity;
import Pacman.Space.Entities.Ghost;
import Pacman.Space.Entities.Pacman;

public abstract class PacState {

    // Attributs
    protected Pacman pacman;
    protected int duration; // ms, -1 pour infini
    protected double startTimeStamp; // System.nanoTime()

    // GetSet

    // Constructeurs
    public PacState(Pacman pacman, int duration){
        this.pacman = pacman;
        this.duration = duration;
        this.startTimeStamp = System.nanoTime();
    }

    // Méthodes
    public void checkDuration(){
        if(duration > -1 && (System.nanoTime() - startTimeStamp) / 1e6 > duration)
            onOutOfDuration();
    }
    public abstract void onEntityCollision(Entity otherEntity);
    public abstract void behave();
    protected abstract void onOutOfDuration();

}
