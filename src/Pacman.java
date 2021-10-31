public class Pacman extends Entity {

    // Constantes
    private static final float BASE_SPEED = 50f; // px / seconde

    // Attributs

    // GetSet

    // Constructeurs
    public Pacman(int xPos, int yPos, GameSpace space, Direction direction){
        super(xPos, yPos, space, direction, BASE_SPEED);
        InputManager.subscribe(this);
    }

    // Méthodes
    public void behave(){
        move();
    }
    @Override
    protected void onRoadBlock() {

    }
    @Override
    protected boolean onCrossroad() {
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
    public void notifyNewInput(Direction newDirection) {
        if(newDirection != null && newDirection.ordinal() == (direction.ordinal() + 2) % 4){
            float[] newCrossroad = findNextCrossroad(getPosition(), newDirection);
            if(space.tileCrossable(GameSpace.positionToTileCoord(newCrossroad))){
                setDirection(newDirection);
                nextCrossroad = newCrossroad;
                InputManager.clearLastInput();
            }
        }
    }
}
