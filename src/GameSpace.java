import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSpace {

    // Constantes
    public static final int TILE_AMOUNT_H = 15;
    public static final int TILE_AMOUNT_V = 17;
    public static final int TILE_SIZE = 25; // px, impair
    public static final int TILE_SIZE_HALF = (int)Math.ceil(TILE_SIZE / 2f) - 1; //px
    public static final int WIDTH = TILE_AMOUNT_H * TILE_SIZE; // px
    public static final int HEIGHT = TILE_AMOUNT_V * TILE_SIZE; // px
    public static final int[] DIMENTION = {WIDTH, HEIGHT};
    public static final int[] LABYRINTH_DIMENTION = {TILE_AMOUNT_H, TILE_AMOUNT_V};

    // Attributs
    private final Random rng;
    private Pacman pacman;
    private final List<Ghost> ghosts = new ArrayList<Ghost>();
    private final List<Gum> gums = new ArrayList<Gum>();
    private boolean[][] labyrinth; // True = passage possible | False = passage impossible

    // GetSet
    public boolean[][] getLabyrinth() {
        return labyrinth;
    }
    public Pacman getPacman() {
        return pacman;
    }
    public List<Ghost> getGhosts() {
        return ghosts;
    }

    // Constructeurs
    public GameSpace(Random rng){
        this.rng = rng;

        labyrinth = staticLabyrinth();
        createAndRandomlyPlacePacman();
        createAndRandomlyPlaceGhosts();
    }

    // Méthodes
    private void createAndRandomlyPlacePacman(){

        int[] tileCoord = new int[2];
        do{
            tileCoord[0] = rng.nextInt(WIDTH / TILE_SIZE);
            tileCoord[1] = rng.nextInt(HEIGHT / TILE_SIZE);
        } while(!tileCrossable(tileCoord));
        pacman = new Pacman((tileCoord[0] * TILE_SIZE) + TILE_SIZE_HALF, (tileCoord[1] * TILE_SIZE) + TILE_SIZE_HALF, this, BehavingEntity.Direction.RIGHT);

    }
    private void createAndRandomlyPlaceGhosts(){

        int[] tileCoord = new int[2];
        for(int i = 0; i < 4; i++){
            do{
                tileCoord[0] = rng.nextInt(WIDTH / TILE_SIZE);
                tileCoord[1] = rng.nextInt(HEIGHT / TILE_SIZE);
            } while(!tileCrossable(tileCoord));
            ghosts.add(new Ghost((tileCoord[0] * TILE_SIZE) + TILE_SIZE_HALF, (tileCoord[1] * TILE_SIZE) + TILE_SIZE_HALF, this, BehavingEntity.Direction.RIGHT));
        }

    }
    public boolean tileCrossable(int[] tileCoord){
        return labyrinth[tileCoord[0]][tileCoord[1]];
    }

    // Méthodes statiques
    private static boolean[][] staticLabyrinth(){

        boolean[][] lab = new boolean[TILE_AMOUNT_H][TILE_AMOUNT_V];

        // Remplissage
        for(int x = 0; x < TILE_AMOUNT_H; x++)
            for (int y = 0; y < TILE_AMOUNT_V; y++){
                if(y % 2 == 0){
                    if(x % 2 == 0 || (y % 4 == 0 && (x-1) % 4 == 0))
                        lab[x][y] = false;
                    else
                        lab[x][y] = true;
                }
                else
                    lab[x][y] = true;
            }

        return lab;
    }
    public static int[] positionToTileCoord(float[] position){
        return new int[] {(int)position[0] / TILE_SIZE, (int)position[1] / TILE_SIZE};
    }
    public static float[] tileCoordToPosition(int[] tileCoord){
        return new float[] {(tileCoord[0] * TILE_SIZE) + TILE_SIZE_HALF, (tileCoord[1] * TILE_SIZE) + TILE_SIZE_HALF};
    }
    public static void wrapPosition(float[] position, int axis){
        if (position[axis] < 0)
            position[axis] += GameSpace.DIMENTION[axis];
        else if(position[axis] >= GameSpace.DIMENTION[axis])
            position[axis] -= GameSpace.DIMENTION[axis];
    }

}
