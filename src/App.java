import Pacman.Controller.GameController;
import Pacman.GameSpace.GameSpace;
import Pacman.View.GameView;
import Pacman.Inputs.InputManager;

import javax.swing.*;
import java.util.Random;

public class App {

    public static void main(String[] args) throws InterruptedException {

        // Utilitaires
        Random rng = new Random();
        InputManager inputManager = new InputManager();

        // Création des éléments du jeu
        GameSpace space = new GameSpace(rng);
        GameView view = new GameView(space);
        GameController controller = new GameController(space, view);

        // Création de la fenêtre
        JFrame frame = new JFrame("Pacman");
        frame.getContentPane().add(view);
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
