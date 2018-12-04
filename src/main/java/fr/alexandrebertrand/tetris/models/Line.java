package fr.alexandrebertrand.tetris.models;

import java.util.ArrayList;

/**
 * Line of the grid
 *
 * @author Alexandre Bertrand
 */
public class Line {

    /*
     * Attribute
     */

    /** Points of the line */
    private final ArrayList<Integer> points;

    /*
     * Construtor
     */

    /**
     * Default constructor
     * @param xCases Number of X cases
     */
    public Line(int xCases) {
        points = new ArrayList<>();
        for (int i = 0; i < xCases; i++) {
            points.add(0);
        }
    }

    /*
     * Methods
     */

    /**
     * Know if the line is clear
     * @return true if the line is clear
     */
    public boolean isClear() {
        return points.stream().noneMatch((p) -> (p == 0));
    }

    /**
     * Update points of the line
     * @param x Index of the point
     * @param v Value if the point
     */
    public void updatePoint(int x, int v) {
        points.set(x, v);
    }

    /*
     * Getter
     */

    public ArrayList<Integer> getPoints() {
        return points;
    }

}
