package Pacman.GameSpace;

import Pacman.GameSpace.Entities.Entity;
import Pacman.GameSpace.Entities.Ghost;
import Pacman.GameSpace.Entities.Pacman;
import Pacman.GameSpace.Entities.States.GhostStates.GhostState;
import Pacman.GameSpace.GridElements.Gate;
import Pacman.GameSpace.GridElements.GridElement;
import Pacman.GameSpace.GridElements.Gums.*;
import Pacman.GameSpace.GridElements.Wall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Classe représentant l'espace de jeu
// Contient le labyrinthe et tous les éléments qui y évoluent
public class GameSpace {

    /// --- Constantes --- ///
    public static final int TILE_AMOUNT_H = 15;
    public static final int TILE_AMOUNT_V = 17;
    public static final int TILE_SIZE = 25; // px, impair
    public static final int TILE_SIZE_HALF = (int)Math.ceil(TILE_SIZE / 2f) - 1; //px
    public static final int WIDTH = TILE_AMOUNT_H * TILE_SIZE; // px
    public static final int HEIGHT = TILE_AMOUNT_V * TILE_SIZE; // px
    public static final int[] DIMENTION = {WIDTH, HEIGHT};
    public static final int[] LABYRINTH_DIMENTION = {TILE_AMOUNT_H, TILE_AMOUNT_V};
    public static final int[] SPAWN_PACMAN = {7, 14};
    public static final int[] SPAWN_GHOST = {7, 9};

    /// --- Attributs --- ///
    private final Random rng;
    private Pacman pacman;
    private final List<Ghost> ghosts = new ArrayList<Ghost>();
    private final GridElement[][] grid = new GridElement[TILE_AMOUNT_H][TILE_AMOUNT_V];
    private final List<Gate> gates = new ArrayList<Gate>();
    private final List<SpaceObserver> observers = new ArrayList<SpaceObserver>();
    private int gumAmount;
    private int score;
    private boolean gameBegun = false;

    /// --- GetSet --- ///
    public GridElement getElementAt(int[] tileCoord){
        return grid[tileCoord[0]][tileCoord[1]];
    }
    public Pacman getPacman() {
        return pacman;
    }
    public List<Ghost> getGhosts() {
        return ghosts;
    }
    public int getGumAmount() {
        return gumAmount;
    }
    public int getScore() {
        return score;
    }
    public List<SpaceObserver> getObservers() {
        return observers;
    }

    /// --- Constructeurs --- ///
    public GameSpace(Random rng){

        this.rng = rng;

        // Création de la grille
        // 0 : vide (null) | 1 : Pacman.Space.GridElements.Wall | 2 : Pacman.Space.GridElements.Gums.NormalGum | 3 : Pacman.Space.GridElements.Gums.SuperGum | 4 : Pacman.Space.GridElements.Gums.MazeGum | 5 : Pacman.Space.GridElements.Gums.SneakyGum
        // 6 : Pacman.Space.GridElements.Gate (open) | 7 : Pacman.Space.GridElements.Gate (closed)
        int[][] dummyGrid = new int[][]{
                {1, 1, 1, 7, 1, 1, 1, 1, 1, 1, 1, 7, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1},
                {1, 2, 1, 1, 2, 2, 2, 3, 2, 2, 2, 1, 1, 2, 1},
                {1, 2, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 2, 1},
                {1, 1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 1},
                {1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 1},
                {6, 2, 2, 1, 1, 2, 1, 2, 1, 2, 1, 1, 2, 2, 6},
                {1, 1, 2, 2, 2, 2, 1, 2, 1, 2, 2, 2, 2, 1, 1},
                {1, 1, 2, 1, 2, 1, 1, 4, 1, 1, 2, 1, 2, 1, 1},
                {1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 2, 1},
                {1, 2, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2, 2, 1},
                {1, 2, 1, 2, 1, 2, 2, 0, 2, 2, 1, 2, 1, 2, 1},
                {1, 5, 2, 2, 2, 2, 1, 1, 1, 2, 2, 2, 2, 5, 1},
                {1, 1, 1, 7, 1, 1, 1, 1, 1, 1, 1, 7, 1, 1, 1},
        };
        gumAmount = 0;
        for (int x = 0; x < TILE_AMOUNT_H; x++)
            for (int y = 0; y < TILE_AMOUNT_V; y++)
                switch (dummyGrid[y][x]) {
                    case 1 -> grid[x][y] = new Wall();
                    case 2 -> {
                        grid[x][y] = new NormalGum(new int[]{x, y}, this);
                        gumAmount++;
                    }
                    case 3 -> {
                        grid[x][y] = new SuperGum(new int[]{x, y}, this);
                        gumAmount++;
                    }
                    case 4 -> {
                        grid[x][y] = new MazeGum(new int[]{x, y}, this);
                        gumAmount++;
                    }
                    case 5 -> {
                        grid[x][y] = new SneakyGum(new int[]{x, y}, this);
                        gumAmount++;
                    }
                    case 6 -> {
                        Gate gOpen = new Gate(true);
                        grid[x][y] = gOpen;
                        gates.add(gOpen);
                    }
                    case 7 -> {
                        Gate gClosed = new Gate(false);
                        grid[x][y] = gClosed;
                        gates.add(gClosed);
                    }
                }

        // Pacman.Space.Entities.Pacman
        pacman = new Pacman(tileCoordToPosition(SPAWN_PACMAN[0]), tileCoordToPosition(SPAWN_PACMAN[1]), this, Entity.Direction.RIGHT);

        // Fantômes
        for(int i = 0; i < 4; i++)
            ghosts.add(new Ghost((7 * TILE_SIZE) + TILE_SIZE_HALF, ((i + 6) * TILE_SIZE) + TILE_SIZE_HALF, this, Entity.Direction.UP, rng));

        // Autre
        score = 0;
    }

