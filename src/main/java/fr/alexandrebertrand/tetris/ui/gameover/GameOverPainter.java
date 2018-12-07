package fr.alexandrebertrand.tetris.ui.gameover;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import fr.alexandrebertrand.tetris.Board;
import fr.alexandrebertrand.tetris.util.graphic.FontManager;
import fr.alexandrebertrand.tetris.util.settings.Settings;

/**
 * Game over painter
 * 
 * @author Alexandre Bertrand
 */
public final class GameOverPainter {

    /*
     * Constructor
     */

    /**
     * Empty private constructor
     */
    private GameOverPainter() {
    }

    /*
     * Method
     */

    /**
     * Paint the game over screen
     * 
     * @param g     Graphic context
     * @param b     Board of the game
     * @param score Score of player
     */
    public static void paint(Graphics g, Board b, int score) {
        g.setColor(Color.WHITE);
        g.fillRoundRect(80, 40, Settings.getGridDimensions().width
                + Settings.getBoxSize() * 4 - 80,
                Settings.getGridDimensions().height - 80, 44, 44);

        g.setColor(Color.DARK_GRAY);
        g.fillRoundRect(84, 44, Settings.getGridDimensions().width
                + Settings.getBoxSize() * 4 - 88,
                Settings.getGridDimensions().height - 88, 40, 40);

        g.setColor(Color.WHITE);

        g.setFont(FontManager.getFont(38f));
        FontMetrics curFM = b.getFontMetrics(FontManager.getFont(38f));
        String t = "Game Over";
        g.drawString(t, (b.getWidth() - curFM.stringWidth(t)) / 2, 100);

        g.fillRect(120, 130, b.getWidth() - 240, 3);
        g.setFont(FontManager.getFont(24f));
        curFM = b.getFontMetrics(FontManager.getFont(24f));
        t = "Score : " + score;
        g.drawString(t, (b.getWidth() - curFM.stringWidth(t)) / 2, 300);

        g.setFont(FontManager.getFont(20f));
        curFM = b.getFontMetrics(FontManager.getFont(20f));
        t = "Press 'R' to restart";
        g.drawString(t, (b.getWidth() - curFM.stringWidth(t)) / 2, (b.getHeight() - 80));
    }

}
