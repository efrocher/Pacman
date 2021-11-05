package Pacman.View;

import Pacman.Space.Entities.Ghost;
import Pacman.Space.Entities.PacStates.PacState;
import Pacman.Space.Entities.PacStates.RegularState;
import Pacman.Space.Entities.PacStates.SneakyState;
import Pacman.Space.Entities.PacStates.SuperState;
import Pacman.Space.GameSpace;
import Pacman.Space.GridElements.Gate;
import Pacman.Space.GridElements.GridElement;
import Pacman.Space.GridElements.Gums.*;
import Pacman.Space.GridElements.Wall;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel implements SpaceObserver{

    // Constantes
    public final static int SCALE = 2;
    public final static int HUD_HEIGHT = GameSpace.TILE_SIZE; // px
    public final static Dimension HUD_LABEL_DIMENSION = new Dimension(GameSpace.TILE_SIZE * 5 * SCALE, HUD_HEIGHT * 3/5 * SCALE);
    public final static int[] SCORE_LABEL_POSITION = new int[] {GameSpace.TILE_SIZE * SCALE, HUD_HEIGHT / 5 * SCALE};
    public final static int[] LIVES_LABEL_POSITION = new int[] {(GameSpace.TILE_SIZE * SCALE) + HUD_LABEL_DIMENSION.width, HUD_HEIGHT / 5 * SCALE};
    private final static Color COLOR_HUD = Color.WHITE;
    private final static Color COLOR_BACKGROUND = Color.BLACK;
    private final static Color COLOR_GAME_BACKGROUND = Color.getHSBColor(0.59f, 0.82f, 0.51f);
    private final static Color COLOR_WALL = Color.BLACK;
    private final static Color COLOR_PACMAN_NORMAL = Color.YELLOW;
    private final static Color COLOR_PACMAN_SNEAKY = Color.getHSBColor(0.16f, 0.68f, 0.86f);
    private final static Color COLOR_PACMAN_SUPER = Color.ORANGE;
    private final static Color COLOR_GHOST_NORMAL = Color.GREEN;
    private final static Color COLOR_GHOST_WEAK = Color.getHSBColor(0.19f, 0.68f, 0.61f);
    private final static Color COLOR_GUM_NORMAL = Color.WHITE;
    private final static Color COLOR_GUM_SNEAKY = Color.MAGENTA;
    private final static Color COLOR_GUM_SUPER = Color.ORANGE;
    private final static Color COLOR_GUM_MAZE = Color.GREEN;
    private Color currentPacmanColor;
    private Color currentGhostColor;


    // Attributs
    private GameSpace space;
    private ScoreLabel scoreLabel;
    private LivesLabel livesLabel;

    // GetSet

    // Constructeurs
    public GameView(GameSpace gameSpace) {

        super();

        this.space = gameSpace;

        // Self
        setBackground(COLOR_BACKGROUND);
        setLayout(null);
        setOpaque(true);
        setSize(GameSpace.WIDTH * SCALE, (GameSpace.HEIGHT + HUD_HEIGHT) * SCALE);
        space.subscribe(this);

        // State-depending colors
        currentPacmanColor = COLOR_PACMAN_NORMAL;
        currentGhostColor = COLOR_GHOST_NORMAL;

        // Labels
        scoreLabel = new ScoreLabel(COLOR_HUD);
        scoreLabel.setScore(space.getScore());
        scoreLabel.setLocation(SCORE_LABEL_POSITION[0], SCORE_LABEL_POSITION[1]);
        scoreLabel.setSize(HUD_LABEL_DIMENSION);
        add(scoreLabel);

        livesLabel = new LivesLabel(COLOR_HUD);
        livesLabel.setLives(space.getPacman().getLives());
        livesLabel.setLocation(LIVES_LABEL_POSITION[0], LIVES_LABEL_POSITION[1]);
        livesLabel.setSize(HUD_LABEL_DIMENSION);
        add(livesLabel);

    }

    // Méthodes
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        drawBackground(g);
        drawWalls(g);
        drawNonWallElements(g);
        drawEntities(g);
        drawHudLine(g);

    }
    private void drawHudLine(Graphics g){

        g.setColor(COLOR_HUD);
        g.drawLine(
                0,
                GameSpace.TILE_SIZE * SCALE,
                GameSpace.WIDTH * SCALE,
                GameSpace.TILE_SIZE * SCALE);

    }
    private void drawBackground(Graphics g){

        g.setColor(COLOR_GAME_BACKGROUND);
        g.fillRect(
                0,
                HUD_HEIGHT * SCALE,
                GameSpace.WIDTH * SCALE,
                GameSpace.HEIGHT * SCALE);

    }
    private void drawWalls(Graphics g) {

        g.setColor(COLOR_WALL);

        for(int x = 0; x < GameSpace.TILE_AMOUNT_H; x++ )
            for(int y = 0; y < GameSpace.TILE_AMOUNT_V; y++ ){
                GridElement element = space.getElementAt(new int[] {x, y});
                if(element != null && (element instanceof Wall || (element instanceof Gate && !element.isCrosseable()))){
                    g.fillRect(
                            x * GameSpace.TILE_SIZE * SCALE,
                            ((y * GameSpace.TILE_SIZE) + HUD_HEIGHT) * SCALE,
                            GameSpace.TILE_SIZE * SCALE,
                            GameSpace.TILE_SIZE * SCALE);
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
                ((y * GameSpace.TILE_SIZE) + GameSpace.TILE_SIZE_HALF + HUD_HEIGHT - (GameSpace.TILE_SIZE / 10)) * SCALE,
                GameSpace.TILE_SIZE / 5 * SCALE,
                GameSpace.TILE_SIZE / 5 * SCALE);

    }
    private void drawEntities(Graphics g) {

        // Ghosts
        g.setColor(currentGhostColor);
        for(Ghost ghost : space.getGhosts())
            g.fillOval(
                    (int) (ghost.getPosition()[0] - GameSpace.TILE_SIZE_HALF + (GameSpace.TILE_SIZE / 5)) * SCALE,
                    (int) (ghost.getPosition()[1] - GameSpace.TILE_SIZE_HALF + HUD_HEIGHT + (GameSpace.TILE_SIZE / 5)) * SCALE,
                    GameSpace.TILE_SIZE * 3/5 * SCALE,
                    GameSpace.TILE_SIZE * 3/5 * SCALE);


        // Pacman.Space.Entities.Pacman
        g.setColor(currentPacmanColor);
        g.fillOval(
                (int) (space.getPacman().getPosition()[0] - GameSpace.TILE_SIZE_HALF + (GameSpace.TILE_SIZE / 5)) * SCALE,
                (int) (space.getPacman().getPosition()[1] - GameSpace.TILE_SIZE_HALF + HUD_HEIGHT + (GameSpace.TILE_SIZE / 5)) * SCALE,
                GameSpace.TILE_SIZE * 3/5 * SCALE,
                GameSpace.TILE_SIZE * 3/5 * SCALE);

    }
    @Override
    public void onPacStateChanged(PacState state) {
        if(space.getPacman().getState() instanceof RegularState) {
            currentPacmanColor = COLOR_PACMAN_NORMAL;
            currentGhostColor = COLOR_GHOST_NORMAL;
        }
        else if(space.getPacman().getState() instanceof SneakyState) {
            currentPacmanColor = COLOR_PACMAN_SNEAKY;
            currentGhostColor = COLOR_GHOST_NORMAL;
        }
        else if(space.getPacman().getState() instanceof SuperState) {
            currentPacmanColor = COLOR_PACMAN_SUPER;
            currentGhostColor = COLOR_GHOST_WEAK;
        }
    }
    @Override
    public void onLivesChanged(int newLives) {
        scoreLabel.setScore(newLives);
    }
    @Override
    public void onScoreChanged(int newScore) {
       scoreLabel.setScore(newScore);
    }
}
