package fr.alexandrebertrand.tetris.model.grid;

import java.util.HashMap;

import fr.alexandrebertrand.tetris.util.graphic.*;
import fr.alexandrebertrand.tetris.util.settings.*;

/**
 * Line of the grid
 *
 * @author Alexandre Bertrand
 */
public class Line {

    /*
     * Attributes
     */

    /** Points of the line */
    private final HashMap<Integer, ColorType> points;

    /*
     * Constructors
     */

    /**
     * Default constructor
     */
    public Line() {
        points = new HashMap<>();
        for (int i = 0; i < Settings.getXCases(); i++) {
            points.put(i, null);
        }
    }

    /*
     * Methods
     */

    /**
     * Know if the line is clear
     * 
     * @return true if the line is clear
     */
    public boolean isClear() {
        return !points.containsValue(null);
    }

    /**
     * Update points of the line
     * 
     * @param x Index of the point
     * @param v Value if the point
     */
    public void updatePoint(int x, ColorType colorType) {
        points.put(x, colorType);
    }

    /*
     * Getters & Setters
     */

    /**
     * Get points of the line
     * 
     * @return Points of the line
     */
    public HashMap<Integer, ColorType> getPoints() {
        return points;
    }

}
