package Pacman.View;

import javax.swing.*;
import java.awt.*;

public class ScoreLabel extends JLabel {

    // Attributs
    private static final String BASE_TEXT = "SCORE : ";

    // GetSet

    // Constructeurs
    public ScoreLabel(int startScore, Color color) {

        super();

        setForeground(color);
        setScore(startScore);
        setFont(new Font("Dialog", Font.PLAIN, GameView.HUD_LABEL_DIMENSION.height));

    }

    // Méthodes
    public void setScore(int score){
        setText(BASE_TEXT + score);
    }

}
