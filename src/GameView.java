import javax.swing.*;
import java.awt.*;

public class GameView extends JComponent {

    // Constantes
    public final static int SCALE = 2;

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
        setBackground(Color.BLUE);
        drawBackground(g);
        drawLabyrinth(g);
        drawEntities(g);

    }
    private void drawBackground(Graphics g){

        g.setColor(Color.getHSBColor(0.59f, 0.82f, 0.51f));
        g.fillRect(0, 0, GameSpace.WIDTH * SCALE, GameSpace.HEIGHT * SCALE);

    }
    private void drawLabyrinth(Graphics g) {

        g.setColor(Color.BLACK);

        for(int x = 0; x < GameSpace.TILE_AMOUNT_H; x++ )
            for(int y = 0; y < GameSpace.TILE_AMOUNT_V; y++ )
                if(!space.getLabyrinth()[x][y]){
                    g.fillRect(x * GameSpace.TILE_SIZE * SCALE, y * GameSpace.TILE_SIZE * SCALE, GameSpace.TILE_SIZE * SCALE, GameSpace.TILE_SIZE * SCALE);
                }

    }
    private void drawEntities(Graphics g){

        // Gums
        g.setColor(Color.WHITE);
        for(Gum gum : space.getGums()){
            if(gum instanceof NormalGum)
                g.setColor(Color.WHITE);
            else if(gum instanceof SneakyGum)
                g.setColor(Color.MAGENTA);
            else if(gum instanceof SuperGum)
                g.setColor(Color.ORANGE);
            else
                g.setColor(Color.GREEN);
            g.fillOval(
                    (int) (gum.getPosition()[0] - GameSpace.TILE_SIZE_HALF + (GameSpace.TILE_SIZE / 2.5)) * SCALE,
                    (int) (gum.getPosition()[1] - GameSpace.TILE_SIZE_HALF + (GameSpace.TILE_SIZE / 2.5)) * SCALE,
                    GameSpace.TILE_SIZE * 1/5 * SCALE,
                    GameSpace.TILE_SIZE * 1/5 * SCALE);
        }

        // Ghosts
        g.setColor(Color.GREEN);
        for(Ghost ghost : space.getGhosts())
            g.fillOval(
                    (int) (ghost.getPosition()[0] - GameSpace.TILE_SIZE_HALF + (GameSpace.TILE_SIZE / 5)) * SCALE,
                    (int) (ghost.getPosition()[1] - GameSpace.TILE_SIZE_HALF + (GameSpace.TILE_SIZE / 5)) * SCALE,
                    GameSpace.TILE_SIZE * 3/5 * SCALE,
                    GameSpace.TILE_SIZE * 3/5 * SCALE);

        // Pacman
        g.setColor(Color.YELLOW);
        g.fillOval(
                (int) (space.getPacman().getPosition()[0] - GameSpace.TILE_SIZE_HALF + (GameSpace.TILE_SIZE / 5)) * SCALE,
                (int) (space.getPacman().getPosition()[1] - GameSpace.TILE_SIZE_HALF + (GameSpace.TILE_SIZE / 5)) * SCALE,
                GameSpace.TILE_SIZE * 3/5 * SCALE,
                GameSpace.TILE_SIZE * 3/5 * SCALE);

    }
}
