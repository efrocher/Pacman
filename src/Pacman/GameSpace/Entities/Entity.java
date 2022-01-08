package Pacman.GameSpace.Entities;

import Pacman.Controller.GameController;
import Pacman.GameSpace.GameSpace;
import Pacman.Inputs.InputManager;

import java.util.Arrays;

// Classe abstraite représentant une entité :
// un personnage capable de se déplacer dans le labyrinthe
public abstract class Entity {

    /// --- Enum --- ///
    public enum Direction{
        UP, LEFT, DOWN, RIGHT
    }

    /// --- Attributs --- ///
    protected final GameSpace space;
    private final float[] position = new float[2];
    private final float speed;
    private final int hitRadius;
    private Direction direction;
    protected int axis;
    protected int directionInAxis;
    protected float[] nextCrossroad;

    /// --- GetSet --- ///
    public GameSpace getSpace() {
        return space;
    }
    public float[] getPosition() {
        return position;
    }
    public void setPositionX(float x){
        position[0] = x;
    }
    public void setPositionY(float y){
        position[1] = y;
    }
    public void setPosition(float[] position){
        this.position[0] = position[0];
        this.position[1] = position[1];
    }
    public Direction getDirection() {
        return direction;
    }
    protected void setDirection(Direction direction){
        this.direction = direction;

        // Calcul de l'axe et de la directions dans l'axe
        // Ces valeurs sont utilisées pour faciliter les des déplacements et changements de direction
        if(direction == Direction.LEFT || direction == Direction.RIGHT)
            axis = 0;
        else
            axis = 1;
        if(direction == Direction.LEFT || direction == Direction.UP)
            directionInAxis = -1;
        else
            directionInAxis = 1;
    }

    /// --- Constructeurs --- ///
    public Entity(float xPos, float yPos, GameSpace space, Direction direction, float speed, int hitRadius) {

        this.space = space;
        this.speed = speed;
        this.hitRadius = hitRadius;
        relocate(new float[] {xPos, yPos}, direction);

    }

    /// --- Méthodes --- ///
    // Comportement de l'entité
    public abstract void behave();
    // Déplacement de l'entité
    public void move(float speedModifier){

        // Var
        float[] position = getPosition();

        // Mouvement
        position[axis] += speed * speedModifier * directionInAxis * GameController.DELTA;
        GameSpace.wrapPosition(position, axis);

        // Si on a dépassé le prochain croisement, et que l'on a décidé de poursuivre tout droit
        if(position[axis] * directionInAxis >= nextCrossroad[axis] * directionInAxis && onCrossroad()){

            // Recherche du croisement suivant
            float[] newCrossroad = Entity.findNextCrossroad(position, direction);

            // Si la tile suivante est traversable, on poursuit
            if(space.tileCrossable(GameSpace.positionToTileCoord(newCrossroad)))
                nextCrossroad = newCrossroad;
            // Si non, retour au centre de la tile actuelle et collision avec le mur
            else {
                nextCrossroad[axis] = newCrossroad[axis] - (directionInAxis * GameSpace.TILE_SIZE);
                setPositionX(nextCrossroad[0]);
                setPositionY(nextCrossroad[1]);
                onRoadBlock();
            }
        }
    }
    // Méthode appelée quand l'entité tente d'entrer dans une tile qui n'est pas traversable
    protected abstract void onRoadBlock();
    // Méthode appelée quand l'entité traverse le milieu d'une tile
    // Retourne true si l'entité a choisi de poursuivre son mouvement dans la même direction
    protected boolean onCrossroad(){
        space.cross(this, GameSpace.positionToTileCoord(nextCrossroad));
        return true;
    }
    // Méthode appelée quand l'entité entre en collision avec une autre entité
    protected abstract void onEntityCollision(Entity otherEntity);
    // Méthode déplaçant l'entité à une position donnée avec une certaine direction
    public void relocate(float[] newPosition, Direction direction){
        position[0] = newPosition[0];
        position[1] = newPosition[1];
        setDirection(direction);
        nextCrossroad = GameSpace.tileCoordToPosition(GameSpace.positionToTileCoord(getPosition()));
        InputManager.clearLastDirectionInput();
    }
    // Méthode appelée quand l'entité est tuée
    public abstract void die();

    /// --- Méthodes statiques --- ///
    // Méthode permettant de trouver le prochain milieu de tile (croisement)
    // que croiserait une entité si elle partait d'une position donnée dans une direction donnée.
    // Probablement peu éfficient mais ça marche et j'ai pas le temps de faire mieux ¯\_(ツ)_/¯
    // Todo : optimiser
    protected static float[] findNextCrossroad(float[] position, Direction direction){

        // Vars
        int axis;
        int directionInAxis;
        int positionInAxis;

        // Récupération de l'axe
        if(direction == Direction.LEFT || direction == Direction.RIGHT)
            axis = 0;
        else
            axis = 1;

        // Récupération de la direction dans l'axe
        // + arrondi de la position au pixel près dans la bonne direction
        if(direction == Direction.LEFT || direction == Direction.UP){
            directionInAxis = -1;
            positionInAxis = (int)Math.floor(position[axis]);
        }
        else{
            positionInAxis = (int)Math.ceil(position[axis]);
            directionInAxis = 1;
        }

        // Recherche du prochain croisement pixel par pixel
        do{
            positionInAxis += directionInAxis;
        } while (Math.abs(positionInAxis % GameSpace.TILE_SIZE) != GameSpace.TILE_SIZE_HALF);

        // Construction de la coordonnée à retourner
        float[] crossRoadposition = Arrays.copyOf(position, 2);
        crossRoadposition[axis] = positionInAxis;

        // Wrap de la coordonnée (gestion de la sortie d'écran)
        GameSpace.wrapPosition(crossRoadposition, axis);

        // Retour
        return crossRoadposition;

    }
    // Méthode verifiant la collision de deux entité
    public static void checkCollision(Entity entity1, Entity entity2){

        if(entity1.hitRadius + entity2.hitRadius > giveDistance(entity1, entity2)){
            entity1.onEntityCollision(entity2);
            entity2.onEntityCollision(entity1);
        }

    }
    // Retourne la distance entre deux entités
    public static float giveDistance(Entity entity1, Entity entity2){
        return (float)Math.sqrt(Math.pow(entity1.position[0] - entity2.position[0], 2) + Math.pow(entity1.position[1] - entity2.position[1], 2));
    }
}
