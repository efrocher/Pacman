package Pacman.Space.Entities;

import Pacman.Space.Entities.PacStates.SuperState;
import Pacman.Space.GameSpace;

import java.util.Random;

public class Ghost extends Entity {

    // Constantes
    private static final float BASE_SPEED = 80f; // px / seconde
    private static final int HIT_RADIUS = 6; // px

    // Attributs
    private Random rng;

    // GetSet

    // Constructeurs
    public Ghost(float xPos, float yPos, GameSpace space, Direction direction, Random rng) {
        super(xPos, yPos, space, direction, BASE_SPEED, HIT_RADIUS);
        this.rng = rng;
    }

    // Méthodes
    public void behave() {
        if(space.getPacman().getState() instanceof SuperState)
            move(0.5f);
        else
            move(1);
    }
    @Override
    protected void onRoadBlock() {

        Direction newDirection;
        float[] newCrossroad;
        do{
            newDirection = Direction.values()[rng.nextInt(4)];
            newCrossroad = findNextCrossroad(getPosition(), newDirection);
        } while(newDirection.ordinal() % 2 == direction.ordinal() % 2 || !space.tileCrossable(GameSpace.positionToTileCoord(newCrossroad)));
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

    }
    @Override
    public void die() {
        relocate(GameSpace.tileCoordToPosition(space.SPAWN_GHOST), Direction.UP);
    }
}
