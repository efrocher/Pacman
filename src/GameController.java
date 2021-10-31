public class GameController {

    // Constantes
    public static final int TARGET_TICK_TIME = 20; // ms
    public static final float DELTA = TARGET_TICK_TIME / 1000f;

    // Attributs
    private GameSpace space;
    private GameView view;
    private double timeStamp;

    // GetSet

    // Constructeurs
    public GameController(GameSpace gameSpace, GameView view){
        this.space = gameSpace;
        this.view = view;
    }

    // Méthodes
    public void run() throws InterruptedException {

        timeStamp = System.nanoTime() / 1e6;
        while(true){

            // Refresh frame
            view.repaint();

            // Comportement des entités
            while(System.nanoTime() / 1e6 > timeStamp + TARGET_TICK_TIME){
                space.getPacman().behave();
                for(Ghost g : space.getGhosts())
                    g.behave();
                timeStamp += TARGET_TICK_TIME;
            }

        }
    }

}
