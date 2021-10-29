public class Ghost extends BehavingEntity {

    // Constantes
    private static final float BASE_SPEED = 25f; // px / seconde

    // Attributs

    // GetSet

    // Constructeurs
    public Ghost(float xPos, float yPos, GameSpace space, Direction direction) {
        super(xPos, yPos, space, direction, BASE_SPEED);
    }

    // Méthodes
    public void behave() {

    }
    @Override
    protected Direction tryToChoseNewDirection() {
        return Direction.UP;
    }
}
