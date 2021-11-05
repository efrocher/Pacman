package Pacman.View;

import Pacman.Space.GameSpace;

import javax.swing.*;
import java.awt.*;

public class LivesLabel extends JLabel implements LivesObserver{

    // Attributs
    private static final String BASE_TEXT = "LIVES : ";

    // GetSet

    // Constructeurs
    public LivesLabel(GameSpace space, Color color) {

        super();

        setForeground(color);
        setLives(space.getPacman().subscribeToLivesUpdates(this));
        setFont(new Font("Dialog", Font.PLAIN, GameView.HUD_LABEL_DIMENSION.height));

    }

    // Méthodes
    public void setLives(int newLives){
        setText(BASE_TEXT + newLives);
    }
    @Override
    public void onLivesChanged(int newLives) {
        setLives(newLives);
    }
}
