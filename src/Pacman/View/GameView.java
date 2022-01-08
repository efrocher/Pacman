package Pacman.View;

import Pacman.GameSpace.Entities.Ghost;
import Pacman.GameSpace.Entities.States.PacStates.*;
import Pacman.GameSpace.GameSpace;
import Pacman.GameSpace.GridElements.Gate;
import Pacman.GameSpace.GridElements.GridElement;
import Pacman.GameSpace.GridElements.Gums.*;
import Pacman.GameSpace.GridElements.Wall;
import Pacman.GameSpace.SpaceObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

// Classe gérant l'affichage du jeu
public class GameView extends JPanel implements SpaceObserver {

    /// --- Constantes --- ///
    public final static int SCALE = 2;
    public final static int HUD_HEIGHT = GameSpace.TILE_SIZE; // px
    public final static Dimension HUD_LABEL_DIMENSION = new Dimension(GameSpace.TILE_SIZE * 5 * SCALE, HUD_HEIGHT * 3/5 * SCALE);
    public final static Dimension HUD_START_LABEL_DIMENSION = new Dimension(GameSpace.TILE_SIZE * 10 * SCALE, HUD_HEIGHT * SCALE);
    public final static int[] SCORE_LABEL_POSITION = new int[] {GameSpace.TILE_SIZE * SCALE, HUD_HEIGHT / 5 * SCALE};
    public final static int[] LIVES_LABEL_POSITION = new int[] {(GameSpace.TILE_SIZE * SCALE) + HUD_LABEL_DIMENSION.width, HUD_HEIGHT / 5 * SCALE};
    public final static int[] START_LABEL_POSITION = new int[] {(GameSpace.WIDTH / 2 * SCALE) - (HUD_START_LABEL_DIMENSION.width / 2), (GameSpace.HEIGHT / 2 * SCALE)};
    private final static Color COLOR_HUD = Color.WHITE;
    private final static Color COLOR_HUD_STARTLABEL = Color.RED;
    private final static Color COLOR_BACKGROUND = Color.BLACK;
    //private final static Color COLOR_GAME_BACKGROUND = Color.getHSBColor(0.59f, 0.82f, 0.51f);
    private final static Color COLOR_GAME_BACKGROUND = Color.BLACK;
    //private final static Color COLOR_WALL = Color.BLACK;
    private final static Color COLOR_WALL = Color.getHSBColor(0.63f, 0.90f, 0.50f);
    private final static Color COLOR_PACMAN_NORMAL = Color.YELLOW;
    private final static Color COLOR_PACMAN_SNEAKY = Color.getHSBColor(0.16f, 0.35f, 0.86f);
    private final static Color COLOR_PACMAN_SUPER = Color.ORANGE;
    private final static Color COLOR_GHOST_NORMAL = Color.getHSBColor(0.375f, 0.65f, 0.50f);
    //private final static Color COLOR_GHOST_WEAK = Color.getHSBColor(0.19f, 0.68f, 0.61f);
    private final static Color COLOR_GHOST_WEAK = Color.getHSBColor(0.59f, 0.82f, 0.65f);
    private final static Color COLOR_GHOST_FACE = Color.WHITE;
    //private final static Color COLOR_GUM_NORMAL = Color.WHITE;
    private final static Color COLOR_GUM_NORMAL = Color.getHSBColor(0.59f, 0.62f, 0.81f);
    private final static Color COLOR_GUM_SNEAKY = Color.MAGENTA;
    private final static Color COLOR_GUM_SUPER = Color.ORANGE;
    private final static Color COLOR_GUM_MAZE = Color.GREEN;

    /// --- Attributs --- ///
    private final GameSpace space;
    private final ScoreLabel scoreLabel;
    private final LivesLabel livesLabel;
    private final JLabel startLabel;
    private Color currentPacmanColor;
    private Color currentGhostColor;
    private Area pacmanShapeClosed;
    private Area pacmanShapeOpen;
    private Area ghostMainShape;
    private Area ghostFaceShape;

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
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
        startLabel = new JLabel("PUSH ANY DIRECTION TO START", SwingConstants.CENTER);
        startLabel.setForeground(COLOR_HUD);
        startLabel.setOpaque(true);
        startLabel.setBackground(COLOR_BACKGROUND);
        startLabel.setLocation(START_LABEL_POSITION[0], START_LABEL_POSITION[1]);
        startLabel.setSize(HUD_START_LABEL_DIMENSION);
        startLabel.setFont(new Font("Dialog", Font.PLAIN, GameView.HUD_START_LABEL_DIMENSION.height * 3/5));
        add(startLabel);

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

