import java.util.Arrays;

public abstract class Entity {

    // Enum
    public enum Direction{
        UP, LEFT, DOWN, RIGHT
    }

    // Attributs
    protected GameSpace space;
    private final float position[] = new float[2];
    private final float speed;
    private final int hitRadius;
    protected Direction direction;
    protected int axis;
    protected int directionInAxis;
    protected float[] nextCrossroad;

    // GetSet
    public float[] getPosition() {
        return position;
    }
    public void setPositionX(float x){
        position[0] = x;
    }
    public void setPositionY(float y){
        position[1] = y;
    }
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
    public Entity(float xPos, float yPos, GameSpace space, Direction direction, float speed, int hitRadius) {

        this.space = space;
        this.speed = speed;
        this.hitRadius = hitRadius;
        relocate(new float[] {xPos, yPos}, direction);

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
            float[] newCrossroad = Entity.findNextCrossroad(position, direction);
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
    protected boolean onCrossroad(){
        space.cross(this, GameSpace.positionToTileCoord(nextCrossroad));
        return true;
    }
    protected abstract void onEntityCollision(Entity otherEntity);
    public abstract void notifyNewInput(Direction input);
    public void relocate(float[] newPosition, Direction direction){
        position[0] = newPosition[0];
        position[1] = newPosition[1];
        setDirection(direction);
        nextCrossroad = GameSpace.tileCoordToPosition(GameSpace.positionToTileCoord(getPosition()));
        InputManager.clearLastInput();
    }

    // Méthodes statiques
    protected static float[] findNextCrossroad(float[] position, Direction direction){

        // Prep
        int axis;
        int directionInAxis;
        int positionInAxis;
        if(direction == Direction.LEFT || direction == Direction.RIGHT)
            axis = 0;
        else
            axis = 1;
        if(direction == Direction.LEFT || direction == Direction.UP){
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
    public static void checkCollision(Entity entity1, Entity entity2){
        if(entity1.hitRadius + entity2.hitRadius > Math.sqrt(Math.pow(entity1.position[0] - entity2.position[0], 2) + Math.pow(entity1.position[1] - entity2.position[1], 2))){
            entity1.onEntityCollision(entity2);
            entity2.onEntityCollision(entity1);
        }
    }
}
