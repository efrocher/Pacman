import java.util.Random;

public class Ghost extends Entity {

    // Constantes
    private static final float BASE_SPEED = 80f; // px / seconde

    // Attributs
    private Random rng;

    // GetSet

    // Constructeurs
    public Ghost(float xPos, float yPos, GameSpace space, Direction direction, Random rng) {
        super(xPos, yPos, space, direction, BASE_SPEED);
        this.rng = rng;
    }

    // Méthodes
    public void behave() {
        move();
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
    public void notifyNewInput(Direction input) {

    }
}
