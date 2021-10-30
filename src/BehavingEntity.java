import java.lang.reflect.Array;
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
    private float nextCrossroad;

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
        findNextCrossroad();
    }

    // Méthodes
    public abstract void behave();
    protected abstract Direction tryToChoseNewDirection();
    protected void move(){

        // Var
        float[] newPosition = Arrays.copyOf(getPosition(), 2);

        // Mouvement
        newPosition[axis] += speed * directionInAxis * GameController.DELTA;
        GameSpace.wrapPosition(newPosition, axis);

        // Changement de direction
        boolean directionChanged = false;
        if(newPosition[axis] * directionInAxis >= nextCrossroad * directionInAxis){

            Direction newDirection = tryToChoseNewDirection();
            // Prep
            int newAxis;
            int newDirectionInAxis;
            int[] hypotheticalTileCoord = GameSpace.positionToTileCoord(getPosition());
            if(newDirection == Direction.LEFT || newDirection == Direction.RIGHT)
                newAxis = 0;
            else
                newAxis = 1;
            if(newDirection == Direction.LEFT || newDirection == Direction.UP)
                newDirectionInAxis = -1;
            else
                newDirectionInAxis = 1;

            // Calcul de la prochaine case qui serait rencontrée
            hypotheticalTileCoord[newAxis] = Math.floorMod(hypotheticalTileCoord[newAxis] + newDirectionInAxis, GameSpace.LABYRINTH_DIMENTION[newAxis]);
            if(hypotheticalTileCoord[newAxis] == -1)
                System.out.println(newDirectionInAxis + ", " + "GameSpace.LABYRINTH_DIMENTION[newAxis]");
            // Result
            if(newDirection != direction && space.tileCrossable(hypotheticalTileCoord))
            {
                newPosition[axis] = nextCrossroad;
                setPositionX(newPosition[0]);
                setPositionY(newPosition[1]);
                setDirection(newDirection);
                directionChanged = true;
            }
            findNextCrossroad();

        }

        if(!directionChanged){

            // Wallcheck
            boolean pathIsClear = true;
            int[] tileCoord = GameSpace.positionToTileCoord(newPosition);
            if(!space.tileCrossable(tileCoord)){
                pathIsClear = false;
            }
            else if((newPosition[axis] % GameSpace.TILE_SIZE) * directionInAxis > GameSpace.TILE_SIZE_HALF * directionInAxis)
            {
                tileCoord[axis] = Math.floorMod(tileCoord[axis] + directionInAxis, GameSpace.LABYRINTH_DIMENTION[axis]);
                if(!space.tileCrossable(tileCoord))
                    pathIsClear = false;
            }

            // Si besoin, roll back à une position souhaitable
            if(!pathIsClear){

                // Recherche d'une tile vide en arrière
                tileCoord[axis] = Math.floorMod(tileCoord[axis] - directionInAxis, GameSpace.LABYRINTH_DIMENTION[axis]);
                while(!space.tileCrossable(tileCoord))
                    tileCoord[axis] = Math.floorMod(tileCoord[axis] - directionInAxis, GameSpace.LABYRINTH_DIMENTION[axis]);


                // Positionnement
                newPosition = GameSpace.tileCoordToPosition(tileCoord);
            }

            // Application de la nouvelle position
            setPositionX(newPosition[0]);
            setPositionY(newPosition[1]);
        }

    }
    private void findNextCrossroad(){

        // Prep
        int positionInAxis;
        if(direction == Direction.LEFT || direction == Direction.UP)
            positionInAxis = (int)Math.floor(getPosition()[axis]);
        else
            positionInAxis = (int)Math.ceil(getPosition()[axis]);

        // Process
        do{
            positionInAxis += directionInAxis;
        } while (Math.abs(positionInAxis % GameSpace.TILE_SIZE) != GameSpace.TILE_SIZE_HALF);
        if(positionInAxis < 0)
            positionInAxis += GameSpace.DIMENTION[axis];
        else if(positionInAxis >= GameSpace.DIMENTION[axis])
            positionInAxis -= GameSpace.DIMENTION[axis];

        // Check validité
        float[] crossRoadposition = Arrays.copyOf(getPosition(), 2);
        crossRoadposition[axis] = positionInAxis;
        if(space.tileCrossable(GameSpace.positionToTileCoord(crossRoadposition)))
            nextCrossroad = positionInAxis;
        else
            nextCrossroad = getPosition()[axis];

    }

    // Méthodes statiques

}