        // Generation des formes
        generatePacmanShapes();
        generateGhostShapes();

    }

    /// --- Méthodes --- ///
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.translate(0, HUD_HEIGHT * SCALE);

        drawBackground(g2D);
        drawWalls(g2D);
        drawNonWallElements(g2D);
        drawEntities(g2D);

        g2D.translate(0, HUD_HEIGHT * -SCALE);
        drawHudLine(g2D);

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
                SCALE,
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
                            (y * GameSpace.TILE_SIZE) * SCALE,
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
                ((y * GameSpace.TILE_SIZE) + GameSpace.TILE_SIZE_HALF - (GameSpace.TILE_SIZE / 10)) * SCALE,
                GameSpace.TILE_SIZE / 5 * SCALE,
                GameSpace.TILE_SIZE / 5 * SCALE);

    }
    private void drawEntities(Graphics2D g) {

        // Ghosts
        for(Ghost ghost : space.getGhosts()){

            // Position
            int x = (int)(ghost.getPosition()[0]) * SCALE;
            int y = (int)(ghost.getPosition()[1]) * SCALE;
            g.translate(x, y);

            // Dessin
            g.setColor(currentGhostColor);
            g.fill(ghostMainShape);
            g.setColor(COLOR_GHOST_FACE);
            g.fill(ghostFaceShape);

            // Rollback
            g.translate(-x, -y);

        }

        // Pacman
        g.setColor(currentPacmanColor);
        drawPacman(g);

    }
    private void drawPacman(Graphics2D g){

        // Position
        int pacX = (int)(space.getPacman().getPosition()[0]) * SCALE;
        int pacY = (int)(space.getPacman().getPosition()[1]) * SCALE;
        g.translate(pacX, pacY);

        // Gestion rotation et ouverture bouche
        if(System.currentTimeMillis() % 400 > 200){
            AffineTransform transform = new AffineTransform();
            Area pacmanShapeOpenRotated = (Area)pacmanShapeOpen.clone();
            switch (space.getPacman().getDirection()) {
                case UP     -> transform.rotate(Math.PI + Math.PI / 2);
                case DOWN   -> transform.rotate(Math.PI / 2);
                case LEFT   -> transform.rotate(Math.PI);
            }
            pacmanShapeOpenRotated.transform(transform);
            g.fill(pacmanShapeOpenRotated);
        }
        else
            g.fill(pacmanShapeClosed);

        g.translate(-pacX, -pacY);

    }
    @Override
    public void onGameStarted() {
        remove(startLabel);
    }
    @Override
    public void onPacStateChanged(PacState state) {
        if(state instanceof PacState_Regular || state instanceof PacState_Waiting) {
            currentPacmanColor = COLOR_PACMAN_NORMAL;
            currentGhostColor = COLOR_GHOST_NORMAL;
        }
        else if(state instanceof PacState_Sneaky) {
            currentPacmanColor = COLOR_PACMAN_SNEAKY;
            currentGhostColor = COLOR_GHOST_NORMAL;
        }
        else if(state instanceof PacState_Super) {
            currentPacmanColor = COLOR_PACMAN_SUPER;
            currentGhostColor = COLOR_GHOST_WEAK;
        }
    }
    @Override
    public void onLivesChanged(int newLives) {
        livesLabel.setLives(newLives);
    }
    @Override
    public void onScoreChanged(int newScore) {
       scoreLabel.setScore(newScore);
    }

    /// --- Méthodes de générations des formes, appelées dans le constructeur --- ///
    private void generatePacmanShapes(){

        // Body
        double diametre = GameSpace.TILE_SIZE * 3f/5 * SCALE;
        pacmanShapeClosed = new Area(new Ellipse2D.Double(
                -diametre / 2,
                -diametre / 2,
                diametre,
                diametre));

        // Mouth
        pacmanShapeOpen = (Area)pacmanShapeClosed.clone();
        java.awt.Polygon mouth = new java.awt.Polygon();
        mouth.addPoint(0, 0);
        mouth.addPoint((int) diametre, (int) -diametre);
        mouth.addPoint((int) diametre, (int) diametre);
        pacmanShapeOpen.subtract(new Area(mouth));

    }
    private void generateGhostShapes(){

        // Head
        double diametre = GameSpace.TILE_SIZE * 12f/25 * SCALE;
        ghostMainShape = new Area(new Ellipse2D.Double(
                -diametre / 2,
                (-diametre / 2) - (GameSpace.TILE_SIZE * 2f/25 * SCALE),
                diametre,
                diametre));

        // Body
        ghostMainShape.add(new Area(new Rectangle(
                -GameSpace.TILE_SIZE * 6/25 * SCALE,
                -GameSpace.TILE_SIZE * 2/25 * SCALE,
                GameSpace.TILE_SIZE * 12/25 * SCALE,
                GameSpace.TILE_SIZE * 8/25 * SCALE)));

        // "Tails"
        java.awt.Polygon tail = new java.awt.Polygon();
        tail.addPoint(-GameSpace.TILE_SIZE * 6/25 * SCALE, GameSpace.TILE_SIZE * 6/25 * SCALE);
        tail.addPoint(-GameSpace.TILE_SIZE * 2/25 * SCALE, GameSpace.TILE_SIZE * 6/25 * SCALE);
        tail.addPoint(-GameSpace.TILE_SIZE * 4/25 * SCALE, GameSpace.TILE_SIZE * 10/25 * SCALE);
        ghostMainShape.add(new Area(tail));
        tail.translate(GameSpace.TILE_SIZE * 4/25 * SCALE, 0);
        ghostMainShape.add(new Area(tail));
        tail.translate(GameSpace.TILE_SIZE * 4/25 * SCALE, 0);
        ghostMainShape.add(new Area(tail));

        // Face
        ghostFaceShape = new Area();
        ghostFaceShape.add(new Area( new Ellipse2D.Double(
                -GameSpace.TILE_SIZE * 5f/25 * SCALE,
                -GameSpace.TILE_SIZE * 3f/25 * SCALE,
                GameSpace.TILE_SIZE * 4f/25 * SCALE,
                GameSpace.TILE_SIZE * 4f/25 * SCALE)));
        ghostFaceShape.add(new Area( new Ellipse2D.Double(
                GameSpace.TILE_SIZE * 1f/25 * SCALE,
                -GameSpace.TILE_SIZE * 3f/25 * SCALE,
                GameSpace.TILE_SIZE * 4f/25 * SCALE,
                GameSpace.TILE_SIZE * 4f/25 * SCALE)));
        ghostFaceShape.add(new Area( new Rectangle(
                -GameSpace.TILE_SIZE * 3/25 * SCALE,
                GameSpace.TILE_SIZE * 2/25 * SCALE,
                GameSpace.TILE_SIZE * 6/25 * SCALE,
                GameSpace.TILE_SIZE * 1/25 * SCALE)));

    }
}
