import javax.swing.*;
import java.awt.*;

public class GameView extends JComponent {

    // Constantes
    public final static int SCALE = 1;

    // Attributs
    private GameSpace gameSpace;

    // GetSet

    // Constructeurs
    public GameView(GameSpace gameSpace) {
        super();
        setOpaque(true);
        setSize(GameSpace.WIDTH * SCALE, GameSpace.HEIGHT * SCALE);

        this.gameSpace = gameSpace;
    }

    // Méthodes
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        setBackground(Color.BLUE);
        drawLabyrinth(g);
        drawEntities(g);
    }
    private void drawLabyrinth(Graphics g) {
        g.setColor(Color.BLACK);
        for(int x = 0; x < GameSpace.TILE_AMOUNT_H; x++ )
            for(int y = 0; y < GameSpace.TILE_AMOUNT_V; y++ )
                if(!gameSpace.getLabyrinth()[x][y]){
                    g.fillRect(x * GameSpace.TILE_SIZE * SCALE, y * GameSpace.TILE_SIZE * SCALE, GameSpace.TILE_SIZE * SCALE, GameSpace.TILE_SIZE * SCALE);
                }
    }
    private void drawEntities(Graphics g){

        // Pacman
        g.setColor(Color.YELLOW);
        g.fillOval((int)gameSpace.getPacman().xPos * SCALE, (int)gameSpace.getPacman().yPos * SCALE, 15 * SCALE, 15 * SCALE);
    }
}
