package fr.alexandrebertrand.tetris.util.graphic;

import java.awt.Graphics;
import java.awt.Point;

import fr.alexandrebertrand.tetris.util.settings.Settings;

/**
 * Paint manager of the game
 * 
 * @author Alexandre Bertrand
 */
public final class PaintManager {

    /*
     * Constructors
     */

    /**
     * Empty private constructor
     */
    private PaintManager() {
    }

    /*
     * Methods
     */

    /**
     * Paint rectangle (box into the grid)
     * 
     * @param g Graphic context
     * @param p Position of the element
     */
    public static void fillBorderRect(Graphics g, Point p) {
        g.fillRect(
                p.x * Settings.getBoxSize() + 1,
                p.y * Settings.getBoxSize()  + 1,
                Settings.getBoxSize() - 2,
                Settings.getBoxSize() - 2
        );
    }
    
    /**
     * Paint rectangle (box into the grid)
     * 
     * @param g Graphic context
     * @param p Position of the element
     * @param o Offset
     */
    public static void fillBorderRect(Graphics g, Point p, Point o) {
        g.fillRect(
                p.x * Settings.getBoxSize() + 1 + o.x,
                p.y * Settings.getBoxSize()  + 1 + o.y,
                Settings.getBoxSize() - 2,
                Settings.getBoxSize() - 2
        );
    }
    
    /**
     * Paint background rectangle
     * 
     * @param g Graphic context
     * @param p Position of the element
     */
    public static void fillRect(Graphics g, Point p) {
        g.fillRect(
                p.x * Settings.getBoxSize() + 2,
                p.y * Settings.getBoxSize() + 2,
                Settings.getBoxSize() - 4,
                Settings.getBoxSize() - 4
        );
    }
    
    /**
     * Paint background rectangle
     * 
     * @param g Graphic context
     * @param p Position of the element
     * @param o Offset
     */
    public static void fillRect(Graphics g, Point p, Point o) {
        g.fillRect(
                p.x * Settings.getBoxSize() + 2 + o.x,
                p.y * Settings.getBoxSize() + 2 + o.y,
                Settings.getBoxSize() - 4,
                Settings.getBoxSize() - 4
        );
    }

}
