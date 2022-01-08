package Pacman.View;

import javax.swing.*;
import java.awt.*;

public class ScoreLabel extends JLabel {

    /// --- Attributs --- ///
    private static final String BASE_TEXT = "SCORE : ";

    /// --- GetSet --- ///

    /// --- Constructeurs --- ///
    public ScoreLabel(Color color) {

        super();

        setForeground(color);
        setText(BASE_TEXT);
        setFont(new Font("Dialog", Font.PLAIN, GameView.HUD_LABEL_DIMENSION.height));

    }

    /// --- Méthodes --- ///
    public void setScore(int score){
        setText(BASE_TEXT + score);
    }
}