    /// --- Méthodes --- ///
    // Méthode créant et plaçant pacman au hasard
    private void createAndRandomlyPlacePacman(){

        int[] tileCoord = new int[2];
        do{
            tileCoord[0] = rng.nextInt(WIDTH / TILE_SIZE);
            tileCoord[1] = rng.nextInt(HEIGHT / TILE_SIZE);
        } while(!tileCrossable(tileCoord));
        pacman = new Pacman((tileCoord[0] * TILE_SIZE) + TILE_SIZE_HALF, (tileCoord[1] * TILE_SIZE) + TILE_SIZE_HALF, this, Entity.Direction.RIGHT);

    }
    // Méthode créant et plaçant les fantômes au hasard
    private void createAndRandomlyPlaceGhosts(){

        int[] tileCoord = new int[2];
        for(int i = 0; i < 4; i++){
            do{
                tileCoord[0] = rng.nextInt(WIDTH / TILE_SIZE);
                tileCoord[1] = rng.nextInt(HEIGHT / TILE_SIZE);
            } while(!tileCrossable(tileCoord));
            ghosts.add(new Ghost((tileCoord[0] * TILE_SIZE) + TILE_SIZE_HALF, (tileCoord[1] * TILE_SIZE) + TILE_SIZE_HALF, this, Entity.Direction.RIGHT, rng));
        }

    }
    // Méthode retournant true si la tile est traversable
    public boolean tileCrossable(int[] tileCoord){
        return grid[tileCoord[0]][tileCoord[1]] == null || grid[tileCoord[0]][tileCoord[1]].isCrosseable();
    }
    // Méthode effectuant toutes les actions liées au commencement de la partie
    public void startGame(){
        if(!gameBegun){

            // Changement d'état
            gameBegun = true;

            // Départ des fantômes
            for(Ghost g : ghosts)
                g.setState(GhostState.REGULAR);

            // Notification aux observateurs
            for(SpaceObserver o : observers)
                o.onGameStarted();
        }
    }
    // Méthode déclenchant le fait d'être traversé par une entité pour l'élément du labyrinthe correspondant
    public void cross(Entity crossingEntity, int[] tileCrossed){
        if(grid[tileCrossed[0]][tileCrossed[1]] != null)
            grid[tileCrossed[0]][tileCrossed[1]].onCrossed(crossingEntity);
    }
    // Suppression d'une pacgomme du labyrinthe
    public void removeGumAtCoordinate(int[] tileCoord){
        if(grid[tileCoord[0]][tileCoord[1]] instanceof Gum){
            grid[tileCoord[0]][tileCoord[1]] = null;
            gumAmount--;
        }
    }
    // Ajout de points
    public void addPoints(int points){
        if(score < 5000 && score + points >= 5000)
            pacman.addLife(1);
        score += points;
        notifyScoreUpdatesSubscribers();
    }
    // Changement de l'état des éléments portes
    public void swapGates(){
        for (Gate g : gates)
            g.swap();
    }
    // Méthode permettant aux observateurs de s'abonner pour recevoir les notifications de changement d'état
    public int subscribe(SpaceObserver observer){
        observers.add(observer);
        return score;
    }
    // Méthode notifiant les observateurs du changement du score
    private void notifyScoreUpdatesSubscribers(){
        for(SpaceObserver o : observers)
            o.onScoreChanged(score);
    }

    /// --- Méthodes statiques --- ///
    // Méthodes convertissant une position en une coordonnée de tile dans le labyrinthe
    public static int[] positionToTileCoord(float[] position){
        return new int[] {(int)position[0] / TILE_SIZE, (int)position[1] / TILE_SIZE};
    }
    public static int[] positionToTileCoord(float positionX, float positionY){
        return new int[] {(int)positionX / TILE_SIZE, (int)positionY / TILE_SIZE};
    }
    public static int positionToTileCoord(float position){
        return (int)position / TILE_SIZE;
    }
    // Méthodes convertissant une coordonnée de tile dans le labyrinthe en position (centre de la tile)
    public static float[] tileCoordToPosition(int[] tileCoord){
        return new float[] {(tileCoord[0] * TILE_SIZE) + TILE_SIZE_HALF, (tileCoord[1] * TILE_SIZE) + TILE_SIZE_HALF};
    }
    public static float[] tileCoordToPosition(int tileCoordX, int tileCoordY){
        return new float[] {(tileCoordX * TILE_SIZE) + TILE_SIZE_HALF, (tileCoordY * TILE_SIZE) + TILE_SIZE_HALF};
    }
    public static float tileCoordToPosition(int tileCoord){
        return (tileCoord * TILE_SIZE) + TILE_SIZE_HALF;
    }
    // Méthode gérant l'overflow/underflow d'une position (gestion des sortie d'écran)
    public static void wrapPosition(float[] position, int axis){

        if (position[axis] < 0)
            position[axis] += GameSpace.DIMENTION[axis];
        else if(position[axis] >= GameSpace.DIMENTION[axis])
            position[axis] -= GameSpace.DIMENTION[axis];

    }

}
