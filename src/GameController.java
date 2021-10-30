public class GameController {

    // Constantes
    public final static int TARGET_FRAME_TIME = 20; // ms
    public final static float DELTA = 1f / TARGET_FRAME_TIME;

    // Attributs
    private GameSpace space;
    private GameView view;
    private int score;

    // GetSet

    // Constructeurs
    public GameController(GameSpace gameSpace, GameView view){
        this.space = gameSpace;
        this.view = view;
        score = 0;
    }

    // Méthodes
    public void run() throws InterruptedException {
        while(true){

            // Refresh frame
            view.repaint();

            // Comportement des entités
            space.getPacman().behave();
            for(Ghost g : space.getGhosts())
                g.behave();

            // Delay
            Thread.sleep(TARGET_FRAME_TIME);
        }
    }
    public void addPoints(int points){
        score += points;
    }

}
