import javax.swing.*;
import java.util.Random;

public class App {

    private static final int FRAME_LOCATION_X = 900;
    private static final int FRAME_LOCATION_Y = 400;

    // Ne touchez pas à ce code
    public static void main(String[] args) throws InterruptedException {

        // Rng
        Random rng = new Random();

        // Création des éléments
        GameSpace gameSpace = new GameSpace(rng);
        GameView view = new GameView(gameSpace);
        GameController controller = new GameController(gameSpace, view);

        // Création de la fenêtre
        JFrame frame = new JFrame("Pacman");
        frame.add(view);
        frame.setSize(view.getSize());
        frame.setLocation(FRAME_LOCATION_X, FRAME_LOCATION_Y);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Lancement
        controller.run();
    }

}
