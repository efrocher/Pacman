import javax.naming.SizeLimitExceededException;

public class GameController {

    // Constantes

    // Attributs
    private GameSpace gameSpace;
    private GameView view;

    // GetSet

    // Constructeurs
    public GameController(GameSpace gameSpace, GameView view){
        this.gameSpace = gameSpace;
        this.view = view;
    }

    // Méthodes
    public void run() throws InterruptedException {
        while(true){
            view.repaint();
            Thread.sleep(1000);
        }
    }

}
