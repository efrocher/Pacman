package Pacman.GameSpace.Entities;

import Pacman.GameSpace.Entities.States.PacStates.*;
import Pacman.GameSpace.GameSpace;
import Pacman.Inputs.InputManager;
import Pacman.Inputs.InputObserver;
import Pacman.GameSpace.SpaceObserver;

// Classe représentant pacman
public class Pacman extends Entity implements InputObserver {

    /// --- Constantes --- ///
    private static final float BASE_SPEED = 100f; // px / seconde
    private static final int HIT_RADIUS = 6; // px
    private static final int BASE_LIVES = 3;

    /// --- Attributs --- ///
    private int lives;
    private PacState state;

    /// --- GetSet --- ///
    public int getLives() {
        return lives;
    }
    public int getState() {
        return state.stateId();
    }
    public void setState(int stateId) {

        state.onEnd();
        state = switch (stateId){
            case PacState.REGULAR   -> new PacState_Regular(this);
            case PacState.WAITING   -> new PacState_Waiting(this);
            case PacState.SNEAKY    -> new PacState_Sneaky(this);
            case PacState.SUPER     -> new PacState_Super(this);
            default -> throw new IllegalStateException("No such state");
        };
        state.onStart();
        notifyPacStateChanged();

    }

    /// --- Constructeurs --- ///
    public Pacman(float xPos, float yPos, GameSpace space, Direction direction){
        super(xPos, yPos, space, direction, BASE_SPEED, HIT_RADIUS);
        InputManager.subscribe(this);
        lives = BASE_LIVES;
        state = new PacState_Waiting(this);
    }

    /// --- Méthodes --- ///
    public void behave(){
        state.behave();
    }
    @Override
    protected void onRoadBlock() {

    }
    @Override
    protected boolean onCrossroad() {

        // Super
        super.onCrossroad();

        // Récupération du dernier input de direction
        Direction newDirection = InputManager.getLastDirectionInput();

        // Si il y a une direction, qu'elle est différente de l'actuelle et qu'elle n'est pas trop ancienne (<500ms)
        if(newDirection != null && newDirection != getDirection() && ((System.nanoTime() - InputManager.getLastDirectionInputTimestamp()) / 1e6) < 500){

            // Recherche du prochain croisement dans cette nouvelle direction
            float[] newCrossroad = findNextCrossroad(nextCrossroad, newDirection);

            // Si la tile est traversable
            if(space.tileCrossable(GameSpace.positionToTileCoord(newCrossroad))){

                // Nouvelle direction
                setDirection(newDirection);

                // Centrage sur le croisement que l'on vient de traverser
                setPosition(nextCrossroad);

                // Prochain croisement
                nextCrossroad = newCrossroad;

                // Le dernier input est "consommé"
                InputManager.clearLastDirectionInput();

                return false;
            }
        }
        return true;
    }
    @Override
    protected void onEntityCollision(Entity otherEntity) {
        state.onEntityCollision(otherEntity);
    }
    // Méthode appelée quand une direction est préssée
    public void onNewDirectionInput(Direction newDirection) {
        state.onNewDirectionInput(newDirection);
    }
    // Méthode gérant la possibilité de faire demi-tour quand une direction est pressée
    public void tryChangeDirection(Direction newDirection){

        if(newDirection != null && newDirection.ordinal() == (getDirection().ordinal() + 2) % 4){
            float[] newCrossroad = findNextCrossroad(getPosition(), newDirection);
            if(space.tileCrossable(GameSpace.positionToTileCoord(newCrossroad))){
                setDirection(newDirection);
                nextCrossroad = newCrossroad;
                InputManager.clearLastDirectionInput();
            }
        }

    }
    @Override
    public void die(){
        lives--;
        relocate(GameSpace.tileCoordToPosition(GameSpace.SPAWN_PACMAN), Direction.RIGHT);
        notifyLivesChanged();
        state = new PacState_Waiting(this);
    }
    // Méthode permettant d'ajouter un nombre donné de vies
    public void addLife(int lives){
        this.lives += lives;
        notifyLivesChanged();
    }
    // Méthode notifiant les observateurs que le nombre de vies a changé
    private void notifyLivesChanged(){
        for(SpaceObserver o : space.getObservers())
            o.onLivesChanged(lives);
    }
    // Méthode notifiant les observateurs que l'état de pacman a changé
    private void notifyPacStateChanged(){
        for(SpaceObserver o : space.getObservers())
            o.onPacStateChanged(state);
    }
}
