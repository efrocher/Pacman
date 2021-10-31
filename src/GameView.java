import javax.swing.*;
import java.awt.*;

public class GameView extends JComponent {

    // Constantes
    public final static int SCALE = 2;
    public final static Color COLOR_BACKGROUND = Color.getHSBColor(0.59f, 0.82f, 0.51f);
    public final static Color COLOR_WALL = Color.BLACK;
    public final static Color COLOR_PACMAN_NORMAL = Color.YELLOW;
    public final static Color COLOR_PACMAN_SNEAKY = Color.YELLOW;
    public final static Color COLOR_PACMAN_SUPER = Color.ORANGE;
    public final static Color COLOR_GHOST_NORMAL = Color.GREEN;
    public final static Color COLOR_GHOST_WEAK = Color.GREEN;
    public final static Color COLOR_GUM_NORMAL = Color.WHITE;
    public final static Color COLOR_GUM_SNEAKY = Color.MAGENTA;
    public final static Color COLOR_GUM_SUPER = Color.ORANGE;
    public final static Color COLOR_GUM_MAZE = Color.GREEN;


    // Attributs
    private GameSpace space;

    // GetSet

    // Constructeurs
    public GameView(GameSpace gameSpace) {

        super();

        setOpaque(true);
        setSize(GameSpace.WIDTH * SCALE, GameSpace.HEIGHT * SCALE);

        this.space = gameSpace;
    }

    // Méthodes
    @Override
    public void paint(Graphics g) {

        super.paint(g);
        setBackground(COLOR_BACKGROUND);
        drawBackground(g);
        drawWalls(g);
        drawNonWallElements(g);
        drawEntities(g);

    }
    private void drawBackground(Graphics g){

        g.setColor(COLOR_BACKGROUND);
        g.fillRect(0, 0, GameSpace.WIDTH * SCALE, GameSpace.HEIGHT * SCALE);

    }
    private void drawWalls(Graphics g) {

        g.setColor(COLOR_WALL);

        for(int x = 0; x < GameSpace.TILE_AMOUNT_H; x++ )
            for(int y = 0; y < GameSpace.TILE_AMOUNT_V; y++ ){
                GridElement element = space.getElementAt(new int[] {x, y});
                if(element != null && (element instanceof Wall || (element instanceof Gate && !element.isCrosseable()))){
                    g.fillRect(x * GameSpace.TILE_SIZE * SCALE, y * GameSpace.TILE_SIZE * SCALE, GameSpace.TILE_SIZE * SCALE, GameSpace.TILE_SIZE * SCALE);
                }
            }

    }
    private void drawNonWallElements(Graphics g) {

        for(int x = 0; x < GameSpace.TILE_AMOUNT_H; x++ )
            for(int y = 0; y < GameSpace.TILE_AMOUNT_V; y++ ){

                GridElement element = space.getElementAt(new int[] {x, y});
                if(element != null && !(element instanceof Wall)){
                    if(element instanceof Gum)
                        drawGum(g, (Gum)element, x, y);
                }

            }

    }
    private void drawGum(Graphics g, Gum gum, int x, int y) {

        // Choix couleur
        if(gum instanceof NormalGum)
            g.setColor(COLOR_GUM_NORMAL);
        else if(gum instanceof SneakyGum)
            g.setColor(COLOR_GUM_SNEAKY);
        else if(gum instanceof SuperGum)
            g.setColor(COLOR_GUM_SUPER);
        else if(gum instanceof MazeGum)
            g.setColor(COLOR_GUM_MAZE);

        // Draw
        g.fillOval(
                ((x * GameSpace.TILE_SIZE) + GameSpace.TILE_SIZE_HALF - (GameSpace.TILE_SIZE / 10)) * SCALE,
                ((y * GameSpace.TILE_SIZE) + GameSpace.TILE_SIZE_HALF - (GameSpace.TILE_SIZE / 10)) * SCALE,
                GameSpace.TILE_SIZE / 5 * SCALE,
                GameSpace.TILE_SIZE / 5 * SCALE);

    }
    private void drawEntities(Graphics g) {

        // Ghosts
        g.setColor(COLOR_GHOST_NORMAL);
        for(Ghost ghost : space.getGhosts())
            g.fillOval(
                    (int) (ghost.getPosition()[0] - GameSpace.TILE_SIZE_HALF + (GameSpace.TILE_SIZE / 5)) * SCALE,
                    (int) (ghost.getPosition()[1] - GameSpace.TILE_SIZE_HALF + (GameSpace.TILE_SIZE / 5)) * SCALE,
                    GameSpace.TILE_SIZE * 3/5 * SCALE,
                    GameSpace.TILE_SIZE * 3/5 * SCALE);

        // Pacman
        g.setColor(COLOR_PACMAN_NORMAL);
        g.fillOval(
                (int) (space.getPacman().getPosition()[0] - GameSpace.TILE_SIZE_HALF + (GameSpace.TILE_SIZE / 5)) * SCALE,
                (int) (space.getPacman().getPosition()[1] - GameSpace.TILE_SIZE_HALF + (GameSpace.TILE_SIZE / 5)) * SCALE,
                GameSpace.TILE_SIZE * 3/5 * SCALE,
                GameSpace.TILE_SIZE * 3/5 * SCALE);

    }
}
