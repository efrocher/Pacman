package Pacman.Space.Entities;

import Pacman.Space.Entities.PacStates.PacState;
import Pacman.Space.Entities.PacStates.RegularState;
import Pacman.Space.Entities.PacStates.StartState;
import Pacman.Space.GameSpace;
import Pacman.View.InputManager;
import Pacman.View.InputObserver;
import Pacman.View.SpaceObserver;

public class Pacman extends Entity implements InputObserver {

    // Constantes
    private static final float BASE_SPEED = 80f; // px / seconde
    private static final int HIT_RADIUS = 6; // px

    // Attributs
    private int lives;
    private PacState state;

    // GetSet
    public int getLives() {
        return lives;
    }
    public PacState getState() {
        return state;
    }
    public void setState(PacState state) {
        this.state = state;
        notifyStateChanged();
    }

    // Constructeurs
    public Pacman(float xPos, float yPos, GameSpace space, Direction direction){
        super(xPos, yPos, space, direction, BASE_SPEED, HIT_RADIUS);
        InputManager.subscribe(this);
        lives = 3;
        state = new StartState(this);
    }

    // Méthodes
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
        if(newDirection != null && newDirection != direction && ((System.nanoTime() - InputManager.getLastInputTimestamp()) / 1e6) < 500){
            float[] newCrossroad = findNextCrossroad(nextCrossroad, newDirection);
            if(space.tileCrossable(GameSpace.positionToTileCoord(newCrossroad))){
                setDirection(newDirection);
                setPositionX(nextCrossroad[0]);
                setPositionY(nextCrossroad[1]);
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
    public void onNewInput(Direction newDirection) {

        // Départ de pacman si en état de départ
        if(state instanceof StartState){
            state = new RegularState(this);
            space.setPacmanStartedMoving(true);
        }

        // Changement de direction
        if(newDirection != null && newDirection.ordinal() == (direction.ordinal() + 2) % 4){
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
        state = new StartState(this);
    }
    public void addLife(int lives){
        this.lives += lives;
        notifyLivesChanged();
    }
    private void notifyLivesChanged(){
        for(SpaceObserver o : space.getObservers())
            o.onLivesChanged(lives);
    }
    private void notifyStateChanged(){
        for(SpaceObserver o : space.getObservers())
            o.onPacStateChanged(state);
    }
}
