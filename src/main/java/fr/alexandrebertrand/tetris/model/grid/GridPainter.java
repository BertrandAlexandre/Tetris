package fr.alexandrebertrand.tetris.model.grid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import fr.alexandrebertrand.tetris.util.graphic.*;
import fr.alexandrebertrand.tetris.util.settings.*;

/**
 * Grid painter
 * 
 * @author Alexandre Bertrand
 */
public final class GridPainter {

    /*
     * Constructor
     */

    /**
     * Empty private constructor
     */
    private GridPainter() {
    }

    /*
     * Methods
     */

    /**
     * Paint grid of the game
     * 
     * @param g Graphic context
     */
    public static void paintGrid(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i < Settings.getXCases(); i++) {
            for (int j = 0; j < Settings.getYCases(); j++) {
                PaintManager.fillBorderRect(g, new Point(i, j));
            }
        }
    }

    /**
     * Paint lines on the grid
     * 
     * @param g    Graphic context
     * @param grid Grid of the game containing lines
     */
    public static void paintLines(Graphics g, ArrayList<Line> grid) {
        for (int i = 0; i < Settings.getYCases(); i++) {
            for (int j = 0; j < Settings.getXCases(); j++) {
                if (grid.get(i).getPoints().get(j) != null) {
                    g.setColor(grid.get(i).getPoints().get(j).getBorderColor());
                    PaintManager.fillBorderRect(g, new Point(j, i));
                    g.setColor(grid.get(i).getPoints().get(j).getColor());
                    PaintManager.fillRect(g, new Point(j, i));
                }
            }
        }
    }

}
