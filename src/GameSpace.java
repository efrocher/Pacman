import java.util.Random;

public class GameSpace {

    // Constantes
    public static final int TILE_AMOUNT_H = 15;
    public static final int TILE_AMOUNT_V = 15;
    public static final int TILE_SIZE = 25; // px
    public static final int WIDTH = TILE_AMOUNT_H * TILE_SIZE; // px
    public static final int HEIGHT = TILE_AMOUNT_V * TILE_SIZE; // px

    // Attributs
    private Random rng;
    private Pacman pacman;
    private Ghost[] ghosts;
    private Gum[] gums;
    private boolean[][] labyrinth; // [x/width][y/height] | True = passage possible | False = passage impossible

    // GetSet
    public boolean[][] getLabyrinth() {
        return labyrinth;
    }
    public Pacman getPacman() {
        return pacman;
    }

    // Constructeurs
    public GameSpace(Random rng){
        this.rng = rng;

        labyrinth = staticLabyrinth();
        createAndRandomlyPlacePacman();
    }

    // Méthodes
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
    private void createAndRandomlyPlacePacman(){

        int x;
        int y;
        do{
            x = rng.nextInt(WIDTH / TILE_SIZE);
            y = rng.nextInt(HEIGHT / TILE_SIZE);
        } while(!labyrinth[x][y]);
        pacman = new Pacman((x * TILE_SIZE) + 5, (y * TILE_SIZE) + 5);

    }
    //public static float[][]

}
