package Pacman.GameSpace.Entities;

import Pacman.GameSpace.Entities.States.GhostStates.GhostState;
import Pacman.GameSpace.Entities.States.GhostStates.GhostState_Afraid;
import Pacman.GameSpace.Entities.States.GhostStates.GhostState_Regular;
import Pacman.GameSpace.Entities.States.GhostStates.GhostState_Waiting;
import Pacman.GameSpace.GameSpace;

import java.util.Random;

public class Ghost extends Entity {

    /// --- Constantes --- ///
    private static final float BASE_SPEED = 80f; // px / seconde
    private static final int HIT_RADIUS = 6; // px

    /// --- Attributs --- ///
    private Random rng;
    private GhostState state;

    /// --- GetSet --- ///
    public int getState() {
        return state.stateId();
    }
    public void setState(int stateId) {

        state.onEnd();
        state = switch (stateId){
            case GhostState.REGULAR     -> new GhostState_Regular(this);
            case GhostState.WAITING     -> new GhostState_Waiting(this);
            case GhostState.AFRAID      -> new GhostState_Afraid(this);
            default -> throw new IllegalStateException("No such state");
        };
        state.onEnd();

    }

    /// --- Constructeurs --- ///
    public Ghost(float xPos, float yPos, GameSpace space, Direction direction, Random rng) {
        super(xPos, yPos, space, direction, BASE_SPEED, HIT_RADIUS);
        this.rng = rng;
        state = new GhostState_Waiting(this);
    }

    /// --- Méthodes --- ///
    public void behave() {
        state.behave();
    }
    @Override
    protected void onRoadBlock() {

        Direction newDirection;
        float[] newCrossroad;
        do{
            newDirection = Direction.values()[rng.nextInt(4)];
            newCrossroad = findNextCrossroad(getPosition(), newDirection);
        } while(newDirection.ordinal() % 2 == getDirection().ordinal() % 2 || !space.tileCrossable(GameSpace.positionToTileCoord(newCrossroad)));
        setDirection(newDirection);
        nextCrossroad = newCrossroad;

    }
    @Override
    protected boolean onCrossroad() {
        super.onCrossroad();
        return true;
    }
    @Override
    protected void onEntityCollision(Entity otherEntity) {
        state.onEntityCollision(otherEntity);
    }
    @Override
    public void die() {
        relocate(GameSpace.tileCoordToPosition(space.SPAWN_GHOST), Direction.UP);
    }
}
