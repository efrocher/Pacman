import java.util.Random;

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
        Random rng = new Random();
        return Direction.values()[rng.nextInt(4)];
    }

}
