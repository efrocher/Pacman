package Pacman.View;

import Pacman.Space.GameSpace;

import javax.swing.*;
import java.awt.*;

public class ScoreLabel extends JLabel implements ScoreObserver{

    // Attributs
    private static final String BASE_TEXT = "SCORE : ";

    // GetSet

    // Constructeurs
    public ScoreLabel(GameSpace space, Color color) {

        super();

        setForeground(color);
        setScore(space.subscribeToScoreUpdates(this));
        setFont(new Font("Dialog", Font.PLAIN, GameView.HUD_LABEL_DIMENSION.height));

    }

    // Méthodes
    public void setScore(int score){
        setText(BASE_TEXT + score);
    }
    @Override
    public void onScoreChanged(int newScore) {
        setScore(newScore);
    }
}
