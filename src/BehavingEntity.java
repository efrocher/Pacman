import java.util.Arrays;

public abstract class BehavingEntity extends Entity {

    // Enum
    public enum Direction{
        UP, LEFT, DOWN, RIGHT
    }

    // Attributs
    protected GameSpace space;
    protected Direction direction;
    protected int axis;
    protected int directionInAxis;
    private float speed;
    protected float[] nextCrossroad;

    // GetSet
    protected void setDirection(Direction direction){
        this.direction = direction;
        if(direction == Direction.LEFT || direction == Direction.RIGHT)
            axis = 0;
        else
            axis = 1;
        if(direction == Direction.LEFT || direction == Direction.UP)
            directionInAxis = -1;
        else
            directionInAxis = 1;
    }

    // Constructeurs
    public BehavingEntity(float xPos, float yPos, GameSpace space, Direction direction, float speed) {
        super(xPos, yPos);
        this.space = space;
        setDirection(direction);
        this.speed = speed;
        nextCrossroad = BehavingEntity.findNextCrossroad(getPosition(), direction);
        if(!space.tileCrossable(GameSpace.positionToTileCoord(nextCrossroad)))
            nextCrossroad = getPosition();
    }

    // Méthodes
    public abstract void behave();
    protected void move(){

        // Var
        float[] position = getPosition();

        // Mouvement
        position[axis] += speed * directionInAxis * GameController.DELTA;
        GameSpace.wrapPosition(position, axis);

        // Dépassement d'un croisement
        if(position[axis] * directionInAxis >= nextCrossroad[axis] * directionInAxis && onCrossroad()){
            float[] newCrossroad = BehavingEntity.findNextCrossroad(position, direction);
            if(space.tileCrossable(GameSpace.positionToTileCoord(newCrossroad)))
                nextCrossroad = newCrossroad;
            else { // Si le prochain croisement est invalide, l'entité est roll back au croisement précédent
                nextCrossroad[axis] = newCrossroad[axis] - (directionInAxis * GameSpace.TILE_SIZE);
                setPositionX(nextCrossroad[0]);
                setPositionY(nextCrossroad[1]);
                onRoadBlock();
            }
        }
    }
    protected abstract void onRoadBlock();
    protected abstract boolean onCrossroad();
    public abstract void notifyNewInput(Direction input);

    // Méthodes statiques
    protected static float[] findNextCrossroad(float[] position, Direction direction){

        // Prep
        int axis;
        int directionInAxis;
        int positionInAxis;
        if(direction == BehavingEntity.Direction.LEFT || direction == BehavingEntity.Direction.RIGHT)
            axis = 0;
        else
            axis = 1;
        if(direction == BehavingEntity.Direction.LEFT || direction == BehavingEntity.Direction.UP){
            directionInAxis = -1;
            positionInAxis = (int)Math.floor(position[axis]);
        }
        else{
            positionInAxis = (int)Math.ceil(position[axis]);
            directionInAxis = 1;
        }


        // Process
        do{
            positionInAxis += directionInAxis;
        } while (Math.abs(positionInAxis % GameSpace.TILE_SIZE) != GameSpace.TILE_SIZE_HALF);
        if(positionInAxis < 0)
            positionInAxis += GameSpace.DIMENTION[axis];
        else if(positionInAxis >= GameSpace.DIMENTION[axis])
            positionInAxis -= GameSpace.DIMENTION[axis];

        // Retour
        float[] crossRoadposition = Arrays.copyOf(position, 2);
        crossRoadposition[axis] = positionInAxis;
        return crossRoadposition;

    }
}
