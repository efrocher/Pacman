public class Pacman extends BehavingEntity {

    // Constantes
    private static final float BASE_SPEED = 50f; // px / seconde

    // Attributs

    // GetSet

    // Constructeurs
    public Pacman(int xPos, int yPos, GameSpace space, Direction direction){
        super(xPos, yPos, space, direction, BASE_SPEED);
    }

    // Méthodes
    public void behave(){
        move();
    }
    @Override
    protected Direction tryToChoseNewDirection() {
        if(InputManager.isUpPressed())
            return Direction.UP;
        else if(InputManager.isDownPressed())
            return Direction.DOWN;
        else if(InputManager.isLeftPressed())
            return Direction.LEFT;
        else if(InputManager.isRightPressed())
            return Direction.RIGHT;
        else return direction;
    }

}
