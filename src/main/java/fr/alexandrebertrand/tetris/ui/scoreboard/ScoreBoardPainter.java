package fr.alexandrebertrand.tetris.ui.scoreboard;

import java.awt.Color;
import java.awt.Graphics;

import fr.alexandrebertrand.tetris.util.graphic.FontManager;
import fr.alexandrebertrand.tetris.util.settings.Settings;

/**
 * Score board painter
 * 
 * @author Alexandre Bertrand
 */
public final class ScoreBoardPainter {

    /*
     * Constructor
     */

    /**
     * Empty private constructor
     */
    private ScoreBoardPainter() {
    }

    /*
     * Method
     */

    /**
     * Paint the right score board panel
     * 
     * @param g       Graphic context
     * @param level   Current level of the game
     * @param nbLines Current number of cleared lines
     * @param score   Current player score
     */
    public static void paint(Graphics g, int level, int nbLines, int score) {
        g.setFont(FontManager.getFont(12f));
        
        int bs = Settings.getBoxSize();
        int rb = (int) Settings.getGridDimensions().getWidth();
        
        g.setColor(Color.BLACK);
        g.fillRoundRect(rb + 20, 25, bs * 4 + 40, 40, 40, 40);
        g.fillRoundRect(rb + 20, 75, bs * 4 + 40, 40, 40, 40);
        g.fillRoundRect(rb + 20, 125, bs * 4 + 40, 40, 40, 40);
        g.fillRoundRect(rb + 20, 210, bs * 4 + 40, bs * 2 + 20, 40, bs * 2 + 20);
        g.fillRoundRect(rb + 20, bs * 2 + 275, bs * 4 + 40, bs * 6 + 40, 40, bs * 6 + 40);

        g.setColor(Color.WHITE);
        g.drawString("Level : " + level, rb + 40, 50);
        g.drawString("Lines : " + nbLines, rb + 40, 100);
        g.drawString("Score : " + score, rb + 40, 150);
        g.drawString("Hold : ", rb + 40, 200);
        g.drawString("Next : ", rb + 40, bs * 2 + 265);
    }

}
