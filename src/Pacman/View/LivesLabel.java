package Pacman.View;

import Pacman.Space.GameSpace;

import javax.swing.*;
import java.awt.*;

public class LivesLabel extends JLabel {

    // Attributs
    private static final String BASE_TEXT = "LIVES : ";

    // GetSet

    // Constructeurs
    public LivesLabel(Color color) {

        super();

        setForeground(color);
        setText(BASE_TEXT);
        setFont(new Font("Dialog", Font.PLAIN, GameView.HUD_LABEL_DIMENSION.height));

    }

    // Méthodes
    public void setLives(int newLives){
        setText(BASE_TEXT + newLives);
    }
}
