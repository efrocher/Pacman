import javax.swing.*;
import java.util.Random;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class App {

    private static final int FRAME_DEFAULT_POSITION_X = 200;
    private static final int FRAME_DEFAULT_POSITION_Y = 200;

    // Ne touchez pas à ce code
    public static void main(String[] args) throws InterruptedException {

        // Utilitaires
        Random rng = new Random();
        InputManager inputManager = new InputManager();

        // Création des éléments du jeu
        GameSpace space = new GameSpace(rng, inputManager);
        GameView view = new GameView(space);
        GameController controller = new GameController(space, view);

        // Création de la fenêtre
        JFrame frame = new JFrame("Pacman");
        frame.add(view);
        frame.addKeyListener(inputManager);
        frame.getContentPane().setPreferredSize(view.getSize());
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Lancement
        controller.run();
    }

}
