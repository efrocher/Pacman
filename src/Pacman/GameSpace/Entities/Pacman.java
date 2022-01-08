package Pacman.GameSpace.Entities;

import Pacman.GameSpace.Entities.States.PacStates.*;
import Pacman.GameSpace.GameSpace;
import Pacman.Inputs.InputManager;
import Pacman.View.InputObserver;
import Pacman.View.SpaceObserver;

public class Pacman extends Entity implements InputObserver {

    /// --- Constantes --- ///
    private static final float BASE_SPEED = 80f; // px / seconde
    private static final int HIT_RADIUS = 6; // px
    private static final int BASE_LIVES = 3;

    /// --- Attributs --- ///
    private int lives;
    private PacState state;

    /// --- GetSet --- ///
    public int getLives() {
        return lives;
    }
    public int getState() {
        return state.stateId();
    }
    public void setState(int stateId) {

        state.onEnd();
        state = switch (stateId){
            case PacState.REGULAR   -> new PacState_Regular(this);
            case PacState.WAITING   -> new PacState_Waiting(this);
            case PacState.SNEAKY    -> new PacState_Sneaky(this);
            case PacState.SUPER     -> new PacState_Super(this);
            default -> throw new IllegalStateException("No such state");
        };
        state.onStart();
        notifyPacStateChanged();

    }

    /// --- Constructeurs --- ///
    public Pacman(float xPos, float yPos, GameSpace space, Direction direction){
        super(xPos, yPos, space, direction, BASE_SPEED, HIT_RADIUS);
        InputManager.subscribe(this);
        lives = BASE_LIVES;
        state = new PacState_Waiting(this);
    }

    /// --- Méthodes --- ///
    public void behave(){
        state.behave();
    }
    @Override
    protected void onRoadBlock() {

    }
    @Override
    protected boolean onCrossroad() {

        super.onCrossroad();

        Direction newDirection = InputManager.getLastInput();
        if(newDirection != null && newDirection != getDirection() && ((System.nanoTime() - InputManager.getLastInputTimestamp()) / 1e6) < 500){
            float[] newCrossroad = findNextCrossroad(nextCrossroad, newDirection);
            if(space.tileCrossable(GameSpace.positionToTileCoord(newCrossroad))){
                setDirection(newDirection);
                setPosition(nextCrossroad);
                nextCrossroad = newCrossroad;
                InputManager.clearLastInput();
                return false;
            }
        }
        return true;
    }
    @Override
    protected void onEntityCollision(Entity otherEntity) {
        state.onEntityCollision(otherEntity);
    }
    @Override
    public void onNewDirectionInput(Direction newDirection) {
        state.onNewDirectionInput(newDirection);
    }
    // Changement de direction (demi-tour uniquement)
    public void tryChangeDirection(Direction newDirection){

        if(newDirection != null && newDirection.ordinal() == (getDirection().ordinal() + 2) % 4){
            float[] newCrossroad = findNextCrossroad(getPosition(), newDirection);
            if(space.tileCrossable(GameSpace.positionToTileCoord(newCrossroad))){
                setDirection(newDirection);
                nextCrossroad = newCrossroad;
                InputManager.clearLastInput();
            }
        }

    }
    @Override
    public void die(){
        lives--;
        relocate(GameSpace.tileCoordToPosition(GameSpace.SPAWN_PACMAN), Direction.RIGHT);
        notifyLivesChanged();
        state = new PacState_Waiting(this);
    }
    public void addLife(int lives){
        this.lives += lives;
        notifyLivesChanged();
    }
    private void notifyLivesChanged(){
        for(SpaceObserver o : space.getObservers())
            o.onLivesChanged(lives);
    }
    private void notifyPacStateChanged(){
        for(SpaceObserver o : space.getObservers())
            o.onPacStateChanged(state);
    }
}
